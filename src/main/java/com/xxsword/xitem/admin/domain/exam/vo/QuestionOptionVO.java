package com.xxsword.xitem.admin.domain.exam.vo;

public class QuestionOptionVO {
    private String id;// 主键id
    private String qid;// 题目id
    private String title;// 选项标题

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
