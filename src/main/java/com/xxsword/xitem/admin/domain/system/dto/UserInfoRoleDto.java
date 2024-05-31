package com.xxsword.xitem.admin.domain.system.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import lombok.Data;

@Data
public class UserInfoRoleDto {
    private String loginName;
    private String userName;
    private String phoneNo;
    private String roleId;

    public LambdaQueryWrapper<UserInfo> toQuery() {
        return new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getStatus, 1)
                .like(StringUtils.isNotBlank(loginName), UserInfo::getLoginName, loginName)
                .like(StringUtils.isNotBlank(userName), UserInfo::getUserName, userName)
                .like(StringUtils.isNotBlank(phoneNo), UserInfo::getPhoneNo, phoneNo)
                .orderByDesc(UserInfo::getId);
    }
}
