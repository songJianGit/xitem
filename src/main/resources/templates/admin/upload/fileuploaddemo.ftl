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
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <div>1.点击上传按钮</div>
                                <div>2.在弹出页面【点击选择文件】按钮，选择需要上传的文件。</div>
                                <div>3.弹出页面中的文件上传完以后，文件会存放在temp文件夹。</div>
                                <div>4.点击弹出页中的【保存】按钮，文件会移出temp，放入指定文件夹。</div>
                                <div>5.输入框中会显示回调信息，我们一般取url参数就行。</div>
                            </div>
                            <div class="card-body">
                                <input class="form-control" type="text" name="fileinfo" id="fileinfo"/>
                                <button type="button" class="btn btn-primary m-t-10" id="upload">上传</button>
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
        layer_show("上传", '${ctx.contextPath}/admin/upload/fileUploadMain?multiple=1&maxnum=3&type=png,mp4', '', '', false, 0);
    });

    function uploadCallback(infos) {
        console.log("uploadCallback", infos);
        $("#fileinfo").val(JSON.stringify(infos));
    }
</script>
</body>
</html>