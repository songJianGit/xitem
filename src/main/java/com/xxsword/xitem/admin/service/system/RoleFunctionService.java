package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.entity.Function;
import com.xxsword.xitem.admin.domain.system.entity.RoleFunction;

import java.util.List;

public interface RoleFunctionService extends IService<RoleFunction> {
    /**
     * 获取角色的菜单
     *
     * @param roleId
     * @return
     */
    List<Function> listFunctionByRoleId(String roleId);

    /**
     * 角色和菜单关联
     *
     * @param roleId
     * @param funIds
     */
    void roleFunctionSave(String roleId, String funIds);

    /**
     * 获取角色的菜单关联表
     * @param roleId
     * @return
     */
    List<RoleFunction> listRoleFunctionByRoleId(String roleId);
}
