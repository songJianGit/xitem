package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableEngine;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;

@TableComment("分片方式上传的文件信息记录")
@TableName("t_sys_upload_log")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UploadLog implements Serializable {
    private static final long serialVersionUID = 109L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    @ColumnComment("用户id")
    private String userid;

    @Column(length = 19)
    @ColumnComment("创建时间")
    private String cdate;

    @Column
    @ColumnComment("文件路径")
    private String url;

    @Column
    @ColumnComment("文件名称")
    private String name;

    @Column(length = 20)
    @ColumnComment("文件大小(单位：字节)")
    private Long size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
