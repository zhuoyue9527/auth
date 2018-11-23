(function (factory) {
    if (typeof define == 'function' && define.amd) {
        define(["afismq"], factory);
    } else {
        var AFIS = window.afis;
        if (AFIS) {
            AFIS.autocomplate = factory(AFIS.afismq);
        }
    }
}(function (afismq) {
    var auto = function (element, options) {
        this.cont = $(element);
        this.options = $.extend(true, {
            messageType: null, //使用远程数据请求的消息类型
            params: {}, //使用远程数据请求数据时可携带的参数
            postKey: 'value', //请求远程数据时，输入内容的参数名
            nameKey: 'text', //显示可选列表的字段
            idKey: 'value', //实际值的字段
            minLength: 2, //最少输入多少字符后开始联想,
            maxRows: 10, //最多显示的条数
            timegap: 300, //联想的时间间隔，连续输入时不进行联想，停顿该时间间隔后才进行联想，单位毫秒
            placeholder: "请输入",//下拉列表输入项提示
            inside: false,
            load: null
        }, options);
        this.lastTime = 0;
        this.store = {
            dic: {},
            selected: null
        };
        this.load = this.options.load;
        if (typeof this.load != 'function') {
            this.load = null;
        }
        if (!this.options.idKey) {
            this.options.idKey = this.options.nameKey;
        }
        if (!this.options.postKey) {
            this.options.postKey = this.options.nameKey;
        }
        if (typeof this.options.filter == 'function') {
            this.filter = this.options.filter;
        } else {
            this.filter = function (data) {
                return data;
            }
        }
        if (typeof this.options.onSelected == 'function') {
            this.onSelected = this.options.onSelected;
        }
        this.template();
    }
    auto.prototype.template = function () {
        var that = this;
        var timespan = new Date().getTime();
        that.store.timespan = timespan;
        var target = "target_" + timespan;
        var id = "id_" + timespan;
        var template = $('<div class="dropdown"><form class="wrapper">' +
            '<input type="text" autocomplete="off" class="disabled dropdown-toggle form-control" data-toggle="dropdown">' +
            '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>' +
            '</form><ul class="dropdown-menu"></ul></div>').appendTo(that.cont);

        template.attr("id", target);
        var toggle = template.find("input.dropdown-toggle");
        toggle.attr("data-target", "#" + target).attr("id", id);
        var menu = template.find(".dropdown-menu");
        menu.attr("aria-labelledby", id);
        var inside = null;
        if (that.options.inside) {
            toggle.removeClass("disabled");
            toggle.attr("readonly", "readonly");
            inside = $('<li class="list-item-inside"><form class="wrapper">' +
                '<input type="text" autocomplete="off" class="form-control">' +
                '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>' +
                '</form></li>').appendTo(menu)
            inside.on("keyup", "input", { that: that }, that.keyup);
            inside.find("input").attr("placeholder", that.options.placeholder);
        } else {
            template.find("form.wrapper input").attr("placeholder", that.options.placeholder);
            template.on("keyup", "form.wrapper input", { that: that }, that.keyup);
        }
        template.on("click", ".dropdown-menu li.list-item>a", { that: that }, that.itemClick);

        that.component = {
            template: template,
            toggle: toggle,
            menu: menu
        };
        if (inside) {
            that.component["inside"] = inside.find("input[type='text']");
        }
        template.on("shown.bs.dropdown", { that: that }, function (e) {
            var that = e.data.that;
            var inside = that.component.inside;
            if (!inside) {
                that.component.toggle.addClass("disabled");
            } else {
                var lis = inside.closest("li").siblings("li.list-item");
                if (lis.length == 0) {
                    that.component.menu.css("border-width", "0");
                }
            }
        });
        template.on("hidden.bs.dropdown", { that: that }, function (e) {
            var that = e.data.that;
            if (!that.options.inside) {
                that.component.toggle.addClass("disabled");
            }
        });
    }
    auto.prototype.itemClick = function (e) {
        var that = e.data.that;
        if ($(this).closest("li").hasClass("disabled")) {
            return;
        }
        var id = $(this).attr("data-id");
        var store = that.store;
        var selected = store.selected;
        if (selected && id == selected[that.options.idKey]) {
            return;
        }
        selected = store.selected = store.dic[id];
        var text = selected[that.options.nameKey];
        if (that.options.inside) {
            that.component.inside.val(text);
        }
        that.component.toggle.val(text);
        $(this).siblings('input[type="radio"]').trigger("click");
        if (typeof that.onSelected == 'function') {
            that.onSelected(selected);
        }
    }
    auto.prototype.keyup = function (e) {
        var that = e.data.that;
        if (e.keyCode == 38) { //上箭头
            //没有筛选的数据，取消
        } else if (e.keyCode == 40) { //下箭头
        } else if (e.keyCode == 13) {  //回车键
        } else {
            text = $(this).val();
            var template = $(that.component.template);
            that.reset();
            if (!that.isAuto(text)) {
                if (that.timeout) {
                    clearTimeout(that.timeout);
                }
                that.remove();
                if (!that.options.inside) {
                    if (template.hasClass("open")) {
                        template.find(".dropdown-toggle").dropdown('toggle');
                    }
                    $(this).addClass("disabled");
                }
            } else {
                that.timeout = setTimeout(function () {
                    that.autoComplete(text, function () {
                        if (!this.component.template.hasClass("open")) {
                            if (!this.options.inside) {
                                this.component.toggle.removeClass("disabled");
                            }
                            this.component.template.find(".dropdown-toggle").dropdown('toggle');
                        }
                    });
                }, that.options.timegap);
            }
        }
    };
    auto.prototype.isAuto = function (text) {
        var that = this;
        // var lastTime = that.lastTime;
        // var nt = new Date().getMilliseconds();
        // if (text.length > that.options.minLength && nt - lastTime > that.options.timegap) {
        //     that.lastTime = nt;
        //     return true;
        // }
        // return false;
        return text.length >= that.options.minLength;
    }
    auto.prototype.autoComplete = function (text, callback) {
        var that = this,
            load = this.load;
        if (load) {
            var params = that.options.params;
            params[that.options.postKey] = text;
            load.call(that, function (data) {
                var list = that.filter(data);
                if (!that.adjArray(list)) {
                    list = data;
                }
                that.addList(list);
                if (typeof callback == 'function') {
                    callback.call(that);
                }
            }, params);
        }
    }
    auto.prototype.adjArray = function (arr) {
        return Object.prototype.toString.call(arr) == "[object Array]";
    }
    auto.prototype.remove = function (menu) {
        var that = this;
        if (menu == null) {
            menu = that.component.menu;
        }
        that.store.dic = {};
        if (that.options.inside) {
            that.component.inside.closest("li").siblings("li").remove();
        } else {
            menu.children().remove();
        }
    }
    auto.prototype.reset = function () {
        var menu = this.component.menu;
        var checkeds = menu.find("input[type='radio']:checked");
        if (checkeds.length > 0) {
            checkeds.prop("checked", false);
        }
        if (this.options.inside) {
            this.component.toggle.val("");
        }
        this.store.selected = null;
    }
    auto.prototype.getValue = function () {
        var selected = this.store.selected;
        if (selected == null) {
            selected = {};
            selected[this.options.nameKey] = this.component.toggle.val();
        }
        return selected;
    }
    auto.prototype.setValue = function (val) {
        var text = "";
        if (typeof val == 'object') {
            text = val[this.options.nameKey];
            this.selected = val;
        } else {
            text = val;
            var selected = {};
            selected[this.options.idKey] = selected[this.options.nameKey] = text;
            this.selected = selected;
        }
        this.component.toggle.val(text);
    }
    auto.prototype.addList = function (list) {
        var that = this;
        if (!that.adjArray(list)) {
            return;
        }
        var menu = that.component.menu;
        that.remove(menu);
        if (list.length > 0) {
            if (that.options.inside) {
                that.component.menu.css("border-width", "1px");
            }
            var idKey = that.options.idKey;
            var nameKey = that.options.nameKey;
            $.each(list, function (i, n) {
                that.add(n, idKey, nameKey, menu);
            });
        } else {
            $('<li class="disabled"><a href="#"><form>暂无数据</form></a></li>').appendTo(menu);
        }
    }
    auto.prototype.parseHtml = function (text) {
        if (typeof text === 'string' && text) {
            return text.replace(/[<>&"]/g, function (c) {
                return { '<': '&lt;', '>': '&gt;', '&': '&amp;', '"': '&quot;' }[c];
            });
        } else {
            return text;
        }
    }
    auto.prototype.add = function (data, idKey, nameKey, menu) {
        if (data.isDivider) {
            $('<li role="separator" class="divider"></li>').appendTo(menu);
        } else {
            if (!idKey) {
                idKey = this.options.idKey;
            }
            if (!nameKey) {
                nameKey = this.options.nameKey;
            }
            if (!menu) {
                menu = this.component.menu;
            }
            var id = data[idKey];
            var name = this.parseHtml(data[nameKey]);
            this.store.dic[id] = data;
            $('<li class="list-item"><input type="radio" name="' + this.store.timespan + '" ><a href="#" data-id="' + id + '" title="' + name + '">' + name + '</a></li>').appendTo(menu);
        }
    }
    return auto;
}));