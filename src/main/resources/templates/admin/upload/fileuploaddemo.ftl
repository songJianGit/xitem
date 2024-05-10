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

            <div class="container-fluid">

                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <#if path??><h3>form的路径：${path}</h3></#if>
                                <input class="form-control" type="text" name="fileinfo" id="fileinfo"/>
                                <button class="btn btn-primary" type="button" id="upload">分片上传</button>
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
    $('#upload').click(function () {
        layer_show("上传", '${ctx.contextPath}/admin/upload/fileUploadMain?type=png,mp4');
    });

    function uploadCallback(infos) {
        console.info(infos);
        $("#fileinfo").val(infos);
    }
</script>
</body>
</html>