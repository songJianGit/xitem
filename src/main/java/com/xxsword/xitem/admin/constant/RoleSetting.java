package com.xxsword.xitem.admin.constant;

import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色配置定义
 */
public enum RoleSetting {

    /**
     * 原本想要定义一个只读角色，但是后来设计是 放到项目里面去控制只读
     */
    ROLE_ADMIN("1", "管理员"),
    ROLE_USER("2042216182987739139", "普通用户");

    /**
     * 角色id
     */
    private String code;
    private String msg;

    private RoleSetting(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static RoleSetting getRoleSettingByCode(String code) {
        if (code == null) {
            return null;
        }
        RoleSetting[] r = RoleSetting.values();
        for (RoleSetting item : r) {
            if (code.equals(item.getCode())) {
                return item;
            }
        }
        return null;
    }

    /**
     * 是否为 管理员
     *
     * @return
     */
    public static boolean isAdmin(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        List<Role> roleList = userInfo.getRoleList();
        if (roleList == null || roleList.isEmpty()) {
            return false;
        }
        return roleList.stream().map(Role::getId).collect(Collectors.toSet()).contains(ROLE_ADMIN.getCode());
    }

    public static boolean isNotAdmin(UserInfo userInfo) {
        return !isAdmin(userInfo);
    }
}
