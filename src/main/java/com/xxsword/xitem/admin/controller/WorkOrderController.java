package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.domain.workorder.convert.WorkOrderConvert;
import com.xxsword.xitem.admin.domain.workorder.dto.WorkOrderDto;
import com.xxsword.xitem.admin.domain.workorder.dto.WorkOrderItemDto;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrder;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrderItem;
import com.xxsword.xitem.admin.domain.workorder.vo.WorkOrderItemVO;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.service.workorder.WorkOrderItemService;
import com.xxsword.xitem.admin.service.workorder.WorkOrderService;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/workorder")
public class WorkOrderController extends BaseController {
    @Autowired
    private WorkOrderService workOrderService;
    @Autowired
    private WorkOrderItemService workOrderItemService;
    @Autowired
    private UserInfoService userInfoService;

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

    @RequestMapping("show")
    public String show(String id, Model model) {
        WorkOrder workOrder = workOrderService.getById(id);

        WorkOrderItemDto workOrderItemDto = new WorkOrderItemDto();
        workOrderItemDto.setWorkOrderId(id);
        List<WorkOrderItem> workOrderItems = workOrderItemService.list(workOrderItemDto.toQuery());
        List<WorkOrderItemVO> workOrderItemVOS = WorkOrderConvert.INSTANCE.toWorkOrderItemVO(workOrderItems)
                .stream()
                .peek(item -> item.setNickName(userInfoService.getById(item.getCreateUserId()).getNickName()))
                .peek(item -> item.setContent(StringEscapeUtils.unescapeHtml4(item.getContent())))
                .collect(Collectors.toList());
        model.addAttribute("workOrder", workOrder);
        model.addAttribute("workOrderItems", workOrderItemVOS);
        return "/admin/workorder/show";
    }

    @RequestMapping("editiframe")
    public String editiframe(HttpServletRequest request) {
        return "/admin/workorder/editiframe";
    }

    /**
     * 回复的保存
     */
    @RequestMapping("saveItem")
    public String saveItem(HttpServletRequest request, WorkOrderItem workOrderItem, RedirectAttributes redirectAttributes) {
        UserInfo userInfo = Utils.getUserInfo(request);
        workOrderItem.setBaseInfo(userInfo);
        workOrderItemService.saveOrUpdate(workOrderItem);
        redirectAttributes.addAttribute("id", workOrderItem.getWorkOrderId());
        return "redirect:show";
    }

//    @RequestMapping("del")
//    @ResponseBody
//    public RestResult del(HttpServletRequest request, String ids) {
//        workOrderService.delByIds(ids);
//        return RestResult.OK();
//    }

    /**
     * 关闭工单
     */
    @RequestMapping("closeWorkOrder")
    @ResponseBody
    public RestResult upWorkStatus(HttpServletRequest request, String id) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(id);
        workOrder.setWorkStatus(1);
        workOrderService.updateById(workOrder);
        return RestResult.OK();
    }

}
