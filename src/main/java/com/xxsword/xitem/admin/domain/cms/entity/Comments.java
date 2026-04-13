package com.xxsword.xitem.admin.domain.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("评论表")
@TableName("t_cms_comments")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Comments extends BaseEntity {

    @Column(type = MySqlTypeConstant.TEXT)
    @ColumnComment("评论内容")
    private String content;

    @Column
    @ColumnComment("评论类型（1-主评论 2-回复）")
    private Integer type;

    @Column(length = 50)
    @ColumnComment("评论类型为回复时，记录主评论id")
    private String comId;

    @Index
    @Column(length = 50)
    @ColumnComment("任务id")
    private String aid;

}
