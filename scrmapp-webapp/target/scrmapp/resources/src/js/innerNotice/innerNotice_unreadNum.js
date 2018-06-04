/**
 * Created by Administrator on 2017/4/17 0017.
 */
var glData = {
    noticeId: $("#noticeIdInput").val(),
    userId: $("#userIdInput").val(),
    pageNum: 1,
    isLastPage: false
};
 var rootUrl = getRootPath();
;
(function($, windoe) {

    ajaxFunc(callBack);

    $(".noData.seeMore").click(function(e) {
        if (glData.isLastPage) {
            $(this).text("已经到底啦");
            return;
        }
        glData.pageNum++;
        ajaxFunc(callBack);
    });



    function ajaxFunc(cb) {
        $.ajax({
            url: "/scrmapp/qyhNotice/getUnReadUserByNotice",
            type: "GET",
            data: {
                noticeId: glData.noticeId,
                pageSize: 20,
                pageNum: glData.pageNum,
                userId: glData.userId
            },
            success: function(data) {
                if (data.errorCode == 0) {
                    if (data.data.isLastPage) {
                        glData.isLastPage = true;
                    }
                    cb(data);
                }
            }
        })
    }

    function callBack(data) {
        if (glData.isLastPage) {
            $(this).text("已经到底啦");
        }
        var str = "";
        $.each(data.data.list, function(i, v) {
            v.avatar = v.avatar || (rootUrl + "/resources/images/defaultUserImg.jpg")
            str += ' <li class="personListCon">' +
                '        <div class="personImgBox" style="background-image: url(\'' + v.avatar + '\')"></div>' +
                '        <span class="nameBox">' + v.name + '</span>' +
                '  </li>';
        });
        $(".personList").append(str);
    }
})(jQuery, window);