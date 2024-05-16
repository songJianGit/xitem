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
                                            <span class="input-group-text">题目分类</span>
                                        </div>
                                        <select class="form-control" name="qclass">
                                            <option value="">---请选择---</option>
                                            <#list qclasslist as item>
                                                <option value="${item.id!}">${item.name!}</option>
                                            </#list>
                                        </select>
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
                                    <div class="form-inline" role="form">
                                        <button type="button" id="add" class="btn btn-primary m-r-5">
                                            新增
                                        </button>
                                        <button type="button" id="del" class="btn btn-primary m-r-5">
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
                                           data-click-to-select="true"
                                           data-url="${ctx.contextPath}/admin/question/data"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-checkbox="true"></th>
                                            <th data-field="title">题目标题</th>
                                            <th data-field="qtype" data-formatter="qtype">题型</th>
                                            <th data-field="qclassname">题目分类</th>
                                            <th data-field="cdate">创建时间</th>
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

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/question/data?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>
