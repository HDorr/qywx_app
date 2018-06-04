(function($, window) {
    $(function() {
        showAreaChose();
    })
})(jQuery, window)

var scrolls = {
    province: null,
    city: null,
    county: null
}

var urls = {
    queryProvince: "http://dev.99baby.cn/scrmapp/scrmapp/consumer/province/get?tokenId=gh_2a05d01977b9&openId=okt_qjjpS2fweRuGuppJbSkmo4vY",
    queryCity: "http://dev.99baby.cn/scrmapp/scrmapp/consumer/city/get?tokenId=gh_2a05d01977b9&openId=okt_qjjpS2fweRuGuppJbSkmo4vY&provinceId=",
    queryCounty: "http://dev.99baby.cn/scrmapp/scrmapp/consumer/area/get?tokenId=gh_2a05d01977b9&openId=okt_qjjpS2fweRuGuppJbSkmo4vY&cityId="
}

function getAddr(type, id, cb) {
    var res = null;
    var url = " ";
    var data = {
        "tokenId": "gh_2a05d01977b9",
        "openId": "okt_qjjpS2fweRuGuppJbSkmo4vY",
    };
    switch (type) {
        case "province":
            url = "http://dev.99baby.cn/scrmapp/scrmapp/consumer/province/get"
            break;
        case "city":
            url = "http://dev.99baby.cn/scrmapp/scrmapp/consumer/city/get"
            data["provinceId"] = id;

            break;
        case "area":
            url = "http://dev.99baby.cn/scrmapp/scrmapp/consumer/area/get"
            data["cityId"] = id;
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
    $(".container").fadeOut(200, function() {
        $(".choseAreaBox").show(cb);
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
    var name = level + "Name";
    var value = level + "Name"
    for (var i = 0; i < data.length; i++) {
        str += ("<li data-" + id + "=" + data[i][id] + "  data-" + name + "=" + data[i][value] + ">" + data[i][value] + "</li>");
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
    scrolls[type] = new IScroll("#" + type + "Wraper", {
        preventDefault: false
    });
    if (chosedId) {
        var chosedLi = $dom.children("li[data-" + type + "id=" + chosedId + "]");
        chosedLi.addClass('liChosed');
        setAddressTag(type, chosedLi.text(), chosedId);
    }
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
    if (provincedata && citydata && areadata) {
        var spanDom = $("#show_contact");
        spanDom.data("provinceid", provincedata.provinceid)
        spanDom.data("provincename", provincedata.provincename)
        spanDom.data("cityid", citydata.cityid)
        spanDom.data("cityname", citydata.cityname)
        spanDom.data("areaid", areadata.areaid)
        spanDom.data("areaname", areadata.areaname)
        spanDom.text(provincedata.provincename + " " + citydata.cityname + " " + areadata.areaname)
        $(".choseAreaBox").fadeOut(200, function() {
            $(".container").show()
        })
    } else {
        $(".choseAreaBox").fadeOut(200, function() {
            $(".container").show()
        })
    }
}


function showAreaChose() {
    $("#select_contact").click(function() {
        showChoseBox(areaSelectInit)
        $(".addressContainer").height($(".choseAreaBox").height() - 90)

        function areaSelectInit() {
            var addrSpan = $("#show_contact");
            var provinceId = addrSpan.data("provinceid"),
                cityId = addrSpan.data("cityid"),
                areaId = addrSpan.data("areaid");
            //如果有默认地址
            if (provinceId && cityId && areaId) {
                //渲染省级数据
                getAddr("province", 0, function(data) {
                    var data = data.data;
                    var $ul = $("#provinceWraper>ul");
                    initChoseArea_hasDefault($ul, data, "province", provinceId)
                        //渲染市级数据
                    getAddr("city", provinceId, function(data) {
                        var data = data.data;
                        var $ul = $("#cityWraper>ul");
                        initChoseArea_hasDefault($ul, data, "city", cityId)
                            //渲染区数据
                        getAddr("area", cityId, function(data) {
                            var data = data.data;
                            var $ul = $("#areaWraper>ul");
                            initChoseArea_hasDefault($ul, data, "area", areaId)
                            addressContainerTranslateX("2");
                        })
                    })
                })
            }
            //如果没有默认地址
            else {
                $.get(urls.queryProvince, function(data) {
                    var data = data.data;
                    var $ul = $("#provinceWraper>ul");
                    initChoseArea_hasDefault($ul, data, "province");
                })
            }
            clickEvents();
        }

        function clickEvents() {
            $(".headerUl").on('click', "li", function(e) {
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
                    data = data.data;
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
    })
}