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
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/myOrder/orderDetail.css${f_ver}">
</head>

<body>
    <div class="main_layer clearfix"></div>
    <input type="hidden" name="orderTime" id="changeOrderInput" value="">
    <!-- 标题template -->
    <script type="text/html" id="title_template">
        <section class="header qy-blue">
            <div class="pull-left">{{orderTypeName}}：<span>{{orderStatus}}</span></div>
            <div class="pull-right">预约时间:
                <p> 
                    <span>{{orderTimeArr[0]}}</span> 
                    <span>{{orderTimeArr[1]}}</span> 
                </p> 
            </div> 
        </section>
    </script>
    <!-- 受理记录template -->
    <script type="text/html" id="accept_record_template">
        <section class="acceptRecord"> 
            <div class="oDHeader"> 
                 <p class="pull-left"><img src="${f_ctxpath}/resources/src/images/icons/waitForAccept.png" />受理记录</p> 
            </div> 
            <ul class="recordStream">
                {{each wechatOrdersRecordList as stage}} 
                <li> 
                    <div class="streamLeft"> 
                        <p class="upperLine"></p> 
                        <p class="circle"></p> 
                        <p class="dnLine"></p> 
                    </div> 
                    <div class="streamRight"> 
                        <p>{{stage.recordContent}}</p> 
                        <p>{{stage.recordTime}}</p> 
                    </div> 
                </li> 
                {{/each}}
            </ul> 
        </section>
    </script>
    <!-- 用户信息template -->
    <script type="text/html" id="userInfo_template">
        <section class="customInfo">
            <div class="oDHeader">
                <p class="pull-left">
                    <img src="${f_ctxpath}/resources/src/images/icons/personInfo.png">
                    用户信息
                </p>
            </div>
            <div class="textBox">
                <div class=" line clearfix">
                    <p class="leftText">联系信息：</p>
                    <p class="rightText">{{userName}}, {{userMobile}},{{contactsTelephone}}</p>
                </div>
                <div class=" line clearfix">
                    <p class="leftText">服务地址：</p>
                    <p class="rightText">{{userAddress}}</p>
                </div>
            </div>
        </section>
    </script>
    <!-- 预约信息template -->
    <script type="text/html" id="orderInfo_template">
        <section class="orderInfo">
            <div class="oDHeader">
                <p class="pull-left"><img src="${f_ctxpath}/resources/src/images/icons/order.png">预约信息</p>
            </div>
            <div class="textBox">
                <div class=" line clearfix">
                    <p class="leftText">预约时间：</p>
                    <p class="rightText">{{orderTime}}</p>
                </div>
                {{if descTitle}}
                <div class=" line clearfix">
                    <p class="leftText">{{descTitle}}</p>
                    <p class="rightText">{{description}}</p>
                </div>
                    {{if maintType == 2}}
                    <div class=" line clearfix">
                        <p class="leftText">是否已购买滤芯:</p>
                        <p class="rightText">{{buyFilter == 1?'已购买':'未购买'}}</p>
                    </div>
                    {{/if}}
                {{/if}}
                {{if faultImage}}
                <div class="imgBox" style="display:block">
                    <span class="pull-left">维修照片：</span>
                </div>
                <div class="line">
                    {{each faultImageArray as imageUrl}}
                        <span class="pull-left" style="background-image:url('{{imageUrl}}')" onclick="previewImage('{{imageUrl}}')"></span>
                    {{/each}}
                </div>
                {{/if}}
            </div>
        </section>
    </script>
    <!-- 产品信息template -->
    <script type="text/html" id="pdtInfo_template">
        <section class="pdtInfo">
            <div class="oDHeader">
                <p class="pull-left"><img src="${f_ctxpath}/resources/src/images/icons/pdtInfo.png">产品信息</p>
            </div>
            {{each products as product}}
            <div class="textBox">
                <div class="line">
                    <p class="pdtName">{{product.productName}}</p>
                </div>
                <div class=" line clearfix">
                    <p class="leftText">产品型号：</p>
                    <p class="rightText">{{product.modelName}}</p>
                </div>
                <div class=" line clearfix">
                    <p class="leftText">产品条码：</p>
                    <p class="rightText">{{product.productBarCodeTwenty}}</p>
                </div>
                <div class="pdtImgBox">
                    <div class="pdtImg" style="background-image: url({{product.productImage}});"></div>
                </div>
            </div>
            {{/each}}
            <!-- 根据status来控制按钮的显示 -->
            <!-- 处理中的状态 data.status:1:处理中  2:已取消  3:重新处理中  4:已接单 5:服务完成 6:评价完成 -->
            {{if status == 1 || status == 3 || status == 4}}
            <div class="btnBox clearfix">
                <button class="qy-btn-180 pull-right" id="changeOrder" data-username="{{userName}}">更改预约</button>
                <button class="qy-btn-cancel pull-right" id="cancelOrder" onclick="cancelOrder(this)" data-username="{{userName}}">取消预约</button>
            </div>
            {{/if}}
        </section>
    </script>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/thirdparty/arttemplate/arttemplate.js"></script>
    <script src="${f_ctxpath}/resources/src/js/myOrder/orderDetail.js${f_ver}"></script>
</body>

</html>