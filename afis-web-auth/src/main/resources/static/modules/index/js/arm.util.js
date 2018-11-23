define(['util'], function (afisutil) {
    var prototype = afisutil.prototype;
    var publ = {};
    publ.warpperQuery = function () {
        console.log("query");
    }
    publ.builderModal = function (title, onConfirm, callback, name, width, option) {
        var options = $.extend({
            title: title,
            onConfirm: onConfirm,
            name: name,
            width: width
        }, option);
        var modal = new this.modal(options);
        if (callback) {
            var cont = $('<div class="afis"></div>').appendTo(modal.body);
            callback(cont);
        }
        return modal;
    };
    publ.query = function (messageType, params, success, error, common) {
        this.afismq.query(messageType, params, success, error, common)
    };
    publ.add = function (messageType, params, success, error, common) {
        var that = this;
        if (typeof params == 'function') {
            common = error;
            error = success;
            success = params;
            params = {};
        }
        that.afismq.add(messageType, params, success, error ? error : function (msg, code, desc) {
            that.popup.warning(desc + "[" + code + "]");
        }, common)
    };
    publ.put = function (messageType, params, success, error, common) {
        var that = this;
        if (typeof params == 'function') {
            common = error;
            error = success;
            success = params;
            params = {};
        }
        this.afismq.put(messageType, params, success, error ? error : function (msg, code, desc) {
            that.popup.warning(desc + "[" + code + "]");
        }, common)
    };
    publ.patch = function (messageType, params, success, error, common) {
        var that = this;
        if (typeof params == 'function') {
            common = error;
            error = success;
            success = params;
            params = {};
        }
        this.afismq.patch(messageType, params, success, error ? error : function (msg, code, desc) {
            that.popup.warning(desc + "[" + code + "]");
        }, common)
    };
    publ.delete = function (messageType, params, success, error, common) {
        var that = this;
        if (typeof params == 'function') {
            common = error;
            error = success;
            success = params;
            params = {};
        }
        this.afismq.delete(messageType, params, success, error ? error : function (msg, code, desc) {
            that.popup.warning(desc + "[" + code + "]");
        }, common)
    };
    publ.loadList = function (name, block, params, load) {
        var that = this, target;
        if (typeof params == 'function') {
            load = params;
            params = {};
        } else if (typeof block != 'string') {
            if (typeof block == 'function') {
                load = block;
                params = {};
            } else if (block != null) {
                load = params;
                params = block;
            }
            block = "COMMON";
        }
        // var dic = that.IENUM[block];
        // if (dic) {
        //     target = dic[name];
        // }
        if (target) {
            if (load) {
                load(target);
            }
            return target.select;
        } else {
            var options = {
                app: { idKey: "id", nameKey: "appName", url: "/auth/admin/applications/limit" },
                system: { idKey: "systemInfo", nameKey: "systemInfo", url: "/auth/admin/logs/systemInfos" },
                browser: { idKey: "browser", nameKey: "browser", url: "/auth/admin/logs/browserInfos" },
                LoadList: function (url, idKey, nameKey, callback, params) {
                    if (typeof callback != 'function') {
                        params = callback;
                        callback = null;
                    }
                    return {
                        idKey: idKey,
                        nameKey: nameKey,
                        loadList: function (func) {
                            that.query(url, params, function (data) {
                                var _data = [];
                                if (name == "system" || name == "browser") {
                                    _data = data.map(function (n) {
                                        var dic = {};
                                        dic[nameKey] = n;
                                        return dic;
                                    });
                                } else {
                                    _data = data;
                                }
                                if (callback) {
                                    callback(_data, idKey, nameKey);
                                }
                                func(_data);
                            });
                        }
                    }
                }
            }
            var option = options[name];
            if (option) {
                var idKey = option.idKey,
                    nameKey = option.nameKey;
                return options.LoadList(option.url, idKey, nameKey, function (data) {
                    if (data) {
                        var str = data.map(function (n) {
                            return n[idKey] + ":" + n[nameKey];
                        }).join(",");
                        if (that.IENUM[block] != null && that.IENUM[block][name] != null) {
                            var ienum = that.IENUM[block][name];
                            ienum.select = [];
                            ienum.dic = {};
                        }
                        target = that.IENUM.AddEnum(block, name).AddList(str);
                        if (load) {
                            load(target);
                        }
                    }
                }, params);
            } else {
                return null;
            }
        }
    };
    publ.loadAuto = function (name, option) {
        var options = {
            user: { idKey: "id", nameKey: "userName", postKey: "userName" }
        };
        var opt = options[name];
        if (opt) {
            return $.extend(opt, option);
        } else {
            return option;
        }
    }
    publ.readonly = function (form, names) {
        var _names = [];
        if (Object.prototype.toString.call(names) == '[object Array]' && names.length > 0) {
            _names = names;
        } else if (typeof names == 'string') {
            _names.push(names);
        }
        if (_names.length > 0) {
            $.each(_names, function (i, name) {
                form.readonly(name);
            });
        }
    }
    $.extend(true, prototype, publ);
    var util = new afisutil();
    util.afismq.unAuth = function () {
        location.href = "/";
    }

    // document.onkeydown = function (event) {
    //     if ((event.keyCode == 112) || //屏蔽 F1
    //         (event.keyCode == 113) || //屏蔽 F2
    //         (event.keyCode == 114) || //屏蔽 F3
    //         (event.keyCode == 115) || //屏蔽 F4
    //         (event.keyCode == 116) || //屏蔽 F5
    //         (event.keyCode == 117) || //屏蔽 F6
    //         (event.keyCode == 118) || //屏蔽 F7
    //         (event.keyCode == 119) || //屏蔽 F8
    //         (event.keyCode == 120) || //屏蔽 F9
    //         (event.keyCode == 121) || //屏蔽 F10
    //         (event.keyCode == 122) || //屏蔽 F11
    //         (event.keyCode == 123)) //屏蔽 F12
    //     {
    //         return false;
    //     }
    // }
    // window.onhelp = function () { return false; }

    return util;
});