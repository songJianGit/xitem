package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.entity.system.Dict;
import com.xxsword.xitem.admin.mapper.system.DictMapper;
import com.xxsword.xitem.admin.service.system.DictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public void delDictByIds(String dictIds) {
        String[] ids = dictIds.split(",");
        List<Dict> dictListUp = new ArrayList<>();
        for (String id : ids) {
            Dict dictUp = new Dict();
            dictUp.setId(id);
            dictUp.setStatus(0);
            dictListUp.add(dictUp);
        }
        updateBatchById(dictListUp);
    }
}
