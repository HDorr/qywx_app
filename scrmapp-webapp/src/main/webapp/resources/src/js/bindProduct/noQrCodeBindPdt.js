(function($, window) {
    var htmlStr = "<div class=\"choseAreaBox\">" +
        "        <div class=\"backBar\">" +
        "            <span id=\"addrGoBack\"> < </span>" +
        "        </div>" +
        "        <ul class=\"headerUl\">" +
        "            <li id=\"headerLiFirst\">请选择</li>" +
        "            <li></li>" +
        "            <li></li>" +
        "        </ul>" +
        "        <div class=\"addressContainer\">" +
        "            <div id=\"provinceWraper\" class=\"wraper\">" +
        "                <ul>" +
        "                </ul>" +
        "            </div>" +
        "            <div id=\"cityWraper\" class=\"wraper\">" +
        "                <ul>" +
        "                </ul>" +
        "            </div>" +
        "            <div id=\"areaWraper\" class=\"wraper\">" +
        "                <ul>" +
        "                </ul>" +
        "            </div>" +
        "        </div>" +
        "    </div>";
    window.openid = "test";
    window.jdLikeAddrScrolls = {
        province: null,
        city: null,
        county: null
    }
    window.jdLikeAddrUrls = {};

    window.jdLikeShowContact = null;

    var addrInit = function(obj) {
        jdLikeAddrUrls = obj.urls;
        jdLikeShowContact = obj.showDomEl
        $("body").append(htmlStr);
        showAreaChose(obj.initDomEl)
    }

    function getAddr(type, id, cb) {
        var res = null;
        var url = " ";
        var data = {
            // "openId": openid,
        };
        switch (type) {
            case "province":
                url = jdLikeAddrUrls.queryProvince
                break;
            case "city":
                url = jdLikeAddrUrls.queryCity
                data["seriesId"] = id;
                break;
            case "area":
                url = jdLikeAddrUrls.queryCounty
                data["typeId"] = id;
                break;
        }
        $.ajax({
            type: "GET",
            data: data,
            url: url,
            success: function(data) {
                cb(data);
            }
        })
    }

    /**
     * [showChoseBox 将注册页面隐藏并显示选择地址页面]
     * @param  {Function} cb [回调函数]
     */
    function showChoseBox(cb) {
        cb();
        $(".container").fadeOut(200, function() {
            $(".choseAreaBox").show();
        })
    }

    /**
     * [renderAreaChose 渲染选择地址区域的li]
     * @param  {[type]} el    [要被插入li的ul]
     * @param  {[type]} data  [获得的省市级数据]
     * @param  {[type]} level [要设置的级别 province,city,county]
     */
    function renderAreaChose(el, data, level) {
        var str = "";
        var id = level + "Id";
        var value = level + "Name"
        for (var i = 0; i < data.length; i++) {
            str += ("<li data-" + id + "=" + data[i][id] + "  data-" + value + "=\"" + data[i][value] + "\">" + data[i][value] + "</li>");
        }
        el.html(str);
    }

    /**
     * [initChoseArea_hasDefault 省市级在有默认地址情况下的渲染和启动划屏特效]
     * @param  {[type]} $dom [父元素ul]
     * @param  {[type]} data [获得的省市级数据]
     * @param  {[type]} type [要设置的级别 province,city,county]
     */
    function initChoseArea_hasDefault($dom, data, type, chosedId) {
        renderAreaChose($dom, data, type);
        $dom.ready(function() {
            if (jdLikeAddrScrolls[type]) {
                if (type == "province") {
                    return
                } else {
                    jdLikeAddrScrolls[type].refresh();
                }
            } else {
                var timer = setTimeout(function() {
                    var scrollItem = $("#" + type + "Wraper")[0];
                    jdLikeAddrScrolls[type] = new IScroll(scrollItem, {
                        preventDefault: true,
                        click: true
                    });
                }, 300)
            }
            if (chosedId) {
                var chosedLi = $dom.children("li[data-" + type + "id=" + chosedId + "]");
                chosedLi.addClass('liChosed');
                setAddressTag(type, chosedLi.text(), chosedId);
            }
        })
    }

    function addressContainerTranslateX(position) {
        var box = $(".addressContainer");
        var headerUl = $(".headerUl");
        switch (position) {
            case "0":
                box.css("transform", "translateX(0)")
                headerUl.children('.headerLiActive').removeClass('headerLiActive').end()
                    .children("li:eq(0)").addClass('headerLiActive')
                break;
            case "1":
                box.css("transform", "translateX(-33.33333%)")
                headerUl.children('.headerLiActive').removeClass('headerLiActive').end()
                    .children("li:eq(1)").addClass('headerLiActive')
                break;
            case "2":
                box.css("transform", "translateX(-66.66666%)")
                headerUl.children('.headerLiActive').removeClass('headerLiActive').end()
                    .children("li:eq(2)").addClass('headerLiActive')
                break;
        }
    }


    function setAddressTag(type, text, id) {
        var id = id || 0;
        var lis = $(".headerUl>li")
        switch (type) {
            case "province":
                $(lis[0]).text(text)
                $(lis[0]).data("provinceid", id)
                $(lis[0]).data("provincename", text)
                break;
            case "city":
                $(lis[1]).text(text)
                $(lis[1]).data("cityid", id)
                $(lis[1]).data("cityname", text)
                break;
            case "area":
                $(lis[2]).text(text)
                $(lis[2]).data("areaid", id)
                $(lis[2]).data("areaname", text)
                break;
        }
    }

    function completeChose() {
        var headerUl = $(".headerUl");
        var provincedata = headerUl.children("li:eq(0)").data(),
            citydata = headerUl.children("li:eq(1)").data(),
            areadata = headerUl.children("li:eq(2)").data();
        if (!$.isEmptyObject(provincedata) && !$.isEmptyObject(citydata) && !$.isEmptyObject(areadata)) {
            var spanDom = $("#" + jdLikeShowContact);
            spanDom.data("provinceid", provincedata.provinceid)
            spanDom.data("provincename", provincedata.provincename)
            spanDom.data("cityid", citydata.cityid)
            spanDom.data("cityname", citydata.cityname)
            spanDom.data("areaid", areadata.areaid)
            spanDom.data("areaname", areadata.areaname)
            spanDom.text(provincedata.provincename + " " + citydata.cityname + " " + areadata.areaname)
            spanDom.val(areadata.areaname)
            $(".choseAreaBox").fadeOut(200, function() {
                $(".container").show()
            })
        } else {
            $(".choseAreaBox").fadeOut(200, function() {
                $(".container").show()
            })
        }
    }

    function clickEvents() {
        $(".headerUl").on('click', "li:not(:eq(2))", function(e) {
            if (!$(this).text()) {
                return;
            }
            $(this).siblings('li:not(#headerLiFirst)').empty()
            addressContainerTranslateX($(this).index() + "");
        })

        $("#provinceWraper>ul").on("click", "li", function(e) {
            $(this).siblings('.liChosed').removeClass('liChosed').end()
                .addClass('liChosed');
            var id = $(this).data("provinceid"),
                name = $(this).data("provincename");
            setAddressTag("province", name, id)
            getAddr("city", id, function(data) {
                setAddressTag("city", "请选择");
                var data = data.data;
                var $ul = $("#cityWraper>ul");
                initChoseArea_hasDefault($ul, data, "city")
                addressContainerTranslateX("1");
            })
        })

        $("#cityWraper>ul").on("click", "li", function(e) {
            $(this).siblings('.liChosed').removeClass('liChosed').end()
                .addClass('liChosed');
            var id = $(this).data("cityid"),
                name = $(this).data("cityname");
            setAddressTag("city", name, id)
            getAddr("area", id, function(data) {
                setAddressTag("area", "请选择");
                data = data.data;
                var $ul = $("#areaWraper>ul");
                initChoseArea_hasDefault($ul, data, "area")
                addressContainerTranslateX("2");
            })
        })

        $("#areaWraper>ul").on("click", "li", function(e) {
            $(this).siblings('.liChosed').removeClass('liChosed').end()
                .addClass('liChosed');
            var id = $(this).data("areaid"),
                name = $(this).data("areaname");
            setAddressTag("area", name, id)
            completeChose();
        })

        $("#addrGoBack").on("click", function() {
            completeChose();
        })
    }

    function showAreaChose(initDomEl) {
        $(".addressContainer").height($(".choseAreaBox").height() - 90)
        clickEvents()
        $("#" + initDomEl).click(function() {
            showChoseBox(areaSelectInit)

            function areaSelectInit() {
                getAddr("province", 0, function(data) {
                    if (data.returnCode == 0) {
                        alert(data.returnMsg)
                    } else {
                        var data = data.data;
                        var $ul = $("#provinceWraper>ul");
                        initChoseArea_hasDefault($ul, data, "province");
                    }
                })
            }
        })
    }

    window.jdLikeAddrInit = addrInit;
})(jQuery, window);


