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
        baseUrl: '/ajax',
        unAuth: false
    }
    MQ.prototype.createMessage = function (messageType, request) {
        var request = {
            'protocolVersion': protocolVersion,
            'clientVersion': clientVersion,
            'timestamp': (new Date()).valueOf(),
            'service': messageType,
            'body': request != null ? request : {}
        };
        return request;
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
    MQ.prototype.post = function (request, messageType, success, error, url) {
        if (typeof messageType === 'string' && typeof success === 'function') {
            request = this.createMessage(messageType, request);
        } else {
            success = messageType;
            error = success;
        }
        $.ajax({
            'url': url != null ? url : this.options.baseUrl,
            'cache': false,
            'dataType': 'json',
            'method': 'post',
            'contentType': "application/json;charset=utf-8",
            'data': JSON.stringify(request),
            'success': function (data) {
                if (success) {
                    success(data);
                }
            },
            'error': (error) ? error : function (e) {
                var status = e.status;
                var data = { 'messageType': messageType, 'responseCode': status };
                if (status == 404) {
                    data.responseDesc = "地址错误!";
                } else if (status == 401) {
                    data.responseDesc = "尚未登录!";
                    if (this.unAuth) {
                        this.unAuth(data);
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
    MQ.prototype.query = function (request, messageType, success, error) {
        this.post(request, messageType, function (response) {
            var code = response.responseCode;
            if (code == 0) {
                success(response.data);
            } else {
                var desc = response.responseDesc;
                var msg = desc ? (desc + "[" + code + "]") : ("操作失败[" + code + "]");
                if (typeof error == 'function') {
                    error(msg, code, desc);
                } else {
                    if (typeof error != 'boolean') {
                        if (typeof that.error == 'function') {
                            that.error(msg, code, desc);
                        }
                    }
                }
            }
        });
    }
    return new MQ();
}));
