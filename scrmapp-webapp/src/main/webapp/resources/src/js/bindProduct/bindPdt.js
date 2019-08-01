var flags = {
    submitFlag: true
};
(function($, window) {
    $.toast.prototype.defaults.duration = 1000;
    var model = getUrlParam("modelName"),
        barCode = getUrlParam("productBarCode")||$('#barCodeIdInput').val(),
        securityCode = $('#securityCodeInput').val()
        rootUrl = getRootPath(),
        o2o = {
            o2o1: "",
            o2o2: ""
        },
        sendData = {
            "itemKind": null,
            "typeName": null,
            "modelName": null,
            "levelId": null,
            "levelName": null,
            "productName": null,
            "productCode": null,
            "productImage": null,
            "productBarCode": null,
            "productBarCodeTwenty": null,
            "shoppingOrder": null,
            "saleType": null,
            "o2o": 1,
            "saleTypeName": null,
            "smallcName":"",
            "buyChannel": null,
            "buyTime": null
        };

    if (model) {
        var data = {
            modelName: model
        }
    } else {
        var data = {
            productBarCode: barCode
        }
    };

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
        }
    })



    //=====================方法区域=====================

    function renderContent(data) {
        //通过条码查询
        if (data.productBarCode) {
            if (data.buyTime != ""){
                $("#datetime-picker").val("data.buyTime")
                $("#datetime-picker").attr("readonly",true)
            }
            var str = '<li class="pdtImg" style="background-image: url(\'' + data.productImage + '\');"></li>' +
                '                <li class="pdtInfoTitle">产品信息</li>' +
                '                <li class="pdtInfoForm firstThree"><span>产品条码:</span><span>' + data.productBarCode + '</span></li>' +
                '                <li class="pdtInfoForm firstThree"><span>产品名称:</span><span>' + data.productName + '</span></li>' +
                '                <li class="pdtInfoForm firstThree"><span>产品大类:</span><span>' + data.typeName + '</span></li>' +
                '                <li class="pdtInfoForm"><span>产品编号:</span><span>' + data.productCode + '</span></li>' +
                '                <li class="pdtInfoForm"><span>产品型号:</span><span>' + data.modelName + '</span></li>' +
                '                <li class="pdtInfoForm"><span>滤芯级别:</span><span>' + data.levelName + '</span></li>' +
                '                <li class="pdtInfoForm" data-saletype=' + data.saleType + '><span>销售类型:</span><span>' + data.saleTypeName + '</span></li>' +
                '                <li class="pdtInfoForm">' +
                '                    <span>购买渠道:</span>' +
                '                    <div class="weui-cells_checkbox">' +
                '                        <label class="weui-check__label" for="online">' +
                '                            <input type="radio" class="weui-check o2oCheck" name="check" data-value="1" id="online" checked="checked">' +
                '                            <i class="weui-icon-checked"></i>' +
                '                            <span>线上</span>' +
                '                        </label>' +
                '                        <label class="weui-check__label" for="offline">' +
                '                            <input type="radio" class="weui-check o2oCheck" data-value="2" name="check" id="offline" >' +
                '                            <i class="weui-icon-checked"></i>' +
                '                            <span>线下</span>' +
                '                        </label>' +
                '                    </div>' +
                '                    <select id="selectBox">' +
                '                        </select>' +
                '                </li>'+
                '<li class="pdtInfoForm"><span>购买日期:</span><span><input placeholder="请选择购买日期" type="text" id="datetime-picker"/></span></li>';

            //通过名字
        } else {
            var str = '<li class="pdtImg" style="background-image: url(\'' + data.productImage + '\');"></li>' +
                '                <li class="pdtInfoTitle">产品信息</li>' +
                '                <li class="pdtInfoForm firstThree"><span>产品名称:</span><span>' + data.productName + '</span></li>' +
                '                <li class="pdtInfoForm firstThree"><span>产品大类:</span><span>' + data.typeName + '</span></li>' +
                '                <li class="pdtInfoForm"><span>产品编号:</span><span>' + data.productCode + '</span></li>' +
                '                <li class="pdtInfoForm"><span>产品型号:</span><span>' + data.modelName + '</span></li>' +
                '                <li class="pdtInfoForm"><span>滤芯级别:</span><span>' + data.levelName + '</span></li>' +
                '                <li class="pdtInfoForm">' +
                '                    <span>购买渠道:</span>' +
                '                    <div class="weui-cells_checkbox">' +
                '                        <label class="weui-check__label" for="online">' +
                '                            <input type="radio" class="weui-check o2oCheck" name="check" data-value="1" id="online" checked="checked">' +
                '                            <i class="weui-icon-checked"></i>' +
                '                            <span>线上</span>' +
                '                        </label>' +
                '                        <label class="weui-check__label" for="offline">' +
                '                            <input type="radio" class="weui-check o2oCheck" data-value="2" name="check" id="offline" >' +
                '                            <i class="weui-icon-checked"></i>' +
                '                            <span>线下</span>' +
                '                        </label>' +
                '                    </div>' +
                '                    <select id="selectBox" >' +
                '                    </select>' +
                '                </li>'+
                '<li class="pdtInfoForm"><span>购买日期:</span><span><input placeholder="请选择购买日期" type="text" id="datetime-picker"/></span></li>';
        }

        $(".pdtInfo").html(str);
        /*$("#datetime-picker").datetimePicker(
            {
                times: function() {
                    return [];
                }
            }
        )*/
        $("#datetime-picker").calendar(
            {
                maxDate:new Date(new Date().getTime() - (1000 * 60 * 60 * 24))
            }
        )

        //初始化购买渠道selector
        renderSelectOpt(1)

        //注册线上线下切换事件
        updateSelectorEvt();

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

    //获取选择的购买渠道并渲染 参数1为线上 2为线下
    function renderSelectOpt(flag) {
        if (o2o["o2o" + flag]) {
            $("#selectBox").html(o2o["o2o" + flag]);
        } else {
            $.ajax({
                url: rootUrl + "/scrmapp/consumer/product/choice",
                type: "GET",
                data: {
                    o2o: flag
                },
                success: function(data) {
                    if (data.returnCode === 1) {
                        o2o["o2o" + flag] = (seto2o(data));
                        $("#selectBox").html(o2o["o2o" + flag])
                    } else {
                        $.toast("获取购买渠道失败", "cancel")
                    }
                }
            })
        }

        //给o2oStr赋值
        function seto2o(data) {
            var str = '<option selected="" value="0">请选择</option>';
            $.each(data.data, function(i, v) {
                str += '<option value="' + v.code + '">' + v.name + '</option>'
            })
            return str;
        }
    }

    //线上线下渠道变化时更新购买渠道selector
    function updateSelectorEvt() {
        $(".o2oCheck").on("change", function(e) {
            var o2oCode = $(this).data("value")
            renderSelectOpt(o2oCode)
        })
    }

    //提交绑定事件
    function bindPdtEvt() {
        $("#bindPdtBtn").click(function(e) {
            if (!flags.submitFlag) {
                return;
            }
            flags.submitFlag = false;
            var buyChannel = $("#selectBox").val();
            var buyTime =  $("#datetime-picker").val();
            if (buyChannel == 0) {
                $.toast("请选择购买渠道", "cancel");
                flags.submitFlag = true;
            } else if(!buyTime){
                $.toast("请选择购买日期", "cancel");
                flags.submitFlag = true;
            } else{
                $.showLoading();
                sendData.o2o = $(".o2oCheck:checked").data("value");
                sendData.buyChannel = buyChannel;
                sendData.shoppingOrder = $("input.qy-input-530").val();
                sendData.buyTime =buyTime;
                sendData.productBarCodeTwenty = barCode.length == 20 ? barCode : "";
                $.ajax({
                    url: rootUrl + "/scrmapp/consumer/product/save",
                    type: "POST",
                    data: sendData,
                    success: function(data) {
                        $.hideLoading();
                        setTimeout(function() {
                            if (data.returnCode === 1) {
                                // $.toast(data.returnMsg, function() {
                                //     window.location.href = pageUrls.bindPdtLists
                                // })
                              let scOrderItemId = localStorage.getItem("scOrderItemId");
                                window.location.href = rootUrl + "/scrmapp/consumer/product/bind/success/page?securityCode="+barCode+"&scOrderItemId="+scOrderItemId;
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
            }
        })
    }
})(jQuery, window)