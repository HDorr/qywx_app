<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>注册有礼</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/regist/regist_page2.css${f_ver}">
    </head>
    <body>
        <div class="main_layer">
            <div class="container">
                <div class="main_logo">
                    <img src="${f_ctxpath}/resources/src/images/regist_mainLogo.png">
                </div>
                <div class="inputArea">
                    <div class="input-box">
                        <div class="icon-box">
                            <img src="${f_ctxpath}/resources/src/images/icons/name.png${f_ver}" alt="">
                        </div>
                        <input class="inputDetail" type="text" placeholder="请输入姓名">
                    </div>
                    <div class="input-box">
                        <div class="icon-box">
                            <img src="${f_ctxpath}/resources/src/images/icons/password.png${f_ver}" alt="">
                        </div>
                        <input class="inputDetail" type="password" placeholder="请输入密码">
                    </div>
                    <div class="input-box choseArea">
                        <div class="icon-box">
                            <img src="${f_ctxpath}/resources/src/images/icons/position.png${f_ver}" alt="">
                        </div>
                        <div class="pc-box inputDetail" id="select_contact">
                            <span data-cityid="610700" data-provinceid="610000" data-areaid="610702" id="show_contact">陕西省 汉中市 汉台区</span>
                            <!-- <span  id="show_contact"></span> -->
                        </div>
                        <i class="icon icon-down "></i>
                    </div>
                </div>
                <button class="bigBtn qyBlue">提交</button>
            </div>
            <div class="choseAreaBox">
                <div class="backBar">
                    <span id="addrGoBack"> < </span>
                </div>
                <ul class="headerUl">
                    <li id="headerLiFirst">请选择</li>
                    <li></li>
                    <li></li>
                </ul>
                <div class="addressContainer">
                    <div id="provinceWraper" class="wraper">
                        <ul>  
                        </ul>
                    </div>
                    <div id="cityWraper" class="wraper">
                        <ul>
                        </ul>
                    </div>
                    <div id="areaWraper" class="wraper">
                        <ul>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/node_modules/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/node_modules/iscroll/build/iscroll.js"></script>
        <script src="${f_ctxpath}/resources/src/js/regist/regist_page2.js${f_ver}"></script>
    </body>
</html>