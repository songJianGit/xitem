package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.dto.system.RoleDto;
import com.xxsword.xitem.admin.domain.entity.system.Role;

import java.util.Collection;
import java.util.List;

public interface RoleService extends IService<Role> {
    /**
     * 获取所有可用角色
     *
     * @return
     */
    List<Role> roleAll();

    Page<Role> pageRole(Page<Role> page, RoleDto roleDto);

    /**
     * 获取角色
     *
     * @param roleId
     * @param b      是否获取角色的菜单信息
     * @return
     */
    Role getRoleById(String roleId, boolean b);

    /**
     * 删除角色（逻辑删除）
     *
     * @param roleIds
     */
    void delRoleByIds(String roleIds);

    /**
     * 启用和停用
     *
     * @param roleId
     * @return
     */
    Role upRoleStatus(String roleId);

}
