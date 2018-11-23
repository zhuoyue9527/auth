(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (AFIS) {
            AFIS = {};
        }
        AFIS.popover = factory();
    }
}(function () {
    var popover = function (toggle, options) {
        if (options == null) {
            options = toggle;
            toggle = null;
        }
        this.component = {};
        if (toggle) {
            this.component.toggle = toggle;
            this.component.toggle.attr("data-toggle", "popover");
        }
        this.options = $.extend(true, {}, this.default, options);
        if (typeof this.options.content == 'function') {
            this.content = this.options.content;
        }
        if (typeof this.options.shown == 'function') {
            this.shown = this.options.shown;
        }
        if (typeof this.options.hidden == 'function') {
            this.hidden = this.options.hidden;
        }
    };
    popover.prototype.default = {
        animation: true,
        container: false,
        placement: "right",
        trigger: "click",
        viewport: null
    };
    popover.prototype.methold = function (method) {
        this.component.toggle.popover(method);
    }
    popover.prototype.show = function () {
        this.methold("show");
    }
    popover.prototype.hide = function () {
        this.component.toggle.trigger("click");
    }
    popover.prototype.popover = function (trigger) {
        var that = this;
        if (trigger) {
            that.component.toggle = trigger;
            that.component.toggle.attr("data-toggle", "popover");
        }
        var toggle = that.component.toggle;
        if (!toggle) {
            return;
        }
        var options = that.options;
        var title = options.title;
        var template = '<div class="popover afis-popover" role="tooltip"><div class="arrow"></div>'
            + (title ? '<h3 class="popover-title"></h3>' : '')
            + '<div class="popover-content"></div></div>';

        var popover = {
            animation: options.animation,
            placement: options.placement,
            html: true,
            container: options.container,
            template: template,
            title: title,
            trigger: options.trigger,
            content: function () {
                var cont = '<span class="empty">暂无内容</span>';
                if (typeof that.content == 'function') {
                    cont = that.content();
                    if (!cont) {
                        cont = '<span class="empty">暂无内容</span>';
                    }
                }
                return cont;
            }
        };
        var viewport = options.viewport;
        if (viewport) {
            popover["viewport"] = viewport;
        }
        that.popover = toggle.popover(popover).data("bs.popover");
        var tip = that.popover.tip();
        that.component = $.extend(true, that.component, {
            tip: tip,
            title: tip.find(".popover-title"),
            content: tip.find(".popover-content")
        });
        toggle.on("shown.bs.popover", { that: that }, function (e) {
            var that = e.data.that;
            if (typeof that.shown == 'function') {
                that.shown.call(that, that.component);
            }
        });
        toggle.on("hidden.bs.popover", { that: that }, function (e) {
            var that = e.data.that;
            if (typeof that.hidden == 'function') {
                that.hidden.call(that, that.component);
            }
        });
    };
    return popover;
}));