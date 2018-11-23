(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(["tablesort", "afismq", "popup"], factory);
    } else {
        var AFIS = window.afis;
        if (AFIS) {
            AFIS.table = factory(AFIS.tablesort, AFIS.afismq, AFIS.popup);
        }
    }
}(function (tablesort, afismq, popup) {
    'use strict'
    var AfisTable = function () {
        this.index = 1;
        this.columns = {};
        this.columnNames = {};
        this.table = null;
        this.unsort = null;
        this.popup = new popup();
        this.buttons = [];
    };
    AfisTable.prototype = {
        addColumn: function (name, alis, format, width, type) {
            var that = this;
            var column = {
                name: name,
                type: type == null ? "string" : type,
                index: that.index++,
                friendly: alis,
                format: format,
                width: width
            };
            this.columns[name] = column;
            return this;
        },
        editColumns: function (cols) {//[object Array] {name:"",alias:""}
            var that = this;
            if (cols != null && Object.prototype.toString.call(cols) == '[object Array]' && cols.length > 0) {
                var dic = {};
                $.each(cols, function (i, name) {
                    dic[name] = i + 1;
                });
                $.each(that.columns, function (name, column) {
                    var index = dic[name];
                    if (!index) {
                        column.index = index;
                        column["hidden"] = true;
                        index = index + 1;
                    } else {
                        column.index = index;
                        column["hidden"] = false;
                    }
                });
            }
        },
        setColumns: function (columns) {
            if (this.table) {
                this.table.setColumns(columns);
            }
        },
        getColumns: function () {
            var columns = [];
            $.each(this.columns, function (name, column) {
                columns.push(column);
            })
            return columns;
        },
        builder: function (opts) {
            if (!$.fn.WATable) {
                return null;
            }
            var that = this, options = {};
            this.options = options = $.extend(true, {}, that.DEFAULTS, opts);
            var unsort = that.unsort;
            if (Object.prototype.toString.call(unsort) != '[object Array]') {
                unsort = [];
            }
            var table_options = options.table_options;
            if (table_options.messageType) {
                table_options["load"] = function (url, params, success, error, common) {
                    afismq.query(url, params, success, error, common);
                }
            }
            if (unsort.length > 0) {
                $(unsort).each(function (i, name) {
                    var column = that.columns[name];
                    if (column) {
                        column["sorting"] = false;
                    }
                })
            }
            table_options["columns"] = this.columns;
            var button = options.button;
            if (button) {
                button.children().remove();
                $.each(that.buttons, function (i, btn) {
                    that.button(button, btn.text, btn.func, btn.style);
                });
            }
            var container = options.container;
            var table = $(container).WATable(table_options);
            if (table_options.onInit) {
                table_options.onInit();
            }
            this.table = table.data("WATable");
        },
        query: function (params, callback) {
            if (this.table) {
                if (params == null) {
                    params = {};
                }
                this.table.fetchParam(params, callback);
            }
        },
        setData: function (rows) {
            if (this.table) {
                this.table.setData(rows);
            }
        },
        setRows: function (rows, isAddRow) {
            if (this.table) {
                this.table.setRows(rows, isAddRow);
            }
        },
        addRows: function (rows) {
            if (this.table) {
                this.table.addRows(rows);
            }
        },
        getRow: function (tr) {
            if (this.table) {
                return this.table.getRow(tr);
            }
            return null;
        },
        getData: function () {
            if (this.table) {
                return this.table.getData();
            } else {
                return [];
            }
        },
        getSelectedRows: function () {
            if (this.table) {
                return this.table.getSelectedRows();
            } else {
                return [];
            }
        }
    };
    AfisTable.prototype.DEFAULTS = {
        template: {
            button: '<button type="button" class="btn"></button>'
        },
        container: false,                   //表格容器div
        button: null,                       //按钮容器div
        table_options: {                    //表格参数
            messageType: false,             //消息类型
            remoteModule: false,            //服务模块
            debug: false,                   //是否开启debug模式，degbug模式会打印日志
            pageSize: 15,                   //一页的条数
            sorting: false,                  //是否开启排序
            sortEmptyLast: true,            //是否将空值排到最后 (本地排序使用)
            columnPicker: true,             //是否显示列选择器
            pageSizes: [15, 20, 50, 100],   //可选页数
            hidePagerOnEmpty: true,         //无数据时不显示分页条
            checkboxes: false,              //是否显示选择框，需要指定唯一列（使用unique属性）
            checkAllToggle: true,           //是否显示‘全选’选项
            preFill: true,                  //数据不足时是否补足条数，当数据不足时，补足行数与的页记录数相同
            columns: false,                 //列模块
            showPager: true,                 //显示页码
            onPostFail: null
        }
    };
    AfisTable.prototype.addBtn = function (text, func, style) {
        var btn = {
            text: text,
            style: style,
            func: func
        }
        this.buttons.push(btn);
        return this;
    };
    AfisTable.prototype.button = function (content, text, func, style) {
        var container = content != null ? content : this.options.button;
        if (container == null) {
            return null;
        }
        if (text == null || text.length == 0) {
            return null;
        }
        var button = $(this.options.template.button).appendTo(container).text(text);
        if (style == null) {
            button.addClass("btn-primary");
        } else {
            button.addClass(style);
        }
        if (typeof func == 'function') {
            button.click(func);
        }
        return button;
    };
    AfisTable.prototype.addNumberColumn = function (name, alis, format, width) {
        return this.addColumn(name, alis, format, width, "number");
    };
    AfisTable.prototype.addDateColumn = function (name, alis, format, width) {
        return this.addColumn(name, alis, format, width, "date");
    };
    AfisTable.prototype.addBooleanColumn = function (name, alis, format, width) {
        return this.addColumn(name, alis, format, width, "bool");
    };
    AfisTable.prototype.fetchTable = function (container, options, button, page) {
        var that = this;
        var cols = [];
        if (page == null && typeof button == 'string') {
            page = button;
            button = null;
        }
        var opts = {
            container: container,
            button: button,
            table_options: options || {}
        }
        if (page) {
            opts.table_options["onColumnPicker"] = function () {
                that.tablesort(tableId, {
                    colset: cols,
                    original: that.getColumns().map(function (n) {
                        return { name: n.name, alias: n.friendly };
                    })
                }, function (columns, modal) {
                    if (columns.length > 0) {
                        afismq.post("/auth/admin/UserTableConfig", { uid: tableId, columns: columns.join(",") }, function (data) {
                            modal.success(function () {
                                that.setColumns(columns);
                                cols = columns;
                                modal.close();
                            });
                        }, function () {
                            modal.warning("操作失败!", function () {
                                modal.close();
                            });
                        });
                    } else {
                        return false;
                    }
                });
            }
            var messageType = opts.table_options.messageType;
            if (messageType) {
                messageType = messageType.replace(/\//g, "_");
            } else {
                messageType = "";
            }
            var tableId = page + "_" + messageType;
            afismq.query("/auth/admin/UserTableConfig", { uid: tableId }, function (data) {
                cols = data && data.columns ? data.columns.split(',') : [];
                that.editColumns(cols);
                that.builder(opts);
            }, function () {
                that.builder(opts);
            });
        } else {
            that.builder(opts);
        }
    };
    AfisTable.prototype.tablesort = function (name, cols, callback) {
        var sort = null;
        var origianl = cols.original;
        var colsSet = [];
        if (cols.colset.length > 0) {
            var odic = {};
            $.each(origianl, function (i, n) {
                odic[n.name] = n;
            });
            var setdic = {};
            $.each(cols.colset, function (i, n) {
                setdic[n] = true;
                colsSet.push(odic[n]);
            });
            $.each(origianl, function (i, n) {
                if (!setdic[n.name]) {
                    var col = $.extend({}, n, { "hidden": true });
                    colsSet.push(col);
                }
            });
        } else {
            colsSet = origianl;
        }
        var _callback = callback;
        var modal = new popup({
            title: "编辑列",
            onConfirm: function () {
                if (_callback && typeof _callback === 'function') {
                    var cols = sort.get();
                    var res = _callback(cols, modal);
                    if (res) {
                        modal.close();
                    }
                }
            },
            btns: [{
                text: "重置", click: function () {
                    modal.body.children().remove();
                    sort = new tablesort(name, origianl);
                    sort.colset.appendTo(modal.body);
                    sort.builder();
                }
            }, {
                text: "还原", click: function () {
                    modal.body.children().remove();
                    sort = new tablesort(name, colsSet);
                    sort.colset.appendTo(modal.body);
                    sort.builder();
                }
            }]
        });
        sort = new tablesort(name, colsSet);
        sort.colset.appendTo(modal.body);
        sort.builder();
        modal.showModal();
    };
    AfisTable.prototype.tip = function (msg) {
        this.popup.info(msg);
    };
    AfisTable.prototype.confirm = function (opera, onConfirm) {
        this.popup.confirm("确定要执行此次" + (opera ? opera : "") + "操作么？", onConfirm);
    };
    AfisTable.prototype.getSelected = function (desc, opera, isSingle, before) {
        var that = this;
        var single = typeof isSingle == 'boolean' && isSingle;
        var rows = that.getSelectedRows();
        var msg = "请选择" + (single ? "一条" : "") + (desc ? desc : "") + "数据进行" + (opera ? opera : "") + "操作";
        if (rows.length == 0) {
            that.tip(msg);
            return null;
        }
        if (single) {
            if (rows.length > 1) {
                that.tip(msg);
                return null;
            } else {
                var row = rows[0];
                if (typeof before == 'function') {
                    var flag = before(row);
                    if (typeof flag == 'boolean' && flag) {
                        return row;
                    } else {
                        if (typeof flag == 'string') {
                            that.tip(flag);
                        }
                        return null;
                    }
                } else {
                    return row;
                }
            }
        } else {
            if (typeof before == 'function') {
                var flag = before(rows);
                if (typeof flag == 'boolean' && flag) {
                    return rows;
                } else {
                    if (typeof flag == 'string') {
                        that.tip(flag);
                    }
                    return null;
                }
            } else {
                return rows;
            }
        }
    };
    AfisTable.prototype.verifyRow = function (func, desc, opera, before) {
        var row = this.getSelected(desc, opera, true, before);
        if (row && typeof func == 'function') {
            func(row);
        }
    };
    AfisTable.prototype.verifyRows = function (func, desc, opera, before) {
        var rows = this.getSelected(desc, opera, false, before);
        if (rows && typeof func == 'function') {
            func(rows);
        }
    };
    AfisTable.prototype.verifyConfirmRow = function (func, desc, opera, before) {
        var row = this.getSelected(desc, opera, true, before);
        if (row && typeof func == 'function') {
            this.confirm(opera, function () {
                func(row);
            });
        }
    };
    AfisTable.prototype.verifyConfirmRows = function (func, desc, opera, before) {
        var rows = this.getSelected(desc, opera, false, before);
        if (rows && typeof func == 'function') {
            this.confirm(opera, function () {
                func(rows);
            });
        }
    };
    return AfisTable;
}));
