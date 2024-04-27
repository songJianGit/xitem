package com.xxsowrd.xitem.admin.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;


@TableComment("用户角色表")
@TableName("t_sys_user_role")
@TableCharset(MySqlCharsetConstant.UTF8MB4)
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserInfoRole implements Serializable {
    private static final long serialVersionUID = 2268284926154065619L;
    @Column
    @ColumnComment("用户id")
    private String userid;
    @Column
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
