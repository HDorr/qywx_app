var ORDERSCODE = $("#ordersCodeInput").val() || getUrlParam("ordersCode")
var USERID = $("#userIdInput").val() || getUrlParam("userId")
var STORAGE_PRODUCTS = JSON.parse(window.localStorage.getItem('unfinished_products') || "[]")
var STORAGE_CURPRODUCT_ID = window.localStorage.getItem('unfinished_cur_productId')
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
Vue.component('VTitle', {
    template: '#title_template',
    props: {
        title: "",
        icon: ""
    }
})

Vue.component('VCheckBoxLine', {
    template: '#check_box_line_tempalte',
    props: {
        text: "",
        isChecked: {
            type: Boolean,
            default() {
                return false
            }
        }
    },
    methods: {
        clickHandler: function () {
            this.$emit('click', this.isChecked)
        }
    }
})

var Barcode = {
    template: '#barCode_template',
    props: {
        barCode: '',
        productImage: ''
    },
    data: function () {
        return {
            inputBarCode: '',
            image: ''
        }
    },
    watch: {
        inputBarCode: function (cur) {
            this.inputBarCode = (cur+'').replace(/[^0-9]/ig,"")
            this.emitChange()
        },
        image: function () {
            this.emitChange()
        }
    },
    methods: {
        uploadImage: function () {
            var _this = this
            if(!WX_READY && !IS_DEVENV){
                $.toptip("上传图片初始化中，请稍后再试", "warning")
                return 
            }
            wxInit_promise.wxUploadImage().then(function(src){
                _this.image = src
            }).fail(function (error) {
                alertMsg.error(error)
            })
        },
        scanCodeHandler: function () {
            var _this = this
            if(!WX_CAN_SCAN && !IS_DEVENV){
                $.toptip("暂时无法使用扫一扫，请稍后再试", "warning")
                return 
            }
            wxInit_promise.wxScanBarCode().then(function(res){
                var barCode = _normalizeBarCode(res)
                if(barCode){
                    _this.inputBarCode = barCode
                }else{
                    alertMsg.error({message:'不是有效的产品码'})
                }
            }).fail(function(err){
                alertMsg.error(err)
            })
        },
        emitChange: function () {
            this.$emit('change', {
                productBarCode: this.inputBarCode,
                productImage: this.image
            })
        }
    },
    created() {
        // this.inputBarCode = this.barCode
        this.image = this.productImage
    }
}

var CheckBoxTable = {
    template:'#check_box_table_template',
    props:{
        subject:{
            type:Object,
            default:function(){
                return {name:'',id:''}
            }
        },
        priceName:{
            type:String,
            default:"price"
        },
        rows:{
            type:Array,
            default:function(){
                return []
            }
        }
    },
    data:function(){
        return {
            innerRows:[],
            invalidPrice:0
        }
    },
    created:function(){
        this.innerRows = this.rows
    },
    watch:{
        rows:function(cur,old){
            if (JSON.stringify(cur) === JSON.stringify(old)){
                return 
            }
            this.innerRows = cur
        },
        innerRows:function(cur,old){
            this.$emit('change',cur)
        }
    },
    methods:{
        toggleCheck:function(index){
            var row = this.innerRows[index]
            if(!row.isChecked ) {
                row.isChecked = true
            }else{
                row.isChecked = !row.isChecked
            }
            this.innerRows.splice(index,1,row)
        },
        priceChange:function(index){
            var row = this.innerRows[index]
            var price = Number(row[this.priceName]) || 0
            if(price <= 0 ){
               row[this.priceName] = this.invalidPrice
            }
            if(row.isChecked){
                this.innerRows.splice(index,1,row)
            }
        }
    }
}

