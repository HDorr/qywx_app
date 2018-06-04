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
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/bindProduct/scanFail.css${f_ver}">
    </head>
    <body>
        <div class="main_layer clearfix">
            <div class="imgBox" style="background-image: url('${f_ctxpath}/resources/images/bindProduct/scanFail.png${f_ver}')"></div>
            <p>抱歉！没有找到该产品条码</p>
            <button class="qy-btn-400" id="bindProductBtn">返回重新绑定</button>
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    </body>
</html>