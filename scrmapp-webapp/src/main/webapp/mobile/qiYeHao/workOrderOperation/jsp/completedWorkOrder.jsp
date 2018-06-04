<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>已完成工单</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/workOrderOperation/completedWorkOrder.css${f_ver}">
    </head>
    <body>
        <input type="hidden" id="userIdInput" value="${userId}">
        <div class="main_layer clearfix">
            <ul>

            </ul>
        </div>
        
        <script id="cpodTemplate" type="text/html">
        {{each list as v i}}
            <li data-orderscode="{{v.ordersCode}}" data-ordertype="{{v.orderType}}" onclick="completeOrderDetail(this)">
                <div class="orderHeader qy-blue">
                    <p>
                        {{v.orderTypeName}}
                        <span>预约单号&nbsp;:&nbsp;</span>
                        <span>{{v.ordersCode}} </span>
                        <span class="pull-right">{{v.orderStatus}}</span>
                    </p>
                </div>
                {{each v.products as product index}}
                <div class="pdtInfo clearfix">
                    <div class="pdtImg pull-left" style="background-image: url('{{product.productImage}}')"></div>
                    <div class="pdtText pull-left">
                        <p>{{product.productName}}</p>
                        <p>产品条码：<span>{{product.productBarCode}} </span></p>
                        <p>产品型号：<span> {{product.modelName}}</span></p>
                    </div>
                </div>
                {{/each}}
                <div class="sepratorLine">
                    <i></i>
                </div>
                <div class="workerInfo clearfix">
                    <div class="pdtImg pull-left" style="background-image: url('{{v.imageUrl}}')"></div>
                    <div class="pdtText pull-left">
                        <p><img src="${f_ctxpath}/resources/src/images/icons/personInfo.png">{{v.userName}}</p>
                        <p><img src="${f_ctxpath}/resources/src/images/icons/timer.png">{{v.orderTime}}</p>
                        <p><img src="${f_ctxpath}/resources/src/images/icons/location.png">{{v.userAddress}}</p>
                    </div>
                </div>
                <div class="operation">
                    <div class="btnBox pull-right">
                        完工时间：{{v.endTime}}
                    </div>
                </div>
            </li>
        {{/each}}
        </script>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/workOrderOperation/completedWorkOrder.js${f_ver}"></script>
    </body>
</html>