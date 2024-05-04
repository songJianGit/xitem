package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.entity.system.Dict;
import com.xxsword.xitem.admin.mapper.system.DictMapper;
import com.xxsword.xitem.admin.service.system.DictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public void delDictByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            return;
        }
        LambdaQueryWrapper<Dict> query = Wrappers.lambdaQuery();
        query.in(Dict::getId, ids.split(","));
        List<Dict> dictList = list(query);
        for (Dict dict : dictList) {
            dict.setStatus(0);
        }
        updateBatchById(dictList);
    }
}
