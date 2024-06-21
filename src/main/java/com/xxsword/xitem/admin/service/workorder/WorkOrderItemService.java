package com.xxsword.xitem.admin.service.workorder;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrderItem;

public interface WorkOrderItemService extends IService<WorkOrderItem> {

    void delByIds(String ids);

    /**
     * 刷新菜单最后更新人和最后更新时间
     */
    void upLastInfo(UserInfo doUserInfo, String ids);

}
