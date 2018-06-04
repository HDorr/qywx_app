<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>完工提交-维修</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/qiYeHao/submit_order.css${f_ver}">
    </head>
    <body>
        <div class="main_layer ">
        
        </div>
        <div class="btn">
            <button class="qy-btn-400">提交完工</button>
        </div>
        <script  id="orderSubmitTemplate" type="text/html">
            <input type="hidden" id="productId" value="{{productId}}">
            <input type="hidden" id="typeName" value="{{typeName}}">
            <input type="hidden" id="modelName" value="{{modelName}}">

            <div class="topTips">
                请确认信息后再提交！
            </div>
            <div class="scanArea clearfix">
                <div class="title">核实产品</div>
                <label class="pull-left">
                    产品条码：
                    <input class="qy-input-360" id="pdtCodeInput" type="number" placeholder="请输入产品条码">
                    <!-- <a href="javascript:;">如何查找产品条码？</a> -->
                </label>
                <div class="scanQrCode pull-right" id="scanQrCode" style="background-image: url('${f_ctxpath}/resources/images/bindProduct/scanQrCode.png');">
                    <p>扫码</p>
                </div>
            </div> 
            <div class="productImg" >
                <div style="border-top: 1px solid #f3f3f3;"></div>
                <div class="formBox">
                    <div class="noPicBox">
                        <span id="uploadBtn" onclick="upLoadImg()">
                            如果没有找到条码，请点击上传图片
                        </span>
                    </div>
                    <div class="picShowBox">
                        <span class="pull-left">现场图片：</span>
                        
                        <span class="picShow imgBox"><img src="" alt=""></span>
                        <span class="newUpLoad" onclick="upLoadImg()">重新上传</span>
                    </div>                
                </div>
                <p>请拍摄照片证明机器上无条码</p>
            </div>
            <div class="order_info">
                <div class="title">工单状态</div>
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
                <div class="imgBox">
                    <span class="pull-left img">
                        <img src="" alt="">
                    </span>
                    <span class="pull-left img">
                        <img src="" alt="">
                    </span>
                </div>
            </div>
            <div class="isCheckItem isMeasure">
                <span class="checkBox"></span>
                <span>是否选择维修措施</span>
            </div>
            <div class="measureBox">
                <!-- <span>下水位浮球有水垢或损坏</span>
                <span>></span> -->
            </div>
            <div class="isCheckItem isPart">
                <span class="checkBox"></span>
                <span>是否使用配件</span>
            </div>
            <div class="partBox">
                <table class="partTable">
                    <thead class="tableHeader">
                        <tr>
                            <td>序号</td>
                            <td>配件名称</td>
                            <td>收费（元）</td>
                            <td>数量</td>
                        </tr>
                    </thead>
                    <tbody class="tableBody">
                   </tbody>
                </table>
            </div>
        </script>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/src/js/qiYeHao/order_submit.js${f_ver}"></script>
    </body>
</html>
