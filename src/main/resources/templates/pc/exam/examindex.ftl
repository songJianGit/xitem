<!DOCTYPE html>
<html lang="zh">
<head>
    <title>考试</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <#list examList as item>
            <div class="card mb-3">
                <div class="card-body">
                    <div>${item.title!}</div>
                    <div>${item.stime!}&nbsp;-&nbsp;${item.etime!}</div>
                </div>
                <div class="card-footer"><a href="${ctx.contextPath}/pc/exam/${item.id!}">查看</a></div>
            </div>
        </#list>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
