package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

public interface UserPaperService extends IService<UserPaper> {

    void upLastInfo(UserInfo doUserInfo, String ids);

}
