<!DOCTYPE html>
<html lang="zh">
<head>
    <title>课程</title>
    <#include "../commons/head.ftl"/>
    <style>
        .course-title {
            text-align: center;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            margin-top: 7px;
            color: #000;
        }
    </style>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body row">
        <#list courseList as item>
            <a class="col-4" href="${ctx.contextPath}/pc/course/c/${item.id!}">
                <div class="card mt-3">
                    <div class="card-body">
                        <img style="width: 100%;max-height: 200px" src="${ctx.contextPath}${item.cover!'/static/admin/commons/img/defaultimg.webp'}" alt="图">
                        <div class="course-title">${item.title!}</div>
                    </div>
                </div>
            </a>
        </#list>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
