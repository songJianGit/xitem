package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.dto.ExamDto;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;

import java.util.List;

public interface ExamService extends IService<Exam> {
    List<Exam> setExamexstatus(List<Exam> list);

    void delByIds(String ids);

    /**
     * 考试的发布和下架
     *
     * @param id
     */
    void release(String id);


    /**
     * 根据用户信息，查询其 公开或授权考试
     */
    Page<Exam> pageExamByUser(Page<Exam> page, String userId, ExamDto examDto);

}
