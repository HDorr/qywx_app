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
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/bindProduct/securityCheck.css${f_ver}">
    </head>
    <body>
        <input type="hidden" id="wxName" value="${data}">
        <div class="main_layer clearfix">
<!--         	<div class="content_success">
        		<div class="top ">
	        		<div class="imgBox">
	        			<span></span>
	        		</div>
        			
        			<p>
        				<span class="thankTxt">感谢使用</span>
        			</p>
        			<p>
        				您验证的产品是沁园官方正品。
        			</p>
        		</div>
        		<div class="down">
        			<p>产品信息如下：</p>
        			<p>产品名称：<span>沁园牌反渗透净水器</span></p>
        			<p>产品型号：<span>XX-XX-XX（电子商务专供）</span></p>
        			<p>产品条码：<span>88112233445566</span></p>
        		</div>
        	</div>
 -->        	<div class="content_failed">
        		<div class="top ">
        			<p>
        				<span class="sorryTxt">抱歉，查询失败</span>
        			</p>
	        		<div class="imgBox">
	        			<span></span>
	        		</div>
        		</div>
        		<div class="down">

        			<p>亲爱的<span class="userName">XXX</span>：</p>
        			<p>很抱歉您的防伪码无法识别，请重试，或与卖家联系咨询。</p>
        		</div>
        	</div>
            
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js"></script>

        <script>
            $(function(){
                var wxName = getUrlParam("wxName")||$('#wxName').val();
                $('.userName').html(wxName)
            })
        </script>
    </body>
</html>