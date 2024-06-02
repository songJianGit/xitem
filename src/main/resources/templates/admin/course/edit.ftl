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
                                <form action="${ctx.contextPath}/admin/course/save" method="post" class="row" enctype="multipart/form-data"
                                      onsubmit="return check();">
                                    <input type="hidden" name="id" value="${course.id!}"/>

                                    <div class="form-group col-12">
                                        <label>课程封面</label>
                                        <input type="file" class="form-control-file" name="fileinfo"/>
                                        <img style="width: 100px" src="${ctx.contextPath}${course.cover!}">
                                    </div>

                                    <div class="form-group col-12">
                                        <label for="title">课程标题</label>
                                        <input id="title" maxlength="100" class="form-control" name="title" value="${course.title!}"/>
                                    </div>

                                    <div class="form-group col-12">
                                        <label for="describe">课程简介</label>
                                        <input id="describe" maxlength="255" class="form-control" name="describe" value="${course.describe!}"/>
                                    </div>

                                    <div class="form-group col-12">
                                        <label for="learnTime">课程时长（分钟）</label>
                                        <input type="number" id="learnTime" class="form-control" name="learnTime" value="${course.learnTime!}"/>
                                    </div>

                                    <div class="form-group col-12">
                                        <button type="submit" class="btn btn-primary">保 存
                                        </button>
                                        <button type="button" class="btn btn-default"
                                                onclick="javascript:history.back(-1);return false;">返 回
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