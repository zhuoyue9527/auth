"use strict";
var protocolVersion = "0.1.2";
var clientVersion = "0.1.2";
requirejs.config({
    baseUrl: '/js/',
    urlArgs: clientVersion,
    paths: {
        //-base--
        jquery: 'afis.libs/jquery-3.2.1.min',
        bootstrap: 'afis.libs/bootstrap/js/bootstrap',
        datepicker: 'afis.libs/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min',
        confirm: 'afis.libs/jquery-confirm/jquery-confirm.min',
        toastr: 'afis.libs/toastr/toastr.min',
        dragsort: 'afis.libs/jquery.dragsort',
        xdate: 'afis.libs/xdate',
        //-requirejs--
        text: 'afis.libs/requirejs/text',
        cssl: 'afis.libs/requirejs/css.min',
        domReady: 'afis.libs/requirejs/domReady',
        //-component--
        tree: 'afis.common/form/afis.tree',
        date: 'afis.common/form/afis.date',
        select: 'afis.common/form/afis.select',
        multiselect: 'afis.common/form/afis.multiselect',
        formitem: 'afis.common/form/afis.formitem',
        forms: 'afis.common/form/afis.forms',
        check: 'afis.common/check/afis.check',
        list: 'afis.common/form/afis.list',
        cascader: 'afis.common/form/afis.cascader',
        autocomplate: 'afis.common/form/afis.autocomplate',
        button: 'afis.common/afis.button',
        ajaxupload: 'afis.common/afis.ajaxupload',
        table: 'afis.common/table/afis.table',
        //-页面--
        accordionmenu: 'afis.common/accordionmenu/accordionmenu',
        tab: 'afis.common/accordionmenu/afis.tab',
        page: 'afis.common/accordionmenu/afis.page',
        //-提示--
        popup: 'afis.common/popup/afis.popup',
        popover: 'afis.common/popup/afis.popover',
        popuplist: 'afis.common/popup/afis.popuplist',
        //-modules----------------------------
        tablesort: '/modules/index/js/afis.tablesort',
        tableWrapper: '/modules/index/js/afis.table.wrapper',
        formtype: '/modules/index/js/afis.formtype',
        component: '/modules/index/js/afis.component',
        util: '/modules/index/js/afis.util',
        armutil: '/modules/index/js/arm.util',
        afismq: '/modules/index/js/afis.mq'
    },
    priority: ['text'],
    shim: {
        bootstrap: { deps: ["jquery"] },
        datepicker: {
            deps: [
                "/js/afis.libs/bootstrap-datepicker/js/bootstrap-datepicker.min.js",
                "cssl!/js/afis.libs/bootstrap-datepicker/css/bootstrap-datepicker.min.css"
            ]
        },
        date: { deps: ["datepicker"] },
        accordionmenu: { deps: ["cssl!/js/afis.common/accordionmenu/css/accordionmenu.css"] },
        util: { deps: ["jquery"] },
        afismq: { deps: ["jquery"] },
        tab: { deps: ['cssl!/js/afis.common/accordionmenu/css/afis.tab.css'] },
        check: { deps: ['cssl!/js/afis.common/check/css/afis.check.css'] },
        component: {
            deps: [
                "bootstrap",
                "datepicker",
                "cssl!/js/afis.common/form/css/afis.form.css",
            ]
        },
        confirm: { deps: ["cssl!/js/afis.libs/jquery-confirm/jquery-confirm.min.css"] },
        toastr: { deps: ["cssl!/js/afis.libs/toastr/toastr.min.css"] },
        popup: {
            deps: [
                'cssl!/js/afis.common/popup/css/afis.popup.css',
                "toastr",
                "confirm"
            ]
        },
        popuplist: { deps: ["cssl!/js/afis.common/popup/css/afis.popuplist.css"] },
        tableWrapper: {
            deps: [
                "cssl!/js/afis.common/check/css/afis.check.css",
                "cssl!/js/afis.common/table/css/afis.table.css",
                "table"
            ]
        }
    }
});
var suffix = '/modules/index/js/';
define(['bootstrap'], function () {
    require([suffix + 'page-bg.js', suffix + 'navbar.js', suffix + 'navigate.js'], function (
        background, navbar, navigate) {
        'use strict';
        var title = "统一认证中心";
        //顶部导航条
        new navbar($(".afis .afis-navbar"), title).builder();
        //默认背景
        new background($(".afis .afis-index"), "欢迎登录," + title + ",管理系统");
        //菜单加载
        new navigate($(".afis .afis-menubar"), $(".afis .afis-index .afis-index-main"));
    });
});