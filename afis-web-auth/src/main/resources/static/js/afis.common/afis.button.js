(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else if (typeof module === 'object' && typeof module.exports === 'object') {
        module.exports = factory();
    } else {
        var AFIS = window.afis;
        if (AFIS) {
            AFIS.button = factory();
        }
    }
}(function () {
    'use strict';
    var Button = function (element, options) {
        this.cont = element;
        this.btns = [];
        this.options = $.extend(true, {}, this.default, options);
        this.cont.addClass(this.options.cls);
    };
    Button.prototype.default = {
        template: '<button type="button" class="btn btn-sm"></button>',
        defcls: "btn-primary",
        cls: "afis-btn"
    };
    Button.prototype.add = function (container, showtext, func, style) {
        if (container == null || showtext == null) {
            return null;
        }
        var that = this;
        var btn = $(that.options.template).appendTo(container).text(showtext);
        btn.addClass(that.options.cls);
        if (typeof style == 'string' && style) {
            btn.addClass(style);
        } else {
            btn.addClass(that.options.defcls);
        }
        if (func != null) {
            btn.click(func);
        }
        return btn;
    };
    Button.prototype.addBtn = function (showText, func, style) {
        var btn = {
            showText: showText,
            style: style,
            func: func
        }
        this.btns.push(btn);
        return this;
    };
    Button.prototype.builder = function (func) {
        var that = this;
        if (that.btns.length > 0) {
            $.each(that.btns, function (i, btn) {
                var option = {};
                if (typeof func == 'function') {
                    option = func(btn);
                    if (option == null) {
                        option = btn;
                    }
                } else {
                    option = btn;
                }
                var cont = option.cont == null ? that.cont : option.cont;
                if (cont) {
                    that.add(cont, option.showText, option.func, option.style);
                }
            });
        }
    };
    //下载
    //上传
    return Button;
}));