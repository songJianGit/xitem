package com.xxsowrd.xitem.admin.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;

@TableComment("角色菜单多对多表")
@TableName("t_sys_role_functions")
@TableCharset(MySqlCharsetConstant.UTF8MB4)
@TableEngine(MySqlEngineConstant.InnoDB)
public class RoleFunctions implements Serializable {
    private static final long serialVersionUID = -928790075642942659L;
    @Column
    @ColumnComment("角色id")
    private String roleid;
    @Column
    @ColumnComment("菜单id")
    private String funid;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getFunid() {
        return funid;
    }

    public void setFunid(String funid) {
        this.funid = funid;
    }
}
