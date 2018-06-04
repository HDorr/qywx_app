/**
 * Created by Administrator on 2017/4/17 0017.
 */

var glData = {
    type: $("#typeInput").val(),
    pageNum: 1,
    isLastPage: false,
    rootUrl: getRootPath()
};

//获取根url
function getRootPath() {
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);
};

;
(function($, window) {

    //点击加载更多
    $("#getMoreBtn").click(function() {
        if (glData.isLastPage) {
            $(this).text("没有更多公告");
            return;
        } else {
            glData.pageNum++;
            getAjaxToRender(glData.pageNum, "", glData.type, callback)
        }
    });
    //搜索
    $("#searchBtn").click(function() {
        glData.pageNum = 1;
        var text = $("#searchByTitle").val().trim();
        getAjaxToRender(glData.pageNum, text, glData.type, callback, true)
    });



    getAjaxToRender(glData.pageNum, "", glData.type, callback);

    function callback(data, isSearch) {
        var data = data.data.data;
        if (data.list.length == 0) {
            $("#getMoreBtn").text("没有获取到相应公告");
            return;
        };
        if (data.isLastPage) {
            $("#getMoreBtn").text("没有更多公告");
            glData.isLastPage = true;
        }
        var tplStr = "";
        $.each(data.list, function(i, v) {
            v.updateTime = new Date(v.updateTime).toLocaleString();
            tplStr += '<li class="listCon activeList">' +
                '<a href="' + v.url + '">' +
                '      <span class="noticeTitle">' + v.noticeTitle + '</span>' +
                '      <span class="rightArrow">></span>' +
                '      <span class="noticeTime">' + v.updateTime + '</span>' +
                '<a/>' +
                '</li>';
        });
        if (isSearch) {
            $("#noticeListContainer").html(tplStr);
        } else {
            $("#noticeListContainer").append(tplStr);
        }

    }

    function getAjaxToRender(pageNum, noticeTitle, type, cb, isSearch) {
        var isSearchFlag = isSearch || "";
        var url = "";
        var data = {};
        switch (type) {
            case "1":
                url = glData.rootUrl + "/qyhNotice/getNoticeListByType";
                data = {
                    "assortmentId": $("#assortmentIdInput").val(),
                    "noticeTitle": noticeTitle,
                    "pageNum": pageNum,
                    "pageSize": 20
                };
                break;
            case "2":
                url = glData.rootUrl + "/qyhNotice/getAllNoticeList";
                data = {
                    "pageNum": pageNum,
                    "pageSize": 20,
                    "noticeTitle": noticeTitle
                };
                break;
            case "3":
                url = glData.rootUrl + "/qyhNotice/getMyCollectionNotice";
                data = {
                    "userId": $("#userIdInput").val(),
                    "pageNum": pageNum,
                    "pageSize": 20,
                    "noticeTitle": noticeTitle
                };
                break;
        }
        $.ajax({
            "url": url,
            "type": "GET",
            "data": data,
            success: function(data) {
                if (data.errorCode == 0) {
                    cb(data, isSearchFlag)
                } else {
                    alert("error")
                }
            }
        })
    }

})(jQuery, window);