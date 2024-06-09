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
                        <div class="card">
                            <div class="card-body">
                                <button type="button" class="btn btn-default"
                                        onclick="history.back(-1);return false;">返 回
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="card">
                            <div class="card-header">
                                题目规则
                            </div>
                            <div class="card-body">
                                <div id="custom-toolbar">
                                    <div class="toolbar-btn-action">
                                        <button type="button" id="add" class="btn btn-primary animated flipInX">
                                            新增
                                        </button>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toolbar="#custom-toolbar"
                                           data-toggle="table"
                                           data-show-refresh="true"
                                           data-url="${ctx.contextPath}/admin/paper/questionRuleData?paperId=${paper.id!}"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="title">规则名称</th>
                                            <th data-field="num" data-formatter="num">抽题数/总题数</th>
                                            <#--<th data-field="snum">总题数</th>-->
                                            <th data-field="id" data-formatter="caozuo" data-width="145px">操作</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-8">
                        <div class="card">
                            <div class="card-header">
                                试卷预览
                            </div>
                            <div class="card-body">
                                <iframe id="paperShwoIframe" style="border: 0;width: 100%;min-height: 623px"
                                        src="${ctx.contextPath}/admin/paper/paperPreview?paperId=${paper.id!}"></iframe>
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
    function num(value, row) {
        let htm = '';
        htm += row.num + "&nbsp/&nbsp" + row.snum;
        return htm;
    }

    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<button type="button" class="btn btn-sm btn-default" onclick="edit(\'' + value + '\')" title="编辑">编辑</button>';
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="del(\'' + value + '\')" title="删除">删除</button>';
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
            url: "${ctx.contextPath}/admin/paper/questionRuleSeq",
            data: {
                id1: objId,
                id2: id
            },
            success: function (data) {
                if (data.result) {
                    reloadData();
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    $("#addQuestion").click(function () {
        listQrs();
    });

    $('#add').click(function () {
        $.ajax({
            url: "${ctx.contextPath}/admin/paper/addQuestionRule",
            data: {
                paperId: '${paper.id}'
            },
            success: function (data) {
                if (data.result) {
                    reloadData();
                } else {
                    alert(data.msg);
                }
            }
        });
    });

    function edit(id) {
        layer_show('编辑', '${ctx.contextPath}/admin/paper/listQRS?qrId=' + id);
    }

    function del(id) {
        $.confirm({
            title: '提示',
            content: '是否删除？',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: "${ctx.contextPath}/admin/paper/delQuestionRule",
                            data: {
                                questionRuleIds: id
                            },
                            success: function (data) {
                                if (data.result) {
                                    reloadData();
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

    function reloadData() {
        $("#table-pagination").bootstrapTable('refresh');
        let paperShwoIframe = document.getElementById('paperShwoIframe');
        paperShwoIframe.src = paperShwoIframe.src; // 重新分配src属性来触发重新加载
    }

</script>
</body>
</html>
