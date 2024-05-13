package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;

@TableComment("用户角色表")
@TableName("t_sys_user_role")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserInfoRole implements Serializable {

    private static final long serialVersionUID = 108L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ColumnComment("主键id")
    @Column(length = 50)
    private String id;

    @Unique(columns = {"userid", "roleid"})
    @Column(length = 50)
    @ColumnComment("用户id")
    private String userid;

    @Column(length = 50)
    @ColumnComment("角色id")
    private String roleid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
}
