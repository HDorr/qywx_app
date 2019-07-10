/**   
* @Title: WechatMediaServiceImpl.java
* @Package com.ziwow.scrmapp.wechat.service.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-4-25 上午10:01:31
* @version V1.0   
*/
package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.tools.oss.CallCenterOssUtil;
import com.ziwow.scrmapp.tools.oss.ChangeAudioFormat;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Resource;

import org.codehaus.xfire.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.utils.Attachment;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.tools.oss.OSSUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.service.WechatMediaService;
import com.ziwow.scrmapp.wechat.service.WeiXinService;

/**
 * @ClassName: WechatMediaServiceImpl
 * @Description: 上传下载多媒体文件
 * @author hogen
 * @date 2017-4-25 上午10:01:31
 * 
 */
@Service
public class WechatMediaServiceImpl implements WechatMediaService,InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(WechatMediaServiceImpl.class);
	
	@Resource
	private WeiXinService weiXinService;

  @Value("${callCenter.file.url}")
  private String callCenterFileUrl;

	@Value("${wechat.appid}")
	private String appid;
	@Value("${wechat.appSecret}")
	private String secret;

	private static final String TMP_DIR=System.getProperty("java.io.tmpdir")+"/";


  /***
   * 在bean装配完成之后将呼叫中心文件服务器的地址注入到工具类{link com.ziwow.scrmapp.tools.oss.CallCenterOssUtil}
   * @throws Exception
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    CallCenterOssUtil.setDownloadUrl(callCenterFileUrl);
    CallCenterOssUtil.setUploadUrl(callCenterFileUrl);
  }

	@Override
	public String downLoadMedia(String media_id) {
		String url = WeChatConstants.WECHAT_MADIA_DOWNLOAD.replace("ACCESS_TOKEN", weiXinService.getAccessToken(appid, secret))
				.replace("MEDIA_ID", media_id);
		
		try{
			Attachment res = HttpKit.download(url);
			logger.info("下载图片，返回结果[{}]",JSON.toJSON(res));
			if(res !=null && res.getError()==null){
				String resUrl = OSSUtil.uploadFile(res.getFileStream(), res.getSuffix());
				return resUrl.substring(0, resUrl.indexOf("?"));
			}else{
				return res.getError();
			}
			
		}catch (Exception e) {
			logger.error("下载图片失败,[{}]",e);
		}
		return null;
	}


	@Override
	public String downLoadMediaForCallCenter(String media_id) {
		String url = WeChatConstants.WECHAT_MADIA_DOWNLOAD.replace("ACCESS_TOKEN", weiXinService.getAccessToken(appid, secret))
				.replace("MEDIA_ID", media_id);
    String fileName="";
    BufferedInputStream in=null;
		try{
			Attachment res = HttpKit.download(url);
			logger.info("下载多媒体文件，返回结果[{}]",JSON.toJSON(res));
			if(res !=null && res.getError()==null){
        if ("amr".equals(res.getSuffix())){
          fileName=res.getFileName();
          res.setSuffix("mp3");
          String mp3Path = saveImageToDisk(res.getFileStream(), fileName,
              TMP_DIR);
          if (StringUtil.isBlank(mp3Path)){
            return null;
          }
          logger.info("保存mp3音频成功[{}]",mp3Path);
          in=new BufferedInputStream(new FileInputStream(mp3Path),8*1024);
          res.getFileStream().close();
          res.setFileStream(in);
        }

				String resUrl = CallCenterOssUtil.uploadFile(res.getFileStream(), res.getSuffix());
				return resUrl;
			}else{
				return res.getError();
			}

		}catch (Exception e) {
			logger.error("下载图片失败,[{}]",e);
		}finally {
      new File(TMP_DIR+fileName+".amr").delete();
      new File(TMP_DIR+fileName+".mp3").delete();
      try {
        if(in!=null){
          in.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
		return null;
	}


  public  String saveImageToDisk(InputStream inputStream, String picName, String picPath)
      throws Exception {
    String filePath = picPath+picName+".amr";
    byte[] data = new byte[10240];
    int len = 0;
    try(FileOutputStream fileOutputStream =new FileOutputStream(filePath)) {
      while ((len = inputStream.read(data)) != -1) {
        fileOutputStream.write(data, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      //生成对应mp3格式
      String mp3File=picPath+picName+".mp3";
      ChangeAudioFormat.changeToMp3(filePath, mp3File);
      return  mp3File;
    }
  }


}
