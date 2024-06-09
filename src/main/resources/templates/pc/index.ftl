<!DOCTYPE html>
<html lang="zh">
<head>
    <title>首页</title>
    <#include "commons/head.ftl"/>
    <style>
        .carousel-control-prev, .carousel-control-next {
            background: 0, 0;
            border: 0;
        }

        .carousel-item img {
            width: 100%;
        }
    </style>
</head>
<body>
<div class="pc-main">
    <#include "commons/header.ftl">
    <#if bannerList?size==0>
        暂无数据
        <#else>
            <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <#list bannerList as item>
                        <li data-target="#carouselExampleIndicators" data-slide-to="${item_index}" <#if item_index==0>class="active"</#if>></li>
                    </#list>
                </ol>
                <div class="carousel-inner">
                    <#list bannerList as item>
                        <div class="carousel-item <#if item_index==0>active</#if> ">
                            <img src="${ctx.contextPath}${item.url!'/static/admin/commons/img/defaultimg.webp'}" alt="轮播图">
                        </div>
                    </#list>
                </div>
                <button class="carousel-control-prev" type="button" data-target="#carouselExampleIndicators" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">上一个</span>
                </button>
                <button class="carousel-control-next" type="button" data-target="#carouselExampleIndicators" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">下一个</span>
                </button>
            </div>
    </#if>
</div>
<#include "commons/js.ftl"/>
<script type="text/javascript">
    $('.carousel').carousel({
        interval: 3000
    })
</script>
</body>
</html>
