package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@TableComment("考试表")
@TableName("t_ex_exam")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Exam extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 201L;
    @Column(length = 100)
    @ColumnComment("考试标题")
    private String title;

    @Column(length = 19)
    @ColumnComment("开始时间")
    private String stime;

    @Column(length = 19)
    @ColumnComment("结束时间")
    private String etime;

    @Column
    @ColumnComment("考试时长（分钟）")
    private Integer duration;// 用户进入考试后，多长时间内需要考完

    @Column
    @ColumnComment("用户的最大考试次数")
    private Integer maxnum;

    @Column(length = 50)
    @ColumnComment("试卷id")
    private String paperid;

    @TableField(exist = false)
    private String papertitle;

    @Column
    @ColumnComment("发布状态（0-初始 1-发布 2-下架）")
    private Integer releasestatus;// 该考试是否显示到前台

    @Column
    @ColumnComment("考试类型（1-公开考试 0-授权考试）")
    private Integer extype;

    /**
     * 考试状态(通过考试的开始和结束时间进行赋值)
     * 0-未开始 1-进行中 2-已结束
     */
    @TableField(exist = false)
    private Integer exstatus;

}
