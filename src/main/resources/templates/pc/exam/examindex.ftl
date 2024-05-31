<!DOCTYPE html>
<html lang="zh">
<head>
    <title>考试</title>
    <#include "../commons/head.ftl"/>
    <style>
        .exam-item {
            margin: 5px;
        }
    </style>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <div class="card">
            <div class="card-header">授权考试</div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <#list examList1 as item>
                        <li class="list-group-item">
                            <a href="${ctx.contextPath}/pc/exam/e/${item.id!}">${item.title!}</a>【${item.stime!}&nbsp;-&nbsp;${item.etime!}】
                        </li>
                    </#list>
                </ul>
                <a href="${ctx.contextPath}/pc/exam/examType?exType=1">更多>></a>
            </div>
        </div>
        <div class="card mt-3">
            <div class="card-header">公开考试</div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <#list examList0 as item>
                        <li class="list-group-item">
                            <a href="${ctx.contextPath}/pc/exam/e/${item.id!}">${item.title!}</a>【${item.stime!}&nbsp;-&nbsp;${item.etime!}】
                        </li>
                    </#list>
                </ul>
                <a href="${ctx.contextPath}/pc/exam/examType?exType=0">更多>></a>
            </div>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
