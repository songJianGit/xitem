package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.project.dto.ProjectDto;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.service.project.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin/project")
public class ProjectController extends BaseController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping("list")
    public String list() {
        return "admin/project/list";
    }

    @RequestMapping("data")
    @ResponseBody
    public RestPaging<Project> data(HttpServletRequest request, ProjectDto dto) {
        Page<Project> data = projectService.page(dto.page(), dto.toQuery());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("show")
    public String show(String id, Model model) {
        Project project = projectService.getById(id);
        if (project == null) {
            project = new Project();
        }
        model.addAttribute("project", project);
        return "/admin/project/show";
    }

    /**
     * 编辑
     */
    @RequestMapping("edit")
    public String edit(String id, Model model) {
        Project project = projectService.getById(id);
        if (project == null) {
            project = new Project();
        }
        model.addAttribute("project", project);
        return "/admin/project/edit";
    }


    /**
     * 保存
     */
    @RequestMapping("save")
    public String save(HttpServletRequest request, Project project) {
        projectService.saveOrUpdate(project);
        return httpRedirect(request, "/admin/project/list");
    }
}
