var glData = {
    ordersCode: getUrlParam("ordersCode") || null,
    changeOrder: getUrlParam("changeOrder") || null,
    dateRes: getShowDate(),
    orderType: null,
    data: getShowDate()
};
// wxInit.init();

(function ($, window) {
    queryDetail(renderDetail)
    wxInit_promise.init().then(function(){
        console.log("微信jssdk就绪")
    }).fail(function(error){
        // alertMsg.error(error)
    })
})(jQuery, window)



function queryDetail(callback) {
    ajax.get(queryUrls.orderDetail, { ordersCode: glData.ordersCode })
        .then(function (data) {
            if (data.returnCode !== 1) {
                $.alert(data.returnMsg, "错误")
                return
            }
            if (callback) {
                callback(data.data)
            }
        }).fail(function (err) {
            $.alert("网络错误", "错误")
        })
}

function renderDetail(data) {
    glData.orderType = data.orderType
    /* 标题 */
    data.orderTimeArr = data.orderTime?data.orderTime.split(" "):"";
    data = _normalizeDataByOrderType(data)
    var titleHtml = template('title_template', data)
    /* 受理进程 */
    if(data.wechatOrdersRecordList)data.wechatOrdersRecordList.reverse()
    var acceptRecordHtml = template('accept_record_template', data)
    /* 用户信息 */
    var userInfoHtml = template('userInfo_template', data)
    /* 预约信息 */
    if (data.faultImage) { data.faultImageArray = data.faultImage.split(',') }
    var orderInfoHtml = template('orderInfo_template', data)
    /* 产品信息 */
    var pdtInfoHtml = template('pdtInfo_template', data)

    var resHtml = titleHtml + acceptRecordHtml + userInfoHtml + orderInfoHtml + pdtInfoHtml
    $('.main_layer').html(resHtml)

    //更改预约时间框
    changeOrderAction();

    //如果是点击更改时间过来的话 直接弹出选择预约时间
    if (glData.changeOrder == 1) {
        $("#changeOrder").picker("open");
    }
}

//取消预约
function cancelOrder(ele) {
    var contacts = $(ele).data("username");
    _comfirmAlert("取消预约", "确认取消预约？")
        .then(function () {
            return ajax.post(queryUrls.orderCancel, { ordersCode: glData.ordersCode, contacts: contacts })
        })
        .then(function (data) {
            if (data.returnCode !== ERR_OK) {
                $.alert(data.returnMsg, '错误')
                return 
            } 
            $.toptip('取消成功','success')
            setTimeout(function(){
                location.href = pageUrls.orderList
            }, 1000)
        })
        .fail(function (err) {
            $.alert(err.message || "网络错误", "错误")
        })
}

//更改预约
function updateOrderTime() {
    var updateTime = $("#changeOrder").val();
    var contacts = $("#changeOrder").data("username");
    _setTimeOut()
    .then(function(){
        return _comfirmAlert('更改预约', '确认修改时间为:' + updateTime + "?")
    })
    .then(function () {
        var updateTime = $("#changeOrder").val();
        var contacts = $("#changeOrder").data("username");
        return ajax.post(queryUrls.orderUpdate, { ordersCode: glData.ordersCode, updateTime: updateTime, contacts: contacts })
    })
    .then(function (data) {
        if(data.returnCode !== ERR_OK){
            $.alert(data.returnMsg, "错误")
            return 
        }
        queryDetail(renderDetail);
        glData.changeOrder = null;
        $.toptip(data.returnMsg, 'success')
    })
    .fail(function (err) {
        $.alert(err.message || "网络错误", "错误")
    })
}


//图片预览
function previewImage(src) {
    wx.previewImage({
        current: src,
        urls: [src]
    });
}

function getShowDate() {
    var dataArr = getFutureDate(7);
    var values = [];
    var displayValues = [];
    var weekday = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]
    $.each(dataArr, function (i, v) {
        if (i != 0) {
            var month = v.getMonth() + 1;
            var day = v.getDate();

            if (new Date().getHours() >= 18) {
		var tmpV= new Date();
		tmpV.setTime(v.getTime()+24*60*60*1000);
		day = tmpV.getDate();
		month = tmpV.getMonth() + 1;
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
    var oDate = new Date()
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
    window.picker_prevValue = glData.data.values[0]
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
        onClose: updateOrderTime,
        onChange: function (e, value, displayValue) {
            if (e.value[0] == picker_prevValue) {
                return false;
            } else {
                picker_prevValue = e.value[0]
                if (e.cols[0].displayValue == glData.data.displayValues[0]) {
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


function _normalizeDataByOrderType(data) {
    switch (data.orderType) {
        case 1://安装单
            data.descTitle = '安装描述：'
            break
        case 2:
            data.descTitle = ''
            break
        case 3:
            data.descTitle = '保养描述：'
            break
    }
    return data
}

function _comfirmAlert(title, text) {
    var def = $.Deferred()
    $.confirm({
        title: title,
        text: text,
        onOK: function () {
            setTimeout(function(){
                def.resolve()
            },250)          
        }
    })
    return def.promise()
}


function _setTimeOut(delayTime) {
    var delayTime = delayTime || 200
    var def = $.Deferred()
    setTimeout(function(){
        def.resolve()
    },delayTime)
    return def.promise()
}


