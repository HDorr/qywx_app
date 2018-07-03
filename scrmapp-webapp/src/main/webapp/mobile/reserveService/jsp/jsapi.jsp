<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>预约更换滤芯</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <script type="text/javascript">
        var orderId = "${orderId}";
        var ids = "${ids}"
        console.log("订单号：" + orderId + "商品IDS：" + ids);
        var idArr = ids.split("_");
        function onBridgeReady(){
            WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":"${appid}",     //公众号名称，由商户传入
                    "timeStamp":"${timeStamp}",         //时间戳，自1970年以来的秒数
                    "nonceStr":"${nonceStr}", //随机串
                    "package":"${packageValue}",
                    "signType":"MD5",         //微信签名方式：
                    "paySign":"${sign}" //微信签名
                },
                function(res){
                    // 使用以下方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                    if(res.err_msg == "get_brand_wcpay_request:ok") {
                        var products = JSON.parse(localStorage.getItem('reserve_products') || "[]")

                        for (var i=0; i<products.length; i++) {
                            var p = products[i];
                            console.log("商品ID: " + p.id);
                            for (var y=0; y<idArr.length; y++) {
                                if (p.id == idArr[y]) {
                                    console.log(p.id+":"+p.serviceStatusStr);
                                    // p.serviceStatus=0;
                                    // p.serviceStatusStr = "已购买滤芯和服务";
                                    //alert(p.serviceStatus);
                                }
                            }
                            /*if (p.serviceStatus =="已购买滤芯未购买服务") {
                                p.serviceStatus = "已购买滤芯和服务";
                                alert(p.serviceStatus);
                            }*/
                        }
                        alert("支付成功");
                        localStorage.setItem('reserve_products',JSON.stringify(products));
                      localStorage.setItem('isPaidSuccess',"true");
                      <%--alert("跳转："+"${pageContext.request.contextPath}/wx/order/query?orderId="+orderId)--%>
                      //此处可以修改商品滤芯服务费状态---------------------------------
                        window.location.href = "${pageContext.request.contextPath}/wx/order/query?orderId="+orderId;
                    }else if(res.err_msg == "get_brand_wcpay_request:fail"){
                        alert('支付失败');
                        localStorage.setItem('isPaidSuccess',"false");
                        //alert(JSON.stringify(res));
                        window.location.href = "${pageContext.request.contextPath}/wx/pay/fail"
                    }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                        alert('支付取消');
                        localStorage.setItem('isPaidSuccess',"false");
                        window.location.href = "${pageContext.request.contextPath}/wx/pay/fail"
                    }else{
                        alert(res.err_msg);
                    }
                }
            );
        }
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        }else{
            onBridgeReady();
        }

    </script>
    <title>订单-支付</title>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        onBridgeReady();
    });
</script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
<script src="${f_ctxpath}/resources/src/js/reserveService/reserveService_updateFilter.js${f_ver}"></script>
</body>
</html>