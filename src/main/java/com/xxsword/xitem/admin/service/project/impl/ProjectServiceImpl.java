package com.xxsword.xitem.admin.service.project.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import java.util.HashSet;
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
        return page(projectDto.toPage(), qProject);
    }

    @Override
    @Transactional
    public void saveProject(Project project, JSONArray users) {
        saveOrUpdate(project);
        Set<String> hasUserIds = new HashSet<>();
        List<ProjectUser> ps = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            JSONObject jsonObject = users.getJSONObject(i);
            String userIdItem = jsonObject.getString("userId");
            hasUserIds.add(userIdItem);
            Integer readFlagItem = jsonObject.getInteger("readFlag");
            ProjectUser pu = projectUserService.getProjectUser(project.getId(), userIdItem);
            if (pu == null) {
                pu = new ProjectUser();
            }
            pu.setUserId(userIdItem);
            pu.setPid(project.getId());
            pu.setReadFlag(readFlagItem);
            ps.add(pu);
        }
        if (!ps.isEmpty()) {
            projectUserService.saveOrUpdateBatch(ps);
        }

        // 删除未出现的用户
        LambdaQueryWrapper<ProjectUser> del = Wrappers.lambdaQuery();
        del.eq(ProjectUser::getPid, project.getId());
        if (!hasUserIds.isEmpty()) {
            del.notIn(ProjectUser::getUserId, hasUserIds);
        }
        projectUserService.remove(del);
    }

    @Override
    @Transactional
    public void delProject(String id) {
        LambdaUpdateWrapper<Project> up = Wrappers.lambdaUpdate();
        up.eq(Project::getId, id);
        up.eq(Project::getStatus, 1);
        up.set(Project::getStatus, 0);
        update(up);

        LambdaUpdateWrapper<ProjectUser> upUser = Wrappers.lambdaUpdate();
        upUser.eq(ProjectUser::getPid, id);
        upUser.eq(ProjectUser::getStatus, 1);
        upUser.set(ProjectUser::getStatus, 0);
        projectUserService.update(upUser);
    }

    private LambdaQueryWrapper<Project> getQ(ProjectDto projectDto, UserInfo userInfo) {
        if (RoleSetting.isNotAdmin(userInfo)) {
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
