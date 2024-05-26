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
                                <form action="${ctx.contextPath}/admin/exam/save" method="post" class="row"
                                      onsubmit="return check();">
                                    <input type="hidden" name="id" value="${exam.id!}"/>
                                    <div class="form-group col-6">
                                        <label for="title">考试标题</label>
                                        <input type="text" class="form-control" id="title" name="title" maxlength="100"
                                               value="${exam.title!}" placeholder="考试标题" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="title">最大重考次数（-1表示不限制）</label>
                                        <input type="text" class="form-control" id="title" name="maxnum"
                                               value="${exam.maxnum!}" placeholder="最大重考次数" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="stime">开始时间</label>
                                        <input autocomplete="off" type="text" class="form-control"
                                               onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="stime"
                                               name="stime" value="${exam.stime!}" placeholder="开始时间" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="etime">结束时间</label>
                                        <input autocomplete="off" type="text" class="form-control"
                                               onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="etime"
                                               name="etime" value="${exam.etime!}" placeholder="结束时间" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="duration">考试时长（单位：分钟。-1表示不限制）</label>
                                        <input type="number" class="form-control" id="duration" name="duration"
                                               value="${exam.duration!}" placeholder="考试时长（单位：分钟）" required/>
                                    </div>
                                    <div class="form-group col-6">
                                        <label for="title">试卷</label>
                                        <input type="text" class="form-control" id="papername" value="${exam.papertitle!}" placeholder="试卷" readonly/>
                                        <input type="hidden" value="${exam.paperid!}" name="paperid" id="paperid">
                                    </div>
                                    <div class="form-group col-12">
                                        <label>二维码</label>
                                        <#if exam.id??>
                                            <img src="${ctx.contextPath}/admin/qrcode/get?content=${ctx.contextPath}/pc/exam/${exam.id!}"
                                                 alt="二维码">
                                        <#else>
                                            <div>保存后，自动生成二维码。</div>
                                        </#if>
                                        <a target="_blank" href="${ctx.contextPath}/pc/exam/${exam.id!}">进入</a>
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
    $("#papername").click(function () {
        layer_show("试卷", "${ctx.contextPath}/admin/paper/paperShow");
    });
    function paperCallback(paper){
        $("#paperid").val(paper.id);
        $("#papertitle").val(paper.title);
    }
</script>
</body>
</html>
