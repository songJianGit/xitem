<!DOCTYPE html>
<html lang="zh">
<head>
    <title>课程</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <div class="card mt-3">
            <div class="card-header">课程</div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <#list courseList as item>
                        <li class="list-group-item">
                            <a href="${ctx.contextPath}/pc/course/c/${item.id!}">${item.title!}</a>
                        </li>
                    </#list>
                </ul>
            </div>
            <div class="card-footer">
                <a href="#!">更多>></a>
            </div>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
