package com.xxsword.xitem.admin.domain.workorder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
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

    @Column(length = 100)
    @ColumnComment("工单标题")
    private String title;

    @Column
    @ColumnComment("工单状态(0-未关闭 1-已关闭)")
    private Integer workStatus;
}
