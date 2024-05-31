package com.xxsword.xitem.admin.domain.exam.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import lombok.Data;

@Data
public class UserPaperDto {
    private String id;
    private String paperId;// 试卷id
    private String examId;// 考试id
    private String userId;// 用户id
    private String userName;// 用户名称
    private Integer subStatus;// 提交状态(0-初始 1-已提交)

    public LambdaQueryWrapper<UserPaper> toQuery() {
        return new LambdaQueryWrapper<UserPaper>().eq(UserPaper::getStatus, 1)
                .eq(StringUtils.isNotBlank(paperId), UserPaper::getPaperId, paperId)
                .eq(StringUtils.isNotBlank(examId), UserPaper::getExamId, examId)
                .eq(StringUtils.isNotBlank(userId), UserPaper::getUserId, userId)
                .eq(subStatus != null, UserPaper::getSubStatus, subStatus)
                .eq(subStatus == null, UserPaper::getSubStatus, 1)// 默认查询已提交的
                .orderByDesc(UserPaper::getCreateDate, UserPaper::getId);
    }
}
