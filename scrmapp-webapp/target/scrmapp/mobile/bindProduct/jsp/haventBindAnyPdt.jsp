<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>扫码绑定产品</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/bindProduct/noQrCodeBindPdt.css${f_ver}">
    </head>
    <body>
        <div class="main_layer">
            <div class="scanArea clearfix">
                <label class="pull-left">
                    产品型号
                    <input class="qy-input-360" type="text" readonly>
                    <a href="javascript:;">如何查找产品条码？</a>
                </label>
                <div class="autoChose qy-btn-180 pull-right">
                    自动选择
                </div>
            </div>
            <button class="qy-btn-all nextStep">下一步</button>
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    </body>
</html>