package com.xxsword.xitem.admin.service.workorder.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrderItem;
import com.xxsword.xitem.admin.mapper.workorder.WorkOrderItemMapper;
import com.xxsword.xitem.admin.service.workorder.WorkOrderItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkOrderItemServiceImpl extends ServiceImpl<WorkOrderItemMapper, WorkOrderItem> implements WorkOrderItemService {

    @Override
    public void delByIds(String ids) {
        String[] idsa = ids.split(",");
        List<WorkOrderItem> workOrderUps = new ArrayList<>();
        for (String id : idsa) {
            WorkOrderItem workOrderUp = new WorkOrderItem();
            workOrderUp.setId(id);
            workOrderUp.setStatus(0);
            workOrderUps.add(workOrderUp);
        }
        updateBatchById(workOrderUps);
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsa = ids.split(",");
        List<WorkOrderItem> listUp = new ArrayList<>();
        for (String id : idsa) {
            WorkOrderItem workOrderUp = new WorkOrderItem();
            workOrderUp.setId(id);
            workOrderUp.setBaseInfo(doUserInfo);
            listUp.add(workOrderUp);
        }
        updateBatchById(listUp);
    }

}
