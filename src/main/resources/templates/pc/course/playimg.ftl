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
            <button type="button" class="btn btn-light mr-3" onclick="backBtn()">返回</button>
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
<script type="text/javascript" src="${ctx.contextPath}/static/pc/timer/onlyhtml.js"></script>
<script type="text/javascript">
    new OneHtm("coursePlay", "${course.id}", "只能同时学习一个课").init();

    let tracer;
    <#if course??>
    tracer = new TimerTracker('${ctx.contextPath}/pc/timer/trace', '${course.id}', '${timerType.code}', '${timerType.time}', '${timerType.timeMax}');
    tracer.start();
    </#if>

    function backBtn() {
        if (isNotBlank(tracer)) {
            tracer.end();
        }
        // history.back();
        self.location = document.referrer;// 返回到上一个页面并刷新页面
    }
</script>
</body>
</html>
