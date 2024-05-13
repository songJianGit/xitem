<!DOCTYPE html>
<html>
<head>
    <#include "../../commons/head.ftl"/>
</head>
<body style="background-color: #fff">
<div class="row">
    <div class="col-lg-12">
        <div class="card">

            <div class="card-toolbar clearfix">
                <form class="form-inline" method="post" action="#!" role="form" id="searchform">
                    <div class="input-group">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="button">
                                问题描述
                            </button>
                        </div>
                        <input type="text" class="form-control" value="" name="f-title"
                               placeholder="问题描述">
                    </div>

                    <div class="input-group">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="button">
                                考题分类
                            </button>
                        </div>
                        <select class="form-control" name="f-qclass">
                            <option value="">---请选择---</option>
                            <#list qclasslist as item>
                                <option value="${item.id!}">${item.name!}</option>
                            </#list>
                        </select>
                        <input type="hidden" value="1" name="fo-qclass">
                    </div>

                    <div class="input-group">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="button">
                                考题类型
                            </button>
                        </div>
                        <select class="form-control" name="f-qtype">
                            <option value="">---请选择---</option>
                            <option value="0">是非</option>
                            <option value="1">单选</option>
                            <option value="2">多选</option>
                        </select>
                        <input type="hidden" value="1" name="fo-qtype">
                    </div>

                    <button type="button" id="searchBtn" class="btn btn-primary">搜索</button>
                </form>
            </div>

            <div class="card-body">
                <div id="custom-toolbar">
                    <div class="form-inline" role="form">
                        <input class="qrs-score form-control" style="width: 80px;" type="number" value=""
                               placeholder="分值" id="scoreInput"/>
                        <button onclick="addQuestionToRule()" class="btn btn-primary">
                            确定
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
                           data-url="${ctx.contextPath}/admin/question/data?f-extype=${exam.examtype!}&fo-extype=1"
                           data-side-pagination="server">
                        <thead>
                        <tr>
                            <th data-checkbox="true"></th>
                            <th data-field="title">问题描述</th>
                            <th data-field="qtype" data-formatter="qtype">问题类型</th>
                            <th data-field="qclassName">问题分类</th>
                            <th data-field="extype" data-formatter="extype">考试测评类型</th>
                            <th data-field="qcode">问题编号</th>
                            <th data-field="cdate">入库时间</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../../commons/js.ftl"/>
<script type="text/javascript">
    function addQuestionToRule() {
        if (getSelectionIds() != false) {
            let score = $("#scoreInput").val()
            if (isBlank(score)) {
                layer.msg("请先填写题目分数");
                return;
            }
            $.ajax({
                url: "${ctx.contextPath}/admin/exam/addQRS",
                data: {
                    'qrid': '${qrid}',
                    'qids': getSelectionIds().join(","),
                    'score': score
                },
                success: function (data) {
                    if (data.result) {
                        parent.reloadTable();
                        $("#table-pagination").bootstrapTable('refresh');
                        layer.msg(data.msg);
                        setTimeout(function () {
                            layer_close();
                        }, 1200);
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    }

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/question/data?f-extype=${exam.examtype!}&fo-extype=1&" + $("#searchform").serialize()
        });
    });
</script>
</body>
</html>
