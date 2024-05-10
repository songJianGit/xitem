package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.entity.Functions;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface FunctionsService extends IService<Functions> {

    /**
     * 将该pid下的菜单和传入菜单取交集
     *
     * @param pid
     * @param functionsList
     * @return
     */
    List<Functions> functionsRetainAll(String pid, List<Functions> functionsList);

    /**
     * 删除菜单（逻辑删除）
     *
     * @param id
     */
    void delFunctionsById(String id);

    /**
     * 保存菜单排序
     */
    void saveFunctionsSeq(String fids, String seqs);

    /**
     * 刷新菜单最后更新人和最后更新时间
     */
    void upLastInfo(UserInfo doUserInfo, String functionsIds);
}
