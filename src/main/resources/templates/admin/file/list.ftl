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
                <iframe id="myIframe" src="${ctx.contextPath}/admin/file/fileTable?delFlag=1"
                        style="width: 100%;border: 0 solid #808080"></iframe>
            </div>
        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    window.addEventListener('message', function (event) {
        // if (event.origin !== "http://xitem.com") return; // 确保只接受来自信任域名的消息
        if (event.data.type === 'resize') {
            let height = event.data.height;
            if (height < 600) {
                height = 600;
            }
            document.getElementById('myIframe').style.height = height + 'px';
        }
    });
</script>
</body>
</html>
