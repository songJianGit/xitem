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
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="${ctx.contextPath}/fileinfo/def/2024/05/26/xxx.png">
            </div>
            <div class="carousel-item">
                <img src="${ctx.contextPath}/fileinfo/def/2024/05/26/xxx.png">
            </div>
            <div class="carousel-item">
                <img src="${ctx.contextPath}/fileinfo/def/2024/05/26/xxx.png">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-target="#carouselExampleIndicators" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-target="#carouselExampleIndicators" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </button>
    </div>
</div>
<#include "commons/js.ftl"/>
<script type="text/javascript">
    $('.carousel').carousel({
        interval: 3000
    })
</script>
</body>
</html>