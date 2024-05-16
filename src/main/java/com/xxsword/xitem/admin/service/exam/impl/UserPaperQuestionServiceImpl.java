package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaperQuestion;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.UserPaperQuestionMapper;
import com.xxsword.xitem.admin.service.exam.UserPaperQuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserPaperQuestionServiceImpl extends ServiceImpl<UserPaperQuestionMapper, UserPaperQuestion> implements UserPaperQuestionService {

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<UserPaperQuestion> listUp = new ArrayList<>();
        for (String id : idsS) {
            UserPaperQuestion itemUp = new UserPaperQuestion();
            itemUp.setId(id);
            itemUp.setBaseInfo(doUserInfo);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }

}
