<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>工单详情</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/qiYeHao/orderDetail.css${f_ver}">
    </head>
    <body>
       <div class="main_layer">
           <div class="orderStatus"></div>
           <div class="orderBasicInfo"></div>
           <div class="productsInfo"></div>
       </div>

    <script type="text/html" id="bloque_template">
        {{if isMarginZero}}
        <div class="bloque noMarginBottom">
        {{else}}
        <div class="bloque">
        {{/if}}
            {{if orderStatus}}
                {{if orderStatus === '已完工'}}
                <span class="complateIcon statusIcon"></span>
                {{else}}
                <span class="cancelIcon statusIcon"></span>
                {{/if}}
            {{/if}}
            <div class="title">{{title}}
                <span class="pull-right">{{status}}</span>
            </div>
            {{each list as item index}}
            {{if item.images}}
            <div class="imgLine clearfix">
                {{each item.images.split(',') as image i}}
                <div class="image pull-left" style="background-image:url('{{image}}')" onClick="previewImage('{{image}}')"></div>
                {{/each}}
            </div>
            {{else if item.label}}
            <div class="line">
                <div class="label">{{item.label}}:</div>
                <div class="content">{{item.content}}</div>
            </div>
            {{/if}}
            {{/each}}  
        </div>  
    </script>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/arttemplate/arttemplate.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/src/js/qiYeHao/orderDetail.js${f_ver}"></script>
    </body>
</html>

