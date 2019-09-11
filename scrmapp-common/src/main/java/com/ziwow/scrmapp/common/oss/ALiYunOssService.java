package com.ziwow.scrmapp.common.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
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
      return "http://" + bucketName + "." + endpoint + "/" + fileName;
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
}
