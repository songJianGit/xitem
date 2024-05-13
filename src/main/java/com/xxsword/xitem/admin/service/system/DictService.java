package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.entity.Dict;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;
import java.util.Map;

public interface DictService extends IService<Dict> {

    void delDictByIds(String ids);

    /**
     * 刷新菜单最后更新人和最后更新时间
     */
    void upLastInfo(UserInfo doUserInfo, String dictIds);

    /**
     * 根据字典类型查询字典信息
     *
     * @param type
     * @return
     */
    List<Dict> listDictByType(String type);

    /**
     * 根据字典类型查询字典信息
     *
     * @param type
     * @return Map<字典id, Dict>
     */
    Map<String, Dict> mapDictByType(String type);

}
