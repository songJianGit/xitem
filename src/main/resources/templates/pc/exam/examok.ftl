<!DOCTYPE html>
<html>
<head>
    <title>考试完成</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div>
    <div>考试标题：${examname!}</div>
    <div>考试得分：${score!}</div>
    <div>姓名：${Session.puser.username!}</div>
    <div>总分：${maxscore!}</div>
    <div>用时：${paperduration!}</div>
    <a href="${ctx.contextPath}/pc/index">返回首页</a>
</div>
</body>
</html>
