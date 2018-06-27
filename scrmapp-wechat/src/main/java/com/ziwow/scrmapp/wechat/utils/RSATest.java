package com.ziwow.scrmapp.wechat.utils;

public class RSATest {
	public static final String publicKeyString = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJuACCBmfQod9WAs68Z0m37e"
			+ "HSoIhAbtjBamvEe6CpcHtaTUeKKbfpx2CKKKNSS7OTfb7j5h6+Fp8cOA3NwHolcCAwEAAQ==";
	public static final String privateKeyString = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAm4AIIGZ9"
			+ "Ch31YCzrxnSbft4dKgiEBu2MFqa8R7oKlwe1pNR4opt+nHYIooo1JLs5N9vuPmHr4Wnxw4D"
			+ "c3AeiVwIDAQABAkAsHj0wBCQB1NIjgVs7qSXc7uPTYysiVA9k9dWBfDU4+BO6UtjcefB/JN"
			+ "JNJM0pvdIfpjeeC4ROOo9lN1cwWtQhAiEAzbieOnnZKh3UOQbEK96A9jgzQOQWKAMI7bRrd"
			+ "s0iFzkCIQDBgTo/KOnNjfOLdTxhdsJ0mQ5SXvYfwk7f/Xpk7vR2DwIhALYc2PWrKDPAdD6H"
			+ "XuH29vMAjV7Ei1igVycWsItazPMBAiEAqdDo9wjGtlf/BcIq7TW1zXGMugkQYiq54aSnxvB"
			+ "VpU0CIAuKrEiXrvR6FNe2iGgS7sFvIz7Aqhfjuy+OqBp3jcNY";

	
	public static void main(String args[]) {
		String data = "gnMP6wDQKgxwWewqBE6msk8BvWjsSKrkUmIr7n3yYLI5JrvlmHbDyrfNxTw5UlZh4VRBmiiOc0o31qGBXv8rug==";
		String data2 = "Lh47DnhQvMSa2BvykMqMpewteI9y/HeD/2p0iQBOaI+oSA4XD5hCMOcqfybb/8TnvV6KsA8pqNHfvPzD4rAcZA==";
		String data3 = "IDCNGMmDPnC4pYgVr8CmCZ3SLLJfkDx2KUPS6RnxNe4hxGTwTOPN7fIsKlin9qGEaKJtsHoZzTHemdn5SsWviQ==";
		//testRsa();
		String s = RsaUtil.decryptData(data2);
		System.out.println(s);
	}

}
