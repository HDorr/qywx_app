var ORDERSCODE = $("#ordersCodeInput").val() || getUrlParam("ordersCode")
var USERID = $("#userIdInput").val() || getUrlParam("userId")


Vue.component('VTitle',{
    template:'#title_template',
    props:{
        title:"",
        icon:""
    }
})

var BasicInfo = {
    template:"#basicInfo_template",
    props:{
        data:"",
        neededData:{
            type:Array,
            default(){
                return [
                    {key:'ordersCode',name:'受理单号'},
                    {key:'userMobile',name:'联系信息'},
                    {key:'contactsTelephone',name:'固定电话'},
                    {key:'userAddress',name:'服务地址'},
                    {key:'orderTime',name:'预约时间'},
                    {key:'description',name:'保养描述'}
                ]
            }
        }
    }
}

var Product = {
    template:'#product_template',
    props:{
        product:{
            type:Object,
            default(){
                return {
                    productId:'',
                    prodcutName:'',
                    modelName:'',
                    productBarCode:''
                }
            }
        }
    },
    methods:{
        comfirmHandler:function(){
            this.$emit('comfirm',this.product)
        },
        cancelHandler:function(){
            var _this = this
            $.confirm("确定退单吗", function() {
                _this.$emit('cancel',_this.product)
            });
            
        }
    }
}

var RefuseOrder = {
    template:'#refuse_order_template',
    data:function(){
        return {
            reason:''
        }
    },
    methods:{
        stopScrollScreen:function(e){
            e.preventDefault()
        },
        comfirm:function(){
            if(!this.reason){
                $.toast('请填写拒单理由','cancel')
                return 
            }
            this.$emit('comfirm',this.reason)
        },
        cancel:function(){
            this.$emit('cancel')
        }
    }
}


var app = new Vue({
    el: '#app',
    components:{
        BasicInfo:BasicInfo,
        Product:Product,
        RefuseOrder:RefuseOrder
    },
    data: {
        detail:{},
        products:[],
        productsStatus:{},
        isShowRefuse:false
    },
    methods: {
        getDetailData:function(){
            var _this = this
            ajax.get(queryUrls.qyhQueryUFinedOrderDetail,{ordersCode:ORDERSCODE})
                .then(function (data) {
                    if (data.returnCode !== ERR_OK) {
                        $.alert(data.returnMsg || "服务器未定义错误", "错误")
                        return
                    }
                    var pdtStatus = _this._getProductsStatus(data.data.products)
                    if(pdtStatus.isAllCompleted){
                        var url = getParamStr(pageUrls.completeWorkOrderTip,{userId:USERID})
                        location.href = url
                        return
                    }
                    if(pdtStatus.isAllCanceled ||pdtStatus.isAllOperated){
                        location.href = pageUrls.unfinishedWorkOrderList+"?userId="+USERID+"&condition=today"
                        return
                    }
                    _this.productsStatus = pdtStatus
                    _this.detail = data.data
                    _this.products = data.data.products
                    setProducts2LocalStorage(_this.products)
                }).fail(function (error) {
                    alertMsg.error(error)
                })
        },
        cancelProductHandler:function(product){
            var _this = this
            if(this.productsStatus.isRefuseOrder){
                _this.isShowRefuse = true
                return
            }
            var queryData = {
                orderId:this.detail.id,
                productId:product.productId,
                orderType: this.detail.orderType,
                orderCode:ORDERSCODE || this.detail.ordersCode,
                userId: USERID || this.detail.userId,  
                contacts: this.detail.userName
            } 
            ajax.post(queryUrls.qyhCancelOrderProduct,queryData,true)
                .then(function(res){
                    if(res.returnCode !== ERR_OK || !res.returnCode){
                        alertMsg.error(res)
                        return 
                    }
                    _this.getDetailData()
                })
                .fail(function(error){
                    alertMsg.error(error)
                })
        },
        refuseOrderHandler:function(reason){
            var queryData = {
                userId: USERID || this.detail.userId,
                ordersCode:ORDERSCODE || this.detail.ordersCode,
                ordersId:this.detail.id,
                ordersType: this.detail.orderType,
                contacts: this.detail.userName,
                reason: reason
            }
            ajax.post(queryUrls.qyhQueryRefuseOrder,queryData)
                .then(function(res){
                    if(res.returnCode !== ERR_OK){
                        alertMsg.error({message:res.returnMsg})
                        return 
                    }
                    location.href = pageUrls.unfinishedWorkOrderList + "?userId="+USERID + "&condition=today"
                })
                .fail(function(error){
                    alertMsg.error(error)
                })
        },
        completeProductHandler:function(product){
            setCurProductId2LocalStorage(product.productId)
            location.href = pageUrls.unfinishedWorkOrderDetail_maintain_detail + "?userId=" + USERID + "&ordersCode=" + ORDERSCODE
        },
        _getProductsStatus(products){
            var status = {
                isAllCompleted  :   false,
                isAllCanceled   :   false,
                isAllOperated   :   false,
                isLast          :   false,
                isRefuseOrder   :   false
            }
            var productsCount = products.length
            var completedCount = 0
            var canceledCount = 0
            var normalCount = 0
            for(var i = 0 ;i < products.length; i++){
                var product = products[i]
                switch(product.status){
                    case 1:
                        normalCount++
                        break;
                    case 2:
                        canceledCount++
                        break;
                    case 3:
                        completedCount++
                        break;
                }      
            }
            if(normalCount === 1) status.isLast = true
            if(normalCount === 0) status.isAllOperated = true
            if(completedCount === productsCount) status.isAllCompleted = true
            if(canceledCount === productsCount) status.isAllCanceled = true
            if(completedCount === 0 && normalCount === 1) status.isRefuseOrder = true
            return status
        }
    },
    created(){
        this.getDetailData()
    }
})

function setCurProductId2LocalStorage(productId){
    window.localStorage.setItem('unfinished_cur_productId',productId)
}
function setProducts2LocalStorage(products){
    window.localStorage.setItem('unfinished_products',JSON.stringify(products))
}






