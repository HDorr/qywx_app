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


var oDate = new Date();

var glData = {
    userId: $("#userIdInput").val() || getUrlParam("userId"),
    ordersCode: getUrlParam("ordersCode") || $("#ordersCodeInput").val(),
    isChange: getUrlParam("isChange") || 0,
    dateRes: getShowDate(),
    canNav: false
};

function ufDetailAjaxQuery(callback) {
    var data = {
        ordersCode: glData.ordersCode
    }
    setTimeout(function() {
        $.showLoading();
        $.ajax({
            type: "GET",
            url: queryUrls.qyhQueryUFinedOrderDetail,
            data: data,
            success: function(data) {
                // var data = JSON.parse(data);
                setTimeout(function() {
                    $.hideLoading();
                    if (data.returnCode === 1) {
                        if (callback) {
                            data.data.description = data.data.description || "无";
                            data.data.productBarCode = data.data.productBarCode || "无";
                            callback(data.data)
                        }
                    } else {
                        $.toast(data.returnMsg, "cancel")
                    }
                }, 400)
                
            },
            error: function(data) {
                $.toast("网络错误", "cancel")
            }
        })
    }, 400)
}

function ufRender(data) {
    if (data.orderTime) {
        data.splitData = data.orderTime.split(" ");
    } else {
        data.splitData = "none"
    }
    data.wechatOrdersRecordList = data.wechatOrdersRecordList.reverse();
    //处理中的状态 data.status:1:处理中  2:已取消  3:重新处理中  4:已接单 5:服务完成 6:评价完成
    if (data.status == 1 || data.status == 3 || data.status == 4) {
        data.isShowBtn = true;
    } else {
        data.isShowBtn = false;
    }
    var html = template("ufDetailTemplate", data);
    $(".main_layer").html(html);
    changeOrderAction();
    if (glData.isChange == 1) {
        $("#changeOrder").picker("open");
    }
    if (data.faultImage&&data.faultImage.length>10) {
        faultImage = data.faultImage.split(',');
        for (var i = 0; i < faultImage.length; i++) {
            $('.imgBox .img img').eq(i).attr('src', faultImage[i]);
            $('.imgBox .img').eq(i).css('display', 'inline-block');
        }
        $('.orderInfo .imgBox').css('display', 'block');
    }
    previewImage()
}

//图片预览
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

//拒绝工单
function refuseOrder(el) {
    var that = $(el);
    confirmModal.init({
        text: "确认拒绝工单吗？",
        tip: "请先联系您所在的服务网点<br/>服务网点电话 : 021-61522809",
        textArea: true,
        callback: function(fn, reason) {
            //判断textarea里的值是否为空
            var text = reason.trim();
            if (!text) {
                $.toptip("必须输入原因", 2000, 'error');
                return;
            }
            fn();
            $.showLoading("取消中")
            var data = {
                userId: glData.userId,
                ordersCode: that.data("orderscode") || glData.ordersCode,
                ordersId: that.data("ordersid"),
                ordersType: that.data("ordertype"),
                contacts: that.data("contacts"),
                reason: text
            }
            $.ajax({
                type: "POST",
                url: queryUrls.qyhQueryRefuseOrder,
                data: data,
                success: function(data) {
                    $.hideLoading();
                    if (data.returnCode == 1) {
                        // $.toptip(data.returnMsg, 2000, 'success');
                        location.href = pageUrls.unfinishedWorkOrderList + "?userId="+glData.userId + "&condition=today"
                    } else {
                        $.toptip(data.returnMsg, 2000, 'error');
                    }
                },
                error: function(data) {
                    $.hideLoading();
                    $.toptip("网络错误", 2000, 'error');
                }
            })
        }
    })
}

//完成工单
function completeOrder(el) {
    location.href = rootPath + "/scrmapp/qyhuser/orders/finish/detail/page?orderType=" + $(el).data("ordertype") + "&userId=" + glData.userId + "&ordersCode=" + glData.ordersCode
}



