package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.workorder.dto.WorkOrderDto;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrder;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.workorder.WorkOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/workorder")
public class WorkOrderController extends BaseController {
    @Autowired
    private WorkOrderService workOrderService;

    @RequestMapping("list")
    public String list() {
        return "/admin/workorder/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public RestPaging<WorkOrder> listData(HttpServletRequest request, WorkOrderDto workOrderDto, Page<WorkOrder> page) {
        Page<WorkOrder> data = workOrderService.page(page, workOrderDto.toQuery());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        WorkOrder workOrder = new WorkOrder();
        if (StringUtils.isNotBlank(id)) {
            workOrder = workOrderService.getById(id);
            model.addAttribute("content", StringEscapeUtils.unescapeHtml4(workOrder.getContent()));
        }
        model.addAttribute("workOrder", workOrder);
        return "/admin/workorder/edit";
    }

//    @RequestMapping("save")
//    public String save(HttpServletRequest request, WorkOrder workOrder) {
//        UserInfo userInfo = Utils.getUserInfo(request);
//        workOrder.setBaseInfo(userInfo);
//        if (StringUtils.isBlank(workOrder.getId())) {
//            workOrder.setWorkStatus(0);
//        }
//        workOrderService.saveOrUpdate(workOrder);
//        return "redirect:list";
//    }

    @RequestMapping("del")
    @ResponseBody
    public RestResult del(HttpServletRequest request, String ids) {
        workOrderService.delByIds(ids);
        return RestResult.OK();
    }

}
