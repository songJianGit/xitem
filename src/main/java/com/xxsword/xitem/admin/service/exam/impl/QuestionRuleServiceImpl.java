package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionRule;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.QuestionRuleMapper;
import com.xxsword.xitem.admin.service.exam.QRSService;
import com.xxsword.xitem.admin.service.exam.QuestionRuleService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionRuleServiceImpl extends ServiceImpl<QuestionRuleMapper, QuestionRule> implements QuestionRuleService {

    @Autowired
    private QRSService qrsService;

    @Override
    public QuestionRule addQuestionRule(UserInfo userInfo, String paperId) {
        QuestionRule questionRule = new QuestionRule();
        questionRule.setBaseInfo(userInfo);
        questionRule.setTitle("规则" + (countByPaperId(paperId) + 1));
        questionRule.setSeq(DateTime.now().getMillis());
        questionRule.setNum(0);
        questionRule.setPaperid(paperId);
        save(questionRule);
        return questionRule;
    }

    @Override
    public void delByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            return;
        }
        String[] idsS = ids.split(",");
        List<QuestionRule> listUp = new ArrayList<>();
        for (String id : idsS) {
            QuestionRule questionRuleUp = new QuestionRule();
            questionRuleUp.setId(id);
            questionRuleUp.setStatus(0);
            listUp.add(questionRuleUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<QuestionRule> listUp = new ArrayList<>();
        for (String id : idsS) {
            QuestionRule itemUp = new QuestionRule();
            itemUp.setId(id);
            itemUp.setBaseInfo(doUserInfo);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public Long countByPaperId(String paperId) {
        return count(new LambdaQueryWrapper<QuestionRule>().eq(QuestionRule::getStatus, 1).eq(QuestionRule::getPaperid, paperId));
    }

    @Override
    public List<QuestionRule> setQuestionRuleSNum(List<QuestionRule> list) {
        for (QuestionRule item : list) {
            item.setSnum(Math.toIntExact(qrsService.countQRSByQrid(item.getId())));
        }
        return list;
    }

    @Override
    public List<QuestionRule> listQuestionRuleByPid(String pid) {
        LambdaQueryWrapper<QuestionRule> q = Wrappers.lambdaQuery();
        q.eq(QuestionRule::getStatus, 1);
        q.eq(QuestionRule::getPaperid, pid);
        q.orderByAsc(QuestionRule::getSeq, QuestionRule::getId);
        return list(q);
    }
}
