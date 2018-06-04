var oDate = new Date();

var glData = {
    ordersCode: getUrlParam("ordersCode") || $('#ordersCodeInput').val() || "",
    changeOrder: getUrlParam("changeOrder"),
    userId: $('#userIdInput').val() || "",
    dateRes: getShowDate(),
    orderType: null,
    data: getShowDate()
};
wxInit.init();

(function($, window) {
    queryDetail(renderDetail)
})(jQuery, window)



function queryDetail(callback) {
    $.ajax({
        url: queryUrls.orderDetail,
        type: "GET",
        data: _getQueryParams(glData),
        success: function(data) {
            // var data = JSON.parse(data);
            if (data.returnCode === 1) {
                if(callback) callback(data.data)
            } else {
                $.toast(data.returnMsg, "cancel")
            }
            previewImage()
        },
        error: function(data) {
            $.toast("网络错误", "cancel")
        }
    })
}

function renderDetail(data) {
    for (var key in data) {
        data[key] = data[key] ? data[key] : "无"
    }
    glData.orderType = data.orderType;
    var orderTimeArr = data.orderTime.split(" ");
    //获取预约进度
    var str1 = '<section class="header qy-blue">' +
        '            <div class="pull-left">' + data.orderTypeName + '：<span>' + data.orderStatus + '</span></div>' +
        '            <div class="pull-right">预约时间：' +
        '                <p>' +
        '                    <span>' + orderTimeArr[0] + '</span>' +
        '                    <span>' + orderTimeArr[1] + '</span>' +
        '                </p>' +
        '            </div>' +
        '        </section>' +
        ' <section class="acceptRecord">' +
        '            <div class="oDHeader">' +
        '                <p class="pull-left"><img src="' + rootPath + '/resources/src/images/icons/waitForAccept.png">受理记录</p>' +
        '            </div>' +
        '            <ul class="recordStream">';

    var orList = data.wechatOrdersRecordList;
    for (var i = orList.length - 1; i >= 0; i--) {
        str1 += '<li>' +
            '       <div class="streamLeft">' +
            '           <p class="upperLine"></p>' +
            '           <p class="circle"></p>' +
            '           <p class="dnLine"></p>' +
            '       </div>' +
            '       <div class="streamRight">' +
            '           <p>' + orList[i].recordContent + '</p>' +
            '           <p>' + orList[i].recordTime + '</p>' +
            '       </div>' +
            '   </li>';
    }
    data.productImage = data.productImage || rootPath + "/resources/images/defaultPdtImg.jpg";
    str1 += ' </ul>' +
        '        </section>' +
        '        <section class="customInfo">' +
        '            <div class="oDHeader">' +
        '                <p class="pull-left"><img src="' + rootPath + '/resources/src/images/icons/personInfo.png">用户信息</p>' +
        '            </div>' +
        '            <div class="textBox">' +
        '                <div class=" line clearfix">' +
        '                    <p class="leftText">联系信息：</p>' +
        '                    <p class="rightText">' + data.userName + '，' + data.userMobile + '</p>' +
        '                </div>' +
        '                <div class=" line clearfix">' +
        '                    <p class="leftText">服务地址：</p>' +
        '                    <p class="rightText">' + data.userAddress + '</p>' +
        '                </div>' +
        '            </div>' +
        '        </section>' +
        '        <section class="orderInfo">' +
        '            <div class="oDHeader">' +
        '                <p class="pull-left"><img src="' + rootPath + '/resources/src/images/icons/order.png">预约信息</p>' +
        '            </div>' +
        '            <div class="textBox">' +
        '                <div class=" line clearfix">' +
        '                    <p class="leftText">预约时间：</p>' +
        '                    <p class="rightText">' + data.orderTime + '</p>' +
        '                </div>' +
        '                <div class=" line clearfix">' +
        '                    <p class="leftText">安装描述：</p>' +
        '                    <p class="rightText">' + data.description + '</p>' +
        '                </div>' +
        '                <div class="imgBox">'+
        '                   <span class="pull-left">维修照片：</span>'+
        '                   <span class="pull-left img">'+
        '                       <img src="" alt="">'+
        '                   </span>'+
        '                   <span class="pull-left img">'+
        '                       <img src="" alt="">'+
        '                   </span>'+
        '               </div>'+
        '            </div>' +
        '        </section>' +
        '        <section class="pdtInfo">' +
        '            <div class="oDHeader">' +
        '                <p class="pull-left"><img src="' + rootPath + '/resources/src/images/icons/pdtInfo.png">产品信息</p>' +
        '            </div>' +
        '            <div class="textBox">' +
        '                <div class="line">' +
        '                    <p class="pdtName">' + data.productName + '</p>' +
        '                </div>' +
        '                <div class=" line clearfix">' +
        '                    <p class="leftText">产品型号：</p>' +
        '                    <p class="rightText">' + data.modelName + '</p>' +
        '                </div>' +
        '                <div class=" line clearfix">' +
        '                    <p class="leftText">产品条码：</p>' +
        '                    <p class="rightText">' + data.productBarCode + '</p>' +
        '                </div>' +
        '                <div class="pdtImgBox">' +
        '                    <div class="pdtImg" style="background-image: url(\'' + data.productImage + '\');"></div>' +
        '                </div>' +
        '            </div>' +
        '            <div class="btnBox clearfix">' +
        '                <button class="qy-btn-180 pull-right" id="changeOrder" data-username="' + data.userName + '">更改预约</button>' +
        '                <button class="qy-btn-cancel pull-right" id="cancelOrder" onclick="cancelOrder(this)" data-username="' + data.userName + '">取消预约</button>' +
        '            </div>' +
        '        </section>';

    $(".main_layer").css("padding-top", "0").html(str1);

    if (data.faultImage&&data.faultImage.length>10) {
        faultImage = data.faultImage.split(',');
        for (var i = 0; i < faultImage.length; i++) {
            $('.imgBox .img img').eq(i).attr('src', faultImage[i]);
            $('.imgBox .img').eq(i).css('display', 'inline-block');
        }
        $('.orderInfo .imgBox').css('display', 'block');
    }
    

    //更改预约时间框
    changeOrderAction();

    //根据status来控制按钮的显示
    var changeBtn = $("#changeOrder"),
        cancelBtn = $("#cancelOrder");
    //处理中的状态 data.status:1:处理中  2:已取消  3:重新处理中  4:已接单 5:服务完成 6:评价完成
    if (data.status == 2 || data.status == 6 || data.status == 5) {
        changeBtn.hide();
        cancelBtn.hide();
    }

    //如果是点击更改时间过来的话 直接弹出选择预约时间
    if (glData.changeOrder == 1) {
        $("#changeOrder").picker("open");
    }
}


