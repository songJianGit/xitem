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
                                <form action="${ctx.contextPath}/admin/question/save" method="post" class="row"
                                      onsubmit="return check();">
                                    <input type="hidden" name="id" value="${question.id!}"/>
                                    <div class="form-group col-12">
                                        <label for="title">题目描述</label>
                                        <textarea maxlength="255" class="form-control" name="title">${question.title!}</textarea>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="qcategoryName">题目目录</label>
                                        <input id="qcategoryName" placeholder="点击选择目录" class="form-control" value="${question.qcategoryName!}" readonly/>
                                        <input id="qcategory" type="hidden" name="qcategory" value="${question.qcategory!}"/>
                                    </div>

                                    <div class="form-group col-6">
                                        <label for="qtype">题目类型</label>
                                        <select class="form-control" name="qtype" id="qtype">
                                            <option value="0"
                                                    <#if question.qtype??><#if question.qtype==0>selected</#if></#if>>是非
                                            </option>
                                            <option value="1"
                                                    <#if question.qtype??><#if question.qtype==1>selected</#if></#if>>单选
                                            </option>
                                            <option value="2"
                                                    <#if question.qtype??><#if question.qtype==2>selected</#if></#if>>多选
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group col-6">
                                        <label>选项</label>
                                        <div id="optionBox"></div>
                                        <button type="button" class="btn btn-sm btn-label btn-default" id="addOption">
                                            <label><i class="mdi mdi-plus"></i></label>
                                            增加选项
                                        </button>
                                        <input type="hidden" id="optionJson" name="optionJson">
                                    </div>
                                    <div class="form-group col-12">
                                        <button type="submit" class="btn btn-primary">保 存</button>
                                        <button type="button" class="btn btn-default"
                                                onclick="history.back(-1);return false;">返 回
                                        </button>
                                    </div>
                                </form>

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
    function check() {
        let qtype = $("#qtype").val();
        let optionOK = 0;
        $("select[name='optionRight']").each(function () {
            let optionA = $(this).val();
            if (optionA == 1) {
                optionOK++;
            }
        });
        if (optionOK == 0) {
            layer.msg("至少需要一个正确答案");
            return false;
        }
        if (qtype == 0 && optionOK > 1) {
            layer.msg("是非题只能有一个正确答案");
            return false;
        }
        if (qtype == 1 && optionOK > 1) {
            layer.msg("单选题只能有一个正确答案");
            return false;
        }
        let title = $("textarea[name='title']").val();
        if (isBlank(title)) {
            layer.msg("请填写题目描述");
            return false;
        }
        let qcategory = $("#qcategory").val();
        if (isBlank(qcategory)) {
            layer.msg("请选择题目目录");
            return false;
        }
        optionJson();
        return true;
    }

    // 整理题目选项信息
    function optionJson() {
        let optionRows = [];
        $(".optionRow").each(function () {
            let optionId = $(this).data('optionid');
            let optionTitle = $(this).find('input[name="optionTitle"]').val();
            let optionRight = $(this).find('select[name="optionRight"]').val();
            let opt = {
                optionId: optionId,
                optionTitle: optionTitle,
                optionRight: optionRight
            };
            optionRows.push(opt);
        });
        $('#optionJson').val(JSON.stringify(optionRows));
    }

    $(function () {
        typeChange();
    });

    $('#addOption').click(function () {
        let num = $('#optionBox').find('.optionRow').length;
        if (num >= 12) {
            layer.msg("最多12个选项");
        } else {
            $('#optionBox').append(getHtm('', qtype, '', 0, num));
        }
    });

    function deleteOption(dom) {
        $(dom).parent().parent(".optionRow").remove();
    }

    $('#qtype').change(function () {
        typeChange();
    });

    function typeChange() {
        $('#optionBox').html('');
        let qid = '${question.id!}';
        let qtype = $("#qtype").val();
        if (isBlank(qid)) {
            if (qtype == 0) {// 是非
                $('#optionBox').append(getHtm('', qtype, '正确', 0, 0));
                $('#optionBox').append(getHtm('', qtype, '错误', 0, 1));
            } else {// 单选和多选
                $('#optionBox').append(getHtm('', qtype, '', 0, 0));
                $('#optionBox').append(getHtm('', qtype, '', 0, 1));
            }
        } else {
            <#list questionOption as option>
            $('#optionBox').append(getHtm('${option.id}', qtype, '${option.title}', `${option.optionRight}`, `${option_index}`));
            </#list>
            if (qtype == 0) {
                qtypeInitTitle0();
                $('#addOption').hide();
            } else {
                $('#addOption').show();
            }
        }
    }

    /**
     *
     * @param optionid 选项id
     * @param qtype 题目类型
     * @param title 选项标题
     * @param optionright 是否正确答案 1-是 0-否
     * @param domNum 选项序号（从0开始，大于1的会显示删除按钮）
     * @returns {string}
     */
    function getHtm(optionid, qtype, title, optionright, domNum) {
        let htm = '';
        let r1 = '';
        let r0 = '';
        let readonly = '';
        if (optionright == 1) {
            r1 = 'selected';
        }
        if (optionright == 0) {
            r0 = 'selected';
        }
        if (qtype == 0) {
            readonly = 'readonly';
        }
        htm += '<div class="form-row m-b-5 optionRow" data-optionid="' + optionid + '">';
        htm += '<div class="col-7">';
        htm += '<input ' + readonly + ' type="text" class="form-control" value="' + title + '" name="optionTitle" maxlength="255">';
        htm += '</div>';
        htm += '<div class="col-2">';
        htm += '<select class="form-control" name="optionRight">';
        htm += '<option value="1" ' + r1 + ' >正确答案</option>';
        htm += '<option value="0" ' + r0 + ' >错误答案</option>';
        htm += '</select>';
        htm += '</div>';

        if (domNum > 1) {
            htm += '<div class="col-3">';
            htm += '<button type="button" class="btn btn-label btn-default" onclick="deleteOption(this)">';
            htm += '<label><i class="mdi mdi-minus"></i></label>';
            htm += '删除选项';
            htm += '</button>';
            htm += '</div>';
        }

        htm += '</div>';
        return htm;
    }

    // 是非题，强制调整选项的标题
    function qtypeInitTitle0() {
        let num = 0;
        $('input[name="optionTitle"]').each(function () {
            if (num == 0) {
                $(this).val('正确');
            }
            if (num == 1) {
                $(this).val('错误');
            }
            if (num > 1) {
                $(this).parent().parent().remove();
            }
            num++;
        });
    }

    $("#qcategoryName").click(function (){
        layer_show("课程目录", '${ctx.contextPath}/admin/category/question/categoryShow');
    });

    function categoryCallback(data){
        $("#qcategory").val(data.id);
        $("#qcategoryName").val(data.title);
    }
</script>
</body>
</html>
