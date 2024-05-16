package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.entity.QRS;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.QRSMapper;
import com.xxsword.xitem.admin.service.exam.QRSService;
import com.xxsword.xitem.admin.service.exam.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QRSServiceImpl extends ServiceImpl<QRSMapper, QRS> implements QRSService {

    @Autowired
    private QuestionService questionService;

    @Override
    public Long countQRSByQrid(String qrid) {
        QueryWrapper<QRS> q = Wrappers.query();
        q.eq("qrid", qrid);
        return count(q);
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<QRS> listUp = new ArrayList<>();
        for (String id : idsS) {
            QRS itemUp = new QRS();
            itemUp.setId(id);
            itemUp.setBaseInfo(doUserInfo);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public List<QRS> qRSsetQuestion(List<QRS> list) {
        for (QRS item : list) {
            item.setQuestion(questionService.getById(item.getQid()));
        }
        return list;
    }

    @Override
    public void addQRS(UserInfo userInfo, String qrid, String qids, Double score) {
        String[] ids = qids.split(",");
        int seq = 0;
        List<QRS> qrsList = list(new Page<>(1, 1), new LambdaQueryWrapper<QRS>().eq(QRS::getStatus, 1).eq(QRS::getQrid, qrid).orderByDesc(QRS::getSeq));
        if (!qrsList.isEmpty()) {
            seq = qrsList.get(0).getSeq();
        }
        List<QRS> qrsListSave = new ArrayList<>();
        for (String item : ids) {
            QRS qrs = getQrs(qrid, item);
            if (qrs == null) {
                qrs = new QRS();
                qrs.setQid(item);
                qrs.setScore(score);
                qrs.setQrid(qrid);
                qrs.setSeq(++seq);
                qrs.setBaseInfo(userInfo);
                qrsListSave.add(qrs);
            }
        }
        saveOrUpdateBatch(qrsListSave);
    }

    @Override
    public QRS getQrs(String qrid, String qid) {
        LambdaQueryWrapper<QRS> q = Wrappers.lambdaQuery();
        q.eq(QRS::getQrid, qrid);
        q.eq(QRS::getQid, qid);
        return getOne(q);
    }
}
