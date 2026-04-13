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
                                            <span class="input-group-text">项目名称</span>
                                        </div>
                                        <input type="text" class="form-control" value="" name="title"
                                               placeholder="项目名称">
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
                                            开始新项目
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
                                           data-url="${ctx.contextPath}/admin/project/data"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="title" data-formatter="title">标题</th>
                                            <th data-field="users" data-formatter="users">项目成员</th>
                                            <th data-field="createDate" data-formatter="createDate">创建时间</th>
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
    function pstatus(value, row) {
        if (value == '') {
            return '';
        }
        if (value == 1) {
            return '正常';
        }
        if (value == 0) {
            return '关闭';
        }
        return value;
    }

    function users(value, row) {
        if (value == '' || value == null) {
            return '';
        }
        let names = [];
        for (let i = 0; i < value.length; i++) {
            names.push(value[i].userName);
        }
        let nameStr = names.join("，");
        return '<div title="' + nameStr + '" class="ellipsis-400">' + nameStr + '</div>';
    }

    function createDate(value, row) {
        if (value == '') {
            return '';
        }
        return value.substring(0, 10);
    }

    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="show(\'' + value + '\')" title="查看">查看</button>';
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="edit(\'' + value + '\')" title="编辑">编辑</button>';
        <#--htm += '<a target="_blank" class="btn btn-sm btn-default" href="${ctx.contextPath}/article/detail?id=' + value + '" title="预览">预览</a>';-->
        htm += '</div>';
        return htm;
    }

    function title(value, row) {
        return '<a href="javascript:;" title="' + value + '" class="ellipsis-390" onclick="show(\'' + row.id + '\')">' + value + '</a>';
    }

    $("#add").click(function () {
        layer_show('新增', "${ctx.contextPath}/admin/project/edit2", "90%");
    });

    function edit(id) {
        layer_show('编辑', "${ctx.contextPath}/admin/project/edit2?id=" + id, "90%");
    }

    function show(id) {
        layer_show('查看', "${ctx.contextPath}/admin/project/edit2?showFlag=1&id=" + id, "90%");
    }

    $('#searchBtn').click(function () {
        reload();
    });

    function reload() {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/project/data?" + $("#searchform").serialize()
        });
    }
</script>
</body>
</html>