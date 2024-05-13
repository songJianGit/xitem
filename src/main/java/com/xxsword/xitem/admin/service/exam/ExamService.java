package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;

import java.util.List;

public interface ExamService extends IService<Exam> {
    List<Exam> setExamexstatus(List<Exam> list);
}
