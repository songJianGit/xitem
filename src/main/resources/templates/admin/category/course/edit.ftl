<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../../commons/head.ftl"/>
</head>
<body style="background-color: #fff">
<form action="#" method="post" id="categoryform">
    <input type="hidden" name="id" value="${category.id!}"/>
    <div class="card">
        <div class="card-body">
            <div class="form-group col-6">
                <label for="title">分类名称</label>
                <input type="text" class="form-control" id="title" name="title"
                       value="${category.title!}" placeholder="分类名称" maxlength="100" required/>
            </div>

            <div class="form-group col-6">
                <label for="ptitle">
                    上级分类
                </label>
                <input type="text" class="form-control" value="${category.ptitle!'无'}" id="ptitle" readonly/>
                <input type="hidden" value="${category.pid!}" id="pid" name="pid"/>
            </div>

            <div class="form-group col-12">
                <button type="button" class="btn btn-primary" id="saveBtn">保 存</button>
                <button type="button" class="btn btn-default" onclick="layer_close();">取 消</button>
            </div>
        </div>
    </div>
</form>
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
    $('#saveBtn').click(function () {
        $.ajax({
            url: "${ctx.contextPath}/admin/category/course/saveCategory",
            cache: false,// 不缓存
            type: "post",
            data: $("#categoryform").serialize(),
            success: function (d) {
                layer.msg(d.msg);
                setTimeout(function () {
                    parent.reload();
                    layer_close();
                }, 500);
            }
        });
    });
    $('#ptitle').click(function () {
        layer_show('分类', '${ctx.contextPath}/admin/category/course/categoryShow');
    });

    function categoryCallback(data) {
        $("#pid").val(data.id);
        $("#ptitle").val(data.title);
    }

</script>
</body>
</html>