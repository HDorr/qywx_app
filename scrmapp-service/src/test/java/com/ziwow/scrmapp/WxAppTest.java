package com.ziwow.scrmapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.ziwow.scrmapp.core.util.AES;
import com.ziwow.scrmapp.core.util.JsonUtils;
import com.ziwow.scrmapp.service.WxAppService;
import com.ziwow.scrmapp.vo.SessionKeyVo;
import com.ziwow.scrmapp.vo.WxUserInfoVo;

public class WxAppTest extends BaseTest{

	@Autowired
	private WxAppService wxAppService;
	
	
	private final String APPID = "wx081b1c0c005f83dd";
	private final String SECRET  = "c3538041f0215ca84cb386572aad5705";
	private final String SESSION_KEY="71bfWub/w1omqQlApKWwJA\u003d\u003d";
	private final String SIGNATURE = "ad91090aff4afb46b6bd71e152396b8215a9c8b7";
	@Test
	public void testAccessToken(){
		String accessToken = wxAppService.getAccessToken(APPID, SECRET);
		System.out.println(accessToken);
	}
	
	@Test
	public void testGetSessionKey(){
		SessionKeyVo vo = wxAppService.getSessionKey(APPID, SECRET, "011lyh7C0LLKoe2dfU8C0q5h7C0lyh7G");
		
		System.out.println(new Gson().toJson(vo));
	}
	
	@Test
	public void testCheckUserInfo(){
		String rawData = "{\"nickName\":\"hogen\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"Sanming\",\"province\":\"Fujian\",\"country\":\"CN\",\"avatarUrl\":\"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLfTCKfofiblkraa51hGNxIKWgCgHIqPlXr81q8DWEJW5GyeAm7HYd5guwd3lMTfQ7jJibsjW1ibDFFg/0\"}";
		StringBuffer sb = new StringBuffer(rawData);
		sb.append(SESSION_KEY);
		byte[] encryData = DigestUtils.sha1(sb.toString());
		byte[] signatureData = SIGNATURE.getBytes();
		
		Boolean checkStatus = Arrays.equals(encryData, signatureData);
		System.out.println(checkStatus);
	}
	
	@Test
	public void testDecodeUserInfo(){
		String encryptedData="4NEnam6pHhvPs34K+NjxJv3umyj3v/y0zemmA65N7akFSpNDk6cNxonYQRzXx6EcQmvxPxla6yws8Gx60mltNnz7Ss+ddB9O/eaeIx39gjJayKqVoE7tChTuDQ/pLzjq5JCaxNodLJUp1wD5pEKHY+RF+niGTSmMikGmPFVkO67e95VstjG7QhVvbzuFgZaGKXrgAvgEIRTsDjigcpfsDSyc8oSFFhQ53mvGxrscoc6wUbYCrVNHb+U55YbooOcTnM/vfREAvI3RpU5bYUVBSFigIGPc9WYmTT/CtE85YADT5FFblGhoo7zbUi9Y6Ln/9t2KfGO9gAYAIk4eUSZlzLP4pCGLRvbLfmgPVzdBPtVIxtHM4TdHFxxhrZErMbTnv8wAdLU0beGC+De1xUE3a7fYb1P6uYjP9biNtA7/lERy7u8xZo/dxjSC8PuLPXrwpyIKKVTof9d6DLApusToow==";
		String iv = "p7j4fsihGMBNkGEO7MP9Kw==";
		try {
			AES aes = new AES();
			byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(SESSION_KEY), Base64.decodeBase64(iv));
			if(null != resultByte && resultByte.length > 0){
				String userInfo = new String(resultByte, "UTF-8");
				System.out.println(userInfo);
			}
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUserInfoToBean() throws IOException{
		String json = "{\"watermark\":{\"timestamp\":1484209209,\"appid\":\"wx081b1c0c005f83dd\"},\"nickName\":\"hogen\",\"avatarUrl\":\"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLfTCKfofiblkraa51hGNxIKWgCgHIqPlXr81q8DWEJW5GyeAm7HYd5guwd3lMTfQ7jJibsjW1ibDFFg/0\",\"province\":\"Fujian\",\"gender\":1,\"language\":\"zh_CN\",\"country\":\"CN\",\"city\":\"Sanming\",\"openId\":\"oovYJ0cBdFtMdx_4y4Xbr8fQ4ZlI\"}";
		WxUserInfoVo vo=JsonUtils.json2Object(json, WxUserInfoVo.class);
		System.out.println(new Gson().toJson(vo));
	
	}
	

}
