/*!art-template - Template Engine | http://aui.github.com/artTemplate/*/ ! function() {
    function a(a) {
        return a.replace(t, "").replace(u, ",").replace(v, "").replace(w, "").replace(x, "").split(y)
    }

    function b(a) {
        return "'" + a.replace(/('|\\)/g, "\\$1").replace(/\r/g, "\\r").replace(/\n/g, "\\n") + "'"
    }

    function c(c, d) {
        function e(a) {
            return m += a.split(/\n/).length - 1, k && (a = a.replace(/\s+/g, " ").replace(/<!--[\w\W]*?-->/g, "")), a && (a = s[1] + b(a) + s[2] + "\n"), a
        }

        function f(b) {
            var c = m;
            if (j ? b = j(b, d) : g && (b = b.replace(/\n/g, function() {
                    return m++, "$line=" + m + ";"
                })), 0 === b.indexOf("=")) {
                var e = l && !/^=[=#]/.test(b);
                if (b = b.replace(/^=[=#]?|[\s;]*$/g, ""), e) {
                    var f = b.replace(/\s*\([^\)]+\)/, "");
                    n[f] || /^(include|print)$/.test(f) || (b = "$escape(" + b + ")")
                } else b = "$string(" + b + ")";
                b = s[1] + b + s[2]
            }
            return g && (b = "$line=" + c + ";" + b), r(a(b), function(a) {
                if (a && !p[a]) {
                    var b;
                    b = "print" === a ? u : "include" === a ? v : n[a] ? "$utils." + a : o[a] ? "$helpers." + a : "$data." + a, w += a + "=" + b + ",", p[a] = !0
                }
            }), b + "\n"
        }
        var g = d.debug,
            h = d.openTag,
            i = d.closeTag,
            j = d.parser,
            k = d.compress,
            l = d.escape,
            m = 1,
            p = {
                $data: 1,
                $filename: 1,
                $utils: 1,
                $helpers: 1,
                $out: 1,
                $line: 1
            },
            q = "".trim,
            s = q ? ["$out='';", "$out+=", ";", "$out"] : ["$out=[];", "$out.push(", ");", "$out.join('')"],
            t = q ? "$out+=text;return $out;" : "$out.push(text);",
            u = "function(){var text=''.concat.apply('',arguments);" + t + "}",
            v = "function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);" + t + "}",
            w = "'use strict';var $utils=this,$helpers=$utils.$helpers," + (g ? "$line=0," : ""),
            x = s[0],
            y = "return new String(" + s[3] + ");";
        r(c.split(h), function(a) {
            a = a.split(i);
            var b = a[0],
                c = a[1];
            1 === a.length ? x += e(b) : (x += f(b), c && (x += e(c)))
        });
        var z = w + x + y;
        g && (z = "try{" + z + "}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:" + b(c) + ".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");
        try {
            var A = new Function("$data", "$filename", z);
            return A.prototype = n, A
        } catch (B) {
            throw B.temp = "function anonymous($data,$filename) {" + z + "}", B
        }
    }
    var d = function(a, b) {
        return "string" == typeof b ? q(b, {
            filename: a
        }) : g(a, b)
    };
    d.version = "3.0.0", d.config = function(a, b) {
        e[a] = b
    };
    var e = d.defaults = {
            openTag: "<%",
            closeTag: "%>",
            escape: !0,
            cache: !0,
            compress: !1,
            parser: null
        },
        f = d.cache = {};
    d.render = function(a, b) {
        return q(a, b)
    };
    var g = d.renderFile = function(a, b) {
        var c = d.get(a) || p({
            filename: a,
            name: "Render Error",
            message: "Template not found"
        });
        return b ? c(b) : c
    };
    d.get = function(a) {
        var b;
        if (f[a]) b = f[a];
        else if ("object" == typeof document) {
            var c = document.getElementById(a);
            if (c) {
                var d = (c.value || c.innerHTML).replace(/^\s*|\s*$/g, "");
                b = q(d, {
                    filename: a
                })
            }
        }
        return b
    };
    var h = function(a, b) {
            return "string" != typeof a && (b = typeof a, "number" === b ? a += "" : a = "function" === b ? h(a.call(a)) : ""), a
        },
        i = {
            "<": "&#60;",
            ">": "&#62;",
            '"': "&#34;",
            "'": "&#39;",
            "&": "&#38;"
        },
        j = function(a) {
            return i[a]
        },
        k = function(a) {
            return h(a).replace(/&(?![\w#]+;)|[<>"']/g, j)
        },
        l = Array.isArray || function(a) {
            return "[object Array]" === {}.toString.call(a)
        },
        m = function(a, b) {
            var c, d;
            if (l(a))
                for (c = 0, d = a.length; d > c; c++) b.call(a, a[c], c, a);
            else
                for (c in a) b.call(a, a[c], c)
        },
        n = d.utils = {
            $helpers: {},
            $include: g,
            $string: h,
            $escape: k,
            $each: m
        };
    d.helper = function(a, b) {
        o[a] = b
    };
    var o = d.helpers = n.$helpers;
    d.onerror = function(a) {
        var b = "Template Error\n\n";
        for (var c in a) b += "<" + c + ">\n" + a[c] + "\n\n";
        "object" == typeof console && console.error(b)
    };
    var p = function(a) {
            return d.onerror(a),
                function() {
                    return "{Template Error}"
                }
        },
        q = d.compile = function(a, b) {
            function d(c) {
                try {
                    return new i(c, h) + ""
                } catch (d) {
                    return b.debug ? p(d)() : (b.debug = !0, q(a, b)(c))
                }
            }
            b = b || {};
            for (var g in e) void 0 === b[g] && (b[g] = e[g]);
            var h = b.filename;
            try {
                var i = c(a, b)
            } catch (j) {
                return j.filename = h || "anonymous", j.name = "Syntax Error", p(j)
            }
            return d.prototype = i.prototype, d.toString = function() {
                return i.toString()
            }, h && b.cache && (f[h] = d), d
        },
        r = n.$each,
        s = "break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",
        t = /\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,
        u = /[^\w$]+/g,
        v = new RegExp(["\\b" + s.replace(/,/g, "\\b|\\b") + "\\b"].join("|"), "g"),
        w = /^\d[^,]*|,\d[^,]*/g,
        x = /^,+|,+$/g,
        y = /^$|,+/;
    e.openTag = "{{", e.closeTag = "}}";
    var z = function(a, b) {
        var c = b.split(":"),
            d = c.shift(),
            e = c.join(":") || "";
        return e && (e = ", " + e), "$helpers." + d + "(" + a + e + ")"
    };
    e.parser = function(a) {
        a = a.replace(/^\s/, "");
        var b = a.split(" "),
            c = b.shift(),
            e = b.join(" ");
        switch (c) {
            case "if":
                a = "if(" + e + "){";
                break;
            case "else":
                b = "if" === b.shift() ? " if(" + b.join(" ") + ")" : "", a = "}else" + b + "{";
                break;
            case "/if":
                a = "}";
                break;
            case "each":
                var f = b[0] || "$data",
                    g = b[1] || "as",
                    h = b[2] || "$value",
                    i = b[3] || "$index",
                    j = h + "," + i;
                "as" !== g && (f = "[]"), a = "$each(" + f + ",function(" + j + "){";
                break;
            case "/each":
                a = "});";
                break;
            case "echo":
                a = "print(" + e + ");";
                break;
            case "print":
            case "include":
                a = c + "(" + b.join(",") + ");";
                break;
            default:
                if (/^\s*\|\s*[\w\$]/.test(e)) {
                    var k = !0;
                    0 === a.indexOf("#") && (a = a.substr(1), k = !1);
                    for (var l = 0, m = a.split("|"), n = m.length, o = m[l++]; n > l; l++) o = z(o, m[l]);
                    a = (k ? "=" : "=#") + o
                } else a = d.helpers[c] ? "=#" + c + "(" + b.join(",") + ");" : "=" + a
        }
        return a
    }, "function" == typeof define ? define(function() {
        return d
    }) : "undefined" != typeof exports ? module.exports = d : this.template = d
}();

var flags = {
    submitFlag: true
};
var rootPath = getRootPath();
var userId = getUrlParam("userId");
var ordersCode = getUrlParam("ordersCode");
var orderType = getUrlParam("orderType");
var barCode = getUrlParam("barCode");
var pageFlag = getUrlParam("pageFlag");

if (!pageFlag) {
    sessionStorage.partData='';
    sessionStorage.userUploadImg ='';
    sessionStorage.codeInputVal ='';
    sessionStorage.measureData='';
}

console.log(pageFlag)
var measureData = sessionStorage.measureData;
var partData = sessionStorage.partData ;
console.log(partData)
sessionStorage.userId =userId;
sessionStorage.partId ='';


var glData = {
    canScan: false
};

var pdtData = {
    typeName:null,
    modelName:null,
    name:null,
}
var checkPdtData = {
    checkPdtCode:null,
    checkPdtImg:null
}
var orderData = {
    ordersCode:null,
    orderType:null,
}
var filterData = {
    id:null,
    filterName:null,
    price:null
}
var maintainData = {
    id:null,
    maintainName:null,
    price:null
}

function unFinishedAjaxQuery() {
    var data = {
        ordersCode: ordersCode, //'WX17042646733929',
    }
    $.ajax({
        type: "GET",
        url: rootPath + '/scrmapp/qyhuser/orders/finish/detail',
        data: data,
        success: function(data) {
            // var data = JSON.parse(data);
            $.hideLoading();
            if (data.returnCode === 1) {
                unFinishedRenderFunc(data.data)
                productId=data.data.productId
                var faultImage = data.data.faultImage,
                    productBarCode = data.data.productBarCode;
                if (productBarCode == '' || productBarCode == null) {
                    $('.main_layer').css('paddingTop', 0);
                    // $('.topTips').css('display', 'none');
                    $('.writeQrcode').css('display', 'block');
                    $('.scanArea').css('display', 'block');
                }
                if (faultImage) {
                    faultImage = faultImage.split(',');
                    
                    console.log(faultImage)
                    for (var i = 0; i < faultImage.length; i++) {
                        $('.imgBox .img img').eq(i).attr('src', faultImage[i]);
                        $('.imgBox .img').eq(i).css('display', 'inline-block');
                    }
                    $('.order_info .imgBox').css('display', 'block');
                    
                }

                if (barCode) {
                    $("#pdtCodeInput").val(barCode);
                    $('#pdtCodeInput').attr('readonly', 'readonly');
                }else{
                    $("#pdtCodeInput").val(sessionStorage.codeInputVal);
                }
                if (sessionStorage.userUploadImg) {
                    $('.picShow img').attr('src',sessionStorage.userUploadImg)
                    $('.noPicBox').css('display', 'none');
                    $('.picShowBox').css('display', 'block');
                }
                writeQrcode()
            } else {
                $.toast(data.returnMsg, "cancel")
            }
            previewImage()

        },
        error: function(data) {
            $.toast("网络错误", "cancel")
        }
    })
}

function unFinishedRenderFunc(data) {
    // console.log(data)
    var htmlStr = template("orderSubmitTemplate", data);
    $(".main_layer").html(htmlStr)
    toggleCheck()
    measureDataRender()
    partDataRender()
    pdtData = {
        typeName:$("#typeName").val(),
        modelName:$("#modelName").val(),
        name:null
    }
    sessionStorage.pdtData = JSON.stringify(pdtData)
    console.log(sessionStorage.pdtData)

}

function previewImage() {
    $('.imgBox img').on('click', function(event) {
        var imgArray = [];
        var curImageSrc = $(this).attr('src');
        var oParent = $(this).parent();
        if (curImageSrc && !oParent.attr('href')) {
            $('.imgBox img').each(function(index, el) {
                var itemSrc = $(this).attr('src');
                imgArray.push(itemSrc);
            });
            wx.previewImage({
                current: curImageSrc,
                urls: imgArray
            });
        }
    });
}

function checkBox() {
    $('.weui-check').on('click', function(event) {
        if ($("input[name='isChange']").is(":checked") == true) {
            $('.changeFilter').css('display', 'block');
            $("input[name='isChange']").attr('value', '1');
        } else {
            $('.changeFilter').css('display', 'none');
            $("input[name='isChange']").attr('value', '2');
        }
    });
}

function upLoadImg(){
    wxInit.init();
    wx.error(function(res) {
        $.toast(("error:" + JSON.stringify(res)), "cancel")
    })
    var imgArr;
    var serverId;
    wx.chooseImage({
        count: 1, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            var localIds = res.localIds;
            syncUpload(localIds);
            // $('.noPicBox').css('display', 'none');
            // $('.picShowBox').css('display', 'block');
            
        }
    });

    var syncUpload = function(localIds){
        var localId = localIds.pop();
        wx.uploadImage({
            localId: localId,
            isShowProgressTips: 1,
            success: function (res) {
                serverId = res.serverId; // 返回图片的服务器端ID
                setTimeout(function(){
                    $.ajax({
                        url: rootUrl+'/weixin/madia/downLoadWechatMedia?media_id='+serverId+'',
                        type: 'GET',
                        dataType: 'JSON',
                        success: function(res){
                            if (res.returnCode==1) {
                                imgArr=res.data;   
                                $('.noPicBox').css('display', 'none');
                                $('.picShowBox').css('display', 'block');
                                // $('.picShow').html('<img src="'+imgArr+'" alt="">')
                                $('.picShow img').attr('src', imgArr);

                            }else{
                                alert(res.returnMsg)
                            }
                            
                        },
                    })
                },300)
                if(localIds.length > 0){
                    syncUpload(localIds);
                }
            }
        });
    };
}

function submitOrder() {

    $('.btn').on('click', function(event) {
        var subData = {
            userId: sessionStorage.userId, //'NzIxMzI='
            ordersCode: '',//订单编号
            is_change_filter: '',//是否更换滤芯
            productBarCode: '',//产品条码
            barCodeImage:'',//产品图片
            repairItemStr:'',//维修措施
            repairParts:'',//维修配件
            installParts:'',//安装配件
            filters:'',//更换滤芯
            maintainPrices:'',//保养项目
            is_replace:''//是否更换配件
        }
        if (!flags.submitFlag) {
            return;
        }
        flags.submitFlag = false;

        var imageSrc =$('.picShow img').attr('src');
        var pdtCodeInput = $('#pdtCodeInput').val();
        if (!pdtCodeInput) {
            $.alert('产品条码不能为空哦！')
            flags.submitFlag = true;
            return false
        }

        var maintainData = [];
        var filterData = [];
        subData.ordersCode = $('.orderNum .con').html();
        if (orderType == 3) {
            //保养项目
            $('.pdtMaintain .tableBody tr').each(function(index, val) {
                if ($(this).attr('class')=='action') {
                    maintainData.push({
                        id:$(this).find('.checkBox').attr('id'),
                        maintainName:$(this).find('.itemName').html(),
                        price:$(this).find('.itemPrice').val()
                    })
                }
            });

            //滤芯项目
            $('.changeFilter .tableBody tr').each(function(index, val) {
                if ($(this).attr('class')=='action') {
                    filterData.push({
                        id:$(this).find('.checkBox').attr('id'),
                        filterName:$(this).find('.itemName').html(),
                        price:$(this).find('.itemPrice').val()
                    })
                }

            });
            if (maintainData.length>0) {
                subData.maintainPrices=JSON.stringify(maintainData);
            }else{
                subData.maintainPrices='';
            }
            if (filterData.length>0) {
                subData.filters=JSON.stringify(filterData);
            }else{
                subData.filters='';
            };


            var classStr = $('.isChangeFilter .checkBox').attr('class')
            console.log(classStr.indexOf('action'))
            if (classStr.indexOf('action')==-1) {
                subData.is_change_filter=0;
                subData.filters=''
            }else{
                subData.is_change_filter=2;
            }


            
        }else if(orderType == 2){
            var classStr = $('.isPart .checkBox').attr('class')
            if (classStr.indexOf('action')==-1) {
                subData.is_replace=0;
                subData.filters=''
            }else{
                subData.is_replace=2;
            }
            if (measureData) {
                subData.repairItemStr=JSON.stringify(measureData);
            }else{
                subData.repairItemStr=''
            };
            if (partData) {
                // subData.repairParts=JSON.stringify(partData);
                var partList = []
                $('.partBox .tableBody .list').each(function(index, val) {
                     partList.push({
                        id:$(this).attr('id'),
                        name:$(this).find('.partName').html(),
                        price:$(this).find('.partPrice').val(),
                        number:$(this).find('.partNum').val()
                     })
                });
                subData.repairParts=JSON.stringify(partList);
            }else{
                subData.repairParts=''
            };
        }else if(orderType == 1){
            if (partData) {
                // subData.repairParts=JSON.stringify(partData);
                var partList = []
                $('.partBox .tableBody .list').each(function(index, val) {
                     partList.push({
                        id:$(this).attr('id'),
                        name:$(this).find('.partName').html(),
                        price:$(this).find('.partPrice').val(),
                        number:$(this).find('.partNum').val()
                     })
                });
                subData.installParts=JSON.stringify(partList);
            }else{subData.installParts=''
            };
            // subData.installParts=JSON.stringify(partData);
        }
        subData.productBarCode = pdtCodeInput;
        subData.barCodeImage=imageSrc;
        if (subData.is_change_filter==2&&!subData.filters) {
            $.alert('如已经勾选【是否换芯】，请勾选至少一种产品换芯项')
            flags.submitFlag = true;
            return false
        }
        // console.log(subData)    
        // flags.submitFlag = true;
        $.showLoading()
        $.ajax({
            url: rootPath + '/scrmapp/qyhuser/orders/finish/order',
            type: 'POST',
            dataType: 'JSON',
            data: subData,
            success: function(res) {
                
                if (res.returnCode == 1) {
                    $.hideLoading()
                    $.toast(res.returnMsg);
                    window.location.href = rootPath + '/mobile/qiYeHao/workOrderOperation/jsp/completeServiceTip.jsp?userId='+userId;
                    setTimeout(function() {
                        flags.submitFlag = true;
                    }, 300)
                } else {
                    $.hideLoading()
                    $.toast(res.returnMsg || "提交失败,请重试！", "cancel")
                    setTimeout(function() {
                        flags.submitFlag = true;
                    }, 300)


                }
                
            },
            error:function(res){
                flags.submitFlag = true;
            }
        })
    });
}

function writeQrcode() {
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
                    var productId = $('#productId').val();
                    var orderType = getUrlParam("orderType");
                    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                    var reg = /barcode=(\d+)/;
                    var r = reg.exec(result)[1];
                    var barCode = r;
                    //$("#pdtCodeInput").val(barCode);
                    window.location.href=result+"&from=qyh"+"&productId="+productId+"&userId="+userId+"&orderType="+orderType+"&ordersCode="+ordersCode;
                    
                }
            });
        } else {
            $.toptip("暂时无法使用扫一扫，请稍后再试", "warning")
        }

    })

}

