package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.dto.ExamDto;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.ExamMapper;
import com.xxsword.xitem.admin.service.exam.ExamService;
import com.xxsword.xitem.admin.utils.ExamUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

    @Override
    public List<Exam> setExamexstatus(List<Exam> list) {
        for (Exam item : list) {
            item.setExStatus(ExamUtil.getExamStatus(item));
        }
        return list;
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<Exam> listUp = new ArrayList<>();
        for (String id : idsS) {
            Exam examUp = new Exam();
            examUp.setId(id);
            examUp.setBaseInfo(doUserInfo);
            listUp.add(examUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void delByIds(UserInfo userInfo, String ids) {
        String[] idsS = ids.split(",");
        List<Exam> listUp = new ArrayList<>();
        for (String id : idsS) {
            Exam examUp = new Exam();
            examUp.setId(id);
            examUp.setBaseInfo(userInfo);
            examUp.setStatus(0);
            listUp.add(examUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void release(UserInfo userInfo, String id) {
        Exam exam = getById(id);
        Exam examUp = new Exam();
        examUp.setId(id);
        examUp.setBaseInfo(userInfo);
        if (exam.getReleaseStatus() == null || exam.getReleaseStatus() == 0 || exam.getReleaseStatus() == 2) {
            examUp.setReleaseStatus(1);
        }
        if (exam.getReleaseStatus() == 1) {
            examUp.setReleaseStatus(2);
        }
        updateById(examUp);
    }

    @Override
    public Page<Exam> pageExamByUser(Page<Exam> page, String userId, ExamDto examDto) {
        Integer exType = examDto.getExType();
        if (exType.equals(1)) {
            return page(page, examDto.toQuery());
        }
        if (exType.equals(0)) {
            return baseMapper.pageExam0ByUser(page, userId, examDto);
        }
        return null;
    }

}
