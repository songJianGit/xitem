<!DOCTYPE html>
<html lang="zh">
<head>
    <title>首页</title>
    <#include "commons/head.ftl"/>
</head>
<body>
<div class="p-5">
    <h5>xitem 首页</h5>
    <div>
        考试：
        <#list examList as item>
            <div><a href="${ctx.contextPath}/pc/exam/index/${item.id!}">${item.title!}</a></div>
        </#list>
    </div>
</div>
<#include "commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
