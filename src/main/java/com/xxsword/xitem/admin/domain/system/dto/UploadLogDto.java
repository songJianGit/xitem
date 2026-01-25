package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.UploadLog;
import lombok.Data;

@Data
public class UploadLogDto {
    private String name;
    private String userId;// 创建人id

    public LambdaQueryWrapper<UploadLog> toQuery() {
        return new LambdaQueryWrapper<UploadLog>()
                .like(StringUtils.isNotBlank(name), UploadLog::getName, name)
                .eq(StringUtils.isNotBlank(userId), UploadLog::getUserId, userId)
                .orderByDesc(UploadLog::getCreateDate, UploadLog::getId);
    }
}
