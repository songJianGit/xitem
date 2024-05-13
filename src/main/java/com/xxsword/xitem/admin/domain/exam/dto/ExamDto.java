package com.xxsword.xitem.admin.domain.exam.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;

public class ExamDto {

    // 考试标题
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LambdaQueryWrapper<Exam> toQuery() {
        return new LambdaQueryWrapper<Exam>().eq(Exam::getStatus, 1)
                .like(StringUtils.isNotBlank(title), Exam::getTitle, title)
                .orderByDesc(Exam::getCdate, Exam::getId);
    }
}
