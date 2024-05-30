package com.xxsword.xitem.admin.domain.exam.vo;

public class UserPaperVO {

    private String id;
    private String cdate;
    private String paperid;
    private String examid;
    private String userid;
    private String username;// 用户名
    private Integer substatus;// 提交状态(0-初始 1-已提交)
    private String subdate;// 提交时间
    private Double score;// 用户总分
    private String duration;// 考试用时（格式：HH:mm:ss）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
