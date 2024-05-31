package com.xxsword.xitem.admin.domain.exam.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import lombok.Data;

@Data
public class ExamDto {

    private String title;// 考试标题
    private Integer extype;// 考试类型
    private Integer releasestatus;// 发布状态

    public LambdaQueryWrapper<Exam> toQuery() {
        return new LambdaQueryWrapper<Exam>().eq(Exam::getStatus, 1)
                .like(StringUtils.isNotBlank(title), Exam::getTitle, title)
                .eq(extype != null, Exam::getExtype, extype)
                .eq(releasestatus != null, Exam::getReleasestatus, releasestatus)
                .orderByDesc(Exam::getCdate, Exam::getId);
    }
}
