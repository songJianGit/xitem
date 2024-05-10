<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <link href="${ctx.contextPath}/static/plugins/webuploader/css/webuploader.css" rel="stylesheet">
</head>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--页面主要内容-->
        <main style="padding-top: 3px">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <div class="toolbar-btn-action">
                                    <div class="btn" id="picker">选择文件</div>
                                    <a id="subBtn" class="btn btn-success" href="#!" style="margin-top: -5px;">保存</a>
                                    <input type="hidden" name="outjson" id="outjson"/>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="uploadstatutable">
                                        <thead>
                                        <tr>
                                            <th>文件名</th>
                                            <th>进度</th>
                                            <th>状态</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#-- 文件显示 -->
                                        </tbody>
                                    </table>
                                </div>
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
<script type="text/javascript" src="${ctx.contextPath}/static/plugins/webuploader/js/webuploader.min.js"></script>
<script type="text/javascript">
    // [{'name':'文件名.xx','url':'/xxx/sss/p11.jpg'},{'name':'文件名22.xx','url':'/xxx/sss/aa222aa.jpg'}]
    let uuid = '${uuid}';// 站点唯一标识
    let allMaxSize = ${maxsize};// 上传文件总大小（单位Mb）
    $(function () {
        webUpload();
        subBtn();
    });

    function subBtn() {
        $('#subBtn').click(function () {
            let maxNum = $('.upfiletable').length;
            if (maxNum > '${maxnum}') {
                layer.msg('最多上传${maxnum}个文件，请删除多余文件');
                return false;
            }

            let bf = true;
            $(".progressAF").each(function () {
                if ($(this).text() != '100%') {
                    bf = false;
                }
            });
            $(".progresstextAF").each(function () {
                if ($(this).text() != '已完成') {
                    bf = false;
                }
            });
            if (!bf) {
                layer.msg('资源列表中的部分文件未成功上传');
                return false;
            }
            let index_zz = layer.load(1, {// 遮罩层
                shade: [0.5, '#fff']
            });
            let timePath = getTimePath();
            assignment(timePath);// 收集信息
            savetoData(timePath);// 保存到数据库
            window.parent.uploadCallback($("#outjson").val());// 回调父页面的函数
            layer.close(index_zz);
            layer_close();
        });
    }

    function getTimePath() {
        let currentDate = new Date();
        let year = currentDate.getFullYear();
        let month = ("0" + (currentDate.getMonth() + 1)).slice(-2); // 补零并截取后两位
        let day = ("0" + currentDate.getDate()).slice(-2); // 补零并截取后两位
        let timePath = '/' + year + '/' + month + '/' + day;
        return timePath;
    }

    function webUpload() {
        let multiple = '${multiple}';
        let multipleB = false;
        if (multiple == 1) {
            multipleB = true;
        }
        let uploader = WebUploader.create({
            // swf文件路径
            swf: '${ctx.contextPath}/static/plugins/webuploadder/Uploader.swf',
            // 文件接收服务端 。
            server: '${ctx.contextPath}/admin/upload/fileUpload',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {
                id: '#picker',
                label: '点击选择文件',
                multiple: multipleB
            },
            formData: {
                'uuid': uuid// 用作上传站点标识
            },
            fileNumLimit: '${maxnum}',
            // 是否分片上传
            chunked: true,
            fileSingleSizeLimit: allMaxSize * 1024 * 1024,
            timeout: 0,
            accept: {// 限定文件格式
                title: 'intoTypes',
                extensions: '${extensions}',
                mimeTypes: '${mimeTypes}'
            },
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false
        });
        // 开始上传
        $("#ctlBtn").on('click', function () {
            uploader.upload();
        });
        // 当有文件被添加进队列的时候
        uploader.on('fileQueued', function (file) {
            $("#uploadstatutable").append(getHtm(file.name, file.size, file.id));

            $("#caozuo" + file.id).click(function () {
                uploader.removeFile(file.id, true);
                $("#row" + file.id).remove();
            });
            uploader.upload(file);// 直接上传文件
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            let $li = $('#bb' + file.id);
            let $litext = $('#cc' + file.id);
            $litext.text('上传中');
            $li.text((percentage * 100 - 1).toFixed(2) + '%');
        });
        // 单个文件上传成功时
        uploader.on('uploadSuccess', function (file, response) {
            let $li = $('#bb' + file.id);
            let $litext = $('#cc' + file.id);
            $litext.text('上传中');
            $li.text('99%');// 上传完成后，更新为99%，因为还未合并
            merge(file, response);
        });

        // 文件按上传完成以后，触发合并操作
        function merge(file, response) {
            let chunks_ = response.data.chunks;
            let suffix_ = response.data.suffix;
            let uuid_ = response.data.uuid;
            let fileid_ = response.data.fileid;
            // console.log(file.id + "," + fileid_);
            setTimeout(function () {// 100毫秒后，发起合并请求
                $.ajax({
                    url: '${ctx.contextPath}/admin/upload/fileMerge',
                    cache: false,
                    async: false,
                    type: "post",
                    data: {
                        "chunks": chunks_,
                        "suffix": suffix_,
                        "uuid": uuid_,
                        "fileid": fileid_
                    },
                    success: function (data) {
                        // ajax返回后，再标记其完成
                        let $li = $('#bb' + file.id);
                        let $litext = $('#cc' + file.id);
                        $litext.text('已完成');
                        $li.text('100%');
                    }
                });
            }, 100);
        };
        // 单个文件上传出错时
        uploader.on('uploadError', function (file, reason) {
            alert('上传出错，原因：' + reason);
        });
        // 所有文件上传完成时触发
        uploader.on('uploadFinished', function (file) {
            alert('上传完成，请点击保存');
        });
        uploader.on('error', function (type) {
            if (type == 'F_DUPLICATE') {
                alert('请不要重复选择文件！');
            } else if (type == 'F_EXCEED_SIZE') {
                alert('总大小不可超过' + allMaxSize + 'Mb');
            } else if (type == 'Q_TYPE_DENIED') {
                alert('请选择指定类型文件');
            } else if (type == 'Q_EXCEED_NUM_LIMIT') {
                alert('最多上传${maxnum}个文件，请先删除多余文件');
            } else {
                alert(type);
            }
        });
    }

    function getHtm(filename, filesize, fileid) {
        let htm = '';
        htm += '<tr class="upfiletable" id="row' + fileid + '" data-id="' + fileid + '">';
        htm += '<td id="aa' + fileid + '" data-size="' + filesize + '">' + filename + '</td>';
        htm += '<td class="progressAF" id="bb' + fileid + '">0%</td>';
        htm += '<td class="progresstextAF" id="cc' + fileid + '">等待</td>';
        htm += '<td id="ee' + fileid + '">';
        htm += '<div class="btn-group"><a id="caozuo' + fileid + '" class="btn btn-primary" href="#!" title="删除" data-toggle="tooltip">删除</a></div>';
        htm += '</td>';
        htm += '</tr>';
        return htm;
    }

    function assignment(timePath) {
        let jsonarray = [];
        $(".upfiletable").each(function () {
            let fileid = $(this).data("id");
            let fileidlo = fileid.toLowerCase();
            let cjson = {};
            let zipname = $("#aa" + fileid).text();
            let zipsize = $("#aa" + fileid).data('size');
            cjson.name = zipname;
            cjson.size = zipsize;
            cjson.urlTemp = '/temp' + '/' + uuid + '/' + fileidlo + zipname.substring(zipname.lastIndexOf("."));
            cjson.url = '/fileinfo/def' + timePath + '/' + uuid + '/' + fileidlo + zipname.substring(zipname.lastIndexOf("."));
            jsonarray.push(cjson);
        });
        $("#outjson").val(JSON.stringify(jsonarray));
    }

    function savetoData(timePath) {
        $.ajax({
            url: '${ctx.contextPath}/admin/upload/saveFile',
            cache: false,
            async: false,
            type: "post",
            data: {
                "infos": $("#outjson").val(),
                "timePath": timePath
            },
            success: function (data) {
                if (data.result) {
                    layer.msg("文件保存成功");
                } else {
                    layer.msg("文件保存失败");
                }
            }
        });
    }
</script>
</body>
</html>