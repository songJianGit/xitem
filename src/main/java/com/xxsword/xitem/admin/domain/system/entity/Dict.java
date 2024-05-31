package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import lombok.Data;

import java.io.Serializable;

@Data
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

}
