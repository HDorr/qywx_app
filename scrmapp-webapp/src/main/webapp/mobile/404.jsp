<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
	<title id="stat"></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/redpack/redpackindex.css${f_ver}" />
    <link id="skin" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
   <div class="wrapper">
    <section class="requestFailed">
        <span><img src="<%=basePath%>resources/images/redenvelopeppsterimg/failed.jpg${f_ver}" alt=""/></span>
        </section>
   </div>
  </body>
</html>
