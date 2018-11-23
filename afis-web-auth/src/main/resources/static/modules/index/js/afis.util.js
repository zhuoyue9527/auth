(function (factory) {
  if (typeof define === 'function' && define.amd) {
    define(['afismq', 'tableWrapper', 'popup', 'popover', 'component', 'button', 'ajaxupload', 'xdate'], factory);
  } else {
    var AFIS = window.afis;
    if (AFIS) {
      AFIS.util = factory(AFIS.afismq, AFIS.table, AFIS.popup, AFIS.popover, AFIS.component, AFIS.button);
    }
  }
}(function (afismq, table, popup, popover, component, button) {
  'use strict';
  var afisUtil = function () {
    this.afismq = afismq;
    this.table = table;
    this.modal = popup;
    this.popover = popover;
    this.form = component;
    this.button = button;
    this.popup = new popup();
  };
  //--枚举-
  afisUtil.prototype.IENUM = {
    AddEnum: function (block, field) {
      if (this[block] == null) {
        this[block] = {};
      }
      if (this[block][field] == null) {
        this[block][field] = {
          name: field,
          AddList: function (key, text) {
            if (this.select == null) {
              this["select"] = [];
              this["dic"] = {};
            }
            if (text != null) {
              this.select.push({ "text": text, "value": key });
              this.dic[key] = text;
            } else {
              var that = this;
              that["select"] = key.split(",").map(function (n) {
                var m = n.split(":"), v = m[0], k = m[1];
                that.dic[v] = k;
                return { "text": k, "value": v }
              });
            }
            return this;
          }
        };
      }
      return this[block][field];
    }
  };
  //--转义-
  afisUtil.prototype.parseStatus = function (status) {
    return function (val) {
      if (val == null || status == null) {
        return "";
      } else {
        var state = status[val];
        if (state) {
          return state;
        } else {
          return val;
        }
      }
    }
  }
  afisUtil.prototype.parseDate = function (sort) {
    return function (time) {
      if (!time) {
        return "";
      }
      if (XDate) {
        var formatter = "yyyy-MM-dd HH:mm:ss";
        if (sort) {
          formatter = "yyyy-MM-dd";
        }
        return time != null ? new XDate(time).toString(formatter) : "";
      } else {
        return time;
      }
    };
  }
  afisUtil.prototype.isNumber = function (target) {
    return typeof target === 'number';
  }
  afisUtil.prototype.parseNumber = function (number, multiple, precision, enlarge) {
    var that = this;
    if (!that.isNumber(number) || !that.isNumber(multiple) || !that.isNumber(precision)) {
      return null;
    }
    if (enlarge) {
      return (parseFloat(number) * multiple).toFixed(precision);
    } else {
      return (parseFloat(number) / multiple).toFixed(precision);
    }
  }
  afisUtil.prototype.parseUnit = function (unit, target) {
    //target 0:重量,1:金额,2:费率
    if (typeof unit === 'string' && unit) {
      return "(" + unit + ")";
    } else {
      return "";
    }
  }
  afisUtil.prototype.parseWeight = function (weight, unit) {
    var number = this.parseNumber(weight, 1000, 3, false);
    if (number) {
      return number + this.parsePrice(unit, 0);
    } else {
      return "";
    }
  }
  afisUtil.prototype.reWeight = function (weight) {
    var number = this.parseNumber(weight, 1000, 0, true);
    if (number) {
      return number;
    } else {
      return 0;
    }
  }
  afisUtil.prototype.parsePrice = function (price, unit) {
    var number = this.parseNumber(price, 100, 2, false);
    if (number) {
      return number + this.parsePrice(unit, 1);
    } else {
      return "";
    }
  }
  afisUtil.prototype.rePrice = function (price) {
    var number = this.parseNumber(price, 100, 0, true);
    if (number) {
      return number;
    } else {
      return 0;
    }
  }
  afisUtil.prototype.parseRate = function (rate, unit) {
    var number = this.parseNumber(rate, 10000, 4, false);
    if (number) {
      return number + this.parsePrice(unit, 3);
    } else {
      return "";
    }
  }
  afisUtil.prototype.reRate = function (rate) {
    var number = this.parseNumber(rate, 10000, 0, true);
    if (number) {
      return number;
    } else {
      return 0;
    }
  }
  afisUtil.prototype.popListOver = function (target, options) {
    if (!options) {
      options = target;
      target = null;
    }
    var that = this;
    var publ = {};
    publ.options = $.extend({
      titleKey: "title",
      txtKey: "txt",
      text: "",
      trigger: "focus",
      rowCount: 15,
      maxCol: 5,
    }, options);
    publ.target = target;
    publ.popover = function (target) {
      var list = this.options.list;
      if (Object.prototype.toString.call(list) != '[object Array]') {
        return;
      }
      if (list.length == 0) {
        return;
      }
      var options = this.options;
      var title = options.title;
      if (target) {
        this.target = target;
      }
      var len = list.length;
      var unit = options.rowCount;
      var division = parseInt(len / unit);
      var maxCol = options.maxCol;

      if (division > maxCol || (division == maxCol && len % unit != 0)) {
        var cont = this.builderTable(list, maxCol + 1);
        var tipPop = new that.modal({
          title: title,
          isConfirm: false,
          templates: { dialog: '<div class="modal-dialog" role="document" style="width:' + ($("body").width() - 100) + 'px;"></div>' }
        });
        cont.css({ "width": "100%" });
        cont.appendTo(tipPop.body);
        this.target.click(function () {
          tipPop.showModal();
        });
      } else {
        var pop = this;
        var builder = function () {
          var cont = null;
          division = parseInt(len / unit);
          if (len > 0) {
            if (division < 1) {
              cont = $('<ul class="popover-list-group"></ul>');
              $.each(list, function (i, n) {
                var li = $('<li class="popover-list-item"><span class="title"></span><span class="txt"></span></li>').appendTo(cont);
                var title = n[options.titleKey];
                if (!title) {
                  li.find(".title").remove();
                } else {
                  li.find(".title").text(title + ":");
                }
                li.find(".txt").text(n[options.txtKey]);
              });
            } else {
              division = len % unit == 0 ? division : division + 1;
              cont = pop.builderTable(list, division);
            }
          } else {
            cont = $('<span class="txt">暂无数据...</span>');
          }
          return cont;
        }
        options["content"] = builder;
        new popover(target, options).popover();
      }
    }
    publ.builderTable = function (list, division) {
      var titleKey = this.options.titleKey;
      var txtKey = this.options.txtKey;

      var cont = $("<table class='popover-table' ><tbody></tbody></table>");
      var tbody = cont.find("tbody");

      var tr = $('<tr class="popover-list-item"></tr>').appendTo(tbody);
      $.each(list, function (i, n) {
        var td = $('<td class="popover-list-item"><span class="title"></span><span class="txt"></span></td>').appendTo(tr);
        var title = n[titleKey];
        if (!title) {
          td.find(".title").remove();
        } else {
          td.find(".title").text(title + ":");
        }
        td.find(".txt").text(n[txtKey]);
        var index = i + 1;
        if (index % division == 0) {
          tr = $('<tr class="popover-list-item"></tr>').appendTo(tbody);
        }
      });
      return cont;
    }
    return publ;
  }
  //--跳转-
  afisUtil.prototype.jumpTo = function (filePath) {
    $("a.jump-export-to").remove();
    if (typeof filePath == 'string' && filePath.length > 0) {
      var a = $('<a style="margin-left:-1000px" class="jump-export-to" href="' + filePath + '" target="_bank"><span>跳转</span></a>').appendTo($("body"));
      a.find("span").trigger("click");
    }
  };
  //--上传-
  afisUtil.prototype.ajaxUpload = function (target, options) {
    if (window.AjaxUpload == null) {
      return;
    }
    new AjaxUpload(target, {
      action: options.action || '/upload',
      value: options.value || 'basePath',
      name: options.name,
      responseType: options.responseType,
      flag: options.flag,
      unique: options.unique,
      local: options.local,
      wrong: "文件导入失败",
      outsize: "导入文件大小过大,请控制文件大小在20M以内",
      onSubmit: function (file, ext) {
        if (options.onSubmit != null && typeof options.onSubmit == 'function') {
          return options.onSubmit(file, ext);
        } else {
          return true;
        }
      },
      onComplete: function (file, response) {
        if (options.onComplete != null && typeof options.onComplete == 'function') {
          return options.onComplete(file, response);
        } else {
          return true;
        }
      }
    });
  };
  afisUtil.prototype.warning = function (msg) {
    this.popup.warning(msg);
  };
  //name 字段名称 action 接口名称
  afisUtil.prototype.uploadFile = function (target, name, action, callback, unique) {
    var that = this;
    var tip = this.popup;
    var setting = {
      action: action,
      name: name,
      value: 'temp/import',
      responseType: 'json',
      flag: 1,
      unique: unique,
      local: false,
      onSubmit: function (file, ext) {
        //rar|zip|pdf|pdfx|txt|csv|xls|xlsx|doc|docx|RAR|ZIP|PDF|PDFX|TXT|CSV|XLS|XLSX|DOC|DOCX
        if (!(ext && /^(xls|xlsx)$/.test(ext))) {
          tip.warning("您上传的文档格式不对，请重新选择");
          return false;
        }
      },
      onComplete: function (file, response) {
        var code = response.responseCode;
        if (code == 0) {
          if (callback && typeof callback == 'function') {
            var data = response.data;
            callback(data);
            return;
          }
        } else {
          var desc = response.responseDesc;
          tip.warning(desc + "[" + code + "]");
        }
      }
    }
    that.ajaxUpload(target, setting);
  };
  afisUtil.prototype.uploadImg = function (target, callback, unique) {
    var that = this;
    var tip = this.popup;
    var setting = {
      action: '/upload',
      name: 'basePath',
      value: 'temp/picture',
      responseType: 'json',
      flag: 2,
      unique: unique,
      local: true,
      onSubmit: function (file, ext) {
        //(jpg|JPG|png|PNG|gif|GIF)
        if (!(ext && /^(jpg|JPG|jpeg|JPEG|png|PNG|bmp|BMP|gif|GIF)$/.test(ext))) {
          tip.warning("您上传的图片格式不对，请重新选择");
          return false;
        }
      },
      onComplete: function (file, response) {
        var code = response.responseCode;
        if (code == 0) {
          if (callback && typeof callback == 'function') {
            var data = response.data;
            callback(data.basePath.name, data.basePath.originalFilename);
            return;
          }
        } else {
          var desc = response.responseDesc;
          tip.warning(desc + "[" + code + "]");
        }
      }
    }
    that.ajaxUpload(target, setting);
  };
  afisUtil.prototype.ajaxUploadClear = function (unique) {
    $("[ajaxupload_unique='" + unique + "']").remove();
  };
  return afisUtil;
}));
