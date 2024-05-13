package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

@TableComment("试卷表")
@TableName("t_ex_paper")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Paper extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 202L;
    @Column
    @ColumnComment("试卷标题")
    private String title;

    @Column(length = 7, decimalLength = 2)
    @ColumnComment("卷面总分")
    private Double score;

    @TableField(exist = false)
    private List<UserPaperQuestion> userPaperQuestionList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<UserPaperQuestion> getUserPaperQuestionList() {
        return userPaperQuestionList;
    }

    public void setUserPaperQuestionList(List<UserPaperQuestion> userPaperQuestionList) {
        this.userPaperQuestionList = userPaperQuestionList;
    }
}
