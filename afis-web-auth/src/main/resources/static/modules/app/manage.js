define(['page', 'text!./manage.html', 'armutil', 'list', 'popover', 'cssl!../index/css/manage.css'], function (page, html, util, list, popover) {
    'use strict';
    var app = new page(html),
        content = null,
        condition = null,
        table = null,
        tip = util.popup,
        status = util.IENUM.AddEnum("app", "status").AddList(":全选,1:正常,2:注销");

    app.init(function () {
        content = this.template;
        initForm();
        initTable();
    });
    var initForm = function () {
        condition = new util.form(content.find(".condition-form"));
        condition
            .addForm("INPUT", "appName", "应用名称")
            .addForm("RADIO", "status", "状态", { data: status.select, def: "" })
            .addForm("RANGEDATE", { start: "startDay", end: "endDay" }, "注册时间")
            .builder();

        new util.button(content.find(".condition-btn")).addBtn("查询", query).addBtn("重置", reset, "btn-reset").builder();
    };
    var initTable = function () {
        var cont = content.find('.table-content');
        var popoverId = "popover" + new Date().getTime();
        cont.attr("id", popoverId);
        table = new util.table();
        table
            .addColumn("id", "ID")
            .addColumn("appName", "应用名称")
            .addColumn("appCode", "应用授权码")
            .addColumn("key", "密钥")
            .addColumn("urlCallback", "返回跳转URL")
            .addColumn("logoPath", "logo", function (val, row, td) {
                if (val != null && val !== "") {
                    var btn = $('<i class="fa fa-image"></i>').appendTo(td);
                    btn.click(function (e) {
                        e.stopPropagation();
                    });
                    new popover(btn, {
                        trigger: "hover",
                        placement: "left",
                        viewport: { selector: '#' + popoverId + '.table-content table.table', padding: 8 },
                        content: function () {
                            var div = $('<div class="popover-img" style="width:102px;height:102px;"></div>');
                            $('<img src="' + val + '" alt="logo">').appendTo(div);
                            return div;
                        }
                    }).popover();
                }
            })
            .addColumn("status", "状态", util.parseStatus(status.dic))
            .addColumn("operateAppName", "操作应用")
            .addColumn("functionList", "已授权功能", function (val, row, td) {
                $('<label><span>查看</span><i class="fa fa-info-circle"></i><label>').appendTo(td).click(function () {
                    loadFunctionList(row.id, false);
                });
            })
            .addColumn("operateName", "操作员")
            .addColumn("operateTime", "操作时间", util.parseDate())
            .addColumn("remark", "备注");

        table.addBtn("新增", add, "fa fa-plus-square")
            .addBtn("修改", edit, "fa fa-pencil-square")
            .addBtn("注销", logout, "fa fa-times-circle")
            .addBtn("恢复", restore, "fa fa-circle-o");

        table.fetchTable(cont, { "messageType": '/auth/admin/applications' }, content.find('div.table-normal>div.table-up-div'), "app_manage");
    };
    var appFunctionList = function (content, url, check, func) {
        var checked = check ? true : false;
        var List = new list(content, {
            check: checked,
            link: false,
            nameKey: "groupName",
            panelKey: "functionList",
            panelParse: function (data, opt) {
                var that = this;
                var idKey = opt.idKey;
                var content = opt.content;
                var check = opt.check;
                var table = $('<table class="table"></table>').appendTo(content);
                var tr = $('<thead><tr></tr></thead>').appendTo(table).find("tr");
                if (checked) {
                    $('<th>选择</th>').appendTo(tr);
                }
                $.each(["id", "名称", "返回跳转URL", "备注"], function (i, name) {
                    $('<th>' + name + '</th>').appendTo(tr);
                });
                var tbody = $('<tbody></tbody>').appendTo(table);
                var sortList = data.sort(function (a, b) {
                    return a.index - b.index;
                });
                $.each(sortList, function (i, n) {
                    var _tr = $('<tr></tr>').appendTo(tbody);
                    if (checked) {
                        $('<td>' + that.component.check(check, n[idKey]) + '</td>').appendTo(_tr);
                    }
                    $.each(["id", "name", "url", "remark"], function (i, name) {
                        var val = n[name];
                        $('<td td-name="' + name + '">' + (val != null ? val : '') + '</td>').appendTo(_tr);
                    });
                });
            },
            collapse: true,
        });
        util.query(url, function (data) {
            var id = 1;
            var list = data.map(function (n) {
                n["id"] = id++;
                return n;
            });
            List.addList(list, func);
        });
        return List;
    }
    //新增 修改
    var popupEdit = function (data, callback) {
        if (data != null && typeof data == 'function') {
            callback = data;
            data = null;
        }
        var fills = [
            { name: "appName", tip: "请填写应用名称" },
            { name: "key", tip: "请填写密钥" }
        ];
        var title = data == null ? "新增" : "修改",
            forms = null,
            list = null,
            logoInfo = null,
            unique = new Date().getTime(),
            modal = util.builderModal(title, onConfirm, function (cont) {
                cont.addClass("afis app-manage left");
                var funcInfo = $('<div class="func-info"></div>').appendTo(cont);
                var appInfo = $('<div class="app-info"></div>').appendTo(cont);
                var logo = $('<div class="app-logo left"><div><img src="/images/default.png" title="" alt=""></div></div>').appendTo(appInfo).find("img");
                util.uploadImg(logo, function (path, name) {
                    logoInfo = {
                        router: "/img/",
                        fileName: name,
                        filePath: path
                    };
                    logo.attr("src", logoInfo.router + logoInfo.filePath);
                }, unique);
                var appInfoContent = $('<div class="app-info-content left"></div>').appendTo(appInfo);
                var options = { fills: fills.map(function (n) { return n.name; }) };
                forms = new util.form(appInfoContent, options);
                forms
                    .addForm("INPUT", "appName", "应用名称")
                    .addForm("TEXTAREA", "appCode", "应用授权码")
                    .addForm("TEXTAREA", "key", "密钥", { suffix: '<i class="fa fa-refresh" aria-hidden="true"></i>' })
                    .addForm("TEXTAREA", "urlCallback", "跳转链接")
                    .addForm("INPUT", "operateAppName", "操作应用")
                    .addForm("TEXTAREA", "remark", "备注")
                    .builder(function (form) {
                        if (form.option == null) {
                            form.option = {};
                        }
                        var name = form.name;
                        if (data == null) {
                            if (name == 'appCode' || name == 'operateAppName') {
                                return null;
                            } else {
                                if (name == "key") {
                                    form.option.state = "readonly";
                                }
                                return form;
                            }
                        } else {
                            var dic = { key: true, appCode: true, operateAppName: true };
                            if (dic[name]) {
                                form.option.state = "readonly";
                            }
                            return form;
                        }
                    });
                $('<div class="func-mask left"><div class="func-content left afis-collapse"></div></div>').appendTo(funcInfo);
                list = appFunctionList(funcInfo.find(".func-content"), "/auth/admin/functions/byGroups", true, function () {
                    if (data) {
                        this.setValue(data.functionList);
                    }
                });
                if (data) {
                    if (data.logoPath) {
                        logo.attr("src", data.logoPath);
                    }
                    forms.setValues(data);
                }
                cont.on("click", "i.fa.fa-refresh", function () {
                    util.query("/auth/admin/applications/key", function (data) {
                        forms.setValues({ key: data });
                    })
                });
                if (data == null) {
                    cont.find("i.fa.fa-refresh").trigger("click");
                }
            }, "app-manage", "larger", {
                    onHidden: function () {
                        util.ajaxUploadClear(unique);
                    }
                });

        function onConfirm(e) {
            var params = forms.getValues();
            $.each(fills, function (i, n) {
                var name = n.name;
                if (!params[name]) {
                    tip.warning(n.tip);
                    params = null;
                    return false;
                }
            });
            if (params == null) {
                return;
            }
            if (logoInfo) {
                params["logoPath"] = logoInfo.filePath;
                params["logoName"] = logoInfo.fileName;
            }
            params["functionList"] = list.getValue();
            if (params["remark"] != null && params["remark"] != "") {
                if (params["remark"].length > 100) {
                    return tip.warning("备注最大字符长度为100，目前为" + params["remark"].length);
                }
            }
            if (data == null) {
                util.add("/auth/admin/appFunctions", params, function (data) {
                    tip.success(function () {
                        modal.close();
                        if (callback) {
                            callback();
                        }
                    });
                });
            } else {
                util.patch("/auth/admin/appFunctions/" + data["id"], params, function (data) {
                    tip.success(function () {
                        modal.close();
                        if (callback) {
                            callback();
                        }
                    });
                });
            }
        }
        modal.showModal();
    };
    var loadFunctionList = function (id, isLoad, func) {
        var url = "/auth/admin/functions/byAppId/" + id;
        if (isLoad) {
            util.query(url, function (data) {
                var selected = [];
                $.each(data, function (i, group) {
                    $.each(group.functionList, function (j, item) {
                        selected.push(item.id);
                    });
                });
                func(selected);
            });
        } else {
            var modal = util.builderModal("查看已授权功能", null, function (cont) {
                cont.addClass("afis afis-collapse func-info left");
                appFunctionList(cont, url, false, function () {
                    if (this.store.list.length > 0) {
                        modal.showModal();
                    } else {
                        tip.info("暂无已授权功能数据");
                    }
                });
            }, "app-manage-function-list", $("body").width() - 445, { isFooter: false });
        }
    }
    //添加
    var add = function () {
        popupEdit(query);
    }
    //修改
    var edit = function () {
        table.verifyRow(function (row) {
            loadFunctionList(row.id, true, function (functionList) {
                popupEdit($.extend({}, row, { functionList: functionList }), query);
            });
        }, "应用", "修改", function (row) {
            if (row.status == 1) {
                return true;
            } else {
                tip.warning("只有正常状态的应用才能执行修改操作");
                return false;
            }
        });
    }
    //注销
    var logout = function () {
        table.verifyConfirmRow(function (row) {
            util.patch("/auth/admin/appFunctions/cancel/" + row.id, function (data) {
                tip.success(query);
            });
        }, "应用", "注销", function (row) {
            if (row.status == 1) {
                return true;
            } else {
                tip.warning("只有正常状态的应用才能执行注销操作");
                return false;
            }
        });
    }
    //恢复
    var restore = function () {
        table.verifyConfirmRow(function (row) {
            util.patch("/auth/admin/appFunctions/recover/" + row.id, function (data) {
                tip.success(query);
            });
        }, "应用", "恢复", function (row) {
            if (row.status == 2) {
                return true;
            } else {
                tip.warning("只有注销状态的应用才能执行恢复操作");
                return false;
            }
        });
    }
    var query = function () {
        var params = condition.getValues();
        table.query(params);
    };
    var reset = function () {
        condition.reset();
    };
    return app;
});