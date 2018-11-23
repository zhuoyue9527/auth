define(['page', 'text!./operate.html', 'armutil'], function (page, html, util) {
    'use strict';
    var log = new page(html),
        content = null,
        condition = null,
        table = null,
        status = util.IENUM.AddEnum("operateLog", "status").AddList(":全选,1:成功,2:失败");

    log.init(function () {
        content = this.template;
        initForm();
        initTable();
    });
    var initForm = function () {
        condition = new util.form(content.find(".condition-form"));
        condition
            .addForm("INPUT", "functionName", "功能")
            .addForm("RADIO", "status", "操作状态", { data: status.select, def: "" })
            .addForm("SELECT", "operateAppId", "操作应用", util.loadList("app"))
            .addForm("INPUT", "operator", "操作员")
            .addForm("RANGEDATE", { start: "startDay", end: "endDay" }, "操作时间")
            .builder();

        new util.button(content.find(".condition-btn")).addBtn("查询", query).addBtn("重置", reset, "btn-reset").builder();
    };
    var initTable = function () {
        table = new util.table();
        table.addColumn("id", "ID")
            .addColumn("functionName", "功能名称")
            .addColumn("loginIp", "IP地址")
            .addColumn("status", "操作状态", util.parseStatus(status.dic))
            .addColumn("operateAppName", "操作应用")
            .addColumn("operateName", "操作员")
            .addColumn("operateTime", "操作时间", util.parseDate())
            .addColumn("remark", "备注");
        table.fetchTable(content.find('.table-content'), { "messageType": '/auth/admin/logs/operateLogs' }, "log_operate");
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