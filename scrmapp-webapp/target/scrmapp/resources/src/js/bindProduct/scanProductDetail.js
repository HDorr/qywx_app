var flags = {
    submitFlag: true
};
(function($, window) {
    $.toast.prototype.defaults.duration = 1000;
    var barCode = getUrlParam("barcode"),
    	sourse = getUrlParam("from"),
    	productId = getUrlParam("productId"),
    	userId = getUrlParam("userId"),
    	orderType = getUrlParam("orderType"),
    	ordersCode = getUrlParam("ordersCode")
        rootUrl = getRootPath(),
        o2o = {
            o2o1: "",
            o2o2: ""
        },
        sendData = {
        	"productId":null,
            "itemKind": null,
            "typeName": null,
            "modelName": null,
            "levelId": null,
            "levelName": null,
            "productName": null,
            "productCode": null,
            "productImage": null,
            "productBarCode": null,
            "shoppingOrder": null,
            "saleType": null,
            "o2o": 1,
            "saleTypeName": null,
            "buyChannel": null
        };
        var data = {
            productBarCode: barCode
        }

    $.ajax({
        url: rootUrl + "/scrmapp/consumer/product/query",
        data: data,
        type: "GET",
        success: function(data) {
            if (data.returnCode === 1) {
                var data = data.data;
                for (var key in sendData) {
                    sendData[key] = data[key]
                }
                renderContent(sendData)
            } else {
                $.toast("获取信息失败", "cancel")
            }
        },
        error:function(){
        }
    })



    //=====================方法区域=====================

    function renderContent(data) {
        //通过条码查询
        if (data.productBarCode) {
            var str = '<li class="pdtImg" style="background-image: url(\'' + data.productImage + '\');"></li>' +
                '                <li class="pdtInfoTitle">产品信息</li>' +
                '                <li class="pdtInfoForm firstThree"><span>产品条码:</span><span>' + data.productBarCode + '</span></li>' +
                '                <li class="pdtInfoForm firstThree"><span>产品名称:</span><span>' + data.productName + '</span></li>' +
                '                <li class="pdtInfoForm firstThree"><span>产品大类:</span><span>' + data.typeName + '</span></li>' +
                '                <li class="pdtInfoForm"><span>产品编号:</span><span>' + data.productCode + '</span></li>' +
                '                <li class="pdtInfoForm"><span>产品型号:</span><span>' + data.modelName + '</span></li>' +
                '                <li class="pdtInfoForm"><span>滤芯级别:</span><span>' + data.levelName + '</span></li>' +
                '                <li class="pdtInfoForm" data-saletype=' + data.saleType + '><span>销售类型:</span><span>' + data.saleTypeName + '</span></li>' ;
        } 

        $(".pdtInfo").html(str);

        //注册提交事件
        bindPdtEvt();


        //如果没有图片获取图片
        if (!data.productImage) {
            getPdtImage(data.modelName, function(data) {
                data.data = data.data || rootUrl + "/resources/images/defaultPdtImg.jpg"
                $(".pdtImg").css("backgroundImage", "url('" + data.data + "')");
                sendData.productImage = data.data;
            })
        }
    }

    //获取产品图片
    function getPdtImage(modelName, callback) {
        $.ajax({
            url: rootUrl + "/scrmapp/consumer/product/getImage",
            type: "GET",
            data: {
                modelName: modelName
            },
            success: function(data) {
                callback(data);
            }
        })
    }


    //提交绑定事件
    function bindPdtEvt() {
        $("#confirmPdtBtn").click(function(e) {
            if (!flags.submitFlag) {
                return;
            }
            flags.submitFlag = false;
            var productId = getUrlParam("productId");
			var newData ={
	        	"productId":productId,
	            "itemKind": sendData.itemKind,
	            "productName": sendData.productName,
	            "productCode": sendData.productCode,
	            "productBarCode": sendData.productBarCode,
	            "typeName": sendData.typeName,
	            "modelName": sendData.modelName,
	            "levelId": sendData.levelId,
	            "levelName": sendData.levelName,
	            "saleType": sendData.saleType,
			}
			console.log(newData)
            $.ajax({
                url: rootUrl + "/scrmapp/qyhuser/orders/barcode/check",
                type: "POST",
                data: newData,
                success: function(data) {
                    $.hideLoading();
                    setTimeout(function() {
                        if (data.returnCode === 1) {
                            // $.toast(data.returnMsg, function() {
                            //     window.location.href = pageUrls.bindPdtLists
                            // })
                            window.location.href = rootUrl + "/scrmapp/qyhuser/orders/finish/detail/page?orderType="+orderType+"&from="+sourse+"&productId="+productId+"&userId="+userId+"&ordersCode="+ordersCode+"&barCode="+barCode;
                        } else {
                            $.toast(data.returnMsg, "cancel")
                        }
                        flags.submitFlag = true;
                    }, 400)
                },
                error: function(data) {

                    $.hideLoading();
                    setTimeout(function() {
                        $.toast("网络错误", "cancel")
                        flags.submitFlag = true;
                    }, 400)
                }
            })
        })
    }
})(jQuery, window)