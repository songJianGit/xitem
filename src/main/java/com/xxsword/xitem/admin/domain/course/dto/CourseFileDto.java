package com.xxsword.xitem.admin.domain.course.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.course.entity.Course;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import lombok.Data;

@Data
public class CourseFileDto {
    private String title;

    public LambdaQueryWrapper<CourseFile> toQuery() {
        return new LambdaQueryWrapper<CourseFile>()
                .eq(CourseFile::getStatus, 1)
                .like(StringUtils.isNotBlank(title), CourseFile::getTitle, title)
                .orderByDesc(CourseFile::getCreateDate, CourseFile::getId);
    }
}
