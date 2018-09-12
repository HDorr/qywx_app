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
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/bindProduct/bindSuccess.css">
    </head>
    <body>
        <div class="main_layer clearfix">
            <div class="imgBox" style="background-image: url('${f_ctxpath}/resources/images/bindProduct/bindFail.png${f_ver}')"></div>
            <p><!-- <i style="background-image: url('${f_ctxpath}/resources/src/images/icons/successIcon.png')"></i> -->抱歉，无法识别您输入的产品型号！</p>
            <button class="qy-btn-400" onclick="goLocation()">
                返回重新绑定
            </button>
            <p class="tips">
                您可通过以下方式解决该问题：<br>
                1. 核对后重新输入;<br>
                2. 咨询在线客服;<br>
                3. 通过全国统一服务热线400-111-1222咨询人工客服。<br>
                (热线服务时间:周一至周日:8:00-20:00)
            </p>
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <!-- <script src="${f_ctxpath}/resources/src/thirdparty/jquery/dist/jquery.min.js"></script> -->
        <!-- <script src="${f_ctxpath}/resources/src/js/common.js"></script> -->
        <script>
            function goLocation(){
                window.history.back()
            }
        </script>
    </body>
</html>