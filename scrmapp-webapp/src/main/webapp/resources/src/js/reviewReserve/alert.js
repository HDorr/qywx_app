+function ($) {
    "use strict";

    $.fn.transitionEnd = function (callback) {
        var events = ['webkitTransitionEnd', 'transitionend', 'oTransitionEnd', 'MSTransitionEnd', 'msTransitionEnd'],
            i, dom = this;

        function fireCallBack(e) {
            /*jshint validthis:true */
            if (e.target !== this) return;
            callback.call(this, e);
            for (i = 0; i < events.length; i++) {
                dom.off(events[i], fireCallBack);
            }
        }

        if (callback) {
            for (i = 0; i < events.length; i++) {
                dom.on(events[i], fireCallBack);
            }
        }
        return this;
    };

    var defaults;

    $.modal = function (params, onOpen) {
        params = $.extend({}, defaults, params);


        var buttons = params.buttons;

        var buttonsHtml = buttons.map(function (d, i) {
            return '<a href="javascript:;" class="weui-dialog__btn ' + (d.className || "") + '">' + d.text + '</a>';
        }).join("");

        var tpl =
            " <div class=\"weui-dialog weui-dialog--visible t-alert-model\">\n" +
            "<div class='t-alert-container'>" +
            "  <div class='weui-dialog__btn t-alert-img'>\n" +
            "   <img src='/scrmapp/resources/src/images/appraise/close.png'>\n" +
            "  </div>\n" +
            "   <div class=\"weui-dialog__hd\">\n" +
            "    <div class=\"weui-dialog__title t-alert-title\">" + params.title + "</div>\n" +
            "   </div>\n" +
            "   <div class=\"t-alert-btn\">\n" +
            "    <span href=\"javascript:;\" class=\"weui-dialog__btn primary\">\n" +
            "      马上去评价\n" +
            "     </span>\n" +
            "   </div>\n" +
            "  </div>" +
            "</div>";

        var dialog = $.openModal(tpl, onOpen);

        dialog.find(".weui-dialog__btn").each(function (i, e) {
            var el = $(e);
            el.click(function () {
                //先关闭对话框，再调用回调函数
                if (params.autoClose) $.closeModal();
            });
        });

        return dialog;
    };

    $.openModal = function (tpl, onOpen) {
        var mask = $("<div class='weui-mask'></div>").appendTo(document.body);
        mask.show();

        var dialog = $(tpl).appendTo(document.body);

        if (onOpen) {
            dialog.transitionEnd(function () {
                onOpen.call(dialog);
            });
        }

        dialog.show();
        mask.addClass("weui-mask--visible");
        dialog.addClass("weui-dialog--visible");


        return dialog;
    }

    $.closeModal = function () {
        $(".weui-mask--visible").removeClass("weui-mask--visible").transitionEnd(function () {
            $(this).remove();
        });
        $(".weui-dialog--visible").removeClass("weui-dialog--visible").transitionEnd(function () {
            $(this).remove();
        });
    };

    $.alert = function (text, title, onOK) {
        var config;
        if (typeof text === 'object') {
            config = text;
        } else {
            if (typeof title === 'function') {
                onOK = arguments[1];
                title = undefined;
            }

            config = {
                text: text,
                title: title,
                onOK: onOK
            }
        }
        return $.modal({
            text: text,
            title: config.title,
            buttons: [{
                text: defaults.buttonOK,
                className: "primary",
                onClick: config.onOK
            }]
        });
    }

    $.alertNew = function (text) {
        return $.modal({
            title: text,
            buttons: [{
                text: "马上去评价"
            }]
        });
    }


    $.confirm = function (text, title, onOK, onCancel) {
        var config;
        if (typeof text === 'object') {
            config = text
        } else {
            if (typeof title === 'function') {
                onCancel = arguments[2];
                onOK = arguments[1];
                title = undefined;
            }

            config = {
                text: text,
                title: title,
                onOK: onOK,
                onCancel: onCancel
            }
        }
        return $.modal({
            text: config.text,
            title: config.title,
            buttons: [{
                text: defaults.buttonCancel,
                className: "default",
                onClick: config.onCancel
            }, {
                text: defaults.buttonOK,
                className: "primary",
                onClick: config.onOK
            }]
        });
    };

    //如果参数过多，建议通过 config 对象进行配置，而不是传入多个参数。
    $.prompt = function (text, title, onOK, onCancel, input) {
        var config;
        if (typeof text === 'object') {
            config = text;
        } else {
            if (typeof title === 'function') {
                input = arguments[3];
                onCancel = arguments[2];
                onOK = arguments[1];
                title = undefined;
            }
            config = {
                text: text,
                title: title,
                input: input,
                onOK: onOK,
                onCancel: onCancel,
                empty: false //allow empty
            }
        }

        var modal = $.modal({
            text: '<p class="weui-prompt-text">' + (config.text || '') + '</p><input type="text" class="weui-input weui-prompt-input" id="weui-prompt-input" value="' + (config.input || '') + '" />',
            title: config.title,
            autoClose: false,
            buttons: [{
                text: defaults.buttonCancel,
                className: "default",
                onClick: function () {
                    $.closeModal();
                    config.onCancel && config.onCancel.call(modal);
                }
            }, {
                text: defaults.buttonOK,
                className: "primary",
                onClick: function () {
                    var input = $("#weui-prompt-input").val();
                    if (!config.empty && (input === "" || input === null)) {
                        modal.find('.weui-prompt-input').focus()[0].select();
                        return false;
                    }
                    $.closeModal();
                    config.onOK && config.onOK.call(modal, input);
                }
            }]
        }, function () {
            this.find('.weui-prompt-input').focus()[0].select();
        });

        return modal;
    };

    //如果参数过多，建议通过 config 对象进行配置，而不是传入多个参数。
    $.login = function (text, title, onOK, onCancel, username, password) {
        var config;
        if (typeof text === 'object') {
            config = text;
        } else {
            if (typeof title === 'function') {
                password = arguments[4];
                username = arguments[3];
                onCancel = arguments[2];
                onOK = arguments[1];
                title = undefined;
            }
            config = {
                text: text,
                title: title,
                username: username,
                password: password,
                onOK: onOK,
                onCancel: onCancel
            }
        }

        var modal = $.modal({
            text: '<p class="weui-prompt-text">' + (config.text || '') + '</p>' +
            '<input type="text" class="weui-input weui-prompt-input" id="weui-prompt-username" value="' + (config.username || '') + '" placeholder="输入用户名" />' +
            '<input type="password" class="weui-input weui-prompt-input" id="weui-prompt-password" value="' + (config.password || '') + '" placeholder="输入密码" />',
            title: config.title,
            autoClose: false,
            buttons: [{
                text: defaults.buttonCancel,
                className: "default",
                onClick: function () {
                    $.closeModal();
                    config.onCancel && config.onCancel.call(modal);
                }
            }, {
                text: defaults.buttonOK,
                className: "primary",
                onClick: function () {
                    var username = $("#weui-prompt-username").val();
                    var password = $("#weui-prompt-password").val();
                    if (!config.empty && (username === "" || username === null)) {
                        modal.find('#weui-prompt-username').focus()[0].select();
                        return false;
                    }
                    if (!config.empty && (password === "" || password === null)) {
                        modal.find('#weui-prompt-password').focus()[0].select();
                        return false;
                    }
                    $.closeModal();
                    config.onOK && config.onOK.call(modal, username, password);
                }
            }]
        }, function () {
            this.find('#weui-prompt-username').focus()[0].select();
        });

        return modal;
    };

    defaults = $.modal.prototype.defaults = {
        title: "提示",
        text: undefined,
        buttonOK: "确定",
        buttonCancel: "取消",
        buttons: [{
            text: "确定",
            className: "primary"
        }],
        autoClose: true //点击按钮自动关闭对话框，如果你不希望点击按钮就关闭对话框，可以把这个设置为false
    };

}($);

