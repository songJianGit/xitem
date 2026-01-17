package com.xxsword.xitem.pc;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.controller.BaseController;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.domain.workorder.convert.WorkOrderConvert;
import com.xxsword.xitem.admin.domain.workorder.dto.WorkOrderItemDto;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrder;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrderItem;
import com.xxsword.xitem.admin.domain.workorder.vo.WorkOrderItemVO;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.service.workorder.WorkOrderItemService;
import com.xxsword.xitem.admin.service.workorder.WorkOrderService;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("pc/workorder")
public class PcWorkOrderController extends BaseController {

    @Autowired
    private WorkOrderService workOrderService;
    @Autowired
    private WorkOrderItemService workOrderItemService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 工单的查看
     */
    @RequestMapping("show")
    public String show(HttpServletRequest request, String id, Model model) {
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
        return "/pc/workorder/show";
    }

    /**
     * 新的工单编辑
     */
    @RequestMapping("edit")
    public String edit(HttpServletRequest request) {
        return "/pc/workorder/edit";
    }

    /**
     * 新的工单保存
     */
    @RequestMapping("save")
    public String save(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file, WorkOrder workOrder, String content) {
        UserInfo userInfo = Utils.getUserInfo(request);
        workOrder.setWorkStatus(0);
        workOrderService.saveOrUpdate(workOrder);

        WorkOrderItem workOrderItem = new WorkOrderItem();
        workOrderItem.setWorkOrderId(workOrder.getId());
        workOrderItem.setContent(content);
        String path = UpLoadUtil.upload(file, "workImg");
        if (StringUtils.isNotBlank(path)) {
            workOrderItem.setFileImg(path);
        }
        workOrderItemService.saveOrUpdate(workOrderItem);
        return "/pc/user/userworkorder";
    }

    /**
     * 继续反馈的保存
     */
    @RequestMapping("saveItem")
    public String saveItem(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file, WorkOrderItem workOrderItem, RedirectAttributes redirectAttributes) {
        UserInfo userInfo = Utils.getUserInfo(request);
        String path = UpLoadUtil.upload(file, "workImg");
        if (StringUtils.isNotBlank(path)) {
            workOrderItem.setFileImg(path);
        }
        workOrderItemService.saveOrUpdate(workOrderItem);
        redirectAttributes.addAttribute("id", workOrderItem.getWorkOrderId());
        return "redirect:show";
    }

}
