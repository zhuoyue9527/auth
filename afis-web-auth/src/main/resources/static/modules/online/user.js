define(['page', 'text!./user.html', 'armutil'], function (page, html, util) {
    'use strict';
    var online = new page(html),
        content = null,
        condition = null,
        table = null,
        tip = util.popup,
        clientType = util.IENUM.AddEnum("user", "clientType").AddList("1:认证管理员账号,2:应用账号");

    online.init(function () {
        content = this.template;
        initForm();
        initTable();
    });
    var initForm = function () {
        condition = new util.form(content.find(".condition-form"));
        condition
            .addForm("INPUT", "userAccount", "用户账号")
            .addForm("INPUT", "userName", "用户名称")
            .addForm("SELECT", "clientType", "类型", clientType.select)
            .addForm("SELECT", "registerAPP", "注册应用", util.loadList("app"))
            .addForm("RANGEDATE", { start: "registerTimeStart", end: "registerTimeEnd" }, "注册时间")
            .addForm("RANGEDATE", { start: "lastLoginTimeStart", end: "lastLoginTimeEnd" }, "最后登录时间")
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
            .addColumn("clientType", "类型", util.parseStatus(clientType.dic))
            .addColumn("registeAppName", "注册应用")
            .addColumn("registerOperateName", "注册操作员")
            .addColumn("registerTime", "注册时间", util.parseDate())
            .addColumn("operateAppName", "操作应用")
            .addColumn("operateName", "操作员")
            .addColumn("operateTime", "操作时间", util.parseDate())
            .addColumn("loginTime", "登录时间", util.parseDate())
            .addColumn("lastLoginTime", "最后登录时间", util.parseDate())
            .addColumn("lastLoginIp", "最后登入IP")
            .addColumn("remark", "备注");

        table.addBtn("踢出", signout, "fa fa-sign-out")
        table.fetchTable(content.find('.table-content'), { "messageType": '/auth/admin/onlineUsers' }, content.find('div.table-normal>div.table-up-div'), "online_user");
    };
    //踢出
    var signout = function () {
        table.verifyConfirmRow(function (row) {
            util.delete("/auth/admin/onlineUsers", { loginKey: row.loginKey, userAccount: row.userAccount }, function (data) {
                tip.success(query);
            });
        }, "在线用户", "踢出");
    }
    var query = function () {
        var params = condition.getValues();
        table.query(params);
    };
    var reset = function () {
        condition.reset();
    };
    return online;
});