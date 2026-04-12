package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.project.dto.RoadMapDto;
import com.xxsword.xitem.admin.domain.project.entity.RoadMap;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.project.RoadMapService;
import com.xxsword.xitem.admin.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin/roadmap")
public class RoadMapController extends BaseController {
    @Autowired
    private RoadMapService roadMapService;

    @RequestMapping("list")
    public String list() {
        return "admin/project/roadmaplist";
    }

    @RequestMapping("data")
    @ResponseBody
    @Operation(summary = "项目里程碑", description = "项目里程碑")
    public RestPaging<RoadMap> data(HttpServletRequest request, RoadMapDto roadMapDto) {
        roadMapDto.setProjectId(Utils.getProjectId(request));
        Page<RoadMap> data = roadMapService.page(roadMapDto.toPage(), roadMapDto.toQuery());
        roadMapService.setRoadMapPercentage(data.getRecords());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        RoadMap roadMap = roadMapService.getById(id);
        if (roadMap == null) {
            roadMap = new RoadMap();
        }
        model.addAttribute("roadMap", roadMap);
        return "/admin/project/roadmapedit";
    }

    @RequestMapping("save")
    @ResponseBody
    public RestResult save(HttpServletRequest request, RoadMap roadMap) {
        roadMap.setPid(Utils.getProjectId(request));
        roadMapService.saveOrUpdate(roadMap);
        return RestResult.OK();
    }
}
