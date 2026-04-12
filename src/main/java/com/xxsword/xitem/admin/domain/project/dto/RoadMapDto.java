package com.xxsword.xitem.admin.domain.project.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.project.entity.RoadMap;
import com.xxsword.xitem.admin.model.PageM;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoadMapDto extends PageM {
    private String title;
    private String projectId;

    public LambdaQueryWrapper<RoadMap> toQuery() {
        return new LambdaQueryWrapper<RoadMap>().eq(RoadMap::getStatus, 1)
                .eq(StringUtils.isNotBlank(projectId), RoadMap::getPid, projectId)
                .like(StringUtils.isNotBlank(title), RoadMap::getTitle, title)
                .orderByDesc(RoadMap::getCreateDate, RoadMap::getId);
    }

}
