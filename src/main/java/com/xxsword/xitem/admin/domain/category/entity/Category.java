package com.xxsword.xitem.admin.domain.category.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("公用分类表")
@TableName("t_cat_category")
public class Category extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 600L;

    @Column(length = 100)
    @ColumnComment("标题")
    private String title;

    @Column(length = 50)
    @Index
    @ColumnComment("父级id")
    private String pid;

    @TableField(exist = false)
    private String ptitle;

    @Column
    @Index
    @ColumnComment("父级id集合")
    private String pids;

    @Column
    @Index
    @ColumnComment("排序(时间戳)")
    private Long seq;
}
