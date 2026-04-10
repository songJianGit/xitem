package com.xxsword.xitem.admin.domain.cms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableEngine;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("任务用户关联表")
@TableName("t_cms_article_user")
@TableEngine(MySqlEngineConstant.InnoDB)
public class ArticleUser extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Column(length = 50)
    @ColumnComment("用户ID")
    private String userId;

    @TableField(exist = false)
    private String userName;

    @Column
    @ColumnComment("任务id")
    private String aid;
}


