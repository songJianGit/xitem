<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../../commons/head.ftl"/>
</head>
<body style="background-color: white">
<div class="card" style="-webkit-box-shadow:none;box-shadow:none">
    <div class="card-body">
        <div id="custom-toolbar">
            <div class="form-inline" role="form">
                <button type="button" id="closeBtn" class="btn btn-primary m-r-5">
                    保存
                </button>
                <button type="button" id="addQuestion" class="btn btn-primary m-r-5">
                    选择考题
                </button>
                <button type="button" class="btn btn-primary m-r-5">
                    保存分值
                </button>
                <button type="button" id="upQuestionSeq" class="btn btn-primary m-r-5">
                    保存排序
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
                   data-url="${ctx.contextPath}/admin/paper/pageQRS?qrid=${questionRule.id!}"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th data-field="question.title">题目描述</th>
                    <th data-field="question.qtype" data-formatter="qtype">题型</th>
                    <th data-field="score">题目分值</th>
                    <th data-field="seq" data-formatter="seq">排序</th>
                    <th data-field="id" data-formatter="caozuo" data-width="70px">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<!--End 页面主要内容-->
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
    function seq(value, row) {
        return '<input class="qrs-seq form-control form-control-sm" type="number" data-id=' + row.id + ' value="' + value + '"/>';
    }

    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        htm += '<a class="btn btn-sm btn-default" onclick="del(\'' + value + '\')" href="javascript:;" title="删除">删除</a>';
        htm += '</div>';
        return htm;
    }

    $("#addQuestion").click(function () {
        layer_show("题目", "${ctx.contextPath}/admin/question/listQuestion?qrid=${questionRule.id}");
    });

    $("#closeBtn").click(function () {
        parent.reloadData();
        layer_close();
    });

    function del(id) {
        $.confirm({
            title: '提示',
            content: '是否要删除？',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: "${ctx.contextPath}/admin/paper/delQRS?qrsids=" + id,
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

    $("#upQuestionSeq").click(function () {
        let json = [];
        $(".qrs-seq").each(function (e) {
            let seq = $(this).val();
            let id = $(this).data("id");
            json.push({"id": id, "seq": seq});
        });
        if (json.length == 0) {
            return false;
        }
        $.ajax({
            url: "${ctx.contextPath}/admin/exam/upQRSSeq",
            data: {
                "json": JSON.stringify(json)
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

    function reloadData(){
        $("#table-pagination").bootstrapTable('refresh');
    }
</script>
</body>
</html>
