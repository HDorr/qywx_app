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
        $.toptip("当前手机不支持扫一扫","warning")
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
            this.inputBarCode = (cur+'').replace(/[^0-9]/ig,"").substr(0,20)
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
            }).fail(function(err){
                alertMsg.error(err)
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

var MaintainActivity = {
    template: '#maintain_activity_template',
    props: {
        text: ""
    },
    methods: {
        updateActivity: function () {
            this.$emit('update')
        }
    }
}

var UsePart = {
    template: '#use_part_template',
    props:{
        parts:{
            type:Array,
            default(){
                return []
            }
        }
    }
}

var ChooseMaintainActivity = {
    template: '#chose_maintain_activity_template',
    props: {
        isShow: {
            type: Boolean,
            default: function () {
                return false
            }
        },
        typeName: {
            type: String,
            default: ''
        },
        smallcName: {
            type: String,
            default: ''
        },
        defaultCurId: {
            type: [Number,String],
            default: ''
        }
    },
    data: function () {
        return {
            activities: [],
            searchName: '',
            curId: ''
        }
    },
    watch: {
        isShow: function (cur) {
            if (cur) {
                this.curId = this.defaultCurId ? this.defaultCurId : this.curId
                this.getActivities()
            }
        }
    },
    methods: {
        comfirmHandler: function () {
            var curActivity = this._getActivityById(this.curId)
            if (curActivity === undefined) {
                alertMsg.error({ message: '未知的维修措施id' })
                return
            }
            this.$emit('comfirm', curActivity)
        },
        cancelHandler: function () {
            this.$emit('cancel')
        },
        searchHandler: function () {
            this.getActivities(this.searchName)
        },
        getActivities: function (name) {
            var _this = this
            var params = {
                typeName: encodeURI(this.typeName),
                smallcName: encodeURI(this.smallcName || "")
            }
            if (name) params.name = encodeURI(name)
            ajax.get(queryUrls.qyhGetRepairItem, params).then(function (data) {
                if (data.returnCode !== ERR_OK) {
                    $.alert(data.returnMsg, '返回错误')
                    return
                }
                _this.activities = data.data
            }).fail(function (error) {
                alertMsg.error(error)
            })
        },
        _getActivityById: function (id) {
            for (var i = 0; i < this.activities.length; i++) {
                var activity = this.activities[i]
                if (activity.id === id) return activity
            }
            return undefined
        }
    }
}

var ChooseMaintainParts = {
    template: '#chose_maintain_parts_template',
    props: {
        isShow: {
            type: Boolean,
            default: function () {
                return false
            }
        },
        modelName: {
            type: String,
            default: ''
        },
        defaultParts: {
            type: Array,
            default: function () { return [] }
        }
    },
    data: function () {
        return {
            name:"",
            parts: [],
            chosedParts: []
        }
    },
    computed: {
        isAllChecked: function () {
            for (var i = 0; i < this.parts.length; i++) {
                var part = this.parts[i]
                if (!part.isChecked) {
                    return false
                }
            }
            return true
        }
    },
    watch: {
        isShow: function (cur) {
            if (cur) {
                this.chosedParts = this.defaultParts ? this.defaultParts : this.chosedParts
                this.getParts()
            }
        }
    },
    methods: {
        getParts: function (name) {
            var _this = this
            var params = {
                modelName: encodeURI(this.modelName || "")
            }
            if (name) params.name = encodeURI(name)
            ajax.get(queryUrls.qyhGetRepairPart, params).then(function (data) {
                if (data.returnCode !== ERR_OK) {
                    $.alert(data.returnMsg, '返回错误')
                    return
                }
                _this.parts = _this._normalizeParts(data.data)
            }).fail(function (error) {
                alertMsg.error(error)
            })
        },
        toggleCheckHandler: function (index) {
            this.parts[index].isChecked = !this.parts[index].isChecked
        },
        toggleCheckAllHandler: function () {
            var curStatus = this.isAllChecked
            for (var i = 0; i < this.parts.length; i++) {
                var part = this.parts[i]
                part.isChecked = !curStatus
            }
        },
        cancelHandler: function () {
            this.$emit('cancel')
        },
        comfirmHandler: function () {
            var checkedParts = this._getCheckedPart(this.parts)    
            this.$emit('comfirm',checkedParts)
        },
        _normalizeParts: function (partsList) {
            var newParts = []
            for (var i = 0; i < partsList.length; i++) {
                var part = partsList[i]
                newParts.push(part)
                newParts[i].isChecked = false
                for (var j = 0; j < this.chosedParts.length; i++) {
                    var chosedPart = this.chosedParts[j]
                    if (part.id === chosedPart.id) newParts[i].isChecked = true
                }
            }
            return newParts
        },
        _getCheckedPart: function (partsList) {
            var resArr = []
            for (var i = 0; i < partsList.length; i++) {
                var part = partsList[i]
                if(part.isChecked){
                    resArr.push({"id":part.id,"name":part.name,"price":part.salePrice,"number":part.number || 1})
                }
            }
            return resArr
        }
    }
}

var app = new Vue({
    el: '#app',
    components: {
        Barcode: Barcode,
        MaintainActivity: MaintainActivity,
        UsePart: UsePart,
        ChooseMaintainActivity: ChooseMaintainActivity,
        ChooseMaintainParts: ChooseMaintainParts
    },
    data: {
        showPage: 1, //1：显示主页面 2：选择维修措施 3：选择配件
        curIndex: '',
        curProduct: '',
        repairItemStr: '',
        repairParts: []
    },
    methods: {
        getProductData: function () {
            var curIndex = this._getProductIndexById(STORAGE_CURPRODUCT_ID)
            if (curIndex === undefined) {
                $.alert('获取产品信息失败', '提示')
                return
            }
            this.curIndex = curIndex
            this.curProduct = STORAGE_PRODUCTS[curIndex]
            this.repairItemStr = this.curProduct.repairItemStr || ''
            this.repairParts = this.curProduct.repairParts || []
        },
        codeImgChangeHandler: function (data) {
            Object.assign(this.curProduct, data)
        },
        isNeedMaintainAcitvityHandler: function (isChecked) {
            if (isChecked) {
                this.repairItemStr = ''
                return
            }
            this.showPage = 2
        },
        comfirmChooseActivityHandler: function (data) {
            this.repairItemStr = data
            this.showPage = 1
        },
        isNeedMaintainPartsHandler: function (isChecked) {
            if (isChecked) {
                this.repairParts = []
                return
            }
            this.showPage = 3
        },
        comfirmChoosePartsHandler:function (parts) {
            this.repairParts = parts
            this.showPage = 1
        },
        cancelChooseActivityAndPartsHandler: function () {
            this.showPage = 1
        },
        comfirmHandler: function () {
            var _this = this
            this._checkData()
                .then(function(){
                    var queryData = _this._normalizeComfirmData(_this.curProduct,_this.repairItemStr,_this.repairParts)
                    //加弹出框,防止表单进行重复提交
                    $.showLoading()
                    return  ajax.post(queryUrls.qyhSubmitOrder_repair,queryData)
                })
                .then(function(data){
                    if(data.returnCode !== ERR_OK){
                        alertMsg.error(data)
                        return
                    }
                    var params = {"userId":USERID,"ordersCode":ORDERSCODE}
                    var url =  getParamStr(pageUrls.unfinishedWorkOrderDetail_repair,params)
                    setTimeout(function(){
                        $.toast('操作成功',function(){
                            location.href = url

                        })
                    },50)  
                })
                .fail(function(error){
                    alertMsg.error(error)
                })
                .always(function () {
                    $.hideLoading()
                })
        },
        _checkData: function () {
            var def = $.Deferred()
            if (!this.curProduct.productImage && !this.curProduct.productBarCode) {
                def.reject({message:'请输入产品条码或上传产品图片'})
            }
            if(this.curProduct.productBarCode){
                ajax.get(queryUrls.queryPdtByCodeOrModal,{"productBarCode":this.curProduct.productBarCode},"查询产品中")
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
        _getProductIndexById: function (productId) {
            for (var i = 0; i < STORAGE_PRODUCTS.length; i++) {
                var product = STORAGE_PRODUCTS[i]
                if (product.productId == productId) {
                    return i
                }
            }
            return undefined
        },
        _normalizeComfirmData: function(product,repairItem,repairParts){
            var res = {
                userId : USERID,
                ordersCode : ORDERSCODE,
                is_replace : repairParts.length === 0 ? 0 : 2
            }
            var products = {
                productId : product.productId,
                productBarCode : product.productBarCode
            }
            if(product.productImage) products.barCodeImage = product.productImage
            if(repairItem) products.repairItemStr = repairItem
            if(repairParts.length !== 0) products.repairParts = repairParts
            res.products = JSON.stringify(products)
            return res
        }
    },
    created: function () {
        this.getProductData()
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








