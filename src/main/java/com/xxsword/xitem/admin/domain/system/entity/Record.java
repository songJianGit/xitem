package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;

@Data
@TableComment("操作日志表")
@TableName("t_sys_record")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Record implements Serializable {
    private static final long serialVersionUID = 104L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    @ColumnComment("用户id")
    private String userId;

    @Column(length = 19)
    @ColumnComment("创建时间")
    private String createDate;

    @Column
    @ColumnComment("访问路径")
    private String doPath;

    @Column
    @ColumnComment("IP地址")
    private String ips;

    @Column(length = 50)
    @ColumnComment("提交方式")
    private String method;

    @Column(type = MySqlTypeConstant.MEDIUMTEXT)
    @ColumnComment("请求内容")
    private String params;

    @Column
    @ColumnComment("游览器信息")
    private String useragent;

    @Column
    @ColumnComment("日志类型(1-操作日志 2-异常日志)")
    private Integer type;

}
