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
                                        <input type="text" class="form-control" id="nickname" name="nickName"
                                               value="${user.nickName!}" placeholder="昵称" maxlength="50" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="username">姓名</label>
                                        <input type="text" class="form-control" id="username" name="userName"
                                               value="${user.userName!}" placeholder="姓名" maxlength="100" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="loginname">登录名</label>
                                        <input type="text" class="form-control" id="loginname" name="loginName"
                                               value="${user.loginName!}" placeholder="登录名" maxlength="50" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="email">邮箱</label>
                                        <input type="email" class="form-control" id="email" name="email" maxlength="100"
                                               value="${user.email!}" placeholder="邮箱"/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="phoneno">手机号码</label>
                                        <input type="text" class="form-control" id="phoneno" name="phoneNo"
                                               pattern="[1-9]\d{10}" maxlength="20"
                                               value="${user.phoneNo!}" placeholder="手机号码" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="organid">部门</label>
                                        <input type="text" class="form-control" id="organname" name="organName"
                                               value="${user.organName!}" placeholder="单位" required readonly
                                               autocomplete="off"/>
                                        <input type="hidden" id="organid" name="organId"
                                               value="${user.organId!}"/>

                                    </div>
                                    <div class="form-group col-6">
                                        <label for="lifedate">账号有效期</label>
                                        <input type="text" class="form-control" id="lifedate" name="lifeDate" autocomplete="off"
                                               onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                               value="${user.lifeDate!}" placeholder="账号有效期" required/>
                                    </div>
                                    <div class="form-group col-12">
                                        <button type="submit" class="btn btn-primary">确 定</button>
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
    function check() {
        if(!checkloginname()){
            return false;
        }
        if(!checkphoneno()){
            return false;
        }
        return true;
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
    function checkphoneno() {
        let b = false;
        let userphoneno = $("#phoneno").val();
        let userid = '${user.id!}';
        $.ajax({
            url: '${ctx.contextPath}/admin/system/checkPhoneNo',
            cache: false,
            async: false,
            data: {
                'userPhoneNo': userphoneno,
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