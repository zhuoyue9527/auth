(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(["toastr"], factory);
    } else {
        var AFIS = window.afis;
        if (AFIS == null) {
            AFIS = window.afis = {};
        }
        AFIS.popup = factory(toastr);
    }
}(function (toastr) {
    'use strict';
    function Popup(options) {
        var templates = options != null ? $.extend({}, this.defaults.templates, options.templates) : this.defaults.templates;
        this.options = $.extend({}, this.defaults, options);
        this.options.templates = templates;
        //---------------------------------
        this.modal = $(this.options.templates.modal);
        var name = this.options.name;
        if (name) {
            this.modal.attr("data-name", name);
        }
        var dialog = $(this.options.templates.dialog).appendTo(this.modal);
        this.content = $(this.options.templates.content).appendTo(dialog);

        if (this.options.isTitle) {
            this.header = $(this.options.templates.header).appendTo(this.content);
            $(this.options.templates.close).appendTo(this.header);
            this.title = $(this.options.templates.title).appendTo(this.header);
            var text = this.options.title;
            if (text) {
                this.title.text(text);
            }
        }
        this.body = $(this.options.templates.body).appendTo(this.content);
        if (this.options.isFooter) {
            this.footer = $(this.options.templates.footer).appendTo(this.content);
            var footer = this.footer;
            if (this.options.isConfirm) {
                var confirm = $(this.options.templates.ok).appendTo(footer);
                confirm.text(this.options.confirm);
                confirm.click(this.options.onConfirm);
            }
            if (this.options.btns.length > 0) {
                var btnTemplate = this.options.templates.button;
                $.each(this.options.btns, function (i, n) {
                    var btn = $(btnTemplate).appendTo(footer);
                    btn.text(n.text);
                    btn.click(n.click);
                });
            }
            if (this.options.isCancel) {
                var cancel = $(this.options.templates.cancel).appendTo(footer);
                cancel.text(this.options.cancel);
                cancel.click(this.options.onCancel);
            }
        }
    }
    Popup.prototype = {
        constructor: Popup,
        defaults: {
            btns: [],
            width: null,
            isFooter: true,
            isTitle: true,
            title: "标题",
            confirm: "确定",
            cancel: "取消",
            isConfirm: true,
            isCancel: true,
            onConfirm: null,
            onCancel: null,
            onHidden: null,
            onShown: null,
            name: "",
            templates: {
                modal: '<div class="popup modal fade" tabindex="-1" role="dialog"></div>',
                dialog: '<div class="modal-dialog modal-lg" role="document"></div>',
                content: '<div class="modal-content"></div>',
                header: '<div class="modal-header"></div>',
                close: '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>',
                title: '<h4 class="modal-title"></h4>',
                body: '<div class="modal-body"></div>',
                footer: '<div class="modal-footer"></div>',
                ok: '<button type="button" class="btn afis-btn btn-primary"></button>',
                cancel: '<button type="button" class="btn afis-btn btn-cancel btn-default" data-dismiss="modal"></button>',
                button: '<button type="button" class="btn afis-btn btn-default"></button>'
            },
            toastr: {
                "closeButton": false,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "rtl": false,
                "positionClass": "toast-mid-center",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": 300,
                "hideDuration": 800,
                "timeOut": 1800,
                "extendedTimeOut": 1000,
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            }
        },
        isFunction: function (obj) {
            return obj != null && typeof obj == 'function';
        },
        close: function () {
            if (!this.isFunction(this.modal.modal)) {
                return;
            }
            this.modal.modal("hide");
        },
        showModal: function () {
            var that = this;
            that.modal.appendTo($("body"));

            var dialog = that.modal.find(".modal-dialog");
            var w = that.options.width;
            if (w === "larger") {
                dialog.width(($("body").width() - 100));
            } else if (typeof w == 'number') {
                dialog.width(w);
            }
            if (!this.isFunction(this.modal.modal)) {
                return;
            }
            this.modal.on('shown.bs.modal', function (e) {
                var onShown = that.options.onShown;
                if (onShown != null && typeof onShown == 'function') {
                    onShown(e);
                }
            });
            this.modal.on('hidden.bs.modal', function (e) {
                var modal = that.modal.data("bs.modal");
                if (modal) {
                    modal.$element.remove();
                    if ($(".popup.modal.fade.in")) {
                        $(document.body).addClass("modal-open");
                    }
                }
                var onHidden = that.options.onHidden;
                if (onHidden != null && typeof onHidden == 'function') {
                    onHidden(e);
                }
            });
            this.modal.modal({ "backdrop": "static", "show": true });
        },
        loadModal: function (path, callback) {
            if (typeof define === 'function' && define.amd) {
                if (!path) {
                    return;
                }
                var that = this;
                require(path, function (page) {
                    var dom = $(page).appendTo(that.body);
                    if (callback != null && typeof callback == 'function') {
                        callback(dom, that);
                    }
                    that.showModal();
                });
            }
        },
        confirm: function (title, content, confirm, cancel) {
            if (!this.isFunction($.confirm)) {
                return;
            }
            var p = {};
            if (typeof content == 'function') {
                cancel = confirm;
                confirm = content;
                content = title;
                title = "操作提示";
            }
            if (title) {
                p.title = title;
            }
            if (content) {
                p.content = content;
            }
            var buttons = {
                yes: {
                    text: "确定",
                    btnClass: "btn-primary",
                },
                no: {
                    text: "取消",
                    btnClass: "btn-default",
                }
            }
            if (confirm) {
                buttons.yes.action = confirm;
            }
            if (cancel) {
                buttons.no.action = cancel;
            }
            p.buttons = buttons;
            $.confirm(p);
        },
        alert: function (title, content, confirm) {
            if (!this.isFunction($.alert)) {
                return;
            }
            var p = {};
            if (typeof content == 'function') {
                confirm = content;
                content = title;
                title = "操作提示";
            } else if (content == null) {
                content = title;
                title = "操作提示";
            }
            if (title) {
                p.title = title;
            }
            if (content) {
                p.content = content;
            }
            if (confirm) {
                p.confirm = confirm;
            }
            p.buttons = {
                yes: {
                    text: "确定",
                    btnClass: 'btn-primary',
                    action: confirm
                }
            }
            $.alert(p);
        },
        success: function (text, func) {
            if (this.toastr) {
                toastr.remove();
            }
            if (typeof text == 'function') {
                func = text;
                text = "操作成功";
            }
            this.toastr = toastr.options = this.options.toastr;
            toastr["success"](text);
            if (func != null && typeof func == 'function') {
                func();
            }
        },
        info: function (text, func) {
            if (this.toastr) {
                toastr.remove();
            }
            this.toastr = toastr.options = this.options.toastr;
            toastr["info"](text);
            if (func != null && typeof func == 'function') {
                func();
            }
        },
        warning: function (text, func) {
            if (this.toastr) {
                toastr.remove();
            }
            this.toastr = toastr.options = this.options.toastr;
            toastr["warning"](text);
            if (func != null && typeof func == 'function') {
                func();
            }
        },
        error: function (text, func) {
            if (this.toastr) {
                toastr.remove();
            }
            this.toastr = toastr.options = this.options.toastr;
            toastr["error"](text);
            if (func != null && typeof func == 'function') {
                func();
            }
        }
    };
    return Popup;
}));