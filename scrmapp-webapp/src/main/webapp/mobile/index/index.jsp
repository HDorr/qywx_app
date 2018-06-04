<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>一键服务</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css">
    </head>
    <body>
        <div class="main_layer">
            <ul class="container">
                <li class="flex-item">
                    <a href="${updateFilterUrl}">
                        <img src="${f_ctxpath}/resources/images/index/changeFilter.png">
                        <p>预约滤芯</p>
                    </a>
                </li>
                <li class="flex-item">
                
                    <a href="${installUrl}">
                        <img src="${f_ctxpath}/resources/images/index/orderInstall.png">
                        <p>预约安装</p>
                    </a>
                </li>
                <li class="flex-item">
                    <a href="${maintainUrl}">
                        <img src="${f_ctxpath}/resources/images/index/orderMantainance.png">
                        <p>预约维修</p>
                    </a>
                </li>
                <li class="flex-item">
                    <a href="${cleanUrl}">
                        <img src="${f_ctxpath}/resources/images/index/orderClean.png">
                        <p>预约清洗</p>
                    </a>
                </li>
                <li class="flex-item">
                    <a href="${securityCheckUrl}">
                        <img src="${f_ctxpath}/resources/images/index/check.png">
                        <p>产品防伪查询</p>
                    </a>
                </li>
                <li class="flex-item">
                    <%--<a href="${changeRemindUrl}">--%>
                    <a >
                        <img src="${f_ctxpath}/resources/images/index/changeTip_gray.jpg">
                        <p style="color:#b3b3b3">滤芯更换提醒设置</p>
                    </a>
                </li>
            </ul>
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script>
            for(var key in localStorage){
                if(key !== "chooseProductNotFirstTime"){
                    localStorage.removeItem(key)
                }
            }
            var flexBox = document.querySelectorAll(".flex-item");
            for(var i = 0 ;i<flexBox.length;i++){
                flexBox[i].addEventListener("touchstart",function(e){
                    this.classList.toggle("active")
                })
                flexBox[i].addEventListener("touchend",function(e){
                    this.classList.toggle("active")
                })
            }
        </script>
    </body>
</html>