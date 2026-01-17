package com.xxsword.xitem.admin.service.workorder;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrder;

public interface WorkOrderService extends IService<WorkOrder> {

    void delByIds(String ids);

}
