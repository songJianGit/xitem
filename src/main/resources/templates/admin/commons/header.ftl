<header class="lyear-layout-header">
    <nav class="navbar">
            <div class="topbar-left">
                <div class="lyear-aside-toggler">
                    <span class="lyear-toggler-bar"></span>
                    <span class="lyear-toggler-bar"></span>
                    <span class="lyear-toggler-bar"></span>
                </div>
            </div>

            <ul class="navbar-right d-flex align-items-center">
                <li class="dropdown dropdown-profile">
                    <a href="#!" data-toggle="dropdown" class="dropdown-toggle">
                        <span>${Session.puser.userName!}</span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li>
                            <a class="dropdown-item" href="${ctx.contextPath}/index"><i class="mdi mdi-account"></i> 学员端</a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="#!" onclick="loginUserPassword()"><i class="mdi mdi-lock-outline"></i> 修改密码</a>
                        </li>
<#--                        <li>-->
<#--                            <a class="dropdown-item" href="#!"><i class="mdi mdi-delete"></i> 清空缓存</a>-->
<#--                        </li>-->
                        <li class="dropdown-divider"></li>
                        <li>
                            <a class="dropdown-item" href="${ctx.contextPath}/loginOut"><i class="mdi mdi-logout-variant"></i> 退出登录</a>
                        </li>
                    </ul>
                </li>
            </ul>

    </nav>
</header>