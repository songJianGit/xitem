<!DOCTYPE html>
<html lang="zh">
<head>
    <title>视频播放</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <div class="card">
        <div class="card-header">${courseFile.title}</div>
        <div class="card-body">
            <#list courseFileItemIds as id>
                <img style="width: 100%" src="${ctx.contextPath}/resource/files/courseFileImg/${id}" alt="图">
            </#list>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
