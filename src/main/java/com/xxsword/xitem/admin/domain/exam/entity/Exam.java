package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;

import java.io.Serializable;

@TableComment("考试表")
@TableName("t_ex_exam")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Exam extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 201L;
    @Column
    @ColumnComment("考试标题")
    private String title;

    @Column(length = 19)
    @ColumnComment("开始时间")
    private String stime;

    @Column(length = 19)
    @ColumnComment("结束时间")
    private String etime;

    @Column
    @ColumnComment("考试时长（分钟）")
    private Integer duration;// 用户进入考试后，多长时间内需要考完

    @Column
    @ColumnComment("用户的最大考试次数")
    private Integer maxnum;

    @Column(length = 50)
    @ColumnComment("试卷id")
    private String paperid;

    @TableField(exist = false)
    private String papertitle;

    /**
     * 考试状态(通过考试的开始和结束时间进行赋值)
     * 0-未开始 1-进行中 2-已结束
     */
    @TableField(exist = false)
    private Integer exstatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(Integer maxnum) {
        this.maxnum = maxnum;
    }

    public String getPaperid() {
        return paperid;
    }

    public void setPaperid(String paperid) {
        this.paperid = paperid;
    }

    public String getPapertitle() {
        return papertitle;
    }

    public void setPapertitle(String papertitle) {
        this.papertitle = papertitle;
    }

    public Integer getExstatus() {
        return exstatus;
    }

    public void setExstatus(Integer exstatus) {
        this.exstatus = exstatus;
    }
}
