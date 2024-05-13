package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;

import java.io.Serializable;

@TableComment("答题记录的对应题目信息")
@TableName("t_ex_user_paper_question")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserPaperQuestion extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 208L;
    @Column(length = 50)
    @ColumnComment("用户id")
    private String userid;

    @Index
    @Column(length = 50)
    @ColumnComment("用户答题记录id")
    private String userpaperid;

    @Column(length = 50)
    @ColumnComment("问题id")
    private String qid;

    @Column
    @ColumnComment("用户答案")
    private String answer;

    @Column(length = 19)
    @ColumnComment("答题时间")
    private String answertime;

    @Column(length = 7, decimalLength = 2)
    @ColumnComment("题目分数")
    private Double qscore;

    @Column(length = 7, decimalLength = 2)
    @ColumnComment("用户得分")
    private Double score;

    @Index
    @Column
    @ColumnComment("排序")
    private Integer seq;

    @TableField(exist = false)
    private Question question;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpaperid() {
        return userpaperid;
    }

    public void setUserpaperid(String userpaperid) {
        this.userpaperid = userpaperid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswertime() {
        return answertime;
    }

    public void setAnswertime(String answertime) {
        this.answertime = answertime;
    }

    public Double getQscore() {
        return qscore;
    }

    public void setQscore(Double qscore) {
        this.qscore = qscore;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
