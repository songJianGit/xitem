package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaperQuestion;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface UserPaperQuestionService extends IService<UserPaperQuestion> {

    void upLastInfo(UserInfo doUserInfo, String ids);

    /**
     * 根据抽提规则，抽取题目
     *
     * @param usp
     * @return
     */
    List<UserPaperQuestion> newPaperQ(UserPaper usp, UserInfo userInfo);

    /**
     * 已有答题记录题目获取
     *
     * @param usp
     * @return
     */
    List<UserPaperQuestion> getPaperQ(UserPaper usp);
}
