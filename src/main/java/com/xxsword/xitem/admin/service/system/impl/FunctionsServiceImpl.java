package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@Transactional
public class FunctionsServiceImpl extends ServiceImpl<FunctionsMapper, Functions> implements FunctionsService {

    @Override
    public Page<Functions> pageFunctions(Page<Functions> page) {
        LambdaQueryWrapper<Functions> query = Wrappers.lambdaQuery();
        query.eq(Functions::getStatus, 1);
        query.orderByAsc(Functions::getSeq, Functions::getId);
        return page(page, query);
    }

    @Override
    public List<Functions> functionsAll() {
        LambdaQueryWrapper<Functions> query = Wrappers.lambdaQuery();
        query.eq(Functions::getStatus, 1);
        query.orderByAsc(Functions::getSeq);
        return list(query);
    }

    @Override
    public List<Functions> functionsByPId(String pid) {
        LambdaQueryWrapper<Functions> query = Wrappers.lambdaQuery();
        query.eq(Functions::getStatus, 1);
        query.eq(Functions::getPid, pid);
        query.orderByAsc(Functions::getSeq, Functions::getId);
        return list(query);
    }

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
    public void delFunctionsById(String id) {
        Functions functions = getById(id);
        functions.setStatus(0);
        updateById(functions);
        delFunctionsDown(functions);// 递归删除其所有子集
    }

    private void delFunctionsDown(Functions functions) {
        LambdaQueryWrapper<Functions> query = Wrappers.lambdaQuery();
        query.eq(Functions::getPid, functions.getId());
        List<Functions> pf = list(query);
        if (pf != null && pf.size() > 0) {
            for (Functions f : pf) {
                f.setStatus(0);
                updateById(f);
                delFunctionsDown(f);
            }
        }
    }

    @Override
    public void saveFunctionsSeq(String fids, String seqs) {
        String[] fidattr = fids.split(",");
        String[] seqattr = seqs.split(",");
        for (int i = 0; i < fidattr.length; i++) {
            Functions functions = getById(fidattr[i]);
            functions.setSeq(Integer.valueOf(seqattr[i]));
            saveOrUpdate(functions);
        }
    }

}
