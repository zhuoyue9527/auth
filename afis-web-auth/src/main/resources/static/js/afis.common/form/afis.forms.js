(function (factory) {
    if (typeof define === "function" && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (AFIS == null) {
            AFIS = window.afis = {};
        }
        AFIS.forms = factory();
    }
}(function () {
    var Forms = function (element, options) {
        this.content = element;
        this.options = $.extend(true, {}, Forms.DEFULATS, options);
        this.names = {};//表单数据
        this.instances = {};//表单实例
        this.forms = [];
        this.setConfig();
        return this;
    };
    Forms.VERSION = "1.0.0";
    Forms.DEFULATS = {
        fills: []
    };
    Forms.prototype.TYPES = {};
    //全局参数处理
    Forms.prototype.setConfig = function () {
        var that = this;
        that.fills = {};
        $.each(that.options.fills, function (i, n) {
            if (typeof n == 'string') {
                that.fills[n] = true;
            } else {
                that.fills[n.name] = n;
            }
        });
    }
    //获取某个表单的对象    
    Forms.prototype.get = function (name) {
        var instance = this.getInstance(name);
        if (!instance) {
            return null;
        }
        return instance.get(name);
    }
    //初始化组件设置
    Forms.prototype.getType = function (type) {
        var that = this;
        if (typeof that.TYPES.getType == 'function') {
            return that.TYPES.getType(type);
        } else {
            var options = {
                template: that.TYPES.template,
                option: {}
            };
            return new type(options);
        }
    }
    //获取表单类型的实例
    Forms.prototype.getInstance = function (param) {
        var that = this;
        var type = that.names[param] ? that.names[param].type : param;
        var TYPE = that.TYPES[type];
        if (!TYPE) {
            return null;
        } else {
            if (!that.instances[type]) {
                that.instances[type] = that.getType(TYPE);
            }
            return that.instances[type];
        }
    }
    //获取表单的值
    Forms.prototype.getValue = function (name) {
        var form = this.get(name);
        if (form && form.val) {
            return form.val();
        } else {
            return null;
        }
    }
    Forms.prototype.getValues = function (names) {
        var vals = {}, that = this;
        if (names != null) {
            if (Object.prototype.toString.call(names) == '[object Array]' && names.length > 0) {
                $.each(names, function (i, name) {
                    vals[name] = that.getValue(name);
                });
            } else if (typeof names == 'string') {
                vals[names] = that.getValue(names);
            }
        } else {
            $.each(that.names, function (name, n) {
                vals[name] = that.instances[n.type].get(name).val();//无需判断
            });
        }
        return vals;
    }
    //设置表单的值
    Forms.prototype.setValue = function (name, val) {
        var form = this.get(name);
        if (form && form.val) {
            form.val(val);
        }
    }
    Forms.prototype.setValues = function (values) {
        if (values != null) {
            var that = this;
            $.each(values, function (name, val) {
                that.setValue(name, val);
            });
        }
    }
    //调用方法
    Forms.prototype.mothed = function (mothed, name, option) {
        var instance = this.getInstance(name);
        if (instance && instance[mothed]) {
            instance[mothed](name, option);
        }
    }
    //新增表单
    Forms.prototype.addForm = function (type, name, prompt, option) {
        this.forms.push({
            type: type,
            name: name,
            prompt: prompt,
            option: option
        });
        return this;
    }
    Forms.prototype.builder = function (func) {
        var that = this;
        var forms = that.forms;
        if (typeof func == 'function') {
            $.each(forms, function (i, form) {
                var opt = func.call(that, form, i);
                if (opt) {
                    that.add(opt);
                }
            });
        } else {
            $.each(forms, function (i, form) {
                that.add(form);
            });
        }
        that.forms = [];
        return that;
    }
    //单个表单参数处理
    Forms.prototype.setOption = function (name, option) {
        var opt = {};
        if (option != null) {
            if (Object.prototype.toString.call(option) == '[object Array]') {
                opt["data"] = option;
            } else if (typeof option == 'object') {
                opt = option;
            } else {
                opt["def"] = option;
            }
        }
        opt["isFill"] = this.fills[name];
        return opt;
    }
    Forms.prototype.add = function (content, type, name, prompt, option) {
        var cont = null;
        if (type == null && typeof content == 'object') {
            cont = content.content || this.content; type = content.type; name = content.name; prompt = content.prompt; option = content.option;
        } else {
            if (name == null) {
                option = type.option; prompt = type.prompt; name = type.name; type = type.type;
                if (content) {
                    cont = content;
                } else {
                    cont = option.content;
                }
                if (cont == null) {
                    cont = this.content;
                }
            } else if (typeof content == 'string') {
                cont = this.content; option = prompt; prompt = name; name = type; type = content;
            }
        }
        if (cont == null) {
            return this;
        }
        //重复添加只会添加第一个
        if (this.names[name] != null) {
            return this;
        }
        var instance = this.getInstance(type);
        if (instance) {
            option = this.setOption(name, option);
            if (typeof name == 'string') {
                this.names[name] = {
                    type: type,
                    prompt: prompt,
                    option: option
                };
                instance.builder(cont, name, prompt, option);
            } else {
                if (typeof instance.extendNames == 'function') {
                    $.extend(true, this.names, instance.extendNames(name, {
                        type: type,
                        prompt: prompt,
                        option: option
                    }));
                    instance.builder(cont, name, prompt, option);
                }
            }
        }
        return this;
    }
    //-常用操作-
    Forms.prototype.set = function (name, option) {
        this.mothed("set", name, option);
    }
    //翻改表单
    Forms.prototype.refash = function (name, option) {
        this.mothed("refash", name, option);
    }
    //还原表单
    Forms.prototype.restore = function (name) {
        this.mothed("restore", name);
    }
    //只读表单
    Forms.prototype.readonly = function (name) {
        this.mothed("readonly", name);
    }
    //禁用表单
    Forms.prototype.disable = function (name) {
        this.mothed("disable", name);
    }
    //重置表单中的值
    Forms.prototype.reset = function () {
        var that = this;
        $.each(that.names, function (name) {
            that.mothed("reset", name, true);
        });
    }
    return Forms;
}));