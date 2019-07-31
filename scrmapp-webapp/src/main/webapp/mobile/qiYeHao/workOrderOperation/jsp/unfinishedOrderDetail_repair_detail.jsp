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
        <link rel="stylesheet" href="${f_ctxpath}/resources/src/css/workOrderOperation/unfinishedOrderDetail_repair_detail.css${f_ver}">
    </head>
    <body>
        <input type="hidden" id="userIdInput" value="${userId}">
        <input type="hidden" id="ordersCodeInput" value="${ordersCode}">
        <div class="main_layer clearfix" id="app">
            <div class="mainPage" v-if="curProduct">
                    <p class="topTip">请确认信息后再提交!</p>
                    <transition name="slide-left">
                        <div class="mainPage" v-show="showPage === 1">
                            <div class="bloque">
                                <v-title title="核实产品" icon="${f_ctxpath}/resources/src/images/icons/pdtInfo.png"></v-title>
                                <barcode :bar-code="curProduct.productBarCode" :product-image="curProduct.barCodeImage" @change="codeImgChangeHandler">
                                </barcode>
                            </div>
                            
                            <div class="bloque">
                                <v-check-box-line text="是否选择维修措施" :is-checked="repairItemStr?true:false" @click="isNeedMaintainAcitvityHandler">
                                </v-check-box-line>
                                <maintain-activity v-show="repairItemStr" 
                                    :text="repairItemStr.name"
                                    @update="showPage = 2">
                                </maintain-activity>
                            </div>
                            
                            <div class="bloque">
                                <v-check-box-line text="是否使用配件" :is-checked="repairParts.length?true:false" @click="isNeedMaintainPartsHandler">
                                </v-check-box-line>
                                <use-part v-show="repairParts.length" :parts="repairParts || []"></use-part>
                            </div>
                            
                            <div class="submitBtn" @click="comfirmHandler">提交完工</div>
                        </div>
                    </transition>
                     
                        
                    <choose-maintain-activity 
                        :is-show = "showPage === 2 ? true : false"
                        :default-cur-id = "repairItemStr.id || ''"
                        :type-name = "curProduct.typeName"
                        :smallc-name = "curProduct.smallcName"
                        @comfirm="comfirmChooseActivityHandler"
                        @cancel="cancelChooseActivityAndPartsHandler">
                    </choose-maintain-activity>
                    
                    <choose-maintain-parts 
                        :is-show = "showPage === 3 ? true : false"
                        :model-name = "curProduct.modelName || ''"
                        :default-parts = "repairParts || []"
                        @comfirm="comfirmChoosePartsHandler"
                        @cancel="cancelChooseActivityAndPartsHandler">                    
                    </choose-maintain-parts>          
            </div>
                
        </div>

        <!-- header 模板 -->
        <script type="text/x-template" id="title_template"> 
            <header>
                <img class="icon" :src="icon">
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
                    <div v-for="(item,index) in 6" class="image" @click="uploadImage(index)">
                        <i class="delImgIcon" @click.stop="delectImg(index)"></i>
                        <img :src="upImgArray[index]"  v-show="upImgArray[index]">
                    </div>
                </div>
                <div class="uploadImage" v-show="!image" @click="uploadImage(0)">
                    <span class="addIcon"></span>
                    <span class="uploadText">如机器上无条码，请点击上传图片证明</span>
                    <p class="tip">请拍摄照片证明机器上无条码</p>
                </div>
            </div> 
        </script>

        <!--维修措施模板 -->
        <script type="text/x-template" id="maintain_activity_template">
            <div class="maintainContent">
                <span></span>
                <span class="maintainActivity" @click="updateActivity">{{text || '未知'}}</span>
            </div>
        </script>

        <!-- 使用配件模板 -->
        <script type="text/x-template" id="use_part_template">
                <table class="parts-table">
                    <thead>
                        <tr>
                            <td>序号</td>
                            <td>配件名称</td>
                            <td>收费(元)</td>
                            <td>数量</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(part,index) in parts" :key="part.id">
                            <td class="indexTd">{{index+1}}</td>
                            <td class="partNameTd">{{part.name}}</td>
                            <td><input type="number" v-model.number="part.salePrice" class="priceInput"></td>
                            <td><input type="number" v-model.number="part.number" class="countInput"></td>
                        </tr>
                    </tbody>
                </table>
        </script>

        <!-- 选择维修措施模板 -->
        <script type="text/x-template" id="chose_maintain_activity_template">
            <transition name="slide-right">
                <div class="choseMaintainActivityPage" v-show="isShow">
                    <div class="searchBarContainer">
                        <div class="searchBar">
                            <input type="text" placeholder="请输入关键词" v-model="searchName">
                            <span class="searchIcon" @click="searchHandler">
                                <img src="${f_ctxpath}/resources/src/images/icons/search_small.png" alt="搜索">
                            </span>
                        </div>
                    </div>
                    <ul class="maintainActivity">
                        <li v-for="(activity,index) in activities" 
                            :key="activity.id"
                            :class="{'checked':activity.id === curId}"
                            @click="curId = activity.id">
                            {{activity.name}}
                        </li>
                    </ul>
                    <div class="btn-box">
                            <button class="comfirmBtn" @click="comfirmHandler">确认</button>
                            <button class="cancelBtn" @click="cancelHandler">取消</button>
                    </div> 
                </div> 
            </transition>              
        </script>

        <!-- 选择维修配件模板 -->
        <script type="text/x-template" id="chose_maintain_parts_template">
            <transition name="slide-right">
                <div class="choseMaintainPartPage" v-show="isShow">
                    <div class="searchBarContainer">
                        <div class="searchBar">
                            <input type="text" placeholder="请输入关键词" v-model="name">
                            <span class="searchIcon" @click="getParts(name)">
                                <img src="${f_ctxpath}/resources/src/images/icons/search_small.png" alt="搜索">
                            </span>
                        </div>
                    </div>
                    <table class="parts-table">
                        <thead>
                            <tr>
                                <td  @click="toggleCheckAllHandler"><i class="isCheckAll checkbox" :class="{'checked':isAllChecked}"></i>序号</td>
                                <td>配件名称</td>
                                <td>单价(元)</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(part,index) in parts" :key="part.id" @click="toggleCheckHandler(index)">
                                <td><i class="checkbox" :class="{'checked':part.isChecked}"></i></td>
                                <td>{{part.name}}</td>
                                <td>{{part.price}}</td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="btn-box">
                        <button class="comfirmBtn" @click="comfirmHandler">确认</button>
                        <button class="cancelBtn" @click="cancelHandler">取消</button>
                    </div>
                </div>
            </transition>
        </script>

        <script src="${f_ctxpath}/resources/src/js/flexible.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jquery/dist/jquery.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jqweui/jquery-weui.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/vue/vue.min.js"></script>
        <script src="${f_ctxpath}/resources/thirdparty/jweixin/jweixin-1.0.0.js"></script>
        <script src="${f_ctxpath}/resources/src/js/common.js${f_ver}"></script>
        <script src="${f_ctxpath}/resources/src/js/workOrderOperation/unfinishedOrderDetail_repair_detail.js${f_ver}"></script>
    </body>
</html>