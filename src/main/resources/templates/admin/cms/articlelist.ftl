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
                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">发布状态</span>
                                        </div>
                                        <select type="text" class="form-control" name="pubFlag">
                                            <option value="">--状态--</option>
                                            <option value="1">发布</option>
                                            <option value="0">未发布</option>
                                        </select>
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
                                           data-url="${ctx.contextPath}/admin/cms/articleListData"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="categoryName">任务状态</th>
                                            <th data-field="title" data-formatter="title">标题</th>
                                            <th data-field="pubFlag" data-formatter="pubFlag">发布状态</th>
                                            <th data-field="hits">点击数</th>
                                            <th data-field="createDate" data-formatter="createDate">创建时间</th>
                                            <th data-field="lastUpdate" data-formatter="lastUpdate">更新时间</th>
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

    function title(value, row) {
        return '<a href="javascript:;" title="' + value + '" class="ellipsis" onclick="edit(\'' + row.id + '\')">' + value + '</a>';
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
        // htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="edit(\'' + value + '\')" title="编辑">编辑</button>';
        htm += '<a target="_blank" class="btn btn-sm btn-default" href="${ctx.contextPath}/article/detail?id=' + value + '" title="预览">预览</a>';
        htm += '</div>';
        return htm;
    }

    $("#add").click(function () {
        window.location.href = '${ctx.contextPath}/admin/cms/articleEdit';
    });

    function edit(id) {
        layer_show('编辑', "${ctx.contextPath}/admin/cms/articleEdit2?id=" + id,"90%");
    }

    $('#searchBtn').click(function () {
        reload();
    });

    function reload(){
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/cms/articleListData?" + $("#searchform").serialize()
        });
    }
</script>
</body>
</html>
