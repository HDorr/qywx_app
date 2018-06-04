//获取根url
function getRootPath() {
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);
};

var addrRootPath = getRootPath();



var urls = {
    queryAddrList: "/webapp/resources/src/js/addressManage/fakeJson/addressListNull.json",
    queryAddrEdit: "/webapp/resources/src/js/addressManage/fakeJson/addressDetail.json",
    querySaveAddr: "/webapp/resources/src/js/addressManage/fakeJson/addressDetail.json",
    queryUpdateAddr: "/webapp/resources/src/js/addressManage/fakeJson/addressDetail.json",
    queryDeleteAddr: "/webapp/resources/src/js/addressManage/fakeJson/addressDelete.json",
    querySetDefault: "/webapp/resources/src/js/addressManage/fakeJson/addressSetDefault.json",
    queryProvince: "/webapp/resources/src/js/addressManage/fakeJson/province.json",
    queryCity: "/webapp/resources/src/js/addressManage/fakeJson/city.json",
    queryCounty: "/webapp/resources/src/js/addressManage/fakeJson/county.json"

    // queryAddrList: addrRootPath + "/scrmapp/consumer/wechatuser/addressList/get",
    // queryAddrEdit: addrRootPath + "/scrmapp/consumer/wechatuser/address/get",
    // querySaveAddr: addrRootPath + "/scrmapp/consumer/wechatuser/address/save",
    // queryUpdateAddr: addrRootPath + "/scrmapp/consumer/wechatuser/address/update",
    // queryDeleteAddr: addrRootPath + "/scrmapp/consumer/wechatuser/address/delete",
    // querySetDefault: addrRootPath + "/scrmapp/consumer/wechatuser/address/setDefault",
    // queryProvince: addrRootPath + "/scrmapp/consumer/province/get",
    // queryCity: addrRootPath + "/scrmapp/consumer/city/get",
    // queryCounty: addrRootPath + "/scrmapp/consumer/area/get"
};

var glData = {
    userId: "test",
    reserveUrl: getUrlParam("reserveUrl") || ""
}




;(function($, window) {
    // if (window.location.hash !== "") {
    //     window.location.hash = "";
    // }

    location.hash = "addrList";

    //showAddrList();
    //定义路由  
    var route = {
        "/addrList": showAddrList,
        "/addrEdit": showAddrEdit,
        "/addrAdd": showAddrAdd
    }

    //初始化路由  
    var router = Router(route);
    router.init();
})(jQuery, window);


function showAddrList() {
    var data = {
        "userId": glData.userId
    };
    myGet(urls.queryAddrList, data, function(data) {
        data.userId = glData.userId;
        if (data.returnCode == 1) {
            if (data.data.length != 0) {
                var html = template("addressListTemplate", data);
                $("#centerArea").html(html);
                $(".addrListEditIcon").attr("src",addrRootPath+"/resources/images/addressEdit.png");
                $(".addrListDelIcon").attr("src",addrRootPath+"/resources/images/addressRemove.png");

            } else {
                var html = template("hasNoAddressList", data);
                $("#centerArea").html(html);
                $("#hasNoAddressListImg").attr("src",addrRootPath+"/resources/images/hasNoAddressYet.png");
            }

        } else {
            $.toast("获取信息失败", 1000, "text");
        }
    })
};

function showAddrEdit() {
    $(".choseAreaBox").remove();
    var id = getUrlParam("id"),
        userid = glData.userId;
    var data = {
        "id": id,
        "userId": glData.userId
    }

    myGet(urls.queryAddrEdit, data, function(data) {
        if (data.returnCode == 1) {
            data.isEdit = 1;
            data.userId = userid;
            var html = template("addressEditTemplate", data);
            $("#centerArea").html(html);
            jdLikeAddrInit({
                urls: {
                    queryProvince: urls.queryProvince,
                    queryCity: urls.queryCity,
                    queryCounty: urls.queryCounty
                },
                initDomEl: "select_contact",
                showDomEl: "show_contact"
            })
        } else {
            $.toptip('获取详细数据失败', 'error');
        }
    })
};

function showAddrAdd() {
    $(".choseAreaBox").remove()
    var data = {
        isEdit: 0,
        userId: glData.userId
    }
    var html = template("addressEditTemplate", data);
    $("#centerArea").html(html);
    jdLikeAddrInit({
        urls: {
            queryProvince: urls.queryProvince,
            queryCity: urls.queryCity,
            queryCounty: urls.queryCounty
        },
        initDomEl: "select_contact",
        showDomEl: "show_contact"
    })
};

