<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>待处理工单</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/workOrderOperation/unfinishedWoOrderList.css${f_ver}">
    </head>
    <body>
        <input type="hidden" id="userIdInput" value="${userId}">
        <!-- <input type="hidden" id="userIdInput" value="NzI3Njg="> -->
        <input type="hidden" id="conditionInput" value="today">
        <div class="main_layer clearfix">
            <div class="weui-tab">
                <div class="weui-navbar">
                    <a class="weui-navbar__item weui-bar__item--on" href="#today" data-flag="today">
                        今日工单(<span class="todayOrders">0</span>)
                    </a>
                    <a class="weui-navbar__item" href="#tomorrow" data-flag="tomorrow">
                        明日工单(<span class="tormorrowOrders">0</span>)
                    </a>
                    <a class="weui-navbar__item" href="#all" data-flag="all">
                        所有工单(<span class="allOrders">0</span>)
                    </a>
                </div>
                <div class="weui-tab__bd">
                    <div id="today" class="weui-tab__bd-item weui-tab__bd-item--active">
                        <ul>
                           
                        </ul>
                    </div>
                    <div id="tomorrow" class="weui-tab__bd-item">
                        <ul>
                           
                        </ul>
                    </div>
                    <div id="all" class="weui-tab__bd-item">
                        <ul>
                           
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <script id="liTemplate" type="text/html">
            {{each wechatOrdersVoList as v i}}
            <li>
                <div class="orderHeader qy-blue">
                    <p>
                        {{v.orderTypeName}}
                        <span>预约单号&nbsp;:&nbsp;</span>
                        <span>{{v.ordersCode}} </span>
                        <span class="pull-right">{{v.orderStatus}}</span>
                    </p>
                </div>
                {{each v.products as product index}}
                <div class="pdtInfo clearfix" data-orderscode="{{v.ordersCode}}" onclick="gotoDetail(this)">
                    <div class="pdtImg pull-left" style="background-image: url('{{product.productImage}}')"></div>
                    <div class="pdtText pull-left">
                        <p>{{product.productName}}</p>
                        <p>产品条码：<span>{{product.productBarCode || ""}} </span></p>
                        <p>产品型号：<span> {{product.modelName}}</span></p>
                    </div>
                </div>
                <div class="sepratorLine">
                    <i></i>
                </div>
                {{/each}}
                <div class="workerInfo clearfix" data-orderscode="{{v.ordersCode}}" >
                    <div class="pdtImg pull-left" style="background-image: url('{{v.imageUrl}}')"></div>
                    <div class="pdtText pull-left" onclick="gotoNav(this)" data-useraddress="{{v.userAddress}}">
                        <p><img src="${f_ctxpath}/resources/src/images/icons/personInfo.png">{{v.userName}},{{v.userMobile}},{{v.contactsTelephone}}</p>
                        <p><img src="${f_ctxpath}/resources/src/images/icons/timer.png">{{v.orderTime}}</p>
                        <p><img src="${f_ctxpath}/resources/src/images/icons/location.png">{{v.userAddress}}<i class="gotoNav"></i></p>
                        
                    </div>
                </div>
                <div class="operation">
                    <div class="btnBox pull-right">
                        {{if v.btnStatus.canRefuse === 1}}
                            <button onclick="refuseOrder(this)" data-orderscode="{{v.ordersCode}}" data-ordersid="{{v.id}}" data-ordertype="{{v.orderType}}" data-contacts="{{v.userName}}" data-flag="{{flag}}">拒绝工单</button>
                        {{else}}
                            <button class="disabled">拒绝工单</button>
                        {{/if}}
                        {{if v.btnStatus.canChangeTime === 1}}
                            <button data-orderscode="{{v.ordersCode}}" onclick="changeOrder(this)" data-flag="{{flag}}">更改预约</button>
                        {{else}}
                            <button class="disabled">更改预约</button>
                        {{/if}}

                        <%--{{if v.btnStatus.canComplete === 1}}--%>
                            <%--<button data-orderscode="{{v.ordersCode}}" data-ordertype="{{v.orderType}}" onclick="completeOrder(this)">完工提交</button>--%>
                        <%--{{else}}--%>
                            <%--<button class="disabled">完工提交</button>--%>
                        <%--{{/if}}--%>
                        <button data-confirmstatus="{{(v.confirmStatus==null||v.confirmStatus == 0)?1:v.confirmStatus}}" data-orderscode="{{v.ordersCode}}" data-ordersid="{{v.id}}" data-ordertype="{{v.orderType}}" data-contacts="{{v.userName}}" onclick="cofirmOrder(this)">{{v.confirmContent}}</button>
                    </div>
                </div>
            </li>
            {{/each}}
        </script>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/arttemplate/arttemplate.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/workOrderOperation/unfinishedWorkOrders.js${f_ver}"></script>
    </body>
</html>