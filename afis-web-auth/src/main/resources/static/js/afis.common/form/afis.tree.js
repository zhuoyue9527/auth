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
    var afisTree = function (content, options) {
        this.options = $.extend({
            idKey: "id",
            nameKey: "name",
            childKey: "child",
            indexKey: "index",
            check: false,
            sign: function (content) {
                $('<i class="glyphicon"></i>').appendTo(content);
            },
            show: function (content, node) {
                $('<span>' + node[this.options.nameKey] + '</span>').appendTo(content);
            },
            onTemplate: null
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
    }
    afisTree.prototype.setOptions = function () {
        var that = this,
            options = this.options;
        that.store.check = typeof options.check == 'boolean' && options.check;
        if (typeof options.filter == 'function') {
            this.filter = options.filter;
        }
        if (typeof options.loadList == 'function') {
            this.loadList = options.loadList;
        }
    }
    afisTree.prototype.template = function () {
        var that = this;
        that.component.content.addClass("afis");
        var container = $('<ul class="tree-container"></ul>').appendTo(this.component.content);
        container.on('show.bs.collapse', { that: that }, that.toggle);
        container.on('hidden.bs.collapse', { that: that }, function (e) {
        });
        $.extend(that.component, {
            container: container
        });
    }
    afisTree.prototype.listToTree = function (list) {
        var tree = {};
        return tree;
    }
    afisTree.prototype.load = function (params, func, force) {
        var that = this;
        if (typeof that.loadList == 'function') {
            if (typeof params == 'function') {
                func = params;
                params = {};
            }
            if (typeof force == 'boolean' && force || that.component.container.children().length == 0) {
                that.loadList(params, function (list) {
                    that.addList(that.component.container, list, func);
                });
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    afisTree.prototype.setNodes = function (nodes, def, func) {
        this.addList(nodes, function () {
            this.setValue(def);
            if (typeof func == 'function') {
                func.call(this);
            }
        });
    }
    afisTree.prototype.toggle = function (e) {
        var target = $(e.target);
        if (target.children().length == 0) {
            var that = e.data.that,
                nodes = target.data(that.options.childKey);
            that.addList(target, nodes, function () {
                target.data(that.options.childKey, null);
            });
        }
    }
    afisTree.prototype.addList = function (content, list, func) {
        content.children().remove();
        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            var that = this,
                options = that.options,
                idKey = options.idKey,
                nameKey = options.nameKey,
                childKey = options.childKey;
            $.each(list, function (i, node) {
                that.add(content, node, idKey, nameKey, childKey);
            });
        }
        if (typeof func == 'function') {
            func.call(that);
        }
    }
    afisTree.prototype.add = function (content, node, idKey, nameKey, childKey) {
        var that = this,
            store = this.store,
            options = this.options,
            show = options.show,
            sign = options.sign,
            id = node[idKey || options.idKey],
            name = node[nameKey || options.nameKey],
            childKey = childKey || options.childKey,
            child = node[childKey];
        var data = {};
        $.each(node, function (key, val) {
            if (key != childKey) {
                data[key] = val;
            }
        });
        store[id] = data;
        var li = $('<li><div class="tree-toggle"><div class="node-sign"></div><div class="node-show"></div></div></li>').appendTo(content);
        if (child != null) {
            var href = "collapse_" + new Date().getTime() + "_" + id,
                toggle = $('<a role="button" href="#' + href + '" data-toggle="collapse" aria-expanded="false"></a>').prependTo(li.find(".node-sign"));
            if (typeof sign == 'function') {
                sign.call(that, toggle, data);
            } else if (typeof sign == 'string') {
                $(sign).appendTo(toggle);
            } else {
                $('<i class="glyphicon"></i>').appendTo(toggle);
            }
            $('<ul id="' + href + '" class="collapse">').appendTo(li).data(childKey, child);
        }
        if (store.check) {
            var check = $('<label class="select-cont check-all">' +
                '<div class="select-wapper"><input type="checkbox"><span class="select-v"></span></div>' +
                '<div class="select-txt"></div></label>').appendTo(li.find(".node-show"));
            if (typeof show == 'function') {
                show.call(that, check.find(".select-txt"), data);
            } else {
                check.find(".select-txt").text(name);
            }
        } else {
            if (typeof show == 'function') {
                show.call(that, li.find(".node-show"), data);
            } else {
                $('<label>' + name + '</label>').appendTo(li.find(".node-show"));
            }
        }
    }
    afisTree.prototype.setValue = function (val) {

    }
    return afisTree;
}));