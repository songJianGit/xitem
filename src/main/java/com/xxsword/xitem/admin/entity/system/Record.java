package com.xxsword.xitem.admin.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import java.io.Serializable;

@TableComment("操作日志表")
@TableName("t_sys_record")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Record implements Serializable {
    private static final long serialVersionUID = -5950916385417252236L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    @Column
    @ColumnComment("用户id")
    private String userid;
    @Column
    @ColumnComment("创建时间")
    private String cdate;
    @Column
    @ColumnComment("访问路径")
    private String dopath;
    @Column
    @ColumnComment("IP地址")
    private String ips;
    @Column
    @ColumnComment("提交方式")
    private String method;
    @Column(type = MySqlTypeConstant.MEDIUMTEXT)
    @ColumnComment("请求内容")
    private String params;
    @Column
    @ColumnComment("游览器信息")
    private String useragent;

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

    public String getDopath() {
        return dopath;
    }

    public void setDopath(String dopath) {
        this.dopath = dopath;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }
}
