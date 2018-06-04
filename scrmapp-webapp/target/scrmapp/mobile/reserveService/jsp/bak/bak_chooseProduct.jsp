<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/23
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>选择产品</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reserveService/reserveService.css${f_ver}">
</head>
<body>
    <header class="conHeader">
        <p>您可以提前预约，享受专业便捷的服务支持！</p>
    </header>
    <div class="main_layer " style="padding-top: 0.666667rem">
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
        <section class="content">
            <ul class="productList">
                <!-- <li>
                    <div class="pdtInfo" >
                        <div class=""></div>
                        <div class="pdtImg pull-left" style="background-image: url('${f_ctxpath}/resources/images/testPdtImg.png')"></div>
                        <div class="pdtText pull-left">
                            <p>沁园牌反渗透净水器</p>
                            <p>产品条码：<span>557816083046302</span></p>
                            <p>产品型号：<span>QG-U-1004(电子商务专供)</span></p>
                        </div>
                    </div>
                </li> -->

            </ul>
        </section>
    </div>
    <footer class="conFooter">
        <button class="qy-btn-400" id="addNewPro">+新增产品</button>
    </footer>

<script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
<script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
<script src="${f_ctxpath}/resources/src/js/reserveService/chooseProduct.js${f_ver}"></script>

</body>
</html>