<!DOCTYPE html>
<html lang="zh">
<head>
    <title>用户中心</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <ul class="list-group">
            <li class="list-group-item"><a href="${ctx.contextPath}/pc/user/userInfo">个人信息</a></li>
            <li class="list-group-item"><a href="${ctx.contextPath}/pc/user/userCourse">课程纪录</a></li>
            <li class="list-group-item"><a href="${ctx.contextPath}/pc/user/userExam">考试纪录</a></li>
            <li class="list-group-item"><a href="${ctx.contextPath}/pc/user/userWorkOrder">问题反馈</a></li>
        </ul>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
