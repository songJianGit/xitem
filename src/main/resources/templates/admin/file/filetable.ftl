<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="card" id="maxHeight">
    <div class="card-body">
        <div id="custom-toolbar">
            <div class="toolbar-btn-action">
                <button type="button" id="upload" class="btn btn-primary">
                    上传文件
                </button>
                <button type="button" id="createFolder" class="btn btn-primary">
                    新建文件夹
                </button>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>大小</th>
                    <th>类型</th>
                    <th>修改日期</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#if path??&&path!="">
                    <tr>
                        <td><a href="${ctx.contextPath}/admin/file/fileTableBack?delFlag=${fileTableDto.delFlag!}&selectFlag=${fileTableDto.selectFlag!}">..</a></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </#if>
                <#list fileData as item>
                    <#assign fileWebPath = "${ctx.contextPath}${upathRelative!}/${item.name!}">
                    <tr>
                        <td>
                            <#if item.type==1><a target="_blank"
                                                 href="${fileWebPath!}">${item.name!}</a></#if>
                            <#if item.type==2><a
                                href="${ctx.contextPath}/admin/file/fileTable?delFlag=${fileTableDto.delFlag!}&selectFlag=${fileTableDto.selectFlag!}&path=${path!}/${item.name!}">${item.name!}</a></#if>
                        </td>
                        <td>${item.size!}</td>
                        <td>
                            <#if item.type==1>文件</#if>
                            <#if item.type==2>文件夹</#if>
                        </td>
                        <td></td>
                        <td>
                            <div class="btn-group">
                                <#if fileTableDto.selectFlag??&&fileTableDto.selectFlag==1&&item.type==1>
                                    <button type="button" class="btn btn-sm btn-default" onclick="selectBtn('${fileWebPath}','${item.name}')" title="选择">选择
                                    </button>
                                </#if>
                                <#if fileTableDto.delFlag??&&fileTableDto.delFlag==1>
                                    <button type="button" class="btn btn-sm btn-default" onclick=" " title="删除">删除
                                    </button>
                                </#if>
                            </div>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">

    function selectBtn(fileWebPath, fileName){
        window.parent.fileWebPath = fileWebPath; // 将数据存储到父页面的全局变量中
        window.parent.fileName = fileName;
        let index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    $('#upload').click(function () {
        layer_show("上传", '${ctx.contextPath}/admin/upload/fileUploadMain?multiple=1&maxnum=3&type=png,mp4', '', '', false, 0);
    });

    $('#createFolder').click(function () {
        layer.prompt({title: '输入文件夹名称', formType: 0}, function (name, index) {
            layer.msg(name);
            $.ajax({
                url: "${ctx.contextPath}/admin/file/createFolder",
                cache: false,// 不缓存
                data: {
                    folderName: name
                },
                success: function (d) {
                    reloadTable();
                    layer.close(index);
                }
            });
        });
    });

    function uploadCallback(infos) {
        console.log("uploadCallback", infos);
        reloadTable();
    }

    // 刷新当前页
    function reloadTable(){
        // 获取当前页面的URL
        let currentUrl = new URL(window.location.href);
        currentUrl.searchParams.set('type', 1);
        window.location.href = currentUrl.href;
    }

    window.parent.postMessage({// 向父级页面发送高度信息
        type: 'resize',
        height: document.getElementById('maxHeight').scrollHeight + 3
    }, '*');
</script>
</body>
</html>
