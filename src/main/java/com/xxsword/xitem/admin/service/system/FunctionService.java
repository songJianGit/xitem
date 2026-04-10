package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.entity.Function;

import java.util.List;

public interface FunctionService extends IService<Function> {

    /**
     * 将该pid下的菜单和传入菜单取交集
     *
     * @param pid
     * @param functionList
     * @return
     */
    List<Function> functionsRetainAll(String pid, List<Function> functionList);

    /**
     * 删除菜单（逻辑删除）
     *
     * @param id
     */
    void delFunctionById(String id);

    /**
     * 保存菜单排序
     */
    void saveFunctionSeq(String fids, String seqs);

}
