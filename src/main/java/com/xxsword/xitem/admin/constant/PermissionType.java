package com.xxsword.xitem.admin.constant;

/**
 * 每张表的数据权限字段是不一样的
 * <p>
 * 比如：在用户表中，查当前及以下，其机构字段应该拿OrganId；在机构表中，查当前及以下，应该是比其表主键id
 */
public enum PermissionType {
    ORGAN(1, "机构表"),
    USERINFO(2, "用户表");

    /**
     * 编码
     */
    private Integer code;
    /**
     * 描述
     */
    private String msg;

    private PermissionType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
