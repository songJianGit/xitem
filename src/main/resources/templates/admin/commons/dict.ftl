<script type="text/javascript">
    function qtype(value, row) {
        if (value == 0) {
            return '是非';
        }
        if (value == 1) {
            return '单选';
        }
        if (value == 2) {
            return '多选';
        }
        return value;
    }

    function extype(value, row) {
        if (value == 0) {
            return '授权考试';
        }
        if (value == 1) {
            return '公开考试';
        }
        return value;
    }

    function releaseStatus(value, row) {
        if (value == 0) {
            return '未发布';
        }
        if (value == 1) {
            return '已发布';
        }
        if (value == 2) {
            return '已下架';
        }
        return value;
    }

    function previewImg(value, row) {
        if (isBlank(value)) {
            value = "/static/admin/commons/img/defaultimg.webp";
        }
        return '<img style="width:70px;" src="${ctx.contextPath}' + value + '"/>';
    }

    function exstatus(value, row) {
        if (value == 0) {
            return '未开始';
        }
        if (value == 1) {
            return '进行中';
        }
        if (value == 2) {
            return '已结束';
        }
        return value;
    }

    function status(value, row) {
        if (value == 0) {
            return '删除';
        }
        if (value == 1) {
            return '启用';
        }
        if (value == 2) {
            return '停用';
        }
        return value;
    }
</script>
