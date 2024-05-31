package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.Functions;
import lombok.Data;

@Data
public class FunctionsDto {
    private String name;
    private String pid;

    public LambdaQueryWrapper<Functions> toQuery() {
        return new LambdaQueryWrapper<Functions>().eq(Functions::getStatus, 1)
                .like(StringUtils.isNotBlank(name), Functions::getName, name)
                .eq(StringUtils.isNotBlank(pid), Functions::getPid, pid)
                .orderByAsc(Functions::getSeq, Functions::getId);
    }
}
