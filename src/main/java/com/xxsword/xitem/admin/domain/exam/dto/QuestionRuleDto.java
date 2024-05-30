package com.xxsword.xitem.admin.domain.exam.dto;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionRule;
import lombok.Data;

@Data
public class QuestionRuleDto {

    private String title;

    private String paperId;

    public LambdaQueryWrapper<QuestionRule> toQuery() {
        return new LambdaQueryWrapper<QuestionRule>().eq(QuestionRule::getStatus, 1)
                .like(StringUtils.isNotBlank(title), QuestionRule::getTitle, title)
                .eq(StringUtils.isNotBlank(paperId), QuestionRule::getPaperid, paperId)
                .orderByAsc(QuestionRule::getSeq, QuestionRule::getId);
    }
}
