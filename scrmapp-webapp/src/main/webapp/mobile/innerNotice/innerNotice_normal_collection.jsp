<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公告详情</title>
    
	<meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/noticeDetail/noticeDetail.css${f_ver}">

  </head>
  
  <body>

  <!-- <section class="content" style="background: #f0f0f6;"> -->
  <section class="content noticeDetailList" style='background-image:url("<c:if test="${data.watermarkImg !=null}">${data.watermarkImg}</c:if><c:if test="${data.watermarkImg ==null}">${f_ctxpath}/resources/images/qyhnoticebg.png${f_ver}</c:if>")'>
		<div class="contentTop">
			<div class="title">${data.noticeTitle }</div>
		</div>
		<div class="topDown">
			<span class="time"><fmt:formatDate value="${data.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			<span class="readNum">浏览(${data.viewCount })</span>
			<c:if test="${data.collection ==true}"><span class="starIcon active"></span></c:if>
			<c:if test="${data.collection ==false}"><span class="starIcon"></span></c:if>
			
		</div>
			
		<div class="contentCenter textCon">
            ${data.noticeContent }
			<!-- <p class="textCon">${data.noticeContent }</p> -->
		</div>
		<c:if test="${data.unReadCount !=null}">
			<ul class="noticeList">
	            <li class="listCon activeList" style="border-top: 1px solid #8f8f8f;">
	                <a href = "qyhNotice/jumpInnerNoticeUnreadNumPage?userId=${data.userId}&noticeId=${data.noticeId }">
		                <span class="noticeTitle">未读人数(${data.unReadCount })</span>
		                <span class="rightArrow">></span>
	                </a>
	            </li>
	        </ul>
        </c:if>
	</section>
  <input type="hidden" id="userIdInput" value=${data.userId} >
  <input type="hidden" id="noticeIdInput" value=${data.noticeId}>
  </body>
   <script src="${f_ctxpath}/resources/src/js/lib-flexible-master/build/flexible_css.debug.js"></script>
   <script src="${f_ctxpath}/resources/src/js/lib-flexible-master/build/flexible.debug.js"></script>
  <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
  <script src="${f_ctxpath}/resources/src/js/common.js"></script>
  <script src="${f_ctxpath}/resources/src/js/innerNotice/innerNotice_normal_collection.js${f_ver}"></script>
</html>
