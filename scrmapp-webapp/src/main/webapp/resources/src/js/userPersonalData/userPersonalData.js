function getRootPath() {
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);
};

var userPersonalDataRootPath = getRootPath();


var globalData = {};
var regs = {
    email: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/
};

var urls = {
    // queryProvince: "/webapp/resources/src/js/userPersonalData/fakeJson/province.json",
    // queryCity: "/webapp/resources/src/js/userPersonalData/fakeJson/city.json",
    // queryCounty: "/webapp/resources/src/js/userPersonalData/fakeJson/county.json",
    // queryMbrInfo: "/webapp/resources/src/js/userPersonalData/fakeJson/memberInfo.json",
    // queryEduLevel: "/webapp/resources/src/js/userPersonalData/fakeJson/eduLevel.json",
    // queryOccupation: "/webapp/resources/src/js/userPersonalData/fakeJson/occupation.json",
    // queryMonthlyIncome: "/webapp/resources/src/js/userPersonalData/fakeJson/monthlyIncome.json"


    queryUpdate: userPersonalDataRootPath + "/scrmapp/consumer/user/wechatuser/update",
    queryMonthlyIncome: userPersonalDataRootPath + "/scrmapp/consumer/user/monthlyIncome/get",
    queryOccupation: userPersonalDataRootPath + "/scrmapp/consumer/user/occupation/get",
    queryEduLevel: userPersonalDataRootPath + "/scrmapp/consumer/user/education/get",
    queryMbrInfo: userPersonalDataRootPath + "/scrmapp/consumer/user/wechatuser/get",
    queryProvince: userPersonalDataRootPath + "/scrmapp/consumer/province/get",
    queryCity: userPersonalDataRootPath + "/scrmapp/consumer/city/get",
    queryCounty: userPersonalDataRootPath + "/scrmapp/consumer/area/get"
};

(function($, window) {

    myGet(urls.queryMbrInfo, {}, function(data) {
        var html = template("userPersonalData", data);
        $("#centerArea").html(html);
        if (data.data.birthday) {
            $("#birthdayChose").val(data.data.birthday + " ")
        }
        $("#birthdayChose").data("birthday", (data.data.birthday || ""))

        var comuniBox = $("#preferenceComu"),
            interestsBox = $("#interestMsgs");
        //已选中的感兴趣沟通方式
        $.each(data.data.communicateChannels, function(i, v) {
                comuniBox.find("[data-code=" + v + "]").addClass('on')
            })
            //已选中感兴趣的类型
        $.each(data.data.interestedTypes, function(i, v) {
            interestsBox.find("[data-code=" + v + "]").addClass('on')
        })

        birthdayPickerInit();

        //启动地址选择
        jdLikeAddrInit({
            urls: {
                queryProvince: urls.queryProvince,
                queryCity: urls.queryCity,
                queryCounty: urls.queryCounty
            },
            initDomEl: "select_contact",
            showDomEl: "show_contact"
        })
    })

    //生日日期
    function birthdayPickerInit() {
        var el = $("#birthdayChose");
        el.datetimePicker({
            times: function() {
                return [];
            },
            onClose: function(picker) {
                picker.input.data("birthday", picker.input.val().trim());
            }
        });
    }
})(jQuery, window)


//tag标签(偏好渠道、感兴趣的信息)
function tagEvent(that) {
    var el = $(that);
    el.toggleClass("on");
}
//选择性别
function choseGender(that) {
    var el = $(that).children(".weui-cell__bd");
    $.actions({
        actions: [{
            text: "男",
            onClick: function() {
                el.text("男");
                el.data("gender", 1)
            }
        }, {
            text: "女",
            onClick: function() {
                el.text("女");
                el.data("gender", 2)
            }
        }],
      onClose:function () {
        $('#weui-actionsheet').remove()
      }
    });

}

//选择婚否
function choseIsMarriage(that) {
    var el = $(that).children(".weui-cell__bd");
    $.actions({
        actions: [{
            text: "已婚",
            onClick: function() {
                el.text("已婚");
                el.data("maritalstatus", 1)
            }
        }, {
            text: "未婚",
            onClick: function() {
                el.text("未婚");
                el.data("maritalstatus", 2)
            }
        }],
      onClose:function () {
        $('#weui-actionsheet').remove()
      }
    });
}

//邮箱格式验证
function checkEmail(that) {
    $("#saveBtn").css("position", "fixed")
    var el = $(that);
    if (!regs.email.test(el.val())) {
        $.toptip('邮箱格式错误', 1000, 'warning')
    }
}

