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
            let filetype;
            //为不同插件指定文件类型及后端地址
            switch (meta.filetype) {
                case 'image':
                    filetype = '.jpg, .jpeg, .png, .gif, .webp';
                    break;
                case 'media':
                    filetype = '.mp3, .mp4';
                    break;
                case 'file':
                    filetype = 'file';
                    break;
                default:
                    filetype = '';
            }
            //模拟出一个input用于添加本地文件
            let input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', filetype);
            input.click();
            input.onchange = function () {
                let file = this.files[0];
                let xhr, formData;
                xhr = new XMLHttpRequest();
                xhr.withCredentials = false;// 默认就是false，表示不在跨域请求中包含任何凭证信息，这里显示声明一下。
                xhr.open('POST', '${ctx.contextPath}/admin/tinymce/upload');
                xhr.onload = function () {
                    if (xhr.status != 200) {
                        alert('HTTP Error: ' + xhr.status);
                        return;
                    }
                    if (xhr.responseText == undefined || xhr.responseText == null || xhr.responseText == '') {
                        alert('ERROR: 上传失败');
                        return;
                    }
                    let json = JSON.parse(xhr.responseText);
                    callback(json.url, json.jsonInfo);
                };
                formData = new FormData();
                formData.append('file', file, file.name);
                xhr.send(formData);
            }
        }
    });

    function getContent() {
        return tinymce.get('editor').getContent();
    }
</script>
</body>
</html>
