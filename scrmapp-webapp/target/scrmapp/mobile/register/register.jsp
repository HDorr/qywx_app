<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>注册</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/regist/regist_page.css${f_ver}">
</head>

<body>
<input type="hidden" value="${url}" id="registUrl" />
    <div class="main_layer">
        <div class="container">
            <div class="main_logo">
                <img src="${data.headimgurl}">
            </div>
            <div class="inputArea">
                <section id="step1Box">
                    <div class="input-box">
                        <div class="icon-box">
                            <img src="${f_ctxpath}/resources/src/images/icons/phone_1.png${f_ver}" alt="">
                        </div>
                        <input type="tel" class="inputDetail" id="phone_number" placeholder="请输入手机号码">
                        <input type="submit" class="send_code" id="sendCfCode" value="发送验证码">
                    </div>
                    <div class="input-box zeroMgBottom">
                        <div class="icon-box">
                            <img src="${f_ctxpath}/resources/src/images/icons/id_code.png${f_ver}" alt="">
                        </div>
                        <input type="number" class="inputDetail" id="confirmCode" placeholder="请输入验证码">
                    </div>
                    <div class="warn-box">
                        <p id="warn_text"></p>
                    </div>
                    <button class="bigBtn qyBlue" id="nextStepBtn">下一步</button>
                    <div class="private_contact">已阅读并同意<a href="${f_ctxpath}/scrmapp/consumer/user/filter/register/private_contact" style="color: #aaa"><u>《沁园隐私政策》</u></a></div>
                </section>

                <section id="step2Box" class="hideStep">
                    <%--<div class="input-box">
                        <div class="icon-box">
                            <img src="${f_ctxpath}/resources/src/images/icons/name.png" alt="">
                        </div>
                        <input class="inputDetail" type="text" id="custmName" placeholder="请输入姓名">
                    </div>--%>
                        <div class="input-box">
                            <div class="icon-box">
                                <img src="${f_ctxpath}/resources/src/images/icons/password.png${f_ver}" alt="">
                            </div>
                            <input class="inputDetail" id="custmPsw" type="password" placeholder="请输入密码">
                        </div>
                        <div class="input-box choseArea">
                            <div class="icon-box">
                                <img src="${f_ctxpath}/resources/src/images/icons/position.png${f_ver}" alt="">
                            </div>
                            <div class="pc-box inputDetail" id="select_contact">
                                <span data-cityid="" data-provinceid="" data-areaid="" id="show_contact">请选择地址</span>
                                <i class="icon icon-down "></i>
                            </div>
                        </div>
                        <button class="bigBtn qyBlue" id="totalSubmit">提交</button>

                </section>
            </div>
        </div>

        <div class="loadEffect">
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
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
    <input id="getCusInfo" type="hidden" data-openid="${data.token}" />
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/iscroll/build/iscroll.js"></script>
    <script src="${f_ctxpath}/resources/src/js/regist/regist_page1.js${f_ver}"></script>
</body>

</html>