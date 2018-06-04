(function($, window) {
    queryOrderList(renderOrderList);
})(jQuery, window)



function queryOrderList(callback) {
    ajax.get(queryUrls.orderList).then(function(data){
        if(!data.returnCode === 1){
            $.alert(data.returnMsg)
            return 
        }
        if(data.data.length === 0){
            $.alert({
                title: '提示',
                text: '您还没预约过任何产品，请返回添加预约',
                onOK: function() {
                    gotoIndex();
                }
            });
            return;
        }
        callback(data.data)
    }).fail(function(err){
    	alertMsg.error(err)
    })
}

function renderOrderList(data) {
    var html = template('lists_template',{list:data})
    $(".main_layer").html(html)
}

function cancelOrder(ele) {
    var orderscode = $(ele).data("orderscode"),
        userName = $(ele).data("username");
    _comfirmCancelAlert().then(function(){
        return ajax.post(queryUrls.orderCancel,{ordersCode: orderscode,contacts: userName})
    }).then(function(data){
        if(data.returnCode !== ERR_OK){
            alertMsg.error(data)
            return 
        }
        queryOrderList(renderOrderList)
        $.toptip('取消成功','success')
    }).fail(function(error){
        alertMsg.error(error)
    })
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

function _comfirmCancelAlert(){
    var def = $.Deferred()
        $.confirm({
            title: '取消预约',
            text:"确定取消预约?",
            onOK: function() {
                setTimeout(function() {
                    def.resolve()
                },250)
            }
        })
    return def.promise()
}