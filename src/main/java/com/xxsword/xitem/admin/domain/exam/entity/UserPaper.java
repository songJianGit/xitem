package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户-试卷-多对多
 * 答题记录
 */
@Data
@TableComment("用户-试卷-多对多")
@TableName("t_ex_user_paper")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserPaper extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 207L;
    @Column(length = 50)
    @ColumnComment("试卷id")
    private String paperid;

    @Index
    @Column(length = 50)
    @ColumnComment("考试id")
    private String examid;

    @Index(columns = {"userid", "paperid", "examid", "substatus"})
    @Column(length = 50)
    @ColumnComment("用户id")
    private String userid;

    @Column
    @ColumnComment("提交状态(0-初始 1-已提交)")
    private Integer substatus;

    @Column(length = 19)
    @ColumnComment("提交时间")
    private String subdate;

    @Column(length = 7, decimalLength = 2)
    @ColumnComment("用户总分")
    private Double score;

}
