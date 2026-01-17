package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.dto.ExamAuthDto;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.exam.entity.ExamAuth;
import com.xxsword.xitem.admin.domain.exam.vo.ExamAuthVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.ExamAuthMapper;
import com.xxsword.xitem.admin.service.exam.ExamAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExamAuthServiceImpl extends ServiceImpl<ExamAuthMapper, ExamAuth> implements ExamAuthService {

    @Override
    public void upExamAuth(UserInfo userInfo, String examId, String userIds) {
        String[] ids = userIds.split(",");
        List<ExamAuth> examAuthListUp = new ArrayList<>();
        for (String id : ids) {
            ExamAuth examAuth = getExamAuth(examId, id);
            if (examAuth == null) {
                examAuth = new ExamAuth();
                examAuth.setExamId(examId);
                examAuth.setUserId(id);
                examAuthListUp.add(examAuth);
            } else {
                log.info("已跳过，重复授权考试:{},{}", examId, id);
            }
        }
        saveBatch(examAuthListUp);
    }

    @Override
    public Page<ExamAuthVO> pageExamAuthByDto(Page<ExamAuth> page, ExamAuthDto dto) {
        return baseMapper.pageExamAuthByDto(page, dto);
    }

    @Override
    public boolean checkUserExamAuth(String userId, Exam exam) {
        if (exam.getExType() == 1) {
            return true;// 公开考试
        }
        if (exam.getExType() == 0) {
            // 授权考试
            LambdaQueryWrapper<ExamAuth> query = Wrappers.lambdaQuery();
            query.eq(ExamAuth::getStatus, 1);
            query.eq(ExamAuth::getExamId, exam.getId());
            query.eq(ExamAuth::getUserId, userId);
            return count(query) > 0;
        }
        return false;
    }

    private ExamAuth getExamAuth(String examId, String userId) {
        LambdaQueryWrapper<ExamAuth> query = Wrappers.lambdaQuery();
        query.eq(ExamAuth::getStatus, 1);
        query.eq(ExamAuth::getExamId, examId);
        query.eq(ExamAuth::getUserId, userId);
        return getOne(query);
    }
}
