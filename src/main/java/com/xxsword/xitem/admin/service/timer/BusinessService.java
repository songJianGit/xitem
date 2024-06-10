package com.xxsword.xitem.admin.service.timer;

import com.xxsword.xitem.admin.domain.timer.entity.Period;

public interface BusinessService {

    /**
     * 各业务处理。
     * 根据计时表中的信息，各取所需，去更新各项业务的业务表。
     * 注意：不要将本计时对象中的任何一个对象参杂到业务逻辑中。
     */
    void business(Period period);
}
