package com.ziwow.scrmapp.wechat.service.impl;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.wechat.service.WechatAESService;

@Service
public class WechatAESServiceImpl implements WechatAESService {
	// 加密key与向量长度为16位
	public final int strLength = 16;
	private static final Logger LOG = LoggerFactory.getLogger(WechatAESServiceImpl.class);
	
	@Value("${cash.aes.key}")
	private String aeskey;
	
	@Value("${cash.aes.iv}")
	private String aesiv;
	
	/**
	 * 加密算法
	 * @param sSrc 加密内容
	 * @param sKey 加密key
	 * @param siv  加密向量
	 * @return 
	 * @throws Exception
	 */
    private  String EncryptAES(String sSrc, String sKey,String siv) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        // 判断加密向量16位
        if(siv==null){
        	System.out.print("加密向量iv为空null");
        	return null;
        }
        byte[] dataBytes = sSrc.getBytes("utf-8");
       
        SecretKeySpec keyspec = new SecretKeySpec(sKey.getBytes("utf-8"), "AES");
       
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"
        int plaintextLength = dataBytes.length;
        int blockSize = cipher.getBlockSize();
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }

        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        
        IvParameterSpec iv = new IvParameterSpec(siv.getBytes("utf-8"));//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, iv);
        byte[] encrypted = cipher.doFinal(plaintext);
        // 使用BASE64做转码功能，同时能起到2次加密的作用。
        return  Base64.encode(encrypted);
    }
	
	
    /**
     * 解密算法
     * @param sSrc
     * @param sKey
     * @param siv
     * @return
     * @throws Exception
     */
    public  String DecryptAES(String sSrc, String sKey,String siv) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            // 判断加密向量
            if(siv==null){
            	System.out.print("加密向量iv为空null");
            	return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            IvParameterSpec iv = new IvParameterSpec(siv
                    .getBytes("utf-8"));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8").trim();
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * 对字符串不满足指定长度的在右侧补空格
     * @param targetStr 
     * @return
     * @throws UnsupportedEncodingException
     */
    public  String PadRight(String targetStr) throws UnsupportedEncodingException{
        int curLength = targetStr.getBytes().length;
        if(targetStr!=null && curLength>strLength)
             targetStr = SubStringByte(targetStr);
         String newString = "";
        int cutLength = strLength-targetStr.getBytes().length;
        for(int i=0;i<cutLength;i++)
             newString +=" ";
        String preString = targetStr+newString;
        return preString;
    }
    
    public  String SubStringByte(String targetStr){
        while(targetStr.getBytes().length>strLength)
             targetStr = targetStr.substring(0,targetStr.length()-1);
        return targetStr;
    }
    
    /**
     * 从普通字符串转换为适用于URL的Base64编码字符串
     * @param normalString
     * @return
     */
    public  String Base64Replace(String normalString){
    	return normalString.replace('+', '*').replace('/', '-').replace('=', '.');
    }
    
    /**
     * 从普通字符串转换为适用于URL的Base64编码字符串
     * @param base64String
     * @return
     */
    public  String Base64Restore(String base64String)
    {
        return base64String.replace('.', '=').replace('*', '+').replace('-', '/');
    }
    
    /**
     * 得到解密内容
     * @param encryptStr
     * @return
     * @throws Exception 
     */
    public  String Decrypt(String key, String iv,String encryptStr) throws Exception{
    	String replacedStr = Base64Restore(encryptStr); //替换特殊字符
    	if(key==null||iv==null){
    		LOG.error("请配置好加密向量与key");
    		return null;
    	} else {
				key = PadRight(key);
				iv = PadRight(iv);
				return DecryptAES(replacedStr, key, iv);
    		
    	}
    }
    
    public String Encrypt(String key, String iv,String str) throws Exception{
    	if(key==null||iv==null){
    		LOG.error("请配置好加密向量与key");
    		return null;
    	} else {
    		key = PadRight(key);
    		iv = PadRight(iv);
    	}
    	String encryStr =  EncryptAES(str, key, iv);
    	if(encryStr!=null){
    		return Base64Replace(encryStr);
    	}
    	return null;
    	
    }


	/**
	 * TODO 简单描述该方法的实现功能.
	 * @see com.ziwow.service.scrm.service.aes.AESService#Decrypt(java.lang.String)
	 */
	@Override
	public String Decrypt(String str) throws UnsupportedEncodingException,
			Exception {
			return Decrypt(aeskey, aesiv, str);
	}


	/**
	 * TODO 简单描述该方法的实现功能.
	 * @see com.ziwow.service.scrm.service.aes.AESService#Encrypt(java.lang.String)
	 */
	@Override
	public String Encrypt(String str) throws UnsupportedEncodingException,
			Exception {
		return EncryptAES(str,aeskey, aesiv);
	}
}