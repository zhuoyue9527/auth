(function (factory) {
    if (typeof define == 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (AFIS == null) {
            AFIS = window.afis = {};
        }
        AFIS.tree = factory();
    }
}(function () {
    var menu = function (content, options) {
        this.options = $.extend({
            idKey: "id",
            pIdKey: "pId",
            nameKey: "name",
            childKey: "children",
            indexKey: "index",
            iconKey: "icon",
            hasIcon: true,
            onTemplate: null,
            list: [],
            selected: null,
            parse: false
        }, options);
        this.component = {
            content: content,
            container: null,
        };
        this.store = {
            dic: {},
            selected: null
        };
        this.setOptions();
        this.template();
        this.initData();
    }
    menu.prototype.initData = function () {
        var list = this.options.list,
            selected = this.options.selected;
        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            this.setList(list, selected);
        } else if (this.loadList) {
            this.load(selected);
        }
    }
    menu.prototype.setOptions = function () {
        var that = this,
            options = this.options;
        that.store.check = typeof options.check == 'boolean' && options.check;
        if (typeof options.loadList == 'function') {
            this.loadList = options.loadList;
        }
    }
    menu.prototype.template = function () {
        var that = this;
        var container = $('<ul class="node-container"></ul>').appendTo(this.component.content);
        container.on('show.bs.collapse', { that: that }, that.toggle);
        container.on('hidden.bs.collapse', { that: that }, function (e) {
        });
        container.on('click', ".content", { that: that }, function (e) {
            var li = $(this).closest("li");
            if (li.hasClass("leaf")) {
                e.data.that.component.content.find("li.active").removeClass("active");
                li.addClass("active");
                var id = $(this).attr("data-id");
                var node = e.data.that.store.dic[id];
                if (node != null) {
                    $(this).trigger($.Event('accordionmenu_click', { "node": node }));
                }
            } else {
                var ul = li.siblings("li").find("ul.collapse.in");
                if (ul.length > 0) {
                    ul.collapse('toggle');
                }
            }
        });
        $.extend(that.component, {
            container: container
        });
    }
    menu.prototype.parse = function (idKey, pIdKey, list) {
        function exists(list, parentId) {
            for (var i = 0; i < list.length; i++) {
                if (list[i][idKey] == parentId) {
                    return true;
                }
            }
            return false;
        }
        var nodes = [];
        for (var i = 0; i < list.length; i++) {
            var row = list[i];
            if (!exists(list, row[pIdKey])) {
                nodes.push(row);
            }
        }
        var toDo = [];
        for (var i = 0; i < nodes.length; i++) {
            toDo.push(nodes[i]);
        }
        while (toDo.length) {
            var node = toDo.shift();
            for (var i = 0; i < list.length; i++) {
                var row = list[i];
                if (row[pIdKey] == node[idKey]) {
                    if (node.children) {
                        node.children.push(row);
                    } else {
                        node.children = [row];
                    }
                    toDo.push(row);
                }
            }
        }
        return nodes;
    }
    menu.prototype.load = function (def, func, force) {
        var that = this;
        if (typeof that.loadList == 'function') {
            if (typeof force == 'boolean' && force || that.component.container.children().length == 0) {
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
    menu.prototype.setList = function (nodes, def, func) {
        var data = [];
        if (this.options.parse) {
            var idKey = this.options.idKey;
            var pIdKey = this.options.pIdKey;
            data = this.parse(idKey, pIdKey, nodes);
        } else {
            data = nodes;
        }
        this.addList(this.component.container, data, function () {
            this.setValue(def);
            if (typeof func == 'function') {
                func.call(this);
            }
        });
    }
    menu.prototype.toggle = function (e) {
        var target = $(e.target);
        if (target.children().length == 0) {
            var that = e.data.that,
                nodes = target.data(that.options.childKey),
                content = that.component.content;

            that.addList(target, nodes, function () {
                target.data(that.options.childKey, null);
                var len = target.parentsUntil(content).filter("ul.collapse").length;
                if (len > 0) {
                    target.find("li>.node-show>.content").css("padding-left", len * 10 + "px");
                }
            });
        }
    }
    menu.prototype.addList = function (content, list, func) {
        content.children().remove();
        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            var that = this;
            $.each(list, function (i, node) {
                that.add(content, node);
            });
        }
        if (typeof func == 'function') {
            func.call(that);
        }
    }
    menu.prototype.add = function (content, node) {
        var store = this.store,
            options = this.options,
            id = node[options.idKey],
            name = node[options.nameKey],
            icon = node[options.iconKey],
            childKey = options.childKey,
            child = node[childKey];
        var data = {};
        $.each(node, function (key, val) {
            if (key != childKey) {
                data[key] = val;
            }
        });
        store.dic[id] = data;
        var content = $('<li></li>').appendTo(content);
        if (child != null) {
            var href = "collapse_" + new Date().getTime() + "_" + id;
            $('<a role="button" href="#' + href + '" data-toggle="collapse" aria-expanded="false" class="node-show"></a>').prependTo(content);
            $('<ul id="' + href + '" class="collapse">').appendTo(content).data(childKey, child);
        } else {
            content.addClass("leaf");
            $('<div class="node-show"></div>').appendTo(content);
        }
        $('<div class="prefix img"></div><div class="content" data-id="' + id + '" title="' + name + '">' + name + '</div><div class="suffix"></div>').appendTo(content.find(".node-show"));
        var prefix = content.find(".prefix");
        if (options.hasIcon) {
            if (icon && typeof icon == 'string') {
                if (/^(jpg|JPG|jpeg|JPEG|png|PNG|bmp|BMP|gif|GIF)$/.test(icon)) {//不是图片
                    $('<img src="' + icon + '">').appendTo(prefix);
                } else {
                    prefix.addClass(icon);
                }
            } else {//值为null
                // $('<i class="glyphicon glyphicon-cog"></i>').appendTo(prefix);
            }
        }
        var suffix = content.find(".suffix");
        if (child != null) {
            $('<i class="glyphicon glyphicon-menu-right"></i>').appendTo(suffix);
        } else {
            suffix.remove();
        }
    }
    menu.prototype.clear = function () {
        var content = this.component.content;
        content.find("li.active").removeClass("active");
        content.find(".collapse.in").removeClass("in");
        content.find("[data-toggle='collapse']").attr('aria-expanded', false);
    }
    menu.prototype.setValue = function (val) {

    }
    menu.prototype.expand = function (page) {
        var that = this,
            id = null,
            content = this.component.content;

        if (typeof page == 'string' || typeof page == 'number') {
            id = page;
        } else if (typeof page == 'object') {
            id = page[that.options.idKey];
        }
        var data = that.store.dic[id];
        if (data) {
            var li = content.find("div[data-id='" + id + "']").parents("li.leaf");
            if (li.length > 0 && !li.hasClass("active")) {
                var active = content.find("li.active");
                if (active.length > 0) {
                    if (li.siblings("li.active").length > 0) {
                        active.removeClass("active");
                        li.addClass("active");
                        var toggle = li.parent("ul.collapse").siblings("[data-toggle='collapse']");
                        if (!toggle.attr("aria-expanded") || toggle.attr("aria-expanded") == "false") {
                            toggle.trigger("click");
                        }
                    } else {
                        var activeTrigger = active.parents("ul.collapse.in");
                        if (activeTrigger.find("div[data-id='" + id + "']").length > 0) {
                            var pcontent = active.children("ul");
                            var uls = li.parentsUntil(pcontent).filter("ul.collapse");
                            var len = uls.length;
                            for (var i = len - 1; i >= 0; i--) {
                                var a = uls.eq(i).siblings('a[data-toggle="collapse"]');
                                if (!a.attr("aria-expanded") || a.attr("aria-expanded") == "false") {
                                    a.trigger("click");
                                }
                            }
                        } else {
                            that.clear();
                            var uls = li.parentsUntil(content).filter("ul.collapse");
                            var len = uls.length;
                            for (var i = len - 1; i >= 0; i--) {
                                var a = uls.eq(i).siblings('a[data-toggle="collapse"]');
                                if (!a.attr("aria-expanded") || a.attr("aria-expanded") == "false") {
                                    a.trigger("click");
                                }
                            }
                        }
                        active.removeClass("active");
                        li.addClass("active");
                    }
                } else {




                }
            }
        }
    }
    return menu;
}));