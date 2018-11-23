(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(['cssl!/modules/index/css/page-bg.css'], factory);
    }
}(function () {
    var background = function (container, options) {
        if (typeof options == 'string') {
            options = { "text": options };
        }
        this.options = $.extend({
            text: "欢迎登录"
        }, options);
        this.bg = $('<div class="bg-img"><span class="bg-text">' + this.options.text + '</span></div>').appendTo(container);
    }
    background.prototype.setImg = function (src) {
        this.bg.css("background-image", "url('" + src + "')");
    }
    background.prototype.setText = function (text) {
        this.bg.find(".bg-text").text(text);
    }
    background.prototype.show = function () {
        this.bg.show();
    }
    background.prototype.hide = function () {
        this.bg.hide();
    }
    return background;
}));