package com.xxsword.xitem.admin.domain.project.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.model.PageM;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto extends PageM {
    private String title;
    private List<String> projectId;

    public LambdaQueryWrapper<Project> toQuery() {
        return new LambdaQueryWrapper<Project>().eq(Project::getStatus, 1)
                .in(projectId != null && !projectId.isEmpty(), Project::getId, projectId)
                .like(StringUtils.isNotBlank(title), Project::getTitle, title)
                .orderByDesc(Project::getCreateDate, Project::getId);
    }

}
