<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>预约更换滤芯</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reserveService/reserveService_updateFilter.css${f_ver}">
</head>
<body>
    <header class="conHeader">
        <p>您可以提前预约，享受专业便捷的服务支持！</p>
    </header>
    <div class="pdt-info bloque" id="pdtInfoBox"></div>
    <div class="user-info bloque" id="addressInfoBox"></div>
    <div class="reserve-info bloque" id="reserveInfoBox"></div>
    <footer class="conFooter" style="background:#fff">
        <button class="qy-btn-400" id="submitReserve">提交预约</button>
    </footer>


<!-- 产品信息-template -->
<script type="text/html" id="pdtInfo_template">
    <header class="title">
        <img src="${f_ctxpath}/resources/src/images/icons/pdtInfo.png">
        产品信息
    </header>
    <ul class="productList">
        {{each list as product}}
        <li>
            <div class="pdtInfo">
                <div class="pdtImg">
                    <img src="{{product.productImage}}" alt="">
                </div>
                <div class="pdtText">
                    <p>{{product.productName}}</p>
                    <p>产品条码: {{product.productBarCode}}</p>
                    <p>产品型号: {{product.modelName}}</p>
                </div>
                <div class="delete-product" data-productid="{{product.id}}"></div>
            </div>
        </li>
        {{/each}}
    </ul>
    <div class="add-box">
        <img src="${f_ctxpath}/resources/src/images/icons/add_grey.png" >
        请点击添加您要预约滤芯服务的产品哟！
    </div>
</script>

<!-- 地址用户信息-template -->
<script type="text/html" id="addressInfo_template">
    <header class="title">
        <img src="${f_ctxpath}/resources/src/images/icons/personInfo.png">
        用户信息
    </header>
    {{if data}}
    <div class="info-box">
        <div class="userAvatar">
            <img src="{{data.headimgurl}}" alt="">
        </div>
        <div class="userText">
            <p><img src="${f_ctxpath}/resources/src/images/icons/personInfo.png">{{data.contacts}}</p>
            <p><img src="${f_ctxpath}/resources/src/images/icons/tel.png"> {{data.contactsMobile}};{{data.fixedTelephone ? data.fixedTelephone : ""}}</p>
            <p><img src="${f_ctxpath}/resources/src/images/icons/location.png">{{data.provinceName}}{{data.cityName}}{{data.areaName}}{{data.streetName}}</p>
        </div>
        <div class="edit edit-address"></div>
     </div>
    {{else}}
    <div class="add-box edit-address">
        <img src="${f_ctxpath}/resources/src/images/icons/add_grey.png" >
        请点击选择您预约服务的地址哟！
    </div>
    {{/if}}
    <p class="tip" style="color:#000">您是否已购买滤芯，请做如下选择</p>
    <div class="isBuyFilter">
        <div class="buyed isBuyFilterSelect" data-isbuyfilter="1">已购买滤芯</div>
        <div class="unbuyed isBuyFilterSelect" data-isbuyfilter="2">未购买滤芯</div>
    </div>
    <p class="isBuyFilter-tip tip">点击菜单-一键服务-产品防伪查询可查询产品真伪</p>
</script>

<!-- 预约信息-template -->
<script type="text/html" id="reserveInfo_template">
    <header class="title">
        <img src="${f_ctxpath}/resources/src/images/icons/reserve.png">
        预约信息
    </header>
    <div class="timePickerContainer">
        <div>上门时间：</div>
        <div id="changeOrder">请填写上门服务时间</div>
    </div>
    <div class="reserveTime-tip tip">预约服务时间,请以工程师确认时间为准!</div>
    <div class="install-desc">
        <p>保养描述</p>
        <textarea id="description" placeholder="如有其他需要特别说明，请填写！"></textarea>
    </div>
</script>

<script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/arttemplate/arttemplate.js"></script>
<script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
<script src="${f_ctxpath}/resources/src/js/reserveService/reserveService_updateFilter.js${f_ver}"></script>
</body>
</html>