<!DOCTYPE html>
<html lang="zh">
<head>
    <title>反馈纪录-新的反馈</title>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="pc-main">
    <#include "../commons/header.ftl"/>
    <div class="pc-body">
        <form action="${ctx.contextPath}/pc/workorder/save" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="title">标题</label>
                <input type="text" class="form-control" placeholder="标题" name="title" id="title" maxlength="100" required/>
            </div>
            <div class="form-group">
                <label for="content">内容</label>
                <textarea rows="3" name="content" id="content" class="form-control" maxlength="2000"></textarea>
            </div>
            <div class="form-group">
                <label for="files">附件</label>
                <input name="file" type="file" class="form-control-file"/>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">提交</button>
                <button type="button" class="btn btn-light" onclick="history.back(-1);return false;">返回</button>
            </div>
        </form>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>