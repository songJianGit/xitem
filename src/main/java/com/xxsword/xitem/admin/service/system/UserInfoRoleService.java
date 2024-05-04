package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.entity.system.Role;
import com.xxsword.xitem.admin.domain.entity.system.UserInfoRole;

import java.util.List;

public interface UserInfoRoleService extends IService<UserInfoRole> {
    /**
     * 根据用户id，查询用户角色
     */
    List<Role> listRoleByUserId(String userId);

    /**
     * 将用户和角色拆分
     */
    void userSplitRole(String roleid, String userids);

    /**
     * 将用户和角色绑定
     */
    void userLinkRole(String roleid, String userids);
}
