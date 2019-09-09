<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>绑定产品</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/bindProduct/bindProduct.css${f_ver}">
    </head>
    <style type="text/css">
        input:disabled{
            background-color:#fff;
            color:#666;
            opacity:1;
            -webkit-text-fill-color:#666;
            -webkit-opacity:1;
        }
    </style>
    <body>
        <input type="hidden" id="barCodeIdInput" value="${data}">
        <input type="hidden" id="securityCodeInput" value="${securityCode}">
        <div class="main_layer">
            <ul class="pdtInfo">
            
                <!--<li class="pdtImg" style="background-image: url('/resources/images/testPdtImg.png');"></li>
                <li class="pdtInfoTitle">产品信息</li>
                <li class="pdtInfoForm firstThree"><span>产品条码:</span><span>5557816083046302</span></li>
                <li class="pdtInfoForm firstThree"><span>产品名称:</span><span>沁园牌反渗透净水器</span></li>
                <li class="pdtInfoForm firstThree"><span>产品大类:</span><span>超滤机</span></li>
                <li class="pdtInfoForm"><span>产品编号:</span><span>0704001</span></li>
                <li class="pdtInfoForm"><span>产品型号:</span><span>QG-U-1004(电子商务专供)</span></li>
                <li class="pdtInfoForm"><span>滤芯级别:</span><span>超滤过滤-5</span></li>
                <li class="pdtInfoForm"><span>销售类型:</span><span>家用</span></li>
                <li class="pdtInfoForm">
                    <span>购买渠道:</span>
                    <div class="weui-cells_checkbox">
                        <label class="weui-check__label" for="online">
                            <input type="radio" class="weui-check" name="check" id="online" checked="checked">
                            <i class="weui-icon-checked"></i>
                            <span>线上</span>
                        </label>
                        <label class="weui-check__label" for="offline">
                            <input type="radio" class="weui-check" name="check" id="offline" >
                            <i class="weui-icon-checked"></i>
                            <span>线下</span>
                        </label>
                    </div>
                    <select>
                        <option selected="" value="1">请选择</option>
                        <option value="2">QQ号</option>
                        <option value="3">Email</option>
                    </select>
                </li>-->
                <div class="weui-loadmore">
                    <i class="weui-loading"></i>
                    <span class="weui-loadmore__tips">正在加载</span>
                </div>
                <div class="weui-loadmore weui-loadmore_line">
                    <span class="weui-loadmore__tips">暂无数据</span>
                </div>
                <div class="weui-loadmore weui-loadmore_line weui-loadmore_dot">
                    <span class="weui-loadmore__tips"></span>
                </div>
            </ul>
            <div class="netNumber">
                <span>网购单号：</span>
                <input type="text" class="qy-input-530">
            </div>
            <button class="qy-btn-all" id="bindPdtBtn">绑定产品</button>
            <p class="warning">所有信息提交后将无法修改</p>
        </div>
        <input type="hidden" id="modelNameInput" value=${modelName}>
        <input type="hidden" id="productBarCodeInput" value=${productBarCode}>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui-1.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/bindProduct/bindPdt.js${f_ver}"></script>
    </body>
</html>