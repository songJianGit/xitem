package com.xxsword.xitem.admin.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;
import java.util.Set;

@TableComment("用户表")
@TableName("t_sys_userinfo")
@TableCharset(MySqlCharsetConstant.UTF8MB4)
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1282638492431542107L;
    @Column
    @ColumnComment("用户姓名")
    private String username;

    @Column
    @Index
    @ColumnComment("登录名")
    private String loginname;

    @Column
    @Index
    @ColumnComment("密码")
    private String password;

    // 格式(分钟,错误次数): 2024-01-01 10:12:,2
    @Column
    @ColumnComment("密码错误次数")
    private String passworderror;

    @Column
    @ColumnComment("邮箱")
    private String email;

    @Column
    @ColumnComment("联系电话")
    private String iphone;

    @Column
    @ColumnComment("用户机构")
    private String organid;
    @TableField(exist = false)
    private String organname;

    @Column
    @ColumnComment("权限")
    private String permissionsids;

    @TableField(exist = false)
    private Set<Role> rolelist;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassworderror() {
        return passworderror;
    }

    public void setPassworderror(String passworderror) {
        this.passworderror = passworderror;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getOrganid() {
        return organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getOrganname() {
        return organname;
    }

    public void setOrganname(String organname) {
        this.organname = organname;
    }

    public Set<Role> getRolelist() {
        return rolelist;
    }

    public void setRolelist(Set<Role> rolelist) {
        this.rolelist = rolelist;
    }

    public String getPermissionsids() {
        return permissionsids;
    }

    public void setPermissionsids(String permissionsids) {
        this.permissionsids = permissionsids;
    }
}