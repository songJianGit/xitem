package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;

import java.io.Serializable;

/**
 * 用户-试卷-多对多
 * 答题记录
 */
@TableComment("用户-试卷-多对多")
@TableName("t_ex_user_paper")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserPaper extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 207L;
    @Index
    @Column(length = 50)
    @ColumnComment("试卷id")
    private String paperid;

    @Column(length = 50)
    @ColumnComment("考试id")
    private String examid;

    @Index
    @Column(length = 50)
    @ColumnComment("用户id")
    private String userid;

    @Index
    @Column
    @ColumnComment("提交状态(0-初始 1-答题中 2-已提交)")
    private Integer substatus;

    @Column(length = 19)
    @ColumnComment("提交时间")
    private String subdate;

    @Column(length = 7, decimalLength = 2)
    @ColumnComment("用户总分")
    private Double score;

    public String getPaperid() {
        return paperid;
    }

    public void setPaperid(String paperid) {
        this.paperid = paperid;
    }

    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getSubstatus() {
        return substatus;
    }

    public void setSubstatus(Integer substatus) {
        this.substatus = substatus;
    }

    public String getSubdate() {
        return subdate;
    }

    public void setSubdate(String subdate) {
        this.subdate = subdate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
