package com.xxsword.xitem.admin.domain.exam.vo;

public class QuestionExcelVO {

    private String id;
    private String title;// 题目标题
    private String qclass;// 题目分类
    private Integer qtype;// 题目类型
    private String qoption;// 选项
    private String qanswer;// 答案

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

    public Integer getQtype() {
        return qtype;
    }

    public void setQtype(Integer qtype) {
        this.qtype = qtype;
    }

    public String getQoption() {
        return qoption;
    }

    public void setQoption(String qoption) {
        this.qoption = qoption;
    }

    public String getQanswer() {
        return qanswer;
    }

    public void setQanswer(String qanswer) {
        this.qanswer = qanswer;
    }
}
