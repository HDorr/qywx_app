<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>预约详情</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/myOrder/orderDetail.css${f_ver}">
</head>

<body>
    <div class="main_layer clearfix" style="padding-top: 40%">
        <div class="weui-loadmore">
                <i class="weui-loading"></i>
                <span class="weui-loadmore__tips">正在加载</span>
            </div>
            <div class="weui-loadmore weui-loadmore_line">
                <span class="weui-loadmore__tips">暂无数据</span>
            </div>
            <div class="weui-loadmore weui-loadmore_line weui-loadmore_dot">
                <span class="weui-loadmore__tips"></span>
            </div>
    </div>
    <input type="hidden" name="orderTime" id="changeOrderInput" value="">
    <input type="hidden"  id="ordersCodeInput" value="${ordersCode}">
    <input type="hidden"  id="userIdInput" value="${userId}">
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js"></script>
    <script src="${f_ctxpath}/resources/src/js/myOrder/orderDetail.js${f_ver}"></script>
</body>
</html>