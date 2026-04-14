<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        .pv-card {
            border: 1px solid #e9ecef;
            border-radius: 10px;
            box-shadow: 0 2px 12px rgba(31, 45, 61, .05);
            margin-bottom: 16px;
        }

        .pv-card .card-header {
            background: #fff;
            border-bottom: 1px solid #eef1f4;
            font-weight: 600;
            color: #343a40;
        }

        .pv-content-wrap {
            line-height: 1.8;
            color: #495057;
            word-break: break-word;
        }

        .pv-empty {
            padding: 12px 0;
            color: #868e96;
            font-size: 13px;
        }

        .pv-member-grid {
            display: grid;
            grid-template-columns: repeat(5, minmax(0, 1fr));
            gap: 10px;
        }

        .pv-member-item {
            border: 1px solid #e9ecef;
            border-radius: 8px;
            background: #fff;
            padding: 10px 8px;
            min-height: 66px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
        }

        .pv-member-avatar {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 6px;
            border: 1px solid #e9ecef;
        }

        .pv-member-avatar-text {
            display: flex;
            align-items: center;
            justify-content: center;
            background: #f1f3f5;
            color: #495057;
            font-size: 13px;
            font-weight: 600;
        }

        .pv-member-name {
            font-weight: 600;
            color: #212529;
            margin-bottom: 6px;
            line-height: 1.3;
            width: 100%;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .pv-member-role {
            font-size: 12px;
            color: #6c757d;
            background: #f1f3f5;
            border-radius: 10px;
            padding: 2px 8px;
            white-space: nowrap;
            max-width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        @media (max-width: 1400px) {
            .pv-member-grid {
                grid-template-columns: repeat(4, minmax(0, 1fr));
            }
        }

        @media (max-width: 1200px) {
            .pv-member-grid {
                grid-template-columns: repeat(3, minmax(0, 1fr));
            }
        }

        @media (max-width: 768px) {
            .pv-member-grid {
                grid-template-columns: repeat(2, minmax(0, 1fr));
            }
        }

        .pv-milestone-item {
            padding: 10px 0 12px;
            border-bottom: 1px dashed #eceff3;
        }

        .pv-milestone-item:last-child {
            border-bottom: 0;
        }

        .pv-milestone-head {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 8px;
            gap: 10px;
        }

        .pv-milestone-title {
            font-weight: 600;
            color: #212529;
            margin: 0;
        }

        .pv-milestone-stats {
            margin: 0;
            padding: 8px 10px;
            font-size: 12px;
            line-height: 1.7;
            color: #495057;
            background: #f8f9fa;
            border: 1px solid #edf0f3;
            border-radius: 6px;
            white-space: pre-wrap;
            word-break: break-word;
        }
    </style>
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <#include "../commons/aside.ftl"/>
        <!--End 左侧导航-->

        <!--头部信息-->
        <#include "../commons/header.ftl"/>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid p-t-15">

                <div class="row">
                    <div class="col-8">
                        <div class="card pv-card">
                            <div class="card-header">${project.title!}
                                <@projectReadFlagTag>
                                    <button type="button" class="btn btn-primary" onclick="edit()">编辑</button>
                                </@projectReadFlagTag>
                            </div>
                            <div class="card-body">
                                <#if projectContent?? && projectContent?trim?length gt 0>
                                    <div class="pv-content-wrap">${projectContent}</div>
                                <#else>
                                    <div class="pv-empty">暂无项目概述内容。</div>
                                </#if>
                            </div>
                        </div>
                        <div class="card pv-card">
                            <div class="card-header">项目成员</div>
                            <div class="card-body">
                                <#if voList?? && (voList?size > 0)>
                                    <div class="pv-member-grid">
                                        <#list voList as item>
                                            <div class="pv-member-item">
                                                <#if item.avatar?? && item.avatar?trim?length gt 0>
                                                    <img class="pv-member-avatar" src="${ctx.contextPath}${item.avatar}"
                                                         alt="${item.userName!'用户头像'}">
                                                <#else>
                                                    <div class="pv-member-avatar pv-member-avatar-text">${item.userNameFast!}</div>
                                                </#if>
                                                <div class="pv-member-name">${item.userName!}</div>
                                                <div class="pv-member-role">${item.jobTitle!'未设置岗位'}</div>
                                            </div>
                                        </#list>
                                    </div>
                                <#else>
                                    <div class="pv-empty">暂无项目成员。</div>
                                </#if>
                            </div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="card pv-card">
                            <div class="card-header">里程碑</div>
                            <div class="card-body">
                                <#if roadMapList?? && (roadMapList?size > 0)>
                                    <#list roadMapList as item>
                                        <div class="pv-milestone-item">
                                            <div class="pv-milestone-head">
                                                <p class="pv-milestone-title">${item.title!'未命名里程碑'}</p>
                                            </div>
                                            <p class="pv-milestone-stats">${item.percentage!'暂无状态统计'}</p>
                                        </div>
                                    </#list>
                                <#else>
                                    <div class="pv-empty">暂无里程碑信息。</div>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function edit() {
        layer_show('编辑', "${ctx.contextPath}/admin/project/edit2?id=${project.id!}", "90%");
    }

    function reload() {
        window.location.reload();
    }

    // 进入项目概述页面时，自动展开任务菜单
    setTimeout(function () {
        $("#1880490630882680833").addClass("open");
    }, 100)
</script>
</body>
</html>