//删除地址
function confirmDelet(that) {
    var el = $(that);
    $.confirm({
        title: '删除',
        text: '确认删除该地址？',
        onOK: function() {
            var data = {
                "userId": el.data("userid"),
                "id": el.data("id")
            }
            myPost(urls.queryDeleteAddr, data, function(data) {
                if (data.returnCode == 1) {
                    $.toast("操作成功", 500, function() {
                        showAddrList();
                    });
                } else {
                    $.toptip('删除失败', 'error');
                }
            })
        },
    });
}

//选择默认地址
function defaultAddrCheckOnChange(that) {
    var el = $(that);
    var isChecked = el.prop("checked");
    if (isChecked) {
        var data = {
            "userId": glData.userId,
            "id": el.data("id")
        }
        myPost(urls.querySetDefault, data, function(data) {
            if (data.returnCode == 1) {
                $(".defaultAddrCheck").not(el).prop("checked", false);
                $.toast("设置成功", 1000)
            } else {
                el.prop("checked", false)
            }
        })
    } //如果是取消本身已经默认的，禁止操作
    else {
        el.prop("checked", true);
    }
}

//修改地址
function addressUpdate(that) {
    var el = $(that);
    var addressBox = $("#show_contact");
    var setDefault = $("#userSetDefault");
    var flag = true;
    if (setDefault.length != 0) {
        var isDefault = setDefault.prop("checked") ? "1" : "2";
    };
    var submitData = {
        id: el.data("id"),
        userId: glData.userId,
        contacts: $("#userContacts").val(),
        contactsMobile: $("#userContactsMobile").val(),
        provinceId: addressBox.data("provinceid"),
        provinceName: addressBox.data("provincename"),
        cityId: addressBox.data("cityid"),
        cityName: addressBox.data("cityname"),
        areaId: addressBox.data("areaid"),
        areaName: addressBox.data("areaname"),
        streetName: $("#userStreetName").val(),
        isDefault: isDefault || "1"
    };
    for (var key in submitData) {
        if (!submitData[key]) {
            flag = false;
            break;
        }
    }
    if (flag) {
        myPost(urls.queryUpdateAddr, submitData, function(data) {
            if (data.returnCode == 1) {
                $.toast("保存成功", 500, function() {
                    // location.reload();
                    window.history.back(-1)
                })
            } else {
                $.toptip('保存失败', 'error');
            }
        })
    } else {
        $.toptip('请输入完整信息', 'error');
    }

}

//添加地址保存
function addressAddSave(that) {
    var el = $(that);
    var addressBox = $("#show_contact");
    var setDefault = $("#userSetDefault");
    var flag = true;
    var isDefault = setDefault.prop("checked") ? "1" : "0";
    var submitData = {
        userId: glData.userId,
        contacts: $("#userContacts").val(),
        contactsMobile: $("#userContactsMobile").val(),
        provinceId: addressBox.data("provinceid"),
        provinceName: addressBox.data("provincename"),
        cityId: addressBox.data("cityid"),
        cityName: addressBox.data("cityname"),
        areaId: addressBox.data("areaid"),
        areaName: addressBox.data("areaname"),
        streetName: $("#userStreetName").val(),
        isDefault: isDefault
    }
    for (var key in submitData) {
        if (!submitData[key]) {
            flag = false;
            break;
        }
    }
    if (flag) {
        myPost(urls.querySaveAddr, submitData, function(data) {
            if (data.returnCode == 1) {
                $.toast("保存成功", 500, function() {
                    // location.reload();
                    window.history.back(-1)
                })
            } else {
                $.toptip('保存失败', 'error');
            }
        });
    } else {
        $.toptip('请输入完整信息', 'error');
    }
}

function gotoOrderService(that) {
    if (glData.reserveUrl) {
        glData.reserveUrl = glData.reserveUrl.split('#')[0]
        var addressId = $(that).data("id");
        var rootPath = getRootPath()
        var pathExp = new RegExp(rootPath,'g')
        var url = pathExp.test(rootPath) ? glData.reserveUrl  : rootPath + glData.reserveUrl
        url +=  "?addressId=" + addressId
        location.href = url
    } else {
        return
    }
}

//获取根url
function getRootPath() {
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);
};

//截取url中？号后的参数值
function getUrlParam(name) {
    var reg = new RegExp(name + '=([^&]*)(&|$)', 'i');
    var r = window.location.href.match(reg);
    if (r != null) {
        return unescape(r[1]);
    }
    return null;
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