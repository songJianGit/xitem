package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.entity.QRS;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionRule;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.QuestionRuleMapper;
import com.xxsword.xitem.admin.service.exam.QRSService;
import com.xxsword.xitem.admin.service.exam.QuestionRuleService;
import com.xxsword.xitem.admin.utils.Utils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        return listQuestionRuleByPid(pid, true);
    }

    /**
     * @param pid
     * @param orderFlag 是否需要排序查询
     * @return
     */
    private List<QuestionRule> listQuestionRuleByPid(String pid, boolean orderFlag) {
        LambdaQueryWrapper<QuestionRule> q = Wrappers.lambdaQuery();
        q.eq(QuestionRule::getStatus, 1);
        q.eq(QuestionRule::getPaperid, pid);
        if (orderFlag) {
            q.orderByAsc(QuestionRule::getSeq, QuestionRule::getId);
        }
        return list(q);
    }

    @Override
    public Double getPaperScore(String paperId) {
        List<QuestionRule> questionRuleList = listQuestionRuleByPid(paperId, false);// 抽题规则
        double score = 0D;
        for (QuestionRule questionRule : questionRuleList) {
            List<QRS> qrsList = qrsService.listQRSByQrid(questionRule.getId(), false);
            if (qrsList == null || qrsList.isEmpty()) {
                continue;
            }
            if (questionRule.getNum() >= qrsList.size()) {// 抽取题目数，大于等于题目总数
                double qrsScore = Utils.sum(qrsList.stream().map(QRS::getScore).filter(Objects::nonNull).collect(Collectors.toList()));
                score = Utils.sum(score, qrsScore);
            } else {
                Double referenceScore = qrsList.get(0).getScore();
                boolean eq = qrsList.stream().allMatch(user -> Objects.equals(user.getScore(), referenceScore));
                if (eq) {// 抽取的题目书中，题目分值全部相等，分数才有意义
                    List<QRS> qrsListSub = qrsList.subList(0, questionRule.getNum());
                    double qrsScore = Utils.sum(qrsListSub.stream().map(QRS::getScore).filter(Objects::nonNull).collect(Collectors.toList()));
                    score = Utils.sum(score, qrsScore);
                } else {
                    return -1D;// 不等则直接返回-1，表示分数无效
                }
            }
        }
        return score;
    }

    @Override
    public Integer getPaperQNum(String paperId) {
        List<QuestionRule> questionRuleList = listQuestionRuleByPid(paperId);// 抽题规则
        long sum = 0L;
        for (QuestionRule questionRule : questionRuleList) {
            Long qrsCount = qrsService.countQRSByQrid(questionRule.getId());
            if (questionRule.getNum() <= qrsCount) {// 抽取题目数，小于等于题目总数
                sum = sum + questionRule.getNum();
            } else {
                sum = sum + qrsCount;
            }
        }
        return (int) sum;
    }

    @Override
    public void questionRuleSeq(UserInfo userInfo, String id1, String id2) {
        QuestionRule questionRule1 = getById(id1);
        QuestionRule questionRule2 = getById(id2);
        QuestionRule questionRule1Up = new QuestionRule();
        QuestionRule questionRule2Up = new QuestionRule();
        questionRule1Up.setId(id1);
        questionRule1Up.setSeq(questionRule2.getSeq());
        questionRule1Up.setBaseInfo(userInfo);
        questionRule2Up.setId(id2);
        questionRule2Up.setSeq(questionRule1.getSeq());
        questionRule2Up.setBaseInfo(userInfo);
        List<QuestionRule> listUp = new ArrayList<>();
        listUp.add(questionRule1Up);
        listUp.add(questionRule2Up);
        updateBatchById(listUp);
    }


}
