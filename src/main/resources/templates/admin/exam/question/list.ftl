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
                                        <form class="form-inline" method="post" action="#!" role="form" id="searchform">

                                            <div class="input-group m-r-5">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">题目描述</span>
                                                </div>
                                                <input type="text" class="form-control" name="title"
                                                       placeholder="题目描述">
                                            </div>

                                            <div class="input-group m-r-5">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">题型</span>
                                                </div>
                                                <select class="form-control" name="qtype">
                                                    <#-- 0-是非 1-单选 2-多选-->
                                                    <option value="">---请选择---</option>
                                                    <option value="0">是非</option>
                                                    <option value="1">单选</option>
                                                    <option value="2">多选</option>
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
                                                <button type="button" id="add" class="btn btn-primary">
                                                    新增
                                                </button>
                                                <button type="button" id="addQuestionExcel" class="btn btn-primary">
                                                    导入
                                                </button>
                                                <button type="button" id="del" class="btn btn-primary">
                                                    删除
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
                                                   data-url="${ctx.contextPath}/admin/question/data"
                                                   data-query-params="pageQueryParams"
                                                   data-side-pagination="server">
                                                <thead>
                                                <tr>
                                                    <th data-checkbox="true"></th>
                                                    <th data-field="title">题目标题</th>
                                                    <th data-field="qtype" data-formatter="qtype">题型</th>
                                                    <th data-field="qcategoryName">题目目录</th>
                                                    <th data-field="createDate" data-width="160px">创建时间</th>
                                                    <th data-field="id" data-formatter="caozuo" data-width="70px">操作</th>
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
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
    $('#add').click(function () {
        window.location.href = '${ctx.contextPath}/admin/question/edit';
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
                                url: "${ctx.contextPath}/admin/question/del",
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

    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<a class="btn btn-sm btn-default m-r-5" href="${ctx.contextPath}/admin/question/edit?id=' + value + '" title="编辑">编辑</a>';
        htm += '</div>';
        return htm;
    }

    $("#addQuestionExcel").click(function () {
        layer_show("导入", "${ctx.contextPath}/admin/question/excelquestion", '700px', '450px');
    });

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/question/data?" + $("#searchform").serialize()
        });
    });

    function reloadTable() {
        $("#table-pagination").bootstrapTable('refresh');
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

    // 搜索
    function searchData(clickTreeId) {
        if (isBlank(clickTreeId)) {
            clickTreeId = '';
        }
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/question/data?qcategory=" + clickTreeId + "&" + $("#searchform").serialize()
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
