<div class="pc-header">
    <div class="pc-header-menu">
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/index">xitem</a></div>
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pc/course/index">课程</a></div>
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pc/exam/index">考试</a></div>
    </div>
    <div class="pc-header-login">
        <#if Session.puser??>
            <div class="btn-group dropleft" style="padding: 5px;background-color: #4d5259;margin-right: 7px">
                <button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false" style="background-color: #4d5259;border: 0;box-shadow:none">
                    <#if (Session.puser.nickName?length>10)>
                        ${Session.puser.nickName?substring(0,8)}...
                    <#else>
                        ${Session.puser.nickName}
                    </#if>
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="${ctx.contextPath}/pc/user/userCenter">个人中心</a>
                    <a class="dropdown-item" href="${ctx.contextPath}/pc/user/userPassword">修改密码</a>
                    <#if Session.puserrole==1>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="${ctx.contextPath}/admin/system/index">管理端</a>
                    </#if>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="${ctx.contextPath}/pc/outLogin">退出登录</a>
                </div>
            </div>
        <#else>
            <div class="pc-header-login-item"><a href="${ctx.contextPath}/login">登录</a></div>
        </#if>
    </div>
</div>