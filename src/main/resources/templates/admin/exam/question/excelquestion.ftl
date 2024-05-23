<!DOCTYPE html>
<html>
<head>
    <#include "../../commons/head.ftl"/>
</head>
<body style="background-color: #fff">
<form action="#!" method="post" id="formData" enctype="multipart/form-data">
    <div style="padding: 23px;">
        <div>模板下载：<a href="${ctx.contextPath}/static/excelTemplate/examT.xlsx">examT.xlsx</a></div>
        <div style="overflow: hidden;border-bottom: 1px solid #e0e0e0;padding-bottom: 17px;margin-top: 23px">
            <input type="file" name="fileinfo" style="float: left">
            <button type="button" style="float: left" class="btn btn-primary" id="subBtn">导 入</button>
        </div>
        <div style="clear: both"></div>
        <div style="margin-top: 13px">上传结果：
            <div id="errorinfo">暂无</div>
        </div>
    </div>
</form>
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
    $("#subBtn").click(function () {
        let index = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        setTimeout(function () {
            let formData = new FormData($("#formData")[0]);
            $.ajax({
                url: "${ctx.contextPath}/admin/question/questionImport",
                data: formData,
                type: 'POST',
                // async: false,
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    layer.close(index);
                    if (data.result) {
                        parent.reloadTable()
                        layer.msg(data.msg);
                        setTimeout(function () {
                            layer_close();
                        }, 1000);
                    } else {
                        layer.msg('导入失败');
                        let info = '';
                        let errors = data.data;
                        for (let i = 0; i < errors.length; i++) {
                            info += errors[i] + '<br/>';
                        }
                        $("#errorinfo").html(info);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.close(index);
                }
            });
        }, 500);
    });
</script>
</body>
</html>
