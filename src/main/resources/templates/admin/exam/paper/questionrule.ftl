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
                                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
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
                                    <div class="form-inline" role="form">
                                        <button type="button" id="add" class="btn btn-primary m-r-5 animated flipInX">
                                            新增
                                        </button>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toolbar="#custom-toolbar"
                                           data-toggle="table"
                                           data-click-to-select="true"
                                           data-url="${ctx.contextPath}/admin/paper/questionRuleData?paperId=${paper.id!}"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="title">规则名称</th>
                                            <th data-field="num">抽题数</th>
                                            <th data-field="snum">总题数</th>
                                            <th data-field="id" data-formatter="caozuo" data-width="115px">操作</th>
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
                                        src="${ctx.contextPath}/admin/paper/paperShow?paperId=${paper.id!}"></iframe>
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

    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<button class="btn btn-sm btn-default m-r-5" onclick="edit(\'' + value + '\')" type="button" title="编辑">编辑</button>';
        htm += '<button class="btn btn-sm btn-default" onclick="del(\'' + value + '\')" type="button" title="删除">删除</button>';
        htm += '</div>';
        return htm;
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
        layer_show('编辑', '${ctx.contextPath}/admin/paper/listQRS?qrid=' + id);
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
