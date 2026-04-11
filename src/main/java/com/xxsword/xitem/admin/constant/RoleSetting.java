package com.xxsword.xitem.admin.constant;

public enum RoleSetting {

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
}
