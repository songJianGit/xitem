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
                    <span class="input-group-text">姓名</span>
                </div>
                <input type="text" class="form-control" value="" name="userName"
                       placeholder="姓名">
            </div>
            <div class="input-group m-r-5">
                <div class="input-group-prepend">
                    <span class="input-group-text">登录名</span>
                </div>
                <input type="text" class="form-control" value="" name="loginName"
                       placeholder="登录名">
            </div>

            <div class="input-group">
                <div class="btn-group">
                    <button type="button" id="searchBtn" class="btn btn-primary m-r-5">搜索</button>
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
                   data-show-refresh="true"
                   data-url="${ctx.contextPath}/admin/system/userListData"
                   data-query-params="pageQueryParams"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th data-field="state" data-checkbox="true"></th>
                    <th data-field="userName">姓名</th>
                    <th data-field="loginName">登录名</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    $("#add").click(function () {
        let ids = getSelectionIds();
        if(ids!=false){
            parent.userCallBack(ids);
            layer_close();
        }
    });

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/system/userListData?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>
