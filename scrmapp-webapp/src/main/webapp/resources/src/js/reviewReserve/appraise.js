$(function () {
    //动态添加节点
    addNode();
    //添加事件
    addEvent();
})

//添加节点
function addNode() {
    var header_question = $("#header_question");
    header_question.html("");
    var appraiseType = $("#hidden_question").data("appraisetype");
    if (appraiseType !== 2) {
        header_question.html(
            "<div class=\"t-container t-header\">\n" +
            "        <div class=\"t-question\">\n" +
            "            <span>工程师是否与您及时预约并准时上门？</span>\n" +
            "        </div>\n" +
            "        <div class=\"t-select\" order=\"1\">\n" +
            "            <span class=\"t-radio t-checked\" choose=\"1\">是</span>\n" +
            "            <span class=\"t-radio\" choose=\"0\">否</span>\n" +
            "        </div>\n" +
            "    </div>");
    } else {
        header_question.html(
            "<div class=\"t-container t-header\">\n" +
            "        <div class=\"t-question\">\n" +
            "            <span>您报修的故障是否已经完全排除？</span>\n" +
            "        </div>\n" +
            "        <div class=\"t-select\" repair=\"1\">\n" +
            "            <span class=\"t-radio t-checked\" choose=\"1\">是</span>\n" +
            "            <span class=\"t-radio\" choose=\"0\">否</span>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div class=\"t-container t-header\">\n" +
            "        <div class=\"t-question\">\n" +
            "            <span>工程师是否与您及时预约并准时上门？</span>\n" +
            "        </div>\n" +
            "        <div class=\"t-select\" order=\"1\">\n" +
            "            <span class=\"t-radio t-checked\" choose=\"1\">是</span>\n" +
            "            <span class=\"t-radio\" choose=\"0\">否</span>\n" +
            "        </div>\n" +
            "    </div>");
    }
}

//添加事件
function addEvent() {
    //给单选框添加事件
    $(".t-select").on("click", function (e) {
        var t = $(e.target);
        if (t.hasClass("t-radio")) {
            if (t.hasClass("t-checked")) {
                t.siblings(".t-radio").addClass("t-checked");
                t.removeClass("t-checked");
            } else {
                t.siblings(".t-radio").removeClass("t-checked");
                t.addClass("t-checked");
            }
        }
    })


    //评价级别事件
    $(".t-img img").on("click", function () {
        var src = $(this).attr("src");
        src = src.substr(0, src.indexOf("/star_"));
        var star_full = src + "/star_full.png";
        var star_empty = src + "/star_empty.png";
        var star = $(this).parent();
        var imgs = star.find("img");
        var length = imgs.index(this) + 1;
        for (i = length; i < 5; i++) {
            imgs.eq(i).attr("src", star_empty);
        }
        for (i = 0; i < length; i++) {
            imgs.eq(i).attr("src", star_full);
        }
        if (star.hasClass("t-star-1")) {
            star.attr("attitude", length);
        } else if (star.hasClass("t-star-2")) {
            star.attr("profession", length);
        }
        var show = "";
        switch (length) {
            case 1:
                show = "非常不满";
                break;
            case 2:
                show = "不满意";
                break;
            case 3:
                show = "一般";
                break;
            case 4:
                show = "满意";
                break;
            case 5:
                show = "非常满意";
                break;
        }
        star.next().text(show);
    })

    //文本域输入备注事件
    $(".t-text").on("input", function () {
        var maxlen = $(this).attr("maxlength");
        var currentLen = $(this).val().length;
        $(".t-limit span").text(maxlen - currentLen);
    })

    //提交数据
    $(".t-submit").on("click", function () {
        submitData();
    })

}

//提交数据
function submitData() {
    //获得服务礼仪和专业度值
    var attitude = $(".t-star-1").attr("attitude");
    var profession = $(".t-star-2").attr("profession");
    if (attitude == 0) {
        alert("请您为服务礼仪点亮星星")
        return;
    }
    if (profession == 0) {
        alert("请您为专业技能点亮星星")
        return;
    }

    //获得问题的选择值
    var questions = $(".t-checked");
    var map = new Map();
    for (i = 0; i < questions.length; i++) {
        var q = $(questions[i]);
        var select = q.parent();
        if (select.attr("repair") == 1) {
            map.set("repair", q.attr("choose"));
        }
        if (select.attr("order") == 1) {
            map.set("order", q.attr("choose"));
        }
    }

    //获得文本域内容
    var content = $(".t-text").val();

    //获得订单号和订单类型
    var hidden_question = $("#hidden_question");
    var orderCode = hidden_question.data("orderscode");
    var appraiseType = hidden_question.data("appraisetype");

    //参数
    var params = {
        "attitude": attitude,
        "profession": profession,
        "repair": !map.get("repair") ? "" : map.get("repair"),
        "order": map.get("order"),
        "content": content,
        "orderCode": orderCode,
        "appraiseType": appraiseType
    }

    $.ajax({
        type: "POST",
        url: queryUrls.newAppraisal,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(params),
        success: function (data) {
            console.log(data.data);
        }
    });
}