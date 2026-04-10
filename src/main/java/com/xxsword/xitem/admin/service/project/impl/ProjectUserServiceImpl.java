package com.xxsword.xitem.admin.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.mapper.project.ProjectUserMapper;
import com.xxsword.xitem.admin.service.project.ProjectUserService;
import org.springframework.stereotype.Service;

@Service
public class ProjectUserServiceImpl extends ServiceImpl<ProjectUserMapper, ProjectUser> implements ProjectUserService {

    @Override
    public ProjectUser getProjectUser(String projectId, String userId) {
        LambdaQueryWrapper<ProjectUser> q = Wrappers.lambdaQuery();
        q.eq(ProjectUser::getPid, projectId);
        q.eq(ProjectUser::getUserId, userId);
        return getOne(q);
    }
}
