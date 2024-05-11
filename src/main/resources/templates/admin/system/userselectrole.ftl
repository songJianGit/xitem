<!DOCTYPE html>
<html>
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
                            <div class="card-body">
                                <ul class="nav nav-tabs">
                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#home-basic1">已授权用户</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#home-basic2">所有用户</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane fade show active" id="home-basic1">
                                        <div class="card">
                                            <div class="card-header">
                                                <form class="form-inline" method="post" action="#!" role="form" id="searchformA">
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
                                                        <input type="text" class="form-control" value="" name="loginName"
                                                               placeholder="登录名">
                                                    </div>
                                                    <div class="input-group">
                                                        <div class="btn-group">
                                                            <button type="button" id="searchBtnA" class="btn btn-primary m-r-5">搜索</button>
                                                            <button type="reset" class="btn btn-default">重置</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>

                                            <div class="card-body">
                                                <div id="custom-toolbarA">
                                                    <div class="form-inline" role="form">
                                                        <button id="del" class="btn btn-primary">
                                                            删除授权
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="table-responsive">
                                                    <table id="table-paginationA"
                                                           data-toolbar="#custom-toolbarA"
                                                           data-toggle="table"
                                                           data-pagination="true"
                                                           data-page-list="[10, 20, 50, 100, 200]"
                                                           data-click-to-select="true"
                                                           data-url="${ctx.contextPath}/admin/system/userListByRoleData?roleId=${roleId}"
                                                           data-side-pagination="server">
                                                        <thead>
                                                        <tr>
                                                            <th data-checkbox="true"></th>
                                                            <th data-field="username">姓名</th>
                                                            <th data-field="loginname">登录名</th>
                                                        </tr>
                                                        </thead>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <#----------------------------------分割线----------------------------------------->
                                    <div class="tab-pane fade" id="home-basic2">
                                        <div class="card">
                                            <div class="card-header">
                                                <form class="form-inline" method="post" action="#!" role="form" id="searchformB">
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
                                                        <input type="text" class="form-control" value="" name="loginName"
                                                               placeholder="登录名">
                                                    </div>

                                                    <div class="input-group">
                                                        <div class="btn-group">
                                                            <button type="button" id="searchBtnB" class="btn btn-primary m-r-5">搜索</button>
                                                            <button type="reset" class="btn btn-default">重置</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="card-body">
                                                <div id="custom-toolbarB">
                                                    <div class="form-inline" role="form">
                                                        <button id="add" class="btn btn-primary">
                                                            添加授权
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="table-responsive">
                                                    <table id="table-paginationB"
                                                           data-toolbar="#custom-toolbarB"
                                                           data-toggle="table"
                                                           data-pagination="true"
                                                           data-page-list="[10, 20, 50, 100, 200]"
                                                           data-click-to-select="true"
                                                           data-url="${ctx.contextPath}/admin/system/userListData"
                                                           data-side-pagination="server">
                                                        <thead>
                                                        <tr>
                                                            <th data-field="state" data-checkbox="true"></th>
                                                            <th data-field="username">姓名</th>
                                                            <th data-field="loginname">登录名</th>
                                                        </tr>
                                                        </thead>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
        let ids = getSelectionId_B_S();
        if(ids!=false){
            $.ajax({
                url: '${ctx.contextPath}/admin/system/userLinkRole',
                cache: false,
                data: {
                    'userIds': ids.join(","),
                    'roleId': '${roleId}'
                },
                success: function (data) {
                    $("#table-paginationA").bootstrapTable('refresh');// 刷新授权
                    if (data.result) {
                        layer.msg(data.msg);
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    });

    $("#del").click(function () {
        let ids = getSelectionId_A_S();
        if(ids!=false){
            $.ajax({
                url: '${ctx.contextPath}/admin/system/userSplitRole',
                cache: false,
                data: {
                    'urIds': ids.join(","),
                },
                success: function (data) {
                    $("#table-paginationA").bootstrapTable('refresh');// 刷新授权
                    if (data.result) {
                        layer.msg(data.msg);
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    });

    function getSelectionId_A_S() {
        let obj = $("#table-paginationA").bootstrapTable('getSelections');
        if (obj.length == 0) {
            layer.alert("请选择要处理的记录.");
            return false;
        }
        let ids = [];
        for (let i = 0; i < obj.length; i++) {
            ids.push(obj[i].id);
        }
        return ids;
    }

    function getSelectionId_B_S() {
        let obj = $("#table-paginationB").bootstrapTable('getSelections');
        if (obj.length == 0) {
            layer.alert("请选择要处理的记录.");
            return false;
        }
        let ids = [];
        for (let i = 0; i < obj.length; i++) {
            ids.push(obj[i].id);
        }
        return ids;
    }
    $('#searchBtnA').click(function () {
        $("#table-paginationA").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/system/userListByRoleData?roleId=${roleId}&" + $("#searchformA").serialize()
        });
    });
    $('#searchBtnB').click(function () {
        $("#table-paginationB").bootstrapTable('refresh', {
            url: "${ctx.contextPath}/admin/system/userListData?" + $("#searchformB").serialize()
        });
    });
</script>
</body>
</html>