<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../../commons/head.ftl"/>
</head>
<body style="background-color: white">
<div class="card" style="-webkit-box-shadow:none;box-shadow:none;">
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
    <div class="card-toolbar d-flex flex-column flex-md-row">
        <div class="toolbar-btn-action">
            <button type="button" id="saveBtn" class="btn btn-label btn-primary">
                <label><i class="mdi mdi-checkbox-marked-circle-outline"></i></label>
                保存
            </button>
            <button type="button" id="addQuestion" class="btn btn-primary">
                添加考题
            </button>
            <button type="button" id="delBtn" class="btn btn-primary m-r-5">
                删除
            </button>
        </div>
    </div>

    <div class="card-body">
        <div id="custom-toolbar">
            <form class="form-inline" action="#!" role="form">
                <div class="input-group m-r-5">
                    <div class="input-group-prepend">
                        <span class="input-group-text">规则名称</span>
                    </div>
                    <input type="text" class="form-control" id="questionRuleTitle" name="title"
                           value="${questionRule.title!}"
                           placeholder="规则名称" maxlength="100">
                </div>
                <div class="input-group m-r-5">
                    <div class="input-group-prepend">
                        <span class="input-group-text">抽提数</span>
                    </div>
                    <input type="number" class="form-control" id="questionRuleNum" name="num"
                           value="${questionRule.num!}" placeholder="抽提数">
                </div>
            </form>
        </div>
        <div class="table-responsive">
            <table id="table-pagination"
                   data-toolbar="#custom-toolbar"
                   data-toggle="table"
                   data-show-refresh="true"
                   data-url="${ctx.contextPath}/admin/paper/listQRSData?qrId=${questionRule.id!}"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th data-checkbox="true"></th>
                    <th data-formatter="indexFormatter">序号</th>
                    <th data-field="title">题目描述</th>
                    <th data-field="qtype" data-formatter="qtype">题型</th>
                    <th data-field="score" data-formatter="score" data-width="100px">分值</th>
                    <th data-field="seq" data-formatter="seq" data-width="100px">排序</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<!--End 页面主要内容-->
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
    function indexFormatter(value, row, index) {
        return index + 1;
    }

    function score(value, row) {
        return '<input class="qrs-score form-control form-control-sm" step="0.01" type="number" data-id=' + row.id + ' value="' + value + '"/>';
    }

    function seq(value, row) {
        return '<input class="qrs-seq form-control form-control-sm" type="number" data-id=' + row.id + ' value="' + value + '"/>';
    }

    $("#addQuestion").click(function () {
        layer_show("题目", "${ctx.contextPath}/admin/question/listQuestion?qrid=${questionRule.id}","95%");
    });

    let index_;
    $("#saveBtn").click(function () {
        index_ = layer.load(1, {shade: [0.1, '#fff']});
        upQuestionScore();
    });

    $("#delBtn").click(function () {
        if (getSelectionIds() != false) {
            del(getSelectionIds());
        }
    });

    function del(ids) {
        $.confirm({
            title: '提示',
            content: '是否删除？',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: "${ctx.contextPath}/admin/paper/delQRS",
                            data: {
                                qrsids: ids.join(',')
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

    // 保存规则名称和抽提数
    function saveQuestionRule() {
        let title = $('#questionRuleTitle').val();
        let num = $('#questionRuleNum').val();
        if (isBlank(title)) {
            layer.msg('规则名称不可为空');
            layer.close(index_);
            return false;
        }
        if (isBlank(num)) {
            layer.msg('抽提数不可为空');
            layer.close(index_);
            return false;
        }
        if (isPositiveInteger(num)) {
            $.ajax({
                url: "${ctx.contextPath}/admin/paper/upQuestionRule",
                type: "post",
                data: {
                    id: '${questionRule.id}',
                    title: title,
                    num: num,
                },
                success: function (data) {
                    if (data.result) {
                        parent.reloadData();
                        layer.close(index_);
                        layer_close();
                    } else {
                        layer.close(index_);
                        alert(data.msg);
                    }
                }
            });
        } else {
            layer.msg('抽提数应该是一个正整数');
            layer.close(index_);
        }
    }

    // 更新排序
    function upQuestionSeq() {
        let jsonSeq = [];
        $(".qrs-seq").each(function (e) {
            let seq = $(this).val();
            let id = $(this).data("id");
            jsonSeq.push({"id": id, "seq": seq});
        });
        $.ajax({
            url: "${ctx.contextPath}/admin/paper/upQRSSeq",
            type: "post",
            data: {
                "jsonSeq": JSON.stringify(jsonSeq)
            },
            success: function (data) {
                if (data.result) {
                    saveQuestionRule()
                } else {
                    layer.close(index_);
                    alert(data.msg);
                }
            }
        });
    }

    // 更新分值
    function upQuestionScore() {
        let jsonScore = [];
        $(".qrs-score").each(function (e) {
            let score = $(this).val();
            let id = $(this).data("id");
            jsonScore.push({"id": id, "score": score});
        });
        $.ajax({
            url: "${ctx.contextPath}/admin/paper/upQRSScore",
            type: "post",
            data: {
                "jsonScore": JSON.stringify(jsonScore)
            },
            success: function (data) {
                if (data.result) {
                    upQuestionSeq();
                } else {
                    layer.close(index_);
                    alert(data.msg);
                }
            }
        });
    }

    function reloadData() {
        $("#table-pagination").bootstrapTable('refresh');
    }

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/paper/listQRSData?qrId=${questionRule.id!}&" + $("#searchform").serialize()
        });
    });
</script>
</body>
</html>
