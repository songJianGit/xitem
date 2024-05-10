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
            "b.*,c.username,c.loginname,c.phoneno " +
            "from t_sys_user_role b left join t_sys_userinfo c on b.userid=c.id " +
            "where 1=1 " +
            "<if test='dto.loginName!=null and dto.loginName!=\"\"'>" +
            "and c.loginname=#{dto.loginName} " +
            "</if>" +
            "<if test='dto.userName!=null and dto.userName!=\"\"'>" +
            "and c.username=#{dto.userName} " +
            "</if>" +
            "<if test='dto.phoneNo!=null and dto.phoneNo!=\"\"'>" +
            "and c.phoneno=#{dto.phoneNo} " +
            "</if>" +
            "<if test='dto.roleId!=null and dto.roleId!=\"\"'>" +
            "and b.roleid=#{dto.roleId} " +
            "</if>" +
            "</script>")
    Page<UserInfoRoleVO> pageUserBuRoleId(Page<UserInfoRole> page, UserInfoRoleDto dto);
}
