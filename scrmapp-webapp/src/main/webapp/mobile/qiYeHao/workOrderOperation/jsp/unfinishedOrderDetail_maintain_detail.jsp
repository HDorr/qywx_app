<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>填写完工信息</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,viewport-fit=cover">
        <meta name="x5-fullscreen" content="true">
        <meta name="full-screen" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/reset.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/weui.min.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/jqweui/jquery-weui.css">
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/workOrderOperation/unfinishedOrderDetail_maintain_detail.css${f_ver}">
    </head>
    <body>
        <input type="hidden" id="userIdInput" value="${userId}">
        <input type="hidden" id="ordersCodeInput" value="${ordersCode}">
        <div class="main_layer clearfix" id="app">
            <p class="topTip">请确认信息后再提交!</p>
            <div class="mainPage" v-if="curProduct">
                <div class="bloque">
                    <v-title title="核实产品" icon="${f_ctxpath}/resources/src/images/icons/pdtInfo.png"></v-title>
                    <barcode :bar-code="curProduct.productBarCode" :product-image="curProduct.barCodeImage" @change="codeImgChangeHandler">
                    </barcode>
                </div>  

                <div class="bloque isUpdateFilter">
                    <span>是否换芯:</span>
                    <button :class="{'active':isChangeFilter}" @click.stop="isChangeFilter = true">是</button>
                    <button :class="{'active':!isChangeFilter}" @click.stop="isChangeFilter = false">否</button> 
                </div>
                
                <div class="bloque productMaintain">
                    <v-title title="产品保养"></v-title>
                    <check-box-table price-name="maintainPrice" :subject="maintainTable.subject" :rows="maintainTable.rows" @change="mantainChangeHandler"></check-box-table>
                </div>

                <div class="bloque productUpdateFilter" v-show="isChangeFilter">
                    <v-title title="产品换芯"></v-title>
                    <check-box-table :subject="filterTable.subject" :rows="filterTable.rows" @change="updateFilterHandler"></check-box-table>
                </div>

                <div class="submitBtn" @click="comfirmHandler">提交完工</div>
            </div>                
        </div>

        <!-- header 模板 -->
        <script type="text/x-template" id="title_template"> 
            <header>
                <span class="title">{{title}}</span>
            </header>
        </script>

        <!-- checkBox-line 模板 -->
        <script type="text/x-template" id="check_box_line_tempalte">
            <div class="checkBoxContainer" :class="{'checked':isChecked}" @click="clickHandler">
                <span class="checkBox">
                    <i></i>
                </span>
                <span class="text">{{text}}</span>
            </div>
        </script>

        <!-- 产品条码模板 -->
        <script type="text/x-template" id="barCode_template">
            <div class="productInfo">
                <div class="barCode" >
                    <span class="left-text">
                        <i>*</i>输入产品条码:</span>
                    <input type="text" placeholder="请输入产品条码" v-model="inputBarCode">
                    <span class="scanBarCode" @click="scanCodeHandler"></span>
                </div>
                <div class="imageBox" v-show="image">
                    <div class="image" :style="{'background-image':'url('+image+')'}">
                        <i class="delImgIcon" @click="image=''"></i>
                    </div>
                </div>
                <div class="uploadImage" v-show="!image" @click="uploadImage">
                    <span class="addIcon"></span>
                    <span class="uploadText">如机器上无条码，请点击上传图片证明</span>
                    <p class="tip">请拍摄照片证明机器上无条码</p>
                </div>
            </div> 
        </script>

        <!-- checkbox table 模板 -->
        <script  type="text/x-template" id="check_box_table_template">
            <table class="checkBoxTable">
                <thead>
                    <tr>
                        <td>保养项</td>
                        <td>{{subject.name}}</td>
                        <td>收费金额(元)</td>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(row,index) in rows" :key="index" >
                        <td @click="toggleCheck(index)"><i class="checkbox" :class="{'checked':row.isChecked}"></i></td>
                        <td @click="toggleCheck(index)">{{row[subject.id]}}</td>
                        <td>
                            <input  type="text"  v-model="row[priceName]"  @change="priceChange(index)">
                        </td>
                    </tr>
                </tbody>
            </table>
        </script>

        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/vue/vue.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/workOrderOperation/unfinishedOrderDetail_maintain_detail.js${f_ver}"></script>
    </body>
</html>