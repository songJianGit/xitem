package com.xxsword.xitem.admin.domain.project.vo;

import lombok.Data;

@Data
public class AUVO {
    private static final long serialVersionUID = 1L;
    // 用户Id（注意：这里也是用户id）
    private String id;
    // 任务id
    private String aid;
    // 用户Id
    private String userId;
    private String userNameFast;
    private String userName;
    private String jobTitle;
    private Boolean joinFlag;
}


