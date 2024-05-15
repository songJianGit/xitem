package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.dto.UserInfoRoleDto;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.UserInfoRole;
import com.xxsword.xitem.admin.mapper.system.UserInfoRoleMapper;
import com.xxsword.xitem.admin.domain.system.vo.UserInfoRoleVO;
import com.xxsword.xitem.admin.service.system.RoleService;
import com.xxsword.xitem.admin.service.system.UserInfoRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInfoRoleServiceImpl extends ServiceImpl<UserInfoRoleMapper, UserInfoRole> implements UserInfoRoleService {

    @Autowired
    private RoleService roleService;

    @Override
    public List<Role> listRoleByUserId(String userId) {
        LambdaQueryWrapper<UserInfoRole> qUserRole = Wrappers.lambdaQuery();
        qUserRole.eq(UserInfoRole::getUserid, userId);
        List<UserInfoRole> userInfoRole = list(qUserRole);
        if (userInfoRole.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> roleIds = userInfoRole.stream().map(UserInfoRole::getRoleid).collect(Collectors.toList());// 角色ids
        LambdaQueryWrapper<Role> qRole = Wrappers.lambdaQuery();
        qRole.in(Role::getId, roleIds);
        qRole.eq(Role::getStatus, 1);
        return roleService.list(qRole);// 角色
    }

    @Override
    public void userSplitRole(String urIds) {
        String[] ids = urIds.split(",");
        LambdaQueryWrapper<UserInfoRole> query = Wrappers.lambdaQuery();
        query.in(UserInfoRole::getId, ids);
        remove(query);
    }

    @Override
    public void userLinkRole(String roleId, String userIds) {
        String[] ids = userIds.split(",");
        List<UserInfoRole> list = new ArrayList<>();
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
            list.add(ur);
        }
        saveBatch(list);
    }

    @Override
    public Page<UserInfoRoleVO> queryUserListByRole(Page<UserInfoRole> page, UserInfoRoleDto dto) {
        return baseMapper.pageUserBuRoleId(page, dto);
    }
}
