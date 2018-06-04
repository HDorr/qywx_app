(function($, window) {
	var localData = {
		notFirstTime: localStorage.getItem("chooseProductNotFirstTime"),
		default_checked_products: JSON.parse(localStorage.getItem("reserve_products")||"[]")
	}
	var products_list = []
	var rootUrl = rootPath,
		orderType = getUrlParam("orderType")-0,
      scOrderItemId= getUrlParam("scOrderItemId"),
		addressId = localStorage.getItem('reserve_addressid') || ""
	$(function(){
		getAndRenderList()
		addNewPdtTrigger()
		togglePdtCheckTrigger()
		comfirmChoseTrigger()
	})
	

	
	function getAndRenderList() {
		ajax.get(queryUrls.getProductsList)
			.then(function (res) {
				if (res.returnCode !== ERR_OK) {
					$.alert(res.returnMsg)
					return
				}
				products_list = res.data
				if (products_list.length === 0) {
					window.location.href = pageUrls.scanToBindPdt;
				} else {
					_randerList(products_list)
					if (!localData.notFirstTime) {
						localStorage.setItem("chooseProductNotFirstTime", true);
						setTimeout(function(){
							$.alert("您好，根据您的注册信息，我们已对您的历史产品进行绑定！")
						},100)						
					}
				}
			})
			.fail(function (err) {
				$.alert(err)
			})
	}

	function addNewPdtTrigger(){
		$('#addNewProduct').click(function(event) {
			window.location.href = pageUrls.scanToBindPdt;
		});
	}

	function togglePdtCheckTrigger(){
		$('.productList').on('click', 'li', function(event) {
			var $this = $(this)
			$this.find('.checkBox').toggleClass('checked')
		});
	}

	function comfirmChoseTrigger(){
		$('#comfirmChose').click(function(event){
			var checked_products = _getCheckedProducts()
			if(!checked_products){
				$.alert('请至少选择一个产品')
				return
			}
			localStorage.setItem('reserve_products',JSON.stringify(checked_products))
			var params = addressId ? {addressId:addressId} :{}
			params.scOrderItemId=scOrderItemId
			switch(orderType){
				case INSTALL_ORDER:
					var jumpTo = getParamStr(pageUrls.reserveInstallPage,params)
					location.href =	jumpTo
					break;
				case MAINTAIN_ORDER:
					var jumpTo = getParamStr(pageUrls.reserveMaintainPage,params)
					location.href =jumpTo
					break;
				case CLEAN_ORDER:
					var jumpTo = getParamStr(pageUrls.reserveCleanPage,params)
					location.href = jumpTo
					break;
				case UPDATE_FILTER_ORDER:
					var jumpTo = getParamStr(pageUrls.reserveUpdateFilterPage,params)
					location.href = jumpTo
					break;
			}
		})
		
	}


	/**
	 * 渲染表格
	 * @param {*} productData 
	 */
	function _randerList(productData) {
		var renderHtml = ""
		for (var i = 0; i < productData.length; i++) {
			var product = productData[i]
			var checkBoxClass = _hasDefaultId(product.id)  ? "checkBox checked" : "checkBox"
			renderHtml += '<li data-productid="' + product.id + '">' +
				'                  <div class="pdtInfo">' +
				'                      <div class="checkBox-container">' +
				'                          <div class="'+ checkBoxClass +'" data-productid="' + product.id + '"></div>' +
				'                      </div>' +
				'                      <div class="pdtImg" >' +
				'                            <img src="' + product.productImage + '" alt="">' +
				'                        </div>' +
				'                      <div class="pdtText">' +
				'                          <p>' + product.productName + '</p>' +
				'                          <p>产品条码: ' + product.productBarCode + '</p>' +
				'                          <p>产品型号: ' + product.modelName + '</p>' +
				'                      </div>' +
				'                  </div>' +
				'              </li>';
		}
		$('.productList').html(renderHtml)
	}

	/**
	 * 用productId来判断是否默认被选中
	 * @param {*} productId 
	 */
	function _hasDefaultId(productId){
		var id = productId + ''
		var	defaultList = localData.default_checked_products
		for(var i = 0;i <defaultList.length; i++){
			if(defaultList[i].id == id) return true
		}
		return false
	}

	/**
	 * 提交的时候获取已选中的产品列表
	 */
	function _getCheckedProducts(){
		var checkedItems = $('.checkBox.checked')
		if(checkedItems.length === 0) return false;
		var checked_products = []
		checkedItems.each(function(i,v){
			var id = $(v).data('productid')
			var product = _getProductById(id)
			if(product){
				checked_products.push(product)
			}
		})
		return checked_products.length === 0 ? false : checked_products
	}

	function _getProductById(id){
		for(var i = 0;i<products_list.length;i++){
			if(products_list[i].id == id){
				return products_list[i]
			}
		}
		return undefined
	}
})(jQuery, window)