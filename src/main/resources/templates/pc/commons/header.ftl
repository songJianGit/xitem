<div class="pc-header">
    <div class="pc-header-menu">
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pcindex">xitem</a></div>
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pc/exam/index">课程</a></div>
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pc/exam/index">考试</a></div>
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pc/exam/index">个人中心</a></div>
    </div>
    <div class="pc-header-login">
        <#if Session.puser??>
            <div class="pc-header-login-item">${Session.puser.nickname!}</div>
            <div class="pc-header-login-item"><a href="${ctx.contextPath}/pc/outLogin">退出登录</a></div>
        <#else>
            <div class="pc-header-login-item"><a href="${ctx.contextPath}/pclogin">登录</a></div>
        </#if>
    </div>
</div>