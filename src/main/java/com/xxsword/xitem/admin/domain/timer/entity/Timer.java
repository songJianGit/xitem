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
@TableName("t_time_timer")
@TableComment("计时信息")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Timer implements Serializable {

    private static final long serialVersionUID = 500L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Column(length = 50)
    private String id;

    @Column
    @ColumnComment("TimerType的code")
    private Integer obType;

    @Column(length = 50)
    @ColumnComment("TimerType的code对应的业务id")
    private String obId;

    @Column
    @ColumnComment("记录总时长（秒）")
    private Integer totalTime;

    @Column(length = 50)
    @ColumnComment("第一次更新的时间")
    private String startTime;

    @Column(length = 50)
    @ColumnComment("最后一次更新时间")
    private String lastTime;

    @Column(length = 50)
    @ColumnComment("用户id")
    private String userId;

}
