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
import com.xxsword.xitem.admin.domain.project.vo.ProjectVO;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.project.ProjectMapper;
import com.xxsword.xitem.admin.mapper.system.UserInfoMapper;
import com.xxsword.xitem.admin.service.project.ProjectService;
import com.xxsword.xitem.admin.service.project.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private UserInfoMapper userInfoMapper;

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
    public List<ProjectVO> setCreateUserName(List<ProjectVO> list) {
        List<String> userIds = list.stream().map(ProjectVO::getCreateUserId).toList();
        List<UserInfo> userInfos = userInfoMapper.selectByIds(userIds);
        Map<String, UserInfo> userInfoMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        for (ProjectVO projectVO : list) {
            UserInfo userInfo = userInfoMap.get(projectVO.getCreateUserId());
            if (userInfo != null) {
                projectVO.setCreateUserName(userInfo.getUserName());
            }
        }
        return list;
    }

    @Override
    @Transactional
    public void saveProject(Project project, JSONArray users, UserInfo userInfo) {
        boolean addFlag = StringUtils.isBlank(project.getId()) || getById(project.getId()) == null;
        saveOrUpdate(project);
        List<ProjectUser> ps = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            JSONObject jsonObject = users.getJSONObject(i);
            String userIdItem = jsonObject.getString("userId");
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
        if (addFlag) {// 新建项目时，需要自动加入新项目
            ProjectUser pu = new ProjectUser();
            pu.setUserId(userInfo.getId());
            pu.setPid(project.getId());
            pu.setReadFlag(1);
            ps.add(pu);
        }
        if (!ps.isEmpty()) {
            projectUserService.saveOrUpdateBatch(ps);
        }

        // 删除未出现的用户
        LambdaQueryWrapper<ProjectUser> del = Wrappers.lambdaQuery();
        del.eq(ProjectUser::getPid, project.getId());
        // 本来想着，创建人不能被剔除，但是后来想想，也许他就是不想看自己创建的项目呢？
//        del.ne(StringUtils.isNotBlank(project.getCreateUserId()), ProjectUser::getUserId, project.getCreateUserId());
        if (!ps.isEmpty()) {
            del.notIn(ProjectUser::getUserId, ps.stream().map(ProjectUser::getUserId).collect(Collectors.toList()));
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
