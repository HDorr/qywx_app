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
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/workOrderOperation/unfinishedwoDetail.css">
    </head>
    <body>
        
        <button id="btn">test</button>    
       <!--  <div id="mobileConfirmModal" class="show">
            <div class="mainContainer">
                <div class="mcmheader">
                    <i onclick="confirmModal.closeModal()">X</i>
                </div>
                <div class="mcmbody">
                    <p class="mcmText">确认更改预约为：<br/>02-16 14:00-16:00?</p>
                    <p class="tipText">温馨提示：<br/>更改前请跟客户协商达成一致！</p>
                    <div class="textAreaBox">
                        <textarea placeholder="请输入更改时间的原因,1-30个字(必填)" maxlength="30"></textarea>
                    </div>
                    <div class="btnBox">
                        <button class="cancel" onclick="confirmModal.closeModal()">取消</button>
                        <button class="confirm" onclick="confirmModal.confirmClick()">确认</button>
                    </div>
                </div>
            </div>
        </div>  -->


        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.2.0.js"></script>
        <!-- <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PeQf3WDGEeajR3Lhzw2VDUbygzuilGXW"></script> -->
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js"></script>
        <script>
            $("#btn").click(function(){
                confirmModal.init({
                    text:"确认更改预约为：<br/>02-16 14:00-16:00?",
                    tip:"温馨提示：<br/>更改前请跟客户协商达成一致！",
                    textArea:true,
                    callback:function(fn,text){
                        console.log(fn);
                        console.log(text)
                    }
                })
            })
        </script>
    </body>
</html>


 


