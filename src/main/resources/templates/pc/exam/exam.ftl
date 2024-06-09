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
<body class="center-vh">
<div class="card card-clear-420">
    <div class="card-body">
        <div class="exam-item">考试标题：${exam.title!}</div>
        <div class="exam-item">开始时间：${exam.stime!}</div>
        <div class="exam-item">结束时间：${exam.etime!}</div>
        <#if exam.duration??>
            <#if exam.duration==-1>
                <div class="exam-item">考试限时：不限</div>
            <#else>
                <div class="exam-item">考试限时：${exam.duration!}分钟</div>
            </#if>
        </#if>
        <div class="exam-item">考试总分：${paperScore!}</div>
        <#if exam.duration??>
            <#if exam.maxNum==-1>
                <div class="exam-item">重考次数：不限</div>
            <#else>
                <div class="exam-item">重考次数：${exam.maxNum!}</div>
            </#if>
        </#if>
    </div>
    <div class="card-body text-center">
        <table class="table">
            <thead>
            <tr>
                <td>开始时间</td>
                <td>用时</td>
                <td>分数</td>
            </tr>
            </thead>
            <tbody>
            <#list listUserPaper as item>
                <tr>
                    <td>${item.createDate!}</td>
                    <td>${item.duration!}</td>
                    <td><a title="查卷"
                           href="${ctx.contextPath}/pc/exam/userPaperPreview?userPaperId=${item.id!}">${item.score!}</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <div class="card-body text-center">
            <#if examStatus==1>
                <a href="${ctx.contextPath}/pc/exam/examPageShow?eid=${exam.id!}" class="btn btn-primary">开始考试</a>
            </#if>
            <button type="button" class="btn btn-secondary" onclick="history.back(-1);return false;">返回
            </button>
        </div>
    </div>
    <#include "../commons/js.ftl"/>
    <script type="text/javascript">
    </script>
</body>
</html>
