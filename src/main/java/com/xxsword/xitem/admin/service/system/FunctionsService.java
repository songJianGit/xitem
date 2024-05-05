package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.entity.system.Functions;

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

}
