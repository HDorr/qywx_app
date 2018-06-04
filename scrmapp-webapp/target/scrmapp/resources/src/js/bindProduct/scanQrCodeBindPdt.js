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
                scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码
                success: function (res) {
                    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
					var barCodeRegExp = new RegExp('barcode=([^&]*)(&|$)', 'i')
					var barCode = result.match(barCodeRegExp)
					if(barCode!=null){
						barCode = unescape(barCode[1])
					}else{
						barCode = result.split(",")[1]					
					}
					if(barCode){
						queryProductByBarcode(barCode)
					}
                }
            });
        } else {
            $.toptip("暂时无法使用扫一扫，请稍后再试", "warning")
        }

    })

    $(".qy-btn-all.nextStep").click(function(){
        queryProductByBarcode()
    })

    $(".haventFindQrCode").click(function() {
        // window.location.href = rootUrl + "/scrmapp/consumer/product/bind/noscan/page";
        window.location.href = pageUrls.noScanToBindPdt;
    })



    function queryProductByBarcode(barCode) {  
        var el = $("#pdtCodeInput")
        if(barCode) el.val(barCode)
        var code = el.val()
        if(!code){
            $.toptip("请输入条码", "error")
            return
        }
        /* if(!_checkBarCode(code)){
            $.toptip("请使用15或20位条码", "error")
            return
        } */
        $.showLoading("查询中")
        $.ajax({
            url: rootUrl + "/scrmapp/consumer/product/query",
            data: {
                productBarCode: code
            },
            type: "GET",
            success: function(data) {
                $.hideLoading();
                setTimeout(function() {
                    if (data.returnCode === 1) {
                        $.toast("查询成功", 500, function() {
                            // window.location.href = rootUrl + "/scrmapp/consumer/product/query/page?productBarCode=" + data.data.productBarCode;
                            window.location.href = pageUrls.bindPdtMain + "?productBarCode=" + code;
                        })
                    } else {
                        // $.toast("获取信息失败", "cancel")
                        window.location.href = rootUrl + "/scrmapp/consumer/product/bindQrCode/fail/page"

                    }
                }, 400)
            },
            error: function(data) {
                $.hideLoading();
                setTimeout(function() {
                    $.toast("网络错误", "cancel")
                }, 400)

            }
        })
    }

    function _checkBarCode(barCode){
        var code = barCode
        if(typeof(code) !== "number") code = code.toString()
        if((code).length !== 15 && (code).length !== 20) return false
        return true
    }
})(jQuery, window)