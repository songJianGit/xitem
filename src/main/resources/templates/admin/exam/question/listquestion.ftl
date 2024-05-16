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
                    <span class="input-group-text">题目描述</span>
                </div>
                <input type="text" class="form-control" name="title"
                       placeholder="题目描述">
            </div>

            <div class="input-group m-r-5">
                <div class="input-group-prepend">
                    <span class="input-group-text">题目分类</span>
                </div>
                <select class="form-control" name="qclass">
                    <option value="">---请选择---</option>
                    <#list qclasslist as item>
                        <option value="${item.id!}">${item.name!}</option>
                    </#list>
                </select>
            </div>

            <div class="input-group m-r-5">
                <div class="input-group-prepend">
                    <span class="input-group-text">题型</span>
                </div>
                <select class="form-control" name="qtype">
                    <#-- 0-是非 1-单选 2-多选-->
                    <option value="">---请选择---</option>
                    <option value="0">是非</option>
                    <option value="1">单选</option>
                    <option value="2">多选</option>
                </select>

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
            <div class="form-inline" role="form">
                <button type="button" id="add" class="btn btn-primary m-r-5">
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
                   data-url="${ctx.contextPath}/admin/question/data"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th data-checkbox="true"></th>
                    <th data-field="title">题目标题</th>
                    <th data-field="qtype" data-formatter="qtype">题型</th>
                    <th data-field="qclassname">题目分类</th>
                    <th data-field="cdate">创建时间</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
    $('#add').click(function () {
        if (getSelectionIds() != false) {
            $.ajax({
                url: "${ctx.contextPath}/admin/paper/addQRS",
                data: {
                    qids: getSelectionIds().join(','),
                    qrid: '${qrid}',
                    score: 0
                },
                success: function (data) {
                    if (data.result) {
                        parent.reloadData();
                        layer_close();
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    });

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/question/data?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>