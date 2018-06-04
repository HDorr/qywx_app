
var flags = {
    submitFlag: true
};

(function($,window){
    wxInit_promise.init()
        .then(function(){})
        .fail(function(error){
            alertMsg.error({message:'上传图片功能初始化失败'})
        })

    var addressId = getUrlParam("addressId");
  var scOrderItemId = getUrlParam("scOrderItemId")
    var user_data = null
    var reserve_products = JSON.parse(localStorage.getItem('reserve_products') || "[]")
    var faultImage = [undefined,undefined,undefined]
    var can_submit = true
    var container_dom = {
        pdtInfoBox : $('#pdtInfoBox'),
        addressInfoBox : $('#addressInfoBox'),
        reserveInfoBox : $('#reserveInfoBox')
    }
    var getData ={
        data: getShowDate()
    };
    renderPdtArea()
    renderAddressArea()
    renderReserveArea()
    deleteProductClick()
    addProductClick()
    choseAddressClick()
    uploadImageClick()
    deletImageClick()
    submitReserveClick()
    pickerInit()



    function renderPdtArea(){
        var pdtInfoHtml = template('pdtInfo_template',{list:reserve_products})
        container_dom.pdtInfoBox.html(pdtInfoHtml)
    }
    function renderAddressArea(){
        ajax.get(queryUrls.queryAddrList).then(function(data){
            var renderdata = {data:null}
            if(data.returnCode === ERR_OK && data.data.length !== 0) {
                renderdata.data = addressId ? _getAddressById(data.data,addressId) : data.data[0]
                localStorage.setItem('reserve_addressid',renderdata.data.id)
                user_data = renderdata.data
            }
            var html = template('addressInfo_template',renderdata)
            container_dom.addressInfoBox.html(html)
        })
    }
    function renderReserveArea(){
        var html = template('reserveInfo_template',{})
        container_dom.reserveInfoBox.html(html)
    }
    function deleteProductClick(){
        container_dom.pdtInfoBox.on('click','.delete-product',function(e){
            var del_id = $(e.target).data('productid')
            reserve_products = _deletProductById(del_id)
            localStorage.setItem('reserve_products',JSON.stringify(reserve_products))
            renderPdtArea()
        })
    }
    function addProductClick(){
        container_dom.pdtInfoBox.on('click','.add-box',function(e){
            var params = {orderType:2}
            var targetUrl = getParamStr(pageUrls.reserveServicePdtList,params)
            location.href = targetUrl
        })
    }
    function choseAddressClick(){
        container_dom.addressInfoBox.on('click','.edit-address',function(e){
            var curUrl = escape(pageUrls.reserveMaintainPage)
            var jumpTo = pageUrls.chooseAddressPage + "?reserveUrl="+curUrl
            window.location.href=jumpTo;
        })
    }
    function uploadImageClick(){
        container_dom.reserveInfoBox.on('click','.imgBox',function(e){
            var $target = $(e.target)
            var index = $target.data('index')
            var imageSrc = null
            wxInit_promise.wxUploadImage().then(function(src){
                imageSrc = src
                _setImage(index,imageSrc)
            }).fail(function(error){
                $.alert(error.message,'上传失败')
            })
        })
    }
    function deletImageClick(){
        container_dom.reserveInfoBox.on('click','.cancelIcon',function(e){
            e.stopPropagation()
            var $target = $(e.target)
            var index = $target.data('index')
            _setImage(index)
        })
    }
    function imagePreviewClick(){

    }
    function submitReserveClick(){
        $("#submitReserve").click(function(e){
            if(!can_submit) return 
            var submit_data = _getSubmitParams()
            var errorMsg = _checkSubmitData(submit_data)
            if(errorMsg){
                $.alert(errorMsg, "提示")
                can_submit = true
                return 
            }
            //submit_data.uuid = JSON.stringify(submit_data)
          submit_data.scOrderItemId=scOrderItemId
            ajax.post(queryUrls.saveReserve,submit_data,true)
                .then(function(data){
                    if(data.returnCode !== ERR_OK){
                        $.alert(data.returnMsg,"错误")
                        return
                    }
                    var jumpTo = getParamStr(pageUrls.reserveSuccess,{
                        name: submit_data.contacts,
                        tel:submit_data.contactsMobile,
                        address:submit_data.address,
                        time:submit_data.orderTime,
                        contactsTelephone:submit_data.contactsTelephone 
                    },true)
                    location.href = jumpTo
                })
                .fail(function(err){alertMsg.error(err)})
                .always(function(){can_submit = true})
        })
    }
    //选择上门服务时间；
    function getShowDate() {
        var dataArr = getFutureDate(7);
        var values = [];
        var displayValues = [];
        var weekday = ["星期日","星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]
        $.each(dataArr, function(i, v) {
            if (i != 0) {
                var month = v.getMonth() + 1;
                var day = v.getDate();
                if (new Date().getHours() >= 18) {
                    var tmpV= new Date();
					tmpV.setTime(v.getTime()+24*60*60*1000);
					day = tmpV.getDate();
					month = tmpV.getMonth() + 1;
                    weekday = ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"]
                }
                month = month < 10 ? ("0" + month) : (month + "");
                day = day < 10 ? ("0" + day) : (day + "");
                var disStr = month + "月" + day + "日[" + weekday[v.getDay()] + "]"
                displayValues.push(disStr);
                var valStr = v.getFullYear() + "-" + month + "-" + day;
                values.push(valStr)
            }
        })

        return {
            values: values,
            displayValues: displayValues
        }
    }
    //初始化更改时间action框
    function pickerInit() {
        var oDate = new Date()
        var currentHour =oDate.getHours() //oDate.getHours();
        var currentDate =oDate.getDate();
        var timeArr =[],
        timeArr1=['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00'];
        if (currentHour<12&&currentHour>=10) {
            timeArr=['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
        }else if (currentHour<14&&currentHour>=12) {
            timeArr=['12:00-14:00', '14:00-16:00', '16:00-18:00']
        }else if (currentHour<16&&currentHour>=14) {
            timeArr=['14:00-16:00', '16:00-18:00']
        }else if (currentHour<18&&currentHour>=16) {
            timeArr=['16:00-18:00']
        }else{
            timeArr=['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
        }
        window.picker_prevValue = getData.data.values[0]
        $("#changeOrder").picker({
            cols: [{
                textAlign: 'center',
                values: getData.data.values,
                displayValues: getData.data.displayValues
            }, {
                textAlign: 'center',
                values:timeArr
            }],
            onClose: function() {
                $('.content').css('paddingBottom', '0.5rem');
                $("#changeOrder").text($("#changeOrder").val())
                //$(".orderTimeTips").css('display', 'block');

            },
            onChange:function(e,value,displayValue){
               if(e.value[0]==picker_prevValue){
                    return false;
               }else{
                    picker_prevValue = e.value[0]
                    if (e.cols[0].displayValue==getData.data.displayValues[0]) {
                        e.cols[1].replaceValues(timeArr,timeArr)
                        e.updateValue();
                    }else{
                        e.cols[1].replaceValues(timeArr1,timeArr1)
                        e.updateValue();
                    }
                }
                e.updateValue();
            }
        });
    }
    function previewImage(curentImgSrc,imgArray){
            var curent = curentImgSrc
            var imgArray = []
            for(var i = 0;i < imgArray.length; i++){
                imgArray.push(curent)
            }
            wx.previewImage({
                current: curent,
                urls: imgArray
            });
            
    }
    function _getAddressById(addressList,id){
        for(var i = 0; i<addressList.length;i++){
            if(addressList[i].id == id){
                return addressList[i]
            }
        }
        return null
    }
    function _deletProductById(id){
        var result = []
        for(var i=0; i<reserve_products.length;i++){
            if(reserve_products[i].id != id){
                result.push(reserve_products[i])
            }
        }
        return result
    }
    function _getSubmitParams(){
        var templateData = {
            "contacts":"",       //联系人姓名
            "contactsMobile":"",//联系人手机号
            "contactsTelephone":"",           //不确定
            "province":"",
            "city":"",
            "area":"",
            "address":"",
            "orderType":2,      //预约类型 1.安装 2.维修 3.保养(滤芯和清洗)'
	        "maintType":0,		//保养类型    orderType为3时, 1:清洗  2:滤芯;  orderType不等于3时,默认为0;',
	        "buyFilter":0,		//是否已购买滤芯 0:默认值 1:已购买  2:未购买',
            "orderTime":$('#changeOrder').val(),   
            "description":$("#description").val(),
            "faultImage":joinString(faultImage[0],faultImage[1]?",":"",faultImage[1]),
            "status":1,
            "productIds":""      
        }
        var res = {}
        for(var key in templateData) {
            var item = templateData[key]
            switch (key) {
                case 'province' :
                    res.province =  user_data.provinceName || "";
                    break;
                case "city" :
                    res.city = user_data.cityName || "";
                    break;
                case "area" :
                    res.area = user_data.areaName || "";
                    break;
                case "address" :
                    res.address = 
                    joinString(user_data.provinceName,user_data.cityName,user_data.areaName,user_data.streetName)
                    break;
                case "productIds" :
                    res.productIds = _getProductsIds(reserve_products)
                    break;
                case "faultImage" :
                    res.faultImage = __getNomalizeFaultImages(faultImage,true)
                    break;
                case "contactsTelephone":
                    res.contactsTelephone = user_data.fixedTelephone
                    break
                default :
                    res[key] = user_data[key] ? user_data[key] : templateData[key]
                    break;
                }
        }
        return res
    }
    function _checkSubmitData(data){
        if(!data.productIds.length)     return "请至少选择一个产品";
        if(!data.orderTime)             return "请选择上门时间";
        if(!data.contacts)              return "请选择有效地址";
        if(!data.description)           return "请填写故障描述";
        return null
    }
    function _getProductsIds(products){
        var ids = []
        for(var i = 0; i < products.length; i++) {
            var product = products[i]
            if(product.id) ids.push(product.id)
        }
        return ids.join(',')
    }
    function _setImage(index,imageUrl){
        var index = index,
            image = imageUrl || undefined
        faultImage[index] = imageUrl
        faultImage = __getNomalizeFaultImages(faultImage)
        for(var i = 0; i<faultImage.length;i++){
            if(faultImage[i]){
                $(".image.image_"+i).attr('src',faultImage[i]).show().siblings('.cancelIcon').show()
            }else{
                $(".image.image_"+i).attr('src',"").hide().siblings('.cancelIcon').hide()
            }
        }
    }
    function __getNomalizeFaultImages(faultImages,needString){
        var res = []
        var needString = needString || false
        for(var i = 0 ;i <faultImages.length;i++){
            if(faultImages[i])res.push(faultImages[i])
        }
        if(needString){
            res = res.join(',')
        }else{
            res.length = 3
        } 
        return res
    }

})(jQuery, window)



