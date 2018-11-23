(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    }
}(function () {
    var afisTab = function (container, options) {
        this.options = $.extend({
            "idKey": "id",
            "titleKey": "title",
            "parse": null
        }, options);

        this.component = {
            content: $('<div class="afis-tabs"></div>').appendTo(container)
        };
        this.store = {
            curr: null,
            tabs: null,
            urls: {}
        };
        this.template();
    }
    afisTab.prototype.template = function () {
        var content = this.component.content;
        var toggle = $('<ul class="nav nav-tabs" role="tablist"></ul>').appendTo(content);
        var tabs = $('<div class="tab-content"></div>').appendTo(content);
        $.extend(this.component, {
            toggle: toggle,
            tabs: tabs
        });
        content.on("click", "[data-close='tab']", { that: this }, function (e) {
            e.stopPropagation();

            var that = e.data.that,
                presentation = $(this).closest("[role='presentation']");
            if (presentation.length == 0) {
                return;
            }
            var page = that.store.tabs[presentation.find("a").attr("data-id")];
            if (page == null) {
                return;
            }
            if (!presentation.hasClass("active")) {
                that.close(page);
                return;
            }
            var next = presentation.next();
            if (next.length > 0) {
                presentation.hide();
                var toggle = next.find("a");
                page.state = page.STATE.CLOSE;
                var target = that.store.tabs[toggle.attr("data-id")];
                if (target != null) {
                    target.state = target.STATE.RESTART;
                }
                that.trigger(toggle);
            } else {
                var prev = presentation.prev();
                if (prev.length > 0) {
                    presentation.hide();
                    var toggle = prev.find("a");
                    page.state = page.STATE.CLOSE;
                    var target = that.store.tabs[toggle.attr("data-id")];
                    if (target != null) {
                        target.state = target.STATE.RESTART;
                    }
                    that.trigger(toggle);
                } else {
                    that.close(page);
                    that.empty();
                }
            }
        });
        //切换 原
        content.on('hidden.bs.tab', { that: this }, function (e) {
            var that = e.data.that,
                page = that.store.tabs[$(e.target).attr("data-id")];
            if (page == null) {
                return;
            }
            that.store.curr = null;
            if (page.state == page.STATE.CLOSE) {
                that.close(page);
            } else {
                page.state = page.STATE.PAUSE;
                if (page.onPause) {
                    page.onPause();
                }
            }
            //e.target // newly activated tab
            //e.relatedTarget // previous active tab            
        });
        //切换 现
        content.on('shown.bs.tab', { that: this }, function (e) {
            var that = e.data.that,
                id = $(e.target).attr("data-id"),
                page = that.store.tabs[id];
            if (page == null) {
                return;
            }
            that.store.curr = id;
            if (page.state == page.STATE.RESTART) {
                if (page.onReStart) {
                    page.onReStart();
                }
            }
            if (that.onActive != null) {
                that.onActive(page);
            }
            //e.target // newly activated tab
            //e.relatedTarget // previous active tab
        });
    }
    //导向 页面
    afisTab.prototype.guide = function (url, data) {
        if (typeof this.options.parse == 'function') {
            var parse = this.options.parse(url, data);
            url = parse.url;
            data = parse.data;
        }
        var page = this.getPage(url, data);
        if (page != null) {
            var id = page.id;
            if (id == this.store.curr) {
                return;
            } else {
                var toggle = this.component.toggle.find("li a[data-id='" + id + "']");
                if (toggle.length > 0) {
                    page.state = page.STATE.RESTART;
                    this.trigger(toggle);
                }
                return;
            }
        }
        var that = this;
        require([url], function (page) {
            var options = that.options,
                tabs = that.store.tabs;
            //页面存在
            if (tabs == null) {
                tabs = {};
                if (that.onShown != null) {
                    that.onShown.call(that);
                }
            }
            var id = data[options.idKey];
            if (id) {
                page.id = id;
            }
            var title = data[options.titleKey];
            if (title) {
                page.title = title;
            }
            page.url = url;
            page.state = page.STATE.INIT;
            page.setData(data);
            that.add(page);
        }, function (error) {
            //页面不存在
            //2 异常处理
            console.log("=页面加载=路径找不到=start===");
            console.log(error);
            console.log("=页面加载=路径找不到=end===");
        });
    }
    afisTab.prototype.add = function (page) {
        var component = this.component,
            store = this.store,
            id = page.id,
            url = page.url,
            tabId = "tab" + id,
            tabpanelId = "tabpanel" + id,
            title = page.title,
            toggle = null,
            content = null;
        var presentation = $('<li role="presentation"></li>').appendTo(component.toggle);
        toggle = $('<a data-id="' + id + '" data-target="#' + tabpanelId + '" id="' + tabId + '" role="tab" data-toggle="tab" aria-controls="' + tabpanelId + '" aria-expanded="true"></a>').appendTo(presentation);
        $('<span title="' + title + '">' + title + '</span><i class="glyphicon glyphicon-remove" data-close="tab"></i>').appendTo(toggle);
        content = $('<div role="tabpanel" class="tab-pane fade" id="' + tabpanelId + '" aria-labelledby="' + tabId + '"></div>').appendTo(component.tabs);
        $(page.html).appendTo(content);
        page.template = content;
        try {
            page.onInit();
            if (store.tabs == null) {
                store.tabs = {};
            }
            store.tabs[id] = page;
            store.curr = id;
            store.urls[url] = id;
            this.trigger(toggle, { pageInit: true });
        } catch (error) {
            //1 异常处理
            console.log("=页面加载=加载异常=start===");
            console.log(error);
            console.log("=页面加载=加载异常=end===");
        }
    }
    afisTab.prototype.trigger = function (toggle, option) {
        toggle.trigger($.Event("click", option ? option : {}));
    }
    afisTab.prototype.getPage = function (url, data) {
        if (this.store.tabs == null) {
            return null;
        }
        var page = null;
        if (typeof url == 'string') {
            if (url) {
                if (url.indexOf(".js")) {
                    var id = this.store.urls[url];
                    if (id == null) {
                        return null;
                    } else {
                        page = this.store.tabs[id];
                    }
                } else {
                    page = this.store.tabs[url];
                }
            } else {
                return null;
            }
        } else {
            var id = this.store.urls[url.url];
            if (id != null) {
                page = this.store.tabs[id];
                data = url.data;
            }
        }
        if (page != null) {
            page.setData(data);
        }
        return page;
    }
    //关闭 页面
    afisTab.prototype.close = function (page) {
        var id = page.id,
            toggle = this.component.toggle,
            tabs = this.component.tabs;
        if (page.onClose) {
            page.onClose();
        }
        this.store.tabs[id] = null;
        this.store.urls[page.url] = null;
        toggle.find("#tab" + id).parent("li").remove();
        tabs.find("#tabpanel" + id).remove();
    }
    //组件 显示
    afisTab.prototype.shown = function (func) {
        if (typeof func == 'function') {
            this.onShown = func;
        }
    }
    //组件 隐藏
    afisTab.prototype.hidden = function (func) {
        if (typeof func == 'function') {
            this.onHidden = func;
        }
    }
    afisTab.prototype.active = function (func) {
        if (typeof func == 'function') {
            this.onActive = func;
        }
    }
    //组件 清空
    afisTab.prototype.empty = function () {
        this.store = {
            curr: null,
            tabs: null,
            urls: {}
        };
        if (this.onHidden != null) {
            this.onHidden.call(this);
        }
    }
    return afisTab;
}));