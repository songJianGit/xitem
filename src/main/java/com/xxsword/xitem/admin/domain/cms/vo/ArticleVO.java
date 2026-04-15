package com.xxsword.xitem.admin.domain.cms.vo;

import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVO {
    private String id;
    private String createDate;
    private String createUserId;
    @Schema(title = "项目名称", description = "项目名称")
    private String projectName;
    @Schema(title = "任务状态", description = "任务状态")
    private String categoryId;
    @Schema(title = "任务状态", description = "任务状态")
    private String categoryName;
    @Schema(title = "标题", description = "标题")
    private String title;
    @Schema(title = "优先级", description = "优先级")
    private String levelId;
    @Schema(title = "优先级名称", description = "优先级名称")
    private String levelName;
    @Schema(title = "项目Id", description = "项目Id")
    private String pid;
    @Schema(title = "计划开始时间", description = "计划开始时间")
    private String stime;
    @Schema(title = "计划结束时间", description = "计划结束时间")
    private String etime;

    @Schema(title = "里程碑id", description = "里程碑id")
    private String roadmapId;

    @Schema(title = "任务成员", description = "任务成员")
    List<ArticleUser> users;

}


