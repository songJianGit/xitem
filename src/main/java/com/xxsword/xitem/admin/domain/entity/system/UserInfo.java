package com.xxsword.xitem.admin.domain.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import java.io.Serializable;
import java.util.List;

@TableComment("用户表")
@TableName("t_sys_userinfo")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1282638492431542107L;
    @Column
    @ColumnComment("用户昵称")
    private String nickname;

    @Column
    @ColumnComment("用户姓名")
    private String username;

    @Column
    @Index
    @Unique
    @ColumnComment("登录名")
    private String loginname;

    @Column
    @Index
    @ColumnComment("密码")
    private String password;

    @Column(length = 50)
    @ColumnComment("密码错误次数(分钟,错误次数)")
    private String passworderror;

    @Column
    @ColumnComment("邮箱")
    private String email;

    @Column(length = 50)
    @ColumnComment("联系电话")
    private String phoneno;

    @Column(length = 50)
    @ColumnComment("用户机构")
    private String organid;

    @TableField(exist = false)
    private String organname;

    @Column
    @ColumnComment("权限类型(0-自己创建的数据 1-管辖机构的当前级及以下数据 2-全部数据)")
    private Integer permissionType;

    @Column(type = MySqlTypeConstant.TEXT)
    @ColumnComment("权限")
    private String permissionids;

    @TableField(exist = false)
    private List<Role> rolelist;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
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

    public List<Role> getRolelist() {
        return rolelist;
    }

    public void setRolelist(List<Role> rolelist) {
        this.rolelist = rolelist;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionids() {
        return permissionids;
    }

    public void setPermissionids(String permissionids) {
        this.permissionids = permissionids;
    }
}