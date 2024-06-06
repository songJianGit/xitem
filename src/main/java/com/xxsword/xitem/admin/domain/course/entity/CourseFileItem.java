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
@TableComment("课件资源清单")
@TableName("t_cus_course_file_item")
public class CourseFileItem extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 403L;

    @Index(columns = {"course_file_id", "seq"})
    @Column(length = 50)
    @ColumnComment("课件id")
    private String courseFileId;

    @Column
    @ColumnComment("资源路径")
    private String filePath;

    @Column
    @ColumnComment("排序")
    private Integer seq;

}
