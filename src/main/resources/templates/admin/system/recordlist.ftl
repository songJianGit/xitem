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

                            <div class="card-header">
                                <form class="form-inline" method="post" id="searchform" action="#!" role="form">
                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">IP</span>
                                        </div>
                                        <input type="text" class="form-control" name="ips" placeholder="IP">
                                    </div>
                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">URL</span>
                                        </div>
                                        <input type="text" class="form-control" name="dopath" placeholder="URL">
                                    </div>
                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">提交参数</span>
                                        </div>
                                        <input type="text" class="form-control" name="params" placeholder="提交参数">
                                    </div>
                                    <button type="button" id="searchBtn" class="btn btn-primary m-r-5">搜索</button>
                                    <button type="reset" class="btn btn-default">重置</button>
                                </form>
                            </div>


                            <div class="card-body">
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toolbar="#custom-toolbar"
                                           data-toggle="table"
                                           data-pagination="true"
                                           data-page-list="[10, 20, 50, 100, 200]"
                                           data-click-to-select="true"
                                           data-url="${ctx.contextPath}/admin/record/recordListData"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-field="userid">操作用户ID</th>
                                            <th data-field="dopath">URI</th>
                                            <th data-field="method">提交方式</th>
                                            <th data-field="params">提交参数</th>
                                            <th data-field="ips">IP</th>
                                            <th data-field="useragent">浏览器信息</th>
                                            <th data-field="cdate">操作时间</th>
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
    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/record/recordListData?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>