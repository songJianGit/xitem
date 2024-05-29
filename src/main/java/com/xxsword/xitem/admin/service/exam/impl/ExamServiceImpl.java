package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
            item.setExstatus(ExamUtil.getExamStatus(item));
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
    public void delExamByIds(String ids) {
        String[] idsS = ids.split(",");
        List<Exam> listUp = new ArrayList<>();
        for (String id : idsS) {
            Exam exam = new Exam();
            exam.setId(id);
            exam.setStatus(0);
            listUp.add(exam);
        }
        updateBatchById(listUp);
    }

}
