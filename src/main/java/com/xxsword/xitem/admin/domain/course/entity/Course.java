package com.xxsword.xitem.admin.domain.course.entity;

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
@TableComment("课程")
@TableName("t_cus_course")
public class Course extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 401L;

    @Column(length = 50)
    @ColumnComment("课程分类")
    private String courseCategory;

    @Index
    @Column(length = 100)
    @ColumnComment("标题")
    private String title;

    @Column
    @ColumnComment("简介")
    private String content;

    @Column
    @ColumnComment("课程时长（分钟）")
    private Integer learnTime;

    @Column
    @ColumnComment("课程封面")
    private String cover;

    @Column
    @ColumnComment("排序字段")
    private Long seq;

    @Index
    @Column
    @ColumnComment("发布状态（0-初始 1-发布 2-下架）")
    private Integer releaseStatus;

    @Column
    @ColumnComment("课件资源id")
    private String courseFileId;

}