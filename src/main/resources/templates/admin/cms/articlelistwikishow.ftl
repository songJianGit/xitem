<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        pre {
            display: block;
            padding: 9px;
            margin: 0 0 10px;
            /*font-size: 13px;*/
            line-height: 20px;
            word-break: break-all;
            word-wrap: break-word;
            white-space: pre-wrap;
            background-color: #f5f5f5;
            border: 1px solid #ccc;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
        }

        img {
            max-width: 100%;
        }
    </style>
</head>
<body>
<div class="card" id="maxHeight">
    <#if article??>
        <div class="card-header">
            ${article.title!}
            <@projectReadFlagTag>
            <ul class="card-actions">
                <li>
                    <a class="delBtn" href="#!" data-id="${article.id!}" data-toggle="tooltip" title="" data-original-title="删除"><i class="mdi mdi-delete-outline"></i></a>
                </li>
                <li>
                    <a class="editBtn" href="#!" data-id="${article.id!}" data-toggle="tooltip" title="" data-original-title="编辑"><i class="mdi mdi-square-edit-outline"></i></a>
                </li>
            </ul>
            </@projectReadFlagTag>
        </div>
    </#if>
    <div class="card-body">
        <#if articleDataContent??>
            ${articleDataContent!}
        </#if>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    window.parent.postMessage({// 向父级页面发送高度信息
        type: 'resize',
        height: document.getElementById('maxHeight').scrollHeight + 3
    }, '*');

    $(function (){
        $(".delBtn").click(function (){
            let id = $(this).data("id");
            window.parent.postMessage({// 向父级页面发送高度信息
                type: 'delBtnFun',
                id: id
            }, '*');
        });
        $(".editBtn").click(function (){
            let id = $(this).data("id");
            window.parent.postMessage({// 向父级页面发送高度信息
                type: 'editBtnFun',
                id: id
            }, '*');
        });
    });
</script>
</body>
</html>
