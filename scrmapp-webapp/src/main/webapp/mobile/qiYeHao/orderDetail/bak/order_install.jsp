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
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/qiYeHao/order_detail.css${f_ver}">
    </head>
    <body>
        <div class="main_layer ">

        </div>


    <script id="orderDetailTemplate" type="text/html">
                <div class="order_state">
                    <div class="title">工单状态</div>
                    <div class="orderType">
                        <span class="pull-left">工单类型：</span>
                        <span class="pull-left con">{{orderTypeName}}</span>

                    </div>
                    <div class="overTime">
                        <span class="pull-left">完工时间：</span>
                        <span class="pull-left con">{{endTime}}</span>

                    </div>
                </div>
                <div class="order_info">
                    <div class="title">工单信息</div>
                    <div class="orderNum">
                        <span class="pull-left">受理单号：</span>
                        <span class="pull-left con">{{ordersCode}}</span>
                    </div>
                    <div class="productName">
                        <span class="pull-left">产品名称：</span>
                        <span class="pull-left con">{{productName}}</span>
                    </div>
                    <div class="productType">
                        <span class="pull-left">产品型号：</span>
                        <span class="pull-left con">{{modelName}}</span>
                    </div>
                    <div class="productCode">
                        <span class="pull-left">产品条码：</span>
                        <span class="pull-left con">{{productBarCode}}</span>
                    </div>
                    <div class="productCode">
                        <span class="pull-left">联系信息：</span>
                        <span class="pull-left con">{{userName}},{{userMobile}}</span>
                    </div>
                    <div class="productCode">
                        <span class="pull-left">服务地址：</span>
                        <span class="pull-left con">{{userAddress}}</span>
                    </div>
                    <div class="productCode">
                        <span class="pull-left">预约时间：</span>
                        <span class="pull-left con">{{orderTime}}</span>
                    </div>

                    <div class="productCode">
                        <span class="pull-left">安装描述：</span>
                        <span class="pull-left con">{{description}}</span>
                    </div>
                    {{if installPartRecord}}
                    <div class="productCode">
                        <span class="pull-left">使用配件：</span>
                        <span class="pull-left con">{{installPartRecord}}</span>
                    </div>
                    {{/if}}
                </div>
                <div class="order_info">
                    <div class="title">产品核实</div>
                    {{if productBarCode}}
                    <div class="orderNum">
                        <span class="pull-left">产品条码：</span>
                        <span class="pull-left con">{{productBarCode}}</span>
                    </div>
                    {{/if}}

                    {{if barCodeImage}}
                    <div class="orderNum">
                        <span class="pull-left">现场照片：</span>
                        <div class="imgBox">
                            <span class="pull-left img">
                                <img src="{{barCodeImage}}" alt="">
                            </span>
                        </div>
                    </div>
                    {{/if}}
                </div>
                {{if orderStatus=="已完工" }}
                <span class="complateIcon"></span>
                {{else }}
                <span class="cancelIcon"></span>
                {{/if}}
    </script>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/src/js/qiYeHao/order_detail.js${f_ver}"></script>
    </body>
</html>

