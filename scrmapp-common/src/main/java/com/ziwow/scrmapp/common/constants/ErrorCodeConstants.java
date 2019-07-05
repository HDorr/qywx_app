/**   
* @Title: ErrorCodeConstants.java
* @Package com.ziwow.scrmapppc.constants
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-3-3 下午3:22:46
* @version V1.0   
*/
package com.ziwow.scrmapp.common.constants;

/**错误码
 * @ClassName: ErrorCodeConstants
 * @author hogen
 * @date 2017-3-3 下午3:22:46
 * 
 */
public class ErrorCodeConstants {

	 public static final Integer CODE_0 = 0;//操作成功
	 public static final Integer CODE_1 = 1;//操作失败
	 public static final Integer CODE_3 = 3;//userId解密失败
	 //内部公告模块
	 public static final Integer CODE_40001 = 40001;//您还不是该企业的员工
	 public static final Integer CODE_40002 = 40002;//您还未关注该企业号
	 public static final Integer CODE_40003 = 40003;//您在该企业号已经被禁用
	 public static final Integer CODE_40004 = 40004;//该公告已经被删除
	 public static final Integer CODE_40005 = 40005;//您没有权限查看该公告
	 public static final Integer CODE_40006 = 40006;//该公告不存在
	 public static final Integer CODE_40007 = 40007;//该公告收藏成功
	 public static final Integer CODE_40008 = 40008;//该公告收藏失败
	 public static final Integer CODE_40009 = 40009;//该公告取消收藏成功
	 public static final Integer CODE_400010 = 400010;//该公告取消收藏失败

	//csm延保卡
	public static final String CODE_E0 = "E0";  //成功
	public static final String CODE_E092 = "E092"; //失败
	public static final String CODE_E094 = "E094"; //异常
	public static final String CODE_E09 = "E09"; //延保卡注册失败

}
