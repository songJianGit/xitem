package com.xxsword.xitem.admin.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("<script>" +
            "select " +
            "count(c.id)" +
            "from t_sys_user_role b left join t_sys_userinfo c on b.userid=c.id " +
            "where 1=1 " +
            "<if test='roleId!=null and roleId!=\"\"'>" +
            "and b.role_id=#{roleId} " +
            "</if>" +
            "</script>")
    int countUserBuRoleId(String roleId);
}
