package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.Dict;

public class DictDto {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LambdaQueryWrapper<Dict> toQuery() {
        return new LambdaQueryWrapper<Dict>().eq(Dict::getStatus, 1)
                .like(StringUtils.isNotBlank(name), Dict::getName, name)
                .eq(StringUtils.isNotBlank(type), Dict::getType, type)
                .orderByDesc(Dict::getCdate, Dict::getId);
    }
}
