/*
* @Author: Administrator
* @Date:   2017-04-23 10:35:10
* @Last Modified by:   Administrator
* @Last Modified time: 2017-05-26 18:23:22
*/
/*
* @Author: Administrator
* @Date:   2017-04-23 15:59:05
* @Last Modified by:   Administrator
* @Last Modified time: 2017-04-24 10:56:58
*/

var flags = {
    submitFlag: true
};

(function ($, window) {
    wxInit.init();
    shieldShare();
    var oDate = new Date();
    var getData = {
        data: getShowDate()
    };
    // var timeArr =[],
    // timeArr1=['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
    var rootUrl = getRootPath();
    var orderType = getUrlParam("orderType");
    var flag = getUrlParam("flag");
    var productId = getUrlParam("productId");
    var addressId = getUrlParam("addressId");
    var addressData = '';
    var productData = '';
    var reserveData = {
        'productId': null,//产品编号
        'typeName': null,//产品大类
        'modelName': null,//产品型号
        'productBarCode': null,//产品条码
        'shoppingOrder': null,//网购单号
        'itemKind': null,//产品组织
        'contacts': null,//联系人
        'contactsMobile': null,//联系电话
        'buyChannel': null,//销售渠道
        'province': null,//省名称
        'city': null,//市名称
        'area': null,
        'address': null,//街道名称
        'orderType': null,//预约类型 1.安装 2.维修 3.保养
        'orderTime': null,//预约时间
        'description': null,//描述
        'faultImage': null,//如果上传了图片，需传图片地址
    };


    var productInfo = {
        productId: productId
    };
    var addressInfo = {
        id: addressId
    };
    changeOrderAction()

    // var obj ={
    //     cb1:function(e,fn){
    //         $('.content').css('paddingBottom', '0.5rem');
    //         $("#changeOrder").text($("#changeOrder").val())
    //     },
    //     cb2:function(e,fn){
    //        if(e.value[0]==picker_prevValue){
    //             return false;
    //        }else{
    //             picker_prevValue = e.value[0]
    //             if (e.cols[0].displayValue==fn.getData.displayValues[0]) {
    //                 e.cols[1].replaceValues(fn.timeArr,fn.timeArr)
    //                 e.updateValue();
    //             }else{
    //                 e.cols[1].replaceValues(fn.timeArr1,fn.timeArr1)
    //                 e.updateValue();
    //             }
    //         }
    //         e.updateValue();
    //     }
    // }  

    // chooseOrderTime.init(obj)    


    //加载产品信息；
    $.ajax({
        url: rootUrl + '/scrmapp/consumer/product/get/baseInfo',
        type: 'GET',
        data: productInfo,
        success: function (res) {
            productData = res.data;
            if (res.returnCode == 1) {
                pdtInfo(productData)
            } else {
                $.toast(res.returnMsg, "cancel")
            }

        }
    });

    function pdtInfo(data) {
        var str = '<div class="pdtInfo" id="productInfo">' +
            '<div class="pdtImg pull-left" style="background-image: url(' + productData.productImage + ')"></div>' +
            '<div class="pdtText pull-left">' +
            '<p>' + productData.productName + '</p>' +
            '<p>产品条码：<span>' + productData.productBarCode + '</span></p>' +
            '<p>产品型号：<span>' + productData.modelName + '</span></p>' +
            '</div>' +
            '</div>'


        $('.addProductBtn').eq(0).replaceWith(str)
    };


    //加载用户信息；
    if (addressId) {
        function userInfo(addressData) {
            var str = '<div class="userInfo" id="userInfo">' +
                '<div class="userImg  pull-left"></div>' +
                '<div class="userDetail  pull-left">' +
                '<div class="info">' +
                '<span class="iconImg pull-left"></span>' +
                '<span class="pull-left txt">' + addressData.contacts + '</span>' +
                '</div>' +
                '<div class="telephone">' +
                '<span class="iconImg pull-left"></span>' +
                '<span  class="pull-left">' + addressData.contactsMobile + '</span>' +
                '</div>' +
                '<div class="position">' +
                '<span class="iconImg pull-left"></span>' +
                '<span  class="pull-left txt">' + addressData.provinceName + addressData.cityName + addressData.areaName + addressData.streetName + '</span>' +
                '</div>' +
                '</div>' +
                '</div>'
            $('.addAddressBtn').replaceWith(str);
        };
        $.ajax({
            url: rootUrl + '/scrmapp/consumer/wechatuser/address/get',
            type: 'GET',
            dataType: 'JSON',
            data: addressInfo,
            success: function (res) {
                addressData = res.data;
                if (res.returnCode == 1) {
                    userInfo(addressData)
                } else {
                    $.toast(res.returnMsg, "cancel")
                }

            }
        })

    }


    //选择产品；
    $('.reserveCon').on('click', '#productInfo', function (event) {
        window.location.href = rootUrl + '/scrmapp/consumer/wechat/orders/service/choice/product?orderType=' + orderType + '&productId=' + productId + '&addressId=' + addressId + '&flag=' + flag + ''
    });

    //选择地址；

    $('.reserveCon').on('click', '#userInfo', function (event) {
        if (orderType == 1) {
            var str = escape('/scrmapp/consumer/wechat/orders/service/install/page?orderType=' + orderType + '&productId=' + productId + '')
            window.location.href = rootUrl + "/scrmapp/consumer/wechatuser/address/index?reserveUrl=" + str;
        }
        else if (orderType == 2) {
            var str = escape('/scrmapp/consumer/wechat/orders/service/repair/page?orderType=' + orderType + '&productId=' + productId + '')
            window.location.href = rootUrl + "/scrmapp/consumer/wechatuser/address/index?reserveUrl=" + str;
        }
        else if (orderType == 3) {
            var str = escape('/scrmapp/consumer/wechat/orders/service/clean/page?orderType=' + orderType + '&productId=' + productId + '&flag=' + flag + '')
            window.location.href = rootUrl + "/scrmapp/consumer/wechatuser/address/index?reserveUrl=" + str;
        }
    });


    //上传图片；
    wx.error(function (res) {
        $.toast(("error:" + JSON.stringify(res)), "cancel")
    })
    var imgArr = []
    $('#uploadBtn').on('click', function () {
        var serverId;
        wx.chooseImage({
            count: 2, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                var localIds = res.localIds;
                syncUpload(localIds);
                previewImage()

            }
        });
    });
    var syncUpload = function (localIds) {
        var localId = localIds.pop();
        wx.uploadImage({
            localId: localId,
            isShowProgressTips: 1,
            success: function (res) {
                serverId = res.serverId; // 返回图片的服务器端ID
                $.ajax({
                    url: rootUrl + '/weixin/madia/downLoadWechatMedia?media_id=' + serverId + '',
                    type: 'GET',
                    dataType: 'JSON',
                    success: function (res) {
                        if (res.returnCode == 1) {
                            if (imgArr.length > 0) {
                                imgArr[1] = res.data;
                            } else {
                                imgArr[0] = res.data;
                            }

                            for (var i = 0; i < imgArr.length; i++) {
                                $('.imgShow span').eq(i).html('<img src="' + imgArr[i] + '" alt="">')
                            }

                        } else {
                            alert(res.returnMsg)
                        }


                    },
                })
                if (localIds.length > 0) {
                    syncUpload(localIds);
                }
            }
        });
    };

    function previewImage() {
        $(document).on('click', '.imgShow img', function (event) {
            var imgArray = [];
            var curImageSrc = $(this).attr('src');
            var oParent = $(this).parent();
            if (curImageSrc && !oParent.attr('href')) {
                $('.imgShow img').each(function (index, el) {
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

    //选择上门服务时间；
    function getShowDate() {
        var dataArr = getFutureDate(7);
        var values = [];
        var displayValues = [];
        var weekday = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]
        $.each(dataArr, function (i, v) {
            if (i != 0) {
                var month = v.getMonth() + 1;
                var day = v.getDate();
                if (oDate.getHours() >= 18) {
                    day = v.getDate() + 1
                    weekday = ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"]
                }
                console.log(i)

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
        var timeArr = [],
            timeArr1 = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00'];
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
        window.picker_prevValue = getData.data.values[0]
        $("#changeOrder").picker({
            cols: [{
                textAlign: 'center',
                values: getData.data.values,
                displayValues: getData.data.displayValues
            }, {
                textAlign: 'center',
                values: timeArr
            }],
            onClose: function () {
                $('.content').css('paddingBottom', '0.5rem');
                $("#changeOrder").text($("#changeOrder").val())
                //$(".orderTimeTips").css('display', 'block');

            },
            onChange: function (e, value, displayValue) {
                if (e.value[0] == picker_prevValue) {
                    return false;
                } else {
                    picker_prevValue = e.value[0]
                    if (e.cols[0].displayValue == getData.data.displayValues[0]) {
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

    var isSubmitting = false;

    //提交预约；
    $('#submitReserve').click(function (event) {
        if (isSubmitting) {
            return;
        } else {
            isSubmitting = true;
        }

        if (!flags.submitFlag) {
            return;
        }
        flags.submitFlag = false;
        var description = $('#description').val()
        var orderTime = $('#changeOrder').val()
        reserveData = {
            'productId': productData.id,                      //产品编号
            'typeName': productData.typeName,                 //产品大类
            'modelName': productData.modelName,               //产品型号
            'productBarCode': productData.productBarCode,     //产品条码
            'shoppingOrder': productData.shoppingOrder,       //网购单号
            'itemKind': productData.itemKind,                 //产品组织
            'buyTime': productData.buyTime,                 //产品购买时间
            'fromChannel': productData.saleType || "",
            //'addressId':addressData.id,                      //地址编号
            'contacts': addressData.contacts,                 //联系人
            'contactsMobile': addressData.contactsMobile,     //联系电话
            'buyChannel': productData.channelName,            //销售渠道
            'province': addressData.provinceName,             //省名称
            'city': addressData.cityName,                     //市名称
            'area': addressData.areaName,
            'address': addressData.streetName,                //街道名称
            'orderType': orderType,                           //预约类型 1.安装 2.维修 3.保养
            'orderTime': orderTime,                           //预约时间
            'description': description,                       //描述
            'faultImage': imgArr.join(","),                             //如果上传了图片，需传图片地址
        };
        if (!reserveData.productId) {
            $.alert('产品信息不能为空！');
            flags.submitFlag = true;
            return;
        }
        if (!reserveData.contacts || !reserveData.contactsMobile) {
            $.alert('请选择地址！');
            flags.submitFlag = true;
            return;
        }
        if (!reserveData.orderTime) {
            $.alert('预约时间不能为空！');
            flags.submitFlag = true;
            return;
        }
        // if (!reserveData.description) {
        //     $.alert('描述不能为空！');
        //     flags.submitFlag = true;
        //     return ;
        // }

        $.showLoading()
        $.ajax({
            url: rootUrl + '/scrmapp/consumer/wechat/orders/save',
            type: 'POST',
            // dataType:'JSON',
            data: reserveData,
            // contentType:'application/json',
            success: function (res) {
                $.hideLoading()
                setTimeout(function () {
                    if (res.returnCode == 1) {
                        var address = escape(addressData.provinceName + addressData.cityName + addressData.areaName + addressData.streetName);
                        var name = escape(addressData.contacts);
                        var str = "name=" + name + "&tel=" + addressData.contactsMobile + "&address=" + address + "&time=" + reserveData.orderTime + "";
                        window.location.href = rootUrl + "/scrmapp/consumer/wechat/orders/service/add/success?" + str;
                    } else {
                        $.toast(res.returnMsg)
                    }
                    flags.submitFlag = true;
                }, 500)
            },
            error: function (data) {
                $.hideLoading()
                setTimeout(function () {
                    $.toast("网络错误", "cancel")
                    flags.submitFlag = true;
                }, 500)
            }
        })
    });


})(jQuery, window)



