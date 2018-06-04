<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/noticeDetail/noticeDetail.css${f_ver}">

    
    <title>${typeTitle}</title>
  </head>
  
  <body>
    <header class="search_head">
		<div class="search_div">
			<span class="searchIcon"></span>
			<input class="searchInput" type="text" id="searchByTitle" placeholder="请输入公告标题">
		</div>
		<span class="searchBtn" id="searchBtn">搜索</span>
	</header>
	<section class="content noticeDetailList"  style="background: #f1f0f6;">
		<ul class="noticeList" id="noticeListContainer">
  <%--          <li class="listCon activeList">
                <a href="javascript:;">
                    <span class="noticeTitle">测试测试</span>
                    <span class="rightArrow">></span>
                    <span class="noticeTime">2017-01-01 23:59</span>
                </a>
            </li>
            <li class="listCon ">
                <span class="noticeTitle">测试测试</span>
                <span class="rightArrow">></span>
                <span class="noticeTime">2017-01-01 23:59</span>
            </li>
            <li class="listCon ">
                <span class="noticeTitle">测试测试</span>
                <span class="rightArrow">></span>
                <span class="noticeTime">2017-01-01 23:59</span>
            </li>
            <li class="listCon ">
                <span class="noticeTitle">测试测试</span>
                <span class="rightArrow">></span>
                <span class="noticeTime">2017-01-01 23:59</span>
            </li>
            <li class="listCon ">
                <span class="noticeTitle">测试测试</span>
                <span class="rightArrow">></span>
                <span class="noticeTime">2017-01-01 23:59</span>
            </li>
            <li class="listCon ">
                <span class="noticeTitle">测试测试</span>
                <span class="rightArrow">></span>
                <span class="noticeTime">2017-01-01 23:59</span>
            </li>
            <li class="listCon ">
                <span class="noticeTitle">测试测试</span>
                <span class="rightArrow">></span>
                <span class="noticeTime">2017-01-01 23:59</span>
            </li>--%>
        </ul>
        <div class="noData" id="getMoreBtn">加载更多</div>
	</section>

    <input type="hidden" id="typeInput" value=${type }>
    <input type="hidden" id="typeTitleInput" value=${typeTitle}>
    <input type="hidden" id="assortmentIdInput" value=${assortmentId }>
    <input type="hidden" id="userIdInput" value=${userId }>
  </body>
  <script src="${f_ctxpath}/resources/src/js/lib-flexible-master/build/flexible_css.debug.js"></script>
  <script src="${f_ctxpath}/resources/src/js/lib-flexible-master/build/flexible.debug.js"></script>
  <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
  <script src="${f_ctxpath}/resources/src/js/common.js"></script>
  <script src="${f_ctxpath}/resources/src/js/innerNotice/noticeDetail_default.js${f_ver}"></script>
</html>
