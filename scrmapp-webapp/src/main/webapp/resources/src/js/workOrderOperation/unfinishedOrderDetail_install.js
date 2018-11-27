var ORDERSCODE = $("#ordersCodeInput").val() || getUrlParam("ordersCode")
var USERID = $("#userIdInput").val() || getUrlParam("userId")
var ORDERTYPE = getUrlParam('orderType') || ''
var isSubmitting = false;
var CANCEL_REASONS = {
    title: "取消原因",
    items: [
        {
            title: "1. 用户退换货",
            value: "用户退换货",
        },
        {
            title: "2. 暂未到货",
            value: "暂未到货",
        },
        {
            title: "3. 安装在其他地址",
            value: "安装在其他地址",
        },
        {
            title: "4. 用户暂时不安装",
            value: "用户暂时不安装",
        },
        {
            title: "5.其他",
            value: "others",
        }
    ]
}
var WX_READY = false
var WX_CAN_SCAN = false


wxInit_promise.init().then(function(){
    WX_READY = true
    return wxInit_promise.checkApi(['scanQRCode'])   
}).then(function(checkResult){
    if(checkResult.scanQRCode){
        WX_CAN_SCAN = true 
    }else{
        $.toptip("当前手机不支持扫一扫功能", "warning")
    }
}).fail(function(error){
    alertMsg.error(error)
})

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
                    {key:'description',name:'安装描述'}
                ]
            }
        }
    }
}

