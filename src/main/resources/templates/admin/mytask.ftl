<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "commons/head.ftl"/>
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <#include "commons/aside.ftl"/>
        <!--End 左侧导航-->

        <!--头部信息-->
        <#include "commons/header.ftl"/>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="lyear-layout-content">
            <div class="container-fluid p-t-15">
                <div class="row">
                    <div class="col-md-6 col-xl-3">
                        <div class="card bg-primary text-white">
                            <div class="card-body clearfix">
                                <div class="flex-box">
                                    <span class="img-avatar img-avatar-48 bg-translucent"><i class="mdi mdi-currency-cny fs-22"></i></span>
                                    <span class="fs-22 lh-22">102,125.00</span>
                                </div>
                                <div class="text-right">今日收入</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-xl-3">
                        <div class="card bg-danger text-white">
                            <div class="card-body clearfix">
                                <div class="flex-box">
                                    <span class="img-avatar img-avatar-48 bg-translucent"><i class="mdi mdi-account fs-22"></i></span>
                                    <span class="fs-22 lh-22">920,000</span>
                                </div>
                                <div class="text-right">用户总数</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-xl-3">
                        <div class="card bg-success text-white">
                            <div class="card-body clearfix">
                                <div class="flex-box">
                                    <span class="img-avatar img-avatar-48 bg-translucent"><i class="mdi mdi-arrow-down-bold fs-22"></i></span>
                                    <span class="fs-22 lh-22">34,005,000</span>
                                </div>
                                <div class="text-right">下载总量</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-xl-3">
                        <div class="card bg-purple text-white">
                            <div class="card-body clearfix">
                                <div class="flex-box">
                                    <span class="img-avatar img-avatar-48 bg-translucent"><i class="mdi mdi-comment-outline fs-22"></i></span>
                                    <span class="fs-22 lh-22">153 条</span>
                                </div>
                                <div class="text-right">新增留言</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-title">待办任务</div>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toolbar="#custom-toolbar"
                                           data-toggle="table"
                                           data-pagination="true"
                                           data-page-list="[10, 20, 50, 100, 200]"
<#--                                           data-show-refresh="true"-->
                                           data-url="${ctx.contextPath}/admin/project/data"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="title">项目名称</th>
                                            <th data-field="pstatus">任务标题</th>
                                            <th data-field="createDate" data-formatter="createDate">创建时间</th>
                                            <th data-field="createDate">计划时间</th>
                                            <th data-field="pstatus">任务状态</th>
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
<#include "commons/js.ftl"/>
<script type="text/javascript">
    function createDate(value, row) {
        if (value == '') {
            return '';
        }
        return value.substring(0, 10);
    }
    function title(value, row) {
        return '<a href="javascript:;" title="' + value + '" class="ellipsis" onclick="show(\'' + row.id + '\')">' + value + '</a>';
    }
    function show(id) {
        window.location.href = '${ctx.contextPath}/admin/project/show?id=' + id;
    }
</script>
</body>
</html>