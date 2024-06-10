<!DOCTYPE html>
<html lang="zh">
<head>
    <title>课程</title>
    <#include "../commons/head.ftl"/>
    <style>
    </style>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <div class="card">
            <div class="card-body">
                <div class="row">
                    <div class="col-5">
                        <img style="width: 100%;max-height: 200px"
                             src="${ctx.contextPath}${course.cover!'/static/admin/commons/img/defaultimg.webp'}"
                             alt="图">
                    </div>
                    <div class="col-7">
                        <div>课程标题：${course.title!}</div>
                        <div>当前进度：<#if courseUser??>${courseUser.precent!}%<#else>0%</#if></div>
                        <div>课程时长：${course.learnTime!}分钟</div>
                        <div class="mt-3">
                            <a class="btn btn-primary" href="${ctx.contextPath}/pc/course/c/${course.id!}">开始学习</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card mt-3">
            <div class="card-header">课程简介</div>
            <div class="card-body">${course.content!'暂无'}</div>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
