<aside class="lyear-layout-sidebar">

    <!-- logo -->
    <div id="logo" class="sidebar-header">
        <div style="font-size: 23px;max-width: 240px;color: #fff;text-align: center;min-height: 68px;line-height: 68px">
            xitem
        </div>
    </div>
    <div class="lyear-layout-sidebar-info lyear-scroll">
        <#--菜单是根据其url是否为空来判断其是否有下一级-->
        <nav class="sidebar-main">
            <ul class="nav-drawer">
                <li class="nav-item" id="1">
                    <a href="${ctx.contextPath}/admin/system/index?mclick=1"><i
                                class="mdi mdi-home"></i><span>后台首页</span></a>
                </li>
                <#if treeMenuList??>
                    <#list Session.treeMenuList as menu>
                        <li class="nav-item nav-item-has-subnav" id="${menu.id}">
                            <#if (menu.nodes?size>0)>
                                <a href="#!">
                                    <#if menu.icon??>
                                    <i class="${menu.icon!}"></i>
                                <#else>
                                    <i class="mdi mdi-format-align-justify"></i>
                                    </#if><span>${menu.name}</span>
                                </a>
                                <ul class="nav nav-subnav">
                                    <#list menu.nodes as node>
                                        <#if (node.nodes?size>0)>
                                            <li class="nav-item nav-item-has-subnav" id="${node.id}">
                                                <a href="#!">${node.name}</a>
                                                <ul class="nav nav-subnav">
                                                    <#list node.nodes as no>
                                                        <li id="${no.id}">
                                                            <a <#if no.target??>target="${no.target!}"</#if>
                                                               href="<#if no.url?starts_with("/")>${ctx.contextPath+no.url!}<#else>${no.url!}</#if><#if no.url!?contains("?")>&<#else>?</#if>mclick=${no.id}">${no.name}</a>
                                                        </li>
                                                    </#list>
                                                </ul>
                                            </li>
                                        <#else>
                                            <li id="${node.id}">
                                                <a <#if node.target??>target="${node.target!}"</#if>
                                                   href="<#if node.url?starts_with("/")>${ctx.contextPath+node.url!}<#else>${node.url!}</#if><#if node.url!?contains("?")>&<#else>?</#if>mclick=${node.id}">${node.name}</a>
                                            </li>
                                        </#if>
                                    </#list>
                                </ul>
                            <#else>
                                <a <#if menu.target??>target="${menu.target!}"</#if>
                                   href="<#if menu.url?starts_with("/")>${ctx.contextPath+menu.url!}<#else>${menu.url!}</#if><#if menu.url!?contains("?")>&<#else>?</#if>mclick=${menu.id}">
                                    <#if menu.icon??>
                                        <i class="${menu.icon!}"></i>
                                    <#else><i class="mdi mdi-format-align-justify"></i></#if>${menu.name}
                                </a>
                            </#if>
                        </li>
                    </#list>
                </#if>
            </ul>
        </nav>

        <div class="sidebar-footer">
            <p class="copyright">Copyright © 2024 <a href="#!">xitem</a>. All rights reserved.</p>
        </div>
    </div>

</aside>
