var rootPath = getRootPath();
var INSTALL_ORDER = 1;
var MAINTAIN_ORDER = 2;
var CLEAN_ORDER = 3;
var UPDATE_FILTER_ORDER = 4;
var ERR_OK = 1;
var IS_DEVENV = (location.origin === "http://localhost:8080") ||
                (location.origin === "http://localhost:9527") ||
                (location.origin === "http://127.0.0.1:8080") ||
                (location.origin === "http://vjddka.natappfree.cc")
var pageUrls = IS_DEVENV ?
{   //开发测试用
    oneClickService: rootPath + "/scrmapp/consumer/product/index",
    scanToBindPdt: rootPath + "/scrmapp/consumer/product/bind/scan/page",
    noScanToBindPdt: rootPath + "/scrmapp/consumer/product/bind/noscan/page",
    bindPdtMain: rootPath + "/scrmapp/consumer/product/query/page",
    bindPdtLists: rootPath + "/scrmapp/consumer/user/filter/myProducts/jsp/myPdtList",
    bindPdtDetail: rootPath + "/scrmapp/consumer/product/detail/page",
    orderList: rootPath + "/scrmapp/consumer/user/filter/myOrder/jsp/myOrderList",
    orderDetail: "../../../mobile/myOrder/html/orderDetail.html",
    securityCheck: rootPath + "/scrmapp/consumer/product/securityCheck/page",
    securityCheckRes: rootPath + "/scrmapp/consumer/product/securityCheck/result/page",
    reserveSuccess:"../../../../mobile/reserveService/html/reserveSuccessPrompt.html",
    reviewPraide: rootPath + "/scrmapp/consumer/wechat/orders/appraisal/page",
    finishedWorkOrderList: rootPath + "/scrmapp/qyhuser/orders/finish/list/page",
    engineerPersonCenter: rootPath + "/scrmapp/qyhuser/index/page",
    unfinishedWorkOrderList: "../../../../mobile/qiYeHao/workOrderOperation/html/unfinishedWorkOrders.html",
    unfinishedWorkOrderDetail_install_detail: "../../../../mobile/qiYeHao/workOrderOperation/html/unfinishedOrderDetail_install.html",
    unfinishedWorkOrderDetail_repair: "../../../../mobile/qiYeHao/workOrderOperation/html/unfinishedOrderDetail_repair.html",
    unfinishedWorkOrderDetail_repair_detail: "../../../../mobile/qiYeHao/workOrderOperation/html/unfinishedOrderDetail_repair_detail.html",
    unfinishedWorkOrderDetail_maintain: "../../../../mobile/qiYeHao/workOrderOperation/html/unfinishedOrderDetail_maintain.html",
    unfinishedWorkOrderDetail_maintain_detail: "../../../../mobile/qiYeHao/workOrderOperation/html/unfinishedOrderDetail_maintain_detail.html",
    completeWorkOrderTip:"../../../../mobile/qiYeHao/workOrderOperation/html/completeServiceTip.html",//完成工单后的成功提示页面
    reserveServicePdtList:"../../../../mobile/reserveService/html/chooseProduct.html",
    chooseAddressPage: rootPath + "/scrmapp/consumer/wechatuser/address/index",                     //地址选择
    reserveInstallPage:"../../../../mobile/reserveService/html/reserveService_install.html",           //预约安装详情
    reserveUpdateFilterPage:"../../../../mobile/reserveService/html/reserveService_updateFilter.html",  //预约更换滤芯详情
    reserveMaintainPage:"../../../../mobile/reserveService/html/reserveService_maintain.html",  //预约维修详情
    reserveCleanPage:"../../../../mobile/reserveService/html/reserveService_clean.html"  //预约清洗滤芯详情
} : { //正式环境
    oneClickService: rootPath + "/scrmapp/consumer/product/index",
    scanToBindPdt: rootPath + "/scrmapp/consumer/product/bind/scan/page",
    noScanToBindPdt: rootPath + "/scrmapp/consumer/product/bind/noscan/page",
    bindPdtMain: rootPath + "/scrmapp/consumer/product/query/page",
    bindPdtLists: rootPath + "/scrmapp/consumer/user/filter/myProducts/jsp/myPdtList",
    bindPdtDetail: rootPath + "/scrmapp/consumer/product/detail/page",
    orderList: rootPath + "/scrmapp/consumer/user/filter/myOrder/jsp/myOrderList",
    orderDetail: rootPath + "/scrmapp/consumer/wechat/orders/service/detail",
    securityCheck: rootPath + "/scrmapp/consumer/product/securityCheck/page",
    securityCheckRes: rootPath + "/scrmapp/consumer/product/securityCheck/result/page",
    reserveSuccess:rootPath + "/scrmapp/consumer/wechat/orders/service/add/success",
    reviewPraide: rootPath + "/scrmapp/consumer/wechat/orders/appraisal/page",
    finishedWorkOrderList: rootPath + "/scrmapp/qyhuser/orders/finish/list/page",
    engineerPersonCenter: rootPath + "/scrmapp/qyhuser/index/page",
    unfinishedWorkOrderDetail: rootPath + "/scrmapp/qyhuser/orders/detail/page",
    unfinishedWorkOrderList: rootPath + "/scrmapp/qyhuser/orders/pending/list/page", //待处理工单列表
    unfinishedWorkOrderDetail_install_detail: rootPath + "/scrmapp/qyhuser/orders/detail/install", //TODO 未完成工单安装单详情提交
    unfinishedWorkOrderDetail_repair:  rootPath + "/scrmapp/qyhuser/orders/detail/repair",//TODO 未完成维修单详情
    unfinishedWorkOrderDetail_repair_detail:  rootPath + "/scrmapp/qyhuser/orders/repair/detail", //TODO 未完成工单维修单产品详情提交
    unfinishedWorkOrderDetail_maintain:  rootPath + "/scrmapp/qyhuser/orders/detail/maintain",//TODO 未完成工单保养单详情
    unfinishedWorkOrderDetail_maintain_detail: rootPath + "/scrmapp/qyhuser/orders/maintain/detail",//TODO 未完成工单保养单产品详情提交
    completeWorkOrderTip:rootPath + "/mobile/qiYeHao/workOrderOperation/jsp/completeServiceTip.jsp",//完成工单后的成功提示页面
    reserveServicePdtList:rootPath + "/scrmapp/consumer/wechat/orders/service/choice/product",
    chooseAddressPage: rootPath + "/scrmapp/consumer/wechatuser/address/index",                     //地址选择
    reserveInstallPage: rootPath +"/scrmapp/consumer/product/reserve/install",           //预约安装详情
    reserveUpdateFilterPage: rootPath +"/scrmapp/consumer/product/reserve/updateFilter",  //预约更换滤芯详情
    reserveMaintainPage: rootPath +"/scrmapp/consumer/product/reserve/maintain",  //预约维修详情
    reserveCleanPage: rootPath +"/scrmapp/consumer/product/reserve/clean"  //预约清洗滤芯详情
}


