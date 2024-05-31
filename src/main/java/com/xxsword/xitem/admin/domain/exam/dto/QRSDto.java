package com.xxsword.xitem.admin.domain.exam.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.exam.entity.QRS;
import lombok.Data;

@Data
public class QRSDto {

    private String qrId;

    public LambdaQueryWrapper<QRS> toQuery() {
        return new LambdaQueryWrapper<QRS>().eq(QRS::getStatus, 1)
                .eq(StringUtils.isNotBlank(qrId), QRS::getQrId, qrId)
                .orderByAsc(QRS::getSeq, QRS::getId);
    }
}
