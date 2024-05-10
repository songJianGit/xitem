package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.Organ;

public class OrganDto {
    private String name;
    private String pid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public LambdaQueryWrapper<Organ> toQuery() {
        return new LambdaQueryWrapper<Organ>().eq(Organ::getStatus, 1)
                .eq(StringUtils.isNotBlank(name), Organ::getName, name)
                .eq(StringUtils.isNotBlank(pid), Organ::getPid, pid)
                .orderByAsc(Organ::getSeq, Organ::getId);
    }
}
