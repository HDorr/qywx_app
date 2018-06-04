<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>预约维修服务</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css${f_ver}">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reserveService/reserveService.css${f_ver}">
    </head>
    <body>
        <header class="conHeader">
            <p>您可以提前预约，享受专业便捷的服务支持！</p>
        </header>
        <div class="main_layer "style="padding-top: 0.666667rem;padding-bottom: 2.0rem">
            <section class="content reserveCon" style="background:#fff" >
                <div class="addInfo">
                    <div class="mark">
                        产品信息&nbsp;&nbsp;>
                    </div>
                    <div class="addProductBtn" id="productInfo" style="background: #fff">
                        <!-- <div>+</div>
                        <p class="pTxt">请点击添加您要预约安装服务的产品哟！</p> -->
                    </div>
                    <div class="mark">
                        用户信息&nbsp;&nbsp;>
                    </div>
                    <div class="addProductBtn addAddressBtn" id="userInfo">
                        <div>+</div>
                        <p class="pTxt">请点击选择您要预约安装服务的地址哟！</p>
                    </div>
                </div>
                <div class="reserveTime">
                    <div class="Mark noBorder">
                        预约信息
                    </div>
                    <div class="chooseTime">
                        <span>上门时间：</span>
                        <div  id="changeOrder">
                            请填写上门服务时间！
                        </div>
                    </div>
                    <p class="orderTimeTips">预约的服务时间，请以工程师确认时间为准。</p>
                </div>
                <div class="installDescription">
                    <div class="Mark noBorder">
                        故障描述
                    </div>
                    <div class="textareaBox">
                        <textarea name="" id="description" cols="30" rows="5" placeholder="如有其他需要特别说明，请填写！"></textarea>
                    </div>
                    
                </div>
                <div class="uploadImg">
                    <span class="uploadTitle">上传照片：</span>
                    <span  class="uploadBtn" id="uploadBtn">
                        +上传
                    </span>
                    <span class="limit">(最多上传2张)</span>
                </div>
                <div class="imgShow">
                    <span>+</span>
                    <span>+</span>
                </div>

            </section>
                
        </div>
            <footer class="conFooter" style="background:#fff">
                <button class="qy-btn-400" id="submitReserve" >提交预约</button>
            </footer>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/src/js/reserveService/reserveService.js${f_ver}"></script>
    </body>
</html>