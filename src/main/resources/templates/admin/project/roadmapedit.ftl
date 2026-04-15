<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body class="card">
<div class="card-body">
    <form action="#!" method="post" id="roadmapform">
        <input type="hidden" name="id" value="${roadMap.id!}"/>

        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">标题</span>
                </div>
                <input type="text" class="form-control" name="title" id="title" value="${roadMap.title!}" maxlength="250"
                       placeholder="标题" required>
            </div>
        </div>

        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">计划时间</span>
                </div>
                <input autocomplete="off" type="text" class="form-control"
                       onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})"
                       name="stime" value="${roadMap.stime!}" placeholder="开始时间" required/>
                <input autocomplete="off" type="text" class="form-control"
                       onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})"
                       name="etime" value="${roadMap.etime!}" placeholder="结束时间" required/>
            </div>
        </div>

        <@projectReadFlagTag>
            <div class="form-group">
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
                <button type="button" class="btn btn-default" id="saveBtn2">保存并关闭</button>
            </div>
        </@projectReadFlagTag>
    </form>

</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">

    $('#saveBtn').click(function () {
        save(1);
    });
    $('#saveBtn2').click(function () {
        save(2);
    });

    function save(type) {
        let title = $("#title").val();
        if(isBlank(title)){
            layer.msg("请填写标题");
            return false;
        }
        $.ajax({
            url: "${ctx.contextPath}/admin/roadmap/save",
            cache: false,// 不缓存
            type: "post",
            data: $("#roadmapform").serialize(),
            success: function (d) {
                layer.msg(d.msg);
                if (type == 2) {
                    setTimeout(function () {
                        parent.reload();
                        layer_close();
                    }, 500);
                }
            }
        });
    }
</script>
</body>
</html>