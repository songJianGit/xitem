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

                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">姓名</span>
                                        </div>
                                        <input type="text" class="form-control" name="userName" placeholder="姓名">
                                    </div>

                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">登录名</span>
                                        </div>
                                        <input type="text" class="form-control" value="" name="loginName"
                                               placeholder="登录名">
                                    </div>

                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">状态</span>
                                        </div>
                                        <select class="form-control" name="status">
                                            <option value="">---状态---</option>
                                            <option value="1">启用</option>
                                            <option value="2">停用</option>
                                        </select>
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
                                <div id="custom-toolbar">
                                    <div class="toolbar-btn-action">
                                        <@funTag tag="sys:user:add">
                                            <button type="button" id="add" class="btn btn-primary">
                                                新增
                                            </button>
                                        </@funTag>
                                        <@funTag tag="sys:user:del">
                                            <button type="button" id="del" class="btn btn-primary">
                                                删除
                                            </button>
                                        </@funTag>
                                        <@funTag tag="sys:user:status">
                                            <button type="button" id="status" class="btn btn-primary">
                                                启用/停用
                                            </button>
                                        </@funTag>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toolbar="#custom-toolbar"
                                           data-toggle="table"
                                           data-pagination="true"
                                           data-page-list="[10, 20, 50, 100, 200]"
                                           data-show-refresh="true"
                                           data-url="${ctx.contextPath}/admin/system/userListData"
                                           data-query-params="pageQueryParams"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-checkbox="true"></th>
                                            <th data-field="userName">姓名</th>
                                            <th data-field="loginName">登录名</th>
                                            <th data-field="email">邮箱</th>
                                            <th data-field="phoneNo">手机号码</th>
                                            <th data-field="status" data-formatter="status">状态</th>
                                            <th data-field="createDate">创建时间</th>
                                            <th data-field="id" data-formatter="caozuo">操作</th>
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
    function caozuo(value, row) {
        let htm = '';
        htm += '<div class="btn-group">';
        <@funTag tag="sys:user:edit">
        htm += '<button type="button" class="btn btn-sm btn-default m-r-5" onclick="edit(\'' + value + '\')" title="编辑">编辑</button>';
        </@funTag>
        <@funTag tag="sys:user:resetpwd">
        htm += '<button type="button" class="btn btn-sm btn-default" onclick="resetpassword(\'' + value + '\')" title="重置密码">重置密码</button>';
        </@funTag>
        htm += '</div>';
        return htm;
    }

    $('#add').click(function () {
        window.location.href = '${ctx.contextPath}/admin/system/userEdit';
    });

    function edit(id) {
        window.location.href = '${ctx.contextPath}/admin/system/userEdit?userId=' + id;
    }

    function resetpassword(id) {
        layer.prompt({title: '请输入新密码', formType: 1}, function (pass, index) {
            $.ajax({
                url: '${ctx.contextPath}/admin/system/resetPassword',
                type: "post",
                cache: false,
                data: {
                    'userId': id,
                    'password': pass
                },
                success: function (data) {
                    if (data.result) {
                        layer.msg('重置成功')
                    } else {
                        layer.msg(data.msg)
                    }
                }
            });
            layer.close(index);
        });
    }

    $('#del').click(function () {
        if (getSelectionIds() != false) {
            $.confirm({
                title: '提示',
                content: '是否删除？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'btn-blue',
                        action: function () {
                            $.ajax({
                                url: "${ctx.contextPath}/admin/system/userDelete",
                                data: {
                                    'userIds': getSelectionIds().join(',')
                                },
                                success: function (data) {
                                    if (data.result) {
                                        $("#table-pagination").bootstrapTable('refresh');
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

    $('#status').click(function () {
        if (getSelectionIds() != false) {
            $.ajax({
                url: "${ctx.contextPath}/admin/system/userStatus",
                type: "post",
                data: {
                    'userIds': getSelectionIds().join(',')
                },
                success: function (data) {
                    if (data.result) {
                        $("#table-pagination").bootstrapTable('refresh');
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    });

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/system/userListData?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>
