<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "commons/head.ftl"/>
</head>
<body class="hold-transition">
<div class="container-fluid" style="padding: 24px 18px;">
    <div class="row justify-content-center">
        <div class="col-lg-10 col-xl-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="card-title mb-0">欢迎使用系统，请完成管理员初始化设置</h4>
                    <p class="text-muted mb-0" style="margin-top: 8px;">带 <span class="text-danger">*</span> 为必填项。该信息将用于首次登录和个人资料展示。</p>
                </div>
                <div class="card-body">
                    <form id="initForm" action="${ctx.contextPath}/admin/system/userSave" method="post" class="row"
                          enctype="multipart/form-data">
                        <input type="hidden" name="id" value="${user.id!}"/>
                        <input type="hidden" name="initFlag" value="1"/>
                        <div class="form-group col-md-6">
                            <label for="username"><span class="text-danger">*</span> 姓名</label>
                            <input type="text" class="form-control" id="username" name="userName"
                                   value="${user.userName!}" placeholder="请输入姓名" maxlength="100" required/>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="loginname"><span class="text-danger">*</span> 登录名</label>
                            <input type="text" class="form-control" id="loginname" name="loginName"
                                   value="${user.loginName!}" placeholder="请输入登录名" maxlength="50" required/>
                            <small class="form-text text-muted">建议使用字母和数字组合，便于记忆与管理。</small>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="password"><span class="text-danger">*</span> 密码</label>
                            <input type="password" class="form-control" id="password" name="password" maxlength="100"
                                   value="" placeholder="请输入密码" autocomplete="new-password" required/>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="password2"><span class="text-danger">*</span> 再次输入密码</label>
                            <input type="password" class="form-control" id="password2" name="password2" maxlength="100"
                                   value="" placeholder="请再次输入密码" autocomplete="new-password" required/>
                            <small id="passwordHint" class="form-text text-muted">密码至少 8 位，且两次输入需保持一致。</small>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="jobTitle"><span class="text-danger">*</span> 用户职位</label>
                            <input type="text" class="form-control" id="jobTitle" name="jobTitle"
                                   maxlength="50" value="${user.jobTitle!}" placeholder="请输入职位名称" required/>
                        </div>
                        <div class="form-group col-md-6">
                            <label>头像</label>
                            <input type="file" class="form-control-file" name="fileinfo"
                                   accept="image/jpeg, image/png, image/webp" onchange="preview(this)"/>
                            <small class="form-text text-muted">支持 JPG、PNG、WEBP 格式，建议使用方形头像。</small>
                            <div id="preview" style="max-width: 100px; margin-top: 8px;"></div>
                            <#if user.avatar?? && user.avatar?trim?length gt 0>
                                <img id="previewNone" style="width: 100px; margin-top: 6px;"
                                     src="${ctx.contextPath}${user.avatar!'/static/admin/commons/img/defaultimg.webp'}"
                                     alt="头像预览">
                            </#if>
                        </div>
                        <div class="form-group col-12" style="margin-top: 12px;">
                            <button type="submit" class="btn btn-primary">保存</button>
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
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/admin-template/js/jquery.min.js"></script>
<script type="text/javascript">
    $("#initForm").on("submit", function (e) {
        let password = $("#password").val();
        let password2 = $("#password2").val();
        let passwordPattern = /^.{8,}$/;
        if (!passwordPattern.test(password)) {
            e.preventDefault();
            $("#passwordHint").removeClass("text-muted").addClass("text-danger").text("密码至少 8 位，请检查后重试。");
            $("#password").focus();
            return;
        }
        if (password.length > 100) {
            e.preventDefault();
            $("#passwordHint").removeClass("text-muted").addClass("text-danger").text("密码长度不能超过 100 位。");
            $("#password").focus();
            return;
        }
        if (password !== password2) {
            e.preventDefault();
            $("#passwordHint").removeClass("text-muted").addClass("text-danger").text("两次输入的密码不一致，请检查后重试。");
            $("#password2").focus();
        }
    });
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
</script>
</body>
</html>
