<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body style="background-color: #fff">
<form action="#" method="post" id="organform">
    <input type="hidden" name="id" value="${organ.id!}"/>
    <div class="card">
        <div class="card-body">
            <div class="form-group col-6">
                <label for="name">机构名称</label>
                <input type="text" class="form-control" id="name" name="name"
                       value="${organ.name!}" placeholder="机构名称" maxlength="100" required/>
            </div>

            <div class="form-group col-6">
                <label for="organnum">机构编号</label>
                <input type="text" class="form-control" id="organnum" name="organNum"
                       value="${organ.organNum!}" placeholder="机构编号" maxlength="100" required/>
            </div>
            <div class="form-group col-6">
                <label for="seq">排序信息</label>
                <input type="number" class="form-control" id="seq" name="seq"
                       value="${organ.seq!}" placeholder="排序信息" required/>
            </div>
            <div class="form-group col-6">
                <label for="pname">
                    上级机构
                </label>
                <input type="text" class="form-control" value="${organ.pname!'无'}" id="pname" readonly/>
                <input type="hidden" value="${organ.pid!}" id="pid" name="pid"/>
            </div>

            <div class="form-group col-12">
                <button type="button" class="btn btn-primary" id="saveBtn">保 存</button>
                <button type="button" class="btn btn-default" onclick="layer_close();">取 消</button>
            </div>
        </div>
    </div>
</form>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    $('#saveBtn').click(function () {
        $.ajax({
            url: "${ctx.contextPath}/admin/organ/saveOrgan",
            cache: false,// 不缓存
            type: "post",
            data: $("#organform").serialize(),
            success: function (d) {
                layer.msg(d.msg);
                setTimeout(function () {
                    parent.reload();
                    layer_close();
                }, 500);
            }
        });
    });
    $('#pname').click(function () {
        layer_show('机构', '${ctx.contextPath}/admin/organ/organShow');
    });

    function organCallback(data) {
        $("#pid").val(data.id);
        $("#pname").val(data.name);
    }

</script>
</body>
</html>