package com.xxsword.xitem.admin.domain.dto.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.entity.system.Role;
import com.xxsword.xitem.admin.domain.entity.system.UserInfo;

public class RoleDto {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LambdaQueryWrapper<Role> toQuery() {
        return new LambdaQueryWrapper<Role>().eq(Role::getStatus, 1)
                .eq(StringUtils.isNotBlank(name), Role::getName, name)
                .orderByDesc(Role::getCdate, Role::getId);
    }
}
