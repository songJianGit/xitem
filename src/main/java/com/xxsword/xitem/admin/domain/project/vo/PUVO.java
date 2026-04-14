package com.xxsword.xitem.admin.domain.project.vo;

import lombok.Data;

@Data
public class PUVO {
    private static final long serialVersionUID = 1L;
    // 用户Id（注意：这里也是用户id）
    private String id;
    // 项目id
    private String pid;
    private String avatar;
    // 用户Id
    private String userId;
    private String userNameFast;
    private String userName;
    private String jobTitle;
    // 用户权限（ 1-正常可编辑 0-只读）
    private Integer readFlag;
    private Boolean joinFlag;
}


