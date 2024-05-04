package com.xxsword.xitem.admin.domain.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;


@TableComment("用户角色表")
@TableName("t_sys_user_role")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserInfoRole implements Serializable {
    private static final long serialVersionUID = 2268284926154065619L;
    @Unique(columns = {"userid", "roleid"})
    @Column(length = 50)
    @ColumnComment("用户id")
    private String userid;

    @Column(length = 50)
    @ColumnComment("角色id")
    private String roleid;

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
