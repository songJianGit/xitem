<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="card">
    <#if exStatus??>
        <div>
            ${exStatus}
        </div>
    </#if>
    <#if '已超过考试时长'==exStatus>
        <div>
            考试规定时长：${duration!}分钟<br/>
            开始答题时间：${cDate!}<br/>
            当前时间：${nowDate!}<br/>
        </div>
    </#if>
</div>
</body>
</html>
