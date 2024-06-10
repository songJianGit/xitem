<!DOCTYPE html>
<html lang="zh">
<head>
    <title>课程</title>
    <#include "../commons/head.ftl"/>
    <style>
        .course-title {
            text-align: center;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            margin-top: 7px;
            color: #000;
        }

        .course-item {
            padding: 0 0 0.25rem 0;
        }
    </style>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <div class="input-group" style="max-width: 370px">
            <input type="text" name="title" value="${courseDto.title!}" class="form-control" placeholder="请输入关键词">
            <div class="input-group-append">
                <button type="button" class="btn btn-outline-secondary" onclick="searchCourse()">搜索</button>
            </div>
        </div>
        <div class="row">
            <#list courseList as item>
                <a class="col-4" href="${ctx.contextPath}/pc/course/detail?cid=${item.id!}">
                    <div class="card mt-3">
                        <div class="card-body course-item">
                            <img style="width: 100%;max-height: 200px"
                                 src="${ctx.contextPath}${item.cover!'/static/admin/commons/img/defaultimg.webp'}"
                                 alt="图">
                            <div class="course-title">${item.title!}</div>
                        </div>
                    </div>
                </a>
            </#list>
        </div>
        <div id="pageDiv" class="mt-3">
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    $(function () {
        pageInit();
    });

    function pageInit() {
        if ('${totalPages!}' != '0') {
            paginator.init("pageDiv", `${totalPages!}`, "pageNum");
            paginator.show(paginator.getUrlParam("pageNum"));
        }
    }

    function searchCourse() {
        let title = $("input[name='title']").val();
        window.location.href = "${ctx.contextPath}/pc/course/index?title=" + title;
    }
</script>
</body>
</html>
