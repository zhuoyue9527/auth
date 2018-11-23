(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (AFIS == null) {
            AFIS = window.afis = {};
        }
        AFIS.date = factory();
    }
}(function () {
    var date = function (content, options) {
        this.options = $.extend(true, {
            range: false,
            name: null,
            datepicker: {
                language: "zh-CN",
                todayHighlight: true,
                todayBtn: "linked",
                autoclose: true,
                format: "yyyy-mm-dd",
                clearBtn: true
            }
        }, options);
        this.component = {
            content: content
        };
        this.setOptions();
        this.template();
    }
    date.prototype.setOptions = function () {
        var options = this.options;
        var range = typeof options.range == 'boolean' && options.range;
        this.store = {
            range: range
        };
    }
    date.prototype.template = function () {
        var that = this,
            options = this.options,
            range = this.store.range,
            datepicker = null,
            content = this.component.content,
            id = "datepicker" + new Date().getTime(),
            name = options.name;
        if (range) {
            var start = "start_" + id, end = "end_" + id;
            if (name != null) {
                if (typeof name == 'object') {
                    start = name.start;
                    end = name.end;
                } else if (typeof name == 'string') {
                    start = "start_" + name;
                    end = "end_" + name;
                }
            }
            var names = {};
            names[start] = 0;
            names[end] = 1;
            that.store["name"] = names;

            datepicker = $('<div class="input-daterange input-group">' +
                '<input type="text" class="form-control" name="' + start + '" />' +
                '<span class="input-group-addon">-</span>' +
                '<input type="text" class="form-control" name="' + end + '" />' +
                '</div>').appendTo(content);
        } else {
            if (name == null) {
                name = id;
            }
            that.store.name = name;
            datepicker = $('<div class="input-group date">' +
                '<input type="text" name="' + name + '" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>' +
                '</div>').appendTo(content);
        }
        datepicker.datepicker(that.options.datepicker);
        $.extend(that.component, {
            datepicker: datepicker
        });
    }
    date.prototype.getDatepicker = function () {
        return this.component.datepicker.data("datepicker");
    }
    date.prototype.reset = function (name) {
        var datepicker = this.getDatepicker();
        if (this.store.range) {
            var index = name == null ? null : this.store.name[name];
            if (index != null) {
                datepicker.pickers[index].clearDates();
            } else {
                datepicker.pickers[0].clearDates();
                datepicker.pickers[1].clearDates();
            }
        } else {
            datepicker.clearDates();
        }
    }
    date.prototype.setValue = function (name, val) {
        var datepicker = this.getDatepicker();
        if (this.store.range) {
            if (val == null) {
                val = name;
                name = null;
            }
            var index = name == null ? null : this.store.name[name];
            if (index != null) {
                datepicker.pickers[index].setDate(val);
            } else {
                datepicker.pickers[0].setDate(val);
                datepicker.pickers[1].setDate(val);
            }
        } else {
            if (val == null) {
                val = name;
            }
            datepicker.setDate(val);
        }
    }
    date.prototype.getValue = function (name) {
        var datepicker = this.getDatepicker();
        if (this.store.range) {
            var pickers = datepicker.pickers;
            if (name == null) {
                return [
                    pickers[0].getFormattedDate(),
                    pickers[1].getFormattedDate()
                ].join("-");
            }
            var index = this.store.name[name];
            if (index == null) {
                return [
                    pickers[0].getFormattedDate(),
                    pickers[1].getFormattedDate()
                ].join("-");
            } else {
                return pickers[index].getFormattedDate();
            }
        } else {
            return datepicker.getFormattedDate();
        }
    }
    return date;
}));