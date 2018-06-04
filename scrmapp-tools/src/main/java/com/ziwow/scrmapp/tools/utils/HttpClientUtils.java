package com.ziwow.scrmapp.tools.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于httpclient 4.3.4版本 ClassName: HttpClientUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2015年1月14日 上午11:29:29 <br/>
 * 
 * @author frank
 * @version
 * @since JDK 1.6
 */
public class HttpClientUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
	private static int connTimeout = 20000;
	private static int readTimeout = 60000;
	private static CloseableHttpClient httpclient = null;
	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(128);
		cm.setDefaultMaxPerRoute(128);
		httpclient = HttpClients.custom().setConnectionManager(cm).build();
	}

	private static Map<String, String> headers = new HashMap<String, String>();
	static {
		headers.put("Content-Type", "data/gzencode and rsa public encrypt;charset=UTF-8");
	}

	public static String httpPost(String url, String xmlParam) throws ConnectTimeoutException, SocketTimeoutException,
			IOException {
		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		StringBuffer suf = new StringBuffer();
		try {
			httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(60000)
					.build();// 设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			if (url.startsWith("https")) {
				httpclient = createSSLInsecureClient();// 执行 Https 请求.
			} else {
				httpclient = HttpClientUtils.httpclient;// 执行 Http 请求.
			}
			response = httpclient.execute(httpPost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			return jsonStr;
		} catch (Exception e) {
			LOGGER.error("httpPost URL [" + url + "] error, ", e);
			return "";
		} finally {
			httpPost.releaseConnection();
			if (httpclient != null) {
				httpclient.close();
			}
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 
	 * @param url
	 * @param bodyParam
	 * @param headParam
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws IOException
	 */
	public static String httpPost(String url, Map<String, Object> bodyParam, Map<String, Object> headParam)
			throws ConnectTimeoutException, SocketTimeoutException, IOException {
		LOGGER.info("httpPost URL [" + url + "] start ");
		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		StringBuffer suf = new StringBuffer();
		try {
			// httpclient = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			if (MapUtils.isNotEmpty(headParam)) {
				// 设置各种头信息
				for (Entry<String, Object> set : headParam.entrySet()) {
					String key = set.getKey();
					String value = set.getValue() == null ? "" : set.getValue().toString();
					headers.put(key, value);
				}
				for (Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}

			// 传入各种参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (MapUtils.isNotEmpty(bodyParam)) {
				for (Entry<String, Object> set : bodyParam.entrySet()) {
					String key = set.getKey();
					String value = set.getValue() == null ? "" : set.getValue().toString();
					nvps.add(new BasicNameValuePair(key, value));
					suf.append(" [" + key + "-" + value + "] ");
				}
			}
			LOGGER.info("param " + suf.toString());
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(connTimeout); // 连接超时
			customReqConf.setSocketTimeout(readTimeout);// 读数据超时
			httpPost.setConfig(customReqConf.build());

			if (url.startsWith("https")) {
				// 执行 Https 请求.
				httpclient = createSSLInsecureClient();
			} else {
				// 执行 Http 请求.
				httpclient = HttpClientUtils.httpclient;
			}
			// 设置http request头

			response = httpclient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				LOGGER.error("HttpStatus ERROR" + "Method failed: " + response.getStatusLine());
				return "";
			} else {
				entity = response.getEntity();
				if (null != entity) {
					byte[] bytes = EntityUtils.toByteArray(entity);
					result = new String(bytes, "UTF-8");
				} else {
					LOGGER.error("httpPost URL [" + url + "],httpEntity is null.");
				}
				LOGGER.info("result:" + result);
				return result;
			}
		} catch (Exception e) {
			LOGGER.error("httpPost URL [" + url + "] error, ", e);
			return "";
		} finally {
			httpPost.releaseConnection();
			if (url.startsWith("https") && httpclient != null && httpclient instanceof CloseableHttpClient) {
				((CloseableHttpClient) httpclient).close();
			}
			if (response != null) {
				response.close();
			}
			LOGGER.info("RESULT:  [" + result + "]");
			LOGGER.info("httpPost URL [" + url + "] end ");
		}
	}

	public static String httpPost(String url, String jsonBody, Map<String, Object> headParam)
			throws ConnectTimeoutException, SocketTimeoutException, IOException {
		LOGGER.info("httpPost URL [" + url + "] start ");
		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		StringBuffer suf = new StringBuffer();
		try {
			// httpclient = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			if (MapUtils.isNotEmpty(headParam)) {
				// 设置各种头信息
				for (Entry<String, Object> set : headParam.entrySet()) {
					String key = set.getKey();
					String value = set.getValue() == null ? "" : set.getValue().toString();
					headers.put(key, value);
				}
				for (Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}

			// 传入json数据
			// 对entity设置编码格式
			HttpEntity reqEntity = new StringEntity(jsonBody,ContentType.APPLICATION_JSON);
			
			httpPost.setEntity(reqEntity);
			httpPost.setHeader("Cotent-Type", "application/json;charset=utf-8");
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(connTimeout); // 连接超时
			customReqConf.setSocketTimeout(readTimeout);// 读数据超时
			httpPost.setConfig(customReqConf.build());

			if (url.startsWith("https")) {
				// 执行 Https 请求.
				httpclient = createSSLInsecureClient();
			} else {
				// 执行 Http 请求.
				httpclient = HttpClientUtils.httpclient;
			}

			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				LOGGER.error("HttpStatus ERROR" + "Method failed: " + response.getStatusLine());
				return "";
			} else {
				entity = response.getEntity();
				if (null != entity) {
					//long len = entity.getContentLength();
//					String asd = EntityUtils.toString(entity);
					byte[] bytes = EntityUtils.toByteArray(entity);
					result = new String(bytes, "UTF-8");
				} else {
					LOGGER.error("httpPost URL [" + url + "],httpEntity is null.");
				}
				LOGGER.info("result:" + result);
				return result;
			}
		} catch (Exception e) {
			LOGGER.error("httpPost URL [" + url + "] error, ", e);
			return "";
		} finally {
			httpPost.releaseConnection();
			if (url.startsWith("https") && httpclient != null && httpclient instanceof CloseableHttpClient) {
				((CloseableHttpClient) httpclient).close();
			}
			if (response != null) {
				response.close();
			}
			LOGGER.info("RESULT:  [" + result + "]");
			LOGGER.info("httpPost URL [" + url + "] end ");
		}
	}

	private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}

			});
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (GeneralSecurityException e) {
			throw e;
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * HttpClientUtils t = new HttpClientUtils(); String url =
		 * "https://api.baidu.com/sem/common/HolmesLoginService"; String key =
		 * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHn/hfvTLRXViBXTmBhNYEIJeGGGDkmrYBxCRelriLEYEcrwWrzp0au9nEISpjMlXeEW4+T82bCM22+JUXZpIga5qdBrPkjU08Ktf5n7Nsd7n9ZeI0YoAKCub3ulVExcxGeS3RVxFai9ozERlavpoTOdUzEH6YWHP4reFfpMpLzwIDAQAB"
		 * ; String token = "5d8c807107443f95a4476d66b6309ad8"; String uuid =
		 * "abc86c00eb0144e58e31fa0befdfd6c7"; Map<String, Object> m = new
		 * HashMap<String, Object>(); m.put("username", "ziwow"); m.put("token",
		 * token); m.put("functionName", "preLogin"); m.put("uuid", uuid);
		 * Map<String, Object> mm = new HashMap<String, Object>();
		 * mm.put("osVersion", "windows"); mm.put("deviceType", "pc");
		 * mm.put("clientVersion", "1.0"); m.put("request", mm); String json =
		 * JSON.toJSONString(m); LOGGER.info("json:" + json); byte[] jj =
		 * MessageGZIP.compressToByte(json);// gzip压缩 int length = jj.length;
		 * String bodyParam = RSAHelper.encrypt(key, jj).toString();//rsa加密
		 * 
		 * Map<String, Object> h = new HashMap<String, Object>(); h.put("UUID",
		 * uuid); h.put("account-type", "1"); t.httpPost(url, bodyParam, h);
		 */

		CloseableHttpClient httpClient = HttpClientUtils.httpclient;
		// 设置代理服务器地址和端口
		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
		StringBuffer url = new StringBuffer();
		url.append("http://analytic.99baby.cn/zwtj/index.php?module=API&method=Actions.getPageTitles");
		String pageName = URLEncoder.encode("muyingtong.99baby.cn", "utf-8");
		// url.append("&pageName=muyingtong.99baby.cn/辽阳威威孕婴连锁-积分兑换");
		url.append("&filter_pattern=" + pageName);
		url.append("&idSite=1&period=day&date=2014-12-04&format=JSON&token_auth=738c60e5723e2bdd3638395926c663af");
		HttpGet httpget = new HttpGet(url.toString());
		// 这里设置字符编码，避免乱码
		httpget.setHeader("Content-Type", "text/html;charset=UTF-8");
		CloseableHttpResponse httpReponse = httpClient.execute(httpget);
		// 打印服务器返回的状态
		System.out.println(httpReponse.getStatusLine());
		// 获取返回的html页面
		HttpEntity entity = httpReponse.getEntity();
		// 打印返回的信息
		// InputStream instreams = entity.getContent();
		// String str = convertStreamToString(instreams);
		String result = new String(EntityUtils.toString(entity).getBytes("ISO_8859_1"), "UTF-8");
		// System.out.println(EntityUtils.toString(entity));
		System.out.println(result);
		// 释放连接
		httpReponse.close();

	}

	public static String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size = 0;

		try {
			while ((size = is.read(bytes)) > 0) {
				String str = new String(bytes, 0, size, "UTF-8");
				sb1.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb1.toString();
	}
	
	/**
	 * 
	 * postJson:(发送json体数据的post请求)
	 *
	 * @author john.chen
	 * @param url
	 * @param json
	 * @return
	 */
	public static String postJson(String url, String json) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		try {
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(600000).setConnectTimeout(600000).build();
			post.setConfig(requestConfig);
			post.setHeader("ContentType", "application/json;charset=UTF-8");
			HttpEntity entity = new StringEntity(json,
					ContentType.APPLICATION_JSON);
			post.setEntity(entity);
			CloseableHttpResponse response = client.execute(post);
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				if (HttpStatus.SC_OK == statusCode
						|| HttpStatus.SC_CREATED == statusCode) {
					String result = EntityUtils.toString(response.getEntity());
					return result;
				}
			} catch (IOException e) {
				throw e;
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String httpGet(String url) throws IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		try {
		    HttpGet get = new HttpGet(url);
		   // get.addHeader( "User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");   
            String charset = "UTF-8";   
		    CloseableHttpResponse res = client.execute(get);
		    try {
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			    HttpEntity entity = res.getEntity();
			    
			    String returnStr = EntityUtils.toString(entity,charset);// 返回json格式
			    return returnStr;
			}
		    } catch (Exception e) {
			throw new RuntimeException(e);
		    } finally {
			get.releaseConnection();
			res.close();
		    }
		} catch (ClientProtocolException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
			client.close();
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
		return "";
	}
	
	public static String httpPostBytesRequest(String url, byte[] data)
			throws ConnectTimeoutException, SocketTimeoutException, IOException {
		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		try {
			httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(30000).setConnectTimeout(60000).build();// 设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			httpPost.setHeader("ContentType", "charset=UTF-8");
			HttpEntity reqEntity = new ByteArrayEntity(data);
			httpPost.setEntity(reqEntity);
			if (url.startsWith("https")) {
				httpclient = createSSLInsecureClient();// 执行 Https 请求.
			} else {
				httpclient = HttpClients.createDefault();
			}
			response = httpclient.execute(httpPost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "UTF-8");
			return jsonStr;
		} catch (Exception e) {
			LOGGER.error("httpPost URL [" + url + "] error, ", e);
			return "";
		} finally {
			httpPost.releaseConnection();
			httpPost = null;
			if (httpclient != null) {
				httpclient.close();
				httpclient = null;
			}
			if (response != null) {
				response.close();
				response = null;
			}
		}
	}
}