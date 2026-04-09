package com.xxsword.xitem.admin.domain.project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableEngine;
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
    @Column
    @ColumnComment("项目Id")
    private String pid;
    @Column
    @ColumnComment("用户Id")
    private String userId;
}


