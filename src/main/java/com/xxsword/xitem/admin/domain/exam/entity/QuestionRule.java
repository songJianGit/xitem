package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;

import java.io.Serializable;

@TableComment("抽题规则")
@TableName("t_ex_question_rule")
@TableEngine(MySqlEngineConstant.InnoDB)
public class QuestionRule extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 206L;
    @Column(length = 100)
    @ColumnComment("标题")
    private String title;

    @Index
    @Column(length = 50)
    @ColumnComment("试卷id")
    private String paperid;

    @Index
    @Column
    @ColumnComment("排序(将时间用作排序标识)")
    private Long seq;

    @TableField(exist = false)
    private Integer snum;// 该规则的题目总数（查询中间表）

    @Column
    @ColumnComment("抽取多少题")
    private Integer num;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaperid() {
        return paperid;
    }

    public void setPaperid(String paperid) {
        this.paperid = paperid;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Integer getSnum() {
        return snum;
    }

    public void setSnum(Integer snum) {
        this.snum = snum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
