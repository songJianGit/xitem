<!DOCTYPE html>
<html lang="zh">
<head>
    <title>反馈纪录</title>
    <link href="${ctx.contextPath}/static/pc/commons/css/style.css" rel="stylesheet">
</head>
<body style="margin: 0">
<textarea id="editor" style="width:99.9%;">${articleData.content!}</textarea>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    tinymce.init({
        selector: '#editor', //容器，可使用css选择器
        language: 'zh_CN',
        height: 579, //编辑器高度
        resize: false, // 禁用缩放按钮
        content_style: "img {max-width:100%;}",// 图片额外样式控制
        branding: false,// 隐藏品牌信息和"Upgrade"图标
        menubar: false, // 隐藏菜单栏
        plugins: 'image',
        image_uploadtab: true,
        toolbar: 'undo redo | styles formatselect | bold italic backcolor forecolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | image | removeformat',
        file_picker_callback: (callback, value, meta) => {// 上传函数（图片，视频，文件）
            let index = layer.open({
                type: 2,
                area: ['79%', ($(window).height() - 50) + 'px'],
                shadeClose: false,// 点击遮罩区域，关闭弹层
                title: '文件',
                content: '${ctx.contextPath}/admin/file/fileTable'
            });
        }
    });

    function getContent() {
        return tinymce.get('editor').getContent();
    }
</script>
</body>
</html>
