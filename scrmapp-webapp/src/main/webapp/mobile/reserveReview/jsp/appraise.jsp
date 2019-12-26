<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>${appraiseTypeName}</title>
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.new.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reserveReview/appraise.css${f_ver}">
</head>
<body>
<span id="relative_path" data-relativepath="${f_ctxpath}" hidden="hidden"></span>
<span id="hidden_question" data-appraisetype="${appraiseType}" data-orderscode="${ordersCode}" hidden="hidden"></span>
<header id="header_question">
    <%--添加提问--%>
</header>
<section>
    <div class="t-container t-body">
        <div class="t-row t-row-1">
            <span class="t-label">服务礼仪：</span>
            <div class="t-img t-star-1" attitude="0">
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
            </div>
            <span class="t-show"></span>
        </div>
        <div class="t-row t-row-2">
            <p>工程师仪容仪表整洁,服务态度礼貌,穿着工装并出示工牌</p>
        </div>
    </div>
    <div class="t-container t-body">
        <div class="t-row t-row-1">
            <span class="t-label">专业技能：</span>
            <div class="t-img t-star-2" profession="0">
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
                <img src="${f_ctxpath}/resources/src/images/appraise/star_empty.png"/>
            </div>
            <span class="t-show"></span>
        </div>
        <div class="t-row t-row-2">
            <p>工程师操作专业且井然有序,讲解使用及保养知识</p>
        </div>
    </div>
    <div class="t-container t-body">
        <div class=" t-row-1">
            <p><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">您是否愿意因为我们的服务将沁园推荐给您的亲友？</font></font></p>
        </div>
        <div class="t-row-3">
            <div class="t-div t-star-3" recommended="0">
                <div>0</div>
                <div>1</div>
                <div>2</div>
                <div>3</div>
                <div>4</div>
                <div>5</div>
                <div>6</div>
                <div>7</div>
                <div>8</div>
                <div>9</div>
                <div>10</div>
            </div>
        </div>
        <div class="t-box">
            <div class="t-box-img">
                <img class="on-recommended" src="./清洗保养评价_files/recommended.png">
                肯定不推荐
            </div>
            <div class="t-box-img">
                <img src="./清洗保养评价_files/recommended.png">
                肯定推荐
            </div>
            <!-- <span class="t-show"></span> -->
        </div>
    </div>
    <div class="t-container t-content-area">
        <textarea placeholder="亲，您对我们的服务还满意吗？" maxlength="80" class="t-text"></textarea>
        <div class="t-limit">还能输入<span>80</span>个字</div>
    </div>
</section>
<footer>
    <div class="t-footer">
        <div class="t-submit">提交评价</div>
    </div>
</footer>
<script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
<%--<script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>--%>
<script src="${f_ctxpath}/resources/src/js/reviewReserve/alert.js${f_ver}"></script>
<script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
<script src="${f_ctxpath}/resources/src/js/reviewReserve/appraise.js${f_ver}"></script>
</body>
</html>