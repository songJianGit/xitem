<script type="text/javascript" src="${ctx.contextPath}/static/plugins/admin-template/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/admin-template/js/popper.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/admin-template/js/bootstrap.min.js"></script>
<script type="text/javascript"
        src="${ctx.contextPath}/static/plugins/admin-template/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/jquery-cookie/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/admin-template/js/main.min.js"></script>
<script type="text/javascript"
        src="${ctx.contextPath}/static/plugins/admin-template/js/jquery-confirm/jquery-confirm.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/layer3.5.1/layer.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript"
        src="${ctx.contextPath}/static/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript"> var contextPath = '${ctx.contextPath}';</script>
<#include "./dict.ftl"/>
<script type="text/javascript">
    // ---菜单回显---begin
    window.onload = function () {
        <#if Session.mclick??>
        let menuClickInfo = '${Session.mclick}';
        if (isNotBlank(menuClickInfo) && menuClickInfo != 'x') {
            $('#' + menuClickInfo).parents('li').addClass('active').addClass('open');
            $('#' + menuClickInfo).addClass('active');
        }
        </#if>
    };
    // ---菜单回显---end

    // 打开弹出层 参数解释： title 标题 url 请求的url w 弹出层宽度（缺省调默认值） h 弹出层高度（缺省调默认值） full 弹出是否立即全屏
    function layer_show(title, url, w, h, full, closeBtn) {
        if (title == null || title == '') {
            title = false;
        }
        if (w == null || w == '') {
            w = '1000px';
        }
        if (h == null || h == '') {
            h = ($(window).height() - 100) + 'px'
        }
        let index = layer.open({
            type: 2,
            area: [w, h],
            shadeClose: false,// 点击遮罩区域，关闭弹层
            title: title,
            content: url,
            closeBtn: closeBtn
        });
        if (full) {
            layer.full(index);
        }
        return index;
    }

    /* 关闭弹出层 */
    function layer_close() {
        let index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    function getSelectionId() {
        let obj = $("#table-pagination").bootstrapTable('getSelections');
        if (obj.length == 0) {
            alert("请选择要处理的记录.");
            return false;
        } else if (obj.length == 1) {
            return obj[0].id;
        } else {
            layer.msg("不能选择多条记录.");
            return false;
        }
    }

    function getSelectionIds() {
        let objs = $("#table-pagination").bootstrapTable('getSelections');
        let obids = [];
        if (objs.length == 0) {
            alert("请选择要处理的记录.");
            return false;
        } else {
            for (let i = 0; i < objs.length; i++) {
                obids.push(objs[i].id);
            }
            return obids;
        }
    }

    // js的空判断
    function isBlank(val) {
        if (val == undefined || val == '' || val.length == 0) {
            return true;
        }
        return false;
    }

    function isNotBlank(val) {
        return !isBlank(val);
    }

</script>
