package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.dto.OrganDto;
import com.xxsword.xitem.admin.domain.system.entity.Organ;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface OrganService extends IService<Organ> {
    /**
     * 所有有效机构
     *
     * @return
     */
    List<Organ> listOrgan();

    /**
     * 该用户管辖的
     *
     * @param userInfo
     * @return
     */
    List<Organ> listOrgan(UserInfo userInfo);

    /**
     * 该用户管辖的
     *
     * @param userInfo
     * @return
     */
    List<Organ> listOrgan(UserInfo userInfo, OrganDto organDto);

    /**
     * 该用户管辖的
     *
     * @param page
     * @param userInfo
     * @param organDto
     * @return
     */
    Page<Organ> pageOrgan(Page<Organ> page, UserInfo userInfo, OrganDto organDto);

    /**
     * 该id子集最大seq
     *
     * @param organId
     * @return
     */
    Organ maxSeq(String organId);

    /**
     * 所有上级机构集合(不包括自己)
     *
     * @param organId
     * @return
     */
    List<Organ> organP(String organId);

    /**
     * 该机构的所有上级机构的ids，逗号分隔(不包括自己)
     */
    String organPIds(String organId);

    /**
     * 该机构的所有上级机构的名字拼接，最后拼接上自己的名字
     */
    String organPNames(String organId);

    /**
     * 所有下级(不包括自己)
     *
     * @param organId
     * @return
     */
    List<Organ> organC(String organId);

    /**
     * 更新全部机构的pids
     */
    void upOrganPids();

    /**
     * 机构的逻辑删除
     */
    void delOrgan(String organIds);

    /**
     * 本机构和本机构以下的机构ids
     *
     * @param organId
     * @return
     */
    List<String> listOrganIdByOrganId(String organId);

    /**
     * 本机构和本机构以下的机构ids
     *
     * @param organIds
     * @return
     */
    List<String> listOrganIdByOrganId(List<String> organIds);

    //=======================================================================

    /**
     * 数据权限过滤
     *
     * @param userInfo
     * @param permissionType com/xxsword/xitem/admin/constant/PermissionType中定义的类型
     * @param query
     */
    void permissionHandle(UserInfo userInfo, String permissionType, LambdaQueryWrapper query);

    /**
     * 本机构和本机构以下数据(用于sql)
     *
     * @return
     */
    String getPermissionDown(UserInfo userInfo);

    /**
     * 本机构和本机构以下数据(用于sql)
     * as 别名
     *
     * @return
     */

    String getPermissionDown(String as, UserInfo userInfo);

}
