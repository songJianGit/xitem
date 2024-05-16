package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaperQuestion;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

public interface UserPaperQuestionService extends IService<UserPaperQuestion> {

    void upLastInfo(UserInfo doUserInfo, String ids);

}
