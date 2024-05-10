package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.dto.UserInfoRoleDto;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.UserInfoRole;
import com.xxsword.xitem.admin.domain.system.vo.UserInfoRoleVO;

import java.util.List;

public interface UserInfoRoleService extends IService<UserInfoRole> {
    /**
     * 根据用户id，查询用户角色
     */
    List<Role> listRoleByUserId(String userId);

    /**
     * 将用户和角色拆分
     */
    void userSplitRole(String urIds);

    /**
     * 将用户和角色绑定
     */
    void userLinkRole(String roleId, String userIds);

    /**
     * 根据角色查询用户
     *
     * @param page
     * @param dto
     * @return
     */
    Page<UserInfoRoleVO> queryUserListByRole(Page<UserInfoRole> page, UserInfoRoleDto dto);
}
