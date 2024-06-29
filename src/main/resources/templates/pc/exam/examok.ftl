<!DOCTYPE html>
<html lang="zh">
<head>
    <title>考试完成</title>
    <#include "../commons/head.ftl"/>
    <style>
        .exam-item {
            margin: 13px 0;
        }
    </style>
</head>
<body class="center-vh">
<div class="card card-clear-420">
    <div class="card-body">
        <div class="exam-item">考试标题：${examtitle!}</div>
        <div class="exam-item">考试得分：${score!}</div>
        <div class="exam-item">姓名：${Session.puser.userName!}</div>
        <div class="exam-item">总分：${maxscore!}</div>
        <div class="exam-item">用时：${paperduration!}</div>
        <div class="exam-item"><a class="btn btn-secondary" href="${ctx.contextPath}/index">返回首页</a></div>
    </div>
</div>
</body>
</html>
