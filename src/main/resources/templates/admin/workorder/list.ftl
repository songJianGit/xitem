<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <#include "../commons/aside.ftl"/>
        <!--End 左侧导航-->

        <!--头部信息-->
        <#include "../commons/header.ftl"/>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid p-t-15">

                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <form class="form-inline" method="post" action="#!" role="form" id="searchform">

                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">标题</span>
                                        </div>
                                        <input type="text" class="form-control" name="title"
                                               placeholder="标题">
                                    </div>

                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">状态</span>
                                        </div>
                                        <select class="form-control" name="workStatus">
                                            <option value="">全部</option>
                                            <option value="0">未关闭</option>
                                            <option value="1">已关闭</option>
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
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toggle="table"
                                           data-pagination="true"
                                           data-page-list="[10, 20, 50, 100, 200]"
                                           data-show-refresh="true"
                                           data-url="${ctx.contextPath}/admin/workorder/listData"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="title">标题</th>
                                            <th data-field="createDate">创建时间</th>
                                            <th data-field="workStatus" data-formatter="workStatus">
                                                状态
                                            </th>
                                            <th data-field="id" data-formatter="caozuo">操作</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<a class="btn btn-sm btn-primary m-r-5" href="${ctx.contextPath}/admin/workorder/show?id=' + value + '" title="查看">查看</a>';
        if (row.workStatus == 0) {
            htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="closeWorkOrder(\'' + value + '\')">关闭</button>';
        }
        htm += '</div>';
        return htm;
    }

    function workStatus(value, row) {
        if (value == 0) {
            return '未关闭';
        }
        if (value == 1) {
            return '已关闭';
        }
        return value;
    }

    function closeWorkOrder(id) {
        $.confirm({
            title: '提示',
            content: '是否关闭？',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: "${ctx.contextPath}/admin/workorder/closeWorkOrder",
                            data: {
                                id: id
                            },
                            success: function (data) {
                                if (data.result) {
                                    layer.msg(data.msg);
                                    $("#table-pagination").bootstrapTable('refresh');
                                } else {
                                    alert(data.msg);
                                }
                            }
                        });
                    }
                },
                cancel: {
                    text: '取消',
                    action: function () {
                    }
                }
            }
        });
    }

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/workorder/listData?" + $("#searchform").serialize()
        });
    });
</script>
</body>
</html>