<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "commons/head.ftl"/>
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <#include "commons/aside.ftl"/>
        <!--End 左侧导航-->

        <!--头部信息-->
        <#include "commons/header.ftl"/>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="lyear-layout-content">
            <div class="container-fluid p-t-15">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <h4>${Session.puser.nickName!}，欢迎回来。</h4>
                            </div>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="row">
                            <div class="col-6">
                                <div class="card">
                                    <div class="card-header">系统资源</div>
                                    <div class="card-body">
                                        <div class="mb-3">
                                            操作系统：${osName!}&nbsp;&nbsp;${availableProcessors!}
                                            核&nbsp;&nbsp;${totalMemory!}
                                        </div>
                                        <div class="mb-3">
                                            CPU：${systemCpuLoad!}%
                                            <div class="progress" style="height: 20px;">
                                                <div class="progress-bar" role="progressbar"
                                                     style="width: ${systemCpuLoad!}%;" aria-valuenow="25"
                                                     aria-valuemin="0" aria-valuemax="100"></div>
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            内存：${usedMemory}&nbsp;/&nbsp;${totalMemory}
                                            <div class="progress" style="height: 20px;">
                                                <div class="progress-bar" role="progressbar"
                                                     style="width: ${usedMemoryPercent!}%;" aria-valuenow="25"
                                                     aria-valuemin="0" aria-valuemax="100">${usedMemoryPercent!}%
                                                </div>
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            磁盘：${usedSpace}&nbsp;/&nbsp;${totalSpace}
                                            <div class="progress" style="height: 20px;">
                                                <div class="progress-bar" role="progressbar"
                                                     style="width: ${usedSpacePercent!}%;" aria-valuenow="25"
                                                     aria-valuemin="0" aria-valuemax="100">${usedSpacePercent!}%
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="card">
                                    <div class="card-header">JVM内存</div>
                                    <div class="card-body">
                                        <div class="mb-3">
                                            堆内：
                                            <div>
                                                初始：${heapMemoryUsageInit!}&nbsp;&nbsp;当前使用：${heapMemoryUsageUsed!}
                                                &nbsp;&nbsp;
                                                已提交：${heapMemoryUsageCommitted!}&nbsp;&nbsp;最大：${heapMemoryUsageMax!}
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            非堆内：
                                            <div>
                                                初始：${nonHeapMemoryUsageInit!}
                                                &nbsp;&nbsp;当前使用：${nonHeapMemoryUsageUsed!}&nbsp;&nbsp;
                                                已提交：${nonHeapMemoryUsageCommitted!}
                                                &nbsp;&nbsp;最大：${nonHeapMemoryUsageMax!}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "commons/js.ftl"/>
</body>
</html>