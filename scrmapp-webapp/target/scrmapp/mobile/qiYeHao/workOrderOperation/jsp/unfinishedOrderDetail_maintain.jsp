<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>完工提交</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/workOrderOperation/unfinishedOrderDetail_maintain.css${f_ver}">
    </head>
    <body>
        <input type="hidden" id="userIdInput" value="${userId}">
        <input type="hidden" id="ordersCodeInput" value="${ordersCode}">
        <div class="main_layer clearfix" id="app">
            <div class="rootContainer" v-if="products.length !== 0">
                <p class="topTip">请确认信息后再提交!</p>
                <div class="bloque">
                    <v-title title="工单预约信息" icon="${f_ctxpath}/resources/src/images/icons/order.png"></v-title>
                    <basic-info :data="detail"></basic-info>
                </div>
                <div class="bloque orderInfo">
                    <v-title title="工单产品信息" icon="${f_ctxpath}/resources/src/images/icons/pdtInfo.png"></v-title>
                    <product v-for="(product,index) in products" :key="product.productId" :product="product" @comfirm="completeProductHandler" @cancel="cancelProductHandler">
                    </product>
                </div>
                <refuse-order v-show="isShowRefuse" @cancel="isShowRefuse = false" @comfirm="refuseOrderHandler"></refuse-order>
            </div>
        </div>

        <!-- header 模板 -->
        <script type="text/x-template" id="title_template"> 
            <header>
                <img class="icon" :src="icon">
                <span class="title">{{title}}</span>
            </header>
        </script>
        
        <!-- 基础信息模板 -->
        <script type="text/x-template" id="basicInfo_template">
            <div class="infoBox">
                <div class="line" v-for="(item,index) in neededData" :key="item.key">
                    <span>{{item.name}}：</span>
                    <span>{{data[item.key]}}</span>
                </div>
            </div>
        </script>
        
        <!-- 产品列表模板 -->
        <script type="text/x-template" id="product_template">
            <div class="infoBox">
                <div class="product">
                    <button v-if="product.status != 1" class="cannotOptBtn">{{product.status == 2 ? '已退单' :'已完工'}}</button>
                    <div class="line" >
                        <span>名称：</span>
                        <span>{{product.productName}}</span>
                    </div>
                    <div class="line">
                        <span>型号：</span>
                        <span>{{product.modelName}}</span>
                    </div>
                    <div class="line">
                        <span>条码：</span>
                        <span>{{product.status == 1 ? product.productBarCode : product.productBarCodeTwenty }}</span>
                    </div>
                    <div class="btnBox" v-if="product.status == 1">
                        <button  v-if="false" @click="cancelHandler" >退单</button>
                        <button @click="comfirmHandler">完工提交</button>
                    </div>
                </div>
            </div>
        </script>

        <!-- 拒绝工单弹框 -->
        <script type="text/x-template" id="refuse_order_template">
            <div class="refuseOrderAlert" @touchmove="stopScrollScreen">
                <div class="mainContainer">
                    <div class="mcmheader">
                        <i @click="cancel">X</i>
                    </div>
                    <div class="mcmbody">
                        <p class="mcmText">确认拒绝工单吗?</p> 
                        <p class="tipText">拒绝操作无法撤回</p>
                        <div class="textAreaBox">
                            <div style="border:1px solid #000">
                                <textarea placeholder="请输入原因,1-30个字(必填)" maxlength="30" v-model="reason"></textarea>
                            </div>
                        </div>
                        <div class="btnBox">
                            <button class="cancel" @click="cancel" >取消</button>
                            <button class="confirm" @click="comfirm">确认</button>
                        </div>
                    </div>
                </div>
            </div>
        </script>

        <script type="text/x-template" id="editProduct_template"></script>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/vue/vue.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/workOrderOperation/unfinishedOrderDetail_maintain.js${f_ver}"></script>
    </body>
</html>