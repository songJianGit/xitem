<!DOCTYPE html>
<html lang="zh">
<head>
    <title>反馈纪录-新的反馈</title>
    <#include "../commons/head.ftl"/>
    <style>
        .border-bottom:last-child {
            border-bottom: none !important; /* 移除最后一个列表项的底部边框 */
        }

        img {
            max-width: 100%;
        }
    </style>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <div class="card">
            <div class="card-header">${workOrder.title!}</div>
            <#list workOrderItems as item>
                <div class="card-body border-bottom">
                    <div class="row">
                        <div class="col-3">
                            <div>${item.nickName!}</div>
                        </div>
                        <div class="col-9">
                            <div style="font-size: 13px;color: #808080">${item.createDate!}</div>
                            <div>${item.content!}</div>
                            <#if item.fileImg??>
                                <div>附件：<a target="_blank" href="${ctx.contextPath}${item.fileImg!}">点击查看</a>
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>
            </#list>
        </div>

        <#if workOrder.workStatus==0>
            <div class="card mt-3">
                <div class="card-header">继续反馈</div>
                <div class="card-body">
                    <form action="${ctx.contextPath}/pc/workorder/saveItem" method="post" enctype="multipart/form-data">
                        <input type="hidden" value="${workOrder.id!}" name="workOrderId">
                        <div class="form-group">
                            <textarea rows="3" name="content" id="content" class="form-control"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="files">附件</label>
                            <input name="file" type="file" class="form-control-file"/>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">提交</button>
                            <button type="button" class="btn btn-light" onclick="history.back(-1);return false;">返回
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        <#else>
            <p style="text-align: center;padding: 7px 0">反馈已完结</p>
        </#if>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>