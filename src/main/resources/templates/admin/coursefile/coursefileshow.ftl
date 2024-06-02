<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body style="background-color: white">
<div class="card">

    <div class="card-header">
        <form class="form-inline" method="post" action="#!" role="form" id="searchform">

            <div class="input-group m-r-5">
                <div class="input-group-prepend">
                    <span class="input-group-text">课件标题</span>
                </div>
                <input type="text" class="form-control" name="title"
                       placeholder="课件标题">
            </div>

            <div class="input-group">
                <div class="btn-group">
                    <button type="button" id="searchBtn" class="btn btn-primary m-r-5">搜索
                    </button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </div>
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
                   data-url="${ctx.contextPath}/admin/coursefile/listData"
                   data-query-params="pageQueryParams"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th data-field="title">课件标题</th>
                    <th data-field="remarks">课件备注</th>
                    <th data-field="createDate" data-width="160px">创建时间</th>
                    <th data-field="id" data-formatter="caozuo">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">

    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<button class="btn btn-sm btn-primary" onclick="add(' + JSON.stringify(row).replace(/"/g, '&quot;') + ')" title="选择">选择</button>';
        htm += '</div>';
        return htm;
    }

    function add(row) {
        parent.courseFileCallback(row);
        layer_close();
    }

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/coursefile/listData?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>
