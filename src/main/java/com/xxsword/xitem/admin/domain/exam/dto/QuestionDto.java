package com.xxsword.xitem.admin.domain.exam.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.exam.entity.Question;
import lombok.Data;

@Data
public class QuestionDto {

    private String title;// 标题
    private String qclass;// 分类
    private Integer qtype;// 类型

    public LambdaQueryWrapper<Question> toQuery() {
        return new LambdaQueryWrapper<Question>().eq(Question::getStatus, 1)
                .like(StringUtils.isNotBlank(title), Question::getTitle, title)
                .eq(StringUtils.isNotBlank(qclass), Question::getQclass, qclass)
                .eq(qtype != null, Question::getQtype, qtype)
                .orderByDesc(Question::getCreateDate, Question::getId);
    }
}
