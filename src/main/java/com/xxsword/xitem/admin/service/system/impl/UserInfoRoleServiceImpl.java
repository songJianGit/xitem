package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.entity.system.Role;
import com.xxsword.xitem.admin.domain.entity.system.UserInfoRole;
import com.xxsword.xitem.admin.mapper.system.UserInfoRoleMapper;
import com.xxsword.xitem.admin.service.system.RoleService;
import com.xxsword.xitem.admin.service.system.UserInfoRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserInfoRoleServiceImpl extends ServiceImpl<UserInfoRoleMapper, UserInfoRole> implements UserInfoRoleService {

    @Autowired
    private RoleService roleService;

    @Override
    public List<Role> listRoleByUserId(String userId) {
        LambdaQueryWrapper<UserInfoRole> qUserRole = Wrappers.lambdaQuery();
        qUserRole.eq(UserInfoRole::getUserid, userId);
        List<UserInfoRole> userInfoRole = list(qUserRole);
        List<String> roleIds = userInfoRole.stream().map(UserInfoRole::getRoleid).collect(Collectors.toList());// 角色ids
        LambdaQueryWrapper<Role> qRole = Wrappers.lambdaQuery();
        qRole.in(Role::getId, roleIds);
        qRole.eq(Role::getStatus, 1);
        return roleService.list(qRole);// 角色
    }

    @Override
    public void userSplitRole(String roleId, String userIds) {
        String[] ids = userIds.split(",");
        LambdaQueryWrapper<UserInfoRole> query = Wrappers.lambdaQuery();
        query.in(UserInfoRole::getUserid, ids);
        query.eq(UserInfoRole::getRoleid, roleId);
        remove(query);
    }

    @Override
    public void userLinkRole(String roleId, String userIds) {
        String[] ids = userIds.split(",");
        for (String id : ids) {
            LambdaQueryWrapper<UserInfoRole> query = Wrappers.lambdaQuery();
            query.eq(UserInfoRole::getUserid, id);
            query.eq(UserInfoRole::getRoleid, roleId);
            long integer = count(query);
            if (integer > 0) {
                continue;
            }
            UserInfoRole ur = new UserInfoRole();
            ur.setRoleid(roleId);
            ur.setUserid(id);
            save(ur);
        }
    }
}
