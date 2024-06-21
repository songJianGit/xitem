package com.xxsword.xitem.admin.domain.workorder.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrder;
import lombok.Data;

@Data
public class WorkOrderDto {
    private String title;
    private String userId;// 创建人id
    private Integer workStatus;

    public LambdaQueryWrapper<WorkOrder> toQuery() {
        return new LambdaQueryWrapper<WorkOrder>().eq(WorkOrder::getStatus, 1)
                .like(StringUtils.isNotBlank(title), WorkOrder::getTitle, title)
                .eq(StringUtils.isNotBlank(userId), WorkOrder::getCreateUserId, userId)
                .eq(workStatus != null, WorkOrder::getWorkStatus, workStatus)
                .orderByDesc(WorkOrder::getCreateDate, WorkOrder::getId);
    }
}
