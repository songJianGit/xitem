<!DOCTYPE html>
<html lang="zh">
<head>
    <title>反馈纪录</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <form action="${ctx.contextPath}/pc/workorder/save" method="post" onsubmit="return check();">
            <input type="hidden" value="${workOrder.id!}" name="id"/>
            <div class="form-group">
                <label for="title">标题</label>
                <input type="text" class="form-control" placeholder="标题" value="${workOrder.title!}" name="title"
                       id="title"/>
            </div>
            <div class="form-group">
                <label for="editor">内容</label>
                <iframe id="editorFrame" src="${ctx.contextPath}/pc/workorder/editiframe?id=${workOrder.id}"
                        style="width: 100%;min-height: 417px;border: 0px solid #808080"></iframe>
                <input type="hidden" name="content" id="content"/>
            </div>
            <button type="submit" class="btn btn-primary">提交</button>
        </form>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function check() {
        getEditorFrameContent();
        return true;
    }

    function getEditorFrameContent() {
        const iframe = document.getElementById('editorFrame');
        let content = iframe.contentWindow.getContent();
        $("#content").val(content);
    }
</script>
</body>
</html>