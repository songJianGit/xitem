package com.xxsword.xitem.admin.service.workorder.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrder;
import com.xxsword.xitem.admin.mapper.workorder.WorkOrderMapper;
import com.xxsword.xitem.admin.service.workorder.WorkOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements WorkOrderService {

    @Override
    public void delByIds(String dictIds) {
        String[] ids = dictIds.split(",");
        List<WorkOrder> workOrderUps = new ArrayList<>();
        for (String id : ids) {
            WorkOrder workOrderUp = new WorkOrder();
            workOrderUp.setId(id);
            workOrderUp.setStatus(0);
            workOrderUps.add(workOrderUp);
        }
        updateBatchById(workOrderUps);
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String dictIds) {
        String[] ids = dictIds.split(",");
        List<WorkOrder> listUp = new ArrayList<>();
        for (String id : ids) {
            WorkOrder workOrderUp = new WorkOrder();
            workOrderUp.setId(id);
            workOrderUp.setBaseInfo(doUserInfo);
            listUp.add(workOrderUp);
        }
        updateBatchById(listUp);
    }

}
