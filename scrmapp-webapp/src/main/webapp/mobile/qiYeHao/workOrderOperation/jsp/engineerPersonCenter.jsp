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
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/workOrderOperation/engineerPersonCenter.css${f_ver}">
    <!-- <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css"> -->
    <!-- <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css"> -->
  </head>
  <body>
    <input type="hidden" id="userIdInput" value="${userId}">
    <div class="main_layer clearfix">
      <div class="upperBanner" id="upperBanner">
        <img src="${f_ctxpath}/resources/images/testHead.png${f_ver}" class="headImg">
        <p><span></span> : <span class="egName">加载中...</span></p>
        <p><span></span> : <span class="egTel">加载中...</span></p>
      </div>
      <div class="appraisalBox">
        <div class="starLine clearfix">
          <p class="pull-left">服务态度：<span id="attrRate">0</span></p>
          <p class="pull-left starIconBox attitudeBox">
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
          </p>
        </div>
        <div class="starLine clearfix">
          <p class="pull-left">专业程度：<span id="proRate">0</span></p>
          <p class="pull-left starIconBox professionBox">
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
          </p>
        </div>
        <div class="starLine clearfix">
          <p class="pull-left">诚信情况：<span id="intRate">0</span></p>
          <p class="pull-left starIconBox integrityBox">
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
            <span class="starIcon"> </span>
          </p>
        </div>
      </div>
    </div>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script>    
        ajaxQueryPC(renderPC);

        function ajaxQueryPC(callback){
          var userId = $("#userIdInput").val() || ""
          var rootUrl = getRootPath();
          var url = rootUrl + "/scrmapp/qyhuser/index?userId="+userId

          $.ajax({
            url:url,
            type:"GET",
            success:function(data){
              if(data.returnCode===1){
                if(callback){
                  callback(data.data)
                }
              }else{
                alert(data.returnMsg)
              }
            },
            error:function(data){
              alert("网络错误")
            }
          })
        }


        function renderPC(data){
          data.attitude=data.attitude||0;
          data.profession=data.profession||0;
          data.integrity=data.integrity||0;
          data.imageUrl=data.imageUrl||"${f_ctxpath}/resources/images/testHead.png${f_ver}";
          data.position = data.position||"未知";
          var upperBox = $("#upperBanner");
          upperBox.find(".headImg").attr('src',data.imageUrl);
          upperBox.find(".egName").text(data.name+"  ("+data.position+")");
          upperBox.find(".egTel").text(data.mobile);


           //服务态度
           $("#attrRate").text(data.attitude)
          var attrStr = "";
          for (var i = 1; i <= 5; i++) {
              if (i <= data.attitude) {
                  attrStr += '<span class="starIcon active"></span>';
              } else {
                  attrStr += '<span class="starIcon"></span>';
              }
          }
          $(".attitudeBox").html(attrStr);

          //专业度
          $("#proRate").text(data.profession)
          var proStr = "";
          for (var i = 1; i <= 5; i++) {
              if (i <= data.profession) {
                  proStr += '<span class="starIcon active"></span>';
              } else {
                  proStr += '<span class="starIcon"></span>';
              }
          }
          $(".professionBox").html(proStr);

          //诚信度
          $("#intRate").text(data.integrity)
          var intStr = "";
          for (var i = 1; i <= 5; i++) {
              if (i <= data.integrity) {
                  intStr += '<span class="starIcon active"></span>';
              } else {
                  intStr += '<span class="starIcon"></span>';
              }
          }
          $(".integrityBox").html(intStr);

        }
    </script>
  </body>
</html>


