package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface ExamService extends IService<Exam> {
    List<Exam> setExamexstatus(List<Exam> list);

    void upLastInfo(UserInfo doUserInfo, String ids);
}
