package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.constant.PermissionType;
import com.xxsword.xitem.admin.domain.system.dto.OrganDto;
import com.xxsword.xitem.admin.domain.system.entity.Organ;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.system.OrganMapper;
import com.xxsword.xitem.admin.service.system.OrganService;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrganServiceImpl extends ServiceImpl<OrganMapper, Organ> implements OrganService {

    @Override
    public List<Organ> listOrgan() {
        return listOrgan(null);
    }

    @Override
    public List<Organ> listOrgan(UserInfo userInfo) {
        return listOrgan(userInfo, new OrganDto());
    }

    @Override
    public List<Organ> listOrgan(UserInfo userInfo, OrganDto organDto) {
        LambdaQueryWrapper<Organ> query = organDto.toQuery();
        if (userInfo != null) {
            permissionHandle(userInfo, PermissionType.ORGAN, query);
        }
        return list(query);
    }

    @Override
    public Page<Organ> pageOrgan(Page<Organ> page, UserInfo userInfo, OrganDto organDto) {
        LambdaQueryWrapper<Organ> query = organDto.toQuery();
        if (userInfo != null) {
            permissionHandle(userInfo, PermissionType.ORGAN, query);
        }
        return page(page, query);
    }

    @Override
    public Organ maxSeq(String organId) {
        OrganDto organDto = new OrganDto();
        organDto.setPid(organId);
        LambdaQueryWrapper<Organ> query = organDto.toQuery();
        List<Organ> p = page(new Page<>(1, 1), query).getRecords();
        if (p.size() == 0) {
            return getById(organId);
        }
        return p.get(0);
    }

    @Override
    public List<Organ> organP(String organId) {
        if (StringUtils.isBlank(organId)) {
            return new ArrayList<>();
        }
        List<Organ> list = new ArrayList<>();
        diGuiUpOrgan(list, organId);
        list.remove(0);// 剔除掉自己
        Collections.reverse(list);
        return list;
    }

    @Override
    public String organPIds(String organId) {
        return organP(organId).stream().map(Organ::getId).collect(Collectors.joining(","));
    }

    // 递归向上寻找机构的所有上级信息
    private void diGuiUpOrgan(List<Organ> list, String organId) {
        Organ organ = getById(organId);
        if (organ != null) {
            list.add(organ);
            diGuiUpOrgan(list, organ.getPid());
        }
    }

    @Override
    public List<Organ> organC(String organId) {
        LambdaQueryWrapper<Organ> q = Wrappers.lambdaQuery();
        q.eq(Organ::getStatus, 1);
        q.last(" AND find_in_set('" + organId + "', pids) ");
        return list(q);
    }

    @Override
    public void upOrganpids() {
        log.info("更新部门pids START");
        LambdaQueryWrapper<Organ> query = Wrappers.lambdaQuery();
        query.eq(Organ::getStatus, 1);
        query.orderByAsc(Organ::getSeq);
        int pageSize = 500;
        long count = count(query);
        long page = Utils.getPage(count, pageSize) + 1;
        try {
            for (int i = 1; i < page; i++) {
                Page<Organ> xo = page(new Page<>(i, pageSize), query);
                List<Organ> organList = xo.getRecords();
                List<Organ> organListUp = new ArrayList<>();
                for (Organ o : organList) {
                    Organ oUp = new Organ();
                    oUp.setId(o.getId());
                    oUp.setPids(organPIds(o.getId()));
                    organListUp.add(oUp);
                }
                updateBatchById(organListUp);
                if (xo.getRecords().size() < pageSize) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("更新部门pids异常");
        }
        log.info("更新部门pids END");
    }

    @Override
    public void delOrgan(String organIds) {
        String[] ids = organIds.split(",");
        for (String id : ids) {
            Organ organUp = new Organ();
            organUp.setId(id);
            organUp.setStatus(0);
            updateById(organUp);
            List<Organ> list = organC(id);
            List<Organ> listUp = new ArrayList<>();
            for (Organ o : list) {
                Organ oUp = new Organ();
                oUp.setId(o.getId());
                oUp.setStatus(0);
                listUp.add(oUp);
            }
            updateBatchById(listUp);
        }
    }

    @Override
    public List<String> listOrganIdByOrganId(String organId) {
        List<Organ> list = organC(organId);
        List<String> stringList = list.stream().map(Organ::getId).collect(Collectors.toList());
        stringList.add(organId);
        return stringList;
    }

    @Override
    public List<String> listOrganIdByOrganId(List<String> organIds) {
        Set<String> set = new HashSet<>();
        for (String id : organIds) {
            set.addAll(listOrganIdByOrganId(id));
        }
        return new ArrayList<>(set);
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String organIds) {
        String[] ids = organIds.split(",");
        List<Organ> listUp = new ArrayList<>();
        for (String id : ids) {
            Organ organUp = new Organ();
            organUp.setId(id);
            organUp.setBaseInfo(doUserInfo);
            listUp.add(organUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void permissionHandle(UserInfo userInfo, String permissionType, LambdaQueryWrapper query) {
        Integer userPermissionType = userInfo.getPermissionType();
        String userId = userInfo.getId();
        if (userPermissionType == null) {
            userPermissionType = 1;// 默认未本级及以下
        }
        if (userPermissionType.equals(2)) {
            return;// 查看全部数据
        }
        List<String> permissionIdsAll = null;
        if (userPermissionType.equals(1)) {
            List<String> permissionIds = getPermissionIds(userInfo);
            permissionIdsAll = listOrganIdByOrganId(permissionIds);
        }
        switch (permissionType) {
            case PermissionType.USERINFO:
                LambdaQueryWrapper<UserInfo> qUser = query;
                if (userPermissionType.equals(0)) {
                    qUser.eq(UserInfo::getCreateUserId, userId);
                }
                if (userPermissionType.equals(1)) {
                    qUser.in(UserInfo::getCreateOrganId, permissionIdsAll);
                }
                break;
            case PermissionType.ORGAN:
                LambdaQueryWrapper<Organ> qOrgan = query;
                if (userPermissionType.equals(0)) {
                    qOrgan.eq(Organ::getCreateUserId, userId);
                }
                if (userPermissionType.equals(1)) {
                    qOrgan.in(Organ::getId, permissionIdsAll);
                }
                break;
            default:
                log.warn("未匹配到权限类型");
                break;
        }
    }


    // =============================数据权限相关================================

    @Override
    public String getPermissionDown(UserInfo userInfo) {
        List<String> permissionIds = getPermissionIds(userInfo);
        List<String> permissionIdsAll = listOrganIdByOrganId(permissionIds);
        return " AND " + Constant.permissionKey + " ('" + String.join("','", permissionIdsAll) + "')";
    }

    @Override
    public String getPermissionDown(String as, UserInfo userInfo) {
        List<String> permissionIds = getPermissionIds(userInfo);
        List<String> permissionIdsAll = listOrganIdByOrganId(permissionIds);
        return " AND " + as + "." + Constant.permissionKey + " ('" + String.join("','", permissionIdsAll) + "')";
    }

    /**
     * 获取用户权限配置信息
     *
     * @param userInfo
     * @return
     */
    private List<String> getPermissionIds(UserInfo userInfo) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(userInfo.getPermissionIds())) {
            return new ArrayList<>(Arrays.asList(userInfo.getPermissionIds().split(",")));
        }
        list.add(userInfo.getOrganId());
        return list;
    }

}
