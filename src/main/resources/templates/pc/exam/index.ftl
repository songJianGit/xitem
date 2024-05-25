<!DOCTYPE html>
<html lang="zh">
<head>
    <title>考试简介</title>
    <#include "../commons/head.ftl"/>
    <style>
        .exam-item {
            margin: 13px 0;
        }
    </style>
</head>
<body>
<div class="card" style="border: 0">
    <div class="card-body">
        <div class="exam-item">考试标题：${exam.title!}</div>
        <div class="exam-item">开始时间：${exam.stime!}</div>
        <div class="exam-item">结束时间：${exam.etime!}</div>
        <#if exam.duration??>
            <#if exam.duration==-1>
                <div class="exam-item">考试时长：不限</div>
            <#else>
                <div class="exam-item">考试时长：${exam.duration!}分钟</div>
            </#if>
        </#if>
        <div class="exam-item">考试总分：${paperScore!}</div>
    </div>
    <div class="card-body text-center">
        <a href="${ctx.contextPath}/pc/exam/examPageShow?eid=${exam.id!}" class="btn btn-primary">开始考试</a>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
