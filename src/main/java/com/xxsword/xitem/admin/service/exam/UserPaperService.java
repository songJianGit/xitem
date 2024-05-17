package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

public interface UserPaperService extends IService<UserPaper> {

    void upLastInfo(UserInfo doUserInfo, String ids);

    /**
     * 获取未提交的UserPaper，没有则新增一个
     * <p>
     * 该方法给paper的userPaperQuestionList赋值
     *
     * @param userInfo
     * @param paper
     * @param examId
     * @param type     1-根据用户，试卷，考试信息获取，有则返回，无则新增 2-永远产生新的试卷
     */
    UserPaper getUserPaper(UserInfo userInfo, Paper paper, String examId, Integer type);

}
