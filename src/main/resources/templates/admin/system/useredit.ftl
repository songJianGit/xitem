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
                                      enctype="multipart/form-data"
                                      onsubmit="return check();">
                                    <input type="hidden" name="id" value="${user.id!}"/>
                                    <input type="hidden" name="joinKey" value="${user.joinKey!}"/>
                                    <div class="form-group col-6">
                                        <label for="username">* 姓名</label>
                                        <input type="text" class="form-control" id="username" name="userName"
                                               value="${user.userName!}" placeholder="姓名" maxlength="100" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="loginname">* 登录名</label>
                                        <input type="text" class="form-control" id="loginname" name="loginName"
                                               value="${user.loginName!}" placeholder="登录名" maxlength="50" required/>
                                        <small class="form-text text-muted">建议使用手机号，便于记忆。</small>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="jobTitle">* 用户职位</label>
                                        <input type="text" class="form-control" id="jobTitle" name="jobTitle"
                                               maxlength="50" value="${user.jobTitle!}" placeholder="用户职位" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="lifedate">* 账号有效期</label>
                                        <input type="text" class="form-control" id="lifedate" name="lifeDate"
                                               autocomplete="off"
                                               onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                               value="${user.lifeDate!}" placeholder="账号有效期" required/>
                                    </div>
                                    <#if roleList??>
                                        <div class="form-group col-6">
                                            <label for="roleId">* 角色</label>
                                            <select class="form-control" name="roleId">
                                                <#list roleList as item>
                                                    <option value="${item.id!}" <#if roleId??><#if roleId==item.id>selected</#if></#if> >${item.name!}</option>
                                                </#list>
                                            </select>
                                        </div>
                                    </#if>
                                    <div class="form-group col-6">
                                        <label for="email">邮箱</label>
                                        <input type="email" class="form-control" id="email" name="email" maxlength="100"
                                               value="${user.email!}" placeholder="邮箱"/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="phoneno">手机号码</label>
                                        <input type="text" class="form-control" id="phoneno" name="phoneNo"
                                               pattern="[1-9]\d{10}" maxlength="20"
                                               value="${user.phoneNo!}" placeholder="手机号码"/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label>头像</label>
                                        <input type="file" class="form-control-file" name="fileinfo"
                                               accept="image/jpeg, image/png, image/webp" onchange="preview(this)"/>
                                        <div id="preview" style="max-width: 100px"></div>
                                        <#if user.avatar?? && user.avatar?trim?length gt 0>
                                            <img id="previewNone" style="width: 100px"
                                                 src="${ctx.contextPath}${user.avatar!'/static/admin/commons/img/defaultimg.webp'}"
                                                 alt="图">
                                        </#if>
                                    </div>
                                    <div class="form-group col-12">
                                        <button type="submit" class="btn btn-primary">保 存</button>
                                        <#if backBtn??>
                                            <button type="button" class="btn btn-default"
                                                    onclick="history.back(-1);return false;">返 回
                                            </button>
                                        </#if>
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
        if (!checkloginname()) {
            return false;
        }
        // if (!checkphoneno()) {
        //     return false;
        // }
        return true;
    }

    function preview(file) {
        $("#previewNone").hide();
        let prevDiv = document.getElementById('preview');
        if (file.files && file.files[0]) {
            let reader = new FileReader();
            reader.onload = function (evt) {
                prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';
            }
            reader.readAsDataURL(file.files[0]);
        } else {
            prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale, src=\'' + file.value + '\'"></div>';
        }
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
    <#if saveMsg??>
        layer.msg("保存成功");
    </#if>
</script>
</body>
</html>