package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.dto.system.RoleDto;
import com.xxsword.xitem.admin.domain.entity.system.Functions;
import com.xxsword.xitem.admin.domain.entity.system.Role;
import com.xxsword.xitem.admin.mapper.system.RoleMapper;
import com.xxsword.xitem.admin.service.system.RoleFunctionsService;
import com.xxsword.xitem.admin.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleFunctionsService roleFunctionsService;

    @Override
    public List<Role> roleAll() {
        LambdaQueryWrapper<Role> query = Wrappers.lambdaQuery();
        query.eq(Role::getStatus, 1);
        query.orderByDesc(Role::getCdate, Role::getId);
        return list(query);
    }

    @Override
    public Page<Role> pageRole(Page<Role> page, RoleDto roleDto) {
        LambdaQueryWrapper<Role> query = roleDto.toQuery();
        return page(page, query);
    }

    @Override
    public Role getRoleById(String roleId, boolean b) {
        Role role = getById(roleId);
        if (b) {
            List<Functions> functionsList = roleFunctionsService.listFunctionsByRoleId(role.getId());
            role.setFunctionlist(functionsList);
        }
        return role;
    }

    @Override
    public void delRoleById(String roleId) {
        Role role = getById(roleId);
        if (role != null) {
            role.setStatus(0);
            updateById(role);
        }
    }

    @Override
    public void delRoleByIds(String[] roleIds) {
        for (String id : roleIds) {
            delRoleById(id);
        }
    }


    @Override
    public Role upRoleStatus(String roleId) {
        Role role = getById(roleId);
        if (role.getStatus() == null || role.getStatus() == 1) {
            role.setStatus(2);
        } else {
            role.setStatus(1);
        }
        updateById(role);
        return role;
    }

}
