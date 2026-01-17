package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestResult;

import java.util.Map;
import java.util.Set;

public interface UserInfoService extends IService<UserInfo> {

    /**
     * 获取用户角色和菜单信息
     *
     * @param user
     * @param role 是否获取其角色信息
     * @return functions 是否获取其角色对应菜单信息
     */
    UserInfo setUserInfoRoleAndFun(UserInfo user, boolean role, boolean functions);

    /**
     * 校验本登录名是否可用
     *
     * @param loginName
     * @param userId    当前用户
     * @return true-还没有人用  false-已有人使用
     */
    boolean checkLonginName(String loginName, String userId);

    /**
     * 校验本手机号是否可用
     *
     * @param phoneNo
     * @param userId  当前用户
     * @return true-还没有人用  false-已有人使用
     */
    boolean checkPhoneNo(String phoneNo, String userId);

    /**
     * 删除用户（逻辑删除）
     */
    void delByIds(String userIds);

    /**
     * 用户的启用和停用
     *
     * @param userId
     * @return 改变后的用户状态
     */
    void upUserInfoStatus(String userId);

    /**
     * 根据登录名和密码获取用户信息
     *
     * @return
     */
    UserInfo getUserInfoByloginNameAndPassword(String loginName, String passWord);

    UserInfo getUserInfoByloginName(String loginName);

    /**
     * 用户登录次数，是否在1分钟内超过5次还没成功触发clearLockUser，则锁定。
     *
     * @param loginName
     * @return false-未被锁定 true-已锁定
     */
    boolean lockUser(String loginName);

    /**
     * 清除该用户的登录记录
     *
     * @param userId
     * @return
     */
    void clearLockUser(String userId);

    /**
     * 修改用户密码
     *
     * @param userId 用户id
     * @param p      原密码
     * @param p1     新密码
     * @param p2     再次输入的新密码
     * @return
     */
    RestResult changePwd(String userId, String p, String p1, String p2);

    void resetPassword(String userId, String password);

    /**
     * 根据角色id查询用户数
     *
     * @param roleId
     * @return
     */
    int countUserBuRoleId(String roleId);

    /**
     * 获取用户
     *
     * @param userIds
     * @return
     */
    Map<String, UserInfo> mapsUser(Set<String> userIds);
}
