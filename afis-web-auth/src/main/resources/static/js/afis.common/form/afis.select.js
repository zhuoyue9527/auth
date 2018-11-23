(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (AFIS == null) {
            AFIS = window.afis = {};
        }
        AFIS.select = factory();
    }
}(function () {
    var select = function (content, options) {
        this.options = $.extend(true, {
            idKey: "value",
            nameKey: "text",
            selected: null,
            list: []
        }, options);
        this.component = {
            content: content
        };
        this.store = {
            dic: {},
            selected: null,
            name: null
        };
        this.setOptions(this.options);
        this.template();
        this.initData();
    }
    select.prototype.initData = function () {
        var list = this.options.list,
            selected = this.options.selected;
        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            this.setList(list, selected);
        } else if (this.loadList) {
            this.load(selected);
        }
    }
    select.prototype.setOptions = function (options) {
        if (typeof options.loadList == 'function') {
            this.loadList = options.loadList;
        }
        if (typeof options.shown == 'function') {
            this.shown = options.shown;
        }
        if (typeof options.onSelected == 'function') {
            this.onSelected = options.onSelected;
        }
        if (typeof options.parseShow == 'function') {
            this.parseShow = options.parseShow;
        }
        if (this.parseShow == null) {
            this.parseShow = function (data, nameKey) {
                var text = null;
                if (typeof data != 'object') {
                    text = data;
                } else {
                    if (typeof nameKey == 'string') {
                        text = data[nameKey];
                    } else {
                        text = data[this.options.nameKey];
                    }
                }
                if (typeof text == 'string') {
                    return text.replace(/[<>&"]/g, function (c) {
                        return { '<': '&lt;', '>': '&gt;', '&': '&amp;', '"': '&quot;' }[c];
                    })
                } else {
                    return text;
                }
            }
        }
        if (typeof options.menuWidth == 'function') {
            this.menuWidth = options.menuWidth;
        }
    }
    select.prototype.template = function () {
        var that = this;
        var id = "dropdown" + new Date().getTime();
        var template = $('<div class="dropdown">' +
            '<button class="form-control btn btn-default dropdown-toggle" id="' + id + '" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">' +
            '<div><span class="afis-txt">请选择</span></div>' +
            '<div class="afis-tip glyphicon"><i class="caret"></i></div></button>' +
            '<div class="dropdown-menu" aria-labelledby="' + id + '"><ul class="list-content"></ul></div></div>').appendTo(that.component.content);
        $.extend(that.component, {
            template: template,
            toggle: template.find(".dropdown-toggle"),
            menu: template.find(".dropdown-menu>ul"),
            show: template.find(".afis-txt"),
            tip: template.find(".afis-tip"),
            item: '<li><input name="' + id + '" type="radio"><a href="#"></a></li>'
        });
        template.on("shown.bs.dropdown", { that: that }, function (e) {
            var that = e.data.that;
            if (typeof that.shown == 'function') {
                that.shown();
            }
        });
        template.on("hidden.bs.dropdown", { that: that }, function (e) {
            var that = e.data.that;
            that.setSelected();
            if (typeof that.onSelected == 'function') {
                that.onSelected(that.store.selected);
            }
        });
        that.component.menu.on("click", "li a", { that: that }, function (e) {
            $(this).siblings("input[data-val]").trigger("click");
        });
        that.component.toggle.on("click", ".afis-tip", { that: that }, function (e) {
            var that = e.data.that;
            var toggle = that.component.toggle;
            var clear = toggle.attr("toggle-selected");
            if (clear === 'true' && clear) {
                e.stopPropagation();
                toggle.attr("toggle-selected", false);
                that.setValue();
            }
        });
    }
    select.prototype.removeItems = function () {
        var store = this.store,
            component = this.component;

        component.show.removeAttr("title");
        component.show.text("请选择");
        component.menu.children().remove();
        store.dic = {};
        store.selected = null;
    }
    select.prototype.load = function (def, func, force) {
        var that = this,
            menu = this.component.menu;
        if (typeof that.loadList == 'function') {
            if (typeof force == 'boolean' && force || menu.find("li").length == 0) {
                that.loadList(function (list) {
                    that.setList(list, def, func);
                });
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    select.prototype.setList = function (list, def, func) {
        var that = this,
            content = this.component.menu;
        that.addList(content, list, function () {
            that.setValue(def);
            if (typeof func == 'function') {
                func.call(this);
            }
        });
    }
    select.prototype.addList = function (content, list, func) {
        if (!content) {
            content = this.component.menu;
        }
        this.removeItems();
        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            var that = this,
                idKey = this.options.idKey,
                nameKey = this.options.nameKey;
            $.each(list, function (i, item) {
                that.add(item, idKey, nameKey, content);
            });
        }
        if (typeof func == 'function') {
            func.call(that);
        }
    }
    select.prototype.add = function (data, idKey, nameKey, menu) {
        var that = this;
        if (!data) {
            return;
        }
        if (!menu) {
            menu = that.component.menu;
        }
        var id = data[idKey || that.options.idKey];
        var text = that.setShow(data, nameKey || that.options.nameKey);
        that.store.dic[id] = data;
        var item = $(that.component.item).appendTo(menu);
        item.find("input").attr("data-val", id);
        item.find("a").attr("title", text).text(text);
    }
    select.prototype.getList = function () {
        var that = this;
        var list = [];
        var dic = that.store.dic;
        $(that.component.menu.find("input[data-val]")).each(function () {
            var id = $(this).attr("data-val");
            var item = dic[id];
            if (item) {
                list.push(item);
            }
        });
        return list;
    }
    select.prototype.setSelected = function (menu) {
        if (menu == null) {
            menu = this.component.menu;
        }
        var selected = null,
            show = this.component.show,
            input = menu.find("input[data-val]:checked");

        if (input.length > 0) {
            selected = this.store.dic[input.attr("data-val")];
        }
        this.store.selected = selected;
        this.component.toggle.attr("toggle-selected", selected != null);
        var text = this.setShow(selected, this.options.nameKey);
        if (text == null || typeof text == 'string' && !text) {
            text = "请选择";
            show.removeAttr("title");
        } else {
            show.attr("title", text);
        }
        show.text(text);
    }
    select.prototype.setShow = function (data, nameKey) {
        var text = null;
        if (data == null) {
            return text;
        }
        if (typeof this.parseShow == 'function') {
            text = this.parseShow(data, nameKey);
        }
        if (text == null) {
            text = data[nameKey];
        }
        return text;
    }
    select.prototype.getValue = function (idKey) {
        var selected = this.store.selected;
        if (!selected) {
            return null;
        }
        var val = null;
        if (typeof idKey == 'boolean' && idKey) {
            val = selected[this.options.idKey];
        } else {
            val = selected;
        }
        return val;
    }
    select.prototype.setValue = function (val) {
        var menu = this.component.menu;
        menu.find("input[data-val]:checked").prop("checked", false);
        if (val != null) {
            var input = menu.find("input[data-val='" + val + "']");
            if (input.length > 0) {
                input.prop("checked", true);
            }
        }
        this.setSelected(menu);
    }
    return select;
}))