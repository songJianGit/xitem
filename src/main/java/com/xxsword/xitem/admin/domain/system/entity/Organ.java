package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;


@TableComment("机构表")
@TableName("t_sys_organ")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Organ extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -688403103187636353L;

    @Column
    @ColumnComment("机构名称")
    private String name;

    @Column
    @ColumnComment("机构编号")
    private String organnum;

    @Column(length = 50)
    @Index
    @ColumnComment("父级id")
    private String pid;

    @TableField(exist = false)
    private String pname;

    @Column
    @Index
    @ColumnComment("父级id集合")
    private String pids;

    @Column
    @Index
    @ColumnComment("排序")
    private Integer seq;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgannum() {
        return organnum;
    }

    public void setOrgannum(String organnum) {
        this.organnum = organnum;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
