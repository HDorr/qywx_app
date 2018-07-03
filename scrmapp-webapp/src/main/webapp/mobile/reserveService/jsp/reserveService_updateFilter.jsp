<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>预约更换滤芯</title>
    <meta name="viewport"
          content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="x5-fullscreen" content="true">
    <meta name="full-screen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
    <link rel="stylesheet"
          href="${f_ctxpath}/resources/src/css/reserveService/reserveService_updateFilter.css${f_ver}">
    <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/pop/common.css">
<%--    <script src="//cdn.jsdelivr.net/npm/eruda"></script>
    <script>eruda.init();</script>--%>
</head>
<body>
<header class="conHeader">
    <p>您可以提前预约，享受专业便捷的服务支持！</p>
</header>
<div class="pdt-info bloque" id="pdtInfoBox"></div>
<div class="user-info bloque" id="addressInfoBox"></div>
<div class="reserve-info bloque" id="reserveInfoBox"></div>
<footer class="conFooter" style="background:#fff">
    <button class="qy-btn-400" id="submitReserve">提交预约</button>
</footer>


<!-- 产品信息-template -->
<script type="text/html" id="pdtInfo_template">
    <header class="title">
        <img src="${f_ctxpath}/resources/src/images/icons/pdtInfo.png">
        产品信息
    </header>
    <ul class="productList">
        {{each list as product}}
        <li>
            <div class="pdtInfo">
                <div class="pdtImg">
                    <img src="{{product.productImage}}" alt="">
                </div>
                <div class="pdtText">
                    <p>{{product.productName}}</p>
                    <p>产品条码: {{product.productBarCode}}</p>
                    <p>产品型号: {{product.modelName}}</p>
                    {{if product.serviceStatus ==5 }}
                    <p style="color:red;">提示：该商品预约次数已用完</p>
                    <p style="color:red;">请到微信商城购买新滤芯</p>
                    {{else}}
                    <p>服务费名称: {{product.modelName}}</p>
                    <p>服务状态: {{product.serviceStatusStr}}</p>
                    {{/if}}
                </div>
                <div class="delete-product" data-productid="{{product.id}}"></div>
            </div>
        </li>
        {{/each}}
    </ul>
    <div class="add-box">
        <img src="${f_ctxpath}/resources/src/images/icons/add_grey.png">
        请点击添加您要预约滤芯服务的产品哟！
    </div>
</script>

<!-- 地址用户信息-template -->
<script type="text/html" id="addressInfo_template">
    <header class="title">
        <img src="${f_ctxpath}/resources/src/images/icons/personInfo.png">
        用户信息
    </header>
    {{if data}}
    <div class="info-box">
        <div class="userAvatar">
            <img src="{{data.headimgurl}}" alt="">
        </div>
        <div class="userText">
            <p><img src="${f_ctxpath}/resources/src/images/icons/personInfo.png">{{data.contacts}}
            </p>
            <p><img src="${f_ctxpath}/resources/src/images/icons/tel.png">
                {{data.contactsMobile}};{{data.fixedTelephone ? data.fixedTelephone : ""}}</p>
            <p><img src="${f_ctxpath}/resources/src/images/icons/location.png">{{data.provinceName}}{{data.cityName}}{{data.areaName}}{{data.streetName}}
            </p>
        </div>
        <div class="edit edit-address"></div>
    </div>
    {{else}}
    <div class="add-box edit-address">
        <img src="${f_ctxpath}/resources/src/images/icons/add_grey.png">
        请点击选择您预约服务的地址哟！
    </div>
    {{/if}}
    <p class="tip" style="color:#000">您是否已购买滤芯，请做如下选择</p>
    <div class="isBuyFilter">
        <div class="buyed isBuyFilterSelect" data-isbuyfilter="1">已购买滤芯</div>
        <div class="unbuyed isBuyFilterSelect" data-isbuyfilter="2">未购买滤芯</div>
    </div>
    <p class="isBuyFilter-tip tip">点击菜单-一键服务-产品防伪查询可查询产品真伪</p>
</script>

<!-- 预约信息-template -->
<script type="text/html" id="reserveInfo_template">
    <header class="title">
        <img src="${f_ctxpath}/resources/src/images/icons/reserve.png">re
        预约信息
    </header>
    <div class="timePickerContainer">
        <div>上门时间：</div>
        <div id="changeOrder">请填写上门服务时间</div>
    </div>
    <div class="reserveTime-tip tip">预约服务时间,请以工程师确认时间为准!</div>
    <div class="install-desc">
        <p>保养描述</p>
        <textarea id="description" placeholder="如有其他需要特别说明，请填写！"></textarea>
    </div>
