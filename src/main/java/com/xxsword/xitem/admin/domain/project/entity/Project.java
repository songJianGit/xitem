package com.xxsword.xitem.admin.domain.project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableEngine;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("项目表")
@TableName("t_project")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Project extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Column
    @ColumnComment("标题")
    private String title;
    @Column(type = MySqlTypeConstant.TEXT)
    @ColumnComment("描述")
    private String description;
    @Column
    @ColumnComment("项目状态(1-正常 0-关闭)")
    private Integer pstatus;
}


