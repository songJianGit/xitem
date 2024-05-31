package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import lombok.Data;

import java.io.Serializable;

@Data
@TableComment("机构表")
@TableName("t_sys_organ")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Organ extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 103L;
    @Column(length = 100)
    @ColumnComment("机构名称")
    private String name;

    @Column(length = 100)
    @ColumnComment("机构编号")
    private String organNum;

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

}
