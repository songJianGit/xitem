package com.xxsword.xitem.admin.domain.timer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_time_trace_0")
@TableComment("计时跟踪信息")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Trace0 implements Serializable {
    private static final long serialVersionUID = 502L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Column(length = 50)
    private String id;

    @Column
    @ColumnComment("设备信息（Device的code）")
    private Integer device;

    @Index
    @Column
    @ColumnComment("时间戳")
    private Long timeStamp;

    @Column(length = 50)
    @ColumnComment("段落id")
    private String periodId;

}