var Product = {
    template: '#products_template',
    props:{
        product:{
            type:Object,
            default(){
                return {}
            }
        },
        isComplete:{
            type:Boolean,
            default:false
        }     
    },
    data:function(){
        return {
            barCode:"",
            productImage:[]
        }
    },
    computed:{
        isCancel:function(){
           return this.product.status == 2 ? true : false
        },
        isShowBarCodeInput:function(){
            return  !this.isCancel && !this.isComplete
        },
        isShowUploadImage:function(){
            return  !this.isCancel && !this.isComplete && this.productImage.length === 0
        }
    },
    watch:{
        barCode:function(cur,old){
            if(cur){
                this.barCode = (cur+'').replace(/[^0-9]/ig,"").substr(0,20)
            } 
        }
    },
    methods:{
        edit:function(){
            var _this = this
            if(_this.isComplete){
                _this.$emit('edit',_this.product.productId)
                return 
            }
            _this._checkData().then(function(){
                var comfirmData = Object.assign({},_this.product,{productBarCode:_this.barCode,productImage:_this.productImage.join(',')})
                _this.$emit('confirm',comfirmData)
            }).fail(function(error){
                alertMsg.error(error)
            })
        },
        uploadImage:function(index){
            if(this.isComplete) return
            if(typeof(index) === 'number' && this.productImage[index]) return
            var _this = this
            if(!WX_READY && !IS_DEVENV){
                $.toptip("上传图片初始化中，请稍后再试", "warning")
                return 
            }
            wxInit_promise.wxUploadImage().then(function(src){
                _this.productImage = _this._setProductImage(src)
            }).fail(function(err){
                alertMsg.error(err)
            })
        },
        delectImg:function(index){
            this.productImage.splice(index,1)
        },
        scanBarCode: function(){
            var _this = this
            if(!WX_CAN_SCAN && !IS_DEVENV){
                $.toptip("暂时无法使用扫一扫，请稍后再试", "warning")
                return 
            }
            wxInit_promise.wxScanBarCode().then(function(res){
                var barCode = _normalizeBarCode(res)
                if(barCode){
                    _this.barCode = barCode
                }else{
                    alertMsg.error({message:'不是有效的产品码'})
                }
            }).fail(function(err){
                alertMsg.error(err)
            })
        },
        cancelProduct: function($event){
            if(this.isCancel){
                return 
            }
            var emitData = {
                event:$event,
                product:this.product
            }
            this.$emit('cancel',emitData)
        },
        _setProductImage(src){
            var res = []
            for(var i = 0; i<this.productImage.length;i++){
                res.push(this.productImage[i])
            }
            if(res.length >= 3){
                res.splice(2,res.length-2,src)
            }else{
                res.push(src)
            }
            return res
        },
        _checkData:function(){
            var def = $.Deferred()
            if(!this.barCode && this.productImage.length === 0){
                def.reject({message:"请输入产品条码或者产品图片"})
            }
            if(this.barCode){
                ajax.get(queryUrls.queryPdtByCodeOrModal,{"productBarCode":this.barCode},"查询产品中")
                    .then(function(data){
                        if(data.returnCode !== ERR_OK) def.reject(data)
                        def.resolve()
                    })
                    .fail(function(error){
                        def.reject(error)
                    })
            }else{
                def.resolve()
            }    
            return def.promise()
        }     
    },
    mounted(){
        // this.barCode = this.product.productBarCode || ""
        this.productImage = this.product.barCodeImage ? this.product.barCodeImage.split(',') : []
        this.barCode = this.product.status === 3 ? this.product.productBarCode :  ""
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
            var reason = this.reason.trim()
            if(!reason){
                $.toast('请填写拒单理由','cancel')
                return 
            }else if(reason.length < 3){
                $.toast('理由需超过3个字','cancel')
                return 
            }
            this.$emit('comfirm',reason)
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
        validProductsCount:0,
        comfirmedProducts:[],
        isShowRefuse:false,
        curOnCancelPdt:null,
        cancelReasons:""
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
                    _this.detail = data.data
                    _this.products = data.data.products
                    _this.comfirmedProducts = _this._getAlreadyCompletedList(data.data.products)
                    _this.validProductsCount = _this._getValidProductsLength(data.data.products)
                    if(data.data.orderType) ORDERTYPE = data.data.orderType 
                }).fail(function (error) {
                    alertMsg.error(error)
                })
        },
        handleProductComfirm:function(product){
            var  index = this._getProductPosById(product.productId)
            this.comfirmedProducts.splice(index,1,product)
        },
        handleProductEdit:function(productId){
            var index = this._getComfirmedProductPosById(productId)
            if(index === undefined) return
            this.comfirmedProducts.splice(index,1,null)
        },
        handelCancelProduct:function(data){
            if(this._isLastToCancel()){
                $.alert("请返回工单列表拒绝此工单")
                return
            }
            this.curOnCancelPdt = data.product
            this.openCancelReasonSelect()
        },
        handleOtherReaconCancel:function(reason){
            this.cancelReasons += this.curOnCancelPdt.modelName + ":" + reason + ";"
            this._setProductToCancelById(this.curOnCancelPdt.productId) 
            this.isShowRefuse = false
        },
        handleCompleteOrder:function(){
            if (isSubmitting) {
                return;
            } else {
                isSubmitting = true;
            }
            if(!this._isAllCompleted()){
                $.toast('您还有产品未确认','cancel')
                return 
            }
            var submitData = this._normalizeSubmitData(this.comfirmedProducts,this.cancelReasons)
            //加弹出框,防止表单进行重复提交
            $.showLoading()
            ajax.post(queryUrls.qyhSubmitOrder_install,submitData).then(function(data){
                if(data.returnCode !== ERR_OK){
                    alertMsg.error(data)
                    return 
                }
                var jumpTo = getParamStr(pageUrls.completeWorkOrderTip,{userId:USERID})
                location.href = jumpTo
            }).fail(function(error){
                alertMsg.error(error)
            }).always(function () {
                $.hideLoading()
                isSubmitting = false;
            })
        },
        initCancelReasonSelect:function(){
            var _this = this
            this.selectPicker = $(this.$refs['select_input'])
            var configration = Object.assign({},CANCEL_REASONS,{
                'closeText':"关闭",
                'onChange': _this.handleCancelReasonChange,
                'onClose':function(){ _this.selectPicker.val("").data("values","")}
            })     
            this.selectPicker.select(configration)
        },
        openCancelReasonSelect:function(){
            this.selectPicker.select('open')
        },
        closeCancelReasonSelect:function(){
            this.selectPicker.select('close')
        },
        handleCancelReasonChange:function(data){
            if(data.values === "others"){
                this.isShowRefuse = true
                return
            }
           this.cancelReasons += this.curOnCancelPdt.modelName + ":" + data.values + ";"
           this._setProductToCancelById(this.curOnCancelPdt.productId) 
        },
        _getProductPosById(productId){
            for(var i = 0 ; i < this.products.length ; i++){
                if(this.products[i].productId == productId){
                    return i
                }
            }
        },
        _getComfirmedProductPosById:function(id,productList){
            var index = undefined
            var list = productList || this.comfirmedProducts
            for(var i = 0 ; i < list.length; i++){
                var item = list[i]
                if(item && item.productId === id){
                    return i
                    break
                }
            }
            return undefined
        },
        _setProductToCancelById(productId){
            var index = this._getProductPosById(productId)
            var product = this.products[index]
            product.status = 2
            this.validProductsCount--
            this.products.splice(index,1,product)       
            this.comfirmedProducts.splice(index,1,null)
        },
        _isLastToCancel:function(){
            return this.validProductsCount === 1
        },
        _normalizeSubmitData:function(comfirmedProducts,cancelReasons){
            var products = []
            var cancelReasons = cancelReasons || ""
            for(var i = 0 ;i < comfirmedProducts.length;i++){
                var product = comfirmedProducts[i]
                if(!product)continue
                var tmpObj = {productId:product.productId}
                if(product.productBarCode) tmpObj.productBarCode = product.productBarCode
                if(product.productImage) tmpObj.barCodeImage = product.productImage
                products.push(tmpObj)
            }
            var res = {
                ordersCode:ORDERSCODE,
                userId:USERID,
                products:JSON.stringify(products)
            }
            cancelReasons && (res.reasons = cancelReasons)
            return res

        },
        _getValidProductsLength:function(products){
            var count = 0
            for(var i = 0 ;i < products.length; i ++ ){
                if(products[i].status !== 2) count++
            }
            return count
        },
        _getAlreadyCompletedList:function(products){
            var res = []
            res.length = products.length
            for(var i = 0 ;i < products.length; i++){
                var product = products[i]
                product.status == 3 && (res[i] = product)
            }
            return res
        },
        _isAllCompleted:function(){
            var count = 0
            for(var i = 0 ;i < this.comfirmedProducts.length ;i++){
                var product = this.comfirmedProducts[i]
                if(product !== null && product !== undefined){
                   count ++
                }
            }
            return count === this.validProductsCount ? true : false
        },
    },
    mounted(){
        this.getDetailData()
        this.initCancelReasonSelect()
    }
})


function _normalizeBarCode(scanResult){
    var result = scanResult.resultStr;
    var barCodeRegExp = new RegExp('barcode=([^&]*)(&|$)', 'i')
    var barCode = result.match(barCodeRegExp)
    if(barCode !== null){
        return unescape(barCode[1])
    }else{
        return result.split(',')[1]
    }
}