function getShowDate() {
    var dataArr = getFutureDate(7);
    var values = [];
    var displayValues = [];
    var weekday = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]
    $.each(dataArr, function(i, v) {
        if (i != 0) {
            var month = v.getMonth() + 1;
            var day = v.getDate();

            if (oDate.getHours() >= 18) {
                day = v.getDate() + 1
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
function changeOrderAction() {
    var currentHour = oDate.getHours() //oDate.getHours();
    var currentDate = oDate.getDate();
    var timeArr = [];
    var timeArr1 = ['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00'];
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
    window.picker_prevValue = glData.dateRes.values[0]
    $("#changeOrder").picker({
        cols: [{
            textAlign: 'center',
            values: glData.dateRes.values,
            displayValues: glData.dateRes.displayValues
        }, {
            textAlign: 'center',
            // values: ['10:00', '12:00', '14:00', '16:00'],
            values: timeArr //['10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00']
        }],
        onClose: function() {
            glData.isChange = 0;
            var that = $("#changeOrder");
            var updateTime = that.val();
            confirmModal.init({
                text: "确认更改预约为<br/>" + updateTime + "?",
                tip: "友情提示：<br/>更改前请跟客户协商达成一致！",
                textArea: true,
                callback: function(fn, reason) {
                    //判断textarea里的值是否为空
                    var text = reason.trim();
                    if (!text) {
                        $.toptip("必须输入原因", 2000, 'error');
                        return;
                    }
                    fn();
                    $.showLoading("更改中")
                    var data = {
                        userId: glData.userId,
                        ordersCode: that.data("orderscode") || glData.ordersCode,
                        ordersId: that.data("ordersid"),
                        ordersType: that.data("ordertype"),
                        contacts: that.data("contacts"),
                        oldTime: that.data("oldtime"),
                        updateTime: updateTime,
                        reason: text
                    }
                    $.ajax({
                        type: "POST",
                        url: queryUrls.qyhQueryChangeOrder,
                        data: data,
                        success: function(data) {
                            $.hideLoading();
                            if (data.returnCode == 1) {
                                $.toptip(data.returnMsg, 2000, 'success');
                                ufDetailAjaxQuery(ufRender);
                            } else {
                                $.toptip(data.returnMsg, 2000, 'error');
                            }
                        },
                        error: function(data) {
                            $.hideLoading();
                            $.toptip("网络错误", 2000, 'error');
                        }
                    })
                }
            })
        },
        onChange: function(e, value, displayValue) {
            if (e.value[0] == picker_prevValue) {
                return false;
            } else {
                picker_prevValue = e.value[0]
                if (e.cols[0].displayValue == glData.dateRes.displayValues[0]) {
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

//调用微信地图去导航
function gotoNav(el) {
    if (glData.canNav) {
        $.showLoading();
        var address = $(el).data("useraddress");
        baiduMapUtils.getCoodByAddress(address, "", function(data) {
            if (data.status == 0) {
                $.hideLoading();
                var lat = data.result.location.lat;
                var lng = data.result.location.lng;
                wx.openLocation({
                    latitude: lat, // 纬度，浮点数，范围为90 ~ -90
                    longitude: lng, // 经度，浮点数，范围为180 ~ -180。
                    name: address, // 位置名
                    address: '', // 地址详情说明
                    scale: 13, // 地图缩放级别,整形值,范围从1~28。默认为最大
                    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                });
            } else {
                $.hideLoading();
                $.toptip('获取地址失败', 'error');
            }
        })
    } else {
        $.alert("尚不能启动导航，请稍后再试")
    }
}


(function($, window) {

    ufDetailAjaxQuery(ufRender)

    wxInit.init();


    wx.ready(function() {
        wx.checkJsApi({
            jsApiList: ['openLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function(res) {
                if (res.checkResult.openLocation) {
                    glData.canNav = true;
                } else {
                    $.toptip("当前手机不支持导航功能", "warning")
                }
            }
        });
    })
    wx.error(function(res) {
        $.toptip("微信地图功能调用失败", "warning")
    })
})(jQuery, window)