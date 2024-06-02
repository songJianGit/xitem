package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.entity.Function;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.system.UserInfoMapper;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.system.RoleFunctionService;
import com.xxsword.xitem.admin.service.system.UserInfoRoleService;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private RoleFunctionService roleFunctionService;
    @Autowired
    private UserInfoRoleService userInfoRoleService;

    @Override
    public UserInfo setUserInfoRoleAndFun(UserInfo user, boolean role, boolean function) {
        if (role) {
            List<Role> roleList = userInfoRoleService.listRoleByUserId(user.getId());
            if (!roleList.isEmpty()) {
                if (function) {
                    for (Role r : roleList) {
                        List<Function> functionList = roleFunctionService.listFunctionByRoleId(r.getId());
                        r.setFunctionList(functionList);
                    }
                }
                user.setRoleList(roleList);
            }
        }
        return user;
    }

    @Override
    public boolean checkLonginName(String loginName, String userId) {
        if (StringUtils.isBlank(loginName)) {
            return false;
        } else {
            loginName = loginName.trim();
        }
        LambdaQueryWrapper<UserInfo> q = Wrappers.lambdaQuery();
        q.eq(UserInfo::getLoginName, loginName);
//        q.eq(UserInfo::getStatus, 1);不论有效还是无效，都要检查唯一
        if (StringUtils.isNotBlank(userId)) {
            q.ne(UserInfo::getId, userId);
        }
        return count(q) <= 0;
    }

    @Override
    public boolean checkPhoneNo(String phoneNo, String userId) {
        if (StringUtils.isBlank(phoneNo)) {
            return false;
        } else {
            phoneNo = phoneNo.trim();
        }
        LambdaQueryWrapper<UserInfo> q = Wrappers.lambdaQuery();
        q.eq(UserInfo::getPhoneNo, phoneNo);
//        q.eq(UserInfo::getStatus, 1);不论有效还是无效，都要检查唯一
        if (StringUtils.isNotBlank(userId)) {
            q.ne(UserInfo::getId, userId);
        }
        return count(q) <= 0;
    }

    @Override
    public void delByIds(String userIds) {
        String[] ids = userIds.split(",");
        List<UserInfo> listUp = new ArrayList<>();
        for (String id : ids) {
            UserInfo userInfoUp = new UserInfo();
            userInfoUp.setId(id);
            userInfoUp.setStatus(0);
            listUp.add(userInfoUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void upUserInfoStatus(String userId) {
        UserInfo userInfo = getById(userId);
        UserInfo userInfoUp = new UserInfo();
        userInfoUp.setId(userId);
        if (userInfo.getStatus() == null || userInfo.getStatus().equals(1)) {
            userInfoUp.setStatus(2);
        } else {
            userInfoUp.setStatus(1);
        }
        updateById(userInfoUp);
    }

    @Override
    public UserInfo getUserInfoByloginNameAndPassword(String loginName, String passWord) {
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(passWord)) {
            return null;
        }
        LambdaQueryWrapper<UserInfo> query = Wrappers.lambdaQuery();
        query.eq(UserInfo::getStatus, 1);
        query.eq(UserInfo::getLoginName, loginName);
        query.eq(UserInfo::getPassword, passWord);
        query.ge(UserInfo::getLifeDate, DateUtil.now());
        UserInfo userInfo = getOne(query);
        if (userInfo == null) {
            return null;
        }
        return userInfo;
    }

    @Override
    public UserInfo getUserInfoByloginName(String loginName) {
        if (StringUtils.isBlank(loginName)) {
            return null;
        }
        LambdaQueryWrapper<UserInfo> query = Wrappers.lambdaQuery();
        query.eq(UserInfo::getStatus, 1);
        query.eq(UserInfo::getLoginName, loginName);
        return getOne(query);
    }

    @Override
    public boolean lockUser(String loginName) {
        if (StringUtils.isBlank(loginName)) {
            return false;
        }
        boolean flag = false;
        UserInfo userInfo = getUserInfoByloginName(loginName);
        if (userInfo == null) {
            return false;
        }
        String nowTime = DateUtil.now(DateUtil.sdfA2);
        String pwdError = userInfo.getPasswordError();
        if (StringUtils.isBlank(pwdError)) {
            pwdError = DateUtil.now(DateUtil.sdfA2) + ",1";
        } else {
            String[] info = pwdError.split(",");
            String time = info[0];
            if (nowTime.equals(time)) {
                int num = Integer.parseInt(info[1]) + 1;
                pwdError = nowTime + "," + num;
                if (num > 5) {
                    flag = true;
                }
            } else {
                pwdError = nowTime + ",1";
            }
        }
        UserInfo userInfoUp = new UserInfo();
        userInfoUp.setId(userInfo.getId());
        userInfoUp.setPasswordError(pwdError);
        updateById(userInfoUp);
        return flag;
    }

    @Override
    public void clearLockUser(String userId) {
        UserInfo userInfoUp = new UserInfo();
        userInfoUp.setId(userId);
        userInfoUp.setPasswordError("");
        updateById(userInfoUp);
    }

    @Override
    public RestResult changePwd(String userId, String p, String p1, String p2) {
        UserInfo user = getById(userId);
        if (p.equals(user.getPassword())) {
            if (p1.equals(p2)) {
                UserInfo userUp = new UserInfo();
                userUp.setId(user.getId());
                userUp.setPassword(p1);
                updateById(userUp);
                return RestResult.OK();
            } else {
                return RestResult.Fail("密码不一致");
            }
        } else {
            return RestResult.Fail("原密码错误");
        }
    }

    @Override
    public void resetPassword(String userId, String password) {
        UserInfo userUp = new UserInfo();
        userUp.setId(userId);
        userUp.setPassword(password);
        updateById(userUp);
    }

    @Override
    public int countUserBuRoleId(String roleId) {
        return baseMapper.countUserBuRoleId(roleId);
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String userIds) {
        String[] ids = userIds.split(",");
        List<UserInfo> listUp = new ArrayList<>();
        for (String id : ids) {
            UserInfo userInfoUp = new UserInfo();
            userInfoUp.setId(id);
            userInfoUp.setBaseInfo(doUserInfo);
            listUp.add(userInfoUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public Map<String, UserInfo> mapsUser(Set<String> userIds) {
        return listByIds(userIds).stream().collect(Collectors.toMap(UserInfo::getId, java.util.function.Function.identity()));
    }

}
