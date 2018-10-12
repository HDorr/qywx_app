package com.ziwow.scrmapp.tools.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;
import net.sf.json.JSONObject;
import org.apache.axis.message.InputStreamBody;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 呼叫中心oss工具
 */
public class CallCenterOssUtil {
  private static final Logger logger = LoggerFactory.getLogger(CallCenterOssUtil.class);

  public static final String UPLOAD_URL="http://222.73.213.180:10002/put";
  public static final String DOWNLOAD_URL="http://222.73.213.180:10002/get";

  /**
   *
   * @MethodName: uploadFile
   * @Description: OSS单文件上传
   * @param input 输入流
   * @param fileType  文件名
   *
   * @return String 文件地址
   */
  public static String uploadFile(InputStream input, String fileType) {
    try {
      byte[] bytes = toByteArray(input);
      return upload(bytes);
    } catch (IOException e) {
      e.printStackTrace();
      logger.info("流转字节数组失败");
      return null;
    }
  }

  public static byte[] toByteArray(InputStream input) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
    }
    return output.toByteArray();
  }

  /**
   * 通过文件名获取上传到oss的文件路径
   * @param fileExten
   * @return
   */
  private static String getOssFileName(String fileExten){

    String fileOssName = UUID
        .randomUUID().toString().toUpperCase().replace("-", "") + "." + fileExten; // 文件名，根据UUID来
    return fileOssName;
  }

  /**
   *
   * @Description: 上传文件
   */
  public static String upload(byte[] input){
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;
    String url = UPLOAD_URL+"?filechannel=h5&filetype=jpg&tenantid=egoo";
    try {
      httpClient = HttpClients.createDefault();

      // 把一个普通参数和文件上传给下面这个地址 是一个servlet
      HttpPost httpPost = new HttpPost(url);

      ByteArrayBody image = new ByteArrayBody(input,ContentType.APPLICATION_JSON,"image.jpg");//传递图片的时候可以通过此处上传image.jpg随便给出即可
      MultipartEntityBuilder me = MultipartEntityBuilder.create();
      me.addPart("body", image);


      HttpEntity reqEntity = MultipartEntityBuilder.create()
          .addPart("body", image)
          .build();

      httpPost.setEntity(reqEntity);

      // 发起请求 并返回请求的响应
      logger.info("调用上传文件请求：[{}],[{}]",url,image);
      response = httpClient.execute(httpPost);
      logger.info("The response value of token:" + response.getFirstHeader("token"));


      // 获取响应对象
      HttpEntity resEntity = response.getEntity();

      String fileName = EntityUtils.toString(resEntity, "UTF-8");
      logger.info("调用上传文件请求返回结果：[{}]",fileName);
      // 销毁
      EntityUtils.consume(resEntity);
      if (StringUtil.isBlank(fileName)){
        return null;
      }
      return fileName;
    }catch (Exception e){
      e.printStackTrace();
      return null;
    }finally {
      try {
        if(response != null){
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        if(httpClient != null){
          httpClient.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
