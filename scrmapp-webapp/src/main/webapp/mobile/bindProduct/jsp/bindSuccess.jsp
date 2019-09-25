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
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/bindProduct/bindSuccess.css?t=121111111">
    </head>
    <body>
    <div class="main_layer clearfix">
        <div class="imgBox" style="background-image: url('${f_ctxpath}/resources/images/bindProduct/bindSuccess.png${f_ver}')"></div>
        <p><i style="background-image: url('${f_ctxpath}/resources/src/images/icons/successIcon.png${f_ver}')"></i>恭喜您，绑定成功</p>
        <p class="gotoPdtList"><a href="${f_ctxpath}/scrmapp/consumer/user/filter/myProducts/jsp/myPdtList">产品列表</a></p>
        <ul class="container">
            <li class="flex-item">
                <a href="${securityCheckUrl}">
                    <img src="${f_ctxpath}/resources/images/index/check.png">
                    <p>防伪验证</p>
                </a>
            </li>
            <li class="flex-item">
                <%--<a href="${maintainUrl}">--%>
                <a >
                    <img src="${f_ctxpath}/resources/images/bindProduct/orderMantainance_gray.png">
                    <p style="color:#b3b3b3">产品说明书</p>
                </a>
            </li>

            <li class="flex-item">
                <a href="javascript:;" id="install">
                    <img src="${f_ctxpath}/resources/images/index/orderInstall.png">
                    <p>预约安装</p>
                </a>
            </li>
            <li class="flex-item">
                <a href="javascript:;" id="clean">
                    <img src="${f_ctxpath}/resources/images/index/orderClean.png">
                    <p>预约清洗</p>
                </a>
            </li>

            <li class="flex-item">
                <a href="javascript:;" id="filter">
                    <img src="${f_ctxpath}/resources/images/index/changeFilter.png">
                    <p>预约更换滤芯</p>
                </a>
            </li>
            <li class="flex-item">
                <a href="javascript:;" id="maintain">
                    <img src="${f_ctxpath}/resources/images/index/orderMantainance.png">
                    <p>预约维修</p>
                </a>
            </li>
        </ul>
    </div>

        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui-1.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#install').on("click",function () {
                  wx.miniProgram.navigateTo({url: "/pages/selectProduct?appointmentType=install"});
                });
                $('#clean').click(function () {
                  wx.miniProgram.navigateTo({url: "/pages/selectProduct?appointmentType=clean"});
                });
                $('#filter').click(function () {
                  wx.miniProgram.navigateTo({url: "/pages/selectProduct?appointmentType=filter"});
                });
                $('#maintain').click(function () {
                  wx.miniProgram.navigateTo({url: "/pages/selectProduct?appointmentType=maintain"});
                });
                var securityCode = getUrlParam("securityCode");
                var rootUrl = getRootPath();
                var data = {
                    barcode:securityCode
                };
                (securityCode.length === 20) && ($("#checkBtn").css('display','block'))
                $('#checkBtn').click(function() {
                    $.ajax({
                        url: rootUrl + "/scrmapp/consumer/user/query/securityCode",
                        data: data,
                        type: "GET",
                        success: function(data) {
                            if (data.returnCode === 1) {
                                console.log(data)
                                var prodName =escape(data.data.prodName);
                                var spec =data.data.spec;
                                var barCode =data.data.barCode;
                                window.location.href=rootUrl + '/mobile/bindProduct/jsp/securityCheckSuccess.jsp?barCode='+barCode+"&spec="+spec+"&prodName="+prodName;
                            } else {
                                var wxName = escape(data.data)
                                window.location.href=rootUrl + '/mobile/bindProduct/jsp/securityCheckFault.jsp?wxName='+wxName;
                            }
                        }
                    })
                    
                });




                // var checkBtn = document.getElementById('checkBtn')
                // checkBtn.onclick=function (){
                //     alert(barCode)
                // }
            })
        </script>
    </body>
</html>
