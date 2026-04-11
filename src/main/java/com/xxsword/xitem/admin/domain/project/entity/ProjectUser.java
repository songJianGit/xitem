package com.xxsword.xitem.admin.domain.project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("项目用户关联表")
@TableName("t_project_user")
@TableEngine(MySqlEngineConstant.InnoDB)
public class ProjectUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Unique(columns = {"pid", "user_id"})
    @Column
    @ColumnComment("项目Id")
    private String pid;

    @Column(length = 50)
    @ColumnComment("用户Id")
    private String userId;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String jobTitle;

    @Column(defaultValue = "1")
    @ColumnComment("用户权限（ 1-正常 0-只读）")
    private Integer readFlag;
}


