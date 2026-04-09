package com.xxsword.xitem.admin.domain.project.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.model.PageM;
import lombok.Data;

@Data
public class ProjectDto extends PageM {
    private String title;

    public LambdaQueryWrapper<Project> toQuery() {
        return new LambdaQueryWrapper<Project>().eq(Project::getStatus, 1)
                .like(StringUtils.isNotBlank(title), Project::getTitle, title)
                .orderByDesc(Project::getCreateDate, Project::getId);
    }

}
