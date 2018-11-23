//数据表*列头的排序与显隐
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(["dragsort"], factory);
    } else {
        var AFIS = window.afis;
        if (AFIS) {
            AFIS.tablesort = factory(AFIS.dragsort);
        }
    }
}(function () {
    'use strict';
    function TableSort(table, cols, options) {
        this.options = $.extend({}, this.defaults, options || {});
        this.options.table = table;
        this.options.cols = (cols == null ? [] : cols);

        var template = this.options.template;
        this.colset = $(template.colset);
        this.edit = $(template.coledit).appendTo(this.colset);
        this.edit.attr("table-name", this.options.table);
        this.body = $(template.body).appendTo(this.edit);
    }
    TableSort.prototype = {
        constructor: TableSort,
        defaults: {
            dragSelector: "a",
            placeHolderTemplate: "<li class='placeHolder'><a></a></li>",
            dragBetween: true,
            table: "",
            name: "name",
            alias: "alias",
            hidden: "hidden",
            dragEnd: function () { },
            cols: [],//{name:"",alias:"",hidden:false/true}
            template: {
                colset: '<div class="table-cols-set"></div>',
                coledit: '<div class="table-cols-edit"></div>',
                body: '<nav class="navbar"><ul class="nav navbar-nav sort-body"></ul></nav>',
                item: '<li class="col-name">' +
                    '<i class="glyphicon col-trigger"></i>' +
                    '<a href="#"><span class="col-alias"></span></a>' +
                    '</li>'
            }
        },
        builder: function () {
            var options = this.options;
            var cols = options.cols;
            var body = this.body.find('.sort-body');
            var item = options.template.item;
            $.each(cols, function (i, n) {
                var _item = $(item).appendTo(body);
                _item.attr("col-name", n[options.name]);
                _item.find('.col-alias').text(n[options.alias]);
                var hidden = n[options.hidden];
                if (typeof hidden === 'boolean' && hidden) {
                    _item.addClass("col-hidden");
                }
            });
            body.on("click", '.col-trigger', function () {
                var col = $(this).parent();
                if (col.hasClass('col-hidden')) {
                    col.removeClass('col-hidden');
                } else {
                    col.addClass('col-hidden');
                }
            });
            body.dragsort({
                dragSelector: options.dragSelector,
                dragBetween: options.dragBetween,
                dragEnd: options.dragEnd,
                placeHolderTemplate: options.placeHolderTemplate
            });
        },
        add: function (name, alias, hidden) {
            var col = {};
            var options = this.options;
            col[options.name] = name;
            col[options.alias] = alias;
            col[options.hidden] = hidden;
            options.cols.push(col);
            return this;
        },
        get: function () {
            var cols = [];
            var items = this.body.find('.sort-body .col-name');
            $(items).each(function (i, item) {
                var _item = $(item);
                if (!_item.hasClass("col-hidden")) {
                    cols.push(_item.attr("col-name"));
                }
            });
            return cols;
        }
    };
    return TableSort;
}));