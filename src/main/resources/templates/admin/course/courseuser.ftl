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
                            <div class="card-header">
                                <form class="form-inline" method="post" action="#!" role="form" id="searchform">

                                    <div class="input-group  m-r-5">
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-default"
                                                    onclick="history.back(-1);return false;">返 回
                                            </button>
                                        </div>
                                    </div>

                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">姓名</span>
                                        </div>
                                        <input type="text" class="form-control" name="userName"
                                               placeholder="姓名">
                                    </div>

                                    <div class="input-group">
                                        <div class="btn-group">
                                            <button type="button" id="searchBtn" class="btn btn-primary m-r-5">搜索
                                            </button>
                                            <button type="reset" class="btn btn-default">重置</button>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="card-body">
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toggle="table"
                                           data-pagination="true"
                                           data-page-list="[10, 20, 50, 100, 200]"
                                           data-show-refresh="true"
                                           data-url="${ctx.contextPath}/admin/course/courseUserData?courseId=${courseId!}"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="createUserName">姓名</th>
                                            <th data-field="createDate">开始时间</th>
                                            <th data-field="precent" data-formatter="precent">当前进度</th>
                                            <th data-field="completeTime">完成时间</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
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
    function precent(value, row) {
        return value + "%";
    }

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/course/courseUserData?courseId=${courseId!}&" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>
