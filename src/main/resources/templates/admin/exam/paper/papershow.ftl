<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../../commons/head.ftl"/>
</head>
<body style="background-color: white">
<div class="card">

    <div class="card-header">
        <form class="form-inline" method="post" action="#!" role="form" id="searchform">

            <div class="input-group m-r-5">
                <div class="input-group-prepend">
                    <span class="input-group-text">试卷标题</span>
                </div>
                <input type="text" class="form-control" name="title"
                       placeholder="试卷标题">
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
        <div id="custom-toolbar">
            <div class="toolbar-btn-action">
                <button type="button" id="add" class="btn btn-primary">
                    确定
                </button>
            </div>
        </div>
        <div class="table-responsive">
            <table id="table-pagination"
                   data-toolbar="#custom-toolbar"
                   data-toggle="table"
                   data-pagination="true"
                   data-page-list="[10, 20, 50, 100, 200]"
                   data-click-to-select="true"
                   data-show-refresh="true"
                   data-url="${ctx.contextPath}/admin/paper/data"
                   data-query-params="pageQueryParams"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th data-radio="true"></th>
                    <th data-field="title">试卷标题</th>
                    <th data-field="snum">总题数</th>
                    <th data-field="score">总分</th>
                    <th data-field="createDate" data-width="160px">创建时间</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
    $('#add').click(function () {
        if (getSelectionId() != false) {
            let obj = $("#table-pagination").bootstrapTable('getSelections');
            parent.paperCallback(obj[0]);
            layer_close();
        }
    });

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/paper/data?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>
