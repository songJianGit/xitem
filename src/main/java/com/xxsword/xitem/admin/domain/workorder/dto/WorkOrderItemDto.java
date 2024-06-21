package com.xxsword.xitem.admin.domain.workorder.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrderItem;
import lombok.Data;

@Data
public class WorkOrderItemDto {
    private String workOrderId;

    public LambdaQueryWrapper<WorkOrderItem> toQuery() {
        return new LambdaQueryWrapper<WorkOrderItem>().eq(WorkOrderItem::getStatus, 1)
                .eq(StringUtils.isNotBlank(workOrderId), WorkOrderItem::getWorkOrderId, workOrderId)
                .orderByAsc(WorkOrderItem::getCreateDate, WorkOrderItem::getId);
    }
}
