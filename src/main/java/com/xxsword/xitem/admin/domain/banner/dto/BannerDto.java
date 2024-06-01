package com.xxsword.xitem.admin.domain.banner.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxsword.xitem.admin.domain.banner.entity.Banner;
import lombok.Data;

@Data
public class BannerDto {
    private Integer releaseStatus;// 发布状态

    public LambdaQueryWrapper<Banner> toQuery() {
        return new LambdaQueryWrapper<Banner>()
                .eq(Banner::getStatus, 1)
                .eq(releaseStatus != null, Banner::getReleaseStatus, releaseStatus)
                .orderByDesc(Banner::getSeq, Banner::getId);
    }
}
