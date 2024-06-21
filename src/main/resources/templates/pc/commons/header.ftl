<div class="pc-header">
    <div class="pc-header-menu">
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pcindex">xitem</a></div>
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pc/course/index">课程</a></div>
        <div class="pc-header-menu-item"><a href="${ctx.contextPath}/pc/exam/index">考试</a></div>
    </div>
    <div class="pc-header-login">
        <#if Session.puser??>
            <div class="btn-group dropleft" style="padding: 5px;background-color: #4d5259;margin-right: 7px">
                <button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false" style="background-color: #4d5259;border: 0;box-shadow:none">
                    ${Session.puser.nickName!}
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="${ctx.contextPath}/pc/user/userCenter">个人中心</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="${ctx.contextPath}/pc/outLogin">退出</a>
                </div>
            </div>
        <#else>
            <div class="pc-header-login-item"><a href="${ctx.contextPath}/login">登录</a></div>
        </#if>
    </div>
</div>