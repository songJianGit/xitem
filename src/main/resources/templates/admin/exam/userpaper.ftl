<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body style="background-color: white">
<div class="card" style="-webkit-box-shadow:none;box-shadow:none;">
    <div class="card-body">
        <div class="table-responsive">
            <table id="table-pagination"
                   data-toggle="table"
                   data-show-refresh="true"
                   data-url="${ctx.contextPath}/admin/exam/userPaperData?examId=${examId!}&userId=${userId!}&subStatus=1"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th data-field="createDate">开始时间</th>
                    <th data-field="subDate">结束时间</th>
                    <th data-field="duration">用时</th>
                    <th data-field="score">分数</th>
                    <th data-field="id" data-formatter="caozuo">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<!--End 页面主要内容-->
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<button type="button" class="btn btn-sm btn-default" title="查看" onclick="showPaper(\'' + value + '\')">查看</button>';
        htm += '</div>';
        return htm;
    }

    function showPaper(userPaperId) {
        layer_show("查看", "${ctx.contextPath}/admin/paper/userPaperPreview?userPaperId=" + userPaperId);
    }

</script>
</body>
</html>
