package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.Record;
import lombok.Data;

@Data
public class RecordDto {
    private String doPath;
    private String ips;
    private String params;

    public LambdaQueryWrapper<Record> toQuery() {
        return new LambdaQueryWrapper<Record>()
                .like(StringUtils.isNotBlank(doPath), Record::getDoPath, doPath)
                .like(StringUtils.isNotBlank(ips), Record::getIps, ips)
                .like(StringUtils.isNotBlank(params), Record::getParams, params)
                .orderByDesc(Record::getCreateDate, Record::getId);
    }
}
