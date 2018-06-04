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

var rootPath = getRootPath();
var userId = sessionStorage.userId;
var ordersCode =  getUrlParam("ordersCode");
var partId =  sessionStorage.partId;
console.log(partId)
var orderType = getUrlParam("orderType");
var pdtData = sessionStorage.pdtData,
	pdtData = JSON.parse(pdtData);
	console.log(sessionStorage.userId)

var ajaxData ={
	modelName:pdtData.modelName,//pdtData.modelName,'QR-R5-03'
	name:null
};
console.log(sessionStorage.partData )
if (sessionStorage.partData) {
	var partArr=JSON.parse(sessionStorage.partData);
}else{
	var partArr=[];
};


function partList(){
	if (orderType==1) {
		var urlPath = rootPath + '/scrmapp/qyhuser/orders/installPart/get'
	} else if(orderType==2) {
		var urlPath = rootPath + '/scrmapp/qyhuser/orders/repairPart/get'
	}
	console.log(ajaxData)
	setTimeout(function(){
	$.showLoading()
	    $.ajax({
	        url: urlPath,
	        type: 'GET',
	        dataType:'JSON',
	        data:ajaxData,
	        success:function(res){
	        	$.hideLoading()
	            var data = res;
	            if (!data.data) {
	            	$("body").html('<div class="errorIcon"></div><p class="errorTxt">抱歉，没有查到该产品对应的配件信息</p>')
	            	
	            } else {
	            	partListRender(data)
	            }
	            
	        },
	        error:function(){
	            console.log(9999)
	        }
	    })
	},500)
}


//搜索
function searchItem(){
	$('.allBtn').click(function(event) {
		ajaxData.name=null;
		partList()
	});
	$('.searBtn').click(function(event) {
		var con = $('.searInput').val();
		ajaxData.name=con;
		if (con == '请输入关键词') {
			ajaxData.name=null;
		}
		partList()
	});
	$('.searInput').blur(function(event) {
		var con = $('.searInput').val();
		ajaxData.name=con;
		if (con == '请输入关键词') {
			ajaxData.name=null;
		}
		partList()
	});
}

//渲染配件列表
function partListRender(data) {
    var htmlStr = template("partListTemplate", data);
    $(".main_layer").html(htmlStr);
    onCheckRender()
    toggleCheck()
    searchItem()
}

//复选框点击切换
function toggleCheck(){

    $('.checkBox').parents('tr').click(function(event) {
        $(this).find('.checkBox').toggleClass('action');
        var classString= $(this).find('.checkBox').attr('class');
        var num = classString.indexOf('action')
    	var a = $('.checkBox').length;
    	var b = $('.checkBox.action').length;

        if (num>-1) {
        	isOnCheck()
        }else {
        	partArr=[]
			isOnCheck()
        	$('.allCheck').removeClass('action')
        }
        if (a==b) {
        	$('.allCheck').addClass('action')
        }
    });

    //全选复选框
    $('.allCheck').parents('tr').click(function(event) {
        $(this).find('.allCheck').toggleClass('action');
        var classString= $('.allCheck').attr('class');
        var num = classString.indexOf('action')
        if (num>-1) {

            $('.checkBox').addClass('action')
            isOnCheck()
        }else{
        	sessionStorage.partId=''
        	partArr=[]
            $('.checkBox').removeClass('action')
        }
    });
}

//数组去重
function uniqueArray(array, key){
    var result = [array[0]];
    for(var i = 1; i < array.length; i++){
        var item = array[i];
        var repeat = false;
        for (var j = 0; j < result.length; j++) {
            if (item[key] == result[j][key]) {
                repeat = true;
                break;
            }
        }
        if (!repeat) {
            result.push(item);
        }
    }
    return result;
}

//遍历选中的项目
function isOnCheck(){
	$('.list').each(function(index, val) {
		var classString= $(this).find('.checkBox').attr('class');
		var num = classString.indexOf('action')
		if (num>-1) {
			var id = $(this).attr('id')
			var name = $(this).find('.partName').html()
			var price = $(this).find('.partPrice').html()
			var partJsonData ={id:id,partName:name,partPrice:price}
			partArr.push(partJsonData)
			
		}

	});	

}

//提交选中项目
function onCheckItem(){

	if (partArr.length>0) {
		sessionStorage.partData=JSON.stringify(uniqueArray(partArr, 'id'))
	}else{
		sessionStorage.partData=partArr
	}
	
	console.log(sessionStorage.partData)
	if (orderType==1) {
		window.location.href=rootPath + '/mobile/qiYeHao/submitOrder/jsp/submit_install.jsp?orderType='+orderType+'&ordersCode='+ordersCode+'&userId='+userId+'&pageFlag=1';
	} else if(orderType==2){
		window.location.href=rootPath + '/mobile/qiYeHao/submitOrder/jsp/submit_repair.jsp?orderType='+orderType+'&ordersCode='+ordersCode+'&userId='+userId+'&pageFlag=1';
	}
}


//渲染已勾选的复选框
function onCheckRender(){
	var a;
	var b=partId.split(',').length
	
	$('.list').each(function(index, val) {
		a = index + 1
		var currentId = $(this).attr('id')
		var num = partId.indexOf(currentId)
		if (num > -1) {
			$(this).children('td').children('.checkBox').addClass('action')
		}
	});

	if (a==b) {
		$('.allCheck').addClass('action')
	}
}

(function($,windows){
	partList()
	$('.confirmBtn').click(function(event) {
		onCheckItem()
	});
})(jQuery,window)