//
//复选框点击切换
function toggleCheck(){

    $('.checkBox').parents('tr').click(function(event) {
        $(this).toggleClass('action')
        $(this).find('.checkBox').toggleClass('action');
        var classString= $(this).find('.checkBox').attr('class');
        var num = classString.indexOf('action')
        if (num=-1) {
            $('.allCheck').removeClass('action')
        }

    });
    // $('.changeFilter input').click(function(event) {
    //     event.stopPropagation();
    // });

    $('.allCheck').parents('tr').click(function(event) {
        $(this).find('.allCheck').toggleClass('action');
        var classString= $('.allCheck').attr('class');
        var num = classString.indexOf('action')
        if (num>-1) {
            $('.checkBox').addClass('action')
        }else{
            $('.checkBox').removeClass('action')
        }
    });

    //是否选择配件
    $('.checkBox').parents('.isPart').click(function(event) {
        
        $(this).find('.checkBox').toggleClass('action');
        var classString= $(this).find('.checkBox').attr('class');
        var num = classString.indexOf('action')
        checkPdtData = {
            checkPdtCode:$('#pdtCodeInput').val(),
            checkPdtImg:$('.picShow img').attr('src')
        }
        sessionStorage.userUploadImg = checkPdtData.checkPdtImg
        sessionStorage.codeInputVal = checkPdtData.checkPdtCode
        if (num>-1) {
            window.location.href=rootPath + '/mobile/qiYeHao/submitOrder/jsp/partList.jsp?orderType='+orderType+'&ordersCode='+ordersCode;
            
        }else{
            sessionStorage.partData='';
            sessionStorage.partId='';
            $('.partBox').remove();
        }
    });

    // 是否选择维修措施
    $('.checkBox').parents('.isMeasure').click(function(event) {
        
        $(this).find('.checkBox').toggleClass('action');
        var classString= $(this).find('.checkBox').attr('class');
        var num = classString.indexOf('action')
        checkPdtData = {
            checkPdtCode:$('#pdtCodeInput').val(),
            checkPdtImg:$('.picShow img').attr('src')
        }
        sessionStorage.userUploadImg = checkPdtData.checkPdtImg
        sessionStorage.codeInputVal = checkPdtData.checkPdtCode
        if (num>-1) {
            setTimeout(function(){
                window.location.href=rootPath + '/mobile/qiYeHao/submitOrder/jsp/measureList.jsp?orderType='+orderType+'&ordersCode='+ordersCode;
            },300)
            
        }else{
            sessionStorage.measureData='';
            $('.measureBox').remove();
        }
    });

    // 是否选择换芯
    $('.checkBox').parents('.isChangeFilter').click(function(event) {
        $(this).find('.checkBox').toggleClass('action');
        var classString = $(this).find('.checkBox').attr('class');
        var num = classString.indexOf('action')

        if (num > -1) {
            $('.changeFilter').css('display', 'block');
            $('body').scrollTop($('.main_layer ')[0].offsetHeight )

        } else {
            $('.changeFilter').css('display', 'none');
        }
    });

}

