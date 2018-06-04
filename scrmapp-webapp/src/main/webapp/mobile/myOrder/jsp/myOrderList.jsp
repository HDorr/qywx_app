<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的预约</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/myOrder/myOrderList.css${f_ver}">
</head>

<body>
    <div class="main_layer clearfix"></div>
    <button class="qy-btn-all" onclick="gotoIndex()">+ 一键服务</button>

    <script type="text/html" id="lists_template">
            <ul>
                {{each list as order index}}
                <li>
                    <div class="orderHeader">
                        <p>{{order.orderTypeName}}
                            <span>预约单号&nbsp;:&nbsp;</span>
                            <span>{{order.ordersCode}}</span>
                            <span class="pull-right">{{order.orderStatus}}</span>
                        </p>
                    </div>
                    {{each order.products as product i}}
                    <div class="pdtInfo clearfix">
                        <div class="pdtImg pull-left" style="background-image: url('{{product.productImage}}')"></div>
                        <div class="pdtText pull-left">
                            <p>{{product.productName}}</p>
                            <p>产品条码：
                                <span>{{product.productBarCode}}</span>
                            </p>
                            <p>产品型号：
                                <span>{{product.modelName}}</span>
                            </p>
                        </div>
                    </div>
                    <div class="sepratorLine">
                        <i></i>
                    </div>
                    {{/each}}
                    <div class="workerInfo clearfix">
                        <div class="pdtImg pull-left" style="background-image: url('{{order.imageUrl}}')"></div>
                        <div class="pdtText pull-left">
                            <p><img src="${f_ctxpath}/resources/src/images/icons/personInfo.png">{{order.userName}} , {{order.userMobile}},{{order.contactsTelephone}}</p>
                            <p><img src="${f_ctxpath}/resources/src/images/icons/timer.png">{{order.orderTime}}</p>
                            <p><img src="${f_ctxpath}/resources/src/images/icons/location.png">{{order.userAddress}}</p>
                        </div>
                    </div>
                    <div class="operation">
                        <div class="btnBox pull-right">
                           <!--  处理中的状态 status:1:处理中  2:已取消 3:重新处理中 4:已接单 5:服务完成 6:评价完成 -->
                           {{if order.status == 1 || order.status == 3 || order.status == 4}}
                           <button class="cancelOrder" onclick="cancelOrder(this)"  data-orderscode="{{order.ordersCode}}" data-username="{{order.userName}}">取消预约</button>
                           <button onclick="changeOrder(this)" data-orderscode="{{order.ordersCode}}">更改预约</button>
                           <button onclick="orderDetail(this)" data-orderscode="{{order.ordersCode}}">查看详情</button>
                           {{else if order.status == 2 || order.status == 6}}
                           <button onclick="orderDetail(this)" data-orderscode="{{order.ordersCode}}">查看详情</button>
                           {{else if order.status == 5}}
                           <button onclick="reviewOrder(this)" data-orderscode="{{order.ordersCode}}">评价</button>
                           <button onclick="orderDetail(this)" data-orderscode="{{order.ordersCode}}">查看详情</button>
                           {{/if}}
                        </div>
                    </div>
                </li>
                {{/each}}
            </ul>
    </script>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/thirdparty/arttemplate/arttemplate.js"></script>
    <script src="${f_ctxpath}/resources/src/js/myOrder/orderList.js${f_ver}"></script>
</body>

</html>