<!DOCTYPE html>
<html lang="zh">
<head>
    <title>考试</title>
    <#include "../commons/head.ftl"/>
    <style>
        .exam-item {
            padding: 7px;
            margin-bottom: 3px;
        }
    </style>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <#list examList as item>
            <div class="exam-item">
                <a href="${ctx.contextPath}/pc/exam/${item.id!}">${item.title!}</a>
            </div>
        </#list>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
