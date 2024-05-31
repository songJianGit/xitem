package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.Organ;
import lombok.Data;

@Data
public class OrganDto {
    private String name;
    private String pid;

    public LambdaQueryWrapper<Organ> toQuery() {
        return new LambdaQueryWrapper<Organ>().eq(Organ::getStatus, 1)
                .like(StringUtils.isNotBlank(name), Organ::getName, name)
                .eq(StringUtils.isNotBlank(pid), Organ::getPid, pid)
                .orderByAsc(Organ::getSeq, Organ::getId);
    }
}
