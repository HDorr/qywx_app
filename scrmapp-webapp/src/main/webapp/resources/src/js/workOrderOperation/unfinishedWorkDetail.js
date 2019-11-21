var oDate = new Date();

var glData = {
    userId: $("#userIdInput").val() || getUrlParam("userId"),
    ordersCode: getUrlParam("ordersCode") || $("#ordersCodeInput").val(),
    isChange: getUrlParam("isChange") || 0,
    dateRes: getShowDate(),
    canNav: false
};

function ufDetailAjaxQuery(callback) {
    var data = {
        ordersCode: glData.ordersCode
    }
    var def = $.Deferred()
    _setTimeOut()
        .then(function(){
            return ajax.get(queryUrls.qyhQueryUFinedOrderDetail,data)
        })
        .then(function(data){
            if(data.returnCode !== ERR_OK){
                alertMsg.error(data)
                return
            }
            data.data.description = data.data.description || "无";
            data.data.productBarCode = data.data.productBarCode || "无";
            if(callback)callback(data.data)
            def.resolve(data.data)
        })
        .fail(function(error){
            alertMsg.error(error)
        })
    return def.promise()
}

function ufRender(data) {
    var def = $.Deferred()
    var data = _normalizeRenderData(data) 
    var headBarHtml = template('headBarTemplate',data)
        customInfoHtml = template('customInfoTemplate',data)
        orderInfoHtml = template('orderInfoTempalte',data)
        pdtInfoHtml = template('pdtInfoTemplate',data)
        statusStreamHtml = template('statusStreamTemplate',data)
    var html = headBarHtml + customInfoHtml + orderInfoHtml + pdtInfoHtml + statusStreamHtml
    $(".main_layer").html(html)
    def.resolve()
    return def.promise()
}

//图片预览
function previewImage(event) {
    var src = event.target.src
    wx.previewImage({
        current:src,
        urls:[src]
    })
   /*  $('.imgBox .images img').on('click', function(event) {
        var imgArray = [];
        var curImageSrc = $(this).attr('src');
        var oParent = $(this).parent();
        if (curImageSrc && !oParent.attr('href')) {
            $('.imgBox img').each(function(index, el) {
                var itemSrc = $(this).attr('src');
                imgArray.push(itemSrc);
            });
            wx.previewImage({
                current: curImageSrc,
                urls: imgArray
            });
        }
    }); */
}

//拒绝工单
function refuseOrder(el) {
    var that = $(el);
    confirmModal.init({
        text: "确认拒绝工单吗？",
        tip: "请先联系您所在的服务网点<br/>服务网点电话 : 021-61522809",
        textArea: true,
        orderType: that.data("ordertype"),
        callback: function(closeFn, reason) {
            //判断textarea里的值是否为空
            var text = reason.trim();
            if (text == "请选择原因") {
                $.toptip("必须选择原因", 2000, 'error');
                return;
            }
            closeFn();
            var data = {
                userId: glData.userId,
                ordersCode: that.data("orderscode") || glData.ordersCode,
                ordersId: that.data("ordersid"),
                ordersType: that.data("ordertype"),
                contacts: that.data("contacts"),
                reason: text
            }
            ajax.post(queryUrls.qyhQueryRefuseOrder,data)
                .then(function(data){
                    if(data.returnCode !== ERR_OK){
                        alertMsg.error(data)
                        return 
                    }
                    location.href = pageUrls.unfinishedWorkOrderList + "?userId="+glData.userId + "&condition=today"
                })
                .fail(function(error){
                    alertMsg.error(error)
                })
        }
    })
}

//完成工单
function completeOrder(el) {
    var $el = $(el)
    var params = {orderType:$el.data("ordertype"),userId:glData.userId,ordersCode:glData.ordersCode}
    var url = ''
    switch(params.orderType){
        case 1 :
            url = pageUrls.unfinishedWorkOrderDetail_install_detail
            break;
        case 2 :
            url = pageUrls.unfinishedWorkOrderDetail_repair
            break;
        case 3 :
            url = pageUrls.unfinishedWorkOrderDetail_maintain
            break
        case 4 :
            url = pageUrls.unfinishedWorkOrderDetail_maintain
            break
    }
    url = getParamStr(url,params)
    location.href = url;
}

function getShowDate() {
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
}

