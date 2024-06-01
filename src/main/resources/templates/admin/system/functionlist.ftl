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

            <div class="container-fluid p-t-15">

                <div class="row">
                    <div class="col-12">
                        <form action="${ctx.contextPath}/admin/system/functionsSeq" method="post"
                              onsubmit="return check();">
                            <div class="card">
                                <div class="card-header">
                                    <div class="toolbar-btn-action">
                                        <button type="button" id="add" class="btn btn-primary" href="#!"><i
                                                    class="mdi mdi-plus"></i> 新增菜单
                                        </button>
                                        <button type="submit" class="btn btn-primary" href="#!"><i
                                                    class="mdi mdi-check"></i>
                                            保存排序
                                        </button>
                                    </div>
                                </div>
                                <div class="card-body">

                                    <table id="treeTable" style="width:100%"
                                           class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>名称</th>
                                            <th>链接</th>
                                            <th>排序</th>
                                            <th>标识</th>
                                            <th>显示</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <#list listFunctions as func>
                                            <tr id="${func.id}" pId=${func.pid}>
                                                <td>
                                                    ${func.name}
                                                </td>
                                                <td>
                                                    ${func.url!}
                                                </td>
                                                <td>
                                                    <input type="hidden" name="ids" value="${func.id}"/>
                                                    <input type="number" name="seqs" value="${func.seq!}"/>
                                                </td>
                                                <td>
                                                    ${func.tag!}
                                                </td>
                                                <td>
                                                    <#if func.showFlag==1>显示</#if>
                                                    <#if func.showFlag==0>隐藏</#if>
                                                </td>
                                                <td>
                                                    <a href="${ctx.contextPath}/admin/system/functionEdit?functionsId=${func.id}">编辑</a>
                                                    <a href="#!" class="functionsDelete" data-id="${func.id}">删除</a>
                                                    <a href="${ctx.contextPath}/admin/system/functionAdd?functionsId=${func.id}">新增下级菜单</a>
                                                </td>
                                            </tr>
                                        </#list>
                                    </table>
                                </div>
                            </div>
                        </form>
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
    $('#treeTable').treeTable({expandLevel: 6}).show();
    $('.functionsDelete').click(function () {
        let functionId = $(this).data('id');
        layer.confirm('确定删除本菜单和其下级所有菜单？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: '${ctx.contextPath}/admin/system/functionDelete',
                cache: false,
                data: {
                    'functionId': functionId
                },
                success: function (data) {
                    if (data.result) {
                        window.location.reload();
                    } else {
                        layer.msg(data.msg);
                    }
                }
            })
        }, function () {
        });
    });

    $('#add').click(function () {
        window.location.href = '${ctx.contextPath}/admin/system/functionAdd';
    });

    // 提交前校验
    function check() {
        let nullFlag = false;
        $('input[name=seqs]').each(function () {
            let inpu = $(this).val();
            if (inpu == undefined || inpu == '') {
                nullFlag = true;
            }
        });
        if (nullFlag) {
            layer.msg('排序信息不能为空');
            return false;
        } else {
            return true;
        }
    }
</script>
</body>
</html>