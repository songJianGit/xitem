package com.xxsword.xitem.admin.domain.course.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("用户学习记录")
@TableName("t_cus_course_user")
public class CourseUser extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 401L;

    @Unique(columns = {"create_user_id", "course_id", "status"})
    @Column(length = 50)
    @ColumnComment("课程id")
    private String courseId;

    @Column
    @ColumnComment("学习总时长（秒）")
    private Integer total;

    @Column
    @ColumnComment("进度（百分比）")
    private Integer precent;

    @Column
    @ColumnComment("precent达到100的时间")
    private String completeTime;

}