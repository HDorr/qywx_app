<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>关注页面</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <style>
    .main-layer{
      max-width: 750px;
      margin:0 auto;
    }
    .main-layer .text{
      width: 100%;
      text-align:center;
      font-size: 18px;
      font-weight: bold;
      margin-top: 40px;
    }
    .main-layer .img-box{
      width: 80%;
      margin: 40px auto 0 auto;
    }
    .main-layer .img-box .qrCode{
    width: 100%;
    }
    </style>
  </head>
  <body>
    <div class="main-layer">
      <p class="text">您还不是粉丝哦，长按钮下方二维码进行关注</p>
      <div class="img-box">
        <img src="${f_ctxpath}/resources/src/images/qy_QR_code.jpg${f_ver}" class="qrCode">
      </div>
    </div>
  </body>
</html>