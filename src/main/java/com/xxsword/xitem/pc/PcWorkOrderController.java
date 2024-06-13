package com.xxsword.xitem.pc;

import com.xxsword.xitem.admin.controller.BaseController;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrder;
import com.xxsword.xitem.admin.service.workorder.WorkOrderService;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("pc/workorder")
public class PcWorkOrderController extends BaseController {

    @Autowired
    private WorkOrderService workOrderService;

    @RequestMapping("editiframe")
    public String editiframe(HttpServletRequest request, String id, Model model) {
        WorkOrder workOrder = workOrderService.getById(id);
        model.addAttribute("content", StringEscapeUtils.unescapeHtml4(workOrder.getContent()));
        return "/pc/workorder/editiframe";
    }

    @RequestMapping("edit")
    public String edit(HttpServletRequest request, String id, Model model) {
        WorkOrder workOrder = workOrderService.getById(id);
        model.addAttribute("workOrder", workOrder);
        return "/pc/workorder/edit";
    }

    @RequestMapping("save")
    public String save(HttpServletRequest request, WorkOrder workOrder, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        workOrder.setBaseInfo(userInfo);
        workOrderService.saveOrUpdate(workOrder);
        return "/pc/user/userworkorder";
    }

}
