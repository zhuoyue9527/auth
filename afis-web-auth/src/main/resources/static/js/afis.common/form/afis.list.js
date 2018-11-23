(function (factory) {
    if (typeof define == 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (AFIS == null) {
            AFIS = window.afis = {};
        }
        AFIS.list = factory();
    }
}(function () {
    var list = function (element, options) {
        this.options = $.extend({
            idKey: "id",
            nameKey: "name",
            panelKey: null,//以此来判断 有值的是二级列表 没有的是 一级列表
            panelParse: null,
            check: false,
            link: true,
            collapse: false
        }, options);

        var id = "accordion" + new Date().getTime();
        this.store = {
            id: id,
            dic: {},
            list: []
        };
        this.template(element);
    }
    list.prototype.template = function (container) {
        var content = $('<div class="panel-group" id="' + this.store.id + '" role="tablist"></div>').appendTo(container);
        this.component = {
            check: function (check, id, name) {
                if (check) {
                    return '<label class="select-cont">' +
                        '<div class="select-wapper"><input data-id="' + id + '" type="checkbox"><span class="select-v"></span></div>' +
                        (name ? ('<span class="select-txt">' + name + '</span>') : '') +
                        '</label>'
                } else {
                    return '<span>' + (name ? name : '') + '</span>';
                }
            },
            content: content
        };
        content.on("change", '[type="checkbox"]', { that: this }, this.toggle);
    }
    list.prototype.addList = function (list, func) {
        var that = this;
        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            var options = this.options,
                check = options.check,
                content = this.component.content;
            that.store.list = list;
            if (check) {
                content.attr("aria-multiselectable", true);
            }
            var opt = {
                "idKey": options.idKey,
                "nameKey": options.nameKey,
                "panelKey": options.panelKey,
                "content": content,
                "check": check
            };
            $.each(list, function (i, item) {
                that.add(item, opt);
            });
        }
        if (typeof func == 'function') {
            func.call(that);
        }
    }
    list.prototype.toggle = function (e) {
        var that = e.data.that;
        setTimeout(function () {
            var content = that.component.content;
            var checked = content.find(".panel-body input[type='checkbox']:checked");
            var seleted = [];
            if (checked.length > 0) {
                $(checked).each(function () {
                    var id = $(this).attr("data-id");
                    if (id) {
                        seleted.push(id);
                    }
                });
            }
            that.store.seleted = seleted;
        }, 300);
    }
    list.prototype.add = function (data, opt) {
        var that = this;
        if (data != null || content != null) {
            var idKey = opt.idKey,
                nameKey = opt.nameKey,
                content = opt.content,
                check = opt.check,
                panelKey = opt.panelKey;

            var id = data[idKey],
                name = data[nameKey];
            var panel = $('<div class="panel panel-default"></div>').appendTo(content);
            var heading = $('<div class="panel-heading" role="tab"><h4 class="panel-title"></h4></div>').appendTo(panel);
            if (panelKey != null) {
                var less = {}; less[panelKey] = null;
                that.store.dic[id] = $.extend(true, {}, data, less);

                var items = data[panelKey],
                    panelId = "panel" + id,
                    collapseId = "collapse" + id,
                    title = heading.find(".panel-title");

                heading.attr("id", panelId);
                $(that.component.check(check, id)).appendTo(title);
                if (check) {
                    var checkToggle = title.find("label.select-cont");
                    checkToggle.attr("id", "check-toggle-" + id);
                    checkToggle.attr("data-toggle", "check");
                    checkToggle.attr("check-target", "#check-target-" + id);
                }
                var toggle = $('<a role="button" data-toggle="collapse" href="#' + collapseId + '" aria-expanded="' + that.options.collapse + '" aria-controls="' + collapseId + '"></a>').appendTo(title);
                $('<i class="glyphicon"></i>').appendTo(toggle);
                $('<span>' + name + '</span>').appendTo(toggle);
                if (that.options.link) {
                    toggle.attr("data-parent", "#" + that.store.id);
                }
                $('<div id="' + collapseId + '" class="panel-collapse collapse" role="tabpanel" aria-labelledby="' + panelId + '"><div class="panel-body"></div></div>').appendTo(panel)
                var body = panel.find(".panel-body");
                if (that.options.collapse) {
                    panel.find(".collapse").addClass("in");
                }
                if (check) {
                    body.attr("check-target", "#check-toggle-" + id)
                        .attr("data-type", "check")
                        .attr("id", "check-target-" + id)
                        .addClass("check-content");
                }
                if (typeof that.options.panelParse == 'function') {
                    that.options.panelParse.call(that, items, { "idKey": idKey, "nameKey": nameKey, "content": body, "check": check });
                } else {
                    var group = $('<div class="list-group"></div>').appendTo(body);
                    $.each(items, function (i, n) {
                        var itemId = n[idKey];
                        $('<a data-id="' + itemId + '" href="#" class="list-group-item">' + that.component.check(check, itemId, n[nameKey]) + '</a>').appendTo(group);
                    });
                }
            } else {
                var title = $(that.component.check(check, id, name)).appendTo(heading.find(".panel-title"));
                if (check) {
                    title.find("input[type='checkbox']").attr("data-id", id);
                }
                that.store.dic[id] = data;
            }
        }
    }
    list.prototype.getValue = function (idKey) {
        return this.store.seleted;
    }
    list.prototype.setValue = function (vals) {
        var content = this.component.content;
        content.find("input[type='checkbox']:checked").prop("checked", false);
        var _vals = [];
        if (typeof vals == "srting") {
            _vals.push(vals);
        }
        if (Object.prototype.toString.call(vals) == '[object Array]' && vals.length > 0) {
            _vals = vals;
        }
        var seleted = [];
        $.each(_vals, function (i, id) {
            var check = content.find("input[type='checkbox'][data-id='" + id + "']");
            if (check.length > 0) {
                check.prop("checked", true);
                seleted.push(id);
            }
        });
        this.store.seleted = seleted;
        if (this.options.check) {
            var panels = content.find(".panel");
            $(panels).each(function () {
                var panel = $(this);
                var toggle = panel.find(".panel-title label.select-cont");
                var checkbox = toggle.find("input[type='checkbox']");
                var checks = panel.find(".panel-body input[type='checkbox']");
                var checked = checks.filter(":checked");
                toggle.find(".select-wapper").removeClass("half-checked");
                if (checked.length > 0) {
                    if (checked.length == checks.length) {
                        checkbox.prop("checked", true);
                    } else {
                        toggle.find(".select-wapper").addClass("half-checked");
                        checkbox.prop("checked", false);
                    }
                } else {
                    checkbox.prop("checked", false);
                }
            });
        }
    }
    return list;
}));