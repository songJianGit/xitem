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
            <video style="width: 100%;min-height: 500px" controlsList="nodownload" controls autoplay>
                <h2>您的浏览器不支持视频标记，建议升级浏览器或使用支持视频标记的浏览器</h2>
                <#if courseFileItemList0??>
                    <source src="${ctx.contextPath}/resource/files/courseFileVideo/${videoId!}"
                            type="video/mp4"/>
                </#if>
            </video>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
