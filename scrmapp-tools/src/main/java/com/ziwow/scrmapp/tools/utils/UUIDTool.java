/**   
 * @Title: UUIDTool.java
 * @Package com.ziwow.qyhapp.weixin.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-12-5 上午9:51:29
 * @version V1.0   
 */
package com.ziwow.scrmapp.tools.utils;

import java.util.UUID;

/**
 * @ClassName: UUIDTool
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-5 上午9:51:29
 * 
 */
public class UUIDTool {

	public UUIDTool() {
	}

	/**
	 * 自动生成32位的UUid，对应数据库的主键id进行插入用。
	 * 
	 * @return
	 */
	public static String getUUID() {
		/*
		 * UUID uuid = UUID.randomUUID(); String str = uuid.toString(); //
		 * 去掉"-"符号 String temp = str.substring(0, 8) + str.substring(9, 13) +
		 * str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
		 * return temp;
		 */

		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args) {
		// String[] ss = getUUID(10);
		for (int i = 0; i < 10; i++) {
			System.out.println("ss[" + i + "]=====" + getUUID());
		}
	}
}
