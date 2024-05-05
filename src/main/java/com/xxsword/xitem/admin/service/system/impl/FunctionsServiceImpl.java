package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.entity.system.Functions;
import com.xxsword.xitem.admin.mapper.system.FunctionsMapper;
import com.xxsword.xitem.admin.service.system.FunctionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FunctionsServiceImpl extends ServiceImpl<FunctionsMapper, Functions> implements FunctionsService {

    @Override
    public List<Functions> functionsRetainAll(String pid, List<Functions> functionsList) {
        if (functionsList.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> listIds = functionsList.stream().map(Functions::getId).collect(Collectors.toList());
        LambdaQueryWrapper<Functions> query = Wrappers.lambdaQuery();
        query.eq(Functions::getStatus, 1);
        query.eq(Functions::getPid, pid);
        query.in(Functions::getId, listIds);
        query.orderByAsc(Functions::getSeq, Functions::getId);
        return list(query);
    }

    @Override
    @Transactional
    public void delFunctionsById(String id) {
        Functions functionsUp = new Functions();
        functionsUp.setId(id);
        functionsUp.setStatus(0);
        updateById(functionsUp);
        delFunctionsDown(id);// 递归删除其所有子集
    }

    private void delFunctionsDown(String functionsId) {
        LambdaQueryWrapper<Functions> query = Wrappers.lambdaQuery();
        query.eq(Functions::getPid, functionsId);
        List<Functions> pf = list(query);
        if (pf != null && pf.size() > 0) {
            for (Functions f : pf) {
                Functions functionsUp = new Functions();
                functionsUp.setId(f.getId());
                functionsUp.setStatus(0);
                updateById(functionsUp);
                delFunctionsDown(f.getId());
            }
        }
    }

    @Override
    public void saveFunctionsSeq(String fids, String seqs) {
        String[] fidAttr = fids.split(",");
        String[] seqAttr = seqs.split(",");
        List<Functions> functionsListUp = new ArrayList<>();
        for (int i = 0; i < fidAttr.length; i++) {
            Functions functionsUp = new Functions();
            functionsUp.setId(fidAttr[i]);
            functionsUp.setSeq(Integer.valueOf(seqAttr[i]));
            functionsListUp.add(functionsUp);
        }
        updateBatchById(functionsListUp);
    }

}
