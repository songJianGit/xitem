package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.entity.Dict;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

public interface DictService extends IService<Dict> {

    void delDictByIds(String ids);

    /**
     * 刷新菜单最后更新人和最后更新时间
     */
    void upLastInfo(UserInfo doUserInfo, String dictIds);

}
