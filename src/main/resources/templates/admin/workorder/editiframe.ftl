<!DOCTYPE html>
<html lang="zh">
<head>
    <title>反馈纪录</title>
    <link href="${ctx.contextPath}/static/pc/commons/css/style.css" rel="stylesheet">
</head>
<body style="margin: 0">
<script id="editor" type="text/plain" style="width:99.9%;height:379px;"></script>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    let ue = UE.getEditor('editor');

    function getContent() {
        return UE.getEditor('editor').getContent();
    }
</script>
</body>
</html>
