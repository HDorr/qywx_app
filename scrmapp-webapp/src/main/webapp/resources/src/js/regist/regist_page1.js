function getRootPath() {
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);
};

var registRootPath = getRootPath();

(function($, window) {
    sendCfCode();
    nextStep();
    showAreaChose();


    var telNum;
    var openid = $("#getCusInfo").data("openid");

    // var openid = "obJNHxMUR55MYhXYItJFuUlpLg_0"

    var scrolls = {
            province: null,
            city: null,
            county: null
        }
        //请求的地址
    var urls = {
        getComfirmCode: registRootPath + "/scrmapp/consumer/user/verifycode/send",
        verifyComfirmCode: registRootPath + "/scrmapp/consumer/user/verifycode/confirm",
        queryProvince: registRootPath + "/scrmapp/consumer/province/get",
        queryCity: registRootPath + "/scrmapp/consumer/city/get",
        queryCounty: registRootPath + "/scrmapp/consumer/area/get",
        queryRegist: registRootPath + "/scrmapp/consumer/user/register"
    }

    //验证用正则
    var reg = {
        phoneNum_reg: /^1[3-9]\d{9}$/,
        cfCode_reg: /^\d{6}$/
    }

    //第一屏警告框
    function warnFade(id, delayTime, text) {
        $("#" + id).text(text).stop().fadeIn().delay(delayTime).fadeOut();
    }

    //切换显示第一步第二步
    function toggleStep() {
        $(".loadEffect").removeClass("on");
        $("#step1Box").toggleClass('hideStep');
        $("#step2Box").toggleClass('hideStep');
    }

    /*第一屏-start*/
    function sendCfCode() {
        $("#sendCfCode").click(function(e) {
            var btn = $(this)
            e.preventDefault();
            telNum = $("#phone_number").val().trim();
            //验证手机号码格式
            if (!reg.phoneNum_reg.test(telNum)) {
                warnFade("warn_text", 3000, "手机号格式错误")
                return;
            };
            //发送获取验证码
            $.ajax({
                url: urls.getComfirmCode,
                type: "POST",
                data: {
                    "openId": openid,
                    "mobile": telNum
                },
                dataType: "json",
                success: function(data) {
                    if (data.returnCode == 0) {
                        warnFade("warn_text", 3000, data.returnMsg)
                    }
                }
            })
            btn.attr("disabled", "true");
            //倒计时
            var secs = 30;
            var timer = setInterval(function() {
                btn.val(secs + "s后重新发送");
                secs--;
                if (secs < 0) {
                    clearInterval(timer);
                    btn.removeAttr('disabled');
                    btn.val("发送验证码")
                }
            }, 1000)
        })
    }

    function nextStep() {
        $("#nextStepBtn").click(function(e) {
            e.preventDefault();
            var code = $("#confirmCode").val();
            telNum = $("#phone_number").val().trim();

            let privateContactChecked = $("#cb_private_contact").hasClass("checked");

            if ( !privateContactChecked ){
              // warnFade("warn_text", 3000, "请阅读并同意《沁园隐私政策》")
              return;
            }

            //验证验证码格式
            if (!reg.cfCode_reg.test(code) || !reg.phoneNum_reg.test(telNum) ) {
                warnFade("warn_text", 3000, "验证码或手机号错误")
            } else {
                //提交
                $(".loadEffect").addClass("on");
                $.ajax({
                    url: urls.verifyComfirmCode,
                    type: "POST",
                    data: {
                        "verifycode": code,
                        "mobile": telNum
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data.returnCode == 0) {
                            warnFade("warn_text", 3000, data.returnMsg)
                        } else {
                            //data.data false代表已注册 需隐藏密码框
                            if (!data.data) {
                                $("#custmPsw").parents(".input-box").remove();
                                $("#totalSubmit").data("ismallmember", "1");
                            } else {
                                $("#totalSubmit").data("ismallmember", "0")
                            }
                            toggleStep();
                        }
                    },
                  complete:function (data) {
                    $(".loadEffect").removeClass("on");
                  }
                })
            }

        })
    }

    /*第一屏-end*/


    /*第二屏-start*/
    function getAddr(type, id, cb) {
        var res = null;
        var url = " ";
        var data = {
            "openId": openid,
        };
        switch (type) {
            case "province":
                url = urls.queryProvince
                break;
            case "city":
                url = urls.queryCity
                data["provinceId"] = id;
                break;
            case "area":
                url = urls.queryCounty
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
            str += ("<li data-" + id + "=" + data[i][id] + "  data-" + value + "=" + data[i][value] + ">" + data[i][value] + "</li>");
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
            if (scrolls[type]) {
                if (type == "province") {
                    return
                } else {
                    scrolls[type].refresh();
                }
            } else {
                var timer = setTimeout(function() {
                    var scrollItem = $("#" + type + "Wraper")[0];
                    scrolls[type] = new IScroll(scrollItem, {
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

          let pwd = $("#custmPsw").val();
          let ismallMember = $("#totalSubmit").data("ismallmember");
          if (pwd || ismallMember==1) {
            $("#totalSubmit").removeClass("bigBtn")
            $("#totalSubmit").addClass("bigBtnAbleClick")
          }
        } else {
            $(".choseAreaBox").fadeOut(200, function() {
                $(".container").show()
            })
        }
    }

    function clickEvents() {

          $("#cb_private_contact").on('click',function() {
            $("#cb_private_contact").toggleClass('checked')
            let ableClick = $("#cb_private_contact").hasClass("checked");
            if (ableClick) {
                $("#nextStepBtn").removeClass("bigBtn")
                $("#nextStepBtn").addClass("bigBtnAbleClick")
            }else {
              $("#nextStepBtn").removeClass("bigBtnAbleClick")
              $("#nextStepBtn").addClass("bigBtn")
            }
          });


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

        $("#totalSubmit").click(function() {

            let ableClick = $("#totalSubmit").hasClass("bigBtnAbleClick");
          let pwd = $("#custmPsw").val();
          let ismallMember = $("#totalSubmit").data("ismallmember");
            if (!ableClick ||  (!pwd && ismallMember==0)) {
              return;
            }
          
          
            var spanDom = $("#show_contact");
            var permitFlag = true;
            var errorStr = "";
            if (isMallMember == 0) {
                window.finaldata = {
                    openId: openid,
                    mobile: telNum,
                    provinceId: spanDom.data("provinceid"),
                    cityId: spanDom.data("cityid"),
                    areaId: spanDom.data("areaid"),
                    //userName: $("#custmName").val(),
                    password: $("#custmPsw").val(),
                    isMallMember: false
                }
            } else {
                window.finaldata = {
                    openId: openid,
                    mobile: telNum,
                    provinceId: spanDom.data("provinceid"),
                    cityId: spanDom.data("cityid"),
                    areaId: spanDom.data("areaid"),
                    isMallMember: true
                        //userName: $("#custmName").val(),
                }
            }
            for (var key in finaldata) {
                if (!finaldata[key] && key != "isMallMember") {
                    errorStr = key;
                    permitFlag = false;
                    break;
                }
            }
            if (permitFlag) {
                $.ajax({
                    url: urls.queryRegist,
                    data: finaldata,
                    type: "POST",
                    dataType: "json",
                    success: function(data) {
                        if (data.returnCode == 1) {
                            var urlStr = $("#registUrl").val();
                            if (urlStr) {
                                location.href = $("#registUrl").val();
                            } else {
                                alert("跳转地址错误，地址为：" + urlStr)
                            }

                        } else {
                            alert(data.returnMsg);
                        }

                    },
                    complete: function(data) {

                    }
                })
            } else {
                alert("error" + key)
            }

        })
    }

    function showAreaChose() {
        $(".addressContainer").height($(".choseAreaBox").height() - 90)
        clickEvents()
        $("#select_contact").click(function() {
            showChoseBox(areaSelectInit)

            function areaSelectInit() {
                /*var addrSpan = $("#show_contact");
                 var provinceId = addrSpan.data("provinceid"),
                 cityId = addrSpan.data("cityid"),
                 areaId = addrSpan.data("areaid");*/
                //如果有默认地址
                /*if (provinceId && cityId && areaId) {
                 //渲染省级数据
                 getAddr("province", 0, function(data) {
                 if (data.returnCode == 0) {
                 alert(data.returnMsg)
                 } else {
                 alert("provinceId")
                 var data = data.data;
                 var $ul = $("#provinceWraper>ul");
                 initChoseArea_hasDefault($ul, data, "province", provinceId)
                 //渲染市级数据
                 getAddr("city", provinceId, function(data) {
                 alert(cityId)
                 var data = data.data;
                 var $ul = $("#cityWraper>ul");
                 initChoseArea_hasDefault($ul, data, "city", cityId)
                 //渲染区数据
                 getAddr("area", cityId, function(data) {
                 alert(areaId)
                 var data = data.data;
                 var $ul = $("#areaWraper>ul");
                 initChoseArea_hasDefault($ul, data, "area", areaId)
                 addressContainerTranslateX("2");
                 })
                 })
                 }

                 })
                 }*/
                //如果没有默认地址
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

    /*第二屏-end*/


})(jQuery, window)