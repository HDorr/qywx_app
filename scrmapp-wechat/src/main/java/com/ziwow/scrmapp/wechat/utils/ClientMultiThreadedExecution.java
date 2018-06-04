package com.ziwow.scrmapp.wechat.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

public class ClientMultiThreadedExecution {
	
	private static  org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ClientMultiThreadedExecution.class);
	
	private static int connTimeout = 20000;
	private static int readTimeout = 60000;
	private static CloseableHttpClient httpclient = null;

	private static Map<String, String> headers = new HashMap<String, String>();
	static {
		headers.put("Content-Type", "data/gzencode and rsa public encrypt;charset=UTF-8");
	}
	
	
	 public static void  httpPost(String url, String jsonBody)
				throws ConnectTimeoutException, SocketTimeoutException, IOException {
			HttpPost httpPost = null;
			CloseableHttpResponse response = null;
			
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(2000);
			cm.setDefaultMaxPerRoute(128);
			httpclient = HttpClients.custom().setConnectionManager(cm).build();
			
			try {
				httpPost = new HttpPost(url);
				// 传入json数据
				if(jsonBody!=null&&jsonBody!=""){
					HttpEntity reqEntity = new StringEntity(jsonBody,"UTF-8");
					httpPost.setEntity(reqEntity);
				}
				// 设置参数
				Builder customReqConf = RequestConfig.custom();
				customReqConf.setConnectTimeout(connTimeout); // 连接超时
				customReqConf.setSocketTimeout(readTimeout);// 读数据超时
				httpPost.setConfig(customReqConf.build());
				PostThread threadPost = new PostThread(httpclient, httpPost);
				threadPost.start();
			} catch (Exception e) {
				LOGGER.error("httpPost URL [" + url + "] error, ", e);
			} finally {
				httpPost.releaseConnection();
				if (response != null) {
					response.close();
				}
			}
		}

    
    static class PostThread extends Thread {
    	 private final CloseableHttpClient httpClient;
         private final HttpContext context;
         private final HttpPost httppost;

         public PostThread(CloseableHttpClient httpClient, HttpPost httppost) {
             this.httpClient = httpClient;
             this.context = new BasicHttpContext();
             this.httppost = httppost;
         }

         @Override
         public void run() {
             try {
            	 HttpEntity postEntity =   httppost.getEntity();
            	 JSONObject obj = new JSONObject();
            	 if(postEntity!=null){
            		 obj = JSONObject.fromObject(postEntity);
            	 }
            	 
                 CloseableHttpResponse response = httpClient.execute(httppost, context);
                 try {
                     HttpEntity entity = response.getEntity();
                    int statusCode = response.getStatusLine().getStatusCode();
         			if (statusCode != HttpStatus.SC_OK) {
         				LOGGER.info("HttpClient Thread调用POST接口"+httppost.getURI()+"失败,状态码为:" + response.getStatusLine());
         			} else {
         				if (null != entity) {
         					byte[] bytes = EntityUtils.toByteArray(entity);
         					LOGGER.info("HttpClient Thread调用POST接口"+httppost.getURI()+",POST DATA:"+obj.toString()+"成功,返回结果为"+new String(bytes, "UTF-8"));
         				} else {
         					LOGGER.info("HttpClient Thread调用POST接口"+httppost.getURI()+",POST DATA:"+obj.toString()+"httpEntity is null");
         				}
         			} 
                 } finally {
                     response.close();
                 }
             } catch (Exception e) {
            	 LOGGER.info("HttpClient Thread调用POST接口"+httppost.getURI()+ " - error: " + e);
             }
         }

     }

    /**
     * A thread that performs a GET.
     */
    static class GetThread extends Thread {

        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;

        public GetThread(CloseableHttpClient httpClient, HttpGet httpget) {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httpget = httpget;
        }

        /**
         * Executes the GetMethod and prints some status information.
         */
        @Override
        public void run() {
            try {
                CloseableHttpResponse response = httpClient.execute(httpget, context);
                try {
                    HttpEntity entity = response.getEntity();
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode != HttpStatus.SC_OK) {
                    	LOGGER.info("HttpClient Thread调用GET接口"+httpget.getURI()+"失败,状态码为:" + response.getStatusLine());
         			} else {
         				if (null != entity) {
         					byte[] bytes = EntityUtils.toByteArray(entity);
         					LOGGER.info("HttpClient Thread调用GET接口"+httpget.getURI()+"成功,返回结果为"+new String(bytes, "UTF-8"));
         				} else {
         					LOGGER.info("HttpClient Thread调用GET接口"+httpget.getURI()+"httpEntity is null");
         				}
         			} 
                } finally {
                    response.close();
                }
            } catch (Exception e) {
            	LOGGER.info("HttpClient Thread调用GET接口"+httpget.getURI()+ " - error: " + e);
            }
        }

    }

}