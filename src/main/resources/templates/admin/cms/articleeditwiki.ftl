<!DOCTYPE html>
<html lang="zh" xmlns="http://www.w3.org/1999/html">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body class="card">
<div class="card-body">
    <form action="#!" method="post" id="articleform">
        <input type="hidden" name="id" value="${article.id!}"/>
        <input type="hidden" name="atype" value="2"/>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">标题</span>
                </div>
                <input type="text" class="form-control" name="title" value="${article.title!}" maxlength="250"
                       placeholder="标题" required>
            </div>
        </div>
        <div class="form-group">
                        <textarea id="editor" name="content"
                                  style="width:99.9%;"><#if article.articleData??>${article.articleData.content!}</#if></textarea>
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
        let editor = tinymce.get('editor');
        if (editor) {
            editor.save();// 在提交前手动保存(TinyMCE 在初始化时会隐藏原始的 <textarea>,原始 <textarea> 的值并不会自动实时更新)
        }
        $.ajax({
            url: "${ctx.contextPath}/admin/cms/articleSave",
            cache: false,// 不缓存
            type: "post",
            data: $("#articleform").serialize(),
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

    tinymce.init({
        selector: '#editor', //容器，可使用css选择器
        relative_urls: false,// 禁用相对路径，强制使用绝对路径
        remove_script_host: true,// 不保留协议和主机名
        convert_urls: true,// 将相对路径转换为绝对路径
        language: 'zh_CN',
        height: 559, //编辑器高度
        resize: false, // 禁用缩放按钮
        content_style: "img {max-width:100%;}",// 图片额外样式控制
        branding: false,// 隐藏品牌信息和"Upgrade"图标
        menubar: false, // 隐藏菜单栏
        plugins: ['image', 'code', 'table', 'lists'],
        image_uploadtab: true,
        toolbar: 'undo redo | styles | bold italic strikethrough forecolor | table bullist numlist | image | removeformat | code',
        file_picker_callback: (callback, value, meta) => {// 上传函数（图片，视频，文件）
            let filetype = 0;
            switch (meta.filetype) {
                case 'image':
                    // filetype = '.jpg, .jpeg, .png, .gif, .webp';
                    filetype = 1;
                    break;
                case 'media':
                    // filetype = '.mp3, .mp4';
                    filetype = 2;
                    break;
                case 'file':
                    // filetype = 'file';
                    filetype = 3;
                    break;
                default:
                    filetype = 0;
            }
            let index = layer.open({
                type: 2,
                area: ['79%', ($(window).height() - 50) + 'px'],
                shadeClose: false,// 点击遮罩区域，关闭弹层
                title: '文件',
                content: '${ctx.contextPath}/admin/file/fileTable?selectFlag=1',
                end: function () {
                    let fileWebPath = window.fileWebPath; // 从全局变量中获取数据
                    let fileName = window.fileName; // 从全局变量中获取数据
                    if (fileWebPath) {
                        let json = {};
                        switch (filetype) {
                            case 1:
                                json = {
                                    alt: fileName
                                };
                                break;
                            case 2:
                                // { source2: 'alt.ogg', poster: 'image.jpg' }
                                json = {
                                    source2: fileWebPath,
                                    poster: "javascript:;"
                                }
                                break;
                            case 3:
                                json = {
                                    text: fileName
                                };
                                break;
                            default:
                                filetype = 0;
                        }
                        callback(fileWebPath, json);
                    }
                }
            });
        }
    });
</script>
</body>
</html>