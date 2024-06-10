<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../../commons/head.ftl"/>
    <link rel="stylesheet" href="${ctx.contextPath}/static/plugins/ztree-v3/css/zTreeStyle/zTreeStyle.css"
          type="text/css">
</head>
<body style="background-color: #fff">
<div class="card" style="-webkit-box-shadow:none;box-shadow:none">
    <div class="card-header">
        <button type="button" class="btn btn-primary" id="saveBtn">确定</button>
    </div>
    <div class="card-body" style="height: 100%">
        <ul id="ztree" class="ztree"></ul>
    </div>
</div>
<#include "../../commons/js.ftl"/>
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/ztree-v3/js/jquery.ztree.all.js"></script>
<script type="text/javascript">
    $("#saveBtn").click(function () {
        let items = [];
        let treeNode = $.fn.zTree.getZTreeObj("ztree");
        let nodes = treeNode.getCheckedNodes(true);
        if (nodes.length == 0) {
            alert("请至少选择一个");
            return false;
        }
        if ('${type}' == '1' && nodes.length != 1) {
            alert("最多选择一个");
            return false;
        }
        $.each(nodes, function (i, item) {
            items.push({"id": item.id, "title": item.name});
        });
        if('${type}' == '1'){
            parent.categoryCallback(items[0]);
        }
        if('${type}' == '2'){
            parent.categoryCallback(items);
        }
        layer_close();
    });

    let zTreeObj;
    $(function () {
        loadTree();
    });
    function loadTree() {
        $.ajax({
            url: "${ctx.contextPath}/admin/category/question/data",
            cache: false,// 不缓存
            success: function (d) {
                let setting = {
                    data: {
                        simpleData: {
                            enable: true// 简单数据
                        }
                    },
                    callback: {
                        onCheck: nodeOnCheck
                    },
                    check: {
                        enable: true,
                        chkboxType: {"Y": "", "N": ""}
                    }
                };
                zTreeObj = $.fn.zTree.init($("#ztree"), setting, d);
                showOne();
            }
        });
    }

    function nodeOnCheck(tevent, treeId, treeNode) {
        if (treeNode.checked) {
            setNodeCheckDisabled(treeNode, true);
        } else {
            setNodeCheckDisabled(treeNode, false);
        }
    }

    //当选中父节点，子节点去除选中状态，同时禁止选择
    //当取消选择父节点，子节点去除禁用状态
    function setNodeCheckDisabled(node, flag) {
        let treeNode = $.fn.zTree.getZTreeObj("ztree");
        if (node.children != null) {
            var nodes = node.children;
            for (let i = 0; i < nodes.length; i++) {
                setNodeCheckDisabled(nodes[i], flag);
                treeNode.checkNode(nodes[i], false, false);
                treeNode.setChkDisabled(nodes[i], flag);
            }
        }
    }

    // 展开第一级
    function showOne() {
        let nodes = zTreeObj.getNodes();
        zTreeObj.expandNode(nodes[0], true, false, false);
    }
</script>
</body>
</html>