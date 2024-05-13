package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;

@TableName("t_sys_dict")
@TableComment("字典表")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Dict extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 101L;
    @Column
    @ColumnComment("名称")
    private String name;

    @Column
    @ColumnComment("值")
    private String val;

    @Index
    @Column
    @ColumnComment("类型")
    private String type;

    @Column
    @ColumnComment("排序")
    private Integer seq;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
