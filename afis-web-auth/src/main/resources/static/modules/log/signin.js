define(['page', 'text!./signin.html', 'armutil'], function (page, html, util) {
    'use strict';
    var log = new page(html),
        content = null,
        condition = null,
        table = null,
        status = util.IENUM.AddEnum("signinLog", "status").AddList(":全选,1:成功,2:失败");

    log.init(function () {
        content = this.template;
        initForm();
        initTable();
    });
    var initForm = function () {
        condition = new util.form(content.find(".condition-form"));
        condition
            .addForm("INPUT", "userAccount", "用户账号")
            .addForm("INPUT", "userName", "用户名称")
            .addForm("SELECT", "loginAppName", "登入应用", util.loadList("app"))
            .addForm("RADIO", "status", "登入状态", { data: status.select, def: "" })
            .addForm("SELECT", "systemInfo", "操作系统", util.loadList("system"))
            .addForm("SELECT", "browserInfo", "浏览器", util.loadList("browser"))
            .addForm("RANGEDATE", { start: "loginTimeStart", end: "loginTimeEnd" }, "登入时间")
            .builder();

        new util.button(content.find(".condition-btn")).addBtn("查询", query).addBtn("重置", reset, "btn-reset").builder();
    };
    var initTable = function () {
        table = new util.table();
        table
            .addColumn("id", "ID")
            .addColumn("userAccount", "用户账号")
            .addColumn("userName", "用户名称")
            .addColumn("loginAppName", "登入应用")
            .addColumn("loginIp", "IP地址")
            .addColumn("status", "登入状态", util.parseStatus(status.dic))
            .addColumn("systemInfo", "操作系统")
            .addColumn("browserInfo", "浏览器")
            .addColumn("loginTime", "登入时间", util.parseDate())
            .addColumn("remark", "备注");

        table.fetchTable(content.find('.table-content'), { "messageType": '/auth/admin/logs/loginLogs' }, "log_signin");
    };
    var query = function () {
        var params = condition.getValues();
        table.query(params);
    };
    var reset = function () {
        condition.reset();
    };
    return log;
});