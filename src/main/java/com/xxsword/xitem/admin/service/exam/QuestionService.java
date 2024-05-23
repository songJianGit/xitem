package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.Question;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaperQuestion;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestResult;

import java.util.List;

public interface QuestionService extends IService<Question> {
    /**
     * 给问题赋值其类型名称
     *
     * @param list
     * @return
     */
    List<Question> setQuestionQclass(List<Question> list);

    /**
     * excel导入解析
     * 解析excel，保存题目
     */
    RestResult excelQuestion(String path, UserInfo userInfo);

    void saveQuestionAndOption(UserInfo userInfo, Question question, String optionJson);

    void upLastInfo(UserInfo doUserInfo, String ids);

    void delQuestionByIds(String ids);

    /**
     * 根据题目，获取题目VO
     *
     * @param setOption 是否给题目，赋值选项
     * @param setRight  是否给选项，赋值答案（1.选项是否携带答案 2-answer字段是否赋值）
     * @param setABC    是否给选项，赋值ABC前缀
     * @return
     */
    QuestionVO getQuestionVO(UserPaperQuestion userPaperQuestion, boolean setOption, boolean setRight, boolean setABC);

    /**
     * 根据题目，获取题目VO
     *
     * @param userPaperQuestionList
     * @param setOption             是否给题目，赋值选项
     * @param setRight              是否给选项，赋值答案（1.选项是否携带答案 2-answer字段是否赋值）
     * @param setABC                是否给选项，赋值ABC前缀
     * @return
     */
    List<QuestionVO> getQuestionVO(List<UserPaperQuestion> userPaperQuestionList, boolean setOption, boolean setRight, boolean setABC);

}
