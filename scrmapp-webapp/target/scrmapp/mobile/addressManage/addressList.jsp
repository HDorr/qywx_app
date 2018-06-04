<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>地址列表</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/addressManage/addressList.css${f_ver}">
    </head>
    <body>
        <div class="address_main_layer">
            
            <div id="centerArea" class="container"></div>
        </div>

        <!-- 地址列表template -->
        <script type="text/html" id="addressListTemplate">
            {{each data as v i}}
            <div class="weui-cells addressContainer" data-id={{v.id}} data-userid=  {{v.userId}} >
                <div class="addressTitle weui-cell">
                    <p class="pull-left weui-cell__bd">{{v.contacts}}</p>
                    <p class="pull-right weui-cell__bd">{{v.contactsMobile}}</p>
                </div>
                <div class="address weui-cell" data-id={{v.id}} onclick="gotoOrderService(this)">
                    <p>{{v.provinceName+v.cityName+v.areaName+v.streetName}}</p>
                </div>
                <div class="seprator weui-cell"></div>
                <div class="opration weui-cell clearfix">
                    <div class="weui-cell__hd weui-cells_checkbox pull-left setDefaultBox">
                        <label class="weui-cell weui-check__label">
                            <div class="weui-cell__hd">
                                {{if v.isDefault==1}}
                                <input type="checkbox" class="weui-check defaultAddrCheck"  data-id={{v.id}} data-userid={{v.userId}} onchange="defaultAddrCheckOnChange   (this)" name="checkbox1" checked>
                                {{else}}
                                <input type="checkbox"  data-id={{v.id}} data-userid={{v.userId}} class="weui-check defaultAddrCheck" onchange="defaultAddrCheckOnChange(this)">
                                {{/if}}
                                <i class="weui-icon-checked"></i>
                            </div>
                            <div class="weui-cell__bd">
                                <p>设为默认</p>
                            </div>
                        </label>
                    </div>
                    <div class="pull-right weui-cell__bd detailOpt">
                        <a href={{"#/addrEdit?id="+v.id+"&userId="+v.userId}} data-id={{v.id}} data-userid= {{v.userId}}>
                            <span class="addrListEditIcon iconBox"></span>
                            编辑
                        </a>
                        <a href="javascript:;" data-id={{v.id}} data-userid={{v.userId}} onclick="confirmDelet(this)">
                            <span class="addrListDelIcon iconBox"></span>
                            删除
                        </a>
                    </div>
                </div>
            </div>
            {{/each}}
            <a id="addNewAddr" href={{"#/addrAdd?userId="+data[0].userId}} class="qyBlue">
                <p>添加新地址 +</p>
            </a>
        </script>

        <!-- 编辑地址template -->
        <script type="text/html" id="addressEditTemplate">
            <div class="weui-cells weui-cells_form addressEditCells">
                <div class="weui-cell">
                    <div class="weui-cell__hd"><label class="weui-label">联系人</label></div>
                    <div class="weui-cell__bd">
                        <input class="weui-input"  id="userContacts" value={{data.contacts}} type="text">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">联系电话</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input"   value={{data.contactsMobile}} id="userContactsMobile" type="tel" maxlength=11 onkeyup="value=value.replace(/[^\d]/g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\W]/g,''))" >
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">固定电话</label>
                    </div>
                    <div class="weui-cell__bd tel-box">
                        <input type="tel" maxlength=5 class="pull-left" id="telArea" value={{data.fixedTelephone[0]}}>
                        <span class="seprate-line pull-left">-</span>
                        <input type="tel" maxlength=8 class="pull-left"  id="telNum" value={{data.fixedTelephone[1]}}>
                    </div>
                </div>
                <a class="weui-cell weui-cell_access" id="select_contact" href="javascript:;">
                    <div class="weui-cell__bd">
                        <p>所在地区</p>
                    </div>
                    <div class="weui-cell__bd" data-provinceid={{data.provinceId}} data-cityid={{data.cityId}} data-areaid={{data.areaId}} data-provincename={{data.provinceName}} data-cityname={{data.cityName}} data-areaname={{data.areaName}} id="show_contact">{{data.provinceName+data.cityName+data.areaName}}</div>
                    <div class="weui-cell__ft">
                    </div>
                </a>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <textarea class="weui-textarea" id="userStreetName" placeholder="请输入文本"   rows="3">{{data.streetName}}</textarea>
                    </div>
                </div>
            </div>
            {{if data.isDefault != 1}}
            <div class="weui-cells weui-cells_form">
                <div class="weui-cell weui-cell_switch">
                    <div class="weui-cell__bd">设为默认</div>
                    <div class="weui-cell__ft">
                        <input class="weui-switch" id="userSetDefault" type="checkbox">
                    </div>
                </div>
            </div>
            {{/if}}
            <div class="weui-cells weui-cells_form">
                <a class="weui-cell weui-cell_access" data-id="{{data.id}}" data-userid="{{data.userId}}" onclick="confirmDelet(this)" id="addressEditDelBtn" href="javascript:;">
                    <div class="weui-cell__bd">
                        <p style="color:red;">删除地址</p>
                    </div>
                </a>
            </div>
            <div id="saveAddressBtn" data-id="{{data.id}}" data-userid="{{data.userId}}" data-faid="{{data.fAid}}" onclick="addressUpdate(this)" class="qyBlue">
                <p>保存</p>
            </div>
        </script>

        <!-- 新增地址template -->
        <script type="text/html" id="addressAddTemplate">
            <div class="weui-cells weui-cells_form addressEditCells">
                <div class="weui-cell">
                    <div class="weui-cell__hd"><label class="weui-label">联系人</label></div>
                    <div class="weui-cell__bd">
                        <input class="weui-input"   type="text" id="userContacts">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">用户手机</label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="tel" maxlength=11 onkeyup="value=value.replace(/[^\d]/g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\W]/g,''))" id="userContactsMobile">
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label">固定电话</label>
                    </div>
                    <div class="weui-cell__bd tel-box">
                        <input type="tel" maxlength=5 class="pull-left" id="telArea">
                        <span class="seprate-line pull-left">-</span>
                        <input type="tel" maxlength=8 class="pull-left"  id="telNum">
                    </div>
                </div>
                <a class="weui-cell weui-cell_access" id="select_contact" href="javascript:;">
                    <div class="weui-cell__bd">
                        <p>所在地区</p>
                    </div>
                    <div class="weui-cell__bd" data-provinceid="" data-cityid="" data-areaid="" data-provincename="" data-cityname="" data-areaname="" id="show_contact"></div>
                    <div class="weui-cell__ft">
                    </div>
                </a>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <textarea class="weui-textarea" id="userStreetName" placeholder="请输入详细地址"   rows="3"></textarea>
                    </div>
                </div>
            </div>
            <div class="weui-cells weui-cells_form">
                <div class="weui-cell weui-cell_switch">
                    <div class="weui-cell__bd">设为默认</div>
                    <div class="weui-cell__ft">
                        <input class="weui-switch" id="userSetDefault" type="checkbox">
                    </div>
                </div>
            </div>
            <div id="saveAddressBtn" onclick="addressAddSave(this)" class="qyBlue">
                <p>保存</p>
            </div>
        </script>

        <!-- 没有地址列表template -->
        <script type="text/html" id="hasNoAddressList">
            <div class="hasNoAddrBox">
                <div class="noAddrImg">
                    <img id="hasNoAddressListImg" src="/scrmapp/resources/images/hasNoAddressYet.png">
                </div>
                <p class="noAddrText">您目前还没有添加地址哟，现在来添加新地址吧</p>
                <a class="noAddrBtn qyBlue" href={{"#/addrAdd?userId="+userId}} >新增地址</a>
            </div>
        </script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/arttemplate/arttemplate.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/director/director.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/iscroll/build/iscroll.js"></script>
        <script src="${f_ctxpath}/resources/src/js/jdLikeAddrChose.js"></script>
        <script src="${f_ctxpath}/resources/src/js/addressManage/addressList.js${f_ver}"></script>    
    </body>
</html>