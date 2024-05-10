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
     * 所有下级(不包括自己)
     *
     * @param organId
     * @return
     */
    List<Organ> organC(String organId);

    /**
     * 更新全部机构的pids
     */
    void upOrganpids();

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

    /**
     * 刷新机构最后更新人和最后更新时间
     */
    void upLastInfo(UserInfo doUserInfo, String organIds);

    //=======================================================================

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
