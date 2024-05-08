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

                                <form action="${ctx.contextPath}/admin/system/userSave" method="post" class="row"
                                      onsubmit="return check();">
                                    <input type="hidden" name="id" value="${user.id!}"/>
                                    <div class="form-group col-6">
                                        <label for="nickname">昵称</label>
                                        <input type="text" class="form-control" id="nickname" name="nickname"
                                               value="${user.nickname!}" placeholder="昵称" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="username">姓名</label>
                                        <input type="text" class="form-control" id="username" name="username"
                                               value="${user.username!}" placeholder="姓名" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="loginname">登录名</label>
                                        <input type="text" class="form-control" id="loginname" name="loginname"
                                               value="${user.loginname!}" placeholder="登录名" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="email">邮箱</label>
                                        <input type="email" class="form-control" id="email" name="email"
                                               value="${user.email!}" placeholder="邮箱"/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="phoneno">手机号码</label>
                                        <input type="text" class="form-control" id="phoneno" name="phoneno"
                                               pattern="[1-9]\d{10}"
                                               value="${user.phoneno!}" placeholder="手机号码" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="organid">部门</label>
                                        <input type="text" class="form-control" id="organname" name="organname"
                                               value="${user.organname!}" placeholder="单位" required readonly
                                               autocomplete="off"/>
                                        <input type="hidden" id="organid" name="organid"
                                               value="${user.organid!}"/>

                                    </div>
                                    <div class="form-group col-12">
                                        <button type="submit" class="btn btn-primary">确 定</button>
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
    function check() {
        return checkloginname();
    }

    function checkloginname() {
        let b = false;
        let userloginname = $("#loginname").val();
        let userid = '${user.id!}';
        $.ajax({
            url: '${ctx.contextPath}/admin/system/checkLonginName',
            cache: false,
            async: false,
            data: {
                'userLoginName': userloginname,
                'userId': userid
            },
            success: function (data) {
                if (data.result) {
                    b = true;
                } else {
                    layer.msg(data.msg);
                    b = false;
                }
            }
        });
        return b;
    };
    $('#organname').click(function () {
        layer_show('机构', '${ctx.contextPath}/admin/organ/organShow');
    })

    function organCallback(data) {
        $("#organid").val(data.id);
        $("#organname").val(data.name);
    }
</script>
</body>
</html>