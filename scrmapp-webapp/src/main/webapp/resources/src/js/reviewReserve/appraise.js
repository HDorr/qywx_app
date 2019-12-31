const appraiseType = $("#hidden_question").data("appraisetype");
const relativePath = $("#relative_path").data("relativepath");

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
    if (appraiseType === 2) {
        header_question.append(
            "<div class=\"t-container t-header\">\n" +
            "        <div class=\"t-question\">\n" +
            "            <span>您报修的故障是否已经完全排除？</span>\n" +
            "        </div>\n" +
            "        <div class=\"t-select\" repair=\"1\">\n" +
            "            <span class=\"t-radio\" choose=\"1\" id='t-repair-choosed'>是</span>\n" +
            "            <span class=\"t-radio\" choose=\"0\" id='t-repair-unchoosed'>否</span>\n" +
            "        </div>\n" +
            "    </div>\n"
        );
    }
    header_question.append(
        "<div class=\"t-container t-header\">\n" +
        "        <div class=\"t-question\">\n" +
        "            <span>工程师是否与您及时预约并准时上门？</span>\n" +
        "        </div>\n" +
        "        <div class=\"t-select\" order=\"1\">\n" +
        "            <span class=\"t-radio\" choose=\"1\" id='t-order-choosed'>是</span>\n" +
        "            <span class=\"t-radio\" choose=\"0\" id='t-order-unchoosed'>否</span>\n" +
        "        </div>\n" +
        "    </div>");
}

//添加事件
function addEvent() {
    //给单选框添加事件
    $(".t-select").on("click", function (e) {
        var t = $(e.target);
        if (t.hasClass("t-radio")) {
            if (!t.hasClass("t-checked")) {
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

    //服务推荐亲友
    $('.t-star-3 div').on('click', function () {
        $(".t-star-3").attr("recommended",$(this).index())
        $(this).addClass('select-div').siblings().removeClass();
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
    var recommended = $(".t-star-3").attr("recommended");
    if (appraiseType === 2) {
        if (!$("#t-repair-choosed").hasClass("t-checked") && !$("#t-repair-unchoosed").hasClass("t-checked")) {
            $.alertNew("报修故障是否已排除?");
            return;
        }
    }
    if (!$("#t-order-choosed").hasClass("t-checked") && !$("#t-order-unchoosed").hasClass("t-checked")) {
        $.alertNew("请您对我们的服务时效进行评分");
        return;
    }
    if (attitude == 0) {
        $.alertNew("请您对我们的专业技能进行评分");
        return;
    }
    if (profession == 0) {
        $.alertNew("请您对我们的服务礼仪进行评分");
        return;
    }
    if (recommended == 11) {
        $.alertNew("请您对我们的服务进行推荐值评分");
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

    //参数
    var params = {
        "attitude": attitude,
        "profession": profession,
        "recommended": recommended,
        "repair": !map.get("repair") ? "" : map.get("repair"),
        "order": map.get("order"),
        "content": content,
        "orderCode": orderCode,
        "appraiseType": appraiseType
    }
    $.showLoading("数据提交中");
    $.ajax({
        type: "POST",
        url: queryUrls.newAppraisal,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(params),
        success: function (data) {
            if (data.returnCode == 1) {
                $.hideLoading();
                praiseSuccess(relativePath);
            } else {
                $.hideLoading();
                $.toast(data.returnMsg, "cancel")
            }
        },
        error: function (data) {
            $.hideLoading();
            $.toast("网络错误", "cancel")
        }
    });
}

function praiseSuccess(src) {
    var htmlStr = "<div class=\"t-success\">\n" +
        "    <div class=\"t-success-img\">\n" +
        "        <img src=\""+src+"/resources/src/images/icons/smill_new.png"+"\">\n" +
        "    </div>\n" +
        "    <p>感谢您对这次服务进行评价！</p>\n" +
        "    <p>感谢您评价完成！</p>\n" +
        "    <div id=\"t-back\" class=\"t-back\">返回我的订单列表</div>\n" +
        "</div>"
    $("body").html(htmlStr);
    $("#t-back").click(function () {
        window.location.replace(pageUrls.orderList);
    })
}