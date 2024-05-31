<!DOCTYPE html>
<html lang="zh">
<head>
    <title>考试</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div style="width: 100%;text-align: center;font-size: 17px;margin-top: 5px;">
        <#if exType==1>公开考试</#if>
        <#if exType==0>授权考试</#if>
    </div>
    <div class="pc-body exam-list">
        <div id="nodata">暂无数据</div>
    </div>
    <div style="width: 100%;text-align: center">
        <button type="button" class="btn btn-primary netPageBtn" onclick="netPage()">查看更多</button>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    let pageNum2024 = 1;
    let pageSize2024 = 20;
    $(function () {
        loadData(pageNum2024);
    });

    function netPage() {
        pageNum2024++;
        loadData(pageNum2024);
    }

    function loadData(pageNum) {
        $.ajax({
            url: '${ctx.contextPath}/pc/exam/examTypeData',
            data: {
                exType: `${exType!}`,
                pageNum: pageNum,
                pageSize: pageSize2024
            },
            success: function (data) {
                if (data.result) {
                    let datas = data.data;
                    if (isNotBlank(datas)) {
                        $("#nodata").remove();
                        let htm = '';
                        for (let i = 0; i < datas.length; i++) {
                            htm += getHtm(datas[i]);
                        }
                        $(".exam-list").append(htm)
                        if (datas.length < pageSize2024) {
                            if (pageNum2024 > 1) {
                                layer.msg("已全部加载")
                            }
                            $(".netPageBtn").hide();
                        }
                    } else {
                        if (pageNum2024 > 1) {
                            layer.msg("已全部加载")
                        }
                        $(".netPageBtn").hide();
                    }
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    }

    function getHtm(item) {
        let htm = '';
        htm += '<div class="card mb-3">';
        htm += '<div class="card-body">';
        htm += '<div>' + item.title + '</div>';
        htm += '<div>' + item.stime + '&nbsp;-&nbsp;' + item.etime + '</div>';
        htm += '</div>';
        htm += '<div class="card-footer"><a href="${ctx.contextPath}/pc/exam/e/' + item.id + '">查看</a></div>';
        htm += '</div>';
        return htm;
    }
</script>
</body>
</html>
