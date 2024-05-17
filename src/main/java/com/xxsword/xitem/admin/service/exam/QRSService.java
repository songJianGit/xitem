package com.xxsword.xitem.admin.service.exam;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.dto.QRSDto;
import com.xxsword.xitem.admin.domain.exam.dto.QuestionDto;
import com.xxsword.xitem.admin.domain.exam.entity.QRS;
import com.xxsword.xitem.admin.domain.exam.vo.QRSVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface QRSService extends IService<QRS> {

    /**
     * 该规则下，有多少题目
     *
     * @param qrid
     * @return
     */
    Long countQRSByQrid(String qrid);

    void upLastInfo(UserInfo doUserInfo, String ids);

    /**
     * 关联
     *
     * @param qrid  抽提规则
     * @param qids  问题ids
     * @param score
     */
    void addQRS(UserInfo userInfo, String qrid, String qids, Double score);

    /**
     * @param qrid 规则id
     * @param qid  题目id
     * @return
     */
    QRS getQrs(String qrid, String qid);

    /**
     * 更新QRS排序信息
     *
     * @param jsonArray
     */
    void upQRSSeq(JSONArray jsonArray);

    /**
     * 更新QRS分值信息
     *
     * @param jsonArray
     */
    void upQRSScore(JSONArray jsonArray);

    /**
     * 查询QRS
     *
     * @param qrsDto
     * @param questionDto
     * @return
     */
    List<QRSVO> listQRS(QRSDto qrsDto, QuestionDto questionDto);

    /**
     * 查询该规则下的QRS信息
     *
     * @param qrid
     * @return
     */
    List<QRS> listQRSByQrid(String qrid);
}
