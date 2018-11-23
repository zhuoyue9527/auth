define(['page', 'text!./edit.html', 'armutil'], function (page, html, util) {
    'use strict';
    var user = new page(html),
        content = null,
        condition = null,
        table = null,
        tip = util.popup,
        clientType = util.IENUM.AddEnum("user", "clientType").AddList("1:认证管理员账号,2:应用账号"),
        status = util.IENUM.AddEnum("user", "status").AddList("1:正常,2:注销,3:冻结"),
        app = null;

    user.init(function () {
        content = this.template;
        initForm();
        initTable();
    });
    var initForm = function () {
        condition = new util.form(content.find(".condition-form"));
        condition.addForm("INPUT", "userAccount", "用户账号")
            .addForm("INPUT", "userName", "用户名称")
            .addForm("SELECT", "clientType", "类型", clientType.select)
            .addForm("SELECT", "status", "状态", status.select)
            .addForm("SELECT", "registerAPP", "注册应用", util.loadList("app", function (data) {
                app = data;
            }))
            .addForm("RANGEDATE", { start: "registerTimeStart", end: "registerTimeEnd" }, "注册时间")
            .builder();
        new util.button(content.find(".condition-btn")).addBtn("查询", query).addBtn("重置", reset, "btn-reset").builder();
    };
    var initTable = function () {
        table = new util.table();
        table.addColumn("id", "ID")
            .addColumn("userAccount", "用户账号")
            .addColumn("userName", "用户名称")
            .addColumn("mobile", "联系手机")
            .addColumn("clientType", "类型", util.parseStatus(clientType.dic))
            .addColumn("status", "状态", util.parseStatus(status.dic))
            .addColumn("registeAppName", "注册应用")
            .addColumn("registeOperateName", "注册操作员")
            .addColumn("registerTime", "注册时间", util.parseDate())
            .addColumn("operateAppName", "操作应用")
            .addColumn("operateName", "操作员")
            .addColumn("operateTime", "操作时间", util.parseDate())
            .addColumn("lastLoginTime", "最后登录时间", util.parseDate())
            .addColumn("lastLoginIp", "最后登入IP")
            .addColumn("remark", "备注");

        table.addBtn("新增", add, "fa fa-plus-square")
            .addBtn("修改", edit, "fa fa-pencil-square");
        table.fetchTable(content.find('.table-content'), { "messageType": '/auth/admin/users' }, content.find('div.table-normal>div.table-up-div'), "user_edit");
    };
    var query = function () {
        var params = condition.getValues();
        table.query(params);
    };
    var reset = function () {
        condition.reset();
    };
    //新增 修改
    var popupEdit = function (data, callback) {
        if (data != null && typeof data == 'function') {
            callback = data;
            data = null;
        }
        var fills = null;
        if (data == null) {
            fills = [
                { name: "userAccount", tip: "请填写用户账号" },
                { name: "userName", tip: "请填写用户名称" },
                { name: "password", tip: "请填写密码" },
                { name: "confirmPassword", tip: "请确认密码" },
                { name: "clientType", tip: "请选择类型" },
                { name: "registerAppId", tip: "请选择注册应用" }
            ];
        } else {
            fills = [
                { name: "userAccount", tip: "请填写用户账号" },
                { name: "userName", tip: "请填写用户名称" },
                { name: "registerAppId", tip: "请选择注册应用" }
            ];
        }
        var title = data == null ? "新增" : "修改",
            forms = null,
            modal = util.builderModal(title, onConfirm, function (cont) {
                var options = { fills: fills.map(function (n) { return n.name; }) };
                forms = new util.form(cont, options);
                forms
                    .addForm("INPUT", "userAccount", "用户账号")
                    .addForm("INPUT", "userName", "用户名称")
                    .addForm("INPUT", "password", "密码", { props: { type: "password" } })
                    .addForm("INPUT", "confirmPassword", "确认密码", { props: { type: "password" } })
                    .addForm("INPUT", "mobile", "联系手机")
                    .addForm("SELECT", "clientType", "类型", clientType.select)
                    .addForm("SELECT", "registerAppId", "注册应用", util.loadList("app", { status: 1 }))
                    .addForm("TEXTAREA", "remark", "备注")
                    .builder();
                forms.get("remark").component.content.css("width", "570px");
                if (data) {
                    util.readonly(forms, ["userAccount", "clientType", "registerAppId"]);
                    var values = $.extend({}, data, {
                        password: null,
                        registerAppId: null
                    });
                    values.clientType = clientType.dic[data.clientType];
                    if (app) {
                        values.registerAppId = app.dic[data.registerAppId];
                    }
                    forms.setValues(values);
                }
            }, "user_edit");

        function onConfirm(e) {
            var params = forms.getValues();
            if (data == null) {
                $.each(fills, function (i, n) {
                    var name = n.name;
                    if (!params[name]) {
                        tip.warning(n.tip);
                        params = null;
                        return false;
                    } else {
                        if (name == "password") {
                            if (!params["confirmPassword"]) {
                                tip.warning("请确认密码");
                                params = null;
                                return false;
                            } else {
                                if (params[name] != params["confirmPassword"]) {
                                    tip.warning("密码前后填写不一致");
                                    params = null;
                                    return false;
                                }
                            }
                        }
                    }
                });
            } else {
                if (!params["userName"]) {
                    tip.warning("请填写用户名");
                    params = null;
                    return false;
                }
                if (params["password"]) {
                    if (params["confirmPassword"] && params["password"] != params["confirmPassword"]) {
                        tip.warning("密码前后填写不一致");
                        params = null;
                        return false;
                    } else if (!params["confirmPassword"]) {
                        tip.warning("请确认密码");
                        params = null;
                        return false;
                    }
                }
            }
            if (params == null) {
                return;
            }
            if (params["mobile"] && !(/^1[0-9]\d{9}$/.test(params["mobile"]))) {
                return tip.warning("请输入正确的手机号");
            }
            if (params["remark"] != null && params["remark"] != "") {
                if (params["remark"].length > 100) {
                    return tip.warning("备注最大字符长度为100，目前为" + params["remark"].length);
                }
            }
            if (data == null) {
                util.add("/auth/admin/users", params, function (data) {
                    tip.success(function () {
                        modal.close();
                        if (callback) {
                            callback();
                        }
                    });
                });
            } else {
                params["userAccount"] = data["userAccount"];
                params["clientType"] = data["clientType"];
                params["registerAppId"] = data["registerAppId"];
                util.patch("/auth/admin/users/" + data["id"], params, function (data) {
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
    var add = function () {
        popupEdit(query);
    };
    var edit = function () {
        table.verifyRow(function (row) {
            popupEdit(row, query);
        }, "用户", "修改", function (row) {
            var s = row.status;
            if (s == 1) {
                return true;
            } else {
                tip.warning("用户已" + status.dic[s] + "不能进行修改操作!");
                return false;
            }
        });
    };
    return user;
});