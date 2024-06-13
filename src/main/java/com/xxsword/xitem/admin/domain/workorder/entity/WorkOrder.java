package com.xxsword.xitem.admin.domain.workorder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_work_order")
@TableComment("工单表")
@TableEngine(MySqlEngineConstant.InnoDB)
public class WorkOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 700L;

    @Column
    @ColumnComment("工单标题")
    private String title;

    @Column(type = MySqlTypeConstant.TEXT)
    @ColumnComment("工单内容")
    private String content;

    @Column
    @ColumnComment("工单状态(0-初始 1-处理中 2-完结)")
    private Integer workStatus;
}
