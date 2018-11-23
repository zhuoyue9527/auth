(function (factory) {
    define(["accordionmenu", "tab"], factory);
}(function (accordionmenu, navigate) {
    var nav = function (menu, page, options) {
        this.options = $.extend({}, options);
        this.component = {
            menu: menu,
            page: page
        };
        this.init();
        this.loadMenu();
    }
    nav.prototype.init = function () {
        var that = this,
            menu = this.component.menu,
            page = this.component.page;
        this.menu = new accordionmenu(menu, {
            idKey: "id",
            pIdKey: "parentId",
            nameKey: "name",
            check: true,
            parse: true
        });
        this.tab = new navigate(page, {
            idKey: "id",
            titleKey: "name",
            parse: function (node) {
                var data = {}, url = "";
                $.each(node, function (key, val) {
                    if (key != "function") {
                        data[key] = val;
                    } else {
                        url = val;
                    }
                });
                return {
                    url: url,
                    data: data
                }
            }
        });
        menu.on("accordionmenu_click", function (e) {
            var node = e.node;
            if (node.function) {
                that.tab.guide(node);
            }
        })
        page.hide();
        this.tab.shown(function () {
            page.show();
        });
        this.tab.hidden(function () {
            that.menu.clear();
            page.hide();
        });
        this.tab.active(function (page) {
            that.menu.expand(page.id);
        });
    }
    nav.prototype.loadMenu = function () {
        var list = [
            { "parentId": 0, "function": null, "name": "用户维护", "id": 1, "icon": "fa fa-users" },
            { "parentId": 1, "function": "/modules/user/edit.js", "name": "用户编辑", "id": 101 },
            { "parentId": 1, "function": "/modules/user/check.js", "name": "用户管理", "id": 102 },
            { "parentId": 0, "function": null, "name": "在线用户", "id": 2, "icon": "fa fa-television" },
            { "parentId": 2, "function": "/modules/online/user.js", "name": "在线管理", "id": 201 },
            { "parentId": 0, "function": null, "name": "日志管理", "id": 3, "icon": "fa fa-list-alt" },
            { "parentId": 3, "function": "/modules/log/operate.js", "name": "操作日志", "id": 301 },
            { "parentId": 3, "function": "/modules/log/signin.js", "name": "登录日志", "id": 302 },
            { "parentId": 0, "function": null, "name": "应用管理", "id": 4, "icon": "fa fa-th" },
            { "parentId": 4, "function": "/modules/app/manage.js", "name": "应用管理", "id": 401 },
            { "parentId": 0, "function": null, "name": "用户授权", "id": 5, "icon": "fa fa-gavel" },
            { "parentId": 5, "function": "/modules/auth/manage.js", "name": "授权管理", "id": 501 },
            { "parentId": 0, "function": null, "name": "授权权限", "id": 6, "icon": "fa fa-superpowers" },
            { "parentId": 6, "function": "/modules/restrict/manage.js", "name": "权限管理", "id": 601 }];
        this.menu.setList(list);
    }
    return nav;
}));