var app = new Vue({
    el: '#app',
    components: {
        Barcode: Barcode,
        CheckBoxTable:CheckBoxTable
    },
    data: {
        curIndex: '',
        curProduct: '',
        isChangeFilter:false,
        maintainTable:{
            subject:{name:'保养措施名称',id:'maintainName'},
            rows:[]
        },
        filterTable:{
            subject:{name:'滤芯名称',id:'filterName'},
            rows:[]
        }
    },
    methods: {
        getProductData: function () {
            var def = $.Deferred()
            var curIndex = this._getProductIndexById(STORAGE_CURPRODUCT_ID)
            if (curIndex === undefined) {
                $.alert('获取产品信息失败', '提示')
                def.reject()
            }
            this.curIndex = curIndex
            this.curProduct = STORAGE_PRODUCTS[curIndex]
            def.resolve()
            return def.promise()
        },
        getMaintainAndFilters:function (){
            var _this = this
            var params = {
                "levelId":_this.curProduct.levelId,
                "productCode":_this.curProduct.productCode
            }
            ajax.get(queryUrls.qyhGetMaintainAndFilter,params).then(function(data){
               _this.maintainTable.rows = data.data.maintainPriceList
               _this.filterTable.rows = data.data.filterList
            }).fail(function(error){
                alertMsg.error(error)
            })
        },
        codeImgChangeHandler: function (data) {
            Object.assign(this.curProduct, data)
        },
        mantainChangeHandler:function(data){
            this.maintainTable.rows = data
        },
        updateFilterHandler:function(data){
            this.filterTable.rows = data
        },
        comfirmHandler: function () {
            var comfirmData = this._normalizeComfirmData()
            this._checkData(comfirmData)
            .then(function(){
                comfirmData.products = JSON.stringify(comfirmData.products)
                return ajax.post(queryUrls.qyhSubmitOrder_maintain,comfirmData)
            })
            .then(function(data){
                if(data.returnCode !== ERR_OK){
                    alertMsg.error({message:data.returnMsg})
                    return 
                }
                var params = {userId:USERID,ordersCode:ORDERSCODE}
                var jumpto =  getParamStr(pageUrls.unfinishedWorkOrderDetail_maintain,params)
                location.href = jumpto
            })
            .fail(function(error){
                alertMsg.error(error)
            })
        },
        _checkData: function (data) {
            var def = $.Deferred()
            var products = data.products
            if (!products.barCodeImage && !products.productBarCode) {
                def.reject({message:'请输入产品条码或上传产品图片'})
            }
            if(data.is_change_filter == 2 && !products.filters){
                def.reject({message:"请选择更换的滤芯"})
            }
            if(products.productBarCode){
                ajax.get(queryUrls.queryPdtByCodeOrModal,{"productBarCode":products.productBarCode},"查询产品中")
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
        },
        _normalizeComfirmData(){
            var resObj = {
                "userId" : USERID,
                "ordersCode": ORDERSCODE,
                "is_change_filter": this.isChangeFilter ? 2 : 1
            }
            var products = {
                productId:this.curProduct.productId  
            }
            var maintainPrices  =    this._getCheckedItem(this.maintainTable.rows),
                filters         =    this._getCheckedItem(this.filterTable.rows)
            if(this.curProduct.productImage)    products.barCodeImage = this.curProduct.productImage
            if(this.curProduct.productBarCode)  products.productBarCode = this.curProduct.productBarCode
            if(maintainPrices.length)           products.maintainPrices = maintainPrices
            if(filters.length)                  products.filters = filters
            resObj.products = products
            return resObj
        },
        _getCheckedItem(arr){
            var resArr = []
            for(var i = 0 ;i < arr.length ; i++){
                if(arr[i].isChecked){
                    var tmpObj = {}
                    for(var key in arr[i]){
                        if(key === 'isChecked') continue
                        if(key === 'maintainPrice') {
                            tmpObj.price = arr[i][key]
                            continue
                        }
                        tmpObj[key] = arr[i][key]         
                    }
                    resArr.push(tmpObj)
                }
            }
            return resArr
        },
        _getProductIndexById: function (productId) {
            for (var i = 0; i < STORAGE_PRODUCTS.length; i++) {
                var product = STORAGE_PRODUCTS[i]
                if (product.productId == productId) {
                    return i
                }
            }
            return undefined
        }
    },
    created: function () {
        this.getProductData().then(this.getMaintainAndFilters)
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

function setCurProductId2LocalStorage(productId) {
    window.localStorage.setItem('unfinished_cur_productId', productId)
}
function setProducts2LocalStorage(products) {
    window.localStorage.setItem('unfinished_products', JSON.stringify(products))
}








