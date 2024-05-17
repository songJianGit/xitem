package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.UserPaperMapper;
import com.xxsword.xitem.admin.service.exam.UserPaperQuestionService;
import com.xxsword.xitem.admin.service.exam.UserPaperService;
import com.xxsword.xitem.admin.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserPaperServiceImpl extends ServiceImpl<UserPaperMapper, UserPaper> implements UserPaperService {
    @Autowired
    private UserPaperQuestionService userPaperQuestionService;

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<UserPaper> listUp = new ArrayList<>();
        for (String id : idsS) {
            UserPaper itemUp = new UserPaper();
            itemUp.setId(id);
            itemUp.setBaseInfo(doUserInfo);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }

    @Override
    @Transactional
    public UserPaper getUserPaper(UserInfo userInfo, Paper paper, String examId, Integer type) {
        if (type == null) {
            type = 1;
        }
        UserPaper userPaper = null;
        if (type == 1) {
            List<UserPaper> listUserPaper = this.listUserPaper(userInfo.getId(), paper.getId(), examId, 0);// 获取其未提交的答题记录
            if (listUserPaper == null || listUserPaper.size() == 0) {
                userPaper = this.newUserPaper(userInfo, paper, examId);
            } else {
                userPaper = listUserPaper.get(0);
                paper.setUserPaperQuestionList(userPaperQuestionService.getPaperQ(userPaper));
                if (listUserPaper.size() > 1) {
                    this.clearUserPaper(listUserPaper);// 一场考试中，一个用户对一张试卷只能有一条substatus为0的数据
                }
            }
        }
        if (type == 2) {
            userPaper = this.newUserPaper(userInfo, paper, examId);
        }
        return userPaper;
    }

    /**
     * 获取某一状态的用户答题记录
     *
     * @param userId
     * @param paperId
     * @param examId
     * @param subStatus 0-初始 1-已提交  null-查询全部
     * @return
     */
    private List<UserPaper> listUserPaper(String userId, String paperId, String examId, Integer subStatus) {
        LambdaQueryWrapper<UserPaper> q = Wrappers.lambdaQuery();
        q.eq(UserPaper::getStatus, 1);
        q.eq(UserPaper::getUserid, userId);
        q.eq(UserPaper::getPaperid, paperId);
        q.eq(UserPaper::getExamid, examId);
        if (subStatus != null) {
            q.eq(UserPaper::getSubstatus, subStatus);
        }
        q.orderByDesc(UserPaper::getCdate, UserPaper::getId);
        return list(q);
    }

    private void clearUserPaper(List<UserPaper> listUserPaper) {
        if (listUserPaper.size() == 0) {
            return;
        }
        if (listUserPaper.size() == 1) {
            return;
        }
        List<UserPaper> userPaperListUp = new ArrayList<>();
        for (int i = 1; i < listUserPaper.size(); i++) {
            UserPaper up = new UserPaper();
            up.setId(listUserPaper.get(i).getId());
            up.setStatus(0);
            userPaperListUp.add(up);
        }
        updateBatchById(userPaperListUp);
    }

    /**
     * 新的试卷
     *
     * @param userInfo
     * @param paper
     * @param examId
     * @return
     */
    private UserPaper newUserPaper(UserInfo userInfo, Paper paper, String examId) {
        UserPaper userPaper = new UserPaper();
        userPaper.setBaseInfo(userInfo);
        userPaper.setSubstatus(0);
        userPaper.setExamid(examId);
        userPaper.setPaperid(paper.getId());
        userPaper.setUserid(userInfo.getId());
        save(userPaper);
        paper.setUserPaperQuestionList(userPaperQuestionService.newPaperQ(userPaper, userInfo));// 新的题目
        return userPaper;
    }
}
