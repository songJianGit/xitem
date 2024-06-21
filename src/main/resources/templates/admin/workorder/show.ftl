<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        img {
            max-width: 100%;
        }
    </style>
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <#include "../commons/aside.ftl"/>
        <!--End 左侧导航-->

        <!--头部信息-->
        <#include "../commons/header.ftl"/>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid p-t-15">

                <div class="row">
                    <div class="col-12">
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

                        <div class="card mt-3">
                            <div class="card-header">回复</div>
                            <div class="card-body">
                                <form action="${ctx.contextPath}/admin/workorder/saveItem" method="post"
                                      onsubmit="return check();">
                                    <input type="hidden" value="${workOrder.id!}" name="workOrderId">
                                    <iframe id="editorFrame" src="${ctx.contextPath}/admin/workorder/editiframe"
                                            style="width: 100%;min-height: 467px;border: 0 solid #808080"></iframe>
                                    <input type="hidden" name="content" id="content"/>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary">提交</button>
                                        <button type="button" class="btn btn-default"
                                                onclick="history.back(-1);return false;">返回
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function check() {
        getEditorFrameContent();
        return true;
    }

    function getEditorFrameContent() {
        const iframe = document.getElementById('editorFrame');
        let content = iframe.contentWindow.getContent();
        $("#content").val(content);
    }
</script>
</body>
</html>