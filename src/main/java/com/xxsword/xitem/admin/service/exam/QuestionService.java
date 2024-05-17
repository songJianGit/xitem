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
     * 按照试卷题目，拿问题对象
     *
     * @param list
     * @param setOption 是否赋值选项信息
     * @return
     */
    List<QuestionVO> listQuestionByUserPaperQuestion(List<UserPaperQuestion> list, boolean setOption);
}
