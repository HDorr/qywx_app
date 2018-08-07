<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>防伪查询</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/securityCheck/securityCheck.css${f_ver}">
</head>

<body>
    <div class="main_layer">
        <!-- <header class="qy-blue">请输入防伪码</header> -->
        <div class="inputArea">
            <div class="inputBox">
                <span class="searIcon"></span>
                <input class="searInput" type="text" id="code" placeholder="请输入防伪码" oninput="if(/\D/.test(this.value)){this.value=this.value.replace(/\D/g,'')}" onpaste="if(/\D/.test(this.value)){this.value=this.value.replace(/\D/g,'')}"  onchange="if(/\D/.test(this.value)){this.value=this.value.replace(/\D/g,'')}">
                <span class="scanIcon" id="scanQrCode"></span>
            </div>
            <p class="tipP">格式不符合要求,请重新输入</p>
        </div>
        <div class="checkArea">
            <button class="qy-btn-all" id="checkCode">立即查询</button>
        </div>
        <div class="tipText">
            <p><i></i>1.防伪码位置：净水设备滤芯防伪码，通常位于滤芯外包装盒顶部；饮水设备滤芯防伪码则位于芯体标签，净水桶产品防伪码请见桶壁标签。净水设备整机防伪码请见产品机身。</p>
            <p><i></i>2.防伪码为16位或者20位数字。</p>
            <p><i></i>3.如您未能查验成功，建议您联系销售商家，或拨打全国统一服务热线400 111 1222进行咨询，我们的服务时间是8:00-20:00。</p>
        </div>
    </div>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/src/js/securityCheck/securityCheck.js${f_ver}"></script>
</body>

</html>