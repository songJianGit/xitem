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
                                    <button type="button" id="searchBtn" class="btn btn-primary m-r-5">搜索</button>
                                    <button type="reset" class="btn btn-default">重置</button>
                                </form>
                            </div>

                            <div class="card-body">
                                <#--                                <h4 class="card-title">文章列表</h4>-->
                                <div id="custom-toolbar">
                                    <div class="toolbar-btn-action">
                                        <@projectReadFlagTag>
                                            <button type="button" id="add" class="btn btn-primary">
                                                新增里程碑
                                            </button>
                                        </@projectReadFlagTag>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toolbar="#custom-toolbar"
                                           data-toggle="table"
                                           data-pagination="true"
                                           data-page-list="[10, 20, 50, 100, 200]"
                                           data-show-refresh="true"
                                           data-url="${ctx.contextPath}/admin/roadmap/data"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="title">标题</th>
                                            <th data-field="percentage" data-formatter="roadMapPercentage">进度</th>
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

    // function title(value, row) {
    //     return '<a href="javascript:;" title="' + value + '" class="ellipsis-390" onclick="show(\'' + row.id + '\')">' + value + '</a>';
    // }

    function roadMapPercentage(value, row) {
        return '<a href="javascript:;" title="' + value + '" onclick="showRoadMap(\'' + row.id + '\')">' + value + '</a>';
    }

    function createDate(value, row) {
        if (value == '') {
            return '';
        }
        return value.substring(0, 16);
    }

    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        <@projectReadFlagTag>
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="edit(\'' + value + '\')" title="编辑">编辑</button>';
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="delById(\'' + value + '\')" title="删除">删除</button>';
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
                            url: "${ctx.contextPath}/admin/roadmap/delById?id=" + id,
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

    $("#add").click(function () {
        layer_show('新增', "${ctx.contextPath}/admin/roadmap/edit", "40%", "260px");
    });

    function show(id) {
        layer_show('查看', "${ctx.contextPath}/admin/roadmap/edit?readFlag=0&id=" + id, "40%", "260px");
    }

    function edit(id) {
        layer_show('编辑', "${ctx.contextPath}/admin/roadmap/edit?id=" + id, "40%", "260px");
    }

    function showRoadMap(id) {
        layer_show('查看', "${ctx.contextPath}/admin/cms/articleListRoadMap?roadMapId=" + id, "90%");
    }

    $('#searchBtn').click(function () {
        reload();
    });

    function reload() {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/roadmap/data?" + $("#searchform").serialize()
        });
    }
</script>
</body>
</html>
