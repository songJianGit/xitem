package com.xxsword.xitem.admin.domain.cms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("文章表（任务表）")
@TableName("t_cms_article")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Article extends BaseEntity {

    public static final String DEFAULT_TEMPLATE = "frontViewArticle";

    private static final long serialVersionUID = 200L;
    @Column(length = 64)
    @ColumnComment("任务状态")
    private String categoryId;
    @TableField(exist = false)
    private String categoryName;
    @Column
    @ColumnComment("标题")
    private String title;
    @Column(length = 64)
    @ColumnComment("优先级")
    private String levelId;
    @TableField(exist = false)
    private String levelName;

    @Index
    @Column(length = 50)
    @ColumnComment("项目Id")
    private String pid;

    @Column(length = 20)
    @ColumnComment("计划开始时间")
    private String stime;
    @Column(length = 20)
    @ColumnComment("计划结束时间")
    private String etime;


    @TableField(exist = false)
    private ArticleData articleData;    //文章副表
}


