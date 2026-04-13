package com.xxsword.xitem.admin.domain.cms.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CommentsVO {
    private String id;
    private String createDate;
    private String createUserId;
    private String createUserName;
    private String createUserNameFast;
    private String createUserAvatar;
    @Schema(title = "评论内容", description = "评论内容")
    private String content;

    @Schema(title = "评论类型（1-主评论 2-回复）", description = "评论类型（1-主评论 2-回复）")
    private Integer type;

    @Schema(title = "评论类型为回复时，记录主评论id", description = "评论类型为回复时，记录主评论id")
    private String comId;

    @Schema(title = "任务id", description = "任务id")
    private String aid;

    @Schema(title = "回复列表", description = "回复列表")
    List<CommentsVO> voList;
}
