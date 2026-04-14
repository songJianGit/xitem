package com.xxsword.xitem.admin.domain.project.vo;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ProjectVO {
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键id", description = "主键id")
    private String id;

    @Schema(title = "创建人id", description = "创建人id")
    private String createUserId;

    @Schema(title = "创建人", description = "创建人")
    private String createUserName;

    @Schema(title = "创建时间", description = "创建时间")
    private String createDate;

    @Schema(title = "标题", description = "标题")
    private String title;

    @Column(type = MySqlTypeConstant.TEXT)
    @Schema(title = "描述", description = "描述")
    private String content;

    @Schema(title = "项目成员", description = "项目成员")
    List<ProjectUser> users;

    @Schema(title = "当前登录用户的项目被只读字段信息", description = "当前登录用户的项目被只读字段信息")
    private Integer userReadFlag;
}


