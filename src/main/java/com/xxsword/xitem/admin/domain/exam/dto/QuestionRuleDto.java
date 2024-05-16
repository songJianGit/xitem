package com.xxsword.xitem.admin.domain.exam.dto;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionRule;

public class QuestionRuleDto {

    private String title;

    private String paperId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public LambdaQueryWrapper<QuestionRule> toQuery() {
        return new LambdaQueryWrapper<QuestionRule>().eq(QuestionRule::getStatus, 1)
                .like(StringUtils.isNotBlank(title), QuestionRule::getTitle, title)
                .eq(StringUtils.isNotBlank(paperId), QuestionRule::getPaperid, paperId)
                .orderByAsc(QuestionRule::getSeq, QuestionRule::getId);
    }
}