var queryUrls = IS_DEVENV ?
{   //开发测试用
    getProductsList:"/resources/fakeJson/productLists.json",
    queryAddrList: "/resources/src/js/addressManage/fakeJson/addressList.json",
    queryPdtByCodeOrModal:"/resources/fakeJson/fetchSuccess.json",
    orderList: "/resources/fakeJson/orderList.json",
    saveReserve:"/resources/fakeJson/fetchSuccess.json", //服务号 保存预约
    orderDetail: "/resources/fakeJson/orderDetail.json",
    orderCancel: "/resources/fakeJson/fetchSuccess.json",
    orderUpdate: "/resources/fakeJson/fetchSuccess.json",
    getReserveData: "/resources/fakeJson/getReserveData.json", //获取预约请假详情
    reservationAppraisal: rootPath + "/scrmapp/consumer/wechat/orders/user/appraisal", //提交预约评价
    qyhGetRepairItem: "/resources/fakeJson/repairItem.json", //获取维修措施列表
    qyhGetRepairPart: "/resources/fakeJson/repairPart.json", //获取维修配件列表
    qyhGetMaintainAndFilter: "/resources/fakeJson/maintainAndFilters.json", //获取保养项和滤芯列表
    qyhQueryUnfinishedOrder: "/resources/fakeJson/unfinishedWorkOrders.json", //获取待处理订单
    qyhSubmitOrder_install:"/resources/fakeJson/fetchSuccess.json", //安装单完工提交
    qyhSubmitOrder_repair: "/resources/fakeJson/fetchSuccess.json", //维修单完工提交
    qyhSubmitOrder_maintain: "/resources/fakeJson/fetchSuccess.json", //保养单完工提交
    qyhCancelOrderProduct:"/resources/fakeJson/fetchSuccess.json",//师傅侧工单取消产品
    qyhQueryFinishedOrder: "/resources/fakeJson/completedWorkOrder.json", //获取已完成工单
    qyhQueryRefuseOrder: "/resources/fakeJson/fetchSuccess.json", //师傅侧拒绝工单
    qyhQueryChangeOrder: rootPath + "/scrmapp/qyhuser/orders/change/ordertime", //师傅侧更改预约时间
    qyhQueryUFinedOrderDetail: "/resources/fakeJson/unfinishedWorkOrderDetail.json", //获取待处理工单详情
    qyhQueryFinedOrderDetail: "/resources/fakeJson/finishedWorkOrderDetail.json" //获取已完成工单详情
} :  {//正式接口
    getProductsList: rootPath + "/scrmapp/consumer/product/list",
    queryAddrList: rootPath + "/scrmapp/consumer/wechatuser/addressList/get",
    queryPdtByCodeOrModal:rootPath + "/scrmapp/consumer/product/query",
    saveReserve:rootPath + "/scrmapp/consumer/wechat/orders/save", //服务号 保存预约
    orderList: rootPath + "/scrmapp/consumer/wechat/orders/list",
    orderDetail: rootPath + "/scrmapp/consumer/wechat/orders/detail",
    orderCancel: rootPath + "/scrmapp/consumer/wechat/orders/cancel",
    orderUpdate: rootPath + "/scrmapp/consumer/wechat/orders/update",
    getReserveData: rootPath + "/scrmapp/consumer/wechat/orders/qyh/info", //获取预约请假详情
    reservationAppraisal: rootPath + "/scrmapp/consumer/wechat/orders/user/appraisal", //提交预约评价
    qyhGetRepairItem: rootPath + "/scrmapp/qyhuser/orders/repairItem/get", //获取维修措施列表
    qyhGetRepairPart: rootPath + "/scrmapp/qyhuser/orders/repairPart/get", //获取维修配件列表
    qyhGetMaintainAndFilter: rootPath + "/scrmapp/qyhuser/orders/miantain/get", //获取保养项和滤芯列表
    qyhQueryUnfinishedOrder: rootPath + "/scrmapp/qyhuser/orders/condition/count", //获取待处理订单
    qyhSubmitOrder_install: rootPath + "/scrmapp/qyhuser/orders/finish/install", //安装单完工提交
    qyhSubmitOrder_repair: rootPath + "/scrmapp/qyhuser/orders/finish/repair", //维修单完工提交
    qyhSubmitOrder_maintain: rootPath + "/scrmapp/qyhuser/orders/finish/maintain", //保养单完工提交
    qyhQueryFinishedOrder: rootPath + "/scrmapp/qyhuser/orders/finished/list", //获取已完成工单
    qyhCancelOrderProduct:rootPath + "/scrmapp/qyhuser/orders/cancel/product",//师傅侧工单取消产品
    qyhQueryRefuseOrder: rootPath + "/scrmapp/qyhuser/orders/refuse", //师傅侧拒绝工单
    qyhQueryChangeOrder: rootPath + "/scrmapp/qyhuser/orders/change/ordertime", //师傅侧更改预约时间
    qyhQueryUFinedOrderDetail: rootPath + "/scrmapp/qyhuser/orders/pending/detail", //获取待处理工单详情
    qyhQueryFinedOrderDetail: rootPath + "/scrmapp/qyhuser/orders/finished/detail", //获取已完成工单详情


}

