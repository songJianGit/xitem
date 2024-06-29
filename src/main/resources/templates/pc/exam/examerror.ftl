<!DOCTYPE html>
<html lang="zh">
<head>
    <title>考试异常</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="card" style="border: 0">
    <#if exStatus??>
        <div class="card-title">
            ${exStatus}
        </div>
    </#if>
    <div class="card-body text-center">
        <#if '超过考试时长未交卷'==exStatus>
            <div>
                考试规定时长：${duration!}分钟<br/>
                开始答题时间：${cDate!}<br/>
                当前时间：${nowDate!}<br/>
                <button type="button" class="btn btn-primary" onclick="onSubFUN()">交卷</button>
            </div>
        </#if>
    </div>
    <div class="card-body">
        <a class="btn btn-secondary" href="${ctx.contextPath}/index">返回首页</a>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function onSubFUN() {
        let indexLoadSub = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/examPageSubmit",
            cache: false,// 不缓存
            data: {
                "userPaperId": '${userPaperId!}'
            },
            success: function (data) {
                layer.close(indexLoadSub);
                if (data.result) {
                    window.location.href = "${ctx.contextPath}/pc/exam/examSubmitOk?userPaperId=${userPaperId!}";
                } else {
                    alert(data.msg);
                }
            }
        });
    }
</script>
</body>
</html>
