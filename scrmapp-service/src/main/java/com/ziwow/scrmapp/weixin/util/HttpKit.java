/**
 * Project Name:api-service
 * File Name:HttpKitNew.java
 * Package Name:com.ziwow.service.scrm.weixin.util
 * Date:2015-1-15下午8:14:24
 * Copyright (c) 2015, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.weixin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

/**
 * ClassName: HttpKitNew <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2015-1-15 下午8:14:24 <br/>
 *
 * @author hogen
 * @version 
 * @since JDK 1.6
 */
public class HttpKit {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static  org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpKit.class);
	
	private static int connTimeout = 20000;
	private static int readTimeout = 60000;
	private static CloseableHttpClient httpclient = null;
	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(500);
		cm.setDefaultMaxPerRoute(128);
		httpclient = HttpClients.custom().setConnectionManager(cm).build();
	}

	private static Map<String, String> headers = new HashMap<String, String>();
	static {
		headers.put("Content-Type", "data/gzencode and rsa public encrypt;charset=UTF-8");
	}
    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException, ExecutionException, InterruptedException {
    	LOGGER.info("新的get方法请求："+url);
    	CloseableHttpClient client = HttpClients.createDefault(); 
    	try {
             if(params != null && !params.isEmpty()){
                 List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                 for(Map.Entry<String,String> entry : params.entrySet()){
                     String value = entry.getValue();
                     if(value != null){
                         pairs.add(new BasicNameValuePair(entry.getKey(),value));
                     }
                 }
                 url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "UTF-8"));
             }
             HttpGet httpGet = new HttpGet(url);
             CloseableHttpResponse response = client.execute(httpGet);
             
             try {
            	 int statusCode = response.getStatusLine().getStatusCode();
                 if (statusCode != 200) {
                     httpGet.abort();
                     throw new RuntimeException("HttpClient,error status code :" + statusCode);
                 }
                 HttpEntity entity = response.getEntity();
                 String result = null;
                 if (entity != null){
                     result = EntityUtils.toString(entity, "utf-8");
                 }
                 EntityUtils.consume(entity);
                 return result;
             } finally {
            	 response.close();
             }
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
        	 try {
 				client.close();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
         }
         return null;
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws UnsupportedEncodingException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, params, null);
    }
    
    
    public static String httpGet(String url){  
    	CloseableHttpClient  client = HttpClients.createDefault(); 
    	try {
    		HttpGet get = new HttpGet(url);  
    		CloseableHttpResponse  res = client.execute(get);  
	        try {  
	            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
	                HttpEntity entity = res.getEntity();  
	                String charset = EntityUtils.toString(entity);// 返回json格式 
	                return charset; 
	            }  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  finally {
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
        return null;  
    } 

    public static String bytesDataUpload(String url,byte[] data){
		LOGGER.info("新的httpPost URL [" + url + "] start ");
		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		try {
			httpPost = new HttpPost(url);
			// 传入字符数据
			HttpEntity reqEntity = new ByteArrayEntity(data);
			httpPost.setEntity(reqEntity);
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
				httpclient = HttpKit.httpclient;
			}

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
			try{
				if (url.startsWith("https") && httpclient != null && httpclient instanceof CloseableHttpClient) {
					((CloseableHttpClient) httpclient).close();
				}
				if (response != null) {
					response.close();
				}
			}catch (Exception e){
				LOGGER.error("httpPost URL [" + url + "] error, ", e);
				LOGGER.info("httpPost URL [" + url + "] 关闭出错");
			}finally{
				httpclient = null;
				response = null;
			}
			LOGGER.info("RESULT:  [" + result + "]");
			LOGGER.info("httpPost URL [" + url + "] end ");
		}
    }
    
    /**
	 * streamDataUpload:(上传流数据). <br/>
	 *
	 * @author Eric
	 * @param url
	 * @param imgStream
	 * @return
	 * @since JDK 1.6
	 */
	public static String streamDataUpload(String url, InputStream imgStream) {
		URL urlGet = null;
		HttpURLConnection conn = null;
		StringBuffer bufferRes = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL"; // 定义数据分隔线
			urlGet = new URL(url);
			conn = (HttpURLConnection) urlGet.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty(
					"user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			String fileName = "ZW" + Calendar.getInstance().getTime().getTime()
					+ ".jpg";
			out = new DataOutputStream(conn.getOutputStream());
			byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
					+ fileName + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] data = sb.toString().getBytes();
			out.write(data);

			DataInputStream fs = new DataInputStream(imgStream);
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = fs.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.write("\r\n".getBytes()); // 多个文件时，二个文件之间加入这个
			fs.close();
			out.write(end_data);
			out.flush();
			out.close();
			// 定义BufferedReader输入流来读取URL的响应
			in = conn.getInputStream();

			BufferedReader read = new BufferedReader(new InputStreamReader(in,
					DEFAULT_CHARSET));
			String valueString = null;
			bufferRes = new StringBuffer();
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}

			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				// 关闭连接
				conn.disconnect();
				conn = null;
			}
			if (out != null) {
				out = null;
			}
			if(in != null){
				in = null;
			}
		}
		return bufferRes.toString();
	}
    
    /**
     * 上传媒体文件
     *
     * @param url
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String url, File file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, ExecutionException, InterruptedException {
    	AsyncHttpClient http = new AsyncHttpClient();
    	AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
    	try {
            builder.setBodyEncoding(DEFAULT_CHARSET);
            String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL"; // 定义数据分隔线
            builder.setHeader("connection", "Keep-Alive");
            builder.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
            builder.setHeader("Charsert", "UTF-8");
            builder.setHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
            builder.setBody(new UploadEntityWriter(end_data, file));
            Future<Response> f = builder.execute();
            String body = f.get().getResponseBody(DEFAULT_CHARSET);
            return body;
    	} finally {
    		if(http!=null){
    			http.close();
    		}
    	}
    }
    
    /**
	 * 上传网络媒体文件
	 * 
	 * @param url
	 * @param params
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String uploadNetFile(String url, String filePath) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException,
			KeyManagementException {
		String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL"; // 定义数据分隔线
		StringBuffer bufferRes = null;
		URL urlGet = new URL(url);
		File file = new File(filePath);
		HttpURLConnection conn = (HttpURLConnection) urlGet.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty(
				"user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + BOUNDARY);
		try {
			OutputStream out = new DataOutputStream(conn.getOutputStream());
				try {
					byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
					StringBuilder sb = new StringBuilder();
					sb.append("--");
					sb.append(BOUNDARY);
					sb.append("\r\n");
					sb.append("Content-Disposition: form-data;name=\"media\";filename=\""+ file.getName() +"\"\r\n");
					sb.append("Content-Type:application/octet-stream\r\n\r\n");
					byte[] data = sb.toString().getBytes();
					out.write(data);
					LOGGER.info("文件地址："+filePath);
					//获得文件的网络地址
			        URL fileURL = new URL(filePath);  
			        //打开链接  
			        HttpURLConnection connDownload = (HttpURLConnection)fileURL.openConnection();  
			        //设置请求方式为"GET"  
			        connDownload.setRequestMethod("GET");  
			        //超时响应时间为5秒  
			        connDownload.setConnectTimeout(5 * 1000);  
			        //通过输入流获取图片数据  
			        InputStream inStream = connDownload.getInputStream();  
				
					DataInputStream fs = new DataInputStream(inStream);
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = fs.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					out.write("\r\n".getBytes()); // 多个文件时，二个文件之间加入这个
					fs.close();
					out.write(end_data);
					out.flush();
				} finally {
					out.close();
				}
	
			// 定义BufferedReader输入流来读取URL的响应
			InputStream in = conn.getInputStream();
			try {
				BufferedReader read = new BufferedReader(new InputStreamReader(in,
						DEFAULT_CHARSET));
				String valueString = null;
				bufferRes = new StringBuffer();
				while ((valueString = read.readLine()) != null) {
					bufferRes.append(valueString);
				}
			} finally {
				in.close();
			}
		} finally {
			if (conn != null) {
				// 关闭连接
				conn.disconnect();
			}
		}
		return bufferRes.toString();
	}

    /**
     * 下载资源
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Attachment download(String url) throws ExecutionException, InterruptedException, IOException {
        Attachment att = new Attachment();
        AsyncHttpClient http = new AsyncHttpClient();
        try {
	        AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
	        builder.setBodyEncoding(DEFAULT_CHARSET);
	        Future<Response> f = builder.execute();
	        if (f.get().getContentType().equalsIgnoreCase("text/plain")) {
	            att.setError(f.get().getResponseBody(DEFAULT_CHARSET));
	        } else {
	            BufferedInputStream bis = new BufferedInputStream(f.get().getResponseBodyAsStream());
	            String ds = f.get().getHeader("Content-disposition");
	            String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
	            String relName = fullName.substring(0, fullName.lastIndexOf("."));
	            String suffix = fullName.substring(relName.length() + 1);
	
	            att.setFullName(fullName);
	            att.setFileName(relName);
	            att.setSuffix(suffix);
	            att.setContentLength(f.get().getHeader("Content-Length"));
	            att.setContentType(f.get().getContentType());
	            att.setFileStream(bis);
	        }
        } finally {
        	if(!http.isClosed()){
        		http.close();
        	}
        }
		return att;
       
    }

    public static String post(String url, String s) throws IOException, ExecutionException, InterruptedException {
        return httpPost(url, s, null);
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
			httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
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
				httpclient = HttpKit.httpclient;
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
		LOGGER.info("新的httpPost URL [" + url + "] start ");
		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		try {
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
			if(StringUtils.isNotBlank(jsonBody)){
				// 传入json数据
				HttpEntity reqEntity = new StringEntity(jsonBody,"UTF-8");
				httpPost.setEntity(reqEntity);
			}
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
				httpclient = HttpKit.httpclient;
			}

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
    
}

