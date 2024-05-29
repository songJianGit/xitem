<!DOCTYPE html>
<html lang="zh">
<head>
    <title>考试纪录</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body exam-list">
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    $.ajax({
        url: '${ctx.contextPath}/pc/user/userExamData',
        data: {
            pageNum: 1,
            pageSize: 20
        },
        success: function (data) {
            if (data.result) {
                let datas = data.data;
                let htm = '';
                for (let i = 0; i < datas.length; i++) {
                    htm += getHtm(datas[i]);
                }
                $(".exam-list").append(htm)
            } else {
                layer.msg(data.msg);
            }
        }
    });

    function getHtm(item) {
        let htm = '';
        htm += '<div class="card mb-3">';
        htm += '<div class="card-body">';
        htm += '<div>' + item.title + '</div>';
        htm += '<div>' + item.stime + '&nbsp;-&nbsp;' + item.etime + '</div>';
        htm += '</div>';
        htm += '<div class="card-footer"><a href="${ctx.contextPath}/pc/exam/' + item.id + '">查看</a></div>';
        htm += '</div>';
        return htm;
    }
</script>
</body>
</html>
