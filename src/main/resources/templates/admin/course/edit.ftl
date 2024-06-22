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
                                <form action="${ctx.contextPath}/admin/course/save" method="post" class="row" enctype="multipart/form-data">
                                    <input type="hidden" name="id" value="${course.id!}"/>

                                    <div class="form-group col-6">
                                        <label for="title">课程标题</label>
                                        <input id="title" maxlength="100" class="form-control" name="title" value="${course.title!}" required/>
                                    </div>

                                    <div class="form-group col-6">
                                        <label for="courseCategoryName">课程目录</label>
                                        <input id="courseCategoryName" placeholder="点击选择目录" class="form-control" value="${course.courseCategoryName!}" readonly/>
                                        <input id="courseCategory" type="hidden" name="courseCategory" value="${course.courseCategory!}"/>
                                    </div>

                                    <div class="form-group col-12">
                                        <label>课程封面</label>
                                        <input type="file" class="form-control-file" name="fileinfo" accept="image/jpeg, image/png"/>
                                        <img style="width: 100px" src="${ctx.contextPath}${course.cover!'/static/admin/commons/img/defaultimg.webp'}" alt="图">
                                    </div>

                                    <div class="form-group col-12">
                                        <label for="content">课程简介</label>
                                        <textarea id="content" maxlength="255" class="form-control" name="content">${course.content!}</textarea>
                                    </div>

                                    <div class="form-group col-6">
                                        <label for="learnTime">课程时长（分钟）</label>
                                        <input type="number" id="learnTime" class="form-control" name="learnTime" value="${course.learnTime!}" required/>
                                    </div>

                                    <div class="form-group col-6">
                                        <label for="courseFileName">课件</label>
                                        <input id="courseFileName" placeholder="点击选择课件" class="form-control" value="${courseFileName!}" readonly/>
                                        <input id="courseFileId" type="hidden" name="courseFileId" value="${course.courseFileId!}"/>
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

    $("#courseFileName").click(function (){
        layer_show("课件", '${ctx.contextPath}/admin/coursefile/courseFileShow');
    });

    function courseFileCallback(data){
        $("#courseFileId").val(data.id);
        $("#courseFileName").val(data.title);
    }

    $("#courseCategoryName").click(function (){
        layer_show("课程目录", '${ctx.contextPath}/admin/category/course/categoryShow');
    });

    function categoryCallback(data){
        $("#courseCategory").val(data.id);
        $("#courseCategoryName").val(data.title);
    }
</script>
</body>
</html>
