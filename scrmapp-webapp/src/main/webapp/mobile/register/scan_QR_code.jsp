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
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
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
      <p class="text" id="qr"></p>
      <div class="img-box">
        <img src="${f_ctxpath}/resources/src/images/qy_QR_code.jpg${f_ver}" class="qrCode">
      </div>
    </div>
  </body>
  <script>
      wx.miniProgram.getEnv(function (res) {
        var elem = document.getElementById('qr');
        if(true===res.miniprogram){
          elem.innerHTML="您还不是粉丝哦，长按二维码保存至本地后，扫描二维码关注沁园即可继续预约";
        }else{
          elem.innerHTML="您还不是粉丝哦，长按钮下方二维码进行关注";
        }
      })
  </script>
</html>