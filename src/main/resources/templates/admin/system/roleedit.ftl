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

                                <form action="${ctx.contextPath}/admin/system/roleSave" method="post" class="row">
                                    <input type="hidden" name="id" value="${role.id!}"/>
                                    <div class="form-group col-12">
                                        <label for="name">角色名</label>
                                        <input type="text" class="form-control" id="name" name="name" value="${role.name!}" placeholder="角色名" maxlength="100" required/>
                                    </div>
                                    <div class="form-group col-12">
                                        <button type="submit" class="btn btn-primary">确 定</button>
                                        <button type="button" class="btn btn-default" onclick="history.back(-1);return false;">返 回</button>
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