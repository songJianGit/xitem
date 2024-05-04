package com.xxsword.xitem.admin.service.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.entity.system.Functions;

import java.util.List;

public interface FunctionsService extends IService<Functions> {

    Page<Functions> pageFunctions(Page<Functions> page);

    /**
     * 获取所有可用菜单
     *
     * @return
     */
    List<Functions> functionsAll();

    /**
     * 获取其下级id（只取一级）
     *
     * @param pid
     * @return
     */
    List<Functions> functionsByPId(String pid);

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

}
