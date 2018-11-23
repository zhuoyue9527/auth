(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (!AFIS) {
            AFIS = window.afis = {};
        }
        AFIS.tablebase = factory();
    }
}(function () {
    var WATable = function () {
        var priv = {}; //private api
        var publ = {}; //public api
        priv.options = {};
        var defaults = {
            remote: true,
            initData: true,
            param: {
                start: 0,
                limit: 5//,
                // sortname: '',
                // sorttype: 'asc' //sort type can be asc/desc
            },
            load: false,
            messageType: false,
            columns: [],
            debug: true,
            //prints debug info to console
            //show filter row
            columnPicker: false,
            //show columnpicker
            checkboxes: false,
            //show body checkboxes
            checkAllToggle: true,
            //show check all toggle
            showPager: true,
            pageSize: 10,
            //current pagesize
            pageSizes: [10, 20, 50, 100],
            //available pagesizes
            hidePagerOnEmpty: false,
            //removes pager if no rows
            preFill: false,
            //prefill table with empty rows
            showAllRows: true,
            editAble: false,
            //当行数不足时，表格显示足够的分页条数
            sorting: false,
            // enable column sorting
            sortEmptyLast: true,
            //empty values will be shown last
            rolResize: true,
            colsSorted: false,
            onColumnPicker: false,
            trName: "trName",
            onPostFail: false
        };
        /* bundled scripts */
        priv.ext = {};
        priv.ext.XDate = XDate;

        //these holds the actual dom table objects, and is used to identify what parts of the table that needs to be created.
        var _cont; //container holding table
        var _table; //the table
        var _tablediv;
        var _head; //table header
        var _headSort; //table header sorting row
        var _body; //table body
        var _foot; //table footer
        var _data; //columns and rows
        var _currPage = 1; //current page
        var _pageSize; //current pagesize
        var _totalPages; //total pages
        var _currSortCol; //current sorting column
        var _currSortFlip = false; //current sorting direction
        var _uniqueCol; //reference to column with the unique property
        var _uniqueCols = {}; //array with checked rows
        var _checkToggleChecked = false; //check-all toggle state
        var _fetchParams = false;

        var _dataKey = publ.dataKey = "original";
        var _trName = null;

        priv.init = function () {
            //_cont：表数据的容器
            _cont = priv.options.id;
            $(_cont).css({ "position": "relative" });
            _trName = priv.options.trName;
            if (priv.options.selectable == null) {
                priv.options.selectable = !priv.options.checkboxes;
            }
            //fill the table with empty cells
            var data = false;
            if (priv.options.columns) {
                data = {
                    cols: priv.options.columns,
                    rows: []
                };
            } else {
                data = {
                    cols: {
                        dummy: {
                            index: 1,
                            friendly: "&nbsp;",
                            type: "string"
                        }
                    },
                    rows: []
                };
            }
            priv.setData(data, false, true, false);
        };
        priv.createHeader = function () {
            //create table itself
            if (!_table) {
                _tablediv = _head = _body = _foot = undefined;
                _tablediv = $('<div class="table_container"></div>').appendTo(_cont);
                _table = $('<table class="watable table table-striped table-hover table-condensed table-bordered"></table>').appendTo(_tablediv);
            }
            //create the header which will later hold both sorting and filtering
            if (!_head) {
                _table.find('thead').remove();
                _headSort = undefined;
                _head = $('<thead></thead>').prependTo(_table);
            }
            //此时colsSorted中的数据只是列名数组
            //sort the columns in index order
            //对数据列进行排序排序规则是index属性值顺序排序
            var colsSorted = false;
            if (!priv.colsSorted) {
                colsSorted = Object.keys(_data.cols).sort(function (a, b) {
                    return _data.cols[a].index - _data.cols[b].index;
                });
                priv.options.colsSorted = colsSorted;
            }
            //表头设置
            if (!_headSort) {
                _head.find('.sort i').tooltip('hide');
                _head.find(".sort").remove();
                _headSort = $('<tr class="sort"></tr>').prependTo(_head);
                //create the checkall toggle
                if (priv.options.checkboxes) {
                    var checked = _checkToggleChecked ? 'checked' : '';
                    var headCell = $('<th style="text-align:center" class="th-checkbox"><label class="select-cont"></label></th>').appendTo(_headSort);
                    var cb = $('<div class="select-wapper"></div>').appendTo(headCell.find("label"));
                    var elem = $('<input {0} class="checkToggle" type="checkbox" />'.f(checked)).appendTo(cb);
                    $('<span class="select-v"></span>').appendTo(cb);
                    elem.on('change', priv.checkToggleChanged);
                }
                var i = 0, colLen = colsSorted.length;
                //create the sortable headers
                for (; i < colLen; i++) {
                    var column = colsSorted[i];
                    var props = _data.cols[column];
                    if (!props.hidden) {
                        var headCell = $('<th {0}></th>'.f(props.width ? 'style="width:' + props.width + 'px"' : '')).appendTo(_headSort);
                        headCell.css({ cursor: 'pointer' });
                        var link;
                        if (priv.options.sorting && props.sorting !== false) {
                            link = $('<span class="pull-left tsort">{0}</span>'.f(props.friendly || column));
                            link.on('click', {
                                column: column
                            }, priv.columnClicked);
                        } else {
                            link = $('<span class="pull-left">{0}</span>'.f(props.friendly || column));
                        }
                        link.appendTo(headCell);
                        //Add sort arrow
                        if (column == _currSortCol) {
                            if (_currSortFlip) {
                                link.addClass("headerSortDown");
                            } else {
                                link.addClass("headerSortUp");
                            }
                        }
                        // 列宽拖动设置
                        if (priv.options.rolResize) {
                            var rol_sp = $('<div class="colspilter">&nbsp</div>').appendTo(headCell);
                            rol_sp.on('mousedown', {
                                column: column
                            }, priv.rolResizeB);
                        }
                    }
                }
            }
        }
        priv.createTable = function () {
            priv.createHeader();
            //主体数据设置 填充body
            if (!_body) {
                var prevBody = _table.find('tbody');
                prevBody.remove();
                _body = $('<tbody style="display:none"></tbody>').insertAfter(_head);
                //find out what rows to show next...
                var rowsAdded = 0;
                var colsSorted = priv.options.colsSorted;
                $.each(_data.rows, function (index, props) {
                    priv.addRowCell(index, props, colsSorted);
                    rowsAdded++;
                    //enough rows created?
                    if (priv.options.showPager) {
                        if (rowsAdded >= priv.options.pageSize) {
                            _data.toRow = _data.fromRow + rowsAdded;
                            return false;
                        }
                    }
                });

                //补足行数
                if (priv.options.showAllRows && rowsAdded < priv.options.pageSize) {
                    for (var x = rowsAdded; x < priv.options.pageSize; x++) {
                        priv.addRowCell(x, {
                            'afis_row_complete': true
                        },
                            colsSorted);
                    }
                }
                if (_currPage == 1) {
                    if (priv.options.remote) {
                        _totalPages = Math.round(Math.ceil(_data.totalSize / _pageSize));
                    } else {
                        _totalPages = Math.round(Math.ceil(_data.rows.length / _pageSize));
                    }
                }
                if (priv.options.eidtAble) {
                    priv.addRowCell(1, {
                        'afis_row_complete': true
                    }, colsSorted);
                }
                _body.show();
            }
            if (priv.options.showPager) {
                priv.tablefoot();
                if (_data.rows.length == 0 && priv.options.hidePagerOnEmpty) {
                    $('.btn-toolbar', _foot).remove();
                }
            }
        };
        priv.tablefoot = function () {
            if (_foot) {
                return;
            }
            //页码操作
            $(_cont).find('.table_footer').remove();
            _foot = $('<div class="table_footer"></div>').appendTo(_cont);
            var footCell = _foot;
            //是否显示分页条
            if (priv.options.showPager) {
                var pageToolbar = $('<div class="pull-left page-tool-bar"></div>').appendTo(footCell);

                //创建页数大小选择器
                if (priv.options.pageSizes.length) {
                    pageToolbar.html("每页行数");
                    $.each(priv.options.pageSizes, function (index, val) {
                        var $a = $('<a href="javascript:void(0);">{0}</a>'.f(val)).appendTo(pageToolbar);
                        _pageSize = _pageSize ? _pageSize : priv.options.pageSize;
                        if (_pageSize == val) {
                            $a.addClass("cur");
                        }
                    });
                    //查询数据
                    pageToolbar.on('click', 'a', priv.pageSizeChanged);
                }

                if (priv.options.columnPicker) {
                    var div = $('<div class="btn-group dropup columnpicker"></div>').appendTo(pageToolbar);
                    var btn = $('<button class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">选择列&nbsp;</button>').appendTo(div);
                    if (priv.options.onColumnPicker) {
                        btn.click(priv.options.onColumnPicker);
                    } else {
                        var span = $('<span class="caret"></span>').appendTo(btn);
                        var dropdown = $('<div class="dropdown-menu"></div>').appendTo(div);
                        var ul = $('<ul></ul>').appendTo(dropdown);
                        var colsSorted = priv.options.colsSorted;
                        var i = 0, colLen = colsSorted.length;
                        for (; i < colLen; i++) {
                            var col = colsSorted[i];
                            var props = _data.cols[col];

                            if (props.type != "unique") {
                                var li = $('<li></li>').appendTo(ul);
                                $('<label><input {0} type="checkbox" title="{1}" value="{1}" >&nbsp;{2}</input></label>'.f(props.hidden ? '' : 'checked', col, props.friendly || col)).appendTo(li);
                            }
                        }
                        if (colLen > priv.options.pageSize) {
                            dropdown.css({ "height": (priv.options.pageSize * 29 + 31) + "px", "overflow": "auto" });
                        }
                        //选择显示列信息
                        div.on('click', 'input', priv.columnPickerClicked);
                        div.on('click', 'label', function (e) {
                            e.stopPropagation();
                        });
                    }
                }

                var pageStep = $('<div class="pull-right page-step"></div>').appendTo(footCell);
                if (_data.rows.length > 0) {
                    var startR = 0;
                    var endR = 0;
                    var totalR = 0;
                    if (priv.options.remote) {
                        startR = _data.start;
                        endR = _data.limit;
                        totalR = _data.totalSize;
                    } else {
                        startR = 0;
                        endR = _data.rows.length;
                        totalR = _data.rows.length;
                    }

                    $('<div class="pull-left">合计{0}条，</div>'.f(_data.totalSize)).appendTo(pageStep);
                    $('<div class="pull-left">共{0}页</div>'.f(_totalPages)).appendTo(pageStep);

                    var step = $('<div class="pull-left step"></div>').appendTo(pageStep);

                    var first = $('<li><a href="javascript:void(0);" style="margin-left:5px;" title="到首页"><</a></li>').appendTo(step);
                    first.on('click', {
                        pageIndex: 1
                    },
                        priv.pageChanged);

                    var ps = 1;
                    var next = -1;
                    if (_currPage > 4) {
                        ps = _totalPages - _currPage > 4 ? _currPage - 4 : (_currPage + (_totalPages - _currPage) - 8 > 1 ? (_currPage + (_totalPages - _currPage) - 8) : 1);
                        next = _totalPages - _currPage > 4 ? _currPage + 4 : _totalPages;
                    } else {
                        next = (9 - _currPage) > (_totalPages - _currPage) ? _totalPages : (_currPage + 9 - _currPage);
                    }

                    for (var i = ps; i <= next; i++) {
                        var $a = $('<li><a href="javascript:void(0);" style="margin-left:5px;" class="{0}" title="到第 {1} 页">{2}</a></li>'.f(i == _currPage ? "cur" : "", i, i)).appendTo(step);
                        if (i != _currPage) $a.on('click', {
                            pageIndex: i
                        },
                            priv.pageChanged);
                    }

                    var last = $('<li><a href="javascript:void(0);" style="margin-left:5px;" title="到最后一页" >></a></li>').appendTo(step);
                    last.on('click', {
                        pageIndex: _totalPages
                    },
                        priv.pageChanged);

                    step.append("&nbsp;&nbsp;到&nbsp;&nbsp;");
                    var inp = $('<input type="text" class="form-control" value="{0}" />'.f(_currPage)).appendTo(step);
                    inp.on('keyup', function () {
                        var _this = $(this);
                        _this.val(_this.val().replace(/[^0-9]/g, ''));
                    });
                    step.append("&nbsp;&nbsp;页&nbsp;&nbsp;");
                    var btn = $('<button class="btn btn-primary">确定</button>').appendTo(step);
                    btn.on('click', {
                        pageIndex: btn
                    },
                        priv.pageChoose);

                } else {
                    pageStep.append("暂无数据");
                }
            }
        };
        priv.addRowCell = function (index, props, colsSorted) {
            var row = $('<tr class="{0}" tr-name="{1}"></tr>'.f(index % 2 == 0 ? 'odd' : 'even', priv.options.trName)).appendTo(_body);
            if (!props['afis_row_complete']) {
                row.attr("data-index", index);
            }
            row.data(_dataKey, props);
            row.on("click", "td[td-name][content]", priv.rowClicked).on("dblclick", "td[td-name]", priv.dbClicked);
            if (priv.options.checkboxes) {
                var cell = $('<td><label class="select-cont"></label></td>').appendTo(row);
                if (!props["afis_row_complete"]) {
                    var check = _uniqueCols[props[_uniqueCol]] != undefined ? 'checked' : '';
                    var checkable = props['checkable'] === false ? 'disabled' : '';
                    var cbdiv = $('<div class="select-wapper"></div>').appendTo(cell.find("label"));
                    var ele = $('<input class="unique" {0} {1} type="checkbox" />'.f(check, checkable)).appendTo(cbdiv);
                    ele.on("change", priv.checkCellToggleChanged)
                    $('<span class="select-v"></span>').appendTo(cbdiv);
                }
            }
            var colLen = colsSorted.length;
            var i = 0;
            for (; i < colLen; i++) {
                var key = colsSorted[i];
                var val = props[key];
                if (!_data.cols[key]) {
                    return;
                }
                if (_data.cols[key].unique) {
                    row.data('unique', val);
                }
                if (!_data.cols[key].hidden) {
                    var cell = $('<td content td-name="' + key + '"></td>').appendTo(row);
                    cell.data('column', key);
                    var format = priv.options.columns[key].format;
                    if (props["afis_row_complete"]) {
                        cell.html("");
                    } else {
                        var _val = priv.parseHtml(val);
                        try {
                            if (format != null) {
                                var formatVal = format(_val, props, cell, key);
                                if (formatVal != null) {
                                    cell.html(formatVal);
                                }
                            } else {
                                cell.html(val == null ? "" : _val);
                            }
                        } catch (e) {
                        } finally {

                        }
                    }
                }
            }
        };
        priv.parseHtml = function (val) {
            if (val == null) {
                return "";
            }
            if (typeof val === 'string' && !isNaN(val)) {
                return val.toString().replace(/[<>&"]/g, function (c) {
                    return { '<': '&lt;', '>': '&gt;', '&': '&amp;', '"': '&quot;' }[c];
                });
            }
            return val;
        };
        priv.update = function (callback, skipCols, resetChecked) {
            if (!priv.options.remote) {
                return;
            }
            var load = priv.options.load;
            if (typeof load != 'function') {
                return;
            }
            var mask = $("<div class='table_mask'><p>正在加载数据...</p></div>").appendTo(_cont);
            load.call(priv, priv.options.messageType, priv.options.param, function (data) {
                priv.setData(data, skipCols, resetChecked, true);
            }, function (msg, code, desc) {
                if (priv.options.onPostFail) {
                    priv.options.onPostFail(msg, code, desc);
                } else {
                    _foot.find(".pull-right.page-step").html(msg);
                }
            }, function () {
                if (typeof callback == "function") {
                    callback.call(priv);
                }
                mask.remove();
            });
        };
        priv.setData = function (pData, skipCols, resetChecked, remote) {
            var data = false;
            if (!remote) {
                data = $.extend(true, {}, pData);
            } else {
                if (Object.prototype.toString.call(pData) === '[object Array]') {
                    data = {
                        rows: pData,
                        start: 0,
                        limit: pData.length,
                        totalSize: pData.length
                    };
                } else {
                    data = {
                        rows: pData.result,
                        start: pData.start,
                        limit: pData.limit,
                        totalSize: pData.totalSize
                    };
                }
            }
            //use previous column definitions?
            //使用前一列定义？跳过列？
            skipCols = skipCols || false || remote;
            if (skipCols) {
                data.cols = _data.cols;
            } else {
                _filterCols = {};
            }

            _data = data;
            _data.rowsOrg = _data.rows;

            //we might have more/less data now. Recalculate stuff.
            if (remote) {
                _currPage = Math.ceil(_data.start / _pageSize) + 1;
                _totalPages = Math.ceil(_data.totalSize / _pageSize);
            } else {
                if (_currPage > 1) {
                    _data.toRow = Math.min(_data.rows.length, _data.toRow);
                    _data.fromRow = _data.toRow - _pageSize;
                    _data.fromRow = Math.max(_data.fromRow, 0);
                    _currPage = Math.ceil(_data.fromRow / _pageSize) + 1;
                    _totalPages = Math.ceil(_data.rows.length / _pageSize);
                } else {
                    _data.fromRow = 0;
                    if (priv.options.pageSize != -1) {
                        _data.toRow = _data.fromRow + priv.options.pageSize;
                    }
                    _data.toRow = Math.max(_data.toRow, _data.rows.length);
                }
            }
            //wash the new data a bit
            _uniqueCol = "";
            $.each(_data.cols, function (col, props) {
                //set sorting
                if (!_currSortCol && props.sortOrder && priv.options.sorting && props.sorting !== false) {
                    _currSortCol = col;
                    _currSortFlip = props.sortOrder != "asc";
                }

                //default to string type if missing
                if (!props.type) {
                    _data.cols[col].type = "string";
                }

                //if several unique columns is defined, use the first.
                if (props.unique) {
                    if (!_uniqueCol) _uniqueCol = col;
                    else props.unique = false;
                }

                //if index property is missing, create one
                if (!props.index) {
                    _data.cols[col].index = new priv.ext.XDate();
                }
                props.column = col;
            });

            //keep any previously checked rows around?
            if (resetChecked === true || resetChecked === undefined) {
                _uniqueCols = {};
            }
            else {
                for (var key in _uniqueCols) {
                    _uniqueCols[key] = priv.getRow(key);
                }
            }

            if (_uniqueCol) {
                //create a unique column definition
                _data.cols["unique"] = {
                    column: "unique",
                    type: "unique",
                    index: -1,
                    hidden: true
                };

                //add rows that needs to be pre-checked
                $.each(_data.rows, function (index, row) {
                    if (row["checked"] === true) {
                        _uniqueCols[row[_uniqueCol]] = row;
                    }
                });
            }

            _head = undefined;
            _body = undefined;
            _foot = undefined;
            priv.sort();
            priv.createTable();
            if (_table && priv.options.checkboxes) {
                var _checkboxes = _table.find("thead").find("input[type='checkbox']");
                if (_checkboxes.length > 0) {
                    var _checkbox = $(_checkboxes[0]);
                    if (_checkbox.is(":checked")) {
                        _checkbox.prop("checked", false);
                    }
                }
            }
        };
        priv.sort = function () {
            if (!_data.cols[_currSortCol]) {
                _currSortCol = "";
            }
            if (!_currSortCol) {
                return;
            }
            var isString = (_data.cols[_currSortCol].type == "string");
            _data.rows = _data.rows.sort(function (a, b) {
                var valA = a[_currSortCol];
                var valB = b[_currSortCol];

                if (isString) {
                    if (valA == undefined) {
                        valA = '';
                    }
                    if (valB == undefined) {
                        valB = '';
                    }
                    if (String(valA).toLowerCase() == String(valB).toLowerCase()) {
                        return 0;
                    }
                    if (String(valA).toLowerCase() > String(valB).toLowerCase()) {
                        return _currSortFlip ? -1 : 1;
                    }
                    else {
                        return _currSortFlip ? 1 : -1;
                    }
                } else {
                    valA = (+valA);
                    valB = (+valB);
                    if (valA == undefined || isNaN(valA)) {
                        valA = priv.options.sortEmptyLast ? _currSortFlip ? Number.NEGATIVE_INFINITY : Number.POSITIVE_INFINITY : Number.NEGATIVE_INFINITY;
                    }
                    if (valB == undefined || isNaN(valB)) {
                        valB = priv.options.sortEmptyLast ? _currSortFlip ? Number.NEGATIVE_INFINITY : Number.POSITIVE_INFINITY : Number.NEGATIVE_INFINITY;
                    }
                    if (valA == valB) return 0;
                    if (valA > valB) return _currSortFlip ? -1 : 1;
                    else return _currSortFlip ? 1 : -1;
                }
            });
        };
        priv.getRow = function (unique) {
            var start = new priv.ext.XDate();
            var row;
            $.each(_data.rowsOrg, function (i, r) {
                if (r[_uniqueCol] == unique) {
                    row = r;
                    return false;
                }
            });
            priv.log('row lookup finished in {0} ms.'.f(new priv.ext.XDate() - start));
            return row;
        };
        priv.log = function (message, isWarning) {
            if (isWarning) {
                console.warn(message);
            }
            else if (priv.options.debug) {
                console.log(message);
            }
        };
        /* Event Handlers */
        priv.pageChoose = function (e) {
            var rr = /^\d+$/g;
            if (this.tagName == 'INPUT') {
                var p = $(this).val();
                if (e.keyCode != 13) {
                    if (p == '' || (rr.test(p) && p <= _totalPages && p > 0)) {
                        $(this).parent('div').removeClass("has-error");
                    } else {
                        $(this).parent('div').addClass("has-error");
                    }
                    return;
                }
            } else {
                p = $(this).prev('input').val();
            }
            if (p == '' || (rr.test(p) && p <= _totalPages && p > 0)) {
                e.data = {
                    pageIndex: p
                };
                priv.pageChanged(e);
            } else {
                alert('请输入正确的页数！');
            }
        }
        /*
         when: changing page in pager
         what: triggers table to be created with new page
         */
        priv.pageChanged = function (e) {
            e.preventDefault();
            if (e.data.pageIndex < 1 || e.data.pageIndex > _totalPages) return;
            //set the new page
            if (priv.options.remote) {
                if (!_fetchParams) {
                    return;
                }
                priv.options.param.start = priv.options.pageSize * (e.data.pageIndex - 1);
                priv.options.param.limit = priv.options.pageSize;
                //                    priv.options.param.limit = priv.options.pageSize * e.data.pageIndex;
                priv.update();
            } else {
                _currPage = e.data.pageIndex;
                priv.log('paging to index:{0}'.f(_currPage));

                //find out what rows to create
                _data.fromRow = ((_currPage - 1) * _pageSize);
                _data.toRow = _data.fromRow + _pageSize;
                if (_data.toRow > _data.rows.length) _data.toRow = _data.rows.length;

                //trigger callback
                if (typeof priv.options.pageChanged == 'function') {
                    priv.options.pageChanged.call(e.target, {
                        event: e,
                        page: _currPage
                    });
                }
                _body = undefined;
                _foot = undefined;
                priv.createTable();
            }
        };
        /*
         when: changing pagesize in pagesize dropdown
         what: triggers table to be created with new pagesize
         */
        priv.pageSizeChanged = function (e) {
            e.preventDefault();
            var val = $(this).text().toLowerCase();

            priv.log('pagesize changed to:{0}'.f(val));

            //set the new pagesize
            //revert to first page, as its gets messy otherwise.
            if (priv.options.remote) {
                if (!_fetchParams) {
                    return;
                }
                _currPage = 1;
                priv.options.param.start = 0;
                priv.options.param.limit = parseInt(val);
                priv.options.pageSize = parseInt(val);
                _pageSize = parseInt(val);

                priv.update();
            }
        };
        /**
         * 列宽拖动，开始
         */
        priv.rolResizeB = function (e) {
            e.stopPropagation();
            $(document).on('mousemove', {
                th: $(this).parent('th'),
                table: $(this).closest('table'),
                x: e.pageX,
                wi: $(this).parent('th').outerWidth(),
                tw: $(this).closest('table').outerWidth(),
                column: e.data.column
            },
                priv.rolResizing);
            $(document).on('mouseup', priv.rolResized);
            $('body').attr("unselectable", 'on');
            $('body').bind('selectstart',
                function () {
                    return false
                });
        };
        priv.rolResizing = function (e) {
            var off = e.data.wi + e.pageX - e.data.x;
            var woff = e.data.tw + e.pageX - e.data.x;
            if (off < 10) {
                e.preventDefault();
                return;
            }
            e.data.th.css({
                width: off
            });
            _data.cols[e.data.column].width = off;
            e.data.table.css({
                width: woff
            });
        };
        priv.rolResized = function (e) {
            $(document).off('mousemove', priv.rolResizing);
            $('body').removeAttr("unselectable");
            $('body').unbind('selectstart');
        };
        /*
         when: clicking a column
         what: triggers table to be sorted by the column
         */
        priv.columnClicked = function (e) {
            //e.preventDefault();
            e.stopPropagation();
            priv.log('col:{0} clicked'.f(e.data.column));

            //set the new sorting column
            if (_currSortCol == e.data.column) {
                _currSortFlip = !_currSortFlip;
            }
            _currSortCol = e.data.column;

            //trigger callback
            if (typeof priv.options.columnClicked == 'function') {
                priv.options.columnClicked.call(e.target, {
                    event: e,
                    column: _data.cols[_currSortCol],
                    descending: _currSortFlip
                });
            }

            _headSort = undefined;
            _body = undefined;
            priv.sort();
            priv.createTable();
        };
        /*
         when: clicking a column in columnpicker
         what: triggers table to show/hide the column
         */
        priv.columnPickerClicked = function (e) {
            e.stopPropagation();

            var elem = $(this);
            var col = elem.val();
            priv.log('col:{0} {1}'.f(col, elem.is(':checked') ? 'checked' : 'unchecked'));

            //toggle column visibility
            _data.cols[col].hidden = !_data.cols[col].hidden;

            _data.cols[col].index = _data.cols[col].index || new priv.ext.XDate();
            _head = undefined;
            _body = undefined;
            priv.createTable();
        };
        //全选复选框 发生change事件
        priv.checkToggleChanged = function (e) {
            e.stopPropagation();
            var elem = $(this);
            if (elem.is(':checked')) {
                elem.parents("div.select-wapper").removeClass("half-checked");
                if (_data.rows == null || _data.rows.length == 0) {
                    return;
                }
                $.each(_data.rows, function (index, props) {
                    var row = _data.rows[index];
                    if (row.checkable === false) {
                        return;
                    }
                    _uniqueCols[props[_uniqueCol]] = row;
                });
                _checkToggleChecked = true;
                var trs = _body.find("tr[tr-name='" + _trName + "'][data-index]");
                $(trs).each(function () {
                    var tr = $(this);
                    var _data = tr.data(_dataKey);
                    if (_data && !_data.afis_row_complete) {
                        tr.find(".unique").prop("checked", true);
                        // tr.addClass("selected");
                    }
                });
            } else {
                for (var key in _uniqueCols) {
                    var row = _uniqueCols[key];
                    if (row.checkable === false) {
                        continue;
                    }
                    else {
                        delete _uniqueCols[key];
                    }
                }
                _checkToggleChecked = false;
                _body.find("tr[tr-name='" + _trName + "'][data-index] input.unique").prop("checked", false);
                // _body.find("tr[tr-name='" + _trName + "'][data-index]").removeClass("selected");
            }
            if (typeof priv.options.checkToggleChanged == 'function') {
                //call 调用一个对象的一个方法，以另一个对象替换当前对象
                priv.options.checkToggleChanged.call(e.target, {
                    event: e,
                    rows: priv.getSelectedRows()
                });
            }
        };
        //判断是否全选
        priv.checkAll = function (e) {
            var len = _body.find("tr[tr-name='" + _trName + "'][data-index]>td input.unique").length;
            var checked = _body.find("tr>td input.unique:checked").length;
            var checkAll = (len == checked);
            var checkbox = _head.find("tr>th input.checkToggle[type='checkbox']");
            checkbox.prop("checked", checkAll);
            var selectWapper = checkbox.parents("div.select-wapper");
            if (!checkAll) {
                if (checked == 0) {
                    selectWapper.removeClass("half-checked");
                } else {
                    selectWapper.addClass("half-checked");
                }
            } else {
                selectWapper.removeClass("half-checked");
            }
            if (checkAll && typeof priv.options.checkToggleChanged == 'function') {
                //call 调用一个对象的一个方法，以另一个对象替换当前对象
                priv.options.checkToggleChanged.call(e.target, {
                    event: e,
                    rows: priv.getSelectedRows()
                });
            }
        };
        //行复选框 发生change事件
        priv.checkCellToggleChanged = function (e) {
            e.stopPropagation();
            priv.checkAll(e);
            if (typeof priv.options.checkCellToggleChanged == 'function') {
                //call 调用一个对象的一个方法，以另一个对象替换当前对象
                priv.options.checkCellToggleChanged.call(e.target, {
                    event: e,
                    rows: priv.getSelectedRows()
                });
            }
        };
        //单元格单机事件
        priv.rowClicked = function (e) {
            e.stopPropagation();
            var td = $(this);
            var tr = td.parent("tr");
            if (tr.attr("data-index")) {
                var row = tr.data(_dataKey);
                var isChecked = false;
                if (priv.options.checkboxes) {
                    isChecked = !tr.find("input.unique[type='checkbox']").is(":checked");
                    tr.find("input.unique").prop("checked", isChecked);
                    priv.checkAll(e);
                } else {
                    if (priv.options.selectable) {
                        //无复选框-单选
                        if (tr.hasClass("selected")) {
                            tr.removeClass("selected");
                            isChecked = false;
                        } else {
                            tr.addClass("selected");
                            tr.siblings("tr[tr-name='" + _trName + "'][data-index]").removeClass("selected");
                            isChecked = true;
                        }
                    }
                }
                var column = _data.cols[td.data('column')];
                if (typeof priv.options.rowClicked == 'function') {
                    priv.options.rowClicked.call(e.target, {
                        event: e,
                        row: row,
                        column: column,
                        checked: isChecked
                    });
                }
            }
        };
        //单元格双击事件
        priv.dbClicked = function (e) {
            if (typeof priv.options.rowClicked == 'function') {
                var td = $(this);
                var tr = td.parent("tr");
                if (tr.attr("data-index")) {
                    var row = tr.data(_dataKey);
                    var isChecked = false;
                    //无复选框-单选
                    if (tr.hasClass("selected")) {
                        tr.removeClass("selected");
                        isChecked = false;
                    } else {
                        tr.addClass("selected");
                        tr.siblings("tr[tr-name='" + _trName + "'][data-index]").removeClass("selected");
                        isChecked = true;
                    }
                    var column = _data.cols[td.data('column')];
                    priv.options.rowClicked.call(e.target, {
                        event: e,
                        row: row,
                        column: column,
                        checked: isChecked
                    });
                }
            }
        };
        /*Public API*/
        publ.fetchParam = function (param, callback) {
            _fetchParams = true;
            priv.options.param = $.extend({}, defaults.param, param);
            _currPage = 1;
            _pageSize = priv.options.pageSize;
            priv.options.param.limit = _pageSize;
            //priv.options.param.limit = priv.options.param.start + priv.options.pageSize;
            priv.update(callback);
        };
        publ.init = function (options) {
            priv.log('watable initialization...');
            $.extend(true, priv.options, defaults, options);
            priv.init();
            return publ;
        };
        publ.update = function (callback, skipCols, resetChecked) {
            priv.log('publ.update called');
            _pageSize = priv.options.pageSize;
            priv.update(callback, skipCols, resetChecked);
            return publ;
        };
        publ.getData = function (checked, filtered) {
            var data = _data["rows"] == null ? [] : _data["rows"];
            return data;
        };
        publ.setData = function (rows) {
            var _zdata = { "rows": rows };
            priv.options.pageSize = rows != null ? (rows.length < 10 ? 10 : rows.length) : 10;
            priv.setData(_zdata, true);
            return publ;
        };
        publ.setRows = function (data, isAddRow) {
            priv.log('publ.setData called');
            var rows = { "rows": data };
            var size = priv.options.pageSize;
            if (data && data.length > 0) {
                size = isAddRow ? data.length + 1 : data.length;
            }
            priv.options.pageSize = size;
            priv.setData(rows, true);
            return publ;
        };
        publ.option = function (option, val) {
            priv.log('publ.option called');
            if (val == undefined) return priv.options[option];
            priv.options[option] = val;
            _head = undefined;
            _body = undefined;
            _foot = undefined;
            priv.createTable();
            return publ;
        };
        /**
         * 获取已选择的行数据
         */
        priv.getSelectedRows = function () {
            var rows = [];
            if (priv.options.checkboxes) {
                var checkboxes = $(_body).find("tr[tr-name='" + _trName + "'][data-index] input.unique");
                $(checkboxes).each(function () {
                    var that = $(this);
                    if (that.is(":checked")) {
                        var data = that.parents("tr").data(_dataKey);
                        rows.push(data);
                    }
                });
            } else {
                $(_body).find('tr[tr-name="' + _trName + '"][data-index].selected').each(function (index) {
                    var _row = $(this).data(_dataKey);
                    rows.push(_row);
                });
            }
            return rows;
        };
        publ.getSelectedRows = function () {
            return priv.getSelectedRows();
        };
        /**
         * 添加数据
         */
        publ.addRows = function (rows) {
            if (Object.prototype.toString.call(rows) === '[object Array]') {
                _data.rows = _data.rows == null ? [] : _data.rows;
                _data.rows = _data.rows.concat(rows);
            } else {
                _data.rows.push(rows);
            }
            _body = undefined;
            priv.createTable();
        };
        publ.getRow = function (tr) {
            if (tr) {
                return $(tr).data(_dataKey);
            }
            return null;
        };
        /**
         * 替换数据
         */
        publ.replaceRows = function (rows) {
            if (Object.prototype.toString.call(rows) === '[object Array]') {
                _data.rows = rows;
            } else {
                _data.rows = [rows];
            }
            _body = undefined;
            priv.createTable();
        };
        publ.setColumns = function (columns) {
            if (columns != null && Object.prototype.toString.call(columns) == '[object Array]' && columns.length > 0) {
                var cols = _data.cols, dic = {};
                var index = 1;
                $.each(columns, function (i, column) {
                    dic[column] = index;
                    index = index + 1;
                });
                $.each(cols, function (name, column) {
                    var index = dic[name];
                    if (index) {
                        _data.cols[name].hidden = false;
                        _data.cols[name].index = index || new priv.ext.XDate();
                    } else {
                        index = index + 1;
                        _data.cols[name].hidden = true;
                        _data.cols[name].index = index || new priv.ext.XDate();
                    }
                });
                _head = undefined;
                _body = undefined;
                priv.createTable();
            }
        };
        return publ;
    };
    $.fn.WATable = function (options) {
        options = options || {};
        return this.each(function () {
            options.id = this;
            $(this).data('WATable', new WATable().init(options));
        });
    };
    //String 类型的一个扩展
    String.prototype.format = String.prototype.f = function () {
        var s = this;
        var i = arguments.length;
        while (i--) {
            s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
        }
        return s;
    };
}));
