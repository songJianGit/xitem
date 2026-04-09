<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <#include "../commons/aside.ftl"/>
        <!--End 左侧导航-->

        <!--头部信息-->
        <#include "../commons/header.ftl"/>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid p-t-15">

                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">

                                <form action="${ctx.contextPath}/admin/cms/articleSave" method="post" class="row"
                                      onsubmit="return check();">
                                    <input type="hidden" name="id" value="${article.id!}"/>

                                    <div class="form-group col-6">
                                        <label for="title">标题</label>
                                        <input type="text" class="form-control" id="title" name="title"
                                               value="${article.title!}" placeholder="标题" maxlength="250" required/>
                                    </div>

                                    <div class="form-group col-6">
                                        <label for="articleCategoryName">文章目录</label>
                                        <input id="articleCategoryName" placeholder="点击选择目录" class="form-control"
                                               value="${article.categoryName!}" readonly/>
                                        <input id="articleCategory" type="hidden" name="categoryId"
                                               value="${article.categoryId!}"/>
                                    </div>

                                    <div class="form-group col-6">
                                        <label for="keywords">关键字（英文逗号分隔）</label>
                                        <input type="text" class="form-control" id="keywords" name="keywords"
                                               value="${article.keywords!}" placeholder="关键字" maxlength="250"/>
                                    </div>
                                    <div class="form-group col-6"></div>
                                    <div class="form-group col-6">
                                        <label for="description">摘要</label>
                                        <textarea id="description" maxlength="255" class="form-control"
                                                  name="description">${article.description!}</textarea>
                                    </div>
                                    <div class="form-group col-12">
                                        <label for="description">正文</label>
                                        <textarea id="editor" name="content"
                                                  style="width:99.9%;"><#if article.articleData??>${article.articleData.content!}</#if></textarea>
                                    </div>
                                    <div class="form-group col-12">
                                        <label for="copyfrom">来源</label>
                                        <input type="text" class="form-control" id="copyfrom" name="copyfrom"
                                               <#if article.articleData??>value="${article.articleData.copyfrom!}"</#if>
                                               placeholder="来源" maxlength="250"/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="customContentView">自定义内容视图</label>
                                        <select id="customContentView" name="customContentView" class="form-control">
                                            <option value="frontViewArticle"
                                                    <#if article.customContentView??><#if article.customContentView=='frontViewArticle'>selected</#if></#if>>
                                                文章
                                            </option>
                                            <option value="frontViewArticlevideo"
                                                    <#if article.customContentView??><#if article.customContentView=='frontViewArticlevideo'>selected</#if></#if>>
                                                视频
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="viewConfig">自定义视图参数（json格式，例如:
                                            {"count":2,"title_show":"yes"}）</label>
                                        <input type="text" class="form-control" id="viewConfig" name="viewConfig"
                                               value="${article.viewConfig!}" placeholder="自定义视图参数"
                                               maxlength="500"/>
                                    </div>

                                    <div class="form-group col-6">
                                        <label style="display: block">发布状态</label>
                                        <div class="form-check form-check-inline">
                                            <input <#if article.pubFlag??><#if article.pubFlag==1>checked</#if></#if>
                                                   class="form-check-input" type="radio" name="pubFlag"
                                                   required
                                                   id="pubFlag1" value="1">
                                            <label class="form-check-label" for="pubFlag1">发布</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input <#if article.pubFlag??><#if article.pubFlag==0>checked</#if></#if>
                                                   class="form-check-input" type="radio" name="pubFlag"
                                                   required
                                                   id="pubFlag0" value="0">
                                            <label class="form-check-label" for="pubFlag0">未发布</label>
                                        </div>
                                    </div>

                                    <div class="form-group col-12">
                                        <button type="submit" class="btn btn-primary">确 定</button>
                                        <button type="button" class="btn btn-default"
                                                onclick="history.back(-1);return false;">返 回
                                        </button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    function check() {
        let articleCategoryName = $("#articleCategoryName").val();
        if (isBlank(articleCategoryName)) {
            layer.msg("请选择文章目录");
            return false;
        }
        return true;
    }

    tinymce.init({
        selector: '#editor', //容器，可使用css选择器
        relative_urls: false,// 禁用相对路径，强制使用绝对路径
        remove_script_host: true,// 不保留协议和主机名
        convert_urls: true,// 将相对路径转换为绝对路径
        language: 'zh_CN',
        height: 579, //编辑器高度
        resize: false, // 禁用缩放按钮
        content_style: "img {max-width:100%;}",// 图片额外样式控制
        branding: false,// 隐藏品牌信息和"Upgrade"图标
        menubar: false, // 隐藏菜单栏
        plugins: ['image', 'code', 'table', 'lists'],
        image_uploadtab: true,
        toolbar: 'undo redo | styles | bold italic strikethrough backcolor forecolor | alignleft aligncenter alignright alignjustify | table bullist numlist | outdent indent | image | removeformat | code',
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

    $("#articleCategoryName").click(function () {
        layer_show("课程目录", '${ctx.contextPath}/admin/category/article/categoryShow');
    });

    function categoryCallback(data) {
        $("#articleCategory").val(data.id);
        $("#articleCategoryName").val(data.title);
    }
</script>
</body>
</html>