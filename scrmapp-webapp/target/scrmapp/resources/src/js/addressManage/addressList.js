
var myAjax = {
    isLoading:function(){
        return $('.weui-toast.weui_loading_toast.weui-toast--visible').length ? true : false
    },
    showLoading:function(){
        if(!myAjax.isLoading()){
            setTimeout(function(){
                $.showLoading('加载中')
            },50)
        }
    },
    get: function(url,data){
        myAjax.showLoading() 
        var data = data || {}
        var def = $.Deferred()
        $.get(url,data).then(function(data){
            setTimeout(function(){
                $.hideLoading()
                def.resolve(data)
            },51)      
        }).fail(function(err){
            setTimeout(function(){
                $.hideLoading()
                def.reject(err)
            },51)            
        })
        return def.promise()
    },
    post: function(url,data,needStringify){
        myAjax.showLoading() 
        var def = $.Deferred()
        var data = data || {}
        var needStringify = needStringify || false
        var ajaxOption = {
            url:url,
            type:'POST',
            data:needStringify ? JSON.stringify(data) : data,
        }
        if(needStringify) ajaxOption.contentType = "application/json;charset=UTF-8"
        $.ajax(ajaxOption).then(function(data){
            setTimeout(function(){
                $.hideLoading()
                def.resolve(data)
            },51)     
        }).fail(function(err){
            setTimeout(function(){
                $.hideLoading()
                def.reject(err) 
            },51)       
        })
        return def.promise()
    }
}
function showErr(error){
    setTimeout(function(){
        $.alert(error.message || error.errMessage || error.statusText || error.returnMsg || '网络错误',"出错啦！")
    },50)     
}
//获取根url
function getRootPath() {
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);
}
//截取url中？号后的参数值
function getUrlParam(name) {
    var reg = new RegExp(name + '=([^&]*)(&|$)', 'i');
    var r = window.location.href.match(reg);
    if (r != null) {
        return unescape(r[1]);
    }
    return null;
}
//启动地址选择页面
function showAddrChose(){
    jdLikeAddrInit({
        urls: {
            queryProvince: urls.queryProvince,
            queryCity: urls.queryCity,
            queryCounty: urls.queryCounty
        },
        initDomEl: "select_contact",
        showDomEl: "show_contact"
    })
}

var addrRootPath = getRootPath();


var urls = {
    // queryAddrList: "/resources/src/js/addressManage/fakeJson/addressList.json",
    // queryAddrEdit: "/resources/src/js/addressManage/fakeJson/addressDetail.json",
    // querySaveAddr: "/resources/src/js/addressManage/fakeJson/addressDetail.json",
    // queryUpdateAddr: "/resources/src/js/addressManage/fakeJson/addressDetail.json",
    // queryDeleteAddr: "/resources/src/js/addressManage/fakeJson/addressDelete.json",
    // querySetDefault: "/resources/src/js/addressManage/fakeJson/addressSetDefault.json",
    // queryProvince: "/resources/src/js/addressManage/fakeJson/province.json",
    // queryCity: "/resources/src/js/addressManage/fakeJson/city.json",
    // queryCounty: "/resources/src/js/addressManage/fakeJson/county.json"

    queryAddrList: addrRootPath + "/scrmapp/consumer/wechatuser/addressList/get",
    queryAddrEdit: addrRootPath + "/scrmapp/consumer/wechatuser/address/get",
    querySaveAddr: addrRootPath + "/scrmapp/consumer/wechatuser/address/save",
    queryUpdateAddr: addrRootPath + "/scrmapp/consumer/wechatuser/address/update",
    queryDeleteAddr: addrRootPath + "/scrmapp/consumer/wechatuser/address/delete",
    querySetDefault: addrRootPath + "/scrmapp/consumer/wechatuser/address/setDefault",
    queryProvince: addrRootPath + "/scrmapp/consumer/province/get",
    queryCity: addrRootPath + "/scrmapp/consumer/city/get",
    queryCounty: addrRootPath + "/scrmapp/consumer/area/get"
};

var glData = {
    userId: "test",
    reserveUrl: getUrlParam("reserveUrl") || ""
}


