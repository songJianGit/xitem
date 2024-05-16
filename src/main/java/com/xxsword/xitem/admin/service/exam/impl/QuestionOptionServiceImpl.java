package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionOption;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.QuestionOptionMapper;
import com.xxsword.xitem.admin.service.exam.QuestionOptionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionOptionServiceImpl extends ServiceImpl<QuestionOptionMapper, QuestionOption> implements QuestionOptionService {

    @Override
    public List<QuestionOption> questionOptionListByQid(String questionId) {
        return list(new LambdaQueryWrapper<QuestionOption>().eq(QuestionOption::getStatus, 1).eq(QuestionOption::getQid, questionId).orderByAsc(QuestionOption::getId));
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<QuestionOption> listUp = new ArrayList<>();
        for (String id : idsS) {
            QuestionOption itemUp = new QuestionOption();
            itemUp.setId(id);
            itemUp.setBaseInfo(doUserInfo);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }
}
