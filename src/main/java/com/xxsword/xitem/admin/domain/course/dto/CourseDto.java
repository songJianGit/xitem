package com.xxsword.xitem.admin.domain.course.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.course.entity.Course;
import lombok.Data;

@Data
public class CourseDto {
    private String title;
    private Integer releaseStatus;// 发布状态

    public LambdaQueryWrapper<Course> toQuery() {
        return new LambdaQueryWrapper<Course>()
                .eq(Course::getStatus, 1)
                .like(StringUtils.isNotBlank(title), Course::getTitle, title)
                .eq(releaseStatus != null, Course::getReleaseStatus, releaseStatus)
                .orderByDesc(Course::getSeq, Course::getId);
    }
}
