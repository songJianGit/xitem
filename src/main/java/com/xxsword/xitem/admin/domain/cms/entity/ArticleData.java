package com.xxsword.xitem.admin.domain.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableEngine;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;

@Data
@TableComment("文章详情表")
@TableName("t_cms_article_data")
@TableEngine(MySqlEngineConstant.InnoDB)
public class ArticleData implements Serializable {

    private static final long serialVersionUID = 201L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ColumnComment("主键id")
    @Column(length = 50)
    private String id;
    @Column(type = MySqlTypeConstant.MEDIUMTEXT)
    @ColumnComment("内容")
    private String content;
    @Column
    @ColumnComment("来源")
    private String copyfrom;
    @Column
    @ColumnComment("相关文章Ids，逗号分隔")
    private String relation;


}