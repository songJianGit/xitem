<header class="lyear-layout-header">
    <nav class="navbar">
        <div class="topbar-left">
            <div class="lyear-aside-toggler">
                <span class="lyear-toggler-bar"></span>
                <span class="lyear-toggler-bar"></span>
                <span class="lyear-toggler-bar"></span>
            </div>

            <div class="topbar-left-project">
                <div class="project-top-select-wrap">
                    <select class="form-control" id="project-top-select" data-live-search="true" data-width="300px"
                            data-title="项目">
                    </select>
                </div>
            </div>

            <#if treeMenuListTop??>
                <#list Session.treeMenuListTop as menu>
                    <#if menu.id!='5'> <#--只显示特定按钮-->
                        <div class="topbar-left-task">
                            <a class="btn btn-link"
                               <#if menu.target??>target="${menu.target!}"</#if>
                               href="<#if menu.url?starts_with("/")>${ctx.contextPath+menu.url!}<#else>${menu.url!}</#if>">${menu.name}</a>
                        </div>
                    </#if>
                </#list>
            </#if>

        </div>

        <ul class="navbar-right d-flex align-items-center">
            <li class="dropdown dropdown-profile">
                <a href="javascript:void(0)" data-toggle="dropdown" class="dropdown-toggle">
                    <img class="img-avatar img-avatar-48 m-r-10"
                         src="${ctx.contextPath}/static/admin/commons/img/defaultimg.webp" alt="头像"/>
                    <span>${Session.puser.userName!}</span>
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