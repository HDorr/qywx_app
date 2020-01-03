var glData = {
    rootUrl: getRootPath(),
    productId: getUrlParam("productId") || ""
};

(function($, window) {
    ajaxGetDetail(renderDetail);
    shieldShare();
})(jQuery, window);


function ajaxGetDetail(callback) {
    $.ajax({
        url: glData.rootUrl + "/scrmapp/consumer/product/get",
        // url: glData.rootUrl + "/resources/fakeJson/productDetail.json",
        data: {
            productId: glData.productId
        },
        type: "GET",
        success: function(data) {
            // var data = JSON.parse(data);
            if (data.returnCode === 1) {
                if (callback) {
                    callback(data.data);
                }
            } else {
                $.toast(data.returnMsg, "cancel")
            }

        }
    })
}


function renderDetail(data) {
    var numArr = ["一", "二", "三", "四", "五", "六", "七", "八", "九", ];
    if (!data.filterRemind || data.filterRemind == 2) {
        data.filterRemindName = "开启滤芯更换提醒"
    } else {
        data.filterRemindName = "关闭滤芯更换提醒"
    }
    for (var key in data) {
        data[key] = data[key] ? data[key] : ""
    };
    data.productImage = data.productImage || glData.rootUrl + "/resources/images/defaultPdtImg.jpg";
    var str1 = '<ul class="pdtInfo">' +
        '            <li class="pdtImg" style="background-image: url(\'' + data.productImage + '\');"></li>' +
        '            <li class="pdtInfoForm"><span>产品条码:</span><span>' + data.productBarCode + '</span></li>' +
        '            <li class="pdtInfoForm"><span>产品名称:</span><span>' + data.productName + '</span></li>' +
        '            <li class="pdtInfoForm"><span>产品大类:</span><span>' + data.typeName + '</span></li>' +
        '            <li class="pdtInfoForm"><span>产品编号:</span><span>' + data.productCode + '</span></li>' +
        '            <li class="pdtInfoForm"><span>产品型号:</span><span>' + data.modelName + '</span></li>' +
        '            <li class="pdtInfoForm"><span>滤芯级别:</span><span>' + data.levelName + '</span></li>' +
        '            <li class="pdtInfoForm"><span>销售类型:</span><span>' + data.saleTypeName + '</span></li>' +
        '            <li class="pdtInfoForm"><span>购买渠道:</span><span>' + data.channelName + '</span></li>' +
        '            <li class="pdtInfoForm"><span>网购单号:</span><span>' + data.shoppingOrder + '</span></li>' +
        '        </ul>';

    //有滤芯

    // if (data.filterList.length > 0) {
    //     var str2 = '<ul class="pdtInfo">'
    //     $.each(data.filterList, function(i, v) {
    //         str2 += '<li class="pdtInfoForm" data-id="' + v.id + '"><span>第' + numArr[i] + '级滤芯:</span><span>' + v.filterName + '</span></li>'
    //     })
    //     str2 += ' <li class="toggleRemind">' +
    //         '     <button data-id="' + data.id + '"onclick="setRemindFilter(this)">' + data.filterRemindName + '</button>' +
    //         '      </li>' +
    //         '       </ul>' +
    //         '       <button class="qy-btn-all" onclick="gotoIndex()" style="padding-bottom:5%">+ 一键服务</button>';
    // } else { //没滤芯
    //     var str2 = '<p style="padding-left:0.333333rem;font-size:0.2933333rem">没有查到该产品的滤芯数据</p>'
    //     str2 += '<button class="qy-btn-all" onclick="gotoIndex()" style="padding-bottom:5%">+ 一键服务</button>';
    //
    // }
    str1 = str1 + '<button class="qy-btn-all" onclick="gotoIndex()" style="padding-bottom:5%">+ 一键服务</button>';
    $(".main_layer").css("padding-top", "0").html(str1);
}


function setRemindFilter(ele) {
    var that = $(ele)
    var data = {
        productId: that.data("id")
    }
    $.ajax({
        url: glData.rootUrl + "/scrmapp/consumer/product/remind",
        data: data,
        type: "GET",
        success: function(data) {
            if (data.returnCode == 1) {
                if (data.data == 1) {
                    that.text("关闭滤芯更换提醒");
                } else {
                    that.text("打开滤芯更换提醒");
                }
                $.toast(data.returnMsg)
            } else {
                $.toast(data.returnMsg, "cancel")
            }
        }
    })
}