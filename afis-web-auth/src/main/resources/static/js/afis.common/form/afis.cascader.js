(function (factory) {
    if (typeof define == 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (AFIS == null) {
            AFIS = window.afis = {};
        }
        AFIS.cascader = factory();
    }
}(function () {
    var cascader = function (content, options) {
        this.options = $.extend({
            "idKey": "value",
            "nameKey": "text",
            "childKey": "child",
            "isLoadChild": false,
            "viewport": null,
            "trigger": "hover"
        }, options);
        this.dataKey = "dataKey";
        this.setOptions(this.options);
        this.component = {
            content: content
        };
        this.store = {
            selected: []
        };
        this.template();
    }
    cascader.prototype.setPosition = function () {
        var viewport = this.options.viewport;
        if (viewport != null) {
            var menu = this.component.menu,
                toggle = this.component.toggle,
                tLeft = toggle.offset().left,
                tW = toggle.outerWidth(),
                mW = menu.outerWidth(),
                vLeft = $(viewport).offset().left,
                vW = $(viewport).outerWidth();

            if (tLeft + mW >= vLeft + vW) {
                this.store.over = true;
                menu.css("left", -Math.abs(mW - tW) + "px");
            } else {
                if (this.store.over && mW > tW) {
                    menu.css("left", -Math.abs(mW - tW) + "px");
                } else {
                    this.store.over = false;
                    menu.css("left", "0px");
                }
            }
        }
    }
    cascader.prototype.setOptions = function (options) {
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
        if (typeof options.menuWidth == 'function') {
            this.menuWidth = options.menuWidth;
        }
        if (!this.menuWidth) {
            this.menuWidth = function (menu) {
                if (!menu) {
                    menu = this.component.menu;
                }
                var w = menu.find(".cascader-level").length * 190;
                menu.css("width", (w == 0 ? 190 : w + 2) + "px");
            }
        }
    }
    cascader.prototype.template = function () {
        var that = this;
        var id = "dropdown" + new Date().getTime();
        var template = $('<div class="dropdown afis-cascader">' +
            '<button type="button" class="form-control btn btn-default dropdown-toggle" id="' + id + '" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">' +
            '<div><span class="afis-txt">请选择</span></div>' +
            '<div class="afis-tip glyphicon"><i class="caret"></i></div>' +
            '</button>' +
            '<form class="dropdown-menu cascader-content" aria-labelledby="' + id + '"></form></div>').appendTo(that.component.content);
        $.extend(that.component, {
            template: template,
            toggle: template.find('.dropdown-toggle'),
            menu: template.find('.dropdown-menu.cascader-content')
        });
        template.on("shown.bs.dropdown", { that: that }, function (e) {
            var that = e.data.that;
            var load = that.load(that.options.selected, function () {
                that.setPosition();
                if (typeof this.shown == 'function') {
                    this.shown();
                }
            });
            if (!load) {
                that.setPosition();
                if (typeof that.shown == 'function') {
                    that.shown();
                }
            }
        });
        template.on("hidden.bs.dropdown", { that: that }, function (e) {
            var that = e.data.that;
            that.component.toggle.attr("toggle-selected", that.store.selected.length > 0);
            if (typeof that.onSelected == 'function') {
                that.onSelected(that.store.selected);
            }
        });
        var trigger = 'click';
        that.component.menu.on(trigger, "li a", { that: that }, that.active);

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
    cascader.prototype.load = function (params, func, force) {
        var that = this;
        if (typeof that.loadList == 'function') {
            if (typeof params == 'function') {
                func = params;
                params = {};
            }
            if (typeof force == 'boolean' && force || that.component.menu.find(".cascader-level").length == 0) {
                that.loadList(params, function (list) {
                    that.setList(list, null, func);
                });
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    cascader.prototype.active = function (e) {
        var that = e.data.that;
        var isToggle = typeof e.isToggle == 'boolean' ? e.isToggle : true;
        var li = $(this).closest("li");
        if (li.hasClass("active")) {
            return;
        } else {
            li.siblings("li.active").removeClass("active");
            li.addClass("active");
            if (li.find("span.glyphicon.glyphicon-chevron-right").length > 0) {
                li.closest(".cascader-level").nextAll(".cascader-level").remove();
                var data = li.data(that.dataKey);
                if (data) {
                    if (that.options.isLoadChild) {
                        that.load(data);
                    } else {
                        var list = data[that.options.childKey];
                        that.addList(list);
                    }
                }
            } else {
                if (isToggle) {
                    that.component.toggle.dropdown("toggle");
                }
            }
            that.setSelected();
        }
    }
    cascader.prototype.setList = function (list, def, func) {
        var that = this;
        that.addList(list, function () {
            this.setValue(def);
            if (typeof func == 'function') {
                func.call(this);
            }
        });
    }
    cascader.prototype.addList = function (list, func, level) {
        if (Object.prototype.toString.call(list) != '[object Array]' || list.length <= 0) {
            return;
        }
        var that = this,
            options = this.options,
            menu = this.component.menu;
        if (level == null) {
            console.log(menu.outerWidth());
            level = $('<div class="cascader-level"><ul class="list-content"></ul></div>').appendTo(menu).find("ul");
        } else {
            level.children().remove();
        }
        this.menuWidth(menu);
        var idKey = options.idKey, nameKey = options.nameKey, childKey = options.childKey;
        $.each(list, function (i, n) {
            that.add(level, n, idKey, nameKey, childKey)
        });
        if (typeof func == 'function') {
            func.call(that, level);
        }
    }
    cascader.prototype.add = function (content, data, idKey, nameKey, childKey) {
        var that = this, options = this.options;
        var id = data[idKey || options.idKey];
        var name = data[nameKey || options.nameKey];
        var item = $('<li><a href="#" title="' + name + '">' + name + '</a></li>').appendTo(content);
        item.attr("data-id", id).attr("data-text", name).find("a");
        item.data(that.dataKey, data);
        if (data[childKey || options.childKey]) {
            $('<span class="glyphicon glyphicon-chevron-right"></span>').appendTo(item);
        }
    }
    cascader.prototype.setSelected = function () {
        var that = this;
        var levels = that.component.menu.find(".cascader-level");
        var selected = [];
        $.each(levels, function () {
            var li = $(this).find("li.active");
            if (li.length > 0) {
                var data = {};
                $.each(li.data(that.dataKey), function (key, val) {
                    if (key != that.options.childKey) {
                        data[key] = val;
                    }
                });
                selected.push(data);
            }
        });
        var text = null;
        if (selected.length > 0) {
            if (typeof that.parseShow == 'function') {
                text = that.parseShow(selected);
            }
            if (!text) {
                text = selected.map(function (n) {
                    return n[that.options.nameKey];
                }).join("/");
            }
        }
        var toggle = that.component.toggle.find(".afis-txt");
        if (text) {
            toggle.attr("title", text);
        } else {
            text = "请选择";
            toggle.removeAttr("title");
        }
        toggle.text(text);
        that.store.selected = selected;
        that.setPosition();
    }
    cascader.prototype.getValue = function (idKey) {
        var that = this;
        var selected = that.store.selected;
        if (selected.length > 0) {
            if (typeof idKey == 'boolean' && idKey) {
                return selected.map(function (n) {
                    return n[that.options.idKey];
                });
            } else {
                return selected;
            }
        } else {
            return selected;
        }
    }
    cascader.prototype.reset = function (val) {
        var that = this;
        var levels = that.component.menu.find(".cascader-level");
        if (levels.length > 0) {
            var level = levels.eq(0);
            level.find("li.active").removeClass("active");
            level.siblings().remove();
        }
        that.menuWidth(that.component.menu);
        that.setSelected();
    }
    cascader.prototype.setValue = function (value) {
        var that = this;
        var levels = that.component.menu.find(".cascader-level");
        if (Object.prototype.toString.call(value) == '[object Array]' && value.length > 0) {
            if (levels.length > 0) {
                $.each(value, function (i, n) {
                    var level = that.component.menu.find(".cascader-level").eq(i);
                    if (level) {
                        var li = level.find("li[data-id='" + n + "']");
                        if (li.length > 0) {
                            li.find("a").trigger($.Event('click', { isToggle: false }));
                        } else {
                            return false;
                        }
                    }
                });
            } else {
                that.load(function () {
                    $.each(value, function (i, n) {
                        var level = that.component.menu.find(".cascader-level").eq(i);
                        if (level) {
                            var li = level.find("li[data-id='" + n + "']");
                            if (li.length > 0) {
                                li.find("a").trigger($.Event('click', { isToggle: false }));
                            } else {
                                return false;
                            }
                        }
                    });
                });
            }
        } else {
            if (levels.length > 0) {
                var level = levels.eq(0);
                level.find("li.active").removeClass("active");
                level.siblings().remove();
            }
            that.menuWidth(that.component.menu);
            that.setSelected();
        }
    }
    return cascader;
}));