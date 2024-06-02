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
                                        <a id="add" class="btn btn-primary">
                                            新增
                                        </a>
                                        <a id="del" class="btn btn-primary">
                                            删除
                                        </a>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toolbar="#custom-toolbar"
                                           data-toggle="table"
                                           data-pagination="true"
                                           data-page-list="[10, 20, 50, 100, 200]"
                                           data-show-refresh="true"
                                           data-url="${ctx.contextPath}/admin/banner/listData"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-checkbox="true"></th>
                                            <th data-field="url" data-formatter="previewImg">图片</th>
                                            <th data-field="createDate">创建时间</th>
                                            <th data-field="releaseStatus" data-formatter="releaseStatus">
                                                发布状态
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
    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';

        if (row.releaseStatus == 0 || row.releaseStatus == 2) {
            htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="release(\'' + value + '\')">发布</button>';
        }
        if (row.releaseStatus == 1) {
            htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="release(\'' + value + '\')">下架</button>';
        }

        htm += '<a class="btn btn-sm btn-default m-r-5" href="${ctx.contextPath}/admin/banner/edit?id=' + value + '" title="编辑">编辑</a>';
        htm += '<button type="button" class="btn btn-sm btn-default" title="拖动排序" draggable="true" ondragstart="dragStart(event,\'' + value + '\')" ondrop="drop(event,\'' + value + '\')" ondragover="allowDrop(event)">';
        htm += '<span class="mdi mdi-cursor-move"></span>';
        htm += '</button>';
        htm += '</div>';
        return htm;
    }

    function allowDrop(event) {
        event.preventDefault();
    }

    function dragStart(event, id) {
        event.dataTransfer.setData("objId", id);
    }

    function drop(event, id) {
        event.preventDefault();
        let objId = event.dataTransfer.getData("objId");
        // console.log(objId + "放到了" + id)
        $.ajax({
            url: "${ctx.contextPath}/admin/banner/bannerSeq",
            data: {
                id1: objId,
                id2: id
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

    $('#add').click(function () {
        window.location.href = '${ctx.contextPath}/admin/banner/edit';
    });

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
                                url: "${ctx.contextPath}/admin/banner/del",
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

    function release(id) {
        $.ajax({
            url: "${ctx.contextPath}/admin/banner/release?id=" + id,
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

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/banner/listData?" + $("#searchform").serialize()
        });
    });
</script>
</body>
</html>