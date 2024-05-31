package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.entity.Function;
import com.xxsword.xitem.admin.domain.system.entity.RoleFunction;
import com.xxsword.xitem.admin.mapper.system.RoleFunctionMapper;
import com.xxsword.xitem.admin.service.system.FunctionService;
import com.xxsword.xitem.admin.service.system.RoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleFunctionServiceImpl extends ServiceImpl<RoleFunctionMapper, RoleFunction> implements RoleFunctionService {
    @Autowired
    private FunctionService functionService;

    @Override
    public List<Function> listFunctionByRoleId(String roleId) {
        List<String> functionIds = listRoleFunctionByRoleId(roleId).stream().map(RoleFunction::getFunId).collect(Collectors.toList());
        if(functionIds.isEmpty()){
            return new ArrayList<>();
        }
        LambdaQueryWrapper<Function> qFunction = Wrappers.lambdaQuery();
        qFunction.in(Function::getId, functionIds);
        qFunction.eq(Function::getStatus, 1);// 菜单会被逻辑删除，需要查询状态为有效的菜单
        return functionService.list(qFunction);
    }

    @Override
    @Transactional
    public void roleFunctionSave(String roleId, String funIds) {
        List<RoleFunction> roleFunctionList = listRoleFunctionByRoleId(roleId);
        Map<String, String> mapFI = new HashMap<>();// 该角色现有<菜单id,中间表id>
        for (RoleFunction roleFunction : roleFunctionList) {
            mapFI.put(roleFunction.getFunId(), roleFunction.getId());
        }
        Set<String> setsF = new HashSet<>(Arrays.asList(funIds.split(",")));// 该角色最新菜单
        List<RoleFunction> listAdd = new ArrayList<>();
        for (String fId : setsF) {
            if (!mapFI.containsKey(fId)) {
                RoleFunction rFun = new RoleFunction();
                rFun.setFunId(fId);
                rFun.setRoleId(roleId);
                listAdd.add(rFun);
            }
        }
        saveBatch(listAdd);// 添加

        List<RoleFunction> listDel = new ArrayList<>();
        for (String fId : mapFI.keySet()) {
            if (!setsF.contains(fId)) {
                RoleFunction rFun = new RoleFunction();
                rFun.setId(mapFI.get(fId));
                listDel.add(rFun);
            }
        }
        removeBatchByIds(listDel);// 删除
    }

    @Override
    public List<RoleFunction> listRoleFunctionByRoleId(String roleId) {
        LambdaQueryWrapper<RoleFunction> qRoleFun = Wrappers.lambdaQuery();
        qRoleFun.eq(RoleFunction::getRoleId, roleId);
        return list(qRoleFun);// 角色的菜单
    }
}
