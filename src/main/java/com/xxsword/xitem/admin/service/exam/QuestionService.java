package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.Question;
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
     * 给问题赋值其选项
     *
     * @param list
     * @return
     */
    List<Question> setQuestionOption(List<Question> list);

    /**
     * excel导入解析
     * 解析excel，保存题目
     */
    RestResult excelQuestion(String path, UserInfo userInfo);

    void saveQuestionAndOption(UserInfo userInfo, Question question, String optionJson);

    void upLastInfo(UserInfo doUserInfo, String ids);

    void delQuestionByIds(String ids);


    /**
     * 按照ids，一个一个取问题对象，不需要去重。
     *
     * @param qIds
     * @param setOption 是否赋值选项信息
     * @return
     */
    List<Question> listQuestionByIds(List<String> qIds, boolean setOption);
}
