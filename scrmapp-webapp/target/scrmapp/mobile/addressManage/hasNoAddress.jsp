<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>地址列表</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <style>
            .address_main_layer{
                background-color: #f4f4f4;
                width: 100%;
                min-height: 100%;
                top:0;
                position: relative;
                max-width: 750px;
                margin: 0 auto;
                padding-bottom:2.666667rem;
            }
            .hasNoAddrBox{
                width: 100%;
                height: 100%;
                position:absolute;
                top:0;
                left:0;
            }
            .hasNoAddrBox .noAddrImg{
                margin-top:6.8rem;
                margin-left:auto;
                margin-right:auto;
                width:5.413333rem;
                height:3.92rem;
            }
            .hasNoAddrBox .noAddrImg>img{
                display:block;
                width: 100%;
                height: 100%;
            }
            .hasNoAddrBox .noAddrText{
                width: 100%;
                margin-top:0.933333rem;
                font-size:0.693333rem;
                text-align:center;
            }
            .hasNoAddrBox .noAddrBtn{
                display: block;
                width:10.666667rem;
                height:1.866667rem;
                margin-top:1.066667rem;
                margin-left:auto;
                margin-right:auto;
                font-size:0.693333rem;
                color:#fff;
            }
        </style>
    </head>
    <body>
        <div class="address_main_layer">
            <div class="hasNoAddrBox">
                <div class="noAddrImg">
                    <img src="${f_ctxpath}/resources/images/hasNoAddressYet.png${f_ver}">
                </div>
                <p class="noAddrText">您目前还没有添加地址哟，现在来添加新地址吧</p>
                <button class="noAddrBtn qyBlue">新增地址</button>
            </div>
            
        </div>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    </body>
</html>