package com.ziwow.scrmapp.common.constants;

/**
 * ClassName: Constant <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-7-23 上午9:56:25 <br/>
 *
 * @author
 * @since JDK 1.6
 */
public class Constant {
    //支付返回code
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int SUCCESS_NULL = 2;
    public static final String PAY_SUCCESS = "SUCCESS";
    public static final String PAY_FAIL = "FAIL";

    //error_code
    public static final Integer ERROR_CODE_0 = 0;//操作成功
    public static final Integer ERROR_CODE_40001 = 40001;//获取sessionKey失败

    // 业务日志分隔符
    public static final String LOG_SPLIT = "\001";

    //用于返回json
    public static final String OK = "OK";

    // 认证校验失败提示信息
    public static final String ILLEGAL_REQUEST = "非法请求,请认证后再重试!";
    public static final String AUTH_KEY = "ZIWOW";

    public static int CUSTOMER = 0;
    public static int ENGINEER = 1;
    public static int MARKETING = 2;

    public static int BIND_GIFT=3;//绑定有礼
    public static int REGISTER_GIFT=4;//注册有礼

    public static final int NORMAL = 1, CANCEL = 2, COMPLETE = 3;

    public static final int HIDDEN = 1;

    public static final int DEAL = 0, FINISH = 1;
}
