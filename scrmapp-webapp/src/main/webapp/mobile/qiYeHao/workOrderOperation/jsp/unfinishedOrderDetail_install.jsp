<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/workOrderOperation/unfinishedOrderDetail_install.css${f_ver}">
    </head>
    <body>
        <input type="hidden" id="userIdInput" value="${userId}">
        <input type="hidden" id="ordersCodeInput" value="${ordersCode}">
        <div class="main_layer clearfix" id="app">
            <p class="topTip">请确认信息后再提交!</p>
            <div class="bloque">
                <v-title title="工单预约信息" icon="${f_ctxpath}/resources/src/images/icons/order.png"></v-title>
                <basic-info :data="detail" ></basic-info>      
            </div>
            
            <div class="bloque orderInfo">
                <v-title title="工单产品信息" icon="${f_ctxpath}/resources/src/images/icons/pdtInfo.png"></v-title>    
                <product 
                    v-for="(product,index) in products" 
                    :key="product.id" :product="product"
                    :is-complete="comfirmedProducts[index] ? true : false" 
                    @confirm="handleProductComfirm" 
                    @edit="handleProductEdit"
                    @cancel="handelCancelProduct">
                </product>
            </div>
            <div class="submitBtn" @click="handleCompleteOrder">提交完工</div>
            <refuse-order v-show="isShowRefuse" @comfirm="handleOtherReaconCancel" @cancel="isShowRefuse = false"></refuse-order>
            <input type="hidden"  ref="select_input" @change="handleCancelReasonChange">
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
        <!-- 产品信息模板 -->
        <script type="text/x-template" id="products_template">
            <div class="infoBox">
                <div class="product">
                    <!-- 完成状态：添加complete类名 -->
                    <div class="isComfirmIcon" v-if="!isCancel" :class="{'complete':isComplete}"></div>
                    <div class="line" >
                        <span>名称：</span>
                        <span>{{product.productName}}</span>
                    </div>
                    <div class="line">
                        <span>型号：</span>
                        <span>{{product.modelName}}</span>
                    </div>
                    <div class="line" v-show="!isShowBarCodeInput">
                        <span>条码：</span>
                        <span>{{barCode || '无'}}</span>
                    </div>
                    <div class="barCode" v-show="isShowBarCodeInput">
                        <span class="left-text">
                            <i>*</i>输入产品条码:</span>
                        <input type="text" placeholder="请输入产品条码" v-model="barCode">
                        <span class="scanBarCode" @click="scanBarCode"></span>
                    </div>
                    <div class="uploadImage" v-show="isShowUploadImage" @click="uploadImage">
                        <span class="addIcon"></span>
                        <span class="uploadText">如机器上无条码，请点击上传图片证明</span>
                        <p class="tip">请拍摄照片证明机器上无条码</p>
                    </div>
                    <div class="imgBox" v-show="!isCancel && productImage.length !== 0"> 
                        <div v-for="(item,index) in 3" class="img" :class="{'hide':!productImage[index] && isComplete}" @click="uploadImage(index)">
                            <i class="delImgIcon" v-show="productImage[index] && !isComplete" @click.stop="delectImg(index)"></i>
                            <img :src="productImage[index]"  v-show="productImage[index]">
                        </div>        
                    </div>
                    <div class="btnBox">
                        <button class="qy-btn-180" :class="{'cancelBtn':isCancel}"  @click="cancelProduct">{{isCancel ? '已取消' : '取消'}}</button>
                        <button class="qy-btn-180" @click="edit" v-show="!isCancel">{{isComplete ? '编辑' : '确认'}}</button>      
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
                        <p class="mcmText">确认取消吗?</p> 
                        <p class="tipText">请填写取消理由</p>
                        <div class="textAreaBox">
                            <div style="border:1px solid #000">
                                <textarea placeholder="请输入原因,3-30个字(必填)"  maxlength="30" v-model="reason"></textarea>
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
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/vue/vue.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/workOrderOperation/unfinishedOrderDetail_install.js${f_ver}"></script>
    </body>
</html>