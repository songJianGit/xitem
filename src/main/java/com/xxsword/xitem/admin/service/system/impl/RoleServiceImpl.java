package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.dto.RoleDto;
import com.xxsword.xitem.admin.domain.system.entity.Function;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.system.RoleMapper;
import com.xxsword.xitem.admin.service.system.RoleFunctionService;
import com.xxsword.xitem.admin.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleFunctionService roleFunctionService;

    @Override
    public List<Role> roleAll() {
        LambdaQueryWrapper<Role> query = Wrappers.lambdaQuery();
        query.eq(Role::getStatus, 1);
        query.orderByDesc(Role::getCreateDate, Role::getId);
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
            List<Function> functionList = roleFunctionService.listFunctionByRoleId(role.getId());
            role.setFunctionList(functionList);
        }
        return role;
    }

    @Override
    public void delRoleByIds(String roleIds) {
        String[] ids = roleIds.split(",");
        List<Role> listUp = new ArrayList<>();
        for (String id : ids) {
            Role roleUp = new Role();
            roleUp.setId(id);
            roleUp.setStatus(0);
            listUp.add(roleUp);
        }
        updateBatchById(listUp);
    }


    @Override
    public Role upRoleStatus(String roleId) {
        Role role = getById(roleId);
        Role roleUp = new Role();
        roleUp.setId(roleId);
        if (role.getStatus() == null || role.getStatus().equals(1)) {
            roleUp.setStatus(2);
        } else {
            roleUp.setStatus(1);
        }
        updateById(roleUp);
        return role;
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String roleIds) {
        String[] ids = roleIds.split(",");
        List<Role> listUp = new ArrayList<>();
        for (String id : ids) {
            Role roleUp = new Role();
            roleUp.setId(id);
            roleUp.setBaseInfo(doUserInfo);
            listUp.add(roleUp);
        }
        updateBatchById(listUp);
    }

}
