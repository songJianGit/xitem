package com.xxsword.xitem.admin.service.workorder;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrderItem;

public interface WorkOrderItemService extends IService<WorkOrderItem> {

    void delByIds(String ids);

}
