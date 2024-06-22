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
                                <form action="${ctx.contextPath}/admin/system/dictSave" method="post" class="row">
                                    <input type="hidden" name="id" value="${dict.id!}"/>
                                    <div class="form-group col-6">
                                        <label for="name">描述</label>
                                        <input type="text" class="form-control" id="name" name="name" maxlength="255"
                                               value="${dict.name!}" placeholder="描述" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="val">值</label>
                                        <input type="text" class="form-control" id="val" name="val" maxlength="255"
                                               value="${dict.val!}" placeholder="值"/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="type">类型</label>
                                        <input type="text" class="form-control" id="type" name="type" maxlength="255"
                                               value="${dict.type!}" placeholder="类型" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="seq">排序</label>
                                        <input type="number" class="form-control" id="seq" name="seq"
                                               value="${dict.seq!}" placeholder="排序" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <button type="submit" class="btn btn-primary" id="saveBtn">确 定
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