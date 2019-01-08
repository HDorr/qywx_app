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
    *{
      margin: 0;
      padding: 0;
    }
    .title{
      text-align: left;
      width: 216px;
      margin: 20px 0;
      font-weight: bold;
      letter-spacing: 2px;
      margin: 20px auto;
    }
    .tips>p{
      margin-bottom: 20px;
    }
    .center{
      text-align: left;
      margin: 0 auto;
      width: 160px;
    }
    .img-box{
      text-align: center;
    }

  </style>
</head>
<body>
<input type="hidden" value="${url}" id="formUrl" />
<div class="main-layer" hidden="" id="defaultChannelPage">
  <p class="text" id="qr">您还不是粉丝哦，长按钮下方二维码进行关注</p >
  <div class="img-box">
    <img src="${f_ctxpath}/resources/src/images/qy_QR_code.png${f_ver}" class="qrCode"/>
  </div>
</div>
<div class="main-layer-new" id="newChannelPage">
  <div class="tip">
    <p class="title" >关注沁园官方微信即可查询</p >
    <div class="tips">
      <p class="text center"><text class="icon">●</text>长按二维码保存图片</p >
      <p class="text marginLeft"><text class="icon">●</text>打开微信扫一扫</p >
      <p class="text marginLeft"><text class="icon">●</text>选择相册中的二维码</p >
      <p class="text marginLeft"><text class="icon">●</text>点击关注公众号</p >
    </div>
  </div>
  <div class="img-box">
    <img src="${f_ctxpath}/resources/src/images/qy_qrcode_new_channel.png${f_ver}" class="newQrCode"/>
  </div>
</div>
</body>

<script type="text/javascript">
  let centerLeft = document.getElementsByClassName("center")[0];
  let marginLefs = document.getElementsByClassName("marginLeft");
  let title = document.getElementsByClassName("title")[0];
  for(let i = 0;i<marginLefs.length;i++){
    alert(centerLeft.getBoundingClientRect().left<html>
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
  *{
    margin: 0;
    padding: 0;
  }
  .title{
    text-align: left;
    width: 216px;
    margin: 20px 0;
    font-weight: bold;
    letter-spacing: 2px;
    margin: 20px auto;
  }
  .tips>p{
    margin-bottom: 20px;
  }
  .center{
    text-align: left;
    margin: 0 auto;
    width: 160px;
  }
  .img-box{
    text-align: center;
  }

</style>
</head>
<body>
<input type="hidden" value="${url}" id="formUrl" />
<div class="main-layer" hidden="" id="defaultChannelPage">
  <p class="text" id="qr">您还不是粉丝哦，长按钮下方二维码进行关注</p >
  <div class="img-box">
    <img src="${f_ctxpath}/resources/src/images/qy_QR_code.png${f_ver}" class="qrCode"/>
  </div>
</div>
<div class="main-layer-new" id="newChannelPage">
  <div class="tip">
    <p class="title" >关注沁园官方微信即可查询</p >
    <div class="tips">
      <p class="text center"><text class="icon">●</text>长按二维码保存图片</p >
      <p class="text marginLeft"><text class="icon">●</text>打开微信扫一扫</p >
      <p class="text marginLeft"><text class="icon">●</text>选择相册中的二维码</p >
      <p class="text marginLeft"><text class="icon">●</text>点击关注公众号</p >
    </div>
  </div>
  <div class="img-box">
    <img src="${f_ctxpath}/resources/src/images/qy_qrcode_new_channel.png${f_ver}" class="newQrCode"/>
  </div>
</div>
</body>

<script type="text/javascript">
  let centerLeft = document.getElementsByClassName("center")[0];
  let marginLefs = document.getElementsByClassName("marginLeft");
  let title = document.getElementsByClassName("title")[0];
  for(let i = 0;i<marginLefs.length;i++){
    marginLefs[i].style.marginLeft = centerLeft.getBoundingClientRect().left;
  }
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
</html>);
    marginLefs[i].style.marginLeft = centerLeft.getBoundingClientRect().left;
  }
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