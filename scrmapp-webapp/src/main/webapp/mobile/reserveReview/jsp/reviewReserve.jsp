<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>预约评价</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css${f_ver}">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reserveReview/reserveReview.css${f_ver}">
    </head>
    <body>
        <div class="main_layer ">
            <div class="reviewTitle">一、请您对师傅本次的服务进行评价！</div>
            <div class="workerInfoWrap">
                <div class="workerInfo">
                    <div class="personImg pull-left"></div>
                    <div class="personInfo pull-left">
                        <span  class="name">张三 服务工程师</span>
                        <span class="tel">18712345678</span>
                    </div>
                    <div class="starLevel pull-right">
                        <div class="top">
                            <span class="pull-left">服务态度：</span>
                            <span class="pull-left attitudeRate">5</span>
                            <div class="starBox pull-left ">
                                <p class="attitudeRateStarBox"></p>
                            </div>
                        </div>
                        <div class="center">
                            <span class="pull-left">专业程度：</span>
                            <span class="pull-left professionRate">5</span>
                            <div class="starBox pull-left ">
                                <p class="professionRateStarBox"></p>
                            </div>
                        </div>
                        <div class="bottom">
                            <span class="pull-left">诚信情况：</span>
                            <span class="pull-left integrityRate">5</span>
                            <div class="starBox pull-left ">
                               <p class="integrityRateStarBox"></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="reviewOperateWrap">
                <div class="reviewOperate">
                    <div class="top">
                        <div class="up">
                            <span class="pull-left">服务态度：</span>
                            <div class="chooseStar pull-left" data-id="attitude">
                            </div>
                        </div>
                        <div class="down">
                            师傅是否守时，是否礼貌，着装是否统一，施工时是否注意卫生！
                        </div>
                    </div>
                    <div class="center">
                        <div class="up">
                            <span class="pull-left">专业度：</span>
                            <div class="chooseStar pull-left" data-id="profession">
                            </div>
                        </div>
                        <div class="down">
                            师傅操作是否标准，是否讲解使用及保养知识！
                        </div>
                    </div>
                    <div class="bottom">
                        <div class="up">
                            <span class="pull-left">诚信情况：</span>
                            <div class="chooseStar pull-left" data-id="integrity">
                            </div>
                        </div>
                        <div class="down">
                            师傅是否出示收费标准，是否让您在服务卡上签字，是否告知服务监督热线！
                        </div>
                    </div>
                </div>
                <div class="reviewTxt">
                    <span class="pull-left">其&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;他：</span>
                        <textarea class="textBox pull-right"  name="" id="reviewContent" cols="30" rows="5"></textarea>                
                </div>
                <div class="reviewTitle">二、您是否愿意向亲朋好友推荐沁园产品！</div>
                <div class="starWrap">
                    <div class="starCon">
                        <span class="pull-left">推荐指数：</span>
                        <div class="chooseStar pull-left" data-id="recommend">
                            <span  class="active"></span>
                            <span  class="active"></span>
                            <span  class="active"></span>
                            <span  class="active"></span>
                            <span  class="active"></span>
                        </div>
                    </div>
                </div>
            </div>

            <footer class="reviewFooter">
                <button onclick="submitReview()" class="qy-btn-400">提交评价</button>
            </footer>
        </div>
        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/reviewReserve/reviewReserve.js${f_ver}"></script>
    </body>
</html>