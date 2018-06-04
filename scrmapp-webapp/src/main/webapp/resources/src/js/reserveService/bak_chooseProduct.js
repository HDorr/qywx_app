/*
 * @Author: Administrator
 * @Date:   2017-04-23 15:59:05
 * @Last Modified by:   Administrator
 * @Last Modified time: 2017-05-19 17:08:09
 */
var glData = {
	notFirstTime: window.localStorage.getItem("chooseProductNotFirstTime")
};
(function($, window) {
	shieldShare();
	var rootUrl = getRootPath();
	var orderType = getUrlParam("orderType");
	var flag = getUrlParam("flag");
	var productId = getUrlParam("productId");
	if (!productId && typeof(productId) != "undefined" && productId != 0) {
		productId = ''
	}
	var addressId = getUrlParam("addressId");
	if (!addressId && typeof(addressId) != "undefined" && addressId != 0) {
		addressId = ''
	}
	var productData;
	$.ajax({
		url: rootUrl + '/scrmapp/consumer/product/list', ///resources/fakeJson/productLists.json/scrmapp/consumer/product/list
		type: 'GET',
		dataType: 'JSON',
		success: function(res) {
			productData = res.data;
			if (productData.length==0) {
				$('.weui-loadmore').eq(1).css('paddingTop', '10px');
				$('.weui-loadmore').eq(0).css('display', 'none');
				$('.weui-loadmore').eq(2).css('display', 'none');
				window.location.href = pageUrls.scanToBindPdt;


			} else {
				for (var i = 0; i < productData.length; i++) {
					var list = '<li>' +
						'<div class="pdtInfo" id="' + productData[i].id + '">' +
						'<div class=""></div>' +
						'<div class="pdtImg pull-left" style="background-image: url(' + productData[i].productImage + ')"></div>' +
						'<div class="pdtText pull-left">' +
						'<p>' + productData[i].productName + '</p>' +
						'<p>产品条码：<span>' + productData[i].productBarCode + '</span></p>' +
						'<p>产品型号：<span>' + productData[i].modelName + '</span></p>' +
						'</div>' +
						'</div>' +
						'</li>'

					$('.productList').append(list)
					$('.weui-loadmore').css('display', 'none');

				}
				if (!glData.notFirstTime) {
					window.localStorage.setItem("chooseProductNotFirstTime", true);
					$.alert("您好，根据您的注册信息，我们已对您的历史产品进行绑定！")
				}
				$('.productList').on('click', '.pdtInfo', function(event) {
					var id = $(this).attr('id')
					if (orderType == 1) {
						window.location.href = rootUrl + '/scrmapp/consumer/wechat/orders/service/install/page?orderType=' + orderType + '&productId=' + id + '&addressId=' + addressId + ''
					} else if (orderType == 2) {
						window.location.href = rootUrl + '/scrmapp/consumer/wechat/orders/service/repair/page?orderType=' + orderType + '&productId=' + id + '&addressId=' + addressId + ''
					} else if (orderType == 3) {

						window.location.href = rootUrl + '/scrmapp/consumer/wechat/orders/service/clean/page?orderType=' + orderType + '&productId=' + id + '&addressId=' + addressId + '&flag=' + flag + ''
					}

				});
			}
		}
	})


	$('#addNewPro').click(function(event) {
		window.location.href = pageUrls.scanToBindPdt;
	});



})(jQuery, window)