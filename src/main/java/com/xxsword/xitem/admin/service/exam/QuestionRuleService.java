package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionRule;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface QuestionRuleService extends IService<QuestionRule> {

    /**
     * 新增一条抽提规则
     * <p>
     * 规则名称默认
     * 抽提0
     * 总题0
     *
     * @param userInfo
     * @return
     */
    QuestionRule addQuestionRule(UserInfo userInfo, String paperId);

    void delByIds(String ids);

    void upLastInfo(UserInfo doUserInfo, String ids);

    /**
     * 该试卷id下，有多少条规则
     *
     * @param paperId
     * @return
     */
    Long countByPaperId(String paperId);

    /**
     * 赋值题目总数字段
     * @param list
     * @return
     */
    List<QuestionRule> setQuestionRuleSNum(List<QuestionRule> list);

    /**
     * 试卷id获取抽提规则
     *
     * @param pid
     * @return
     */
    List<QuestionRule> listQuestionRuleByPid(String pid);
}
