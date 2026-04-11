package com.xxsword.xitem.admin.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.project.dto.ProjectUserDto;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.mapper.project.ProjectUserMapper;
import com.xxsword.xitem.admin.service.project.ProjectUserService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

@Service
public class ProjectUserServiceImpl extends ServiceImpl<ProjectUserMapper, ProjectUser> implements ProjectUserService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public List<ProjectUser> listProjectUser(ProjectUserDto dto) {
        return list(dto.toQuery());
    }

    @Override
    public Map<String, List<ProjectUser>> mapProjectUser(List<String> projectId) {
        LambdaQueryWrapper<ProjectUser> q = Wrappers.lambdaQuery();
        q.in(ProjectUser::getPid, projectId);
        q.eq(ProjectUser::getStatus, 1);
        q.orderByAsc(ProjectUser::getCreateDate, ProjectUser::getId);
        List<ProjectUser> projectUsers = list(q);
        return projectUsers.stream().collect(Collectors.groupingBy(ProjectUser::getPid));
    }

    @Override
    public ProjectUser getProjectUser(String projectId, String userId) {
        LambdaQueryWrapper<ProjectUser> q = Wrappers.lambdaQuery();
        q.eq(ProjectUser::getStatus, 1);
        q.eq(ProjectUser::getUserId, userId);
        q.eq(ProjectUser::getPid, projectId);
        return getOne(q);
    }

    @Override
    public void setProjectUserUserName(List<ProjectUser> projectUsers) {
        List<String> userIds = projectUsers.stream().map(ProjectUser::getUserId).collect(Collectors.toList());
        List<UserInfo> userInfos = userInfoService.listByIds(userIds);
        Map<String, UserInfo> userInfoMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        for (ProjectUser projectUser : projectUsers) {
            UserInfo userInfo = userInfoMap.get(projectUser.getUserId());
            if (userInfo != null) {
                projectUser.setUserName(userInfo.getUserName());
                projectUser.setJobTitle(userInfo.getJobTitle());
            }
        }
    }
}
