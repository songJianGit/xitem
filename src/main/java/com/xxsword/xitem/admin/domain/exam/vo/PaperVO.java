package com.xxsword.xitem.admin.domain.exam.vo;

import java.util.List;

public class PaperVO {
    private String id;
    private String title;
    private Double score;// 卷面总分
    private Integer snum;// 总题目数
    private List<QuestionVO> questionVOList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getSnum() {
        return snum;
    }

    public void setSnum(Integer snum) {
        this.snum = snum;
    }

    public List<QuestionVO> getQuestionVOList() {
        return questionVOList;
    }

    public void setQuestionVOList(List<QuestionVO> questionVOList) {
        this.questionVOList = questionVOList;
    }
}
