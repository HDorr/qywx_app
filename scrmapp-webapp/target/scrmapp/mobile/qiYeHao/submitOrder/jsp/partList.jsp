<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>选择配件</title>
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
    	<!-- <div class="errorIcon"></div>
    	<p class="errorTxt">抱歉，没有查到该产品对应的配件信息</p> -->
        
        <div class="main_layer " style="padding-top: 0px;">
        
        </div>
        <div class="btn">
            <button class="qy-btn-180 confirmBtn">确认</button>
            <button class="qy-btn-180 cancelBtn" onclick="history.go(-1)">取消</button>
        </div>
        <script   id="partListTemplate" type="text/html">
            <div class="topTips">
                请确认信息后再提交！
            </div>
            <div class="searchBox">
                <span class="allBtn">全部</span>
                <div class="">
                    <!-- <span class="allBtn">全部</span> -->
                    <input class="searInput" type="text" value="请输入关键词" value="请输入关键词" onfocus="if (value =='请输入关键词'){value =''}" onblur="if (value ==''){value='请输入关键词'}">
                    <span class="searBtn"></span>
                </div>
            </div>
            <div class="partListBox">
            	<table>
            		<thead>
            			<tr>
            				<td><span class="allCheck"  style="margin-right: 0.133333rem"></span>序号</td>
            				<td>配件名称</td>
            				<td>单价（元）</td>
            				<!-- <td>数量</td> -->
            			</tr>
            		</thead>
            		<tbody>
            		{{each data as v index}}
            			<tr class="list" id="{{v.id}}">
            				<td><span  class="checkBox" id="{{v.id}}"></span></td>
            				<td class="partName">{{v.name}}</td>
            				<td class="partPrice">{{v.salePrice}}</td>
            				<!-- <td class="partCount">{{v.partCount}}</td> -->
            			</tr>
            		{{/each}}
            			
            		</tbody>
            	</table>
            </div>
        </script>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/src/js/qiYeHao/partList.js${f_ver}"></script>
    </body>
</html>
