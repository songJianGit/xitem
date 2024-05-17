<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../../commons/head.ftl"/>
    <style>

        .q-item-box {
            border-bottom: 1px solid #4d525912;
        }

        .q-item-title {
            margin-top: 7px;
        }

        .q-item-score{
            float: right;
        }

        .q-item-op-box {
            display: flex;
            flex-direction: column;
            margin-top: 7px;
            margin-bottom: 7px;
            margin-left: 7px;
        }

        .q-item-op span {
            margin-left: 5px;
        }
    </style>
</head>
<body style="background-color: #fff">
<div class="card" style="-webkit-box-shadow:none;box-shadow:none">
    <div class="card-body">
        <#list questionList as item>
            <div class="q-item-box">
                <div class="q-item-title">${item_index+1}.${item.title!}<label class="q-item-score">【${item.score!}分】</label></div>
                <div class="q-item-op-box">
                    <#list item.questionOptionList as option>
                        <div class="q-item-op">
                            <#if item.qtype==0 || item.qtype==1>
                                <input name="q-item-op-i${item.id!}" value="${option.id!}" type="radio"/>
                            </#if>
                            <#if item.qtype==2>
                                <input name="q-item-op-i${item.id!}" value="${option.id!}" type="checkbox"/>
                            </#if>
                            <span>${option.title}</span>
                        </div>
                    </#list>
                </div>
            </div>
        </#list>
    </div>
</div>
<#include "../../commons/js.ftl"/>
<script type="text/javascript">
</script>
</body>
</html>
