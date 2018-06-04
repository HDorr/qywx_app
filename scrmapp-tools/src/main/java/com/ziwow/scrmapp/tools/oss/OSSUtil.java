package com.ziwow.scrmapp.tools.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;

/**
 * 阿里云OSS文件上传工具类
 * 
 * @包名 com.ziwow.scrmapp.tools.oss
 * @文件名 OSSUploadUtil.java
 * @作者 john.chen
 * @创建日期 2017-3-6
 * @版本 V 1.0
 */
public class OSSUtil {
	private static OSSConfig config = null;
	/**
	 * 
	 * @MethodName: uploadFile
	 * @Description: OSS单文件上传
	 * @param file 
	 * @return String 文件地址
	 */
	public static String uploadFile(File file) {
		String fileName = file.getName();
		String fileExten = FilenameUtils.getExtension(fileName);
		String fileOssName = getOssFileName(fileExten);
		try {
			InputStream input = new FileInputStream(file);
			return putObject(input, fileOssName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
		String fileOssName = getOssFileName(fileType);
		return putObject(input, fileOssName);
	}
	
	/**
	 * 
	 * @MethodName: updateFile
	 * @Description: 更新文件:只更新内容，不更新文件名和文件地址。
	 *               (因为地址没变，可能存在浏览器原数据缓存，不能及时加载新数据，例如图片更新，请注意)
	 * @param file
	 * @param fileType
	 * @param oldUrl
	 * @return String
	 */
	public static String updateFile(File file, String oldUrl) {
		String fileName = getFileName(oldUrl);
		if (fileName == null)
			return null;
		try {
			InputStream input = new FileInputStream(file);
			return putObject(input, fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @MethodName: updateFile
	 * @Description: 更新文件:只更新内容，不更新文件名和文件地址。
	 *               (因为地址没变，可能存在浏览器原数据缓存，不能及时加载新数据，例如图片更新，请注意)
	 * @param input
	 * @param oldUrl
	 * @return String
	 */
	public static String updateFile(InputStream input, String oldUrl) {
		String fileName = getFileName(oldUrl);
		if (fileName == null)
			return null;
		return putObject(input, fileName);
	}
	
	/**
	 * 
	 * @MethodName: replaceFile
	 * @Description: 替换文件:删除原文件并上传新文件，文件名和地址同时替换 解决原数据缓存问题，只要更新了地址，就能重新加载数据)
	 * @param file
	 * @param fileType
	 *            文件后缀
	 * @param oldUrl
	 *            需要删除的文件地址
	 * @return String 文件地址
	 */
	public static String replaceFile(File file, String oldUrl) {
		boolean flag = deleteFile(oldUrl); // 先删除原文件
		if (!flag) {
			// 更改文件的过期时间，让他到期自动删除。
		}
		return uploadFile(file);
	}

	/**
	 * 
	 * @MethodName: deleteFile
	 * @Description: 单文件删除
	 * @param fileUrl 需要删除的文件ur（注意：路径只到.jpg为止）
	 * @return boolean 是否删除成功
	 */
	public static boolean deleteFile(String fileUrl) {
		config = config == null ? new OSSConfig() : config;
		String bucketName = getBucketName(fileUrl); // 根据url获取bucketName
		String fileName = getFileName(fileUrl); 	  // 根据url获取fileName
		if (bucketName == null || fileName == null)
			return false;
		OSSClient ossClient = null;
		try {
			ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
			GenericRequest request = new DeleteObjectsRequest(bucketName).withKey(fileName);
			ossClient.deleteObject(request);
		} catch (Exception oe) {
			oe.printStackTrace();
			return false;
		} finally {
			ossClient.shutdown();
		}
		return true;
	}

	/**
	 * 
	 * @MethodName: batchDeleteFiles
	 * @Description: 批量文件删除(较快)：适用于相同endPoint和BucketName
	 * @param fileUrls
	 *            需要删除的文件url集合
	 * @return int 成功删除的个数
	 */
	public static int deleteFile(List<String> fileUrls) {
		int deleteCount = 0; // 成功删除的个数
		String bucketName = getBucketName(fileUrls.get(0)); // 根据url获取bucketName
		List<String> fileNames = getFileName(fileUrls); // 根据url获取fileName
		if (bucketName == null || fileNames.size() <= 0)
			return 0;
		OSSClient ossClient = null;
		try {
			ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
			DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(fileNames);
			DeleteObjectsResult result = ossClient.deleteObjects(request);
			deleteCount = result.getDeletedObjects().size();
		} catch (OSSException oe) {
			oe.printStackTrace();
			throw new RuntimeException("OSS服务异常:", oe);
		} catch (ClientException ce) {
			ce.printStackTrace();
			throw new RuntimeException("OSS客户端异常:", ce);
		} finally {
			ossClient.shutdown();
		}
		return deleteCount;

	}

	/**
	 * 
	 * @MethodName: batchDeleteFiles
	 * @Description: 批量文件删除(较慢)：适用于不同endPoint和BucketName
	 * @param fileUrls
	 *            需要删除的文件url集合
	 * @return int 成功删除的个数
	 */
	public static int deleteFiles(List<String> fileUrls) {
		int count = 0;
		for (String url : fileUrls) {
			if (deleteFile(url)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 
	 * @MethodName: putObject
	 * @Description: 上传文件
	 * @param file
	 * @param ossFileName
	 * @return String
	 */
	private static String putObject(InputStream input, String ossFileName) {
		config = config == null ? new OSSConfig() : config;
		String ossUrl = null; // 默认null
		OSSClient ossClient = null;
		try {
			String fileType = FilenameUtils.getExtension(ossFileName);
			String endPoint = config.getEndpoint();
			String accessKey = config.getAccessKeyId();
			String accessKeySecret = config.getAccessKeySecret();
			String bucketName = config.getBucketName();
			ossClient = new OSSClient(endPoint, accessKey, accessKeySecret);
			ObjectMetadata meta = new ObjectMetadata(); // 创建上传Object的Metadata
			meta.setContentType(getContentType(fileType)); // 设置上传内容类型
			meta.setCacheControl("no-cache"); // 被下载时网页的缓存行为
			PutObjectRequest request = new PutObjectRequest(bucketName, ossFileName, input, meta); // 创建上传请求
			//上传文件 
			ossClient.putObject(request);
			// 设置URL过期时间为10年    3600l*1000*24*365*10
			Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
			// 生成URL
			URL url = ossClient.generatePresignedUrl(bucketName, ossFileName , expiration);
			if (url != null) {
				ossUrl = url.toString();
			}
		} catch (OSSException oe) {
			oe.printStackTrace();
		} catch (ClientException ce) {
			ce.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
		return ossUrl;
	}

	/**
	 * 
	 * @MethodName: fileName
	 * @Description: 获取文件类型
	 * @param FileType
	 * @return String
	 */
	private static String getContentType(String fileType) {
		if ("bmp".equalsIgnoreCase(fileType))
			return "image/bmp";
		if ("gif".equalsIgnoreCase(fileType))
			return "image/gif";
		if ("jpeg".equalsIgnoreCase(fileType) || "jpg".equalsIgnoreCase(fileType) || "png".equalsIgnoreCase(fileType))
			return "image/jpeg";
		if ("html".equalsIgnoreCase(fileType))
			return "text/html";
		if ("txt".equalsIgnoreCase(fileType))
			return "text/plain";
		if ("vsd".equalsIgnoreCase(fileType))
			return "application/vnd.visio";
		if ("ppt".equalsIgnoreCase(fileType) || "pptx".equalsIgnoreCase(fileType))
			return "application/vnd.ms-powerpoint";
		if ("doc".equalsIgnoreCase(fileType) || "docx".equalsIgnoreCase(fileType))
			return "application/msword";
		if ("xml".equalsIgnoreCase(fileType))
			return "text/xml";
		if ("mp4".equalsIgnoreCase(fileType))
			return "video/mp4";
		return "application/octet-stream";
	}

	/**
	 * 
	 * @MethodName: getBucketName
	 * @Description: 根据url获取bucketName
	 * @param fileUrl
	 *            文件url
	 * @return String bucketName
	 */
	private static String getBucketName(String fileUrl) {
		String http = "http://";
		String https = "https://";
		int httpIndex = fileUrl.indexOf(http);
		int httpsIndex = fileUrl.indexOf(https);
		int startIndex = 0;
		if (httpIndex == -1) {
			if (httpsIndex == -1) {
				return null;
			} else {
				startIndex = httpsIndex + https.length();
			}
		} else {
			startIndex = httpIndex + http.length();
		}
		int endIndex = fileUrl.indexOf(".oss-");
		return fileUrl.substring(startIndex, endIndex);
	}

	/**
	 * 
	 * @MethodName: getFileName
	 * @Description: 根据url获取fileName
	 * @param fileUrl
	 *            文件url
	 * @return String fileName
	 */
	private static String getFileName(String fileUrl) {
		String str = "aliyuncs.com/";
		int beginIndex = fileUrl.indexOf(str);
		if (beginIndex == -1)
			return null;
		return fileUrl.substring(beginIndex + str.length());
	}

	/**
	 * 
	 * @MethodName: getFileName
	 * @Description: 根据url获取fileNames集合
	 * @param fileUrl
	 *            文件url
	 * @return List<String> fileName集合
	 */
	private static List<String> getFileName(List<String> fileUrls) {
		List<String> names = new ArrayList<String>();
		for (String url : fileUrls) {
			names.add(getFileName(url));
		}
		return names;
	}
	
	/**
	 * 通过文件名获取上传到oss的文件路径
	 * @param fileExten
	 * @return
	 */
	private static String getOssFileName(String fileExten){
		config = config == null ? new OSSConfig() : config;
		String fileOssName = config.getPicLocation() + UUID.randomUUID().toString().toUpperCase().replace("-", "") + "." + fileExten; // 文件名，根据UUID来
		return fileOssName;
	}
}