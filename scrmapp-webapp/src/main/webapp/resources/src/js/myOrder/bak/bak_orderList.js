(function($, window) {
    queryOrderList(renderOrderList);
})(jQuery, window)



function queryOrderList(callback) {
    $.ajax({
        type: "GET",
        url: queryUrls.orderList,
        success: function(data) {
            // var data = JSON.parse(data);
            if (data.returnCode === 1) {
                if (callback) {
                    callback(data.data);
                }
            } else {
                $.toast(data.returnMsg, "cancel")
            }
        },
        error: function(err) {
            $.toast("网络错误", "cancel")
        }
    })
}


function renderOrderList(data) {
    if (data.length == 0) {
        $.alert({
            title: '提示',
            text: '您还没预约过任何产品，请返回添加预约',
            onOK: function() {
                gotoIndex();
            }
        });
        return;
    }
    var str = '<ul>';
    $.each(data, function(i, v) {
        v.productImage = v.productImage || rootPath + "/resources/images/defaultPdtImg.jpg"
        for (var key in v) {
            v[key] = v[key] ? v[key] : "无"
        }

        str += ' <li>' +
            '       <div class="orderHeader qy-blue">' +
            '           <p>' + v.orderTypeName + '' +
            '               <span>预约单号&nbsp;:&nbsp;</span>' +
            '               <span>' + v.ordersCode + '</span>' +
            '               <span class="pull-right">' + v.orderStatus + '</span>' +
            '           </p>' +
            '       </div>' +
            '       <div class="pdtInfo clearfix">' +
            '           <div class="pdtImg pull-left" style="background-image: url(\'' + v.productImage + '\')"></div>' +
            '           <div class="pdtText pull-left">' +
            '               <p>' + v.productName + '</p>' +
            '               <p>产品条码：<span>' + v.productBarCode + '</span></p>' +
            '               <p>产品型号：<span>' + v.modelName + '</span></p>' +
            '           </div>' +
            '       </div>' +
            '       <div class="sepratorLine">' +
            '           <i></i>' +
            '       </div>' +
            '       <div class="workerInfo clearfix">' +
            '           <div class="pdtImg pull-left" style="background-image: url(\'' + v.imageUrl + '\')"></div>' +
            '           <div class="pdtText pull-left">' +
            '               <p><img src="' + rootPath + '/resources/src/images/icons/personInfo.png">' + v.userName + ',' + v.userMobile + '</p>' +
            '               <p><img src="' + rootPath + '/resources/src/images/icons/timer.png">' + v.orderTime + '</p>' +
            '               <p><img src="' + rootPath + '/resources/src/images/icons/location.png">' + v.userAddress + '</p>' +
            '           </div>' +
            '       </div>' +
            '       <div class="operation">' +
            '           <div class="btnBox pull-right">';
        //处理中的状态 v.status:1:处理中  2:已取消 3:重新处理中 4:已接单 5:服务完成 6:评价完成
        if (v.status == 1 || v.status == 3 || v.status == 4) {
            str += '<button class="cancelOrder" onclick="cancelOrder(this)"  data-orderscode="' + v.ordersCode + '" data-username="' + v.userName + '">取消预约</button>' +
                '<button onclick="changeOrder(this)" data-orderscode="' + v.ordersCode + '">更改预约</button>' +
                '<button onclick="orderDetail(this)" data-orderscode="' + v.ordersCode + '">查看详情</button>';
        } else if (v.status == 2 || v.status == 6) {
            str += '<button onclick="orderDetail(this)" data-orderscode="' + v.ordersCode + '">查看详情</button>';
        } else if (v.status == 5) {
            str +=
                '<button onclick="reviewOrder(this)" data-orderscode="' + v.ordersCode + '">评价</button>' +
                '<button onclick="orderDetail(this)" data-orderscode="' + v.ordersCode + '">查看详情</button>';
        }



        str += '           </div>' +
            '       </div>' +
            '     </li>';
    })
    str += '</ul>';
    $(".main_layer").css("padding-top", "0").html(str);
}



function cancelOrder(ele) {
    var orderscode = $(ele).data("orderscode"),
        userName = $(ele).data("username");
    $.confirm({
        title: '取消预约',
        text: '确定取消预约？',
        onOK: function() {
            setTimeout(function() {
                $.showLoading();
                $.ajax({
                    url: queryUrls.orderCancel,
                    type: "POST",
                    data: {
                        ordersCode: orderscode,
                        contacts: userName
                    },
                    success: function(data) {
                        $.hideLoading();
                        setTimeout(function() {
                            if (data.returnCode === 1) {
                                $.toast(data.returnMsg, function() {
                                    queryOrderList(renderOrderList);
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
            }, 500)
        }
    });
}

function changeOrder(ele) {
    window.location.href = pageUrls.orderDetail + "?ordersCode=" + $(ele).data("orderscode") + "&changeOrder=1"
        //console.log($(ele).data("orderscode"))
}

function orderDetail(ele) {
    window.location.href = pageUrls.orderDetail + "?ordersCode=" + $(ele).data("orderscode")
        //console.log($(ele).data("orderscode"))
}

function reviewOrder(ele) {
    //alert($(ele).data("orderscode"))
    window.location.href = pageUrls.reviewPraide + "?ordersCode=" + $(ele).data("orderscode")
}