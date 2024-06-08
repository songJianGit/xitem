package com.xxsword.xitem.admin.domain.timer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_time_trace")
@TableComment("计时跟踪信息")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Trace implements Serializable {
    public static final String T_START = "start";// 开始
    public static final String T_TRACE = "trace";// 跟踪
    public static final String T_END = "end";// 结束

    private static final long serialVersionUID = 502L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Column(length = 50)
    private String id;

    @Index(columns = {"user_id", "ob_type", "ob_id", "trace_type", "trace_id", "time_stamp"})
    @Column
    @ColumnComment("TimerType的code")
    private Integer obType;

    @Column(length = 50)
    @ColumnComment("TimerType的code对应的业务id")
    private String obId;

    @Column
    @ColumnComment("设备信息（Device的code）")
    private Integer device;

    @Column
    @ColumnComment("时间戳")
    private Long timeStamp;

    @Column(length = 50)
    @ColumnComment("跟踪id")
    private String traceId;

    @Column(length = 50)
    @ColumnComment("用户id")
    private String userId;

    @Column(length = 5)
    @ColumnComment("类型（start trace end）")
    private String traceType;

}
