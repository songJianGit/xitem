package com.xxsword.xitem.admin.service.timer;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.timer.entity.Trace;

public interface TraceService extends IService<Trace> {

    /**
     * 删除30天前的Trace数据
     */
    void delTrace();

}
