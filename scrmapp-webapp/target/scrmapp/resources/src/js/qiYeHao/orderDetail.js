var ORDERSCODE =  getUrlParam("ordersCode")
var WX_READY = false
function renderation(data){
    $('.orderStatus').html(_getOrderStatusHtml(data))
    $('.orderBasicInfo').html(_getOrderBasicInfo(data))
    $('.productsInfo').html(_getProductsInfo(data))
}

function _getOrderStatusHtml(data){
    var res = {
        title:'工单状态',
        orderStatus:data.orderStatus || "已完工",
        list:[
            {label:"工单类型",content:data.orderTypeName},
            {label:"完工时间",content:data.endTime}
        ]
    }
    var html = template('bloque_template',res)
    return html
}   

function _getOrderBasicInfo(data){
    var res = {
        title:'工单基础信息',
        list:[
            {label:"联系信息",content:data.userName + ',' + data.userMobile},
            {label:"固定电话",content:data.contactsTelephone ? data.contactsTelephone : ""},
            {label:"服务地址",content:data.userAddress},
            {label:"预约时间",content:data.orderTime},
            {label:"详细描述",content:data.description},
            {images:data.faultImage}
        ]
    }
    var html = template('bloque_template',res)
    return html
}

function _getProductsInfo(data){
    var resHtml = ''
    for(var i = 0 ;i<data.productFinish.length; i++){
        var product = data.productFinish[i]
        var info = __getNormalizedProductInfo(product)
        var verify =__getNormalizedProductVerify(product)
        var infoHtml = template('bloque_template',info),
            verifyHtml = template('bloque_template',verify)
        resHtml += (infoHtml + verifyHtml)
    }
    return resHtml
}

function __getNormalizedProductInfo(product){
    var info = {
        title:'工单产品信息',
        status:product.status || "",
        isMarginZero:1,
        list:[
            {label:"名称",content:product.productName},
            {label:"型号",content:product.modelName},
            {label:"条码",content:product.productBarCode}               
        ]
    }
    if(product.changeItem)      info.list.push({label:"滤芯更换",content:product.changeItem})
    if(product.maintainItem)    info.list.push({label:"保养项目",content:product.maintainItem})
    if(product.itemRecord)      info.list.push({label:"维修措施",content:product.itemRecord})
    if(product.partRecord)      info.list.push({label:"维修配件",content:product.partRecord})
    return info
}

function __getNormalizedProductVerify(product){
    var verify = {
        title:'产品核实',
        list:[
            {label:"产品条码",content:product.productBarCodeTwenty},
            {images:product.productImage}
        ]
    }
    return verify
}

function previewImage(src){
    if(!WX_READY){
        $.alert('请稍后尝试')
        return 
    }
    wx.previewImage({
        current: src,
        urls: [src]
    });
}

;(function($,windows){
    ajax.get(queryUrls.qyhQueryFinedOrderDetail,{ordersCode:ORDERSCODE}).then(function(data){
            if(data.returnCode !== ERR_OK){
                alertMsg.error(data)
                return 
            }
            renderation(data.data)
        }).fail(function(error){
            alertMsg.error(error)
        })	

    wxInit_promise.init().then(function(){
        WX_READY = true
    }).fail(function(error){
        alertMsg.error(error)
    })
        

    })(jQuery,window)