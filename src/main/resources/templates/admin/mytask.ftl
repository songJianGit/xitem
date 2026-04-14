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
                                           data-url="${ctx.contextPath}/admin/cms/userListData2"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="projectName" data-formatter="projectName">项目标题</th>
                                            <th data-field="title" data-formatter="title">任务标题</th>
                                            <th data-field="categoryName">任务状态</th>
                                            <th data-field="levelName">优先级</th>
                                            <th data-field="stime" data-formatter="plantime">计划时间</th>
                                            <th data-field="createDate" data-formatter="createDate">创建时间</th>
<#--                                            <th data-field="id" data-formatter="caozuo">操作</th>-->
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

    function plantime(value, row) {
        let htm='';
        if (value != '' && value != null) {
            htm+=value;
        }
        if (row.etime != '' && row.etime != null) {
            htm+='~'+row.etime;
        }
        return htm;
    }

    function projectName(value, row) {
        return '<a href="${ctx.contextPath}/admin/project/projectView?projectId='+row.pid+'" title="' + value + '" class="ellipsis-390" onclick="showProjectName(\'' + row.id + '\')">' + value + '</a>';
    }

    <#--function showProjectName(id) {-->
    <#--    layer_show('查看', "${ctx.contextPath}/admin/project/edit2?readFlag=0&id=" + id, "90%");-->
    <#--}-->

    function title(value, row) {
        return '<a href="javascript:;" title="' + value + '" class="ellipsis-390" onclick="show(\'' + row.id + '\')">' + value + '</a>';
    }
    function show(id) {
        layer_show('查看', "${ctx.contextPath}/admin/cms/articleEdit2?readFlag=0&id=" + id, "90%");
    }
    /*function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="show(\'' + value + '\')" title="查看">查看</button>';
        // htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="edit(\'' + value + '\')" title="编辑">编辑</button>';
        <#--htm += '<a target="_blank" class="btn btn-sm btn-default" href="${ctx.contextPath}/article/detail?id=' + value + '" title="预览">预览</a>';-->
        htm += '</div>';
        return htm;
    }*/
</script>
</body>
</html>