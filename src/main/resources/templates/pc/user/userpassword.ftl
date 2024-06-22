<!DOCTYPE html>
<html lang="zh">
<head>
    <title>用户中心</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <div class="alert alert-secondary" role="alert">
            注意：密码至少8位，需要包含大小写英文和数字。
        </div>
        <div class="form-group">
            <label for="password1">原密码</label>
            <input type="password" class="form-control" id="password1" maxlength="100">
        </div>
        <div class="form-group">
            <label for="password2">新密码</label>
            <input type="password" class="form-control" id="password2" maxlength="100">
        </div>
        <div class="form-group">
            <label for="password3">再次输入新密码</label>
            <input type="password" class="form-control" id="password3" maxlength="100">
        </div>
        <div class="form-group">
            <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            <button type="button" class="btn btn-light" onclick="history.back(-1);return false;">返回</button>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    $('#saveBtn').click(function () {
        let p1 = $('#password1').val();
        let p2 = $('#password2').val();
        let p3 = $('#password3').val();
        if (isBlank(p1)) {
            layer.msg("请填写原密码");
            return false;
        }
        if (isBlank(p2)) {
            layer.msg("请填写新密码");
            return false;
        }
        if (isBlank(p3)) {
            layer.msg("请再次输入新密码");
            return false;
        }
        $.ajax({
            url: "${ctx.contextPath}/pc/user/userPasswordSave",
            cache: false,// 不缓存
            type: "post",
            data: {
                password1: p1,
                password2: p2,
                password3: p3
            },
            success: function (d) {
                if (d.result) {
                    layer.msg("保存成功！请重新登录");
                } else {
                    layer.msg(d.msg);
                }
            }
        });
    });
</script>
</body>
</html>
