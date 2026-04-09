<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--页面主要内容-->
        <main style="padding-top: 3px">
            <div class="container-fluid p-t-15">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <form class="form-inline" method="post" action="#!" role="form"
                                      id="searchform">
                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">姓名</span>
                                        </div>
                                        <input type="text" class="form-control" value="" name="userName"
                                               placeholder="姓名">
                                    </div>
                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">登录名</span>
                                        </div>
                                        <input type="text" class="form-control" value=""
                                               name="loginName"
                                               placeholder="登录名">
                                    </div>
                                    <div class="input-group">
                                        <div class="btn-group">
                                            <button type="button" id="searchBtn"
                                                    class="btn btn-primary m-r-5">搜索
                                            </button>
                                            <button type="reset" class="btn btn-default">重置</button>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="card-body">
                                <div id="custom-toolbar">
                                    <div class="toolbar-btn-action">
                                        <button type="button" id="add" class="btn btn-primary">
                                            添加授权
                                        </button>
                                        <button type="button" id="del" class="btn btn-primary">
                                            删除授权
                                        </button>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toolbar="#custom-toolbar"
                                           data-toggle="table"
                                           data-pagination="true"
                                           data-page-list="[10, 20, 50, 100, 200]"
                                           data-show-refresh="true"
                                           data-url="${ctx.contextPath}/admin/system/userListByRoleData?roleId=${roleId}"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-checkbox="true"></th>
                                            <th data-field="userName">姓名</th>
                                            <th data-field="loginName">登录名</th>
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

    $("#add").click(function () {
        layer_show("用户", "${ctx.contextPath}/admin/system/userShow");
    });

    function userCallBack(ids) {
        $.ajax({
            url: '${ctx.contextPath}/admin/system/userLinkRole',
            type: "post",
            cache: false,
            data: {
                userIds: ids.join(","),
                roleId: '${roleId}'
            },
            success: function (data) {
                $("#table-pagination").bootstrapTable('refresh');// 刷新授权
                if (data.result) {
                    layer.msg(data.msg);
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    $("#del").click(function () {
        let ids = getSelectionIds();
        if (ids != false) {
            $.confirm({
                title: '提示',
                content: '是否删除？',
                buttons: {
                    confirm: {
                        text: '确认',
                        action: function () {
                            $.ajax({
                                url: '${ctx.contextPath}/admin/system/userSplitRole',
                                type: "post",
                                cache: false,
                                data: {
                                    'urIds': ids.join(","),
                                },
                                success: function (data) {
                                    $("#table-pagination").bootstrapTable('refresh');// 刷新授权
                                    if (data.result) {
                                        layer.msg(data.msg);
                                    } else {
                                        alert(data.msg);
                                    }
                                }
                            });
                        }
                    },
                    cancel: {
                        text: '取消',
                        action: function () {
                        }
                    }
                }
            });
        }
    });

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/system/userListByRoleData?roleId=${roleId}&" + $("#searchform").serialize()
        });
    });
</script>
</body>
</html>