var glData = {
        userId: $("#userIdInput").val() || getUrlParam('userId'),
        condition: $("#conditionInput").val() || "today",
        todayDom: $("#today>ul"),
        tomorrowDom: $("#tomorrow>ul"),
        allDom: $("#all>ul"),
        todayHtml: "",
        tomorrowHtml: "",
        allHtml: "",
        canNav: false
    }
function clearOrderLocalStorage(){
    window.localStorage.removeItem('unfinished_products')
}
function renderList(condition) {
    getListData(condition).then(function(data){
        if(data.returnCode !== ERR_OK){
            alertMsg.error({message:data.returnMsg})
            return 
        }
        var renderData = _setOrderStatusByProductsStatus(data.data,condition)
        unFinishedRenderFunc(condition,renderData)
    }).fail(function(error){
        alertMsg.error(error)
    })
}
function getListData(condition){
    var condition = condition || "today"
    var data = {
        userId: glData.userId,
        condition:condition
    }
    return ajax.get(queryUrls.qyhQueryUnfinishedOrder,data)
}

function unFinishedRenderFunc(flag, data) {
    $(".todayOrders").text(data.today);
    $(".tormorrowOrders").text(data.tomorrow);
    $(".allOrders").text(data.total);
    data.flag = flag
    var html = template("liTemplate", data);
    glData[flag + "Html"] = html;
    glData[flag + "Dom"].html(html);
    var curNav = $(".weui-navbar__item[data-flag=" + flag + "]");
    if (!curNav.hasClass('weui-bar__item--on')) {
        curNav.trigger('click')
    }
    if (data.wechatOrdersVoList && data.wechatOrdersVoList.length > 0) {
        glData.destinationCoods = [];
    }
}

function tabEvts() {
    $(".weui-navbar .weui-navbar__item").on("click", function(e) {
        var flag = $(this).data("flag");
        if (!glData[flag + "Html"]) {
            renderList(flag)
        }
    })
}

function refuseOrder(el) {
    var that = $(el);
    confirmModal.init({
        text: "确认拒绝工单吗？",
        tip: "请先联系您所在的服务网点<br/>服务网点电话 : 021-61522809",
        textArea: true,
        callback: function(fn, reason) {
            //判断textarea里的值是否为空
            var text = reason.trim();
            if (text == "请选择原因") {
                $.toptip("必须选择原因", 2000, 'error');
                return;
            }
            fn();
            $.showLoading("取消中")
            var data = {
                userId: glData.userId,
                ordersCode: that.data("orderscode"),
                ordersId: that.data("ordersid"),
                ordersType: that.data("ordertype"),
                contacts: that.data("contacts"),
                reason: text
            }
            ajax.post(queryUrls.qyhQueryRefuseOrder,data)
                .then(function(data){
                    if (data.returnCode == 1) {
                        $.toptip(data.returnMsg || '拒单成功', 2000, 'success');
                        renderList(that.data('flag'))
                    } else {
                        $.toptip(data.returnMsg, 2000, 'error');
                    }
                })
                .fail(function(error){
                    alertMsg.error(error)
                })
        }
    })
}

function completeOrder(el) {
    var orderType =  $(el).data("ordertype")
    var url = ''
    switch(orderType){
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
    var params = {orderType:orderType,userId:glData.userId,ordersCode:$(el).data("orderscode")}
    url = getParamStr(url,params)
    // var url = rootPath + "/scrmapp/qyhuser/orders/finish/detail/page?orderType=" + $(el).data("ordertype") + "&userId=" + glData.userId + "&ordersCode=" + $(el).data("orderscode")
    location.href = url;
}

function changeOrder(el) {
    var $el = $(el)
    var params = {ordersCode:$el.data('orderscode'),userId:glData.userId,isChange:'1'}
    var url =  getParamStr(pageUrls.unfinishedWorkOrderDetail,params)
    location.href = url;
}

function gotoDetail(el) {
    var params = {ordersCode:$(el).data('orderscode'),userId:glData.userId,isChange:'0'}
    var url =  getParamStr(pageUrls.unfinishedWorkOrderDetail,params) 
    location.href = url
}

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


function _startWx(){
    wxInit.init()
    wx.ready(function() {
        wx.checkJsApi({
            jsApiList: ['openLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function(res) {
                if (res.checkResult.openLocation) {
                    glData.canNav = true;
                } else {
                    $.toptip("当前手机不支持导航功能", "warning")
                }

            }
        });
    })
    wx.error(function(res) {
        $.toptip("微信地图功能调用失败", "warning")
    })
}

/**
 * 根据每个订单下每个产品的状态（status：1(正常)，2(取消)，3(完工)）来设置 订单的可操作项
 * @param {* Object} data 
 * @return {* Object} data (data.wechatOrdersVoList每一项增加了btnStatus:{"canRefuse":1,"canChangeTime":1,"canComplete":1})
 *                     1为可操作  0为不可操作
 */
function _setOrderStatusByProductsStatus(data,condition){
    var resData = JSON.parse(JSON.stringify(data))
    resData.flag = condition
    if(!resData.wechatOrdersVoList) return resData
    for(var i = 0 ; i < resData.wechatOrdersVoList.length;i++){
        var order = resData.wechatOrdersVoList[i]
        var btnStatus = {"canRefuse":1,"canChangeTime":1,"canComplete":1}
        /* 可加入限制按钮的逻辑  不显示相应功能按钮 至0即可 */
        order.btnStatus = btnStatus
    }
    return resData
}

(function($, window) {
    _startWx()
    clearOrderLocalStorage()
    var condition = "today";
    if (glData.condition) {
        condition = glData.condition;
    }
    renderList(condition)
    tabEvts();

    
})(jQuery, window)



