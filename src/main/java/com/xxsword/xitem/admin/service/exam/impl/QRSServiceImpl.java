package com.xxsword.xitem.admin.service.exam.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.dto.QRSDto;
import com.xxsword.xitem.admin.domain.exam.dto.QuestionDto;
import com.xxsword.xitem.admin.domain.exam.entity.QRS;
import com.xxsword.xitem.admin.domain.exam.vo.QRSVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.QRSMapper;
import com.xxsword.xitem.admin.service.exam.QRSService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QRSServiceImpl extends ServiceImpl<QRSMapper, QRS> implements QRSService {

    @Override
    public Long countQRSByQrid(String qrid) {
        LambdaQueryWrapper<QRS> q = Wrappers.lambdaQuery();
        q.eq(QRS::getQrId, qrid);
        return count(q);
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<QRS> listUp = new ArrayList<>();
        for (String id : idsS) {
            QRS itemUp = new QRS();
            itemUp.setId(id);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void addQRS(UserInfo userInfo, String qrid, String qids, Double score) {
        String[] ids = qids.split(",");
        int seq = 0;
        List<QRS> qrsList = list(new Page<>(1, 1), new LambdaQueryWrapper<QRS>().eq(QRS::getStatus, 1).eq(QRS::getQrId, qrid).orderByDesc(QRS::getSeq));
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
                qrs.setQrId(qrid);
                qrs.setSeq(++seq);
                qrsListSave.add(qrs);
            }
        }
        saveOrUpdateBatch(qrsListSave);
    }

    @Override
    public QRS getQrs(String qrid, String qid) {
        LambdaQueryWrapper<QRS> q = Wrappers.lambdaQuery();
        q.eq(QRS::getQrId, qrid);
        q.eq(QRS::getQid, qid);
        return getOne(q);
    }

    @Override
    public void upQRSSeq(JSONArray jsonArray) {
        List<QRS> qrsList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject j = jsonArray.getJSONObject(i);
            QRS qrs = new QRS();
            qrs.setId(j.getString("id"));
            Integer s = j.getInteger("seq");
            if (s == null) {
                continue;
            }
            qrs.setSeq(s);
            qrsList.add(qrs);
        }
        updateBatchById(qrsList);
    }

    @Override
    public void upQRSScore(JSONArray jsonArray) {
        List<QRS> qrsList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject j = jsonArray.getJSONObject(i);
            QRS qrs = new QRS();
            qrs.setId(j.getString("id"));
            Double d = j.getDouble("score");
            if (d == null) {
                continue;
            }
            qrs.setScore(d);
            qrsList.add(qrs);
        }
        updateBatchById(qrsList);
    }

    @Override
    public List<QRSVO> listQRS(QRSDto qrsDto, QuestionDto questionDto) {
        return baseMapper.listQRS(qrsDto, questionDto);
    }

    @Override
    public List<QRS> listQRSByQrid(String qrid, boolean orderFlag) {
        LambdaQueryWrapper<QRS> q = Wrappers.lambdaQuery();
        q.eq(QRS::getQrId, qrid);
        if (orderFlag) {
            q.orderByAsc(QRS::getSeq, QRS::getId);
        }
        return list(q);
    }

    @Override
    public List<QRS> listQRSByQrid(String qrid) {
        return listQRSByQrid(qrid, true);
    }
}
