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
                                <form action="${ctx.contextPath}/admin/banner/save" method="post" class="row" enctype="multipart/form-data">
                                    <input type="hidden" name="id" value="${banner.id!}"/>
                                    <div class="form-group col-6">
                                        <label>图片</label>
                                        <input type="file" class="form-control-file" name="fileinfo"/>
                                        <img style="width: 100px" src="${ctx.contextPath}${banner.url!'/static/admin/commons/img/defaultimg.webp'}" alt="图">
                                    </div>
                                    <div class="form-group col-12">
                                        <button type="submit" class="btn btn-primary">保 存
                                        </button>
                                        <button type="button" class="btn btn-default"
                                                onclick="history.back(-1);return false;">返 回
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
</script>
</body>
</html>
