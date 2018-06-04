<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>防伪查询</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/securityCheck/securityCheckResult.css${f_ver}">
    </head>
    <body>
        <div class="main_layer">
            <!-- 查询成功 加success类名 查询失败 加fail类名 -->
           <div class="container">
               <p class="btnLikeTip"></p>
               <!-- <p class="btnLikeTip">抱歉，查询失败</p> -->
               <div class="imgBox"></div>
               <p>亲爱的用户:</p>
               <p class="resultText"></p>
               <!-- <p>很抱歉，您输入的防伪码无法识别，请重新输入，或与卖家联系咨询。</p> -->
           </div>
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script>
            var isSuccess = getUrlParam("status")==1?true:false;
            if(!isSuccess){
                $(".btnLikeTip").text("抱歉，查询失败");
                $(".resultText").text("很抱歉，您输入的防伪码无法识别，请重新输入，或与卖家联系咨询。")
                $(".container").addClass('fail');
            }else{
                 $(".btnLikeTip").text("感谢使用");
                $(".resultText").text("您验证的产品是沁园官方正品。")
                $(".container").addClass('success');
            }
        </script>
    </body>
</html>