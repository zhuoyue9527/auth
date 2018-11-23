(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(["formitem", "forms"], factory);
    } else {
        var AFIS = window.afis;
        if (AFIS) {
            AFIS.formtype = factory(AFIS.formitem, AFIS.forms);
        }
    }
}(function (FormItem, Forms) {
    'use strict';
    var TYPES = {
        template: {
            form: '<div class="afis-form"></form>',
            title: '<div class="form-part title"></div>',
            content: '<div class="form-part content-wapper">' +
                '<div class="content-item content-main"><div class="prefix"></div><div class="content"></div><div class="suffix"></div></div>' +
                '<div class="content-item info"></div>' +
                '<div class="content-item error"></div>' +
                '</div>',
            extend: '<div class="form-part extend"></div>'
        },
        FormItem: FormItem,
        getType: function (type) {
            var options = {
                template: this.template,
                option: {}
            };
            return new type(options);
        },
        Type: function (FormItem) {
            var type = function (options) {
                this.template = options.template;
                this.option = options.option;
                this.FormItem = FormItem;
                this.names = {};
            };
            type.prototype.get = function (name) {
                return this.names[name];
            }
            type.prototype.mothed = function (mothed, name, option) {
                var form = this.get(name);
                if (form && form[mothed]) {
                    return form[mothed](option);
                } else {
                    return null;
                }
            }
            type.prototype.reset = function (name, isEmpty) {
                this.mothed("reset", name, isEmpty);
            }
            type.prototype.refash = function (name, option) {
                this.mothed("refash", name, option);
            }
            type.prototype.changeState = function (name, state) {
                this.mothed("changeState", name, state);
            }
            type.prototype.readonly = function (name) {
                this.changeState(name, -1);
            }
            type.prototype.disable = function (name) {
                this.changeState(name, 0);
            }
            type.prototype.restore = function (name) {
                this.changeState(name, 1);
            }
            return type;
        }
    };
    $.extend(true, Forms.prototype.TYPES, TYPES);
    return Forms;
}));