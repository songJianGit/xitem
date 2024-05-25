package com.xxsword.xitem.admin.domain.exam.vo;

import com.xxsword.xitem.admin.domain.exam.entity.QuestionOption;

import java.util.List;

public class QuestionVO {

    private String id;
    private String title;// 题目标题
    private String qclass;// 题目分类（字典id）
    private String qclassname;
    private Integer qtype;// 题目类型(0-是非 1-单选 2-多选)
    private Double score;// 题目分值
    private String userpaperquestionid;// 用户试题t_ex_user_paper_question表的主键id
    private List<QuestionOptionVO> questionOptionList;
    private String answer;// 答案（示例数据：C）

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

    public String getQclass() {
        return qclass;
    }

    public void setQclass(String qclass) {
        this.qclass = qclass;
    }

    public String getQclassname() {
        return qclassname;
    }

    public void setQclassname(String qclassname) {
        this.qclassname = qclassname;
    }

    public Integer getQtype() {
        return qtype;
    }

    public void setQtype(Integer qtype) {
        this.qtype = qtype;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getUserpaperquestionid() {
        return userpaperquestionid;
    }

    public void setUserpaperquestionid(String userpaperquestionid) {
        this.userpaperquestionid = userpaperquestionid;
    }

    public List<QuestionOptionVO> getQuestionOptionList() {
        return questionOptionList;
    }

    public void setQuestionOptionList(List<QuestionOptionVO> questionOptionList) {
        this.questionOptionList = questionOptionList;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