</script>

<script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
<script src="${f_ctxpath}/resources/thirdparty/arttemplate/arttemplate.js"></script>
<script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
<script src="${f_ctxpath}/resources/src/js/reserveService/reserveService_updateFilter.js${f_ver}"></script>
<%--<script src="${f_ctxpath}/resources/src/pop/jquery-1.10.1.min.js"></script>
<script src="${f_ctxpath}/resources/src/pop/alertPopShow.js"></script>--%>
<script>
  var flags = {
    submitFlag: true
  };

  function loadExtentFile(filePath, fileType) {
    if (fileType == "js") {
      var oJs = document.create_rElement('script');
      oJs.setAttribute("type", "text/javascript");
      oJs.setAttribute("src", filename);//文件的地址 ,可为绝对及相对路径
      document.getElementsByTagName_r("head")[0].appendChild(oJs);//绑定
    } else if (fileType == "css") {
      var oCss = document.create_rElement("link");
      oCss.setAttribute("rel", "stylesheet");
      oCss.setAttribute("type", "text/css");
      oCss.setAttribute("href", filename);
      document.getElementsByTagName_r("head")[0].appendChild(oCss);//绑定
    }
  };

  $(function () {
    /*     wxInit.init();
        shieldShare();
        wx.error(function(res) {
            $.toast(("error:" + JSON.stringify(res)), "cancel")
        })
    */

    var isPageHide = false;
    window.addEventListener('pageshow', function () {
      if (isPageHide) {
        window.location.reload();
      }
    });
    window.addEventListener('pagehide', function () {
      isPageHide = true;
    });


    var addressId = getUrlParam("addressId");
    var scOrderItemId = getUrlParam("scOrderItemId")
    var user_data = null
    var reserve_products = JSON.parse(localStorage.getItem('reserve_products') || "[]");
    var can_submit = true;
    var getData = {
      data: getShowDate()
    };

    renderPdtArea()
    renderAddressArea()
    renderReserveArea()
    deleteProductClick()
    addProductClick()
    choseAddressClick()
    isBuyFilterClick()
    submitReserveClick()
    pickerInit()

    function renderPdtArea() {
      ajax.post(queryUrls.getProductsListWithServiceFeeStatus,reserve_products,true,'加载中...')
      .then(function (res) {
        if (res.returnCode !== ERR_OK) {
          $.alert("获取产品列表失败")
          return
        }
        reserve_products = res.data
        localStorage.setItem('reserve_products', JSON.stringify(reserve_products));
        if (reserve_products.length === 0) {
          window.location.href = pageUrls.scanToBindPdt;
        } else {
          var pdtInfoHtml = template('pdtInfo_template', {list: reserve_products})
          $("#pdtInfoBox").html(pdtInfoHtml)
        }
      })
      .fail(function (err) {
        $.alert(err)
      })
    }

    function renderAddressArea() {
      ajax.get(queryUrls.queryAddrList).then(function (data) {
        var renderdata = {data: null}
        if (data.returnCode === ERR_OK && data.data.length !== 0) {
          renderdata.data = addressId ? _getAddressById(data.data, addressId) : data.data[0]
          localStorage.setItem('reserve_addressid', renderdata.data.id)
          user_data = renderdata.data
        }
        var html = template('addressInfo_template', renderdata)
        $("#addressInfoBox").html(html)
      })
    }

    function renderReserveArea() {
      var html = template('reserveInfo_template', {})
      $("#reserveInfoBox").html(html)
    }

    function deleteProductClick() {
      $("#pdtInfoBox").on('click', '.delete-product', function (e) {
        var del_id = $(e.target).data('productid')
        reserve_products = _deletProductById(del_id)
        localStorage.setItem('reserve_products', JSON.stringify(reserve_products))
        renderPdtArea()
      })
    }

    function addProductClick() {
      $("#pdtInfoBox").on('click', '.add-box', function (e) {
        var params = {orderType: 4}
        var targetUrl = getParamStr(pageUrls.reserveServicePdtList, params)
        location.href = targetUrl
      })
    }

    function choseAddressClick() {
      $("#addressInfoBox").on('click', '.edit-address', function (e) {
        var curUrl = escape(pageUrls.reserveUpdateFilterPage)
        var jumpTo = pageUrls.chooseAddressPage + "?reserveUrl=" + curUrl
        window.location.href = jumpTo;
      })
    }

    function isBuyFilterClick() {

      $(document).on('click', ".isBuyFilterSelect", function (e) {
        var $target = $(e.target);
        $target.addClass('active').siblings('.isBuyFilterSelect').removeClass('active')
        var res = {};
        if ($target.data('isbuyfilter') == '1') {
          $target.parent('.isBuyFilter').siblings('.isBuyFilter-tip').show()
          res.productIds = _getProductsIds(reserve_products);
          res.payProductIds = _getPayProductsIds(reserve_products);
          //$.alert(JSON.stringify(res));

        } else {
          $target.parent('.isBuyFilter').siblings('.isBuyFilter-tip').hide()
          res.productIds = _getProductsIds(reserve_products);
          res.payProductIds = _getPayProductsIds(reserve_products);
        }
        localStorage.removeItem("isPaidSuccess");
        if (res.payProductIds!=undefined && res.payProductIds!='' ) {
          window.location.href = "http://dev.99baby.cn/scrmapp/wx/user/auth?productIds="
              + res.productIds;
        }
      })
    }

    function submitReserveClick() {
      $("#submitReserve").click(function (e) {
        if (!can_submit) return

        //再次校验
        var productIds = _getPayProductsIds(reserve_products);
        if (productIds!=undefined && productIds!=''){
          //清楚支付状态
          localStorage.removeItem("isPaidSuccess");
            window.location.href = "http://dev.99baby.cn/scrmapp/wx/user/auth?productIds="
                + productIds;
            return
        }

        var submit_data = _getSubmitParams()
        var errorMsg = _checkSubmitData(submit_data)
        if (errorMsg) {
          $.alert(errorMsg, "提示")
          can_submit = true
          return
        }
        if (scOrderItemId!=null && scOrderItemId!=undefined && scOrderItemId!=''){
          submit_data.scOrderItemId = scOrderItemId
        }
        ajax.post(queryUrls.saveReserve, submit_data, true)
        .then(function (data) {
          if (data.returnCode !== ERR_OK) {
            $.alert(data.returnMsg, "错误")
            return
          }
          var jumpTo = getParamStr(pageUrls.reserveSuccess, {
            name: submit_data.contacts,
            tel: submit_data.contactsMobile,
            address: submit_data.address,
            time: submit_data.orderTime,
            contactsTelephone: submit_data.contactsTelephone
          }, true)
          location.href = jumpTo
        })
        .fail(function (err) {
          alertMsg.error(err)
        })
        .always(function () {
          can_submit = true
        })
      })
    }

    //选择上门服务时间；
    function getShowDate() {
      var dataArr = getFutureDate(7);
      var values = [];
      var displayValues = [];
      var weekday = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]
      $.each(dataArr, function (i, v) {
        if (i != 0) {
          var month = v.getMonth() + 1;
          var day = v.getDate();
          if (new Date().getHours() >= 18) {
            var tmpV = new Date();
            tmpV.setTime(v.getTime() + 24 * 60 * 60 * 1000);
            day = tmpV.getDate();
            month = tmpV.getMonth() + 1;
            weekday = ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"]
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
      var currentHour = oDate.getHours() //oDate.getHours();
      var currentDate = oDate.getDate();
      var timeArr = [],
          timeArr1 = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00'];
      if (currentHour < 12 && currentHour >= 10) {
        timeArr = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
      } else if (currentHour < 14 && currentHour >= 12) {
        timeArr = ['12:00-14:00', '14:00-16:00', '16:00-18:00']
      } else if (currentHour < 16 && currentHour >= 14) {
        timeArr = ['14:00-16:00', '16:00-18:00']
      } else if (currentHour < 18 && currentHour >= 16) {
        timeArr = ['16:00-18:00']
      } else {
        timeArr = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
      }
      window.picker_prevValue = getData.data.values[0]
      $("#changeOrder").picker({
        cols: [{
          textAlign: 'center',
          values: getData.data.values,
          displayValues: getData.data.displayValues
        }, {
          textAlign: 'center',
          values: timeArr
        }],
        onClose: function () {
          $('.content').css('paddingBottom', '0.5rem');
          $("#changeOrder").text($("#changeOrder").val())
          //$(".orderTimeTips").css('display', 'block');

        },
        onChange: function (e, value, displayValue) {
          if (e.value[0] == picker_prevValue) {
            return false;
          } else {
            picker_prevValue = e.value[0]
            if (e.cols[0].displayValue == getData.data.displayValues[0]) {
              e.cols[1].replaceValues(timeArr, timeArr)
              e.updateValue();
            } else {
              e.cols[1].replaceValues(timeArr1, timeArr1)
              e.updateValue();
            }
          }
          e.updateValue();
        }
      });
    }

    function _getAddressById(addressList, id) {
      for (var i = 0; i < addressList.length; i++) {
        if (addressList[i].id == id) {
          return addressList[i]
        }
      }
      return null
    }

    function _deletProductById(id) {
      var result = []
      for (var i = 0; i < reserve_products.length; i++) {
        if (reserve_products[i].id != id) {
          result.push(reserve_products[i])
        }
      }
      return result
    }

    function _getSubmitParams() {
      var templateData = {
        "contacts": "",                                                          //联系人姓名
        "contactsMobile": "",                                                    //联系人手机号
        "contactsTelephone": "",                                                               //不确定
        "province": "",
        "city": "",
        "area": "",
        "address": "",
        "orderType": 3,                                                          //预约类型 1.安装 2.维修 3.保养(滤芯和清洗)'
        "maintType": 2,						                                    //保养类型    orderType为3时, 1:清洗  2:滤芯;  orderType不等于3时,默认为0;',
        "buyFilter": $(".isBuyFilterSelect.active").data('isbuyfilter') || "",		//是否已购买滤芯 0:默认值 1:已购买  2:未购买',
        "orderTime": $('#changeOrder').val(),
        "description": $("#description").val(),
        "faultImage": "",
        "status": 1,
        "productIds": "",
        "payProductIds": "",
        "serviceFeeIds":""
      }
      var res = {}
      for (var key in templateData) {
        var item = templateData[key]
        switch (key) {
          case 'province' :
            res.province = user_data.provinceName || "";
            break;
          case "city" :
            res.city = user_data.cityName || "";
            break;
          case "area" :
            res.area = user_data.areaName || "";
            break;
          case "address" :
            res.address =
                joinString(user_data.provinceName, user_data.cityName, user_data.areaName,
                    user_data.streetName)
            break;
          case "productIds" :
            res.productIds = _getProductsIds(reserve_products)
            break
          case "contactsTelephone":
            res.contactsTelephone = user_data.fixedTelephone
            break
          case "serviceFeeIds":
            res.serviceFeeIds = _getServiceFeeIds(reserve_products)
            break
          case "payProductIds":
            res.payProductIds = _getServiceFeeProductsIds(reserve_products)
            break
          default :
            res[key] = user_data[key] ? user_data[key] : templateData[key]
            break;
        }
      }
      return res
    }

    function _checkSubmitData(data) {
      if (!data.productIds.length) return "请至少选择一个产品";
      if (!data.buyFilter) return "请选择是否已购买滤芯";
      if (!data.orderTime) return "请选择上门时间";
      if (!data.contacts) return "请选择有效地址";
      if (!data.description) return "请填写保养描述";
      return null
    }

    function _getProductsIds(products) {
      var ids = []
      for (var i = 0; i < products.length; i++) {
        var product = products[i]
        if (product.id ) ids.push(product.id)
      }
      return ids.join(',')
    }

    function _getServiceFeeIds(products) {
      var ids = []


      for (var i = 0; i < products.length; i++) {
        var product = products[i]
        if (product.serviceFeeId ) ids.push(product.serviceFeeId)
      }
      return ids.join(',')
    }


    function _getPayProductsIds(products) {
      var ids = []
      for (var i = 0; i < products.length; i++) {
        var product = products[i]
        if (product.id && product.serviceStatus==0) ids.push(product.id)
      }
      return ids.join(',')
    }

    function _getServiceFeeProductsIds(products) {
      var ids = []
      for (var i = 0; i < products.length; i++) {
        var product = products[i]
        if (product.id && product.serviceStatus==1) ids.push(product.id)
      }
      return ids.join(',')
    }
  });


  document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    let paidStatus = localStorage.getItem("isPaidSuccess");
    console.log("支付结果" + paidStatus);
    if (paidStatus === "true") {
      console.log("支付成功");
      $(".buyed").addClass('active').siblings('.isBuyFilterSelect').removeClass('active');
      $(".isBuyFilter-tip").show();
    } else {
      console.log("未支付成功");
    }
  });



</script>
</body>
</html>