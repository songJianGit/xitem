<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../../commons/head.ftl"/>
    <style>
        .paper-box {
            padding: 7px;
        }

        .paper-title {
            font-size: 19px;
            text-align: center;
            padding: 9px 0;
            border-bottom: 1px solid #4d525912;
            margin-bottom: 7px;
        }

        .q-item-box:not(:last-child) {
            border-bottom: 1px solid #4d525912;
            padding-bottom: 3px;
        }

        .q-item-title-box {
            margin-top: 7px;
        }

        .q-item-title {
            display: inline;
            margin-left: 3px;
            font-size: 15px;
            font-weight: bold;
        }

        .q-item-score {
            float: right;
        }

        .q-item-op-box {
            display: flex;
            flex-direction: column;
            margin-top: 7px;
            margin-bottom: 7px;
            margin-left: 7px;
        }

        .q-item-op {
            position: relative;
            margin: 3px 0;
        }

        .q-item-op-ipt {
            position: absolute;
            top: 6px;
        }

        .q-item-op-label {
            display: inline;
            padding-left: 23px;
        }
    </style>
</head>
<body style="background-color: #fff">
<div class="callout callout-success">
    <div>总题数：${paperVO.snum!}&nbsp;题</div>
    <div>当前总分：${paperVO.score!}&nbsp;分</div>
</div>
<div class="paper-box">
    <div class="paper-title">${paperVO.title!}</div>
    <#list paperVO.questionVOList as item>
        <div class="q-item-box">
            <div class="q-item-title-box">
                <label class="q-item-title">${item_index+1}.&nbsp;${item.title!}</label>
                <label class="q-item-score">【${item.score!}分】</label>
            </div>
            <div class="q-item-op-box">
                <#list item.questionOptionList as option>
                    <div class="q-item-op">
                        <#if item.qtype==0 || item.qtype==1>
                            <input class="q-item-op-ipt" id="lable-i${option.id!}"
                                   name="q-item-op-i${item.id!}" value="${option.id!}" type="radio"/>
                        </#if>
                        <#if item.qtype==2>
                            <input class="q-item-op-ipt" id="lable-i${option.id!}"
                                   name="q-item-op-i${item.id!}" value="${option.id!}" type="checkbox"/>
                        </#if>
                        <label class="q-item-op-label" for="lable-i${option.id!}">
                            ${option.title!}
                        </label>
                    </div>
                </#list>
            </div>
            <div class="q-item-op-answer">
                答案：${item.answer!}
            </div>
        </div>
    </#list>
</div>
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
