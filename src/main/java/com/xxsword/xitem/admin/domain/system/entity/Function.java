package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import lombok.Data;

import java.io.Serializable;

@Data
@TableComment("菜单表")
@TableName("t_sys_function")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Function extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 102L;
    @Column(length = 100)
    @ColumnComment("菜单名称")
    private String name;

    @Index
    @Column
    @ColumnComment("链接")
    private String url;

    @Column
    @ColumnComment("菜单标示")
    private String tag;

    @Column(length = 50)
    @ColumnComment("父级id")
    private String pid;

    @Column
    @Index
    @ColumnComment("排序")
    private Integer seq;

    @Column
    @ColumnComment("可见 1-显示 0-隐藏")
    private Integer showFlag;// 是否显示在菜单中，有些菜单只作为权限标识，不显示

    @Column
    @ColumnComment("图标")
    private String icon;

    @Column(defaultValue = "_self", length = 50)
    @ColumnComment("菜单打开方式")
    private String target;

}
