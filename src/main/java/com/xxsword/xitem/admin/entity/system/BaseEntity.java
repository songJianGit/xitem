package com.xxsword.xitem.admin.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;

public abstract class BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ColumnComment("主键id")
    private String id;
    @Column
    @ColumnComment("创建人id")
    private String cuserid;
    @TableField(exist = false)
    private String cusername;// 创建人姓名
    @Column
    @Index
    @ColumnComment("创建机构id")
    private String corganid;
    @Column
    @ColumnComment("更新人id")
    private String upid;
    @Column
    @ColumnComment("更新时间")
    private String udate;
    @Column
    @ColumnComment("创建时间")
    private String cdate;
    @Column
    @Index
    @ColumnComment("删除标记(0-删除 1-可用)")
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuserid() {
        return cuserid;
    }

    public void setCuserid(String cuserid) {
        this.cuserid = cuserid;
    }

    public String getCusername() {
        return cusername;
    }

    public void setCusername(String cusername) {
        this.cusername = cusername;
    }

    public String getCorganid() {
        return corganid;
    }

    public void setCorganid(String corganid) {
        this.corganid = corganid;
    }

    public String getUpid() {
        return upid;
    }

    public void setUpid(String upid) {
        this.upid = upid;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
