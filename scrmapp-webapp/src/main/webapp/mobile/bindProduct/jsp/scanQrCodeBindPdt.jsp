<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>绑定产品</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css${f_ver}">
         <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/bindProduct/scanQrCodeBindPdt.css${f_ver}">
    </head>
    <body>
        <div class="main_layer">
            <div class="scanArea clearfix">
                <p class="pull-left">
                    产品条码
                    <input class="qy-input-360" id="pdtCodeInput" style="margin-left:0.266667rem" type="tel" maxlength=20 oninput="if(/\D/.test(this.value)){this.value=this.value.replace(/\D/g,'')}" onpaste="if(/\D/.test(this.value)){this.value=this.value.replace(/\D/g,'')}"  onchange="if(/\D/.test(this.value)){this.value=this.value.replace(/\D/g,'')}">
                </p>
                <div class="scanQrCode pull-right" id="scanQrCode" style="background-image: url('${f_ctxpath}/resources/images/bindProduct/scanQrCode.png${f_ver}');">
                    <p>扫 码</p>
                </div>
            </div>
            <button class="qy-btn-all nextStep">下一步</button>
            <button class="haventFindQrCode">我没有找到产品条码</button>
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/bindProduct/scanQrCodeBindPdt.js${f_ver}"></script>

    </body>
</html>