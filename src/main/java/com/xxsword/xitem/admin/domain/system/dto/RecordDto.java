package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.Record;

public class RecordDto {
    private String dopath;
    private String ips;
    private String params;

    public String getDopath() {
        return dopath;
    }

    public void setDopath(String dopath) {
        this.dopath = dopath;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public LambdaQueryWrapper<Record> toQuery() {
        return new LambdaQueryWrapper<Record>()
                .like(StringUtils.isNotBlank(dopath), Record::getDopath, dopath)
                .like(StringUtils.isNotBlank(ips), Record::getIps, ips)
                .like(StringUtils.isNotBlank(params), Record::getParams, params)
                .orderByDesc(Record::getCdate, Record::getId);
    }
}
