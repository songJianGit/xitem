package com.xxsword.xitem.admin.controller;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.project.convert.ProjectConvert;
import com.xxsword.xitem.admin.domain.project.dto.ProjectDto;
import com.xxsword.xitem.admin.domain.project.dto.ProjectUserDto;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.domain.project.vo.PUVO;
import com.xxsword.xitem.admin.domain.project.vo.ProjectVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.project.ProjectService;
import com.xxsword.xitem.admin.service.project.ProjectUserService;
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
            }
        }
        return new RestPaging<>(data.getTotal(), voList);
    }

    @RequestMapping("list")
    @ResponseBody
    public RestResult<List<Project>> list(HttpServletRequest request, ProjectDto dto) {
        UserInfo userInfo = Utils.getUserInfo(request);
        List<Project> list = projectService.listProjectBy(dto, userInfo);
        return RestResult.OK(list);
    }

    @RequestMapping("projectIndex")
    public String projectIndex(HttpServletRequest request, Model model) {
        return "/admin/project/projectindex";
    }

//    @RequestMapping("show")
//    public String show(HttpServletRequest request, String id, Model model) {
//        Project project = projectService.getById(id);
//        if (project == null) {
//            project = new Project();
//        }
//        model.addAttribute("project", project);
//        request.getSession().setAttribute(Constant.PROJECT_SELECT_KEY, id);
//        return "/admin/project/show";
//    }

    /**
     * 编辑
     */
    @RequestMapping("edit2")
    public String edit2(String id, Integer showFlag, Model model) {
        Project project = projectService.getById(id);
        List<UserInfo> userInfos = userInfoService.listUserInfo();
        Map<String, ProjectUser> projectUserIds = null;
        if (project == null) {
            project = new Project();
        } else {
            List<ProjectUser> projectUserList = projectUserService.list(new ProjectUserDto(id, null).toQuery());// 项目内成员
            projectUserIds = projectUserList.stream().collect(Collectors.toMap(ProjectUser::getUserId, Function.identity()));
        }
        List<PUVO> voList = new ArrayList<>();
        for (UserInfo user : userInfos) {
            PUVO projectUserVO = new PUVO();
            projectUserVO.setId(user.getId());
            projectUserVO.setUserId(user.getId());
            projectUserVO.setUserNameFast(user.getUserName().substring(0, 1));
            projectUserVO.setUserName(user.getUserName());
            projectUserVO.setAvatar(user.getAvatar());
            projectUserVO.setPid(id);
            projectUserVO.setJobTitle(user.getJobTitle());
            if (projectUserIds == null || !projectUserIds.containsKey(user.getId())) {
                projectUserVO.setReadFlag(1);
                projectUserVO.setJoinFlag(false);
            } else {
                ProjectUser projectUser = projectUserIds.get(user.getId());
                projectUserVO.setReadFlag(projectUser.getReadFlag());
                projectUserVO.setJoinFlag(projectUserIds.containsKey(user.getId()));
            }
            voList.add(projectUserVO);
        }
        model.addAttribute("voList", voList);
        model.addAttribute("project", project);
        model.addAttribute("showFlag", showFlag);
        return "/admin/project/edit2";
    }


    /**
     * 保存
     */
    @RequestMapping("save")
    public String save(HttpServletRequest request, Project project, String projectuserlists) {
        JSONArray users = JSONArray.parseArray(StringEscapeUtils.unescapeHtml4(projectuserlists));
        projectService.saveProject(project, users);
        return httpRedirect(request, "/admin/project/list");
    }
}
