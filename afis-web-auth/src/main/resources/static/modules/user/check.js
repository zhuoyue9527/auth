define(['page', 'text!./check.html', 'armutil'], function (page, html, util) {
    'use strict';
    var user = new page(html),
        content = null,
        condition = null,
        table = null,
        tip = util.popup,
        clientType = util.IENUM.AddEnum("user", "clientType").AddList("1:认证管理员账号,2:应用账号"),
        status = util.IENUM.AddEnum("user", "status").AddList("1:正常,2:注销,3:冻结");

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
            .addForm("SELECT", "registerAPP", "注册应用", util.loadList("app"))
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

        table.addBtn("注销", logout, "fa fa-times-circle")
            .addBtn("冻结", frozen, "fa fa-cloud")
            .addBtn("解冻", unfreeze, "fa fa-sun-o")
            .addBtn("解锁", unlock, "fa fa-unlock-alt");

        table.fetchTable(content.find('.table-content'), { "messageType": '/auth/admin/users' }, content.find('div.table-normal>div.table-up-div'), "user_check");
    };
    //注销
    var logout = function () {
        table.verifyConfirmRow(function (row) {
            util.patch("/auth/admin/users/cancel/" + row.id, function (data) {
                tip.success(query);
            });
        }, "用户", "注销", function (row) {
            var s = row.status;
            if (s != 2) {
                return true;
            } else {
                tip.warning("用户已注销");
                return false;
            }
        });
    }
    //冻结
    var frozen = function () {
        table.verifyConfirmRow(function (row) {
            util.patch("/auth/admin/users/frozen/" + row.id, function (data) {
                tip.success(query);
            });
        }, "会员", "冻结", function (row) {
            if (row.status == 1) {
                return true;
            } else {
                tip.warning("非正常状态的用户不可以执行冻结操作");
                return false;
            }
        });
    }
    //解冻
    var unfreeze = function () {
        table.verifyConfirmRow(function (row) {
            util.patch("/auth/admin/users/thaw/" + row.id, function (data) {
                tip.success(query);
            });
        }, "会员", "解冻", function (row) {
            if (row.status == 3) {
                return true;
            } else {
                tip.warning("只有被冻结的用户才能执行解冻操作");
                return false;
            }
        });
    }
    //解锁
    var unlock = function () {
        table.verifyConfirmRow(function (row) {
            util.patch("/auth/admin/users/unlock/" + row.id, function (data) {
                tip.success(query);
            });
        }, "会员", "解锁", function (row) {
            if (row.status == 1) {
                return true;
            } else {
                tip.warning("只有正常状态的用户才能执行解锁操作");
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
    return user;
});