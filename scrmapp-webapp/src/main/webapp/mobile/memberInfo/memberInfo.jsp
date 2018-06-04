<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>个人资料</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css${f_ver}">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/userPersonalData/userPersonalData.css${f_ver}">
    </head>
    <body>
        <div class="userInfo_main_layer">
            <div id="centerArea" class="container">
                <div id="centerArea" class="container">
                    <div class="loadingArea">
                        <div class="weui-loadmore">
                            <i class="weui-loading"></i>
                            <span class="weui-loadmore__tips">正在加载</span>
                        </div>
                        <div class="weui-loadmore weui-loadmore_line weui-loadmore_dot">
                            <span class="weui-loadmore__tips"></span>
                        </div>
                    </div>
                </div>             
            </div>
            
        </div>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/director/director.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/iscroll/build/iscroll.js"></script>
        <script src="${f_ctxpath}/resources/src/js/jdLikeAddrChose.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/userPersonalData/userPersonalDataTemplate/build/template.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/userPersonalData/userPersonalData.js${f_ver}"></script>
        <script>

        shieldShare();

            function shieldShare() {
                function onBridgeReady() {
                    WeixinJSBridge.call('hideOptionMenu');
                }
            
                if (typeof WeixinJSBridge == "undefined") {
                    if (document.addEventListener) {
                        document.addEventListener('WeixinJSBridgeReady',            onBridgeReady, false);
                    } else if (document.attachEvent) {
                        document.attachEvent('WeixinJSBridgeReady',             onBridgeReady);
                        document.attachEvent('onWeixinJSBridgeReady',           onBridgeReady);
                    }
                } else {
                    onBridgeReady();
                }
            }
        </script>
        
    </body>
</html>