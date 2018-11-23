define(['page', 'text!./manage.html', 'armutil'], function (page, html, util) {
    'use strict';
    var auth = new page(html),
        content = null,
        condition = null,
        table = null,
        tip = util.popup,
        warrantCondition = util.IENUM.AddEnum("auth", "warrantCondition").AddList(":全部,0:未授权,1:已授权"),
        warrant = util.IENUM.AddEnum("auth", "warrant").AddList("0:未授权,1:已授权");

    auth.init(function () {
        content = this.template;
        initForm();
        initTable();
    });
    var initForm = function () {
        condition = new util.form(content.find(".condition-form"));
        condition
            .addForm("INPUT", "userAccount", "用户账号")
            .addForm("INPUT", "userName", "用户名称")
            .addForm("RADIO", "warrant", "是否已授权", warrantCondition.select)
            .addForm("SELECT", "userAppId", "可授权应用", util.loadList("app"))
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
            .addColumn("appName", "应用")
            .addColumn("warrant", "是否已授权", util.parseStatus(warrant.dic))
            .addColumn("operateName", "操作员")
            .addColumn("operateTime", "操作时间", util.parseDate());

        table.addBtn("授权", authorize, "fa fa-gavel")
        table.fetchTable(content.find('.table-content'), { "messageType": '/auth/admin/appUsers' }, content.find('div.table-normal>div.table-up-div'), "auth_manage");
    };
    //授权
    var authorize = function () {
        table.verifyRow(function (row) {
            popupEdit(row, query);
        }, "授权管理", "授权", function (row) {
            return true;
        });
    }
    var popupEdit = function (data, callback) {
        if (typeof data == 'function') {
            callback = data;
            data = null;
        }
        var fills = ["warrant"],
            forms = null;

        var modal = util.builderModal("授权", onConfirm, function (cont) {
            var options = { fills: fills.map(function (n) { return n.name; }) };
            forms = new util.form(cont, options);
            forms
                .addForm("INPUT", "userAccount", "用户账号")
                .addForm("INPUT", "appName", "应用")
                .addForm("INPUT", "appPassword", "登录密码", { props: { type: "password" } })
                .addForm("INPUT", "confirm", "确认密码", { props: { type: "password" } })
                .addForm("RADIO", "warrant", "是否已授权", warrant.select)
                .builder();
            forms.setValues($.extend({}, data, { appPassword: null }));
            util.readonly(forms, ["userAccount", "appName"]);
        }, "auth-manage");

        function onConfirm(e) {
            var params = forms.getValues();
            if (params.appPassword) {
                if (params.appPassword !== params.confirm) {
                    tip.warning("密码输入不一致,请确认密码");
                    return;
                }
            }
            if (!params.warrant) {
                tip.warning("请确定是否授权");
                return;
            }
            params["appId"] = data.appId;
            params["userId"] = data.userId;
            util.patch("/auth/admin/appUsers/edit/" + data.id, params, function (data) {
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
    var query = function () {
        var params = condition.getValues();
        table.query(params);
    };
    var reset = function () {
        condition.reset();
    };
    return auth;
});