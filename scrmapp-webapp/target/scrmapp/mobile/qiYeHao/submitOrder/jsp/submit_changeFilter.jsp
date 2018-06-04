<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>完工提交-保养</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/index/index.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/qiYeHao/submit_order.css${f_ver}">
    </head>
    <body>
        <div class="topTips">
            <p>请确认信息后再提交！</p>
        </div>
        <div class="main_layer ">
        
            <div class="order_info">
                <div class="title">工单状态</div>
                <div class="orderNum">
                    <span class="pull-left">受理单号：</span>
                    <span class="pull-left con">123456789987456</span>
                </div>
                <div class="productName">
                    <span class="pull-left">产品名称：</span>
                    <span class="pull-left con">沁园牌反渗透进水器</span>
                </div>
                <div class="productType">
                    <span class="pull-left">产品型号：</span>
                    <span class="pull-left con">QU-G4-1004(电子商务专供)</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">产品条码：</span>
                    <span class="pull-left con">123456789987456</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">联系信息：</span>
                    <span class="pull-left con">张三，12345678998</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">服务地址：</span>
                    <span class="pull-left con">上海市黄浦区XXX号XX路X楼</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">预约时间：</span>
                    <span class="pull-left con">2017-04-28  13:00~15:00</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">保养描述：</span>
                    <span class="pull-left con">更换滤芯</span>
                </div>
            </div>
            <div class="isChangeFilter">
                <div class="weui-cells weui-cells_checkbox">
                  <label class="weui-cell weui-check__label" for="s10">
                  是否换芯
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s10">
                      <i class="weui-icon-checked"></i>
                    </div>
                  </label>
                </div>
            </div>
            <div class="maintain">
                <div class="title">产品保养</div>
                <div class="weui-cells ">

                  <label class="weui-cell">
                    <div class="weui-cell__hd left">
                      <p>保养项</p>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>滤芯名称</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>收费金额（元）</p>
                    </div>
                  </label>
                </div>
                <div class="weui-cells weui-cells_checkbox">
                  <label class="weui-cell weui-check__label" for="s11">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s11">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>水质监测</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>--------</p>
                    </div>
                  </label>
                  <label class="weui-cell weui-check__label" for="s12">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s12">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>整机外部清洁</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>200.00</p>
                    </div>
                  </label>
                  <label class="weui-cell weui-check__label" for="s13">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s13">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>RO/超滤膜清洗</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>200.00</p>
                    </div>
                  </label>
                  <label class="weui-cell weui-check__label" for="s14">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s14">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>热胆/加热体除垢</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>200.00</p>
                    </div>
                  </label>
                  <label class="weui-cell weui-check__label" for="s15">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s15">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>其他</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>--------</p>
                    </div>
                  </label>
                </div>
            </div>
            <div class="maintain">
                <div class="title">产品换芯</div>
                <div class="weui-cells ">

                  <label class="weui-cell">
                    <div class="weui-cell__hd left">
                      <p>保养项</p>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>滤芯名称</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>收费金额（元）</p>
                    </div>
                  </label>
                </div>
                <div class="weui-cells weui-cells_checkbox">
                  <label class="weui-cell weui-check__label" for="s16">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s16">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>PP棉</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>111.00</p>
                    </div>
                  </label>
                  <label class="weui-cell weui-check__label" for="s17">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s17">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>颗粒活性炭</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>200.00</p>
                    </div>
                  </label>
                  <label class="weui-cell weui-check__label" for="s18">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s18">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>颗粒活性炭</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>200.00</p>
                    </div>
                  </label>
                  <label class="weui-cell weui-check__label" for="s19">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s19">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>颗粒活性炭</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>200.00</p>
                    </div>
                  </label>
                  <label class="weui-cell weui-check__label" for="s20">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="s20">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>颗粒活性炭</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>200.00</p>
                    </div>
                  </label>
                </div>
            </div>
        </div>
        <div class="btn">
            <button class="qy-btn-400">提交完工</button>
        </div>
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/src/js/qiYeHao/order_submit.js${f_ver}"></script>
    </body>
</html>
