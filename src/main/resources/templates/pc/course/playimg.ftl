<!DOCTYPE html>
<html lang="zh">
<head>
    <title>视频播放</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <div class="card">
        <div class="card-title mb-0" style="padding: 5px;">
            <button type="button" class="btn btn-light mr-3" onclick="history.back(-1);return false;">返回</button>
            <#if course??>
                ${course.title!}
            <#else>
                ${courseFile.title!}
            </#if>
        </div>
        <div class="card-body">
            <#list courseFileItemIds as id>
                <img style="width: 100%" src="${ctx.contextPath}/resource/files/courseFileImg/${id}" alt="图">
            </#list>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script src="${ctx.contextPath}/static/pc/timer/study.timeV3.js"></script>
<script type="text/javascript">
    <#if course??>
    let tracer = new TimerTracker('${ctx.contextPath}/pc/timer/trace', '${course.id}', '${timerType.code}', '${timerType.time}', '${timerType.timeMax}');
    tracer.start();
    </#if>
</script>
</body>
</html>
