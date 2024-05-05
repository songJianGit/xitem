package com.xxsword.xitem.admin.domain.dto.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.entity.system.Dict;
import com.xxsword.xitem.admin.domain.entity.system.Functions;

public class FunctionsDto {
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

    public LambdaQueryWrapper<Functions> toQuery() {
        return new LambdaQueryWrapper<Functions>().eq(Functions::getStatus, 1)
                .eq(StringUtils.isNotBlank(name), Functions::getName, name)
                .eq(StringUtils.isNotBlank(pid), Functions::getPid, pid)
                .orderByAsc(Functions::getSeq, Functions::getId);
    }
}
