package com.xxsword.xitem.admin.domain.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;

@TableComment("角色菜单多对多表")
@TableName("t_sys_role_functions")
@TableEngine(MySqlEngineConstant.InnoDB)
public class RoleFunctions implements Serializable {
    private static final long serialVersionUID = -928790075642942659L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ColumnComment("主键id")
    @Column(length = 50)
    private String id;

    @Unique(columns = {"roleid", "funid"})
    @Index(columns = {"roleid", "funid"})
    @Column(length = 50)
    @ColumnComment("角色id")
    private String roleid;

    @Column(length = 50)
    @ColumnComment("菜单id")
    private String funid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
