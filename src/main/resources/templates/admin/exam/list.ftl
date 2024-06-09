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
                                            <span class="input-group-text">考试标题</span>
                                        </div>
                                        <input type="text" class="form-control" name="title"
                                               placeholder="考试标题">
                                    </div>

                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">考试类型</span>
                                        </div>
                                        <select class="form-control" name="exType">
                                            <option value="">---考试类型---</option>
                                            <option value="1">公开考试</option>
                                            <option value="0">授权考试</option>
                                        </select>
                                    </div>

                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">发布状态</span>
                                        </div>
                                        <select class="form-control" name="releaseStatus">
                                            <option value="">---发布状态---</option>
                                            <option value="0">未发布</option>
                                            <option value="1">已发布</option>
                                            <option value="2">已下架</option>
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
                                    <div class="toolbar-btn-action">
                                        <button type="button" id="add" class="btn btn-primary">
                                            新增
                                        </button>
                                        <button type="button" id="del" class="btn btn-primary">
                                            删除
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
                                           data-url="${ctx.contextPath}/admin/exam/data"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-checkbox="true"></th>
                                            <th data-field="title">考试标题</th>
                                            <th data-field="exType" data-formatter="extype">考试类型</th>
                                            <th data-field="stime" data-width="160px">开始时间</th>
                                            <th data-field="etime" data-width="160px">结束时间</th>
                                            <th data-field="duration" data-formatter="duration">考试时长</th>
                                            <th data-field="releaseStatus" data-formatter="releaseStatus">发布状态
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
    function duration(value, row) {
        return value + '&nbsp;分钟';
    }

    $('#add').click(function () {
        window.location.href = '${ctx.contextPath}/admin/exam/edit';
    });

    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<a class="btn btn-sm btn-default m-r-5" href="${ctx.contextPath}/admin/exam/edit?id=' + value + '" title="编辑">编辑</a>';

        if (row.releaseStatus == 0 || row.releaseStatus == 2) {
            htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="release(\'' + value + '\')">发布</button>';
        }
        if (row.releaseStatus == 1) {
            htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="release(\'' + value + '\')">下架</button>';
        }

        if (row.exType == 0) {
            htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="examAuth(\'' + value + '\')">授权</button>';
        }

        htm += '<a class="btn btn-sm btn-default" href="${ctx.contextPath}/admin/exam/examScore?examId=' + value + '" title="考试成绩">考试成绩</a>';
        htm += '</div>';
        return htm;
    }

    function examAuth(id) {
        layer_show("考试授权", "${ctx.contextPath}/admin/exam/userExamAuth?examId=" + id);
    }

    function release(id) {
        $.ajax({
            url: "${ctx.contextPath}/admin/exam/release?id=" + id,
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

    $('#del').click(function () {
        if (getSelectionIds() != false) {
            $.confirm({
                title: '提示',
                content: '是否删除？',
                buttons: {
                    confirm: {
                        text: '确认',
                        action: function () {
                            $.ajax({
                                url: "${ctx.contextPath}/admin/exam/del",
                                data: {
                                    ids: getSelectionIds().join(',')
                                },
                                success: function (data) {
                                    if (data.result) {
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
    });

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/exam/data?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>
