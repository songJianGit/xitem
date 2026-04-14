<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
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
                    <div class="col-3">
                        <div class="card">
                            <div class="card-header">
                                文章列表
                                <@projectReadFlagTag>
                                    <button class="btn btn-primary" type="button" onclick="addBtn()">添加文章</button>
                                </@projectReadFlagTag>
                            </div>
                            <div class="card-body">
                                <ul class="list-group">
                                    <#list alist as item>
                                        <li class="list-group-item">
                                            <a target="contentFrame"
                                               href="${ctx.contextPath}/admin/cms/articlelistwikishow?id=${item.id!}">${item.title}</a>
                                        </li>
                                    </#list>
                                </ul>
                            </div>

                        </div>
                    </div>
                    <div class="col-9">
                        <#if articleId??>
                            <iframe id="myIframe" name="contentFrame"
                                    src="${ctx.contextPath}/admin/cms/articlelistwikishow?id=${articleId!}"
                                    style="width: 100%;border: 0 solid #808080"></iframe>
                        <#else >
                            暂无数据
                        </#if>
                    </div>
                </div>
            </div>
        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">

    function addBtn() {
        layer_show('新增', "${ctx.contextPath}/admin/cms/articleEdit3", "80%");
    }

    function editBtn(id) {
        layer_show('编辑', "${ctx.contextPath}/admin/cms/articleEdit3?id=" + id, "80%");
    }

    function delById(id) {
        $.confirm({
            title: '提示',
            content: '是否删除？',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: "${ctx.contextPath}/admin/cms/delById?id=" + id,
                            success: function (d) {
                                layer.msg(d.msg);
                                setTimeout(function () {
                                    reload();
                                }, 500)
                            }
                        });
                    }
                },
                cancel: {
                    text: '取消',
                    action: function () {
//                            $.alert('取消的!');
                    }
                }
            }
        });
    }

    function reload() {
        window.location.reload();
    }

    window.addEventListener('message', function (event) {
        // if (event.origin !== "http://xitem.com") return; // 确保只接受来自信任域名的消息
        if (event.data.type === 'resize') {
            let height = event.data.height;
            if (height < 600) {
                height = 600;
            }
            document.getElementById('myIframe').style.height = height + 'px';
        }
        if (event.data.type === 'editBtnFun') {
            let id = event.data.id;
            editBtn(id);
        }
        if (event.data.type === 'delBtnFun') {
            let id = event.data.id;
            delById(id);
        }
    });
</script>
</body>
</html>
