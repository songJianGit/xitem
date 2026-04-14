package com.xxsword.xitem.admin.controller;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.constant.RoleSetting;
import com.xxsword.xitem.admin.domain.project.convert.ProjectConvert;
import com.xxsword.xitem.admin.domain.project.dto.ProjectDto;
import com.xxsword.xitem.admin.domain.project.dto.ProjectUserDto;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.domain.project.entity.RoadMap;
import com.xxsword.xitem.admin.domain.project.vo.PUVO;
import com.xxsword.xitem.admin.domain.project.vo.ProjectVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.project.ProjectService;
import com.xxsword.xitem.admin.service.project.ProjectUserService;
import com.xxsword.xitem.admin.service.project.RoadMapService;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/project")
public class ProjectController extends BaseController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoadMapService roadMapService;

    @RequestMapping("data")
    @ResponseBody
    public RestPaging<ProjectVO> data(HttpServletRequest request, ProjectDto dto) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Page<Project> data = projectService.pageProjectBy(dto, userInfo);
        List<ProjectVO> voList = ProjectConvert.INSTANCE.toProjectVO(data.getRecords());
        List<String> projectId = voList.stream().map(ProjectVO::getId).toList();
        Map<String, List<ProjectUser>> listMap = projectUserService.mapProjectUser(projectId);
        for (ProjectVO projectVO : voList) {
            if (listMap.containsKey(projectVO.getId())) {
                List<ProjectUser> users = listMap.get(projectVO.getId());
                projectUserService.setProjectUserUserName(users);
                projectVO.setUsers(users);

                Map<String, ProjectUser> projectUserIds = users.stream().collect(Collectors.toMap(ProjectUser::getUserId, Function.identity()));
                ProjectUser p = projectUserIds.get(userInfo.getId());
                if (p == null) {
                    projectVO.setUserReadFlag(0);
                } else {
                    projectVO.setUserReadFlag(p.getReadFlag());
                }
            }
        }
        projectService.setCreateUserName(voList);
        return new RestPaging<>(data.getTotal(), voList);
    }

    @RequestMapping("list")
    @ResponseBody
    public RestResult<List<Project>> list(HttpServletRequest request, ProjectDto dto) {
        UserInfo userInfo = Utils.getUserInfo(request);
        List<Project> list = projectService.listProjectBy(dto, userInfo);
        return RestResult.OK(list);
    }

    @RequestMapping("projectView")
    public String projectView(HttpServletRequest request, String projectId, Model model) {
        if (StringUtils.isBlank(projectId)) {
            projectId = Utils.getProjectId(request);
        } else {
            request.getSession().setAttribute(Constant.PROJECT_SELECT_ID_KEY, projectId);
        }
        Project project = projectService.getById(projectId);
        List<RoadMap> roadMapList = roadMapService.listRoadMap(projectId);
        roadMapService.setRoadMapPercentage(roadMapList);

        List<PUVO> puvoList = new ArrayList<>();
        List<ProjectUser> projectUserList = projectUserService.listProjectUser(new ProjectUserDto(projectId, null));
        if (!projectUserList.isEmpty()) {
            List<UserInfo> userInfos = userInfoService.listByIds(projectUserList.stream().map(ProjectUser::getUserId).toList());
            Map<String, ProjectUser> projectUserIds = projectUserList.stream().collect(Collectors.toMap(ProjectUser::getUserId, Function.identity()));
            puvoList = projectUserService.listProjectUser(userInfos, projectUserIds, projectId);
        }
        model.addAttribute("project", project);
        model.addAttribute("projectContent", StringEscapeUtils.unescapeHtml4(project.getContent()));
        model.addAttribute("roadMapList", roadMapList);
        model.addAttribute("voList", puvoList);
        return "/admin/project/projectview";
    }

    @RequestMapping("projectIndex")
    public String projectIndex(HttpServletRequest request, Model model) {
        return "/admin/project/projectindex";
    }

    @RequestMapping("edit2")
    public String edit2(HttpServletRequest request, String id, Integer readFlag, Model model) {
        if (readFlag == null) {
            readFlag = 1;
        }
        Project project = projectService.getById(id);
        List<UserInfo> userInfos = userInfoService.listUserInfo();
        Map<String, ProjectUser> projectUserIds = null;
        if (project == null) {
            project = new Project();
        } else {
            List<ProjectUser> projectUserList = projectUserService.list(new ProjectUserDto(id, null).toQuery());// 项目内成员
            projectUserIds = projectUserList.stream().collect(Collectors.toMap(ProjectUser::getUserId, Function.identity()));
        }
        model.addAttribute("voList", projectUserService.listProjectUser(userInfos, projectUserIds, id));
        model.addAttribute("project", project);
        model.addAttribute("readFlag", readFlag);
        return "/admin/project/edit2";
    }


    /**
     * 保存
     */
    @RequestMapping("save")
    @ResponseBody
    public RestResult save(HttpServletRequest request, Project project, String projectuserlists) {
        UserInfo userInfo = Utils.getUserInfo(request);
        JSONArray users = JSONArray.parseArray(StringEscapeUtils.unescapeHtml4(projectuserlists));
        projectService.saveProject(project, users, userInfo);
        return RestResult.OK();
    }

    @RequestMapping("delById")
    @ResponseBody
    public RestResult delById(HttpServletRequest request, String id) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Project project = projectService.getById(id);
        if (RoleSetting.isNotAdmin(userInfo) && !userInfo.getId().equals(project.getCreateUserId())) {
            return RestResult.Fail("不能删除别人的数据");
        }
        projectService.delProject(id);
        return RestResult.OK();
    }
}
