package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableComment("用户表")
@TableName("t_sys_userinfo")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 107L;
    @Column(length = 50)
    @ColumnComment("用户昵称")
    private String nickName;

    @Column(length = 100)
    @ColumnComment("用户姓名")
    private String userName;

    @Index
    @Unique
    @Column(length = 50)
    @ColumnComment("登录名")
    private String loginName;

    @Column(length = 100)
    @Index
    @ColumnComment("密码")
    private String password;

    @Column(length = 50)
    @ColumnComment("密码错误次数(分钟,错误次数)")
    private String passwordError;

    @Column(length = 100)
    @ColumnComment("邮箱")
    private String email;

    @Unique
    @Column(length = 20)
    @ColumnComment("联系电话")
    private String phoneNo;

    @Column(length = 50)
    @ColumnComment("用户机构")
    private String organId;

    @TableField(exist = false)
    private String organName;

    @Column
    @ColumnComment("权限类型(0-自己创建的数据 1-管辖机构的当前级及以下数据 2-全部数据)")
    private Integer permissionType;

    @Column(type = MySqlTypeConstant.TEXT)
    @ColumnComment("权限")
    private String permissionIds;

    @Index
    @Column(length = 19)
    @ColumnComment("账号有效期(超过该日期后无法登录)")
    private String lifeDate;

    @TableField(exist = false)
    private List<Role> roleList;

}