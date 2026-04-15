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
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                我的待办
                            </div>
                            <div class="card-body">
                                <div id="custom-toolbar">
                                    <form class="form-inline" method="post" id="searchform" action="#!" role="form">
                                        <div class="input-group m-r-5">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">项目标题</span>
                                            </div>
                                            <input type="text" class="form-control" name="projectTitle" placeholder="项目标题">
                                        </div>
                                        <div class="input-group m-r-5">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">任务标题</span>
                                            </div>
                                            <input type="text" class="form-control" name="title" placeholder="任务标题">
                                        </div>
                                        <div class="input-group m-r-5">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">任务状态</span>
                                            </div>
                                            <select class="form-control selectpicker" name="categoryIds" data-title="任务状态" data-width="220px" multiple>
                                                <#list categoryList as item>
                                                    <option value="${item.id!}">${item.title!}</option>
                                                </#list>
                                            </select>
                                        </div>

                                        <div class="input-group m-r-5">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">优先级</span>
                                            </div>
                                            <select class="form-control selectpicker" name="levelIds" data-title="优先级" data-width="130px" multiple>
                                                <#list categoryListLevel as item>
                                                    <option value="${item.id!}">${item.title!}</option>
                                                </#list>
                                            </select>
                                        </div>
                                        <button type="button" id="searchBtn" class="btn btn-primary m-r-5"><i class="mdi mdi-filter-outline"></i>搜索</button>
                                        <button type="reset" class="btn btn-default"><i class="mdi mdi-reload"></i>重置</button>
                                    </form>

                                </div>
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
                                            <th data-field="users" data-formatter="users">任务成员</th>
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

    function users(value, row) {
        if (value == '' || value == null) {
            return '';
        }
        let names = [];
        for (let i = 0; i < value.length; i++) {
            names.push(value[i].userName);
        }
        let nameStr = names.join("，");
        return '<div title="' + nameStr + '" class="ellipsis-300">' + nameStr + '</div>';
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
        return '<a href="${ctx.contextPath}/admin/system/index?mclick=7&funId=5&projectId='+row.pid+'" title="' + value + '" class="ellipsis-390">' + value + '</a>';
    }

    function title(value, row) {
        return '<a href="javascript:;" title="' + value + '" class="ellipsis-390" onclick="show(\'' + row.id + '\')">' + value + '</a>';
    }
    function show(id) {
        layer_show('查看', "${ctx.contextPath}/admin/cms/articleEdit2?readFlag=0&id=" + id, "90%");
    }

    $('#searchBtn').click(function () {
        reload();
    });

    function reload(){
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/cms/userListData2?" + $("#searchform").serialize()
        });
    }

    $('.selectpicker').selectpicker();
    $('#searchform').on('reset', function() {
        // 使用 setTimeout 确保在原生重置后执行
        setTimeout(function() {
            $('.selectpicker').trigger('change');
        }, 5);
    });
</script>
</body>
</html>