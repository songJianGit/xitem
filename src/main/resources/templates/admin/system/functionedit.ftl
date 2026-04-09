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

                                <form action="${ctx.contextPath}/admin/system/functionSave" method="post" class="row">
                                    <input type="hidden" name="id" value="${function.id!}"/>
                                    <div class="form-group col-6">
                                        <label for="name">菜单名称</label>
                                        <input type="text" class="form-control" id="name" name="name" value="${function.name!}" placeholder="菜单名称" maxlength="100" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="url">链接</label>
                                        <input type="text" class="form-control" id="url" name="url" value="${function.url!}" placeholder="链接" maxlength="255" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="seq">排序</label>
                                        <input type="number" class="form-control" id="seq" name="seq" value="${function.seq!}" placeholder="排序" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="tag">标识</label>
                                        <input type="text" class="form-control" id="tag" name="tag" value="${function.tag!}" placeholder="标识" maxlength="255" required/>
                                    </div>

                                    <div class="form-group col-6">
                                        <label>是否显示</label>
                                        <div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="showFlag" id="showflag1" value="1" <#if function.showFlag==1>checked</#if>>
                                                <label class="form-check-label" for="showflag1">显示</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="showFlag" id="showflag0" value="0" <#if function.showFlag==0>checked</#if>>
                                                <label class="form-check-label" for="showflag0">隐藏</label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group col-6">
                                        <label>上级菜单</label>
                                        <div>
                                            <input type="text" placeholder="上级菜单" class="form-control" value="${pfunction.name!'无'}" id="upFunctionsName" readonly/>
                                            <input type="hidden" name="pid" value="${pfunction.id!0}" id="upFunctionsId"/>
                                        </div>
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