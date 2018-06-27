package com.ziwow.scrmapp.wechat.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;

public class RsaUtil {
	public static final String publicKeyString = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJuACCBmfQod9WAs68Z0m37e"
			+ "HSoIhAbtjBamvEe6CpcHtaTUeKKbfpx2CKKKNSS7OTfb7j5h6+Fp8cOA3NwHolcCAwEAAQ==";
	public static final String privateKeyString = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAm4AIIGZ9"
			+ "Ch31YCzrxnSbft4dKgiEBu2MFqa8R7oKlwe1pNR4opt+nHYIooo1JLs5N9vuPmHr4Wnxw4D"
			+ "c3AeiVwIDAQABAkAsHj0wBCQB1NIjgVs7qSXc7uPTYysiVA9k9dWBfDU4+BO6UtjcefB/JN"
			+ "JNJM0pvdIfpjeeC4ROOo9lN1cwWtQhAiEAzbieOnnZKh3UOQbEK96A9jgzQOQWKAMI7bRrd"
			+ "s0iFzkCIQDBgTo/KOnNjfOLdTxhdsJ0mQ5SXvYfwk7f/Xpk7vR2DwIhALYc2PWrKDPAdD6H"
			+ "XuH29vMAjV7Ei1igVycWsItazPMBAiEAqdDo9wjGtlf/BcIq7TW1zXGMugkQYiq54aSnxvB"
			+ "VpU0CIAuKrEiXrvR6FNe2iGgS7sFvIz7Aqhfjuy+OqBp3jcNY";

	/**
	 * RSA加密
	 */
	public static String encryptData(String data) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString.getBytes()));
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA", "BC");
			PublicKey publicKey = kf.generatePublic(pubX509);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] dataToEncrypt = data.getBytes("utf-8");
			byte[] encryptedData = cipher.doFinal(dataToEncrypt);
			String encryptString = Base64.encodeBase64String(encryptedData);
			return encryptString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA解密
	 */
	public static String decryptData(String data) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString.getBytes()));
		try {
		    KeyFactory kf = KeyFactory.getInstance("RSA", "BC");
		    PrivateKey privateKey = kf.generatePrivate(priPKCS8);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] descryptData = Base64.decodeBase64(data);
			byte[] descryptedData = cipher.doFinal(descryptData);
			String srcData = new String(descryptedData, "utf-8");
			return srcData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
