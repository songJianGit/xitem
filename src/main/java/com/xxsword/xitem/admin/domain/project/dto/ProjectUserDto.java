package com.xxsword.xitem.admin.domain.project.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.model.PageM;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectUserDto extends PageM {
    private String pid;
    private String userId;

    public ProjectUserDto() {
    }

    public ProjectUserDto(String pid, String userId) {
        this.pid = pid;
        this.userId = userId;
    }

    public LambdaQueryWrapper<ProjectUser> toQuery() {
        return new LambdaQueryWrapper<ProjectUser>().eq(ProjectUser::getStatus, 1)
                .eq(StringUtils.isNotBlank(pid), ProjectUser::getPid, pid)
                .eq(StringUtils.isNotBlank(userId), ProjectUser::getUserId, userId)
                .orderByAsc(ProjectUser::getCreateDate, ProjectUser::getId);
    }

}
