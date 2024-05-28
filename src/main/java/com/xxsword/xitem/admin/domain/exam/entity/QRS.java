package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;

import java.io.Serializable;

@TableComment("规则-题目-多对多")
@TableName("t_ex_qrs")
@TableEngine(MySqlEngineConstant.InnoDB)
public class QRS extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 203L;
    @Column(length = 50)
    @Unique(columns = {"qrid", "qid"})
    @ColumnComment("规则id")
    private String qrid;

    @Column(length = 50)
    @ColumnComment("问题id")
    private String qid;

    @Index
    @Column
    @ColumnComment("排序")
    private Integer seq;

    @Column(length = 7, decimalLength = 2)
    @ColumnComment("题目分值")
    private Double score;

    public String getQrid() {
        return qrid;
    }

    public void setQrid(String qrid) {
        this.qrid = qrid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
