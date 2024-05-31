package com.xxsword.xitem.admin.domain.exam.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;
import lombok.Data;

@Data
public class PaperDto {

    private String title;// 标题

    public LambdaQueryWrapper<Paper> toQuery() {
        return new LambdaQueryWrapper<Paper>().eq(Paper::getStatus, 1)
                .like(StringUtils.isNotBlank(title), Paper::getTitle, title)
                .orderByDesc(Paper::getCreateDate, Paper::getId);
    }
}
