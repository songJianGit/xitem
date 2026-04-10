package com.xxsword.xitem.admin.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;


public interface ProjectUserService extends IService<ProjectUser> {

    ProjectUser getProjectUser(String projectId, String userId);

}
