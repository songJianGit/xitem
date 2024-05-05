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
                                            <span class="input-group-text" id="inputGroup-sizing-default">用户名</span>
                                        </div>
                                        <input type="text" class="form-control" name="userName" placeholder="用户名">
                                    </div>

                                    <div class="input-group m-r-5">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="inputGroup-sizing-default">登录名</span>
                                        </div>
                                        <input type="text" class="form-control" value="" name="loginName"
                                               placeholder="登录名">
                                    </div>
                                    <div class="input-group">
                                        <div class="btn-group">
                                            <button type="button" id="searchBtn" class="btn btn-primary m-r-5">搜索</button>
                                            <button type="reset" class="btn btn-default">重置</button>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="card-body">
                                <div id="custom-toolbar d-flex flex-column flex-md-row">
                                    <div class="toolbar-btn-action">
                                        <a id="add" class="btn btn-primary m-r-5">
                                            新增
                                        </a>
                                        <a id="del" class="btn btn-primary m-r-5">
                                            删除
                                        </a>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="table-pagination"
                                           data-toolbar="#custom-toolbar"
                                           data-toggle="table"
                                           data-pagination="true"
                                           data-page-list="[10, 20, 50, 100, 200]"
                                           data-click-to-select="true"
                                           data-url="${ctx.contextPath}/admin/system/userListData"
                                           data-side-pagination="server">
                                        <thead>
                                        <tr>
                                            <th data-checkbox="true"></th>
                                            <th data-field="username">用户名</th>
                                            <th data-field="loginname">登录名</th>
                                            <th data-field="email">邮箱</th>
                                            <th data-field="phoneno">手机号码</th>
                                            <th data-field="cdate">创建时间</th>
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
        htm += '<a class="btn btn-sm btn-default m-r-5" href="#" onclick="edit(\'' + value + '\')" title="编辑">编辑</a>';
        htm += '<a class="btn btn-sm btn-default" href="#" onclick="resetpassword(\'' + value + '\')" title="重置密码">重置密码</a>';
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
                cache: false,
                async: false,
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
                content: '是否要删除？',
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

    $('#searchBtn').click(function () {
        $("#table-pagination").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/system/userListData?" + $("#searchform").serialize()
        });
    });

</script>
</body>
</html>
