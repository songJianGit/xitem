package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;

import java.io.Serializable;

@TableComment("题目的选项")
@TableName("t_ex_question_option")
@TableEngine(MySqlEngineConstant.InnoDB)
public class QuestionOption extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 205L;

    @Index
    @Column
    @ColumnComment("题目id")
    private String qid;

    @Column
    @ColumnComment("选项标题")
    private String title;

    @Column
    @ColumnComment("是否正确答案(0-否 1-是)")
    private Integer optionright;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOptionright() {
        return optionright;
    }

    public void setOptionright(Integer optionright) {
        this.optionright = optionright;
    }
}
