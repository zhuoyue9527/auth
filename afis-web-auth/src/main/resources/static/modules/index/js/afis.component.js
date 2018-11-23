(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(["formtype",
            "autocomplate",
            "cascader",
            "select",
            "multiselect",
            "check",
            "date"], factory);
    } else {
        var AFIS = window.afis;
        if (AFIS) {
            AFIS.component = factory(AFIS.formtype,
                AFIS.autocomplate,
                AFIS.cascader,
                AFIS.select,
                AFIS.multiselect,
                AFIS.check,
                AFIS.date);
        }
    }
}(function (Forms,
    autocomplate,
    cascader,
    select,
    multiselect,
    check,
    date) {
    'use strict';
    var TYPES = Forms.prototype.TYPES;

    TYPES.INPUT = new TYPES.Type(TYPES.FormItem);
    TYPES.INPUT.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        //-template--
        var template = function () {
            var template = {};
            template[this.state.disabled] = {
                template: '<input type="text" disabled name="' + this.store.name + '" autocomplete="off" class="form-control">',
                getVal: function () {
                    return this.target.val();
                },
                setVal: function (val) {
                    this.target.val(val);
                }
            };
            template[this.state.common] = {
                template: '<input type="text" name="' + this.store.name + '" autocomplete="off" class="form-control">',
                init: function (form) {
                    var input = $(this.template).appendTo(form.component.content);
                    var props = form.store.option.props;
                    if (props) {
                        $.each(props, function (key, val) {
                            input.attr(key, val);
                        });
                    }
                    return input;
                },
                getVal: function () {
                    return this.target.val();
                },
                setVal: function (val) {
                    this.target.val(val);
                }
            };
            return template;
        }
        $.extend(true, form.stateTemplate, template.call(form));
        this.names[form.store.name] = form.builder();
    }
    TYPES.TEXTAREA = new TYPES.Type(TYPES.FormItem);
    TYPES.TEXTAREA.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        //-template--
        var template = function () {
            var template = {};
            template[this.state.disabled] = {
                template: '<textarea disabled name="' + this.store.name + '" autocomplete="off" class="form-control"></textarea>',
                getVal: function () {
                    return this.target.val();
                },
                setVal: function (val) {
                    this.target.val(val);
                }
            };
            template[this.state.common] = {
                template: '<textarea name="' + this.store.name + '" autocomplete="off" class="form-control"></textarea>',
                getVal: function () {
                    return this.target.val();
                },
                setVal: function (val) {
                    this.target.val(val);
                }
            };
            return template;
        }
        $.extend(form.stateTemplate, template.call(form));
        this.names[form.store.name] = form.builder();
    }
    TYPES.DATE = new TYPES.Type(TYPES.FormItem);
    TYPES.DATE.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        var template = function () {
            var template = {};
            template[this.state.common] = {
                getVal: function (option) {
                    return this.target.getValue(option);
                },
                init: function (form) {
                    var target = null;
                    var content = form.component.content;
                    var option = form.store.option;
                    target = new date(content, option);
                    return target;
                },
                setVal: function (val) {
                    if (val == "") {
                        this.target.reset();
                    } else {
                        this.target.setValue(val);
                    }
                }
            };
            return template;
        }
        $.extend(form.stateTemplate, template.call(form));
        this.names[form.store.name] = form.builder();
    }
    TYPES.CHECKBOX = new TYPES.Type(TYPES.FormItem);
    TYPES.CHECKBOX.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        var template = function () {
            var template = {};
            template[this.state.common] = {
                getVal: function () {
                    return this.target.getValue(true);
                },
                init: function (form) {
                    var option = form.store.option,
                        content = form.component.content,
                        target = null;
                    content.attr("data-type", "check").addClass("check-content");
                    target = new check(content, option);
                    target.setList(option.data);
                    return target;
                },
                setVal: function (val) {
                    this.target.setValue(val);
                }
            };
            return template;
        }
        $.extend(form.stateTemplate, template.call(form));
        this.names[form.store.name] = form.builder();
    };
    TYPES.RADIO = new TYPES.Type(TYPES.FormItem);
    TYPES.RADIO.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        var template = function () {
            var template = {};
            template[this.state.common] = {
                getVal: function () {
                    return this.target.getValue(true);
                },
                init: function (form) {
                    var option = form.store.option,
                        content = form.component.content,
                        target = null;
                    content.attr("data-type", "radio").addClass("check-content");
                    option.type = "radio";
                    target = new check(content, option);
                    if (typeof option.loadList == 'function') {
                        target.load(option.def, option.loadListed);
                    } else {
                        target.setList(option.data);
                    }
                    return target;
                },
                setVal: function (val) {
                    this.target.setValue(val);
                }
            };
            return template;
        }
        $.extend(form.stateTemplate, template.call(form));
        this.names[form.store.name] = form.builder();
    };
    TYPES.RANGEDATE = new TYPES.Type(TYPES.FormItem);
    TYPES.RANGEDATE.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        form.changeState = function (state) {
            if (state != null && state != this.store.state) {
                var names = this.store.name;
                var val = {
                    "start": this.getVal(names.start),
                    "end": this.getVal(names.end)
                };
                this.component.content.children().remove();
                this.target = null;
                this.store.state = state;
                this.builder(state);
                this.setVal(val);
            }
        }
        var template = function () {
            var template = {};
            template[this.state.common] = {
                getVal: function () {
                    var name = this.data.name;
                    return this.target.getValue(name);
                },
                init: function (form) {
                    var target = null;
                    var content = form.component.content;
                    var option = $.extend(true, {}, form.store.option);
                    option["range"] = true;
                    var name = form.store.name;
                    if (typeof name === 'string') {
                        option["name"] = name;
                    } else {
                        option["name"] = $.extend({}, name);
                    }
                    target = new date(content, option);
                    return target;
                },
                setVal: function (val) {
                    var name = this.data.name;
                    if (val == "") {
                        this.target.reset(name);
                    } else {
                        this.target.setValue(name, val);
                    }
                }
            };
            return template;
        }
        $.extend(form.stateTemplate, template.call(form));
        var id = form.store.name.start + "_" + form.store.name.end + "_" + new Date().getTime();
        this.names[id] = form.builder();
        this.names[form.store.name.start] = this.names[form.store.name.end] = id;
    }
    TYPES.RANGEDATE.prototype.get = function (name) {
        var id = this.names[name];
        if (id) {
            var form = this.names[id];
            form.data.name = name;
            return form;
        }
        return null;
    }
    TYPES.RANGEDATE.prototype.extendNames = function (name, item) {
        var names = {};
        names[name.start] = item;
        names[name.end] = item;
        return names;
    }
    TYPES.SELECT = new TYPES.Type(TYPES.FormItem);
    TYPES.SELECT.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        var template = function () {
            var template = {};
            template[this.state.common] = {
                getVal: function () {
                    return this.target.getValue(true);
                },
                init: function (form) {
                    var option = form.store.option;
                    var content = form.component.content;
                    var target = new select(content, option);
                    var list = option.data;
                    target.setList(list, option.def);
                    return target;
                },
                setVal: function (val) {
                    this.target.setValue(val);
                }
            };
            return template;
        }
        $.extend(form.stateTemplate, template.call(form));
        this.names[form.store.name] = form.builder();
    }
    TYPES.MULTISELECT = new TYPES.Type(TYPES.FormItem);
    TYPES.MULTISELECT.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        var template = function () {
            var template = {};
            template[this.state.common] = {
                getVal: function () {
                    return this.target.getValue(true);
                },
                init: function (form) {
                    var option = form.store.option;
                    var target = new multiselect(form.component.content, option);
                    var list = option.data;
                    target.addList(list);
                    return target;
                },
                setVal: function (val) {
                    this.target.setValue(val);
                }
            };
            return template;
        }
        $.extend(form.stateTemplate, template.call(form));
        this.names[form.store.name] = form.builder();
    }
    TYPES.AUTOCOMPLATESELECT = new TYPES.Type(TYPES.FormItem);
    TYPES.AUTOCOMPLATESELECT.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        var template = function () {
            var template = {};
            template[this.state.common] = {
                getVal: function () {
                    return this.target.getValue(true);
                },
                init: function (form) {
                    var target = null;
                    var content = form.component.content;
                    var option = form.store.option;
                    target = new autocomplate(content, option);
                    return target;
                },
                setVal: function (val) {
                    this.target.setValue(val);
                }
            };
            return template;
        }
        $.extend(form.stateTemplate, template.call(form));
        this.names[form.store.name] = form.builder();
    }
    TYPES.CASCADERSELECT = new TYPES.Type(TYPES.FormItem);
    TYPES.CASCADERSELECT.prototype.builder = function (container, name, prompt, opt) {
        var form = new this.FormItem(this.template, this.option).initialize(container, name, prompt, opt);
        var template = function () {
            var template = {};
            template[this.state.common] = {
                getVal: function () {
                    return this.target.getValue(true);
                },
                init: function (form) {
                    var target = null;
                    var content = form.component.content;
                    var option = form.store.option;
                    target = new cascader(content, option);
                    return target;
                },
                setVal: function (val) {
                    this.target.setValue(val);
                }
            };
            return template;
        }
        $.extend(form.stateTemplate, template.call(form));
        this.names[form.store.name] = form.builder();
    }
    return Forms;
}));