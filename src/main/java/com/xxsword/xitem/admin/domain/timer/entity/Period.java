package com.xxsword.xitem.admin.domain.timer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableEngine;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_time_period")
@TableComment("计时段落信息")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Period implements Serializable {

    private static final long serialVersionUID = 501L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Column(length = 50)
    private String id;

    @Column
    @ColumnComment("本段时长(秒)")
    private Integer cost;

    @Column
    @ColumnComment("本段小段时长(秒)")
    private Integer costItem;

    @Column
    @ColumnComment("TimerType的code")
    private Integer obType;

    @Column(length = 50)
    @ColumnComment("TimerType的code的业务id")
    private String obId;

    @Column
    @ColumnComment("开始时间戳")
    private Long startStamp;

    @Column
    @ColumnComment("结束时间戳")
    private Long endStamp;

    @Column(length = 50)
    @ColumnComment("用户id")
    private String userId;

}
