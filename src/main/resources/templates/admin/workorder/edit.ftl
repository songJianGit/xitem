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
                                <form action="#!" method="post" class="row"
                                      onsubmit="return check();">
                                    <input type="hidden" name="id" value="${workOrder.id!}"/>

                                    <div class="form-group col-6">
                                        <label for="title">标题</label>
                                        <input id="title" class="form-control" name="title" value="${workOrder.title!}" readonly/>
                                    </div>
                                    <div class="form-group col-12">
                                        <label>内容</label>
                                        ${content!}
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
