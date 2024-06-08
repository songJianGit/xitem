<div class="pc-header">
    <div class="pc-header-menu">
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pcindex">xitem</a></div>
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pc/course/index">课程</a></div>
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pc/exam/index">考试</a></div>
    </div>
    <div class="pc-header-login">
        <#if Session.puser??>
            <div class="pc-header-login-item">
                <a class="pc-header-login-username" href="${ctx.contextPath}/pc/user/userCenter">${Session.puser.nickName!}</a>
            </div>
            <div class="pc-header-login-item"><a href="${ctx.contextPath}/pc/outLogin">退出</a></div>
        <#else>
            <div class="pc-header-login-item"><a href="${ctx.contextPath}/pclogin">登录</a></div>
        </#if>
    </div>
</div>