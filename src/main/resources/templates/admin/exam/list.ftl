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
                                            <span class="input-group-text">考试名称</span>
                                        </div>
                                        <input type="text" class="form-control" name="title"
                                               placeholder="考试名称">
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
                                        <button type="button" id="add" class="btn btn-primary">
                                            新增
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
                                           data-url="${ctx.contextPath}/admin/exam/data"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="title">考试名称</th>
                                            <th data-field="stime">开始时间</th>
                                            <th data-field="etime">结束时间</th>
                                            <th data-field="duration">考试时长（分钟）</th>
                                            <th data-field="exstatus" data-formatter="exstatus">状态</th>
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
    $('#add').click(function () {
        window.location.href = '${ctx.contextPath}/admin/exam/edit';
    });

    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<a class="btn btn-sm btn-default m-r-5" href="${ctx.contextPath}/admin/exam/edit?id=' + value + '" title="编辑">编辑</a>';
        htm += '<a class="btn btn-sm btn-default" href="${ctx.contextPath}/admin/exam/examUserScore?eid=' + value + '" title="考试成绩">考试成绩</a>';
        htm += '</div>';
        return htm;
    }

    function del(id) {
        $.confirm({
            title: '提示',
            content: '是否删除？',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: "${ctx.contextPath}/admin/exam/del?id=" + id,
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

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/exam/data?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>