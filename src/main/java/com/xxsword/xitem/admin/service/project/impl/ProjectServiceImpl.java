package com.xxsword.xitem.admin.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.constant.RoleSetting;
import com.xxsword.xitem.admin.domain.project.dto.ProjectDto;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.project.ProjectMapper;
import com.xxsword.xitem.admin.service.project.ProjectService;
import com.xxsword.xitem.admin.service.project.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private ProjectUserService projectUserService;

    @Override
    public List<Project> listProjectBy(ProjectDto projectDto, UserInfo userInfo) {
        LambdaQueryWrapper<Project> qProject = getQ(projectDto, userInfo);
        if (qProject == null) {
            return new ArrayList<>();
        }
        return list(qProject);
    }

    @Override
    public Page<Project> pageProjectBy(ProjectDto projectDto, UserInfo userInfo) {
        LambdaQueryWrapper<Project> qProject = getQ(projectDto, userInfo);
        if (qProject == null) {
            return new Page<>();
        }
        return page(projectDto.page(), qProject);
    }

    @Override
    @Transactional
    public void addProject(Project project, String userId) {
        if (StringUtils.isBlank(project.getId())) {
            project.setPstatus(1);
        }
        saveOrUpdate(project);
        ProjectUser p = projectUserService.getProjectUser(project.getId(), userId);
        if (p == null) {
            p = new ProjectUser();
            p.setUserId(userId);
            p.setPid(project.getId());
            projectUserService.save(p);
        }
    }

    private LambdaQueryWrapper<Project> getQ(ProjectDto projectDto, UserInfo userInfo) {
        Set<String> roleIds = userInfo.getRoleList().stream().map(Role::getId).collect(Collectors.toSet());
        if (roleIds.contains(RoleSetting.ROLE_USER.getCode()) || roleIds.contains(RoleSetting.ROLE_READ_ONLY.getCode())) {
            LambdaQueryWrapper<ProjectUser> q = Wrappers.lambdaQuery();
            q.eq(ProjectUser::getUserId, userInfo.getId());
            q.eq(ProjectUser::getStatus, 1);

            q.select(ProjectUser::getPid);
            List<ProjectUser> projectUserList = projectUserService.list(q);
            List<String> pids = projectUserList.stream().map(ProjectUser::getPid).toList();
            if (pids.isEmpty()) {
                return null;
            }
            projectDto.setProjectId(pids);
        }
        return projectDto.toQuery();
    }
}
