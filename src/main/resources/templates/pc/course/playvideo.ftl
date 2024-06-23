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
            <video style="width: 100%;min-height: 500px" controlsList="nodownload" controls autoplay>
                <h2>您的浏览器不支持视频标记，建议升级浏览器或使用支持视频标记的浏览器</h2>
                <#list courseFileItemIds as id>
                    <source src="${ctx.contextPath}/resource/files/courseFileVideo/${id!}"
                            type="video/mp4"/>
                </#list>
            </video>
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
