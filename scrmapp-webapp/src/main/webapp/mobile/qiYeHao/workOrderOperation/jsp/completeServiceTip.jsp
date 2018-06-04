<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>预约服务</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <!-- <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css"> -->
        <!-- <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css"> -->
        <style>
            .main_layer {
                  position: relative;
                  max-width: 750px;
                  margin: 0 auto;
                  height: 100%;
                  background-color: #f4f4f4;
                  padding-top: 2.24rem;
                }
                .main_layer .imgBox {
                  width: 3.24rem;
                  height: 3.24rem;
                  background-size: cover;
                  margin-left: auto;
                  margin-right: auto;
                }
                .main_layer > p {
                  width: 100%;
                  text-align: center;
                  color: #383838;
                  font-size: 0.333333rem;
                  margin-top: 0.573333rem;
                }
                .main_layer .qy-btn-400 {
                  display: block;
                  margin-top: 0.64rem;
                  margin-left: auto;
                  margin-right: auto;
                }
                .loadingBox {
                  padding-top: 40%;
            }
        </style>
    </head>
    <body>
        <div class="main_layer clearfix">
            <div class="imgBox" style="background-image: url('${f_ctxpath}/resources/images/workOrderOperation/claps.png${f_ver}')"></div>
                <p>恭喜您，本次服务完成啦！</p>
                <a class="qy-btn-400" id="bindProductBtn"  href="javascript:;">返回我的工单列表</a>
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <!-- <script src="${f_ctxpath}/resources/src/js/workOrderOperation/installWorkOrder.js"></script> -->
        <script>  
              var userId = getUrlParam('userId');
              document.getElementById('bindProductBtn').addEventListener('click',function(e){
                window.location.href = pageUrls.unfinishedWorkOrderList+"?userId="+userId+"&condition=today"
              })
        </script>
    </body>
</html>