package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.project.dto.ProjectDto;
import com.xxsword.xitem.admin.domain.project.dto.ProjectUserDto;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.project.ProjectService;
import com.xxsword.xitem.admin.service.project.ProjectUserService;
import com.xxsword.xitem.admin.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/project")
public class ProjectController extends BaseController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectUserService projectUserService;

    @RequestMapping("data")
    @ResponseBody
    public RestPaging<Project> data(HttpServletRequest request, ProjectDto dto) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Page<Project> data = projectService.pageProjectBy(dto, userInfo);
        return new RestPaging<>(data.getTotal(), data.getRecords());
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
        return "/admin/projectindex";
    }

    @RequestMapping("show")
    public String show(HttpServletRequest request, String id, Model model) {
        Project project = projectService.getById(id);
        if (project == null) {
            project = new Project();
        }
        model.addAttribute("project", project);
        request.getSession().setAttribute(Constant.PROJECT_SELECT_KEY, id);
        return "/admin/project/show";
    }

    /**
     * 编辑
     */
    @RequestMapping("edit2")
    public String edit2(String id, Model model) {
        Project project = projectService.getById(id);
        if (project == null) {
            project = new Project();
        }
        model.addAttribute("project", project);
        return "/admin/project/edit2";
    }


    /**
     * 保存
     */
    @RequestMapping("save")
    public String save(HttpServletRequest request, Project project) {
        UserInfo userInfo = Utils.getUserInfo(request);
        projectService.addProject(project, userInfo.getId());
        return httpRedirect(request, "/admin/project/list");
    }
}
