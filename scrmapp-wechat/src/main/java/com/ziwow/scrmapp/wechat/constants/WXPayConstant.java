package com.ziwow.scrmapp.wechat.constants;

public interface WXPayConstant {

	/*String APP_ID = "wx180143def83cffac";  //微信公众账号或开放平台APP的唯一标识
	//AppSecret是APPID对应的接口密码
	String APP_SECRET = "6c1ed2548cc7d0f3afd011bc62d39721";
	String MCH_ID = "1248223901";   //微信支付商户号
    //微信交易过程生成签名的密钥，商户支付密钥Key
	String KEY = "Xquark1234wohaoqingyuanshc223411";
	//工程服务器地址
    String BASE_URL = "http://dev.99baby.cn/scrmapp";
    // 与接口配置信息中的 Token 要一致
    String TOKEN = "wxjava0614";
    // 支付类型
	String TRADE_TYPE = "JSAPI";
	//签名加密方式
    String SIGN_TYPE = "MD5";
    //微信签名文件位置
 	String CERT_PATH = "/usr/local/pay/apiclient_cert.p12";
	//退款地址
	String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	String ORDER_PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder"; // 统一下单
	String ORDER_PAY_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery"; // 支付订单查询
	String ORDER_CLOSE = "https://api.mch.weixin.qq.com/pay/closeorder"; // 关闭订单
	String ORDER_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund"; // 申请退款
	String ORDER_REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery"; // 申请退款查询

	//查询产品服务路径
	String modelnameServiceQueryUrl="http://mhbs.51shop.mobi/v2/openapi/scrm/product/serviceFee/status";
	//购买产品服务后通知后台路径
	String modelnameServicePayNotifyUrl="http://mhbs.51shop.mobi/v2/openapi/scrm/order/serviceFee/payNotify";*/




	//服务费状态
    String filterServicePayed = "已购买滤芯和服务";
    String filterServiceUnpay = "已购买滤芯未购买服务";
    String filterServiceNone = "";

    //滤芯付款状态
    int payStatusZero = 0;   //未付款
    int payStatusOne = 1;   //已经付款
    int payStatusRefund = 5;  //退款

	String code = "wxpayqinquan";
}
