var glData = {
    canScan: false
};

(function($, window) {
    rootUrl = getRootPath();

    wxInit.init();

    $.toast.prototype.defaults.duration = 1000;

    wx.ready(function() {
        wx.checkJsApi({
            jsApiList: ['scanQRCode'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function(res) {
                if (res.checkResult.scanQRCode) {
                    glData.canScan = true;
                } else {
                    $.toptip("当前手机不支持扫一扫功能", "warning")
                }
            }
        });
    })
    wx.error(function(res) {
        $.toptip("微信扫一扫功能无法使用", "warning")
    })


    $("#scanQrCode").on("click", function(e) {
        if (glData.canScan) {
            wx.scanQRCode({
                needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                scanType: ["qrCode"], // 可以指定扫二维码还是一维码，默认二者都有
                success: function(res) {

                    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                    var arr = result.split(",")
                    if (arr.length == 1) {
                    	// var reg = /barcode=(\d+)/;
                    	var reg = /\d+/;
	                    var r = reg.exec(result)[0];
	                    var barCode = r;
	                    $(".searInput").val(barCode);
	                    ajaxQuery1(barCode);
                    }else if(arr.length == 2){
                        var barCode = arr[1];
                        $(".searInput").val(barCode);
                        ajaxQuery1(barCode);
                    } else {
                        $.toast("不是有效二维码", "cancel")
                    }
                }
            });
        } else {
            $.toptip("暂时无法使用扫一扫，请稍后再试", "warning")
        }

    })


    function ajaxQuery1(code) {
        $.showLoading("查询中");
        setTimeout(function(){
            $.hideLoading();
            $.ajax({
                url: rootUrl + "/scrmapp/consumer/user/query/securityCode",
                data: {
                    barcode: code
                },
                type: "GET",
                success: function(data) {
                        if (data.returnCode === 1) {
                            console.log(data)
                            var prodName =data.data.prodName;
                            var spec =data.data.spec;
                            var barCode =data.data.barCode;
                            var glCode=data.data.glCode;
                            var jumpUrl = rootUrl + '/mobile/bindProduct/jsp/securityCheckSuccess.jsp?barCode='+escape(barCode)+"&spec="+escape(spec)+"&prodName="+escape(prodName)+"&glCode="+escape(glCode);
                            window.location.href=jumpUrl
                        } else {
                            var wxName = escape(data.data)
                            window.location.href=rootUrl + '/mobile/bindProduct/jsp/securityCheckFault.jsp?wxName='+wxName;
                        }
                },
                error: function(data) {
                    
                    setTimeout(function() {
                        $.toast("网络错误", "cancel")
                    }, 400)

                }
            })
        },300)
    }
    function ajaxQuery2(code) {
        $.showLoading("查询中");
        setTimeout(function(){
            
            $.ajax({
                url: rootUrl + "/weixin/checkSecurityCode",
                data: {
                    code: code
                },
                type: "GET",
                success:function(data){
                    $.hideLoading();
                    if(data.returnCode!=0){
                        data.status = 1
                         $.toast("查询成功",function(){
                            window.location.href = pageUrls.securityCheckRes+"?status="+data.status
                         });
                    }else{
                        data.status = 0
                        $.toast("查询成功",function(){
                            window.location.href = pageUrls.securityCheckRes+"?status="+data.status
                         });
                    }
                },
                error:function(data){
                    $.toast("网络错误","cancel")
                }
            })
        },300)
    }
	var tipP = $(".tipP");
    $("#checkCode").click(function(){
        var code = $("#code").val();
        if(code.length!=16&&code.length!=20){
            tipP.fadeIn(300).delay(3000).fadeOut(300)
        }else if(code.length==16){
            ajaxQuery2(code)
        }else if(code.length==20){
            ajaxQuery1(code)
        }
    })   
})(jQuery, window)