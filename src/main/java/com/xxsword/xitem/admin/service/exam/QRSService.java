package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.QRS;
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
     * 给QRS赋值问题对象
     *
     * @param list
     * @return
     */
    List<QRS> qRSsetQuestion(List<QRS> list);

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
}
