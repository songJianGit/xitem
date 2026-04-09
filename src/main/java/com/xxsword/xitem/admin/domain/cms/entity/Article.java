package com.xxsword.xitem.admin.domain.cms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableEngine;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("文章表")
@TableName("t_cms_article")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Article extends BaseEntity {

    public static final String DEFAULT_TEMPLATE = "frontViewArticle";

    private static final long serialVersionUID = 200L;
    @Column(length = 64)
    @ColumnComment("分类编号")
    private String categoryId;
    @TableField(exist = false)
    private String categoryName;
    @Column
    @ColumnComment("标题")
    private String title;
    @Column
    @ColumnComment("关键字")
    private String keywords;
    @Column
    @ColumnComment("描述、摘要")
    private String description;
    @Column
    @ColumnComment("点击数")
    private Integer hits;
    @Column
    @ColumnComment("自定义内容视图")
    private String customContentView;
    @Column(length = 500)
    @ColumnComment("视图参数")
    private String viewConfig;
    @Column
    @ColumnComment("发布状态(1-发布 0-未发布)")
    private Integer pubFlag;
    @TableField(exist = false)
    private ArticleData articleData;    //文章副表
}


