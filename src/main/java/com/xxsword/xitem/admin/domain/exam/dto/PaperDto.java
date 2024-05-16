package com.xxsword.xitem.admin.domain.exam.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;

public class PaperDto {

    private String title;// 标题

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public LambdaQueryWrapper<Paper> toQuery() {
        return new LambdaQueryWrapper<Paper>().eq(Paper::getStatus, 1)
                .like(StringUtils.isNotBlank(title), Paper::getTitle, title)
                .orderByDesc(Paper::getCdate, Paper::getId);
    }
}
