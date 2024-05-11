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
                                <div>
                                    <div style="line-height: 1.3;" class="btn m-r-10" id="picker">选择文件</div>
                                    <a id="subBtn" class="btn btn-primary m-r-5" href="#!" style="margin-top: -5px;">
                                        保存
                                    </a>
                                    <a id="cancelBtn" class="btn btn-default" href="#!" style="margin-top: -5px;">
                                        返回
                                    </a>
                                    <input type="hidden" name="outjson" id="outjson"/>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="uploadstatutable">
                                        <thead>
                                        <tr>
                                            <th>文件名</th>
                                            <th>文件大小</th>
                                            <th>上传进度</th>
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
    let allMaxSize = ${allMaxSize};// 文件总大小限制（单位Mb）
    let itemMaxSize = ${itemMaxSize};// 单个文件大小限制（单位Mb）
    $(function () {
        webUpload();
        subBtn();// 保存事件
        cancelBtn();// 返回事件
    });

    function cancelBtn() {
        $('#cancelBtn').click(function () {
            layer.confirm('文件不会被保存，确定返回？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer_close();
            }, function () {
            });
        });
    }

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
                layer.msg('部分文件可能还在传输中，请稍候...');
                return false;
            }
            assignment();// 收集信息
            savetoData();// 保存到数据库&回调上级页面输出信息
        });
    }

    function webUpload() {
        let multiple = '${multiple}';
        let multipleB = false;
        if (multiple == 1) {
            multipleB = true;
        }
        let uploader = WebUploader.create({
            // swf文件路径
            swf: '${ctx.contextPath}/static/plugins/webuploader/Uploader.swf',
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
            chunked: true,// 是否分片上传
            duplicate: false,// 是否去重， 根据文件名字、文件大小和最后修改时间来生成hash Key
            fileSizeLimit: allMaxSize * 1024 * 1024,// 验证文件总大小是否超出限制, 超出则不允许加入队列
            fileSingleSizeLimit: itemMaxSize * 1024 * 1024,// 验证单个文件大小是否超出限制, 超出则不允许加入队列
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
        uploader.on('uploadFinished', function () {
            layer.msg('数据已上传');// 出现该提示时，表示数据分片全部上传完成了，但是合并可能还未结束
        });
        uploader.on('error', function (type) {
            if (type == 'F_DUPLICATE') {
                alert('请不要重复选择文件！');
            } else if (type == 'F_EXCEED_SIZE') {
                alert('单个文件大小不可超过' + itemMaxSize + 'MB');
            } else if (type == 'Q_EXCEED_SIZE_LIMIT') {
                alert('文件总大小不可超过' + allMaxSize + 'MB');
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
        htm += '<td>' + (filesize / 1024 / 1024).toFixed(2) + ' MB</td>';
        htm += '<td class="progressAF" id="bb' + fileid + '">0%</td>';
        htm += '<td class="progresstextAF" id="cc' + fileid + '">等待</td>';
        htm += '<td id="ee' + fileid + '">';
        htm += '<div class="btn-group"><a id="caozuo' + fileid + '" class="btn btn-primary" href="#!" title="删除" data-toggle="tooltip">删除</a></div>';
        htm += '</td>';
        htm += '</tr>';
        return htm;
    }

    function assignment() {
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
            jsonarray.push(cjson);
        });
        $("#outjson").val(JSON.stringify(jsonarray));
    }

    function savetoData() {
        let index_ = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        $.ajax({
            url: '${ctx.contextPath}/admin/upload/saveFile',
            cache: false,
            type: "post",
            data: {
                "infos": $("#outjson").val()
            },
            success: function (data) {
                layer.close(index_);
                if (data.result) {
                    window.parent.uploadCallback(data.data);// 回调父页面的函数
                    layer_close();
                } else {
                    layer.msg("文件保存失败");
                }
            }
        });
    }
</script>
</body>
</html>