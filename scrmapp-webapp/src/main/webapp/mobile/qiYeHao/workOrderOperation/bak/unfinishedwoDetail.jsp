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
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/workOrderOperation/installWorkOrder.css${f_ver}">
    </head>
    <body>
        <input type="hidden" id="userIdInput" value="${userId}">
        <input type="hidden" id="ordersCodeInput" value="${ordersCode}">
        <div class="main_layer clearfix">

        </div>


        <script id="ufDetailTemplate" type="text/html">
            <section class="header qy-blue">
                <div class="pull-left">{{orderTypeName}}：<span>{{orderStatus}}</span></div>
                <div class="pull-right">预约时间：
                    <p>
                        <span>{{splitData[0]}}</span>
                        <span>{{splitData[1]}}</span>
                    </p>
                </div>
            </section>
            <section class="customInfo">
                <div class="oDHeader">
                    <p class="pull-left"><img src="${f_ctxpath}/resources/src/images/icons/personInfo.png${f_ver}">用户信息</p>
                </div>
                <div class="textBox">
                    <div class=" line clearfix">
                        <p class="leftText">联系信息：</p>
                        <a  href="tel:{{userMobile}}" class="rightText">{{userMobile}} <i class="telIcon"></i></a>
                    </div>
                    <div class=" line clearfix">
                        <p class="leftText">服务地址：</p>
                        <p class="rightText" data-useraddress="{{userAddress}}" onclick="gotoNav(this)" > {{userAddress}} <i class="navIcon"></i></p>
                    </div>
                </div>
            </section>
            <section class="orderInfo">
                <div class="oDHeader">
                    <p class="pull-left"><img src="${f_ctxpath}/resources/src/images/icons/order.png${f_ver}">预约信息</p>
                </div>
                <div class="textBox">
                    <div class=" line clearfix">
                        <p class="leftText">预约时间：</p>
                        <p class="rightText">{{orderTime}}</p>
                    </div>
                    <div class=" line clearfix">
                        <p class="leftText">安装描述：</p>
                        <p class="rightText">{{description}}</p>
                    </div>
                    <div class="imgBox">
                       <span class="pull-left">维修照片：</span>
                       <span class="pull-left img">
                           <img src="" alt="">
                       </span>
                       <span class="pull-left img">
                           <img src="" alt="">
                       </span>
                   </div>
                </div>
            </section>
            <section class="pdtInfo">
                <div class="oDHeader">
                    <p class="pull-left"><img src="${f_ctxpath}/resources/src/images/icons/pdtInfo.png${f_ver}">产品信息</p>
                </div>
                <div class="textBox">
                    <div class="line">
                        <p class="pdtName">{{productName}}</p>
                    </div>
                    <div class=" line clearfix">
                        <p class="leftText">产品型号：</p>
                        <p class="rightText">{{modelName}}</p>
                    </div>
                    <div class=" line clearfix">
                        <p class="leftText">产品条码：</p>
                        <p class="rightText">{{productBarCode}}</p>
                    </div>
                    <div class="pdtImgBox">
                        <div class="pdtImg" style="background-image: url('{{productImage}}');"></div>
                    </div>
                </div>
            </section>
            <section class="acceptRecord">
                <div class="oDHeader">
                    <p class="pull-left"><img src="${f_ctxpath}/resources/src/images/icons/waitForAccept.png${f_ver}">受理记录</p>
                </div>
                <ul class="recordStream">
                    {{each wechatOrdersRecordList as v i}}
                    <li>
                        <div class="streamLeft">
                            <p class="upperLine"></p>
                            <p class="circle"></p>
                            <p class="dnLine"></p>
                        </div>
                        <div class="streamRight">
                            <p>{{v.recordContent}}</p>
                            <p>{{v.recordTime}}</p>
                        </div>
                    </li>
                    {{/each}}
                </ul>
                {{if isShowBtn}}
                <div class="btnBox clearfix">
                    <button class="qy-btn-180 pull-right" data-ordertype="{{orderType}}" onclick="completeOrder(this)" >完工提交</button>
                    <button class="qy-btn-cancel pull-right" id="changeOrder" data-orderscode="{{ordersCode}}" data-ordersid="{{id}}" data-ordertype="{{orderType}}" data-contacts="{{userName}}" data-oldtime="{{orderTime}}">更改预约</button>
                    <button class="qy-btn-cancel pull-right" data-orderscode="{{ordersCode}}" data-ordersid="{{id}}" data-ordertype="{{orderType}}" data-contacts="{{userName}}" onclick="refuseOrder(this)">拒绝工单</button>
                </div>
                {{/if}}
            </section>
        </script>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/workOrderOperation/unfinishedwoDetail.js${f_ver}"></script>
    </body>
</html>