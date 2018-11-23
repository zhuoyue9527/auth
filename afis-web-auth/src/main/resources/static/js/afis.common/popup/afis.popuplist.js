(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(["popup", "afismq"], factory);
    } else {
        var AFIS = window.afis;
        if (AFIS) {
            AFIS.popupList = factory(AFIS.popup, AFIS.afismq);
        }
    }
}(function (popup, afismq) {
    var popupList = function (options) {
        this.options = $.extend(true, {}, {
            idKey: "id",
            nameKey: "name",
            postKey: "",
            messageType: "",
            params: "",
            format: null,
            filter: null,
            dataKey: "data-item",
            selected: [],
            limit: null,
            list: [],
            def: "",
            isPagePost: true,
            isInitQuery: true,
            pageLimit: 15,
            pageSplit: 9
        }, options);
        var limit = this.options.limit
        this.options.limit = typeof limit == 'number' && parseInt(limit) >= 1 ? parseInt(limit) : null;
        this.options.type = this.options.limit != 1 ? "checkbox" : "radio";
        if (!this.options.format) {
            var that = this;
            that.options.format = function (data) {
                return data[that.options.nameKey];
            }
        }
        this.store = {
            selected: {}
        };
        this.template();
        var that = this;
        var selected = that.options.selected;
        if (that.options.type == 'checkbox') {
            $.each(selected, function (i, id) {
                that.store.selected[id] = true;
            });
        } else {
            var id = Object.prototype.toString.call(selected) == '[object Array]' ? selected[0] : selected;
            that.store.selected[id] = true;
        }
    };
    popupList.prototype.template = function () {
        var template = $(
            '<table class="popup-list">' +
            '<tbody>' +
            '<td class="popup-content load-data">' +
            '<div class="form-group"><input type="text" class="form-control popup-list-input"><i class="glyphicon glyphicon-search popup-list-search"></i></div>' +
            '<div class="input-group count"><span>搜索结果：<strong></strong></span></div>' +
            '<div class="list-group"></div>' +
            '<nav aria-label="Page navigation"><ul class="pagination"></ul></nav>' +
            '</td>' +
            '<td class="popup-content selected-data">' +
            '<div class="input-group count"><span>已选：<strong></strong></span></div>' +
            '<div class="list-group"></div>' +
            '</td>' +
            '</tbody>' +
            '</table>');
        this.component = $.extend(true, this.component, {
            template: template,
            loadContent: template.find(".popup-content.load-data .list-group"),
            input: template.find(".popup-content.load-data .popup-list-input"),
            btn: template.find(".popup-content.load-data .popup-list-search"),
            lcount: template.find(".popup-content.load-data .count strong"),
            page: template.find(".popup-content.load-data .pagination"),

            selectedContent: template.find(".popup-content.selected-data .list-group"),
            scount: template.find(".popup-content.selected-data .count strong")
        });
        var def = this.options.def;
        if (def) {
            this.component.input.val(def);
        }
    }
    popupList.prototype.initView = function () {
        var that = this;
        var component = that.component;
        var loadContent = component.loadContent;
        var isCheck = that.options.type == "checkbox";
        loadContent.on("change", "input", { that: that, isCheck: isCheck }, that.onChange);
        component.btn.on("click", { that: that }, that.onSearch);
        if (that.options.isPagePost) {
            component.page.on("click", "li[page-index]", { that: that }, that.pageQuery);
            component.page.on("click", "a[page-index]", { that: that }, that.pageQuery);
        }
        var selectedContent = component.selectedContent;
        selectedContent.on("click", ".list-group-item .badge", { that: that }, that.onClose);
    }
    popupList.prototype.onSearch = function (e) {
        var that = e.data.that;
        var search = that.component.input.val();
        that.post(search);
    }
    popupList.prototype.post = function (search) {
        var that = this;
        var messageType = that.options.messageType;
        if (!messageType) {
            return;
        }
        if (that.options.isPagePost) {
            that.pageQuery();
        } else {
            var params = $.extend(true, {}, that.options.params);
            var postKey = that.options.postKey;
            if (postKey) {
                if (!search) {
                    search = that.component.input.val();
                }
                params[postKey] = search;
            }
            afismq.query(params, messageType, function (data) {
                that.filter(data);
            }, false);
        }
    }
    popupList.prototype.filter = function (data) {
        var that = this;
        var list = data;
        var filter = that.options.filter;
        if (typeof filter === 'function') {
            list = filter(data);
        }
        if (Object.prototype.toString.call(list) == '[object Array]' && list.length > 0) {
            that.addList(list);
        }
    }
    popupList.prototype.pageQuery = function (e) {
        var that = null, start = 0;
        if (e == null) {
            that = this;
        } else {
            that = e.data.that;
            start = (parseInt($(this).attr("page-index")) - 1) * that.options.pageLimit;
        }
        var params = $.extend(true, {}, that.options.params);
        var postKey = that.options.postKey;
        if (postKey) {
            params[postKey] = that.component.input.val();
        }
        params["start"] = start;
        params["limit"] = that.options.pageLimit;
        afismq.query(params, that.options.messageType, function (data) {
            that.setPage(data)
        }, false);
    }
    popupList.prototype.setPage = function (data) {
        var that = this;
        var page = that.component.page;
        var list = data.result;
        var start = data.start;
        var limit = data.limit;
        var totalSize = data.totalSize;
        page.children().remove();
        page.removeClass("empty");
        if (totalSize == 0) {
            page.addClass("empty");
            var li = $('<li><span>暂无数据</span></li>').appendTo(page);
        } else {
            var index = parseInt(start / limit) + 1
            var split = that.options.pageSplit;
            var total = totalSize % limit == 0 ? parseInt(totalSize / limit) : parseInt(totalSize / limit) + 1;
            var i = 1, len = total;
            if (total > split) {
                var half = Math.floor(split / 2);
                if (index + half > total) {
                    i = total - split;
                } else if (index - half < 0) {
                    len = split;
                } else {
                    i = index - half;
                    len = index + half;
                }
            }
            $('<li page-index="1"><a href="#" aria-label="Previous"><span aria-hidden="true">首页</span></a></li>').appendTo(page);
            for (; i <= len; i++) {
                $('<li><a page-index="' + i + '" href="#">' + i + '</a></li>').appendTo(page);
            }
            $('<li page-index="' + total + '"><a href="#" aria-label="Next"><span aria-hidden="true">尾页</span></a></li>').appendTo(page);
            page.find("a[page-index='" + index + "']").closest("li").addClass("active");
        }
        that.addList(list);
    }
    popupList.prototype.onSelected = function (data, content) {
        var that = this;
        var id = data[that.options.idKey];
        if (!content) {
            content = that.component.selectedContent;
        }
        var hasItem = that.hasItem(id, content);
        if (!hasItem) {
            var format = that.options.format;
            if (!that.addSelectedItem(data, format, content)) {
                this.cancelItem(null, id);
            }
        }
    }
    popupList.prototype.onUnSelected = function (id, content) {
        var that = this;
        if (!content) {
            content = that.component.selectedContent;
        }
        var hasItem = that.hasItem(id, content);
        if (hasItem) {
            that.removeItem(id, content);
        }
    }
    popupList.prototype.onChange = function (e) {
        var that = e.data.that;
        var target = $(this).closest("[data-id]");
        var isCheck = e.data.isCheck;
        var content = that.component.selectedContent;
        if ($(this).is(":checked")) {
            if (!isCheck) {
                that.removeItem(content);
                $(this).prop("checked", true);
            }
            that.onSelected(target.data(that.options.dataKey), content);
        } else {
            that.onUnSelected(target.attr("data-id"), content);
        }
    }
    popupList.prototype.onClose = function (e) {
        var that = e.data.that;
        var id = $(this).closest("[data-id]").attr("data-id");
        var content = that.component.selectedContent;
        that.removeItem(id, content);
    }
    popupList.prototype.onConfirm = function (onConfirm) {
        if (typeof onConfirm == 'function') {
            this.store["onConfirm"] = onConfirm;
        }
    }
    popupList.prototype.popup = function (options) {
        var that = this, pop;
        if (!options) {
            options = {};
        }
        if (that.store.onConfirm) {
            options["onConfirm"] = function () {
                var selected = that.getSelected();
                if (selected.length == 0) {
                    that.tip("请选择一条数据");
                    return;
                }
                that.store.onConfirm(that.options.type == "radio" ? selected[0] : selected);
            }
        }
        options["onShown"] = function () {
            if (that.options.isInitQuery) {
                that.post();
            }
        }
        pop = new popup(options);
        this.component["popup"] = pop;
        that.component.template.appendTo(pop.body);
        that.initView();
        pop.showModal();
    }
    popupList.prototype.tip = function (info) {
        var pop = this.component.popup;
        if (pop) {
            pop.warning(info);
        }
    }
    popupList.prototype.addList = function (list) {
        var that = this;
        var format = that.options.format;
        var loadContent = that.component.loadContent;
        loadContent.children().remove();
        var selectedContent = that.component.selectedContent;
        $.each(list, function (i, data) {
            var isCheck = that.addLoadItem(data, format, loadContent);
            if (isCheck != null && isCheck === true) {
                that.addSelectedItem(data, format, selectedContent);
            }
        });
        that.component.lcount.text(list.length + "条数据");
    }
    popupList.prototype.addLoadItem = function (data, format, content) {
        var that = this;
        var id = data[that.options.idKey];
        if (that.hasItem(id, content)) {
            return null;
        }
        var type = that.options.type;
        var text = format == null ? null : format(data);
        if (text == null) {
            text = data[that.options.nameKey];
        }
        var name = that.options.name;
        var item = $('<label data-id="' + id + '"><input name="' + name + '" type="' + type + '"><span title="' + text + '" class="list-group-item">' + text + '</span></label>').appendTo(content);
        item.data(that.options.dataKey, data);
        var isCheck = this.store.selected[id];
        if (isCheck === true) {
            item.find("input").prop("checked", true);
        }
        return isCheck;
    }
    popupList.prototype.addSelectedItem = function (data, format, content) {
        var that = this;
        var limit = that.options.limit;
        if (typeof limit == 'number') {
            var count = that.getSelectedCount(content);
            if (count >= limit) {
                return false;
            }
        }
        var id = data[that.options.idKey];
        if (that.hasItem(id, content)) {
            return false;
        }
        var text = format == null ? null : format(data);
        if (text == null) {
            text = data[that.options.nameKey];
        }
        var item = $('<div data-id="' + id + '" title="' + text + '" class="list-group-item">' + text + '<span class="badge"><i class="glyphicon glyphicon-remove"></i></span></div>').appendTo(content);
        item.data(that.options.dataKey, data);
        this.store.selected[id] = true;
        this.component.scount.text(content.children().length + "条数据");
        return true;
    }
    popupList.prototype.hasItem = function (id, content) {
        var item = content.find('[data-id="' + id + '"]');
        return item.length == 1;
    }
    popupList.prototype.getSelectedItem = function (content) {
        if (content == null) {
            content = this.component.selectedContent;
        }
        return content.find("[data-id]");
    }
    popupList.prototype.getSelectedCount = function (content) {
        var items = this.getSelectedItem(content);
        return items.length;
    }
    popupList.prototype.getSelected = function (content) {
        var selected = [];
        var items = this.getSelectedItem(content);
        var dataKey = this.options.dataKey;
        $(items).each(function () {
            var data = $(this).data(dataKey);
            selected.push(data);
        });
        return selected;
    }
    popupList.prototype.removeItem = function (id, content) {
        var loadContent = this.component.loadContent;
        if (content == null) {
            content = id;
            id = null;
            content.children().remove();
            this.store.selected = {};
            this.cancelItem(loadContent);
        } else {
            content.find('[data-id="' + id + '"]').remove();
            this.store.selected[id] = false;
            this.cancelItem(loadContent, id);
        }
        this.component.scount.text(content.children().length + "条数据");
    }
    popupList.prototype.cancelItem = function (content, id) {
        if (content == null) {
            content = this.component.loadContent;
        }
        if (id) {
            content.find('[data-id="' + id + '"] input:checked').prop("checked", false);
        } else {
            content.find("input:checked").prop("checked", false);
        }
    }
    return popupList;
}));