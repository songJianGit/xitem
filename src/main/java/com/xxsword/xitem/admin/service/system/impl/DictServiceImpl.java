package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.entity.Dict;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.system.DictMapper;
import com.xxsword.xitem.admin.service.system.DictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public void delByIds(String dictIds) {
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

    @Override
    public void upLastInfo(UserInfo doUserInfo, String dictIds) {
        String[] ids = dictIds.split(",");
        List<Dict> listUp = new ArrayList<>();
        for (String id : ids) {
            Dict dictUp = new Dict();
            dictUp.setId(id);
            dictUp.setBaseInfo(doUserInfo);
            listUp.add(dictUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public List<Dict> listDictByType(String type) {
        return list(new LambdaQueryWrapper<Dict>().eq(Dict::getType, type).eq(Dict::getStatus, 1));
    }

    @Override
    public Map<String, Dict> mapDictByType(String type) {
        List<Dict> listDictByType = listDictByType(type);
        Map<String, Dict> map = new HashMap<>();
        for (Dict item : listDictByType) {
            map.put(item.getId(), item);
        }
        return map;
    }
}
