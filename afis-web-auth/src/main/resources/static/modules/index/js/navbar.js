
(function (factory) {
    if (typeof define == 'function' && define.amd) {
        define(["armutil", "popover", "cssl!/modules/index/css/navbar.css"], factory);
    }
}(function (util, popover) {
    var nav = function (container, options) {
        if (typeof options == 'string') {
            options = { "title": options }
        }
        this.options = $.extend({
            logo: "/images/logo.png",
            title: "管理系统",
            user: "",
            tipFunc: function (data) {
                return data;
            },
            userFunc: function (user) {
                return user;
            }
        }, options);
        this.component = {
            content: $('<div class="afis-navbar-content"></div>').appendTo(container)
        };
        this.container = container;
        this.tip = null;
        this.template();
    };
    nav.prototype.template = function () {
        var options = this.options,
            content = this.component.content;
        $.extend(true, this.component, {
            img: $('<img class="logo" src="' + options.logo + '" alt="">').appendTo(content),
            title: $('<span class="title">' + options.title + '</span>').appendTo(content),
            user: $('<ul class="user-info right">' +
                '<li>欢迎<span class="user red">' + options.user + '</span>登录本系统</li>' +
                '<li class="bell pointer"><button class="fa fa-bell"></button><div class="badge"></div></li>' +
                '<li class="key pointer"><i class="fa fa-key"></i><span>修改密码</span></li>' +
                '<li class="sign-out pointer"><i class="fa fa-sign-out"></i><span>退出</span></li>' +
                '</ul>').appendTo(content)
        });
        this.component.tip = this.component.content.find(".user-info .fa-bell");
        this.component.badge = this.component.content.find(".badge");
        content.on("click", "li.sign-out", { that: this }, this.signOut);
        content.on("click", "li.key", { that: this }, this.changepwd);
        this.setTip(this.options.tipData || []);
    }
    nav.prototype.setTip = function (list) {
        if (Object.prototype.toString.call(list) == '[object Array]') {
            this.tipData = list
        }
        var badge = this.component.badge;
        var len = this.tipData.length;
        if (len > 0) {
            var text = "";
            if (len >= 99) {
                text = "99+";
            } else {
                text = len;
            }
            badge.text(text);
        } else {
            badge.text("0");
        }
        if (this.tip == null) {
            var that = this,
                tipFunc = that.options.tipFunc;
            that.tip = new popover(that.component.tip, {
                placement: "bottom",
                trigger: "focus",
                content: function () {
                    var lis = "";
                    $.each(that.tipData, function (i, data) {
                        lis += ('<li>' + tipFunc.call(that, data) + '</li>');
                    });
                    if (lis.length == 0) {
                        return "";
                    } else {
                        return "<div class='bell-content'><ul>" + lis + "</ul></div>";
                    }
                }
            });
            that.tip.popover();
        }
    }
    nav.prototype.builder = function () {
        this.loadTipData();
        this.loadUserData();
        this.component.content.find("li.key").remove();
    }
    nav.prototype.signOut = function (e) {
        var that = e.data.that;
        location.href = "/logout";
    }
    nav.prototype.changepwd = function (e) {
        var that = e.data.that;
    }
    nav.prototype.loadTipData = function () {
        // this.setTip();
        this.component.content.find("li.bell").remove();
    }
    nav.prototype.setUser = function (data) {
        var user = this.component.user.find(".user");
        if (data) {
            if (typeof data == 'string') {
                user.text(data);
            } else {
                user.text(data.userName);
            }
        } else {
            user.text("");
        }
    }
    nav.prototype.loadUserData = function () {
        var that = this;
        //加载用户信息
        util.query("/me", function (info) {
            that.setUser(info);
        });
    }
    return nav;
}));