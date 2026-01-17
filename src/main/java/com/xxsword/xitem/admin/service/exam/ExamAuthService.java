package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.dto.ExamAuthDto;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.exam.entity.ExamAuth;
import com.xxsword.xitem.admin.domain.exam.vo.ExamAuthVO;

public interface ExamAuthService extends IService<ExamAuth> {

    /**
     * 添加或更新授权信息
     *
     * @param examId
     * @param userIds
     */
    void upExamAuth(String examId, String userIds);

    Page<ExamAuthVO> pageExamAuthByDto(Page<ExamAuth> page, ExamAuthDto dto);

    /**
     * 该用户是否有该考试的权限
     *
     * @param userId
     * @param exam
     * @return true-有 false-没有
     */
    boolean checkUserExamAuth(String userId, Exam exam);
}
