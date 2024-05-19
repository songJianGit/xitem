package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaperQuestion;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface UserPaperQuestionService extends IService<UserPaperQuestion> {

    void upLastInfo(UserInfo doUserInfo, String ids);

    /**
     * 根据抽提规则，抽取题目
     */
    void newPaperQ(UserPaper usp, UserInfo userInfo);

    /**
     * 按照试卷，获取题目
     *
     * @param setOption 是否给题目，赋值选项
     * @param setRight  是否给选项，赋值答案（1.选项是否携带答案 2-answer字段是否赋值）
     * @param setABC    是否给选项，赋值ABC前缀
     * @return
     */
    List<QuestionVO> listQuestionByUserPaper(UserPaper userPaper, boolean setOption, boolean setRight, boolean setABC);
}
