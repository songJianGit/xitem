<header class="lyear-layout-header">
    <nav class="navbar">
        <div class="topbar-left" style="display: flex; align-items: center;">
            <div class="lyear-aside-toggler">
                <span class="lyear-toggler-bar"></span>
                <span class="lyear-toggler-bar"></span>
                <span class="lyear-toggler-bar"></span>
            </div>

            <div class="topbar-left-project" style="margin-left: 13px;">
                <div>
                    <select class="form-control" id="project-top-select" data-live-search="true" data-width="300px"
                            data-title="项目">
                    </select>
                </div>
            </div>

            <#if treeMenuListTop??>
                <#list Session.treeMenuListTop as menu>
                    <div class="topbar-left-task">
                        <a style="margin-left: 13px;color: #33cabb" class="btn btn-link"
                           <#if menu.target??>target="${menu.target!}"</#if>
                           href="<#if menu.url?starts_with("/")>${ctx.contextPath+menu.url!}<#else>${menu.url!}</#if>">${menu.name}</a>
                    </div>
                </#list>
            </#if>

        </div>

        <ul class="navbar-right d-flex align-items-center">
            <!--切换主题配色-->
            <li class="dropdown dropdown-skin">
                <span data-toggle="dropdown" class="icon-item"><i class="mdi mdi-palette"></i></span>
                <ul class="dropdown-menu dropdown-menu-right" data-stopPropagation="true">
                    <li class="drop-title"><p>主题</p></li>
                    <li class="drop-skin-li clearfix">
                <span class="inverse">
                  <input type="radio" name="site_theme" value="default" id="site_theme_1" checked>
                  <label for="site_theme_1"></label>
                </span>
                        <span>
                  <input type="radio" name="site_theme" value="dark" id="site_theme_2">
                  <label for="site_theme_2"></label>
                </span>
                    </li>
                </ul>
            </li>
            <!--切换主题配色-->

            <li class="dropdown dropdown-profile">
                <a href="javascript:void(0)" data-toggle="dropdown" class="dropdown-toggle">
                    <img class="img-avatar img-avatar-48 m-r-10"
                         src="${ctx.contextPath}/static/admin/commons/img/defaultimg.webp" alt="笔下光年"/>
                    <span>笔下光年</span>
                </a>
                <ul class="dropdown-menu dropdown-menu-right">
                    <li>
                        <a class="dropdown-item" href="#!" onclick="loginUserPassword()"><i
                                    class="mdi mdi-lock-outline"></i> 修改密码</a>
                    </li>
                    <li class="dropdown-divider"></li>
                    <li>
                        <a class="dropdown-item" href="${ctx.contextPath}/loginOut"><i
                                    class="mdi mdi-logout-variant"></i> 退出登录</a>
                    </li>
                </ul>
            </li>
        </ul>

    </nav>
</header>