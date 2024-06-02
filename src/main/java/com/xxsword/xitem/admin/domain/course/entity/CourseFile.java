package com.xxsword.xitem.admin.domain.course.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("课件")
@TableName("t_cus_course_file")
public class CourseFile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 402L;

    @Column(length = 50)
    @ColumnComment("课件分类")
    private String courseFileCategory;

    @Column(length = 100)
    @ColumnComment("课件标题")
    private String title;

    @Column
    @ColumnComment("课件备注")
    private String remarks;

    @Column
    @ColumnComment("课件类型|1:scorm,2:video,3:website,5:office或pdf类型")
    private Integer courseType;// 暂时只做视频类型

}
