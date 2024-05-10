package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.entity.Functions;
import com.xxsword.xitem.admin.domain.system.entity.RoleFunctions;
import com.xxsword.xitem.admin.mapper.system.RoleFunctionsMapper;
import com.xxsword.xitem.admin.service.system.FunctionsService;
import com.xxsword.xitem.admin.service.system.RoleFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleFunctionsServiceImpl extends ServiceImpl<RoleFunctionsMapper, RoleFunctions> implements RoleFunctionsService {
    @Autowired
    private FunctionsService functionsService;

    @Override
    public List<Functions> listFunctionsByRoleId(String roleId) {
        List<String> functionIds = listRoleFunctionsByRoleId(roleId).stream().map(RoleFunctions::getFunid).collect(Collectors.toList());
        if(functionIds.isEmpty()){
            return new ArrayList<>();
        }
        LambdaQueryWrapper<Functions> qFunctions = Wrappers.lambdaQuery();
        qFunctions.in(Functions::getId, functionIds);
        qFunctions.eq(Functions::getStatus, 1);// 菜单会被逻辑删除，需要查询状态为有效的菜单
        return functionsService.list(qFunctions);
    }

    @Override
    @Transactional
    public void roleFunctionsSave(String roleId, String funIds) {
        List<RoleFunctions> roleFunctionsList = listRoleFunctionsByRoleId(roleId);
        Map<String, String> mapFI = new HashMap<>();// 该角色现有<菜单id,中间表id>
        for (RoleFunctions roleFunctions : roleFunctionsList) {
            mapFI.put(roleFunctions.getFunid(), roleFunctions.getId());
        }
        Set<String> setsF = new HashSet<>(Arrays.asList(funIds.split(",")));// 该角色最新菜单
        List<RoleFunctions> listAdd = new ArrayList<>();
        for (String fId : setsF) {
            if (!mapFI.containsKey(fId)) {
                RoleFunctions rFun = new RoleFunctions();
                rFun.setFunid(fId);
                rFun.setRoleid(roleId);
                listAdd.add(rFun);
            }
        }
        saveBatch(listAdd);// 添加

        List<RoleFunctions> listDel = new ArrayList<>();
        for (String fId : mapFI.keySet()) {
            if (!setsF.contains(fId)) {
                RoleFunctions rFun = new RoleFunctions();
                rFun.setId(mapFI.get(fId));
                listDel.add(rFun);
            }
        }
        removeBatchByIds(listDel);// 删除
    }

    @Override
    public List<RoleFunctions> listRoleFunctionsByRoleId(String roleId) {
        LambdaQueryWrapper<RoleFunctions> qRoleFun = Wrappers.lambdaQuery();
        qRoleFun.eq(RoleFunctions::getRoleid, roleId);
        return list(qRoleFun);// 角色的菜单
    }
}
