(function (factory) {
    if (typeof define === "function" && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (AFIS == null) {
            AFIS = window.afis = {};
        }
        AFIS.formitem = factory();
    }
}(function () {
    function FormItem(template, option) {
        this.option = option;
        this.component = {
            container: template.form,
            title: template.title,
            content: template.content,
            prefix: null,
            suffix: null,
            info: null,
            error: null,
            extend: template.extend
        };
        this.store = {
            option: {}
        };
        this.stateTemplate = {};
        this.data = {};
    }
    FormItem.prototype.state = {
        common: 1,
        readonly: -1,
        disabled: 0
    };
    FormItem.prototype.initialize = function (container, name, prompt, option) {
        var form = $(this.component.container).appendTo(container);
        var title = null;
        if (prompt) {
            title = $(this.component.title).appendTo(form);
            $('<label class="control-label"></label>').appendTo(title).text(prompt);
        }
        var content = $(this.component.content).appendTo(form);
        var prefix = content.find(".prefix");
        if (option.prefix) {
            prefix.html(option.prefix);
        }
        var suffix = content.find(".suffix");
        if (typeof option.suffix == 'boolean' && !option.suffix) {
            suffix.remove();
            suffix = null;
        } else {
            if (option.suffix != null) {
                suffix.html(option.suffix);
            }
        }
        if (option.isFill != null) {
            $('<i class="star">*</i>').appendTo(suffix);
        }
        var info = content.find(".info");
        if (option.info) {
            info.html(option.info);
        }
        var error = content.find(".error");
        if (option.error) {
            error.html(option.error);
        }
        var extend = $(this.component.extend).appendTo(form);
        if (option.extend) {
            extend.html(option.extend);
        }
        this.defStateTemplate = {
            template: '<span name="' + name + '"></span>',
            getVal: function () {
                return this.target.text();
            },
            setVal: function (val) {
                this.target.text(val);
            }
        };
        if (typeof option.initialize == 'function') {
            option.initialize.call(this, form);
        }
        this.stateTemplate[this.state.readonly] = this.defStateTemplate;
        this.component = {
            container: form,
            title: title,
            content: content.find(".content"),
            prefix: prefix,
            suffix: suffix,
            info: info,
            error: error,
            extend: extend
        };
        var state = this.state[option.state] != null ? this.state[option.state] : this.state.common;
        this.store = {
            container: container,
            prompt: prompt,
            option: option,
            name: name,
            state: state
        }
        return this;
    }
    FormItem.prototype.changeState = function (state) {
        if (state != null && state != this.store.state) {
            var val = this.val();
            this.component.content.children().remove();
            this.target = null;
            this.store.state = state;
            this.builder(state);
            this.setVal(val);
        }
    };
    FormItem.prototype.builder = function (state) {
        if (this.target == null) {
            var stateTemplate = this.stateTemplate[this.store.state];
            if (!stateTemplate) {
                stateTemplate = this.defStateTemplate;
            }
            if (typeof stateTemplate.init == 'function') {
                this.target = stateTemplate.init(this);
                if (this.target == null) {
                    this.target = $(stateTemplate.template).appendTo(this.component.content);
                }
            } else {
                this.target = $(stateTemplate.template).appendTo(this.component.content);
            }
        }
        if (this.val == null) {
            //-val--
            this.setVal = function (val) {
                this.stateTemplate[this.store.state].setVal.call(this, val);
            }
            this.getVal = function (option) {
                return this.stateTemplate[this.store.state].getVal.call(this, option);
            }
            this.val = function (val) {
                if (val == null) {
                    return this.getVal();
                } else {
                    this.setVal(val);
                }
            }
        }
        if (state == null) {
            this.setVal(this.store.option.def);
        }
        return this;
    };
    FormItem.prototype.reset = function (isEmpty) {
        if (typeof isEmpty == 'boolean' && isEmpty) {
            this.val("");
        } else {
            var def = this.store.option.def;
            this.val(def ? def : "");
        }
    };
    return FormItem;
}))