var confirmUrls = {
    qyhConfirmReceive:orderType => rootPath+`/scrmapp/qyhuser/orders/confirm/receive/${orderType}`, //师傅确认接单(1.安装单,2.维修单,3.保养单)
    qyhConfirmArrive:orderType => rootPath+`/scrmapp/qyhuser/orders/confirm/arrive/${orderType}` //师傅确认到达现场(1.安装单,2.维修单,3.保养单)
}

//跳转至一键预约页面
function gotoIndex() {
    window.location.href = pageUrls.oneClickService;
}


///判断是否为微信浏览器
function isWeiXin() {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == 'micromessenger') {
        return true;
    } else {
        return false;
    }
}

/**
 * 将url，data拼接成  http://baidu.com?key1=value1&key2=value2
 * @param {*} url
 * @param {*} data
 */
function getParamStr (url,data,isEscape) {
    var isEscape = isEscape || false
    if (JSON.stringify(data) === "{}") {
        return url
    }
    var params = ''
    var res = url
    for (var k in data) {
      var value = data[k] !== undefined ? data[k] : ''
      params += '&' + k + '=' + (isEscape ? escape(value) : encodeURIComponent(value))
    }
    params = params ? params.substring(1) : ""
    res += (res.indexOf('?') < 0 ? '?' : '&') + params
    return res
  }

