package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import lombok.Data;

@Data
public class UserInfoDto {
    private String loginName;
    private String userName;
    private String phoneNo;
    private String roleId;
    private Integer status;

    public LambdaQueryWrapper<UserInfo> toQuery() {
        return new LambdaQueryWrapper<UserInfo>()
                .in(status == null, UserInfo::getStatus, 1, 2)
                .eq(status != null, UserInfo::getStatus, status)
                .like(StringUtils.isNotBlank(loginName), UserInfo::getLoginname, loginName)
                .like(StringUtils.isNotBlank(userName), UserInfo::getUsername, userName)
                .like(StringUtils.isNotBlank(phoneNo), UserInfo::getPhoneno, phoneNo)
                .orderByDesc(UserInfo::getId);
    }
}
