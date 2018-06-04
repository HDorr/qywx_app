<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/23
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>预约服务-成功提示</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css${f_ver}">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reserveService/reserveService.css${f_ver}">
</head>
<body>
    <div class="main_layer ">
        <div class="promptImg">
            <span></span>
        </div>
        <p class="successTxt">恭喜你，预约提交成功！</p>
        <div class="reserveInfoBox">
            <div class="reserveInfo">
                <p>您的预约信息</p>
                <p>联系信息：<span></span></p>
                <p>服务地址：<span></span></p>
                <p>预约时间：<span></span></p>
            </div>
        </div>
    </div>
    <footer class="conFooter" style="background:#f4f4f4">
        <button class="qy-btn-400" id="backToReserveList" >返回我的预约列表</button>
    </footer>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/src/js/reserveService/reserveSccess.js${f_ver}"></script>
</body>
</html>