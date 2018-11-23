define(['page', 'text!./manage.html', 'armutil', 'list', 'popover', 'cssl!../index/css/manage.css'], function (page, html, util, list, popover) {
    'use strict';
    var restrict = new page(html),
        content = null,
        condition = null,
        table = null,
        tip = util.popup,
        warrant = util.IENUM.AddEnum("auth", "warrant").AddList("0:未授权,1:已授权");

    restrict.init(function () {
        content = this.template;
        initForm();
        initTable();
    });
    var initForm = function () {
        condition = new util.form(content.find(".condition-form"));
        condition
            .addForm("INPUT", "userAccount", "用户账号")
            .addForm("INPUT", "userName", "用户名称")
            .addForm("SELECT", "appId", "注册应用", util.loadList("app"))
            .builder();

        new util.button(content.find(".condition-btn")).addBtn("查询", query).addBtn("重置", reset, "btn-reset").builder();
    };
    var initTable = function () {
        table = new util.table();
        table
            .addColumn("id", "ID")
            .addColumn("userAccount", "用户账号")
            .addColumn("userName", "用户名称")
            .addColumn("mobile", "联系手机")
            .addColumn("appName", "应用名称")
            .addColumn("warrant", "是否已授权", util.parseStatus(warrant.dic))
            .addColumn("operateName", "操作员")
            .addColumn("operateTime", "操作时间", util.parseDate())
            .addColumn("remark", "备注");

        table.addBtn("添加", add, "fa fa-plus-square")
            .addBtn("删除", del, "fa fa-trash")
        var options = {
            "messageType": '/auth/admin/userPermissons',
            "checkboxes": false,
            "rowClicked": function (data) {
                var row = data.row;
            },
            "onInit": function () {
            }
        };
        table.fetchTable(content.find('.table-content'), options, content.find('div.table-normal>div.table-up-div'), "restrict_manage");
    };

    var appFunctionList = function (content) {
        var List = new list(content, {
            idKey: "id",
            nameKey: "appName",
            check: true,
            link: true,
            panelKey: "appList",
            panelParse: function (data, opt) {
                var that = this;
                var idKey = opt.idKey;
                var content = opt.content;
                var id = content.attr("id");
                var check = opt.check;
                var table = $('<table class="table"></table>').appendTo(content);
                var tr = $('<thead><tr><th>选择</th></tr></thead>').appendTo(table).find("tr");
                $.each(["id", "应用授权码", "应用名称", "秘钥", "回跳URL", "logo", "备注"], function (i, name) {
                    $("<th>" + name + "</th>").appendTo(tr);
                });
                var tbody = $('<tbody></tbody>').appendTo(table);
                $.each(data, function (j, n) {
                    var tr = $('<tr></tr>').appendTo(tbody);
                    $('<td>' + that.component.check(check, n[idKey]) + '</td>').appendTo(tr);
                    $.each(["id", "appCode", "appName", "key", "urlCallback", "logoPath", "remark"], function (i, name) {
                        var td = $('<td td-name="' + name + '"></td>').appendTo(tr);
                        var val = n[name];
                        if (i == 5) {
                            if (val != null && val !== "") {
                                var btn = $('<i class="fa fa-image"></i>').appendTo(td);
                                btn.click(function (e) {
                                    e.stopPropagation();
                                });
                                new popover(btn, {
                                    trigger: "hover",
                                    placement: "left",
                                    viewport: { selector: '#' + id + '.panel-body table.table', padding: 8 },
                                    content: function () {
                                        var div = $('<div class="popover-img" style="width:102px;height:102px;"></div>');
                                        $('<img src="' + val + '" alt="logo">').appendTo(div);
                                        return div;
                                    }
                                }).popover();
                            }
                        } else {
                            td.text(val != null ? val : "");
                        }
                    });
                });
            },
            collapse: true,
        });
        var listData = [];
        var group = { "id": new Date().getTime(), "appName": "应用列表", "appList": [] };
        util.query("/auth/admin/applications/limit", function (data) {
            group.appList = data;
            listData.push(group);
            List.addList(listData, function () {
                var toggle = this.component.content.find("[data-toggle='collapse']");
                toggle.removeAttr("href").removeAttr("data-toggle").find(".glyphicon").remove();
            });
        });
        return List;
    }
    var loadUserFunctionList = function (id, func) {
        util.query('/auth/admin/appUsers/userApps/' + id, function (data) {
            var selected = [];
            if (Object.prototype.toString.call(data) == '[object Array]' && data.length > 0) {
                selected = data.map(function (n) {
                    return n.appId;
                });
            }
            func(selected);
        });
    }
    //新增
    var popupEdit = function (data, callback) {
        if (data != null && typeof data == 'function') {
            callback = data;
            data = null;
        }
        var title = "管理应用授权权限",
            forms = null,
            userInfo = null,
            list = null,
            modal = util.builderModal(title, onConfirm, function (cont) {
                cont.addClass("afis app-manage left");
                var funcInfo = $('<div class="func-info"></div>').appendTo(cont);
                var appInfo = $('<div class="app-info"><div class="user-select"></div></div>').appendTo(cont);
                var logo = $('<div class="app-logo left"><div><img src="/images/default.png" title="" alt=""></div></div>').appendTo(appInfo).find("img");
                logo.parents(".app-logo").hide();
                var appInfoContent = $('<div class="app-info-content left"></div>').appendTo(appInfo);
                var options = { fills: ["userId"] };
                forms = new util.form(appInfoContent, options);
                forms
                    .addForm("AUTOCOMPLATESELECT", "userId", "用户", util.loadAuto("user", {
                        inside: true,
                        load: function (func, params) {
                            util.query('/auth/admin/users/vague', params, function (data) {
                                func(data);
                            })
                        }, onSelected: function (user) {
                            userInfo = user;
                            forms.setValues(userInfo);
                            loadUserFunctionList(userInfo.id, function (selected) {
                                list.setValue(selected);
                            });
                        }
                    }))
                    .addForm("INPUT", "userAccount", "用户账号")
                    .addForm("INPUT", "userName", "用户名称")
                    .addForm("INPUT", "mobile", "联系手机")
                    // .addForm("INPUT", "registeAppName", "注册应用")
                    .addForm("TEXTAREA", "remark", "备注")
                    .builder(function (form) {
                        if (form.option == null) {
                            form.option = {};
                        }
                        if (form.name == "userId") {
                            form.content = appInfo.find(".user-select");
                        } else {
                            form.option.state = "readonly";
                        }
                        return form;
                    });
                $('<div class="func-mask left"><div class="func-content left afis-collapse"></div></div>').appendTo(funcInfo);
                list = appFunctionList(funcInfo.find(".func-content"));
                if (data) {
                    logo.attr("src", data.logo);
                    forms.setValues(data);
                } else {
                    logo.attr("src", "/images/icon/100 (0).jpg");
                }
            }, "restrict-manage", "larger");

        function onConfirm(e) {
            var params = {};
            if (!userInfo) {
                tip.warning("请选择一个用户");
                return;
            }
            params.userId = userInfo.id;
            params.appIdList = list.getValue();
            util.add("/auth/admin/userPermissons", params, function (data) {
                tip.success(function () {
                    modal.close();
                    if (callback) {
                        callback();
                    }
                });
            });
        }
        modal.showModal();
    };
    //添加
    var add = function () {
        popupEdit(query);
    }
    //删除
    var del = function () {
        table.verifyConfirmRow(function (row) {
            util.delete("/auth/admin/userPermissons/" + row.id, function (data) {
                tip.success(query);
            });
        }, "用户", "删除");
    }
    var query = function () {
        var params = condition.getValues();
        table.query(params);
    };
    var reset = function () {
        condition.reset();
    };
    return restrict;
});