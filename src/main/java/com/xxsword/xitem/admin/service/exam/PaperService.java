package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

public interface PaperService extends IService<Paper> {

    void delPaperByIds(String ids);

    void upLastInfo(UserInfo doUserInfo, String ids);

}
