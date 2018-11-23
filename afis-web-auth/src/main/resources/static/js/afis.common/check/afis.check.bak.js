(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (!AFIS) {
            AFIS = {};
        }
        AFIS.check = factory();
    }
}(function () {
    var check = function (content, options) {
        this.options = $.extend(true, {
            "idKey": "value",
            "nameKey": "text",
            "radio": false,
            "defItem": null,
            "all": null
        }, options);
        this.component = {
            content: content
        };
        this.store = {
            dic: {},
            selected: null
        };
        this.setOptions();
        this.template();
    }
    check.prototype.setOptions = function () {
        var options = this.options;
        this.radio = typeof options.radio == 'boolean' && options.radio;
        if (typeof options.loadList == 'function') {
            this.loadList = options.loadList;
        }
        if (typeof options.onSelected == 'function') {
            this.onSelected = options.onSelected;
        }
    }
    check.prototype.template = function () {
        var that = this,
            name = new Date().getTime();

        var item = '<label class="select-cont check-item"><div class="select-wapper">' +
            (that.radio ? '<input type="radio" name="radio' + name + '">' : '<input type="checkbox">') +
            '<span class="select-v"></span></div><span class="select-txt"></span></label>';
        that.component.item = item;
        if (!that.radio) {
            that.setAll(that.options.all);
        }
        that.component.content.on("change", ".check-item .select-wapper input", { that: that }, function (e) {
            var thatCheck = e.data.that;
            thatCheck.setSelected();
            if (typeof thatCheck.onSelected == 'function') {
                thatCheck.onSelected.call(thatCheck, thatCheck.store.selected, $(this));
            }
        });
        that.component.content.on("change", ".check-all .select-wapper input", { that: that }, function (e) {
            var thatCheck = e.data.that;
            thatCheck.component.content.find(".check-item input[data-val]").prop("checked", $(this).is(":checked"));
            thatCheck.setSelected(false);
            if (typeof thatCheck.onSelected == 'function') {
                thatCheck.onSelected.call(thatCheck, thatCheck.store.selected, $(this));
            }
        });
    }
    check.prototype.setAll = function (option) {
        if (option) {
            var content = option.content,
                all = $(this.component.item);
            all.removeClass("check-item");
            all.addClass("check-all");
            if (content) {
                all.appendTo(content);
            } else {
                all.prependTo(this.component.content);
            }
            all.find("input").attr("data-val", option[this.options.idKey]);
            var text = option[this.options.nameKey];
            all.find(".select-txt").text(text == null ? "全选" : text);
            this.component.all = all;
        }
    }
    check.prototype.add = function (data, idKey, nameKey, content) {
        var that = this;
        var id = data[idKey || that.options.idKey];
        var text = data[nameKey || that.options.nameKey];
        var component = that.component;
        if (!content) {
            content = component.content;
        }
        this.store.dic[id] = data;
        var item = $(component.item).appendTo(content);
        item.find("input").attr("data-val", id);
        if (text != null) {
            item.find(".select-txt").text(text);
        }
        return item;
    }
    check.prototype.removeItems = function () {
        var store = this.store,
            component = this.component;

        component.content.children(".check-item").remove();
        store.dic = {};
        store.selected = null;
    }
    check.prototype.addList = function (list, func) {
        var that = this,
            content = this.component.content;

        that.removeItems();
        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            var idKey = that.options.idKey,
                nameKey = that.options.nameKey;
            //--------------------------------
            var defItem = that.options.defItem;
            if (defItem) {
                var def = {};
                if (typeof defItem == 'boolean' && defItem) {
                    def[idKey] = "";
                    def[nameKey] = "默认";
                } else {
                    def = defItem;
                }
                var item = that.add(def, idKey, nameKey, content);
                item.find("input[data-val]").addClass("check-item-def");
            }
            $.each(list, function (i, data) {
                that.add(data, idKey, nameKey, content);
            });
        }
        if (typeof func == 'function') {
            func.call(that);
        }
    }
    check.prototype.load = function (def, func) {
        var that = this;
        if (typeof that.loadList == 'function') {
            if (typeof def == 'function') {
                func = def;
                def = null;
            }
            that.loadList(function (list) {
                that.setList(list, def, func);
            });
        }
    }
    check.prototype.setList = function (list, def, func) {
        var that = this;
        that.addList(list, function () {
            this.setValue(def);
            if (typeof func == 'function') {
                func.call(this);
            }
        });
    }
    check.prototype.getList = function () {
        var list = [],
            content = this.component.content,
            dic = this.store.dic;
        $(content.find(".check-item input[data-val]")).each(function () {
            if ($(this).hasClass("check-item-def")) {
                return true;
            }
            var id = $(this).attr("data-val");
            list.push(dic[id]);
        });
        return list;
    }
    check.prototype.setSelected = function (radio, content) {
        var that = this;
        if (radio == null) {
            radio = that.radio;
        }
        if (content == null) {
            content = that.component.content;
        }
        var selected = null;
        var checkes = content.find(".check-item input[data-val]:checked");
        if (checkes.length > 0) {
            if (radio) {
                var id = checkes.eq(0).attr("data-val");
                selected = that.store.dic[id];
            } else {
                selected = [];
                var dic = that.store.dic;
                $(checkes).each(function () {
                    var id = $(this).attr("data-val");
                    selected.push(dic[id]);
                })
            }
        }
        that.store.selected = selected;
        //-全选设置--
        if (that.component.all) {
            var all = that.component.all.find(".select-wapper");
            that.store.isAllChecked = false;
            if (selected && selected.length > 0) {
                var len = content.find(".check-item input[data-val]").length;
                if (len == selected.length) {
                    that.store.isAllChecked = true;
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
    check.prototype.setValue = function (val) {
        var that = this,
            content = this.component.content,
            radio = this.radio;
        content.find(".check-item input[data-val]:checked").prop("checked", false);
        if (val != null) {
            if (radio) {
                var check = content.find(".check-item input[type='radio'][data-val='" + val + "']");
                if (check.length > 0) {
                    check.prop("checked", true);
                }
            } else {
                var vals = [];
                if (Object.prototype.toString.call(val) == '[object Array]' && val.length > 0) {
                    vals = val;
                } else {
                    vals.push(val);
                }
                if (vals.length > 0) {
                    $.each(vals, function (i, n) {
                        var check = content.find(".check-item input[type='checkbox'][data-val='" + n + "']");
                        if (check.length > 0) {
                            check.prop("checked", true);
                        }
                    });
                }
            }
        }
        that.setSelected(radio, content);
    }
    check.prototype.getValue = function (idKey) {
        var selected = this.store.selected;
        if (selected == null) {
            return null;
        }
        if (typeof idKey == 'boolean' && idKey) {
            var key = this.options.idKey;
            if (Object.prototype.toString.call(selected) == '[object Array]') {
                return selected.map(function (n) {
                    return n[key];
                });
            } else {
                return selected[key];
            }
        } else {
            return selected;
        }
    }
    return check;
}));