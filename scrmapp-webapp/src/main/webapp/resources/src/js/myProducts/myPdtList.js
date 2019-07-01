var glData = {
    rootUrl: getRootPath(),
    hasPdtsStr: ' <ul id="pdtList">' +
        '           ' +
        '        </ul>' +
        '    <a class="qy-btn-all" href="' + pageUrls.scanToBindPdt + '">+  新增产品</a>',
    hasntPdtsStr: ' <div class="imgBox" style="background-image: url(\'/scrmapp/resources/images/bindProduct/haventBindYet.png\')"></div>' +
        '        <p>您还没有绑定产品哟，赶快来绑定吧！</p>' +
        '        <a class="qy-btn-400" id="bindProductBtn"  href="' + pageUrls.scanToBindPdt + '">绑定产品</a>',
    notFirstTime: window.localStorage.getItem("myPdtListNotFirstTime")


};


(function($, window) {
    $.toast.prototype.defaults.duration = 800;
    ajaxGetList(renderFunc)
    shieldShare();


})(jQuery, window)



function deletePdt(ele) {
    $.confirm({
        title: '确认删除',
        text: '确认删除该产品？',
        onOK: function() {
            setTimeout(function() {
                var data = {
                    productId: $(ele).data("id")
                }
                $.ajax({
                    type: "POST",
                    data: data,
                    url: glData.rootUrl + "/scrmapp/consumer/product/delete",
                    success: function(data) {
                        if (data.returnCode === 1) {
                            $.toast("删除成功", function() {
                                ajaxGetList(renderFunc);
                            })
                        } else {
                            $.toast(data.returnMsg, "cancel");
                        }
                    }
                })
            }, 500)
        }
    });

};

function goToPdtDetail(ele) {
    var productId = $(ele).data("id")
    window.location.href = pageUrls.bindPdtDetail + "?productId=" + productId
};

//跳转至小程序延保详情页面
function gotoMiniDetail(barCode) {
    if (barCode){
        wx.miniProgram.navigateTo({url:'/pages/ewCodeDetails?bar_code='+barCode});
    }else {
        $.alert("该机器没有条码,无法查看保修信息!");
        return;
    }
}


/*function setRemindFilter(ele) {
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
}*/

function goBindPdt() {
    // window.location.href = glData.rootUrl + "/scrmapp/consumer/product/bind/scan/page"
    window.location.href = pageUrls.scanToBindPdt;
}




function ajaxGetList(callback) {
    $.ajax({
        type: "GET",
        url: glData.rootUrl + "/scrmapp/consumer/product/list",
        // url: glData.rootUrl + "/resources/fakeJson/productLists.json",
        success: function(data) {
            if (data.returnCode === 1) {
                if (callback) {
                    callback(data);
                }
            } else {
                $.toast("data.returnMsg", "cancel")
            }
        }
    })
}

function renderFunc(data) {
    //有产品
    if (data.data.length > 0) {
        $(".main_layer").html(glData.hasPdtsStr);
        $(".main_layer").css("padding-top", "0")
        var data = data.data;
        var strLi = "";
        var deleteIcon = glData.rootUrl + '/resources/src/images/icons/delete-grey.png';
        var ewIcon = glData.rootUrl + '/resources/src/images/icons/ew.png';
        $.each(data, function(i, v) {
            var str='';
            if (!v.filterRemind || v.filterRemind == 2) {
                v.filterRemindName = "开启滤芯更换提醒"
            } else {
                v.filterRemindName = "关闭滤芯更换提醒"
            }

            if (!$.isEmptyObject(v.guarantee) && !$.isEmptyObject(v.productBarCode)) {
                str='                    <div class="btnBox pull-right">' +
                    '                        <button class="ew_btn" onclick="gotoMiniDetail('+v.productBarCode+')"> ' +
                    '                           <div class="ewPdt_one" style="background-image: url(\'' + ewIcon + '\');"></div> ' +
                '                               <div class="ewPdt_two">保修状态: '+v.guarantee.message+'</div></button>'+
                '                           <button hidden="hidden" onclick="goToPdtDetail(this)" data-id=' + v.id + '>' + v.filterRemindName + '</button>' +
                '                        </div>' ;
            }
            v.productImage = v.productImage || glData.rootUrl + "/resources/images/defaultPdtImg.jpg";
            strLi += ' <li data-id=' + v.id + '>' +
                '                <div class="pdtInfo clearfix" onclick="goToPdtDetail(this)" data-id=' + v.id + '>' +
                '                    <div class="pdtImg pull-left" style="background-image: url(\'' + v.productImage + '\')"></div>' +
                '                    <div class="pdtText pull-left">' +
                '                        <p>' + v.productName + '</p>' +
                '                        <p>产品条码：<span>' + v.productBarCode + '</span></p>' +
                '                        <p>产品型号：<span>' + v.modelName + '</span></p>' +
                '                    </div>' +
                '                </div>' +
                '                <div class="operation">' +
                '                    <i class="deletePdt" onclick="deletePdt(this)" style="background-image: url(\'' + deleteIcon + '\');" data-id=' + v.id + '></i>' +
                 str+
                '                    <div class="btnBox pull-right">' +
                '                        <button onclick="gotoIndex()">+ 一键服务</button>'+
                '                        <button hidden="hidden" onclick="goToPdtDetail(this)" data-id=' + v.id + '>' + v.filterRemindName + '</button>' +
                '                    </div>' +
                '                </div>' +
                '            </li>';
        })
        $("#pdtList").html(strLi);
        if (!glData.notFirstTime) {
            window.localStorage.setItem("myPdtListNotFirstTime", true);
            $.alert("系统已根据您的历史产品自动进行了绑定")
        }
    } else { //没绑定产品
        $(".main_layer").html(glData.hasntPdtsStr)
        $(".main_layer").css("padding-top", "3.16rem")
    }
}