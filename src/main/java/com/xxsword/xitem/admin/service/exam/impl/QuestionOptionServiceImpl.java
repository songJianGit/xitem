package com.xxsword.xitem.admin.service.exam.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionOption;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.QuestionOptionMapper;
import com.xxsword.xitem.admin.service.exam.QuestionOptionService;
import com.xxsword.xitem.admin.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionOptionServiceImpl extends ServiceImpl<QuestionOptionMapper, QuestionOption> implements QuestionOptionService {

    @Autowired
    private RedisService redisService;

    @Override
    public List<QuestionOption> questionOptionListByQid(String questionId) {
        String data = redisService.getValueByKey(questionId);
        List<QuestionOption> list;
        if (StringUtils.isBlank(data)) {
            list = list(new LambdaQueryWrapper<QuestionOption>().eq(QuestionOption::getStatus, 1).eq(QuestionOption::getQid, questionId).orderByAsc(QuestionOption::getId));
            redisService.setKeyValue(questionId, JSONArray.toJSONString(list), 10);
        } else {
            list = JSONArray.parseArray(data, QuestionOption.class);
        }
        return list;
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<QuestionOption> listUp = new ArrayList<>();
        for (String id : idsS) {
            QuestionOption itemUp = new QuestionOption();
            itemUp.setId(id);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }
}
