package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.Function;
import lombok.Data;

@Data
public class FunctionsDto {
    private String name;
    private String pid;

    public LambdaQueryWrapper<Function> toQuery() {
        return new LambdaQueryWrapper<Function>().eq(Function::getStatus, 1)
                .like(StringUtils.isNotBlank(name), Function::getName, name)
                .eq(StringUtils.isNotBlank(pid), Function::getPid, pid)
                .orderByAsc(Function::getSeq, Function::getId);
    }
}
