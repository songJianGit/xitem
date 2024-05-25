<!DOCTYPE html>
<html>
<head>
    <title>答题卡</title>
    <#include "../commons/head.ftl"/>
    <style>
        .q-item-box {
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;
            gap: 0.5rem;
        }

        .q-item {
            border: 1px solid #6c757d;
            padding: 7px;
            flex-basis: calc(20% - 0.5rem);
            text-align: center;
            margin-bottom: 0.5rem;
            border-radius: 3px;
            cursor: pointer;
        }

        .q-color {
            margin-top: 1rem;
            display: flex;
            justify-content: center;
            gap: 1.5rem;
        }

        .q-color-item {
            display: flex;
            align-items: center;
            gap: 0.6rem;
        }
    </style>
</head>
<body>
<div class="card" style="border: 0">
    <div class="q-color">
        <div class="q-color-item">
            <div style="background-color: #007bff;width: 20px;height: 20px;border-radius: 3px"></div>
            已答
        </div>
        <div class="q-color-item">
            <div style="background-color: #ffffff;width: 20px;height: 20px;border-radius: 3px;border: 1px solid #6c757d"></div>
            未答
        </div>
    </div>
    <div class="card-body q-item-box">
        <#list userPaperQuestionList as item>
            <#if item.answer?? && item.answer!=''>
                <div class="q-item" style="background-color: #007bff;color: #fff"
                     data-num="${item_index+1}">${item_index+1}</div>
            <#else>
                <div class="q-item" data-num="${item_index+1}">${item_index+1}</div>
            </#if>
        </#list>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    $(".q-item").click(function () {
        parent.examSheetCallback($(this).data('num'));
        layer_close();
    });
</script>
</body>
</html>