function showAddrList() {
    var data = {
        "userId": glData.userId
    };
    myAjax.get(urls.queryAddrList,data).then(function(data){
        data.userId = glData.userId
        if (data.returnCode == 1) {
            if (data.data.length != 0) {
                var html = template("addressListTemplate", data);
                $("#centerArea").html(html);
                $(".addrListEditIcon").attr("src","/resources/src/images/icons/addressEdit.png");
                $(".addrListDelIcon").attr("src","/resources/src/images/icons/addressRemove.png");

            } else {
                var html = template("hasNoAddressList", data);
                $("#centerArea").html(html);
                $("#hasNoAddressListImg").attr("src","/resources/src/images/icons/hasNoAddressYet.png");
            }

        } else {
            showErr(data)
        }
    }).fail(function(error){
        showErr(error)
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
    myAjax.get(urls.queryAddrEdit,data).then(function(data){
        if (data.returnCode == 1) {
            data.userId = userid;
            data.data.fixedTelephone = data.data.fixedTelephone ? data.data.fixedTelephone.split('-') : ""
            var html = template("addressEditTemplate", data);
            $("#centerArea").html(html);
            showAddrChose()
        } else {
            showErr(data)
        }
    }).fail(function(error){
        showErr(error)
    })
};

function showAddrAdd() {
    $(".choseAreaBox").remove()
    var data = {
        userId: glData.userId
    }
    var html = template("addressAddTemplate", data);
    $("#centerArea").html(html);
    showAddrChose()
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
            myAjax.post(urls.queryDeleteAddr,data).then(function(data){
                if (data.returnCode == 1) {
                    $.toptip("设置成功", 1000,"success")
                    showAddrList();
                } else {
                    showErr(data)
                }
            }).fail(function(error){
                showErr(error)
            })
        },
    });
}

//选择默认地址
function defaultAddrCheckOnChange(that) {
    var el = $(that);
    var isChecked = el.prop("checked");
    if(!isChecked){
        el.prop("checked", true)
        return
    }
    var data = {
        "userId": glData.userId,
        "id": el.data("id")
    }
    myAjax.post(urls.querySetDefault,data).then(function(data){
        if (data.returnCode == 1) {
            $(".defaultAddrCheck").not(el).prop("checked", false);
            $.toptip("设置成功", 1000,"success")
        } else {
            el.prop("checked", false)
        }
    }).fail(function(error){
        el.prop("checked", false)
        showErr(error)
    })
}

//修改地址
function addressUpdate(that) {
    var sendData = _getCheckedSubmitData(that)
    if(sendData.message){
        showErr(sendData)
        return
    }
    myAjax.post(urls.queryUpdateAddr,sendData).then(function(data){
        if (data.returnCode == 1) {
            $.toptip("保存成功", 1000,"success")
            setTimeout(function(){
                window.history.back(-1)
            },1000)
        } else {
            showErr(data)
        }
    }).fail(function(error){
        showErr(error)
    })

}

//添加地址保存
function addressAddSave(that) {
    var sendData = _getCheckedSubmitData(that)
    if(sendData.message){
        showErr(sendData)
        return
    }
    myAjax.post(urls.querySaveAddr,sendData).then(function(data){
        if (data.returnCode == 1) {
            $.toptip("设置成功", 1000,"success")
            setTimeout(function(){
                window.history.back(-1)
            },1000)
        } else {
            showErr(data)
        }
    }).fail(function(error){
        showErr(error)
    })
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

function _getCheckedSubmitData(elm){
    var el = $(elm);
    var addressBox = $("#show_contact"),
        setDefault = $("#userSetDefault");
    var fixedTelArea = $("#telArea").val(),
        fixedTelNum  = $("#telNum").val()
    var id = el.data("id") || ""
    var fAid = el.data("faid") || ""
    var isDefault = setDefault.length === 0? "1" : (setDefault.prop("checked") ? "1" : "2");
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
    fAid && (submitData.fAid = fAid)
    if(id) submitData.id = id
    for (var key in submitData) {
        if (!submitData[key]) {
            return {message:"请将信息填写完整"}
        }
    }
    var fixedTelephone = ''
    if( fixedTelArea || fixedTelNum ){
        if (!fixedTelArea) return {message:"请填写固定电话区号"}
        if (!fixedTelNum) return {message:"请填写固定电话号码"}
        if (!/^[0-9]{3,4}$/.test(fixedTelArea)) return {message:"区号格式错误"}
        if (!/^[0-9]{3,8}$/.test(fixedTelNum)) return {message:"固定电话格式错误"}
        fixedTelephone = fixedTelArea + "-" + fixedTelNum
    }
    if(fixedTelephone) submitData.fixedTelephone = fixedTelephone
    return submitData
}












;(function($, window) {
    location.hash = "addrList"; 
    var route = {
        "/addrList": showAddrList,
        "/addrEdit": showAddrEdit,
        "/addrAdd": showAddrAdd
    }
    //初始化路由  
    var router = Router(route);
    router.init();
})(jQuery, window);


