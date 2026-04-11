<!DOCTYPE html>
<html lang="zh" xmlns="http://www.w3.org/1999/html">
<head>
    <#include "../commons/head.ftl"/>
    <link rel="stylesheet" type="text/css" href="${ctx.contextPath}/static/admin/commons/pm-user/pm.css">
</head>
<body class="card">
<div class="card-body">
    <form action="#!" method="post" id="articleform" class="row">
        <input type="hidden" name="id" value="${article.id!}"/>
        <input type="hidden" id="userlists" name="userlists" value=""/>
        <div class="col-8">
            <div class="row">

                <div class="form-group col-12">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">标题</span>
                        </div>
                        <input type="text" class="form-control" name="title" value="${article.title!}" maxlength="250"
                               placeholder="标题" required>
                    </div>
                </div>

                <div class="form-group col-6">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">任务状态</span>
                        </div>
                        <select id="articleCategoryName" name="categoryId" class="form-control">
                            <#if categoryList??>
                                <#list categoryList as item>
                                    <option value="${item.id!}"
                                            <#if article.categoryId??><#if article.categoryId==item.id>selected</#if></#if>>
                                        ${item.title}
                                    </option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>

                <div class="form-group col-6">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">优先级</span>
                        </div>
                        <select id="articleCategoryName" name="levelId" class="form-control">
                            <#list categoryListLevel as item>
                                <option value="${item.id!}"
                                        <#if article.levelId??><#if article.levelId==item.id>selected</#if></#if>>
                                    ${item.title}
                                </option>
                            </#list>
                        </select>
                    </div>
                </div>

                <div class="form-group col-6">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">计划时间</span>
                        </div>
                        <input autocomplete="off" type="text" class="form-control"
                               onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})"
                               name="stime" value="${article.stime!}" placeholder="开始时间" required/>
                        <input autocomplete="off" type="text" class="form-control"
                               onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})"
                               name="etime" value="${article.etime!}" placeholder="结束时间" required/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                        <textarea id="editor" name="content"
                                  style="width:99.9%;"><#if article.articleData??>${article.articleData.content!}</#if></textarea>
            </div>
            <#if !(showFlag?? && showFlag==1)>
                <div class="form-group">
                    <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
                    <button type="button" class="btn btn-default" id="saveBtn2">保存并关闭</button>
                </div>
            </#if>
        </div>

        <div class="col-4 pl-lg-4">
            <div class="project-members-wrap">
                <div>
                    <div class="pm-title">任务成员</div>
                    <div class="pm-sub">列出任务内全部用户；勾选表示已加入<strong>当前任务</strong>。</div>
                </div>

                <div class="pm-search-wrap">
                    <div class="input-group input-group-sm">
                        <div class="input-group-prepend">
                            <span class="input-group-text bg-white"><i class="mdi mdi-magnify text-muted"></i></span>
                        </div>
                        <input type="search" class="form-control" id="pmSearch" placeholder="按姓名筛选…"
                               autocomplete="off">
                    </div>
                </div>

                <div class="d-flex justify-content-between align-items-center pm-toolbar">
                    <span class="text-muted small" id="pmStat">已加入 <strong class="text-dark">0</strong> / 共 <strong
                                class="text-dark">0</strong> 人</span>
                </div>

                <ul class="pm-list" id="pmMemberList">
                    <#list voList as item>
                        <li class="pm-item is-in-project" data-pm-id="${item.id!}" data-pm-name="${item.userName!}">
                            <div class="pm-check-cell">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" data-user="${item.userId!}"
                                           class="project-user-list custom-control-input pm-in-project-cb"
                                           id="pm_cb_${item.id!}" <#if item.joinFlag>checked</#if>>
                                    <label class="custom-control-label" for="pm_cb_${item.id!}"><span class="sr-only">加入本任务</span></label>
                                </div>
                            </div>
                            <div class="pm-avatar"><span>${item.userNameFast!}</span></div>
                            <div class="pm-meta">
                                <div class="pm-name">${item.userName!}</div>
                                <div class="pm-role">${item.jobTitle!}</div>
                            </div>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    </form>

</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript" src="${ctx.contextPath}/static/admin/commons/pm-user/pm.js"></script>
<script type="text/javascript">
    <#if showFlag?? && showFlag==1>
    // 禁用页面的input，富文本编辑器，按钮
    $("input").attr("disabled", "disabled");
    $("textarea").attr("disabled", "disabled");
    $("button").attr("disabled", "disabled");
    $("select").attr("disabled", "disabled");
    </#if>

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

        // 获取所有加入项目的人员
        let projectuser = $(".project-user-list:checked");
        let userlists = [];
        projectuser.each(function () {
            let userId = $(this).data('user');
            userlists.push(userId);
        });
        $("#userlists").val(JSON.stringify(userlists));

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
        <#if showFlag?? && showFlag==1>readonly: true, </#if>
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

    $('.selectpicker').selectpicker();
</script>
</body>
</html>