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
                                <form action="${ctx.contextPath}/admin/coursefile/save" method="post" class="row"
                                      enctype="multipart/form-data"
                                      onsubmit="return check();">
                                    <input type="hidden" name="id" value="${courseFile.id!}"/>

                                    <div class="form-group col-6">
                                        <label for="title">课件标题</label>
                                        <input id="title" maxlength="100" class="form-control" name="title" required
                                               value="${courseFile.title!}"/>
                                    </div>

                                    <div class="form-group col-6">
                                        <label>课件类型</label>
                                        <select class="form-control" name="courseType">
                                            <#-- <option value="1" <#if courseFile.courseType??><#if courseFile.courseType==1>selected</#if></#if> >scorm</option>-->
                                            <option value="2"
                                                    <#if courseFile.courseType??><#if courseFile.courseType==2>selected</#if></#if> >
                                                视频
                                            </option>
                                            <#-- <option value="3" <#if courseFile.courseType??><#if courseFile.courseType==3>selected</#if></#if> >链接</option>-->
                                            <#-- <option value="5" <#if courseFile.courseType??><#if courseFile.courseType==5>selected</#if></#if> >pdf</option>-->
                                        </select>
                                    </div>

                                    <div class="form-group col-12">
                                        <label for="remarks">课件备注</label>
                                        <textarea id="remarks" maxlength="255" class="form-control"
                                                  name="remarks">${courseFile.remarks!}</textarea>
                                    </div>

                                    <div class="form-group col-12">
                                        <button type="button" class="btn btn-primary" id="uploadBtn">上传文件</button>
                                        <input type="hidden" name="fileInfos" id="video-input">
                                        <div>
                                            <video id="video-box" style="width: 600px;height: 400px" controls="controls"
                                                   preload="none" data-setup="{}">
                                                <#if courseFileItem0??>
                                                    <source src="${ctx.contextPath}${courseFileItem0.filePath!}"
                                                            type="video/mp4">
                                                </#if>
                                            </video>
                                        </div>
                                    </div>

                                    <div class="form-group col-12">
                                        <button type="submit" class="btn btn-primary">保 存
                                        </button>
                                        <button type="button" class="btn btn-default"
                                                onclick="javascript:history.back(-1);return false;">返 回
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

    $('#uploadBtn').click(function () {
        layer_show("上传", '${ctx.contextPath}/admin/upload/fileUploadMain?type=mp4', '', '', false, 0);
    });

    function uploadCallback(infos) {
        console.log("uploadCallback1", infos);
        $('#video-box').attr("src", '${ctx.contextPath}' + infos[0].url);
        $("#video-input").val(infos[0].url);
    }

</script>
</body>
</html>