//图片预览
function previewImage() {
    $('.imgBox img').on('click', function(event) {
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
    });
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
        onClose: function() {
            var updateTime = $("#changeOrder").val();
            var contacts = $("#changeOrder").data("username");
            setTimeout(function() {
                $.confirm({
                    title: '更改预约',
                    text: '确认修改时间为:' + updateTime + "?",
                    onOK: function() {
                        setTimeout(function() {
                            var updateTime = $("#changeOrder").val();
                            var contacts = $("#changeOrder").data("username");
                            $.ajax({
                                type: "POST",
                                data: {
                                    ordersCode: glData.ordersCode,
                                    updateTime: updateTime,
                                    contacts: contacts
                                },
                                url: queryUrls.orderUpdate,
                                success: function(data) {;
                                    if (data.returnCode === 1) {
                                        $.toast(data.returnMsg, function() {
                                            queryDetail(renderDetail);
                                            glData.changeOrder = null;
                                        })
                                    } else {
                                        $.toast(data.returnMsg, "cancel")
                                    }
                                },
                                error: function(data) {
                                    $.toast("网络错误", "cancel")
                                }
                            })
                        }, 500)
                    }
                });
            }, 500)
        },
        onChange: function(e, value, displayValue) {
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


//取消预约
function cancelOrder(ele) {
    var contacts = $(ele).data("username");
    $.confirm({
        title: '取消预约',
        text: '确认取消预约？',
        onOK: function() {
            setTimeout(function() {
                $.showLoading();
                $.ajax({
                    url: queryUrls.orderCancel,
                    type: "POST",
                    data: {
                        ordersCode: glData.ordersCode,
                        contacts: contacts
                    },
                    success: function(data) {
                        $.hideLoading();
                        setTimeout(function() {
                            if (data.returnCode === 1) {
                                $.toast(data.returnMsg, function() {
                                    location.href = pageUrls.orderList;
                                })
                            } else {
                                $.toast(data.returnMsg, "cancel")
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
            }, 400)

        },
        onCancel: function() {}
    });
}

function _getQueryParams(data){
    var res = {}
    if(data.ordersCode) res.ordersCode = data.ordersCode
    if(data.userId) res.userId = data.userId
    return res    
}