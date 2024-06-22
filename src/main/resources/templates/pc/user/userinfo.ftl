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
        <div class="form-group">
            <label for="nickName">昵称</label>
            <input type="text" class="form-control" value="${userInfo.nickName!}" id="nickName" name="nickName"
                   maxlength="50">
        </div>
        <div class="form-group">
            <label>姓名</label>
            <input type="text" class="form-control" value="${userInfo.userName!}" readonly>
        </div>
        <div class="form-group">
            <label>登录名</label>
            <input type="text" class="form-control" value="${userInfo.loginName!}" readonly>
        </div>
        <div class="form-group">
            <label for="email">邮箱</label>
            <input type="email" class="form-control" value="${userInfo.email!}" id="email" name="email" maxlength="100">
        </div>
        <div class="form-group">
            <label>手机号码</label>
            <input type="text" class="form-control" value="${userInfo.phoneNo!}" readonly>
        </div>
        <div class="form-group">
            <label>所属部门</label>
            <input type="text" class="form-control" value="${userInfo.organName!}" readonly>
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
        let nickName = $('#nickName').val();
        if (isBlank(nickName)) {
            layer.msg("请填写昵称");
            return false;
        }
        $.ajax({
            url: "${ctx.contextPath}/pc/user/userInfoSave",
            cache: false,// 不缓存
            type: "post",
            data: {
                nickName: nickName,
                email: $('#email').val()
            },
            success: function (d) {
                if (d.result) {
                    layer.msg("保存成功！<br/>重新登录，便可刷新缓存");
                } else {
                    layer.msg("保存失败");
                }
            }
        });
    });
</script>
</body>
</html>