+ function($) {
    "use strict";

    var defaults;

    var show = function(html, className) {
        className = className || "";
        var mask = $("<div class='weui-mask_transparent'></div>").appendTo(document.body);

        var tpl = '<div class="weui-toast ' + className + '">' + html + '</div>';
        var dialog = $(tpl).appendTo(document.body);

        dialog.show();
        dialog.addClass("weui-toast--visible");
    };

    var hide = function(callback) {
        $(".weui-mask_transparent").remove();
        $(".weui-toast--visible").removeClass("weui-toast--visible").transitionEnd(function() {
            var $this = $(this);
            $this.remove();
            callback && callback($this);
        });
    }

    $.toast = function(text, style, callback) {
        if (typeof style === "function") {
            callback = style;
        }
        var className, iconClassName = 'weui-icon-success-no-circle';
        var duration = toastDefaults.duration;
        if (style == "cancel") {
            className = "weui-toast_cancel";
            iconClassName = 'weui-icon-cancel'
        } else if (style == "forbidden") {
            className = "weui-toast--forbidden";
            iconClassName = 'weui-icon-warn'
        } else if (style == "text") {
            className = "weui-toast--text";
        } else if (typeof style === typeof 1) {
            duration = style
        }
        show('<i class="' + iconClassName + ' weui-icon_toast"></i><p class="weui-toast_content">' + (text || "已经完成") + '</p>', className);

        setTimeout(function() {
            hide(callback);
        }, duration);
    }

    $.showLoading = function(text) {
        var html = '<div class="weui_loading">';
        html += '<i class="weui-loading weui-icon_toast"></i>';
        html += '</div>';
        html += '<p class="weui-toast_content">' + (text || "数据加载中") + '</p>';
        show(html, 'weui_loading_toast');
    }

    $.hideLoading = function(callback) {
        hide(function() {
            setTimeout(function() {
                callback()
            }, 300)
        });
    }

    var toastDefaults = $.toast.prototype.defaults = {
        duration: 2500
    }

}($);