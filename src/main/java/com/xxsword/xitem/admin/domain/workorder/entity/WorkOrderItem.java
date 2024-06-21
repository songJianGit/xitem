package com.xxsword.xitem.admin.domain.workorder.entity;

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

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_work_order_item")
@TableComment("工单对话记录表")
@TableEngine(MySqlEngineConstant.InnoDB)
public class WorkOrderItem extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 701L;

    @Column(length = 50)
    @ColumnComment("工单id")
    private String workOrderId;

    @Column(type = MySqlTypeConstant.TEXT)
    @ColumnComment("内容")
    private String content;

    @Column
    @ColumnComment("图片附件")
    private String fileImg;

}
