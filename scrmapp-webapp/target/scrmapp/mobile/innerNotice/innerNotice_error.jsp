<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

    <input type="hidden" id="errorCode" value=${errorCode}>
    <input type="hidden" id="msg" value=${msg}>

    <section class="content">

      <div class="imgBox">
          <span class="img" ></span>      
      </div>
      <p class="warningTxt"></p>
      <div class="qrCode">
          <span class="img" ></span>      
      </div>
    </section>
  </body>
  <script src="${f_ctxpath}/resources/src/js/lib-flexible-master/build/flexible_css.debug.js"></script>
  <script src="${f_ctxpath}/resources/src/js/lib-flexible-master/build/flexible.debug.js"></script>
  <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
  <script src="${f_ctxpath}/resources/src/js/common.js"></script>
  <script src="${f_ctxpath}/resources/src/js/innerNotice/innerNotice_error.js${f_ver}"></script>
</html>
