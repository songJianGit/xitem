<header class="lyear-layout-header">
    <nav class="navbar">
            <div class="topbar-left" style="display: flex; align-items: center;">
                <div class="lyear-aside-toggler">
                    <span class="lyear-toggler-bar"></span>
                    <span class="lyear-toggler-bar"></span>
                    <span class="lyear-toggler-bar"></span>
                </div>

                <div class="topbar-left-project" style="margin-left: 12px;">
                    <div>
                        <select class="form-control" id="example-select" name="example-select" size="1">
                            <option value="0">请选择</option>
                            <option value="1">选项 #1</option>
                            <option value="2">选项 #2</option>
                            <option value="3">选项 #3</option>
                        </select>
                    </div>
                </div>
                <div class="topbar-left-task" style="margin-left: 12px;">
                    <div>
                       我的工作
                    </div>
                </div>
                <div class="topbar-left-setting" style="margin-left: 12px;">
                    <div>
                        个人设置
                    </div>
                </div>
                <div class="topbar-left-setting" style="margin-left: 12px;">
                    <div>
                        系统设置
                    </div>
                </div>
            </div>

            <ul class="navbar-right d-flex align-items-center">
                <li class="dropdown dropdown-profile">
                    <a href="#!" data-toggle="dropdown" class="dropdown-toggle">
                        <span>${Session.puser.userName!}</span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li>
                            <a class="dropdown-item" href="#!" onclick="loginUserPassword()"><i class="mdi mdi-lock-outline"></i> 修改密码</a>
                        </li>
                        <li class="dropdown-divider"></li>
                        <li>
                            <a class="dropdown-item" href="${ctx.contextPath}/loginOut"><i class="mdi mdi-logout-variant"></i> 退出登录</a>
                        </li>
                    </ul>
                </li>
            </ul>

    </nav>
</header>