//获取根url
function getRootPath() {
    var strFullPath = window.location.href;
    var strPath = window.location.pathname;
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

//获取当日算起未来的n天日期
function getFutureDate(dayNum) {
    var oDate = new Date(); //获取当前时间
    var dayArr = [oDate]; //定义一个数组存储所有时间
    for (var i = 1; i <= dayNum; i++) {
        dayArr.push(new Date(oDate.getFullYear(), oDate.getMonth(), oDate.getDate() + i)); //把未来几天的时间放到数组里
    }
    return dayArr;
}

//获取当日算起过去的n天日期
function getHistoryDate(dayNum) {
    var oDate = new Date(); //获取当前时间
    var dayArr = [oDate]; //定义一个数组存储所有时间
    for (var i = 1; i <= dayNum; i++) {
        dayArr.push(new Date(oDate.getFullYear(), oDate.getMonth(), oDate.getDate() - i)); //把未来几天的时间放到数组里
    }
    return dayArr;
}

//拼接字符串
function joinString(args){
    var resStr = ""
    for(var i = 0; i<arguments.length; i++){
        if(!arguments[i]) continue
        resStr += arguments[i]
    }
    return resStr
}

var ajax = {
    isLoading:function(){
        return $('.weui-toast.weui_loading_toast.weui-toast--visible').length ? true : false
    },
    showLoading:function(text){
        var text = text || '加载中'
        if(!ajax.isLoading()){
            setTimeout(function(){
                $.showLoading(text)
            },50)
        }
    },
    get: function(url,data,loadingText){
        ajax.showLoading(loadingText)
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
    post: function(url,data,needStringify,loadingText){
        var def = $.Deferred()
        var data = data || {}
        var needStringify = needStringify || false
        var ajaxOption = {
            url:url,
            type:IS_DEVENV ? 'GET' : 'POST',
            data:needStringify ? JSON.stringify(data) : data,
        }
        ajax.showLoading(loadingText)
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

var alertMsg = {
    error :function(error){
        setTimeout(function(){
            $.alert(error.message || error.errMessage || error.statusText || error.returnMsg || '网络错误',"出错啦！")
        },35)
    }
}

var wxInit = {
    data: {
        "signature": "",
        "appId": "",
        "timestamp": "",
        "nonceStr": ""
    },
    wxConfig: function() {
        wx.config({
            debug: false,
            appId: wxInit.data.appId,
            timestamp: wxInit.data.timestamp,
            nonceStr: wxInit.data.nonceStr,
            signature: wxInit.data.signature,
            jsApiList: ['onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo',
                'startRecord',
                'stopRecord',
                'onVoiceRecordEnd',
                'playVoice',
                'pauseVoice',
                'stopVoice',
                'onVoicePlayEnd',
                'uploadVoice',
                'downloadVoice',
                'chooseImage',
                'previewImage',
                'uploadImage',
                'downloadImage',
                'translateVoice',
                'getNetworkType',
                'openLocation',
                'getLocation',
                'hideOptionMenu',
                'showOptionMenu',
                'hideMenuItems',
                'showMenuItems',
                'hideAllNonBaseMenuItem',
                'showAllNonBaseMenuItem',
                'closeWindow',
                'scanQRCode',
                'chooseWXPay',
                'openProductSpecificView',
                'addCard',
                'chooseCard',
                'openCard'
            ]
        });
    },
    init: function() {
        var url = location.href;
        $.ajax({
            type: "GET",
            url: getRootPath() + "/weixin/getJSApiSign",
            data: {
                url: url
            },
            success: function(data) {
                wxInit.data.signature = data.data.signature;
                wxInit.data.appId = data.data.appId;
                wxInit.data.timestamp = data.data.timestamp;
                wxInit.data.nonceStr = data.data.nonceStr;
                wxInit.wxConfig();
            }
        });
    }
};


var wxInit_promise = {
    API_LIST:['chooseImage','previewImage','uploadImage','downloadImage','openLocation','getLocation','hideOptionMenu','showOptionMenu','hideMenuItems','showMenuItems','hideAllNonBaseMenuItem','showAllNonBaseMenuItem','closeWindow','scanQRCode'],
    _getAuth:function(){
         var def = $.Deferred()
         var url = location.href
         ajax.get(getRootPath() + "/weixin/getJSApiSign",{url:url})
            .then(function(data){
                if(data.returnCode !== ERR_OK){
                    def.reject(data)
                }
                def.resolve(data)
            })
            .fail(function(error){
                def.reject(error)
            })
            return def.promise()
    },
    init:function(){
        var def = $.Deferred()
        wxInit_promise._getAuth().then(function(data){
            wx.config({
                debug: false,
                appId: data.data.appId,
                timestamp: data.data.timestamp,
                nonceStr: data.data.nonceStr,
                signature: data.data.signature,
                jsApiList:wxInit_promise.API_LIST
            })
            wx.ready(function(){
                def.resolve()
            })
            wx.error(function(error){
                def.reject(error)
            })
        }).fail(function(error){
            def.reject(error)
        })
        return def.promise()
    },
    wxUploadImage:function() {
        var def = $.Deferred()
        if (IS_DEVENV) {
            def.resolve(wxInit_promise.dev_getRandomPic())
        }
        wxInit_promise._wxUploadImageAlies._wxChoseImage().then(function (res) {
            return wxInit_promise._wxUploadImageAlies._wxUploadImage(res.localIds[0])
        }).then(function (res) {
            return wxInit_promise._wxUploadImageAlies._getImageSrc(res.serverId)
        }).then(function (src) {
            def.resolve(src)
        }).fail(function (err) {
            def.reject(err)
        })
        return def.promise()
    },
    wxScanBarCode:function() {
        var def = $.Deferred()
        if (IS_DEVENV) {
            def.resolve({resultStr:'barcode=1234567891234556'})
        }
        wx.scanQRCode({
            needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            scanType: ["barCode"], // 可以指定扫二维码还是一维码
            success: function (res) {
                def.resolve(res)
            },
            fail:function(error){
                def.reject(error)
            }
        });
        return def.promise()
    },
    checkApi:function(checkList) {
        var def = $.Deferred()
        wx.checkJsApi({
            jsApiList:checkList,
            success:function(res) {
                def.resolve(res.checkResult)
            },
            fail:(function(error){
                def.reject(error)
            })
        })
        return def.promise()
    },
    _wxUploadImageAlies : {
        _wxChoseImage: function () {
            var def = $.Deferred()
            wx.chooseImage({
                count: 1,
                sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                success: function (res) {
                    def.resolve(res)
                },
                fail: function (err) {
                    def.reject(err)
                }
            })
            return def.promise()
        },
        _wxUploadImage: function (localId) {
            var def = $.Deferred()
            wx.uploadImage({
                localId: localId,
                isShowProgressTips: 1,
                success: function (res) {
                    def.resolve(res)
                },
                fail: function (err) {
                    def.reject(err)
                }
            })
            return def.promise()
        },
        _getImageSrc: function (serverId) {
            var def = $.Deferred()
            ajax.get(rootPath + '/weixin/madia/downLoadWechatMedia', { media_id: serverId })
                .then(function (data) {
                    if (data.returnCode !== 1) {
                        def.reject({ errMsg: data.returnMsg })
                    }
                    def.resolve(data.data)
                })
                .fail(function (err) {
                    def.reject({ errMsg: "网络错误" })
                })
            return def.promise()
        }
    },
    dev_getRandomPic:function() {
        var testImages = ["https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517226847470&di=452a2dd9d0712992b62a8ec2a5bf118f&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20160124%2FImg435671677.jpg"]
        // return testImages[parseInt(Math.random() * 10)]
        return testImages[0]
    }
}
//屏蔽微信右上角分享功能
function shieldShare() {
    function onBridgeReady() {
        WeixinJSBridge.call('hideOptionMenu');
    }
    if (typeof WeixinJSBridge == "undefined") {
        if (document.addEventListener) {
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        } else if (document.attachEvent) {
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    } else {
        onBridgeReady();
    }
}


//绑定公用按钮触摸效果时间
(function(window) {
    var btns = document.querySelectorAll(".qy-btn-400,.qy-btn-all,.qy-btn-180");
    for (var i = 0; i < btns.length; i++) {
        btns[i].addEventListener("touchstart", function(e) {
            this.classList.toggle("onTouch")
        })
        btns[i].addEventListener("touchend", function(e) {
            this.classList.toggle("onTouch")
        })
    }

    if ($.toast) {
        $.toast.prototype.defaults.duration = 1000;
    }

})(window)



/**
 * 弹出确认取消弹框
 * [confirmModal.init description]
 * @type {function}
 * param  object{
 *             text:要在弹框中显示的内容
 *             tip:显示提示
 *             textArea：boolean 是否启用输入框 默认false
 *             callback:function(fnCloseModal){}//点击确认后的回调函数，会传一个关闭弹框的函数作为参数
 *             }
 *             如果textArea为true 则callback中传入的参数为（fncloseModal,textAreaText）
 */
var confirmModal = {
    htmlStr: '<div class="mainContainer">' +
        '<div class="mcmheader">' +
        '<i onclick=confirmModal.closeModal()>X</i>' +
        '</div>' +
        '<div class="mcmbody">' +
        '<p class="mcmText">确认删除吗？</p>' +
        '<p class="tipText">1111</p>' +
        '<div class="btnBox">' +
        '<button class="cancel" onclick=confirmModal.closeModal()>取消</button>' +
        '<button class="confirm" onclick=confirmModal.confirmClick()>确认</button>' +
        '</div>' +
        '</div>' +
        '</div>',

    htmlStrWithTextArea: '<div class="mainContainer">' +
        '<div class="mcmheader">' +
        '<i onclick="confirmModal.closeModal()">X</i>' +
        '</div>' +
        '<div class="mcmbody">' +
        '<p class="mcmText">消息提示</p>' +
        '<p class="tipText">tips</p>' +
        '<div class="textAreaBox">' +
        '<div style="border:1px solid #000">' +
        '<textarea placeholder="请输入原因,1-30个字(必填)" maxlength="30"></textarea>' +
        '</div>' +
        '</div>' +
        '<div class="btnBox">' +
        '<button class="cancel" onclick="confirmModal.closeModal()">取消</button>' +
        '<button class="confirm" onclick="confirmModal.confirmClick()">确认</button>' +
        '</div>' +
        '</div>' +
        '</div>',

    dom: {
        modal: null,
        textDom: null,
        tipDom: null
    },

    renderObj: {
        text: "提示信息",
        tip: "",
        textArea: false,
        callback: null
    },

    init: function(obj) {
        for (var key in obj) {
            confirmModal.renderObj[key] = obj[key];
        };
        var needTextArea = confirmModal.renderObj.textArea;

        if (!document.querySelector("#mobileConfirmModal")) {
            var myElement = document.createElement("div");
            myElement.setAttribute("id", "mobileConfirmModal");
            if (needTextArea) {
                myElement.innerHTML = confirmModal.htmlStrWithTextArea;
            } else {
                myElement.innerHTML = confirmModal.htmlStr;
            }
            document.querySelector("body").appendChild(myElement);
        };
        confirmModal.dom.modal = document.querySelector("#mobileConfirmModal");
        confirmModal.dom.textDom = document.querySelector(".mcmText");
        confirmModal.dom.tipDom = document.querySelector(".tipText");

        confirmModal.dom.textDom.innerHTML = confirmModal.renderObj.text;
        confirmModal.dom.tipDom.innerHTML = confirmModal.renderObj.tip;
        confirmModal.dom.modal.classList.add("show");
    },

    closeModal: function() {
        confirmModal.dom.modal.classList.remove("show")
    },

    confirmClick: function() {
        if (confirmModal.renderObj.callback) {
            if (confirmModal.renderObj.textArea) {
                var textAreaDom = confirmModal.dom.modal.querySelector("textarea")
                var textStr = textAreaDom.value
                confirmModal.renderObj.callback(confirmModal.closeModal, textStr)
            } else {
                confirmModal.renderObj.callback(confirmModal.closeModal)
            }
        } else {
            return;
        }
    }
}

// 百度地图相关方法
var baiduMapUtils = {
    jsonpAjax: function(url, data, callback) {
        data.ak = "PeQf3WDGEeajR3Lhzw2VDUbygzuilGXW";
        data.output = "json"
        $.ajax({
            type: "GET",
            url: url,
            async: false,
            data: data,
            dataType: "jsonp",
            contentType: "application/json;utf-8",
            success: function(data) {
                if (callback) {
                    callback(data)
                }
            }
        })
    },

    convertGPSCood: function(coodsArr, callback) {
        var coords = coodsArr.join(";");
        baiduMapUtils.jsonpAjax("http://api.map.baidu.com/geoconv/v1/", {
            coords: coords,
            from: 1,
            to: 5
        }, callback)
    },

    getCoodByAddress: function(address, type, callback) {
        baiduMapUtils.jsonpAjax("http://api.map.baidu.com/geocoder/v2/", {
            address: address,
            ret_coordtype: type || "gcj02ll"
        }, callback)
    },

    getDistance: function(startCood, endCood, callback) {
        baiduMapUtils.jsonpAjax("http://api.map.baidu.com/routematrix/v2/driving", {
            origins: startCood,
            destinations: endCood,
            tactics: 11
        }, callback)
    }
}

//预约时间相关方法；
var chooseOrderTime = {
    init: function(obj) {
        var oDate = new Date();
        var dObj = {
            timeArr: [],
            timeArr1: ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00'],
            getData: '',
            cb1: null,
            cb2: null
        };
        for (var key in obj) {
            dObj[key] = obj[key]
        };

        dObj.getData = chooseOrderTime.getShowDate()
        chooseOrderTime.changeOrderAction(dObj)
    },

    //获取当前日期
    getShowDate: function() {
        var oDate = new Date();
        var dataArr = getFutureDate(7);
        var values = [];
        var displayValues = [];
        var weekday = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]
        $.each(dataArr, function(i, v) {
            if (i != 0) {
                var month = v.getMonth() + 1;
                var day = v.getDate();
                if (oDate.getHours() >= 18) {
                    day = v.getDate() + 1
                    weekday = ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"]
                }

                month = month < 10 ? ("0" + month) : (month + "");
                day = day < 10 ? ("0" + day) : (day + "");
                var disStr = month + "月" + day + "日[" + weekday[v.getDay()] + "]"
                displayValues.push(disStr);
                var valStr = v.getFullYear() + "-" + month + "-" + day;
                values.push(valStr)
            }
        })

        return {
            values: values,
            displayValues: displayValues
        }
    },

    //初始化更改时间action框
    changeOrderAction: function(pram) {
        console.log(pram)
        var oDate = new Date();

        var currentHour = oDate.getHours() //oDate.getHours();
        var currentDate = oDate.getDate();
        // pram.timeArr =[],
        // pram.timeArr1=['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00'];
        if (currentHour <= 12 && currentHour > 10) {
            pram.timeArr = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
        } else if (currentHour <= 14 && currentHour > 12) {
            pram.timeArr = ['12:00-14:00', '14:00-16:00', '16:00-18:00']
        } else if (currentHour <= 16 && currentHour > 14) {
            pram.timeArr = ['14:00-16:00', '16:00-18:00']
        } else if (currentHour <= 18 && currentHour > 16) {
            pram.timeArr = ['16:00-18:00']
        } else {
            pram.timeArr = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
        }
        window.picker_prevValue = pram.getData.values[0]
        $("#changeOrder").picker({
            cols: [{
                textAlign: 'center',
                values: pram.getData.values,
                displayValues: pram.getData.displayValues
            }, {
                textAlign: 'center',
                values: pram.timeArr
            }],
            onClose: function(e) {
                if (pram.cb1) {
                    pram.cb1(e, pram)
                }
            },
            onChange: function(e, value, displayValue) {
                if (pram.cb2) {
                    pram.cb2(e, pram)
                }
            }
        });
    }

}


//es5兼容 Object.assign()方法
if (typeof Object.assign != 'function') {
    Object.assign = function(target) {
      'use strict';
      if (target == null) {
        throw new TypeError('Cannot convert undefined or null to object');
      }

      target = Object(target);
      for (var index = 1; index < arguments.length; index++) {
        var source = arguments[index];
        if (source != null) {
          for (var key in source) {
            if (Object.prototype.hasOwnProperty.call(source, key)) {
              target[key] = source[key];
            }
          }
        }
      }
      return target;
    };
  }