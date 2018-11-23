/**
 * 标准通讯组件
 */
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else {
        var AFIS = window.afis;
        if (!AFIS) {
            AFIS = window.afis = {};
        }
        AFIS.afismq = factory();
    }
}(function () {
    'use strict';
    var MQ = function (option) {
        this.options = $.extend(true, {}, MQ.DEFAULTS, option);
    }
    MQ.DEFAULTS = {
        unAuth: false
    }
    MQ.prototype.registeUnAuth = function (callback) {
        if (typeof callback === 'function') {
            this.unAuth = callback;
        }
    }
    MQ.prototype.error = function (callback) {
        if (typeof callback === 'function') {
            this.error = callback;
        }
    }
    MQ.prototype.request = function (type, url, data, success, error) {
    	var that = this;
        $.ajax({
            'url': url,
            'cache': false,
            'dataType': 'json',
            'type': type,
            'contentType': "application/json;charset=utf-8",
            'data': data,
            'success': function (data) {
                if (success) {
                    success(data);
                }
            },
            'error': (error) ? error : function (e) {
                var status = e.status;
                if (e.statusText = 'parsererror') {
                    status = 401;
                }
                var data = { 'responseCode': status };
                if (status == 404) {
                    data.responseDesc = "地址错误!";
                } else if (status == 401) {
                    data.responseDesc = "尚未登录!";
                    if (that.unAuth) {
                    	that.unAuth(data);
                        return;
                    }
                } else {
                    data.responseDesc = "服务器错误!";
                }
                if (success) {
                    success(data);
                }
            }
        });
    }
    MQ.prototype.callback = function (response, success, error, common) {
        var that = this;
        var code = response.responseCode;
        if (code == 0 && typeof success == 'function') {
            success(response.data);
        } else {
            var desc = response.responseDesc;
            var msg = desc ? (desc + "[" + code + "]") : ("操作失败[" + code + "]");
            if (typeof error == 'function') {
                error(msg, code, desc);
            } else {
                if (typeof error != 'boolean') {
                    if (typeof error == 'function') {
                        error(msg, code, desc);
                    }
                }
            }
        }
        if (typeof common == 'function') {
            common();
        }
    }
    MQ.prototype.createMessage = function (type, url, data) {
        var params = {};
        if (data != null) {
            if (type == "GET") {
                var _url = [];
                $.each(data, function (key, val) {
                    if (val != null && val !== "") {
                        _url.push(key + "=" + val);
                    }
                });
                if (_url.length > 0) {
                    params.url = url + "?" + _url.join("&");
                } else {
                    params.url = url;
                }
                params.data = null;
            } else {
                params.url = url;
                var _data = {};
                $.each(data, function (key, val) {
                    if (val != null && val !== "") {
                        _data[key] = val;
                    }
                });
                params.data = JSON.stringify(_data);
            }
        } else {
            params.url = url;
            params.data = null;
        }
        return params;
        // return $.extend(true, {
        //     'protocolVersion': protocolVersion,
        //     'clientVersion': clientVersion,
        //     'timestamp': (new Date()).valueOf()
        // }, data);
    }
    MQ.prototype.setRequest = function (type, url, data, success, error, common) {
        var that = this;
        if (typeof data == 'function') {
            common = error;
            error = success;
            success = data;
            data = null;
        }
        var params = that.createMessage(type, url, data);
        that.request(type, params.url, params.data, function (response) {
            that.callback(response, success, error, common);
        });
    }
    //查询
    MQ.prototype.query = function (url, data, success, error, common) {
        this.setRequest("GET", url, data, success, error, common);
    }
    //新增
    MQ.prototype.add = function (url, data, success, error, common) {
        this.setRequest("POST", url, data, success, error, common);
    }
    //post
    MQ.prototype.post = function (url, data, success, error, common) {
        this.setRequest("POST", url, data, success, error, common);
    }
    //修改 全量
    MQ.prototype.put = function (url, data, success, error, common) {
        this.setRequest("PUT", url, data, success, error, common);
    }
    //修改 部分
    MQ.prototype.patch = function (url, data, success, error, common) {
        this.setRequest("PATCH", url, data, success, error, common);
    }
    //删除
    MQ.prototype.delete = function (url, data, success, error, common) {
        this.setRequest("DELETE", url, data, success, error, common);
    }
    return new MQ();
}));
