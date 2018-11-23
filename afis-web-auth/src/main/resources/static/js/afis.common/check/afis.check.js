(function (factory) {
    if (typeof define == 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (!AFIS) {
            AFIS = window.afis = {};
        }
        AFIS.check = factory();
    }
}(function () {
    var Check = function (element, options) {
        this.options = $.extend({}, Check.DEFAULTS, options);
        this.component = {
            content: element
        };
        this.store = {
            dic: {},
            selected: null
        };
        this.setOptions();
        this.template();
        if (this.options.toggle) {
            this.toggle();
        }
    }
    Check.DEFAULTS = {
        toggle: true,
        type: "checkbox",
        name: null,
        idKey: "value",
        nameKey: "text",
        parent: null
    };
    Check.prototype.setOptions = function () {
        var options = this.options,
            component = this.component;

        var type = options.type;
        if (!(typeof type === 'string' && /checkbox|radio/.test(type))) {
            type = "checkbox";
        }
        component.type = type;
        if (type === 'radio') {
            component.name = options.name ? options.name : "radio" + new Date().getTime();
        }
    }
    Check.prototype.template = function () {
        var content = this.component.content;
        if (!content.hasClass("check-content")) {
            if (this.component.type == "checkbox") {
                content.attr("data-type", "check").addClass("check-content");
            } else {
                content.attr("data-type", "radio").addClass("check-content");
            }
        }
        $.extend(this.component, {
            check: function (text) {
                return '<label class="select-cont"><div class="select-wapper">' +
                    (this.type === 'radio' ? '<input type="radio" name="' + this.name + '">' : '<input type="checkbox">') +
                    '<span class="select-v"></span></div><span class="select-txt">' + text + '</span></label>';
            }
        });
        content.data("afis.check", this);
    }
    Check.prototype.add = function (data, opt) {
        var content = opt.content;
        var id = data[opt.idKey];
        var name = data[opt.nameKey];
        var check = $(this.component.check(name)).appendTo(content);
        check.find("input[type]").attr("data-id", id);
        return check;
    }
    Check.prototype.addList = function (list, callback, builder) {
        var that = this;
        var content = that.remove();
        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            var opt = {
                idKey: that.options.idKey,
                nameKey: that.options.nameKey,
                content: content
            };
            if (typeof builder == 'function') {
                $.each(list, function (i, n) {
                    var params = builder.call(that, n, opt);
                    that.add(params.n, params.opt);
                });
            } else {
                $.each(list, function (i, n) {
                    that.add(n, opt);
                });
            }
            if (typeof callback == 'function') {
                callback.call(that);
            }
        }
    }
    Check.prototype.setList = function (list, def, builder) {
        this.addList(list, function () {
            this.setValue(def);
        }, builder);
    }
    Check.prototype.setValue = function (val) {
        if (val == null) {
            return;
        }
        var content = this.component.content;
        if (this.component.type == 'checkbox') {
            var selected = [];
            if (Object.prototype.toString.call(val) == '[object Array]' && val.length > 0) {
                selected = val;
            } else if (typeof val == 'string' || typeof val == 'number') {
                selected = [val];
            }
            $.each(selected, function (i, n) {
                var check = content.find("input[type='checkbox'][data-id='" + n + "']");
                if (check.length > 0) {
                    check.prop("checked", true);
                }
            });
        } else {
            var radio = content.find("input[type='radio'][data-id='" + val + "']");
            if (radio.length > 0) {
                radio.prop("checked", true);
            }
        }
        this.toggle();
    }
    Check.prototype.getValue = function (idKey) {
        return this.store.selected;
    }
    Check.prototype.builder = function (list, func) {

    }
    Check.prototype.toggle = function () {
        var content = this.component.content,
            selected = [],
            checkeds = null;
        if (this.component.type == "checkbox") {
            var target = content.attr("check-target");
            if (target) {
                var toggle = null, checks = null;
                toggle = content.find(target);
                if (toggle.length > 0) {
                    checks = toggle.siblings().find("input[type='checkbox']");
                } else {
                    toggle = $(target);
                    checks = content.find("input[type='checkbox']");
                }
                toggle.find(".half-checked").removeClass("half-checked");
                checkeds = checks.filter(":checked");
                var len = checkeds.length;
                if (len > 0) {
                    if (checks.length != checkeds.length) {
                        toggle.find("input[type='checkbox']").prop("checked", false);
                        toggle.find(".select-wapper").addClass("half-checked");
                    } else {
                        toggle.find("input[type='checkbox']").prop("checked", true);
                    }
                } else {
                    toggle.find("input[type='checkbox']").prop("checked", false);
                }
            } else {
                checkeds = content.find("input[type='checkbox']:checked");
            }
            $(checkeds).each(function () {
                var id = $(this).attr("data-id");
                if (id != null) {
                    selected.push(id);
                }
            });
        } else {
            selected = content.find("input[type='radio']:checked").attr("data-id");
        }
        this.store.selected = selected;
    }
    Check.prototype.reset = function () {
        this.component.content.find("input:checked").prop("checked", false);
        this.toggle();
    }
    Check.prototype.remove = function () {
        var content = this.component.content;
        content.find(".select-cont").remove();
        this.store.dic = {};
        this.store.selected = null;
        return content;
    }
    function Plugin(option) {
        return this.each(function () {
            var content = $(this);
            var data = content.data('afis.check');
            var options = $.extend({}, Check.DEFAULTS, content.data(), typeof option == 'object' && option);
            if (!data) {
                data = new Check(content, options);
                content.data('afis.check', data);
            } else {
                if (typeof option == 'string') {
                    data[option]();
                }
            }
        });
    }
    //CHECK DATA-API
    //==============
    $(document).on('change.afis.check.data-api', '.afis [data-toggle="check"] input[type="checkbox"]', function (e) {
        var toggle = $(this).parents('[data-toggle="check"]');
        toggle.find(".half-checked").removeClass("half-checked");
        var content = $(toggle.attr("check-target"));
        content.find('input[type="checkbox"]').prop("checked", $(this).is(":checked"));
        var data = content.data('afis.check');
        var option = data ? "toggle" : toggle.data();
        Plugin.call(content, option);
    });
    $(document).on('change.afis.check.data-api', '.afis [data-type="check"].check-content input[type="checkbox"]', function (e) {
        var content = $(this).parents('[data-type="check"].check-content');
        var data = content.data('afis.check');
        var option = null;
        if (data) {
            option = "toggle";
        } else {
            var target = $(content.attr("check-target"));
            if (target.length > 0) {
                option = target.data();
            }
        }
        Plugin.call(content, option);
    });
    $(document).on('change.afis.check.data-api', '.afis [data-type="radio"].check-content input[type="radio"]', function (e) {
        var content = $(this).parents('[data-type="radio"].check-content');
        var data = content.data('afis.check');
        var option = null;
        if (data) {
            option = "toggle";
        } else {
            option = content.data();
        }
        Plugin.call(content, option);
    });
    return Check;
}));