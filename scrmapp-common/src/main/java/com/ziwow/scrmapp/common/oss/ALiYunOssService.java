package com.ziwow.scrmapp.common.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author: yyc
 * @date: 19-9-11 下午9:54
 */
@Component
public class ALiYunOssService {

  @Value("${oss.accessKeyId}")
  private String accessKeyId;

  @Value("${oss.accessKeySecret}")
  private String accessKeySecret;

  @Value("${oss.endpoint}")
  private String endpoint;

  @Value("${oss.bucketName}")
  private String bucketName;

  private static ObjectMetadata metadata;

  static {
    metadata = new ObjectMetadata();
    metadata.setContentType("multipart/form-data");
    metadata.setCacheControl("no-cache");
    metadata.setContentType(getContentType(null));
  }

  /**
   * 上传流
   *
   * @return 阿里云图片路径
   */
  public String uploadInputStream(InputStream stream, String fileName) {
    OSSClient ossClient = null;
    try {
      ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
      PutObjectRequest request = new PutObjectRequest(bucketName, fileName, stream, metadata);
      ossClient.putObject(request);
      return "https://" + bucketName + "." + endpoint + "/" + fileName;
    } catch (OSSException e) {
      throw new IllegalStateException("阿里云上传失败", e);
    } catch (Exception e) {
      throw new IllegalStateException("阿里云上传失败", e);
    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }
  }

  /**
   * 获取contentType
   *
   * @param fileName {@link String}
   * @return {@link String}
   */
  private static String getContentType(String fileName) {
    if (StringUtils.isEmpty(fileName)) {
      return "image/jpeg";
    }
    // 文件的后缀名
    String fileExtension = fileName.substring(fileName.lastIndexOf("."));
    if (".bmp".equalsIgnoreCase(fileExtension)) {
      return "image/bmp";
    }
    if (".gif".equalsIgnoreCase(fileExtension)) {
      return "image/gif";
    }
    if (".jpeg".equalsIgnoreCase(fileExtension)
        || ".jpg".equalsIgnoreCase(fileExtension)
        || ".png".equalsIgnoreCase(fileExtension)) {
      return "image/jpeg";
    }
    if (".html".equalsIgnoreCase(fileExtension)) {
      return "text/html";
    }
    if (".txt".equalsIgnoreCase(fileExtension)) {
      return "text/plain";
    }
    if (".vsd".equalsIgnoreCase(fileExtension)) {
      return "application/vnd.visio";
    }
    if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
      return "application/vnd.ms-powerpoint";
    }
    if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
      return "application/msword";
    }
    if (".xml".equalsIgnoreCase(fileExtension)) {
      return "text/xml";
    }
    // 默认返回类型
    return "image/jpeg";
  }
}
