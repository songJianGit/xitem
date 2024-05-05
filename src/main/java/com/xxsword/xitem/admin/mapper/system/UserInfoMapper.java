package com.xxsword.xitem.admin.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.dto.system.UserInfoDto;
import com.xxsword.xitem.admin.domain.entity.system.UserInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("<script>" +
            "select " +
            "c.*" +
            "from t_sys_user_role b left join t_sys_userinfo c on b.userid=c.id " +
            "where 1=1 " +
            "<if test='dto.loginName!=null and dto.loginName!=\"\"'>" +
            "and c.loginname=#{userInfoDto.loginName} " +
            "</if>" +
            "<if test='dto.userName!=null and dto.userName!=\"\"'>" +
            "and c.username=#{userInfoDto.userName} " +
            "</if>" +
            "<if test='dto.phone!=null and dto.phone!=\"\"'>" +
            "and c.phone=#{userInfoDto.phone} " +
            "</if>" +
            "<if test='dto.roleId!=null and dto.roleId!=\"\"'>" +
            "and b.roleid=#{userInfoDto.roleId} " +
            "</if>" +
            "</script>")
    Page<UserInfo> pageUserBuRoleId(Page<UserInfo> page, UserInfoDto dto);

    @Select("<script>" +
            "select " +
            "count(c.id)" +
            "from t_sys_user_role b left join t_sys_userinfo c on b.userid=c.id " +
            "where 1=1 " +
            "<if test='roleId!=null and roleId!=\"\"'>" +
            "and b.roleid=#{roleId} " +
            "</if>" +
            "</script>")
    int countUserBuRoleId(String roleId);
}
