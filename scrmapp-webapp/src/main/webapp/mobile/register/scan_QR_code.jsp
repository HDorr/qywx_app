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
      font-size: 15px;
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


    .main-layer-new{
      max-width: 750px;
      margin:0 auto;
    }
    /*.main-layer-new p:nth-child(1){
      margin-top: 100px;
    }*/
    .main-layer-new .text{
      width: 100%;
      text-align:left;
      /*font-size: 18px;*/
      /*font-weight: bold;*/
      margin-top: 20px;
    }
    .main-layer-new .img-box{
      /*width: 80%;*/
      /*margin: 40px auto 0 auto;*/
      text-align:center;
      margin-top:40px;
    }
    .main-layer-new .img-box .qrCode{
      width: 60%;
    }
    .main-layer-new .icon{
      margin-right: 10px;
    }
    .tip{
      margin-left: 50%;
      transform: translateX(-50%);
    }
    .tips{
      width: 90%;
      margin:  0 auto;
    }
    .tip > .text{
      margin-top: 100px;
    }
  </style>
</head>
<body>
<%--<input type="hidden" value="myPdtList" id="formUrl" />--%>
<input type="hidden" value="${url}" hidden="hidden" id="formUrl" />
<div class="main-layer" hidden="" id="defaultChannelPage">
  <p class="text" id="qr">您还不是粉丝哦，长按钮下方二维码进行关注</p>
  <div class="img-box">
    <img src="${f_ctxpath}/resources/src/images/qy_QR_code.png${f_ver}" class="qrCode">
  </div>
</div>

<div class="main-layer-new" hidden="hidden" id="newChannelPage">
  <div class="tip">
    <p class="text" >关注沁园官方微信即可查询</p>
    <div class="tips">
      <p class="text"><text class="icon">●</text>长按二维码保存图片</p>
      <p class="text"><text class="icon">●</text>打开微信扫一扫</p>
      <p class="text"><text class="icon">●</text>选择相册中的二维码</p>
      <p class="text"><text class="icon">●</text>点击关注公众号</p>
    </div>
  </div>
  <div class="img-box">
    <img src="${f_ctxpath}/resources/src/images/qy_qrcode_new_channel.png${f_ver}" class="newQrCode">
  </div>
</div>
</body>
<script>
  wx.miniProgram.getEnv(function (res) {
    if(true===res.miniprogram){
      let fromUrl =document.getElementById('formUrl').value;
      // if (fromUrl.indexOf("myPdtList")!=-1 || fromUrl.indexOf("myOrderList")!=-1){
      document.getElementById("defaultChannelPage").style.display="none";
      document.getElementById("newChannelPage").style.display="inline";
      // }else {
      //   document.getElementById("newChannelPage").style.display="none";
      //   document.getElementById("defaultChannelPage").style.display="inline";
      // }
    }else{
      document.getElementById("newChannelPage").style.display="none";
      document.getElementById("defaultChannelPage").style.display="inline";
    }
  })
</script>
</html>