(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (AFIS == null) {
            AFIS = window.afis = {};
        }
        AFIS.multiselect = factory();
    }
}(function () {
    var multiselect = function (content, options) {
        this.options = $.extend(true, {
            "idKey": "value",
            "nameKey": "text",
            "selecrAll": null
        }, options);
        this.component = {
            content: content
        };
        this.store = {
            dic: {},
            selected: []
        };
        this.setOptions(this.options);
        this.template();
    }
    multiselect.prototype.setOptions = function (options) {
        if (typeof options.loadList == 'function') {
            this.loadList = options.loadList;
        }
        if (typeof options.shown == 'function') {
            this.shown = options.shown;
        }
        if (typeof options.onSelected == 'function') {
            this.onSelected = options.onSelected;
        }
        if (typeof options.onAllChange == 'function') {
            this.onAllChange = options.onAllChange;
        }
        if (typeof options.parseShow == 'function') {
            this.parseShow = options.parseShow;
        }
        if (this.parseShow == null) {
            this.parseShow = function (selected) {
                var that = this;
                if (Object.prototype.toString.call(selected) == '[object Array]' && selected.length > 0) {
                    return selected.map(function (n) {
                        return n[that.options.nameKey];
                    }).join(",");
                } else {
                    return "请选择";
                }
            }
        }
        if (typeof options.menuWidth == 'function') {
            this.menuWidth = options.menuWidth;
        }
    }
    multiselect.prototype.template = function () {
        var that = this;
        var id = "dropdown" + new Date().getTime();
        var template = $('<div class="dropdown afis-multiselect">' +
            '<button class="form-control btn btn-default dropdown-toggle" id="' + id + '" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">' +
            '<div><span class="afis-txt">请选择</span></div>' +
            '<div class="afis-tip glyphicon"><i class="caret"></i></div></button>' +
            '<form class="dropdown-menu" aria-labelledby="' + id + '"><ul class="list-content"></ul></form></div>').appendTo(that.component.content);
        $.extend(that.component, {
            template: template,
            toggle: template.find(".dropdown-toggle"),
            menu: template.find(".dropdown-menu>ul.list-content"),
            show: template.find(".afis-txt"),
            tip: template.find(".afis-tip")
        });
        template.on("shown.bs.dropdown", { that: that }, function (e) {
            var that = e.data.that;
            var load = that.load(that.options.selected, function () {
                if (typeof this.shown == 'function') {
                    this.shown();
                }
            });
            if (!load) {
                if (typeof that.shown == 'function') {
                    that.shown();
                }
            }
        });
        template.on("hidden.bs.dropdown", { that: that }, function (e) {
            var that = e.data.that;
            if (typeof that.onSelected == 'function') {
                that.onSelected(that.store.selected);
            }
        });
        that.component.menu.on("click", "li a", { that: that }, function (e) {
            $(this).siblings("input[data-val]").trigger("click");
        });
        that.component.template.on("change", "li.select-all input", { that: that }, function (e) {
            var that = e.data.that;
            var menu = that.component.menu;
            var checked = $(this).is(":checked");
            menu.find("li.select-item input").prop("checked", checked);
            that.setSelected(menu);
        });
        that.component.menu.on("change", "li.select-item input", { that: that }, function (e) {
            var that = e.data.that;
            that.setSelected();
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
    multiselect.prototype.load = function (def, func, force) {
        var that = this,
            menu = this.component.menu;
        if (typeof that.loadList == 'function') {
            if (typeof force == 'boolean' && force || menu.find("li.select-item").length == 0) {
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
    multiselect.prototype.setList = function (list, def, func) {
        var that = this;
        that.addList(list, function () {
            this.setValue(def);
            if (typeof func == 'function') {
                func.call(this);
            }
        });
    }
    multiselect.prototype.removeItems = function () {
        var store = this.store,
            show = this.component.show,
            menu = this.component.menu,
            toggle = this.component.toggle;

        menu.children("li.select-item").remove();
        show.removeAttr("title");
        show.text("请选择");
        toggle.removeAttr("toggle-selected");

        store.dic = {};
        store.selected = [];
    }
    multiselect.prototype.addList = function (list, func) {
        var menu = this.component.menu;
        this.removeItems();

        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            var that = this,
                idKey = this.options.idKey,
                nameKey = this.options.nameKey;

            that.addSelectAll(that.options.selectAll, menu);
            $.each(list, function (i, item) {
                that.add(item, idKey, nameKey, menu);
            });
        }
        if (typeof func == 'function') {
            func.call(that);
        }
    }
    multiselect.prototype.add = function (data, idKey, nameKey, menu) {
        var that = this;
        if (!data) {
            return;
        }
        if (!menu) {
            menu = that.component.menu;
        }
        var id = data[idKey ? idKey : that.options.idKey];
        var text = data[nameKey ? nameKey : that.options.nameKey];
        that.store.dic[id] = data;
        var li = $('<li class="select-item"><label class="select-cont"><div class="select-wapper"><input type="checkbox"><span class="select-v"></span></div><span class="select-txt"></span></label></li>').appendTo(menu);
        li.find(".select-txt").attr("title", text).text(text);
        li.find("input").attr("data-val", id);
    }
    multiselect.prototype.getList = function () {
        var that = this;
        var list = [];
        var dic = that.store.dic;
        $(that.component.menu.find("li.select-item input[data-val]")).each(function () {
            var id = $(this).attr("data-val");
            var item = dic[id];
            if (item) {
                list.push(item);
            }
        });
        return list;
    }
    multiselect.prototype.addSelectAll = function (option, menu) {
        if (option) {
            var id = option[this.options.idKey];
            var all = $('<ul class="list-fixed"><li><label class="select-cont"></label></li></ul>');
            $('<div class="select-wapper"><input type="checkbox"><span class="select-v"></span></div><span class="select-txt"></span>').appendTo(all.find("label"));
            $('<li role="separator" class="divider"></li>').appendTo(all);
            all.prependTo(menu.parent());
            all.find("li").addClass("select-all");
            var text = option[this.options.nameKey];
            all.find(".select-txt").text(text || "全选");
            if (!(id == null || typeof id == 'string' && !id)) {
                all.find("input").attr("data-val", id);
            }
            this.component.all = all;
        }
    }
    multiselect.prototype.setSelected = function (menu) {
        var that = this;
        if (menu == null) {
            menu = that.component.menu;
        }
        var show = that.component.show;
        var selected = [];
        var inputs = menu.find("li.select-item input[data-val]:checked");
        $(inputs).each(function () {
            var id = $(this).attr("data-val");
            selected.push(that.store.dic[id]);
        });
        that.store.selected = selected;
        that.component.toggle.attr("toggle-selected", selected.length > 0);
        var text = typeof that.parseShow == 'function' ? that.parseShow(selected) : selected.map(function (n) {
            return n[that.options.nameKey];
        }).join(",");
        if (text == null || typeof text == 'string' && !text) {
            text = "请选择";
            show.removeAttr("title");
        } else {
            show.attr("title", text);
        }
        show.text(text);
        //-----set-all-------
        if (that.component.all.length > 0) {
            var all = that.component.all.find("li.select-all .select-wapper");
            if (all.length > 0) {
                var len = menu.find("li.select-item input[data-val]").length;
                if (selected.length > 0) {
                    if (len == selected.length) {
                        all.find("input").prop("checked", true);
                        all.removeClass("half-checked");
                    } else {
                        all.find("input").prop("checked", false);
                        all.addClass("half-checked");
                    }
                } else {
                    all.find("input").prop("checked", false);
                    all.removeClass("half-checked");
                }
            }
        }
    }
    multiselect.prototype.getValue = function (idKey) {
        var selected = this.store.selected;
        if (selected.length == 0) {
            return [];
        }
        if (typeof idKey == 'boolean' && idKey) {
            var idKey = this.options.idKey;
            return selected.map(function (n) {
                return n[idKey];
            });
        } else {
            return selected;
        }
    }
    multiselect.prototype.setValue = function (val) {
        var menu = this.component.menu;
        menu.find("input[data-val]:checked").prop("checked", false);
        if (Object.prototype.toString.call(val) == '[object Array]' && val.length > 0) {
            $.each(val, function (i, id) {
                var input = menu.find("input[data-val='" + id + "']");
                if (input.length > 0) {
                    input.prop("checked", true);
                }
            });
        }
        this.setSelected(menu);
    }
    return multiselect;
}))