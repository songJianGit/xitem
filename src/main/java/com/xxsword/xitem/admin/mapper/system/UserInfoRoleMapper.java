package com.xxsword.xitem.admin.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.system.dto.UserInfoRoleDto;
import com.xxsword.xitem.admin.domain.system.entity.UserInfoRole;
import com.xxsword.xitem.admin.domain.system.vo.UserInfoRoleVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRoleMapper extends BaseMapper<UserInfoRole> {
    @Select("<script>" +
            "select " +
            "b.*,c.user_name,c.login_name,c.phone_no " +
            "from t_sys_user_role b left join t_sys_userinfo c on b.user_id=c.id " +
            "where 1=1 " +
            "<if test='dto.loginName!=null and dto.loginName!=\"\"'>" +
            "and c.login_name=#{dto.loginName} " +
            "</if>" +
            "<if test='dto.userName!=null and dto.userName!=\"\"'>" +
            "and c.user_name=#{dto.userName} " +
            "</if>" +
            "<if test='dto.phoneNo!=null and dto.phoneNo!=\"\"'>" +
            "and c.phone_no=#{dto.phoneNo} " +
            "</if>" +
            "<if test='dto.roleId!=null and dto.roleId!=\"\"'>" +
            "and b.role_id=#{dto.roleId} " +
            "</if>" +
            "</script>")
    Page<UserInfoRoleVO> pageUserBuRoleId(Page<UserInfoRole> page, UserInfoRoleDto dto);
}
