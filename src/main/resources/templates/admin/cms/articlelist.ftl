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
                                <form class="form-inline" method="post" id="searchform" action="#!" role="form">
                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">标题</span>
                                        </div>
                                        <input type="text" class="form-control" name="title" placeholder="标题">
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
                                    <button type="reset" class="btn btn-default m-r-5"><i class="mdi mdi-reload"></i>重置</button>
                                </form>
                            </div>

                            <div class="card-body">
                                <div id="custom-toolbar">
                                    <div class="toolbar-btn-action" style="display: flex; align-items: center; gap: 10px; flex-wrap: nowrap;">
                                        <@projectReadFlagTag>
                                            <button type="button" id="add" class="btn btn-primary">
                                                <i class="mdi mdi-plus"></i>新增任务
                                            </button>
                                            <button type="button" class="btn btn-default" onclick="exportData()"><i class="mdi mdi-download"></i>导出全部</button>
                                        </@projectReadFlagTag>
                                        <div class="form-inline view-btn-group" style="display: flex; align-items: center; margin-bottom: 0; margin-left: 8px; padding-left: 12px; border-left: 1px solid #e9ecef;">
                                            <div class="custom-control custom-checkbox mr-sm-2">
                                                <input value="1" type="radio" name="taskSearchFlag" class="custom-control-input" id="allTask" checked>
                                                <label class="custom-control-label" for="allTask">全部任务</label>
                                            </div>
                                            <div class="custom-control custom-checkbox mr-sm-2">
                                                <input value="2" type="radio" name="taskSearchFlag" class="custom-control-input" id="myTask">
                                                <label class="custom-control-label" for="myTask">我参与的</label>
                                            </div>
                                        </div>
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
                                            <th data-field="title" data-formatter="title">标题</th>
                                            <th data-field="users" data-formatter="users">任务成员</th>
                                            <th data-field="categoryName" data-formatter="categoryName">任务状态</th>
                                            <th data-field="levelName" data-formatter="levelName">优先级</th>
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
    function levelName(value, row) {
        <@projectReadFlagOutTag>
            <#if flag==1>
                let htm = '<select class="form-control" data-project="'+row.id+'" onchange="levelNameUP(this)">';
                if(isBlank(row.levelId)){
                    htm += '<option value="" disabled selected>请选择</option>';
                }
                <#list categoryListLevel as item>
                htm += '<option value="${item.id!}"  '+((`${item.id!}`==row.levelId)?"selected":"")+' >${item.title!}</option>';
                </#list>
                htm += '</select>';
                return htm;
            </#if>
        <#if flag==0>
            return value;
        </#if>
        </@projectReadFlagOutTag>
    }

    function levelNameUP(dom){
        $.ajax({
            url: "${ctx.contextPath}/admin/cms/upLevelId?projectId=" + $(dom).data("project") + "&levelId="+$(dom).val(),
            success: function (d) {
                layer.msg("成功更新优先级");
            }
        });
    }

    function categoryName(value, row) {
        <@projectReadFlagOutTag>
        <#if flag==1>
        let htm = '<select class="form-control" data-project="'+row.id+'" onchange="categoryNameUP(this)">';
        <#list categoryList as item>
        htm += '<option value="${item.id!}"  '+((`${item.id!}`==row.categoryId)?"selected":"")+' >${item.title!}</option>';
        </#list>
        htm += '</select>';
        return htm;
        </#if>
        <#if flag==0>
        return value;
        </#if>
        </@projectReadFlagOutTag>
    }

    function categoryNameUP(dom){
        $.ajax({
            url: "${ctx.contextPath}/admin/cms/upCategoryId?projectId=" + $(dom).data("project") + "&categoryId="+$(dom).val(),
            success: function (d) {
                layer.msg("成功更新任务状态");
            }
        });
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
        let htm = '';
        if (value != '' && value != null) {
            htm += value;
        }
        if (row.etime != '' && row.etime != null) {
            htm += '~' + row.etime;
        }
        return htm;
    }

    function title(value, row) {
        return '<a href="javascript:;" title="' + value + '" class="ellipsis-390" onclick="show(\'' + row.id + '\')">' + value + '</a>';
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
        <@projectReadFlagTag>
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="edit(\'' + value + '\')" title="编辑"><i class="mdi mdi-square-edit-outline"></i>编辑</button>';
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="delById(\'' + value + '\')" title="删除"><i class="mdi mdi-delete"></i>删除</button>';
        </@projectReadFlagTag>
        htm += '</div>';
        return htm;
    }

    function delById(id) {
        $.confirm({
            title: '提示',
            content: '是否删除？',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: "${ctx.contextPath}/admin/cms/delById?id=" + id,
                            success: function (d) {
                                layer.msg(d.msg);
                                reload();
                            }
                        });
                    }
                },
                cancel: {
                    text: '取消',
                    action: function () {
//                            $.alert('取消的!');
                    }
                }
            }
        });
    }

    function exportData() {
        window.open('${ctx.contextPath}/admin/cms/export', '_blank');
    }

    $("#add").click(function () {
        layer_show('新增', "${ctx.contextPath}/admin/cms/articleEdit2", "90%");
    });

    function show(id) {
        layer_show('查看', "${ctx.contextPath}/admin/cms/articleEdit2?readFlag=0&id=" + id, "90%");
    }

    function edit(id) {
        layer_show('编辑', "${ctx.contextPath}/admin/cms/articleEdit2?id=" + id, "90%");
    }

    $('#searchBtn').click(function () {
        reload();
    });

    function reload() {
        let taskSearchFlag = $("input[name='taskSearchFlag']:checked").val() || "1";
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/cms/userListData1?" + $("#searchform").serialize()+"&taskSearchFlag="+taskSearchFlag
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
