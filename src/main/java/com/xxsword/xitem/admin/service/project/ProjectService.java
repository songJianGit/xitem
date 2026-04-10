package com.xxsword.xitem.admin.service.project;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.project.dto.ProjectDto;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface ProjectService extends IService<Project> {
    List<Project> listProjectBy(ProjectDto projectDto, UserInfo userInfo);

    Page<Project> pageProjectBy(ProjectDto projectDto, UserInfo userInfo);

    void addProject(Project project, String userId);
}
