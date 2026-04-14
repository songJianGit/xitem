<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body class="card">
<div class="card-header">
    <form class="form-inline" method="post" id="searchform" action="#!" role="form">
        <div class="input-group m-r-5">
            <div class="input-group-prepend">
                <span class="input-group-text">标题</span>
            </div>
            <input type="text" class="form-control" name="title" placeholder="标题">
        </div>
        <button type="button" id="searchBtn" class="btn btn-primary m-r-5">搜索</button>
        <button type="reset" class="btn btn-default">重置</button>
    </form>
</div>
<div class="card-body">
    <div class="table-responsive">
        <table id="table-pagination"
               data-toolbar="#custom-toolbar"
               data-toggle="table"
               data-pagination="true"
               data-page-list="[10, 20, 50, 100, 200]"
               data-show-refresh="true"
               data-url="${ctx.contextPath}/admin/cms/userListData3?roadMapId=${roadMapId!}"
               data-query-params="pageQueryParams"
               data-side-pagination="server">
            <thead>
            <tr>
                <th data-field="title" data-formatter="title">标题</th>
                <th data-field="users" data-formatter="users">任务成员</th>
                <th data-field="categoryName">任务状态</th>
                <th data-field="levelName">优先级</th>
                <th data-field="stime" data-formatter="plantime">计划时间</th>
                <th data-field="createDate" data-formatter="createDate">创建时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function users(value, row) {
        if (value == '' || value == null) {
            return '';
        }
        let names = [];
        for (let i = 0; i < value.length; i++) {
            names.push(value[i].userName);
        }
        return names.join("，");
    }

    function plantime(value, row) {
        let htm = '';
        if (value != '' && value != null) {
            htm += value;
        }
        if (row.etime != '' && row.etime != null) {
            htm += '~' + row.etime;
        }
        return htm;
    }

    function title(value, row) {
        return '<a href="javascript:;" title="' + value + '" class="ellipsis-390" onclick="show(\'' + row.id + '\')">' + value + '</a>';
    }

    function createDate(value, row) {
        if (value == '') {
            return '';
        }
        return value.substring(0, 16);
    }

    function lastUpdate(value, row) {
        if (value == '') {
            return '';
        }
        return value.substring(0, 16);
    }

    function show(id) {
        layer_show('查看', "${ctx.contextPath}/admin/cms/articleEdit2?readFlag=0&id=" + id, "90%");
    }

    $('#searchBtn').click(function () {
        reload();
    });

    function reload() {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/cms/userListData3?roadMapId=${roadMapId!}&" + $("#searchform").serialize()
        });
    }
</script>
</body>
</html>
