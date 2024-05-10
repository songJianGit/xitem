package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.entity.Functions;
import com.xxsword.xitem.admin.domain.system.entity.RoleFunctions;

import java.util.List;

public interface RoleFunctionsService extends IService<RoleFunctions> {
    /**
     * 获取角色的菜单
     *
     * @param roleId
     * @return
     */
    List<Functions> listFunctionsByRoleId(String roleId);

    /**
     * 角色和菜单关联
     *
     * @param roleId
     * @param funIds
     */
    void roleFunctionsSave(String roleId, String funIds);

    /**
     * 获取角色的菜单关联表
     * @param roleId
     * @return
     */
    List<RoleFunctions> listRoleFunctionsByRoleId(String roleId);
}
