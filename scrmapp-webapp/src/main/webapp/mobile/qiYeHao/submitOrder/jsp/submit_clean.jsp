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
        <div class="main_layer "style="padding-top: 0px;">
        
        </div>
        <div class="btn">
            <button class="qy-btn-400">提交完工</button>
        </div>
        <script  id="orderSubmitTemplate" type="text/html">
            <input type="hidden" id="productId" value="{{productId}}">

            <div class="topTips">
                请确认信息后再提交！
            </div>
            <div class="scanArea clearfix">
                <div class="title">核实产品</div>
                <label class="pull-left">
                    产品条码：
                    <input class="qy-input-360" id="pdtCodeInput" type="number" placeholder="请输入产品条码">
                    <!-- <a href="javascript:;">如何查找产品条码？</a> -->
                </label>
                <div class="scanQrCode pull-right" id="scanQrCode" style="background-image: url('${f_ctxpath}/resources/images/bindProduct/scanQrCode.png');">
                    <p>扫码</p>
                </div>
            </div> 
            <div class="productImg" >
                <div style="border-top: 1px solid #f3f3f3;"></div>
                <div class="formBox">
                    <div class="noPicBox">
                        <span id="uploadBtn" onclick="upLoadImg()">
                            如果没有找到条码，请点击上传图片
                        </span>
                    </div>
                    <div class="picShowBox">
                        <span class="pull-left">现场图片：</span>
                        
                        <span class="picShow imgBox"><img src="" alt=""></span>
                        <span class="newUpLoad" onclick="upLoadImg()">重新上传</span>
                    </div>                
                </div>
                <p>请拍摄照片证明机器上无条码</p>
            </div>

            <div class="order_info">
                <div class="title">工单状态</div>
                <div class="orderNum">
                    <span class="pull-left">受理单号：</span>
                    <span class="pull-left con">{{ordersCode}}</span>
                </div>
                <div class="productName">
                    <span class="pull-left">产品名称：</span>
                    <span class="pull-left con">{{productName}}</span>
                </div>
                <div class="productType">
                    <span class="pull-left">产品型号：</span>
                    <span class="pull-left con">{{modelName}}</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">产品条码：</span>
                    <span class="pull-left con">{{productBarCode}}</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">联系信息：</span>
                    <span class="pull-left con">{{userName}},{{userMobile}}</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">服务地址：</span>
                    <span class="pull-left con">{{userAddress}}</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">预约时间：</span>
                    <span class="pull-left con">{{orderTime}}</span>
                </div>
                <div class="productCode">
                    <span class="pull-left">保养描述：</span>
                    <span class="pull-left con">{{description}}</span>
                </div>
            </div>
            <div class="isCheckItem isChangeFilter">
                <span class="checkBox"></span>
                <span>是否换芯</span>
            </div>
            {{if maintainPriceList&&maintainPriceList.length!=0}}
            <div class="itemBox pdtMaintain">
                <div class="itemHeader">
                  产品保养
                </div>
                <table class="itemTable">
                    <thead class="tableHeader">
                        <tr>
                            <td>保养项</td>
                            <td>保养措施名称</td>
                            <td>收费金额（元）</td>
                        </tr>
                    </thead>
                    <tbody class="tableBody">
                    {{each maintainPriceList as v index}}
                        <tr>
                            <td><span id="{{v.id}}" class="checkBox"></span></td>
                            <td class="itemName">{{v.maintainName}}</td>
                            <td><input class="itemPrice" type="number" value="{{v.maintainPrice}}"></td>
                        </tr>
                    {{/each}}
                    </tbody>
                </table>
            </div>
            {{else}}
            <div class="itemBox pdtMaintain">
                <div class="itemHeader">
                  产品保养
                </div>
                <table class="itemTable">
                    <thead class="tableHeader">
                        <tr>
                            <td>保养项</td>
                            <td>保养措施名称</td>
                            <td>收费金额（元）</td>
                        </tr>
                    </thead>
                    <tbody class="tableBody">
                        <tr>
                            <td colspan=3>该产品暂无保养数据！</span></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            {{/if}}

            {{if filterList&&filterList.length!=0}}
            <div class="itemBox changeFilter">
                <div class="itemHeader">
                  产品换芯
                </div>
                <table class="itemTable">
                    <thead class="tableHeader">
                        <tr>
                            <td>保养项</td>
                            <td>滤芯名称</td>
                            <td>收费金额（元）</td>
                        </tr>
                    </thead>
                    <tbody class="tableBody">
                    {{each filterList as v index}}
                        <tr>
                            <td><span id="{{v.id}}" class="checkBox"></span></td>
                            <td class="itemName">{{v.filterName}}</td>
                            <td><input class="itemPrice" type="text" value="{{v.price}}"></td>
                        </tr>
                    {{/each}}
                    </tbody>
                </table>
            </div>
            {{else}}
            <div class="itemBox changeFilter">
                <div class="itemHeader">
                  产品换芯
                </div>
                <table class="itemTable">
                    <thead class="tableHeader">
                        <tr>
                            <td>保养项</td>
                            <td>滤芯名称</td>
                            <td>收费金额（元）</td>
                        </tr>
                    </thead>
                    <tbody class="tableBody">
                        <tr>
                            <td colspan=3>该产品暂无滤芯数据！</span></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            {{/if}}
<!--             {{if filterList!=''&&filterList!=null&&filterList!=[]}}
            <div class="isChangeFilter">
                <div class="weui-cells weui-cells_checkbox">
                  <label class="weui-cell weui-check__label " for="s10" >
                  是否换芯
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="isChange" id="s10" value="2">
                      <i class="weui-icon-checked"></i>
                    </div>
                  </label>
                </div>
            </div>
            {{/if}}

            {{if maintainPriceList!=''&&maintainPriceList!=null&&maintainPriceList!=[]}}
            <div class="maintain isMaintain">
                <div class="title">产品保养</div>
                <div class="weui-cells top">

                  <label class="weui-cell">
                    <div class="weui-cell__hd left">
                      <p>保养项</p>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>保养措施名称</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>收费金额（元）</p>
                    </div>
                  </label>
                </div>
                <div class="weui-cells weui-cells_checkbox">
                {{each maintainPriceList as v index}}

                  <label class="weui-cell weui-check__label" for="a{{index}}">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="a{{index}}" value="{{index}}">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>{{v.maintainName}}</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>{{v.price}}</p>
                    </div>
                  </label>
                {{/each}}
                </div>
            </div>
            {{/if}}
            
            {{if filterList!=''&&filterList!=null&&filterList!=[]}}
            <div class="maintain changeFilter">
                <div class="title">产品换芯</div>
                <div class="weui-cells top">

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
                {{each filterList as v index}}
                  <label class="weui-cell weui-check__label" for="b{{index}}">
                    <div class="weui-cell__hd left">
                      <input type="checkbox" class="weui-check" name="checkbox1" id="b{{index}}" value="{{index}}">
                      <i class="weui-icon-checked"></i>
                    </div>
                    <div class="weui-cell__bd center">
                      <p>{{v.filterName}}</p>
                    </div>
                    <div class="weui-cell__ft right">
                      <p>{{v.price}}</p>
                    </div>
                  </label>
                  {{/each}}
                </div>
            </div>
            {{/if}}
 -->        </script>

        
    <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
    <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
    <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
    <script src="${f_ctxpath}/resources/src/js/qiYeHao/order_submit.js${f_ver}"></script>
    </body>
</html>