(function($, window) {
    $.toast.prototype.defaults.duration = 1000;
    var rootUrl = getRootPath();
    jdLikeAddrInit({
        urls: {
            queryProvince: rootUrl + "/scrmapp/consumer/product/series",
            queryCity: rootUrl + "/scrmapp/consumer/product/type",
            queryCounty: rootUrl + "/scrmapp/consumer/product/model"
        },
        initDomEl: "select_content",
        showDomEl: "show_content"
    })
    $(".qy-btn-all.nextStep").click(function() {
        var modal = $("#show_content").val();
        if (!modal) {
            $.toast("请选择一个型号", "cancel");
        } else {
            $.showLoading("查询中");
            $.ajax({
                url: rootUrl + "/scrmapp/consumer/product/query",
                type: "GET",
                data: {
                    modelName: modal
                },
                success: function(data) {
                    $.hideLoading()
                    setTimeout(function() {
                        if (data.returnCode === 1) {
                            $.toast("查询成功", function() {
                                var modelName = escape(data.data.modelName);
                                // window.location.href = rootUrl + "/scrmapp/consumer/product/query/page?modelName=" + modelName;
                                window.location.href = pageUrls.bindPdtMain + "?modelName=" + modal;
                            });
                        } else {
                            // $.toast(data.returnMsg, "cancel");
                            window.location.href = rootUrl + "/scrmapp/consumer/product/bindModel/fail/page"
                        }
                    }, 400)
                },
                error: function(data) {
                    $.hideLoading();
                    setTimeout(function() {
                        $.toast("网络错误", "cancel")
                    }, 400)
                }
            })
        }
    })
})(jQuery, window)