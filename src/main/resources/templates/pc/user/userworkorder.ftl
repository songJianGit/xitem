<!DOCTYPE html>
<html lang="zh">
<head>
    <title>反馈纪录</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <a class="btn btn-outline-secondary mb-2" href="${ctx.contextPath}/pc/workorder/edit">新的反馈</a>
        <div class="data-list"></div>
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
            url: '${ctx.contextPath}/pc/user/userWorkOrderData',
            data: {
                pageNum: pageNum,
                pageSize: pageSize2024
            },
            success: function (data) {
                if (data.result) {
                    let datas = data.data;
                    if(isNotBlank(datas)){
                        $("#nodata").remove();
                        let htm = '';
                        for (let i = 0; i < datas.length; i++) {
                            htm += getHtm(datas[i]);
                        }
                        $(".data-list").append(htm)
                        if (datas.length < pageSize2024) {
                            if (pageNum2024 > 1) {
                                layer.msg("已全部加载")
                            }
                            $(".netPageBtn").hide();
                        }
                    }else {
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
        htm += '</div>';
        htm += '<div class="card-footer"><a href="${ctx.contextPath}/pc/workorder/show?id=' + item.id + '">查看</a></div>';
        htm += '</div>';
        return htm;
    }
</script>
</body>
</html>