//初始化更改时间action框
function changeOrderAction() {
    var currentHour = oDate.getHours() //oDate.getHours();
    var currentDate = oDate.getDate();
    var timeArr = [];
    var timeArr1 = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00'];
    if (currentHour < 12 && currentHour >= 10) {
        timeArr = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
    } else if (currentHour < 14 && currentHour >= 12) {
        timeArr = ['12:00-14:00', '14:00-16:00', '16:00-18:00']
    } else if (currentHour < 16 && currentHour >= 14) {
        timeArr = ['14:00-16:00', '16:00-18:00']
    } else if (currentHour < 18 && currentHour >= 16) {
        timeArr = ['16:00-18:00']
    } else {
        timeArr = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
    }
    window.picker_prevValue = glData.dateRes.values[0]
    $("#changeOrder").picker({
        cols: [{
            textAlign: 'center',
            values: glData.dateRes.values,
            displayValues: glData.dateRes.displayValues
        }, {
            textAlign: 'center',
            // values: ['10:00', '12:00', '14:00', '16:00'],
            values: timeArr //['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
        }],
        onClose: function() {
            glData.isChange = 0;
            var that = $("#changeOrder");
            var updateTime = that.val();
            confirmModal.init({
                text: "确认更改预约为<br/>" + updateTime + "?",
                tip: "友情提示：<br/>更改前请跟客户协商达成一致！",
                textArea: true,
                orderType: 4,
                callback: function(fn, reason) {
                    //判断textarea里的值是否为空
                    var text = reason.trim();
                    if (!text) {
                        $.toptip("必须输入原因", 2000, 'error');
                        return;
                    }
                    fn();
                    var data = {
                        userId: glData.userId,
                        ordersCode: that.data("orderscode") || glData.ordersCode,
                        ordersId: that.data("ordersid"),
                        ordersType: that.data("ordertype"),
                        contacts: that.data("contacts"),
                        oldTime: that.data("oldtime"),
                        updateTime: updateTime,
                        reason: text
                    }
                    ajax.post(queryUrls.qyhQueryChangeOrder,data)
                        .then(function(data){
                            if(data.returnCode !== ERR_OK){
                                alertMsg.error(data)
                                return
                            }
                            return ufDetailAjaxQuery()
                        })
                        .then(function(data){
                            return ufRender(data)
                        })
                        .then(function(){
                            changeOrderAction()
                        })
                        .fail(function(error){
                            alertMsg.error(error)
                        })                
                }
            })
        },
        onChange: function(e, value, displayValue) {
            if (e.value[0] == picker_prevValue) {
                return false;
            } else {
                picker_prevValue = e.value[0]
                if (e.cols[0].displayValue == glData.dateRes.displayValues[0]) {
                    e.cols[1].replaceValues(timeArr, timeArr)
                    e.updateValue();
                } else {
                    e.cols[1].replaceValues(timeArr1, timeArr1)
                    e.updateValue();
                }
            }
            e.updateValue();
        }
    });
}

//调用微信地图去导航
function gotoNav(el) {
    if (glData.canNav) {
        $.showLoading();
        var address = $(el).data("useraddress");
        baiduMapUtils.getCoodByAddress(address, "", function(data) {
            if (data.status == 0) {
                $.hideLoading();
                var lat = data.result.location.lat;
                var lng = data.result.location.lng;
                wx.openLocation({
                    latitude: lat, // 纬度，浮点数，范围为90 ~ -90
                    longitude: lng, // 经度，浮点数，范围为180 ~ -180。
                    name: address, // 位置名
                    address: '', // 地址详情说明
                    scale: 13, // 地图缩放级别,整形值,范围从1~28。默认为最大
                    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                });
            } else {
                $.hideLoading();
                $.toptip('获取地址失败', 'error');
            }
        })
    } else {
        $.alert("尚不能启动导航，请稍后再试")
    }
}

function _normalizeRenderData(data){
    if (data.orderTime) {
        data.splitData = data.orderTime.split(" ");
    } else {
        data.splitData = "none"
    }
    data.wechatOrdersRecordList = data.wechatOrdersRecordList.reverse()
    data.btnStatus = {canRefuse:1,canChangeTime:1,canComplete:1}
    return data
}

function _setTimeOut(delay){
    var def = $.Deferred()
    var delay = delay || 400
    setTimeout(function(){
        def.resolve()
    },delay)
    return def.promise()
}

(function($, window) {
    ufDetailAjaxQuery()
        .then(function(data){
            return ufRender(data)
        })
        .then(function(){
            changeOrderAction()
            if (glData.isChange == 1) {
                $("#changeOrder").picker("open");
            }
        })

    wxInit_promise.init()
        .then(function(){
            return wxInit_promise.checkApi(['openLocation'])
        })
        .then(function(checkResult){
            if(checkResult.openLocation){
                glData.canNav = true
            }else{
                $.toptip("当前手机不支持导航功能", "warning")
            }
        })
        .fail(function(error){
            alertMsg.error(error)
        })
})(jQuery, window)