package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.entity.Function;
import com.xxsword.xitem.admin.mapper.system.FunctionMapper;
import com.xxsword.xitem.admin.service.system.FunctionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FunctionServiceImpl extends ServiceImpl<FunctionMapper, Function> implements FunctionService {

    @Override
    public List<Function> functionsRetainAll(String pid, List<Function> functionList) {
        if (functionList.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> listIds = functionList.stream().map(Function::getId).collect(Collectors.toList());
        LambdaQueryWrapper<Function> query = Wrappers.lambdaQuery();
        query.eq(Function::getStatus, 1);
        query.eq(Function::getPid, pid);
        query.in(Function::getId, listIds);
        query.orderByAsc(Function::getSeq, Function::getId);
        return list(query);
    }

    @Override
    @Transactional
    public void delFunctionById(String id) {
        Function functionUp = new Function();
        functionUp.setId(id);
        functionUp.setStatus(0);
        updateById(functionUp);
        delFunctionDown(id);// 递归删除其所有子集
    }

    private void delFunctionDown(String functionId) {
        LambdaQueryWrapper<Function> query = Wrappers.lambdaQuery();
        query.eq(Function::getPid, functionId);
        List<Function> pf = list(query);
        if (pf != null && pf.size() > 0) {
            for (Function f : pf) {
                Function functionUp = new Function();
                functionUp.setId(f.getId());
                functionUp.setStatus(0);
                updateById(functionUp);
                delFunctionDown(f.getId());
            }
        }
    }

    @Override
    public void saveFunctionSeq(String fids, String seqs) {
        String[] fidAttr = fids.split(",");
        String[] seqAttr = seqs.split(",");
        List<Function> functionListUp = new ArrayList<>();
        for (int i = 0; i < fidAttr.length; i++) {
            Function functionUp = new Function();
            functionUp.setId(fidAttr[i]);
            functionUp.setSeq(Integer.valueOf(seqAttr[i]));
            functionListUp.add(functionUp);
        }
        updateBatchById(functionListUp);
    }

}
