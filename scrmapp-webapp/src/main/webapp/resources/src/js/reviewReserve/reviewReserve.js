var glData = {
    sendData: {
        ordersCode: getUrlParam("ordersCode") /*"WX17041802343915"*/ , //受理单号
        attitude: 5, // 态度
        profession: 5, // 专业度
        integrity: 5, // 诚信度
        recommend: 5, // 推荐程度
        content: "", // 评价内容
    }
};



(function($, window) {
    ajaxInfo(renderReview)
})(jQuery, window)


function ajaxInfo(callback) {
    $.showLoading();
    $.ajax({
        url: queryUrls.getReserveData,
        type: "GET",
        data: {
            "ordersCode": glData.sendData.ordersCode
        },
        success: function(data) {
            if (data.returnCode == 1) {
                if (callback) {
                    $.hideLoading();
                    if (data.data) {
                        callback(data.data)
                    }
                }
            } else {
                $.hideLoading();
                $.toast(data.returnMsg, "cancel")
            }
        },
        error: function(data) {
            $.hideLoading();
            $.toast("网络错误", "cancel")
        }
    })
}

function renderReview(data) {
    data.attNum = Math.round(data.attitude);
    data.proNum = Math.round(data.profession);
    data.intNum = Math.round(data.integrity);

    $(".personImg.pull-left").css("backgroundImage", "url('" + data.imageUrl + "')");
    $(".name").text(data.name + " (" + data.position + " )");
    $(".tell").text(data.mobile);

    //服务态度
    $(".attitudeRate").text(data.attitude);
    $(".attitudeRateStarBox").css("width", data.attitude * 20 + "%");

    //专业程度
    $(".professionRate").text(data.profession);
    $(".professionRateStarBox").css("width", data.profession * 20 + "%");

    //诚信情况
    $(".integrityRate").text(data.integrity);
    $(".integrityRateStarBox").css("width", data.integrity * 20 + "%");

    var strAtt = "",
        strPro = "",
        strInt = "";

    for (var i = 0; i < 5; i++) {
        strAtt += '<span class="active"></span>'
        strPro += '<span class="active"></span>'
        strInt += '<span class="active"></span>'
    }

    $(".chooseStar[data-id=attitude]").html(strAtt);
    $(".chooseStar[data-id=profession]").html(strPro);
    $(".chooseStar[data-id=integrity]").html(strInt);
    changeStar()
}
//加星减星
function changeStar() {
    $(".chooseStar").click(function(e) {
        var spanEl = $(e.target);
        if (spanEl.hasClass('active')) {
            spanEl.removeClass("active").nextAll('span').removeClass('active');
        } else {
            spanEl.addClass("active").prevAll('span').addClass('active');
        }
        var starLen = $(this).children(".active").length;
        glData.sendData[$(this).data("id")] = starLen;
    })
}

function submitReview() {
    $.showLoading("数据提交中")
    glData.sendData.content = $("#reviewContent").val();
    $.ajax({
        url: queryUrls.reservationAppraisal,
        data: glData.sendData,
        type: "POST",
        success: function(data) {
            if (data.returnCode == 1) {
                $.hideLoading();
                praiseSuccess();
            } else {
                $.hideLoading();
                $.toast(data.returnMsg, "cancel")
            }
        },
        error: function(data) {
            $.hideLoading();
            $.toast("网络错误", "cancel")
        }
    })
}


function praiseSuccess() {
    var htmlStr = ' <div class="imgBox" style="background-image: url(\'/scrmapp/resources/src/images/icons/smill.png\')"></div>' +
        '            <p>感谢您对这次服务进行评价！</p>' +
        '            <p>感谢您评价完成！</p>' +
        '            <button id="returnToOrderList" class="qy-btn-400">' +
        '                返回我的订单列表' +
        '            </button>';
    $(".main_layer").css("padding-top", "3.16rem").html(htmlStr);

    $("#returnToOrderList").click(function() {
        location.href = pageUrls.orderList;
    })
}