//选择教育程度
function educationLevelEvt(that) {
    var el = $(that);
    myGet(urls.queryEduLevel, {}, function(data) {
        if (data.returnCode == 1) {
            var actions = [];
            $.each(data.data, function(i, v) {
                actions.push({
                    text: v.name,
                    code: v.code,
                    onClick: function() {
                        el.children(".weui-cell__bd").text(this.text).data("educationlevel", this.code)
                    }
                })
            })
            $.actions({
                actions: actions,
              onClose:function () {
                $('#weui-actionsheet').remove()
              }
            })
        }
    })
}

//选择职业
function occupationEvt(that) {
    var el = $(that);
    myGet(urls.queryOccupation, {}, function(data) {
        if (data.returnCode == 1) {
            var actions = [];
            $.each(data.data, function(i, v) {
                actions.push({
                    text: v.name,
                    code: v.code,
                    onClick: function() {
                        el.children(".weui-cell__bd").text(this.text).data("occupation", this.code)
                    }
                })
            })
            $.actions({
                actions: actions,
              onClose:function () {
                $('#weui-actionsheet').remove()
              }
            })
        }
    })
}

//选择月收入
function monthlyIncome(that) {
    var el = $(that);
    myGet(urls.queryMonthlyIncome, {}, function(data) {
        if (data.returnCode == 1) {
            var actions = [];
            $.each(data.data, function(i, v) {
                actions.push({
                    text: v.name,
                    code: v.code,
                    onClick: function() {
                        el.children(".weui-cell__bd").text(this.text).data("monthlyincome", this.code)
                    }
                })
            })
            $.actions({
                actions: actions,
              onClose:function () {
                $('#weui-actionsheet').remove()
              }
            })
        }
    })
}

//保存提交
function updateEvt(that) {
    var addressEl = $("#show_contact");
    var permitionFlag = true;
    var tags = function(id) {
        var arr = [];
        $("#" + id + " .cmWay.on").each(function(i, v) {
            arr.push($(v).data("code"));
        })
        return arr.join(",")
    };

    var mustData = {
        userName: $("[data-userName]").val().trim() || "",
        nickName: $("[data-nickName]").val().trim() || "",
        gender: $("[data-gender]").data("gender") || "",
        mobilePhone: $("[data-mobilePhone]").val().trim() || "",
        email: $("[data-email]").val().trim() || "",
        birthday: $("[data-birthday]").val().trim() || "",
        provinceId: addressEl.data("provinceid") || "",
        cityId: addressEl.data("cityid") || "",
        areaId: addressEl.data("areaid") || "",
        communicateChannel: tags("preferenceComu"),
        interestedType: tags("interestMsgs")
    }
    var unnecessaryData = {
        maritalStatus: $("[data-maritalstatus]").data("maritalstatus"),
        educationLevel: $("[data-educationlevel]").data("educationlevel"),
        occupation: $("[data-occupation]").data("occupation"),
        monthlyIncome: $("[data-monthlyincome]").data("monthlyincome")
    }
    for (var key in mustData) {
        if (!mustData[key]) {
            $.toptip('请将信息填写完整', 1000, 'warning')
            permitionFlag = false;
            break;
        }
    }
    if (!regs.email.test(mustData.email)) {
        $.toptip('邮箱格式错误', 1000, 'warning');
        permitionFlag = false;
    }
    if (permitionFlag) {
        for (var key in unnecessaryData) {
            mustData[key] = unnecessaryData[key];
        }
        myPost(urls.queryUpdate, mustData, function(data) {
            if (data.returnCode == 1) {
                $.toptip('修改成功', 1000, 'success');
                window.history.back(-1);
            } else {
                $.toptip(data.returnMsg, 1000, 'error');
            }
        })
    } else {
        return;
    }
}

//解决安卓虚拟键盘顶fixed
function fixAndroidFocus(that) {
    $("#saveBtn").css("position", "static")
}

function fixAndroidBlur(that) {
    $("#saveBtn").css("position", "fixed")
}


//get请求
function myGet(url, data, cb) {
    $.ajax({
        type: "GET",
        data: data,
        url: url,
        success: function(data) {
            // var data = JSON.parse(data);
            cb(data)
        }
    })
}


//post请求
function myPost(url, data, cb) {
    $.ajax({
        type: "POST",
        data: data,
        url: url,
        success: function(data) {
            cb(data)
        }
    })
}