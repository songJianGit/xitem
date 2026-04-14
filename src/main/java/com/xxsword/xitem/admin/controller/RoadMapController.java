package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.constant.RoleSetting;
import com.xxsword.xitem.admin.domain.project.dto.RoadMapDto;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.domain.project.entity.RoadMap;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.project.ProjectUserService;
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
    @Autowired
    private ProjectUserService projectUserService;

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
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
    public String edit(HttpServletRequest request, String id, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
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

    @RequestMapping("delById")
    @ResponseBody
    public RestResult delById(HttpServletRequest request, String id) {
        UserInfo userInfo = Utils.getUserInfo(request);
        RoadMap roadMap = roadMapService.getById(id);
        if (RoleSetting.isNotAdmin(userInfo) && !userInfo.getId().equals(roadMap.getCreateUserId())) {
            return RestResult.Fail("不能删除别人的数据");
        }
        LambdaUpdateWrapper<RoadMap> up = Wrappers.lambdaUpdate();
        up.eq(RoadMap::getId, id);
        up.set(RoadMap::getStatus, 0);
        roadMapService.update(up);
        return RestResult.OK();
    }
}
