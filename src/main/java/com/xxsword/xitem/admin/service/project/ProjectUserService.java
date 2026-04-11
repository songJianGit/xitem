package com.xxsword.xitem.admin.service.project;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.project.dto.ProjectUserDto;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;


public interface ProjectUserService extends IService<ProjectUser> {

    List<ProjectUser> listProjectUser(ProjectUserDto dto);

    Map<String, List<ProjectUser>> mapProjectUser(List<String> projectId);

    ProjectUser getProjectUser(String projectId, String userId);

    void setProjectUserUserName(List<ProjectUser> projectUsers);

}
