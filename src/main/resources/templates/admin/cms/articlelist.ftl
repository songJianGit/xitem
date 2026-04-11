<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <link rel="stylesheet" href="${ctx.contextPath}/static/plugins/ztree-v3/css/zTreeStyle/zTreeStyle.css"
          type="text/css">
    <style>
        .ellipsis {
            display: block;
            white-space: nowrap; /* 确保文本在一行内显示 */
            overflow: hidden; /* 超出容器部分的文本隐藏 */
            text-overflow: ellipsis; /* 使用打点表示被截断的文本 */
            width: 390px;
        }
    </style>
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
                                <form class="form-inline" method="post" id="searchform" action="#!" role="form">
                                    <input type="hidden" id="categoryIds" name="categoryIds">
                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">标题</span>
                                        </div>
                                        <input type="text" class="form-control" name="title" placeholder="标题">
                                    </div>
                                    <button type="button" id="searchBtn" class="btn btn-primary m-r-5">搜索</button>
                                    <button type="reset" class="btn btn-default">重置</button>
                                </form>
                            </div>


                            <div class="card-body">
<#--                                <h4 class="card-title">文章列表</h4>-->
                                <div id="custom-toolbar">
                                    <div class="toolbar-btn-action">
                                        <button type="button" id="add" class="btn btn-primary">
                                            新增任务
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
                                           data-url="${ctx.contextPath}/admin/cms/userListData1"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="title" data-formatter="title">任务标题</th>
                                            <th data-field="users" data-formatter="users">任务成员</th>
                                            <th data-field="categoryName">任务状态</th>
                                            <th data-field="levelName">优先级</th>
                                            <th data-field="stime" data-formatter="plantime">计划时间</th>
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
    function pubFlag(value, row) {
        if (value == '1') {
            return '发布';
        }
        if (value == '0') {
            return '未发布';
        }
        return '';
    }

    function users(value, row) {
        if (value == '' || value == null) {
            return '';
        }
        let names = [];
        for (let i = 0; i < value.length; i++) {
            names.push(value[i].userName);
        }
        return names.join("，");
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

    function title(value, row) {
        return '<a href="javascript:;" title="' + value + '" class="ellipsis" onclick="show(\'' + row.id + '\')">' + value + '</a>';
    }

    function createDate(value, row) {
        if (value == '') {
            return '';
        }
        return value.substring(0, 16);
    }

    function lastUpdate(value, row) {
        if (value == '') {
            return '';
        }
        return value.substring(0, 16);
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

    $("#add").click(function () {
        layer_show('新增', "${ctx.contextPath}/admin/cms/articleEdit2","90%");
    });

    function show(id) {
        layer_show('查看', "${ctx.contextPath}/admin/cms/articleEdit2?showFlag=1&id=" + id, "90%");
    }

    function edit(id) {
        layer_show('编辑', "${ctx.contextPath}/admin/cms/articleEdit2?id=" + id, "90%");
    }

    $('#searchBtn').click(function () {
        reload();
    });

    function reload(){
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/cms/userListData1?" + $("#searchform").serialize()
        });
    }
</script>
</body>
</html>
