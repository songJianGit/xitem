package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.entity.*;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.UserPaperQuestionMapper;
import com.xxsword.xitem.admin.service.exam.QRSService;
import com.xxsword.xitem.admin.service.exam.QuestionRuleService;
import com.xxsword.xitem.admin.service.exam.QuestionService;
import com.xxsword.xitem.admin.service.exam.UserPaperQuestionService;
import com.xxsword.xitem.admin.utils.DateUtil;
import com.xxsword.xitem.admin.utils.ExamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPaperQuestionServiceImpl extends ServiceImpl<UserPaperQuestionMapper, UserPaperQuestion> implements UserPaperQuestionService {

    @Autowired
    private QuestionRuleService questionRuleService;
    @Autowired
    private QRSService qrsService;
    @Autowired
    private QuestionService questionService;

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<UserPaperQuestion> listUp = new ArrayList<>();
        for (String id : idsS) {
            UserPaperQuestion itemUp = new UserPaperQuestion();
            itemUp.setId(id);
            itemUp.setBaseInfo(doUserInfo);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }

    /**
     * 根据规则生成新的试卷
     *
     * @param userPaper
     */
    @Override
    public void newPaperQ(UserPaper userPaper, UserInfo userInfo) {
        List<QuestionRule> questionRuleList = questionRuleService.listQuestionRuleByPid(userPaper.getPaperid());// 抽题规则
        List<UserPaperQuestion> userPaperQuestionList = new ArrayList<>();
        int seq = 0;
        for (QuestionRule item : questionRuleList) {
            List<QRS> qIds = this.listQuestionByQR(item);
            for (QRS ite : qIds) {
                UserPaperQuestion userPaperQuestion = new UserPaperQuestion();
                userPaperQuestion.setBaseInfo(userInfo);
                userPaperQuestion.setQid(ite.getQid());
                userPaperQuestion.setUserpaperid(userPaper.getId());
                userPaperQuestion.setCdate(DateUtil.now());
                userPaperQuestion.setQscore(ite.getScore());// 取中间表的分数
                userPaperQuestion.setSeq(seq);
                seq++;
                userPaperQuestionList.add(userPaperQuestion);
            }
        }
        saveBatch(userPaperQuestionList);
    }

    private List<QRS> listQuestionByQR(QuestionRule questionRule) {
        List<QRS> qrsList = qrsService.listQRSByQrid(questionRule.getId());
        List<QRS> questionIds = new ArrayList<>();
        List<Integer> list = ExamUtil.generateShuffledUniqueNumbers(questionRule.getNum() > qrsList.size() ? qrsList.size() : questionRule.getNum(), 0, qrsList.size());
        List<Integer> collect = list.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());// 将抽取的题目按照顺序排列
        for (Integer ite : collect) {
            questionIds.add(qrsList.get(ite));
        }
        return questionIds;
    }

    @Override
    public List<UserPaperQuestion> getPaperQ(UserPaper userPaper) {
        LambdaQueryWrapper<UserPaperQuestion> q = Wrappers.lambdaQuery();
        q.eq(UserPaperQuestion::getUserpaperid, userPaper.getId());
        q.orderByAsc(UserPaperQuestion::getSeq, UserPaperQuestion::getId);
        return list(q);
    }

    @Override
    public List<QuestionVO> listQuestionByUserPaper(UserPaper userPaper, boolean setOption, boolean setRight, boolean setABC) {
        List<UserPaperQuestion> list = getPaperQ(userPaper);
        return questionService.getQuestionVO(list, setOption, setRight, setABC);
    }

    @Override
    public List<Integer> checkBlankNum(UserPaper userPaper) {
        List<Integer> info = new ArrayList<>();
        List<UserPaperQuestion> list = getPaperQ(userPaper);
        if (list.size() == 0) {
            return info;
        }
        for (int i = 0; i < list.size(); i++) {
            UserPaperQuestion up = list.get(i);
            if (StringUtils.isBlank(up.getAnswer())) {
                info.add(i + 1);
            }
        }
        return info;
    }
}
