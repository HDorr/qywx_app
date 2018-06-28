package com.ziwow.scrmapp.wechat.wxpay;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.KeyStore;
import java.util.*;

/**
 * 微信支付工具类
 */
public class WeixinPayUtil {
    private static final Logger log = LoggerFactory.getLogger(WeixinPayUtil.class);
	private static final int CONNECT_TIME_OUT = 5000; //链接超时时间3秒
	public static CloseableHttpClient httpclient;

    /**
     * 加载证书
     */
    private static void initCert(String mchid, String certpath) throws Exception {
        // 证书密码，默认为商户ID
        String key = mchid;
        // 证书的路径
        String path = certpath;
        // 指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 读取本机存放的PKCS12证书文件
        InputStream instream=WeixinPayUtil.class.getClassLoader().getResourceAsStream(certpath);
        //FileInputStream instream = new FileInputStream(new File(path));
        try {
            // 指定PKCS12的密码(商户ID)
            keyStore.load(instream, key.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, key.toCharArray()).build();
        SSLConnectionSocketFactory sslsf =
                new SSLConnectionSocketFactory(sslcontext, new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        RequestConfig REQUEST_CONFIG = RequestConfig.custom().
                setConnectTimeout(CONNECT_TIME_OUT)
                .setCircularRedirectsAllowed(true).build();
        httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

	public static String getPayNo(String mchid, String certpath, String url, String xmlParam) {
        // 加载证书
        try {
            initCert(mchid, certpath);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
		HttpPost httpost = new HttpPost(url);
		String prepay_id = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "UTF-8");
			Map<String, Object> dataMap = new HashMap<String, Object>();

			if (jsonStr.indexOf("FAIL") != -1) {
				return prepay_id;
			}
			Map map = doXMLParse(jsonStr);
			String return_code = (String) map.get("return_code");
			prepay_id = (String) map.get("prepay_id");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return prepay_id;
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			m.put(k, v);
		}
		// 关闭流
		in.close();
		return m;
	}

	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	/**
	 * 获取统一订单提交返回字符串值
	 * @param url
	 * @param xmlParam
	 * @return
	 */
	public static String getTradeOrder(String mchid, String certpath, String url, String xmlParam) {
        // 加载证书
        try {
            initCert(mchid, certpath);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
		HttpPost httpost = new HttpPost(url);
		String jsonStr = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return jsonStr;
	}
	
	/**
	 * 生成二维码地址
	 * @param weixinInfoDTO
	 * @return
	 */
	public static String generateCodeUrl(String mchid, String certpath, String orderpay, WeixinInfoDTO weixinInfoDTO){
		String codeUrl = "";
		String submitXml = buildWeixinXml(weixinInfoDTO);
		String resultStr = getTradeOrder(mchid, certpath, orderpay, submitXml);
		if(StringUtils.isNotBlank(resultStr)){
			try {
				Map map = doXMLParse(resultStr);
				codeUrl = (String)map.get("code_url");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		return codeUrl;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static String createSign(SortedMap<String, String> packageParams, String key) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		return sign;

	}
	
	public static String getSign(Map<String,String> paramMap, String key){
		List list = new ArrayList(paramMap.keySet());
		Object[] ary = list.toArray();
		Arrays.sort(ary);
		list = Arrays.asList(ary);
		String str = "";
		for(int i=0;i<list.size();i++){
			str+=list.get(i)+"="+paramMap.get(list.get(i)+"")+"&";
		}
		str+="key="+key;
		str = MD5Util.MD5Encode(str, "UTF-8").toUpperCase();

		return str;
	}
	
	/**
	 * 创建提交统一订单的xml
	 * @return
	 */
	public static String buildWeixinXml(WeixinInfoDTO weixinInfoDTO){
		String xml = "<xml>" + "<appid>"+weixinInfoDTO.getAppid()+"</appid>"
				+ "<body>"+weixinInfoDTO.getBody()+"</body>" + "<mch_id>"+weixinInfoDTO.getMch_id()+"</mch_id>"
				+ "<nonce_str>"+weixinInfoDTO.getNonce_str()+"</nonce_str>"
				+ "<notify_url>"+weixinInfoDTO.getNotify_url()+"</notify_url>"
				+ "<out_trade_no>"+weixinInfoDTO.getOut_trade_no()+"</out_trade_no>"
				+ "<spbill_create_ip>"+weixinInfoDTO.getSpbill_create_ip()+"</spbill_create_ip>"
				+ "<total_fee>"+weixinInfoDTO.getTotal_fee()+"</total_fee>"
				+ "<trade_type>"+weixinInfoDTO.getTrade_type()+"</trade_type>"
				+ "<sign>"+weixinInfoDTO.getSign()+"</sign>" + "</xml>";
		System.out.println(xml);
		return xml;
	}
	
	/**
	 * 处理xml请求信息
	 * @param request
	 * @return
	 */
	public static String getXmlRequest(HttpServletRequest request){
		BufferedReader bis = null;
		String result = "";
		try{
			bis = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			while((line = bis.readLine()) != null){
				result += line;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(bis != null){
				try{
					bis.close();
				}catch(IOException e){
					log.error(e.getMessage(),e);
				}
			}
		}
		return result;
	}
	
	/**
	 * 查询订单
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
	public static Map checkWxOrderPay(String appid, String mchid, String orderpayquery,
									  String key, String certpath, String orderId) throws Exception{
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		Boolean payFlag = false;
		String sign = "";
		SortedMap<String, String> storeMap = new TreeMap<String, String>();
		storeMap.put("out_trade_no", orderId); // 商户 后台的贸易单号
		storeMap.put("appid", appid); // appid
		storeMap.put("mch_id", mchid); // 商户号
		storeMap.put("nonce_str", nonce_str); // 随机数
		sign = createSign(storeMap, key);
		
		String xml = "<xml><appid>"+appid+"</appid>"+
                "<mch_id>"+mchid+"</mch_id>"+
                "<nonce_str>"+nonce_str+"</nonce_str>"+
                "<out_trade_no>"+orderId+"</out_trade_no>"+
                "<sign>"+sign+"</sign></xml>";
		String resultMsg = getTradeOrder(mchid, certpath, orderpayquery, xml);
	    log.info("orderquery,result:" + resultMsg);
		if(StringUtils.isNotBlank(resultMsg)){
	    	Map resultMap = WeixinPayUtil.doXMLParse(resultMsg);
	    	if(resultMap != null && resultMap.size() > 0){
	    		/*String result = (String)resultMap.get("trade_state");
	    		if(StringUtils.isNotBlank(result)){
	    			if(result.equals("SUCCESS") || result.equals("success")){
	    				payFlag = true;
	    			}
	    		}*/
	    		return resultMap;
	    	}
	    }
	    return null;
	}
}
