/**   
* @Title: UniqueIDBuilderShortUrl.java
* @Package com.ziwow.marketing.core.util
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-9-9 上午10:23:14
* @version V1.0   
*/
package com.ziwow.scrmapp.tools.utils;

import java.util.HashSet;
import java.util.UUID;

/**
 * @ClassName: UniqueIDBuilderShortUrl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-9-9 上午10:23:14
 * 
 */
public class UniqueIDBuilderShortUrl {

	private UniqueIDBuilderShortUrl() {
	}




	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	public static String getUniqueIdValue() {
		/*int hashCodeV = generateShortUuid().hashCode();
		if (hashCodeV < 0) {
			hashCodeV = -hashCodeV;
		}*/
		return generateShortUuid();
	}
	
	public static void main(String[] args) {
		HashSet<String> abc = new HashSet<String>();
		for(int i=0; i<1000000; i++){
			String value = generateShortUuid();
			System.out.println(i+value);
			abc.add(value);
		}
		System.out.println(abc.size());
	}
}
