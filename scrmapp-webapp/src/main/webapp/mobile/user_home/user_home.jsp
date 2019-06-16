<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>个人中心</title>
    <meta name="viewport" content="width=750,maximum-scale=1.3,user-scalable=no">
    <%--<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">--%>
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.min.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/user_home/user_home.css${f_ver}">
</head>
<body>
<div class="main_layer">
    <div class="head_banner">
        <img class="bannerImg" src="${f_ctxpath}/resources/images/top_info.png${f_ver}">
        <div class="headerImg" style="background-image: url('${data.headimgurl}')"></div>
        <div class="nickName" >${data.nickName}</div>
        <%--<div class="bindPhoneText" >绑定手机完成注册</div>--%>
        <%--<p>${data.nickName}</p>--%>
    </div>

    <div class="menu_area weui-grids">
        <a href="${f_ctxpath}/scrmapp/consumer/user/memberInfo/index"  class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img src="${f_ctxpath}/resources/images/home_menu/user_info_blue.png${f_ver}" alt="">
            </div>
            <p class="weui-grid__label">
                个人资料
            </p>
            <p class="shadow"></p>
        </a>
        <a href="${f_ctxpath}/scrmapp/consumer/wechatuser/address/index" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img src="${f_ctxpath}/resources/images/home_menu/my_address_blue.png${f_ver}" alt="">
            </div>
            <p class="weui-grid__label">
                我的地址
            </p>
            <p class="shadow"></p>
        </a>
        <a href="${f_ctxpath}/scrmapp/consumer/user/filter/myProducts/jsp/myPdtList" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img src="${f_ctxpath}/resources/images/home_menu/my_product_blue.png${f_ver}" alt="">
            </div>
            <p class="weui-grid__label">
                我的产品
            </p>
            <p class="shadow"></p>
        </a>
        <a href="${f_ctxpath}/scrmapp/consumer/user/filter/myOrder/jsp/myOrderList" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img src="${f_ctxpath}/resources/images/home_menu/my_order_blue.png${f_ver}" alt="">
            </div>
            <p class="weui-grid__label">
                我的预约
            </p>
            <p class="shadow"></p>
        </a>
        <a href="${f_ctxpath}/scrmapp/consumer/user/filter/myOrder/jsp/myOrderList" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img src="${f_ctxpath}/resources/images/home_menu/ACPP.png${f_ver}" alt="">
            </div>
            <p class="weui-grid__label">
                延保服务
            </p>
            <p class="shadow"></p>
        </a>
       <!--  <a href="javascript:;" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img src="${f_ctxpath}/resources/images/home_menu/myCount.png" alt="">
            </div>
            <p class="weui-grid__label">
                我的优惠
            </p>
            <p class="shadow"></p>
        </a>
        <a href="javascript:;" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img src="${f_ctxpath}/resources/images/home_menu/advise.png" alt="">
            </div>
            <p class="weui-grid__label">
                吐槽&建议
            </p>
            <p class="shadow"></p>
        </a> -->
    </div>
</div>
<script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
<script>

  // (function (doc, win) {
  //   var docEl = doc.documentElement;
  //   resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize';
  //   recalc = function () {
  //     var clientWidth = docEl.clientWidth;
  //     if (!clientWidth) return;
  //     docEl.style.fontSize = 50 * (clientWidth / 750) + 'px';
  //   };
  //   if (!doc.addEventListener) return;
  //   win.addEventListener(resizeEvt, recalc, false);
  //   doc.addEventListener('DOMContentLoaded', recalc, false);
  // }(document,window));

    var gridEl = document.querySelectorAll(".weui-grid");
    for(var i = 0 ;i<gridEl.length;i++){
        gridEl[i].addEventListener("touchstart",function(e){
            this.classList.toggle("onTouch")
        })
        gridEl[i].addEventListener("touchend",function(e){
            this.classList.toggle("onTouch")
        })
    }

    shieldShare();

    function shieldShare() {
        function onBridgeReady() {
            WeixinJSBridge.call('hideOptionMenu');
        }
    
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady',onBridgeReady, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady',onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady',onBridgeReady);
            }
        } else {
            onBridgeReady();
        }
    }
  
</script>
</body>
</html>