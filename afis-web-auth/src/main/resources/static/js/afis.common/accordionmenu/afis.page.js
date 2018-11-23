(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    }
}(function () {
    var page = function (html) {
        this.html = html;
        this.template = null;
        this.id = new Date().getTime();
        this.title = "标题";
        this.url = "";
        this.data = {};
        this.state = this.STATE.INIT;
    };
    page.prototype.STATE = {
        INIT: 0,
        PAUSE: 1,
        RESTART: 2,
        CLOSE: 3
    };
    //初始化
    page.prototype.init = function (func) {
        return this.setFunc(func, "Init");
    };
    //暂停
    page.prototype.pause = function (func) {
        return this.setFunc(func, "Pause");
    };
    //重启
    page.prototype.reStart = function (func) {
        return this.setFunc(func, "ReStart");
    };
    //关闭
    page.prototype.close = function (func) {
        this.setFunc(func, "Close");
        this.dispose();
    };
    page.prototype.dispose = function () {
        this.template = null;
        this.data = null;
    };
    page.prototype.setData = function (data) {
        this.data = data;
    };
    page.prototype.setFunc = function (func, name) {
        if (typeof func == 'function') {
            this["on" + name] = func;
        }
        return this;
    }
    return page;
}));