//选择后配件数据返回渲染页面；
function partDataRender(){
    if (!partData) {
        return
    }
    partData = JSON.parse(partData)
    console.log(partData)
    if (partData.length>0) {
        var data  = partData;
        for (var i = 0; i < data.length; i++) {
            var a =i+1
            var htmlStr =   '<tr class="list" id="'+data[i].id+'">'+
                                '<td class="partId">'+a+'</td>'+
                                '<td class="partName">'+data[i].partName+'</td>'+
                                '<td ><input class="partPrice" type="number" value="'+data[i].partPrice+'"></td>'+
                                '<td ><input class="partNum" type="number" value="0"></td>'+
                            '</tr>';
            $(".tableBody").append(htmlStr);
        }
        $('.partBox').css('display', 'block');
        $('.isPart .checkBox').addClass('action')

        $('.partBox').click(function(event) {
            var partIdArr = [];
            $('.list').each( function(index, val) {
                var partId = $(this).attr('id')
                partIdArr.push(partId)
                
            });

            checkPdtData = {
                checkPdtCode:$('#pdtCodeInput').val(),
                checkPdtImg:$('.picShow img').attr('src')
            }
            sessionStorage.userUploadImg = checkPdtData.checkPdtImg
            sessionStorage.codeInputVal = checkPdtData.checkPdtCode

            sessionStorage.partId = partIdArr
            window.location.href=rootPath + '/mobile/qiYeHao/submitOrder/jsp/partList.jsp?orderType='+orderType+'&ordersCode='+ordersCode;
        });

        $('.partBox input').click(function(event) {
            event.stopPropagation();
        });
    }
}

//选择维修措施后返回渲染数据
function measureDataRender(){
    if (!measureData) {
        return
    }
    if (measureData) {
        measureData=JSON.parse(measureData)
        var htmlStr = '<span class="measureItem" id="'+measureData.id+'" itemcode="'+measureData.itemCode+'">'+measureData.name+'</span><span>></span>';
        $(".measureBox").append(htmlStr);
        $(".measureBox").css('display', 'block');
        $('.isMeasure .checkBox').addClass('action')

        $('.measureBox').click(function(event) {
            checkPdtData = {
                checkPdtCode:$('#pdtCodeInput').val(),
                checkPdtImg:$('.picShow img').attr('src')
            }
            sessionStorage.userUploadImg = checkPdtData.checkPdtImg
            sessionStorage.codeInputVal = checkPdtData.checkPdtCode
            window.location.href=rootPath + '/mobile/qiYeHao/submitOrder/jsp/measureList.jsp?orderType='+orderType+'&ordersCode='+ordersCode;
        });
    }
}


(function($, windows) {
    unFinishedAjaxQuery();
    toggleCheck()
    
    
    submitOrder()
})(jQuery, window)