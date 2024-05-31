<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <link href="${ctx.contextPath}/static/plugins/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet"
          type="text/css"/>
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
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <form action="${ctx.contextPath}/admin/system/roleFunctionSave" method="post">
                                    <input type="hidden" value="${role.id}" name="roleId"/>
                                    <button type="button" class="btn btn-primary" onclick="history.back();">返回
                                    </button>
                                    <button type="submit" class="btn btn-primary" data-type="addBtn">保存</button>
                                    <div class="divider text-uppercase">${role.name}</div>
                                    <table id="treeTable" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th><input type="checkbox" id="checkAll">全选</th>
                                        </tr>
                                        </thead>
                                        <#list listFunction as func>
                                            <tr id="${func.id}" pId="${func.pid}">
                                                <td>
                                                    <input type="checkbox"
                                                           class="checkboxinput${func.pid} inputid${func.id}"
                                                           name="funIds" value="${func.id}" data-pid="${func.pid}"/>
                                                    <span style="margin-left: 3px">${func.name}</span>
                                                </td>
                                            </tr>
                                        </#list>
                                    </table>
                                </form>
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
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/treeTable/jquery.treeTable.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#treeTable").treeTable({expandLevel: 6}).show();
        $(":checkbox[name=funIds]").click(function () {
            let status = $(this).prop('checked');
            let value = $(this).val();
            let data_value = $(this).data('pid');

            diguiCheckedLO(status, value);
            diguiCheckedUP(data_value);
        });

        // 还原本角色信息
        <#list roleFunction as item >
        $('.inputid' + '${item.id}').prop('checked', true);
        </#list>
        <#if roleFunction?? && roleFunction?size!=0>
            $('#checkAll').prop('checked', true);
        </#if>
    });

    // 递归调用，选中其下所有 或 取消所有
    function diguiCheckedLO(status, value) {
        let tag = ".checkboxinput" + value;
        if (status) {
            $(tag).prop('checked', true);
        } else {
            $(tag).prop('checked', false);
        }
        let lis = $(tag);
        if (lis.length > 0) {
            lis.each(function () {
                diguiCheckedLO(status, $(this).val());
            });
        }
    }

    // 递归调用，选中其上父级菜单
    function diguiCheckedUP(value) {
        let tag = ".inputid" + value;
        $(tag).prop('checked', true);
        let lis = $(tag);
        if (lis.length > 0) {
            lis.each(function () {
                diguiCheckedUP($(this).data('pid'));
            });
        }
    }

    $('#checkAll').click(function () {
        $(':checkbox[name=funIds]').prop('checked', this.checked);
    });

</script>
</body>
</html>