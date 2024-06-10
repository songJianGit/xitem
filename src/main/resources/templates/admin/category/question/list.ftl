<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../../commons/head.ftl"/>
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <#include "../../commons/aside.ftl"/>
        <!--End 左侧导航-->

        <!--头部信息-->
        <#include "../../commons/header.ftl"/>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="lyear-layout-content">
            <div class="container-fluid p-t-15">
                <div class="row">
                    <div class="col-12">
                        <div class="row">
                            <div class="col-4">
                                <div class="card">
                                    <div class="card-body">
                                        <ul id="ztree" class="ztree"></ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-8">
                                <div class="card">
                                    <div class="card-header">
                                        <form class="form-inline" method="post" id="searchform" action="#!" role="form">
                                            <div class="input-group m-r-5">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">分类名称</span>
                                                </div>
                                                <input type="text" class="form-control" name="title"
                                                       placeholder="分类名称">
                                            </div>

                                            <div class="input-group">
                                                <div class="btn-group">
                                                    <button type="button" id="searchBtn" class="btn btn-primary m-r-5">
                                                        搜索
                                                    </button>
                                                    <button type="reset" class="btn btn-default">重置</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="card-body">
                                        <h4 class="card-title">分类列表</h4>
                                        <div id="custom-toolbar">
                                            <div class="toolbar-btn-action">
                                                <button type="button" id="add" class="btn btn-primary">
                                                    分类新增
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
                                                   data-url="${ctx.contextPath}/admin/category/question/pageById"
                                                   data-query-params="pageQueryParams"
                                                   data-side-pagination="server">
                                                <thead>
                                                <tr>
                                                    <th data-field="title">标题</th>
                                                    <th data-field="createDate">创建时间</th>
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
                </div>
            </div>
        </main>
    </div>
</div>
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="edit(' + "\'" + row.id + "\'" + ')">编辑</button>';
        htm += '<button type="button" class="btn btn-sm btn-default" onclick="del(' + "\'" + row.id + "\'" + ')">删除</button>';
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
            url: "${ctx.contextPath}/admin/category/question/seq",
            data: {
                id1: objId,
                id2: id
            },
            success: function (data) {
                if (data.result) {
                    reload();
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    $('#add').click(function () {
        layer_show('新增', "${ctx.contextPath}/admin/category/question/categoryEdit");
    });

    $("#searchBtn").click(function () {
        searchData();
    });

    function edit(id) {
        layer_show('编辑', "${ctx.contextPath}/admin/category/question/categoryEdit?id=" + id);
    }

    function del(id) {
        $.alert({
            title: '请确认',
            content: '确定删除本分类和其下级所有分类?',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: "${ctx.contextPath}/admin/category/question/delCategory",
                            data: {
                                'categoryIds': id
                            },
                            cache: false,// 不缓存
                            success: function (data) {
                                if (data.result) {
                                    reload();
                                } else {
                                    layer.msg(data.msg)
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
</script>
<script type="text/javascript">
    function onClick(event, treeId, treeNode) {
        let clickTreeId = treeNode.id;
        searchData(clickTreeId);
    }

    let zTreeObj;
    $(function () {
        loadTree();
    });

    // 刷新页面上的树和表格
    function reload() {
        loadTree();
        $("#table-pagination").bootstrapTable('refresh');
    }

    // 加载树
    function loadTree() {
        $.ajax({
            url: "${ctx.contextPath}/admin/category/question/data",
            cache: false,// 不缓存
            success: function (d) {
                let setting = {
                    data: {
                        simpleData: {
                            enable: true// 简单数据
                        }
                    },
                    view: {
                        selectedMulti: false// 禁止多选
                    },
                    edit: {
                        drag: {
                            autoExpandTrigger: true,
                            isCopy: false,//所有操作都是move
                            isMove: false,
                            prev: false,
                            next: false,
                            inner: false
                        }
                    },
                    callback: {
                        onClick: onClick
                    }
                };
                zTreeObj = $.fn.zTree.init($("#ztree"), setting, d);
                showOne();
            }
        });
    }

    // 分类搜索
    function searchData(clickTreeId) {
        if (isBlank(clickTreeId)) {
            clickTreeId = '';
        }
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/category/question/pageById?pid=" + clickTreeId + "&" + $("#searchform").serialize()
        });
    }

    // 展开第一级
    function showOne() {
        let nodes = zTreeObj.getNodes();
        zTreeObj.expandNode(nodes[0], true, false, false);
    }
</script>
</body>
</html>