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
@TableComment("里程碑")
@TableName("t_roadmap")
@TableEngine(MySqlEngineConstant.InnoDB)
public class RoadMap extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Column
    @ColumnComment("标题")
    private String title;

    @Column(length = 50)
    @ColumnComment("项目Id")
    private String pid;

    @Column(length = 20)
    @ColumnComment("计划开始时间")
    private String stime;

    @Column(length = 20)
    @ColumnComment("计划结束时间")
    private String etime;

    @TableField(exist = false)
    private String percentage;// 进度描述
}


