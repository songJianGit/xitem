package com.xxsword.xitem.admin.service.cms.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleUser;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.cms.ArticleUserMapper;
import com.xxsword.xitem.admin.service.cms.ArticleUserService;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleUserServiceImpl extends ServiceImpl<ArticleUserMapper, ArticleUser> implements ArticleUserService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public List<ArticleUser> listArticleUserBy(String aid) {
        LambdaQueryWrapper<ArticleUser> q = Wrappers.lambdaQuery();
        q.eq(ArticleUser::getStatus, 1);
        q.eq(ArticleUser::getAid, aid);
        return list(q);
    }

    @Override
    public ArticleUser getArticleUser(String aid, String userId) {
        LambdaQueryWrapper<ArticleUser> q = Wrappers.lambdaQuery();
        q.eq(ArticleUser::getStatus, 1);
        q.eq(ArticleUser::getAid, aid);
        q.eq(ArticleUser::getUserId, userId);
        return getOne(q);
    }

    @Override
    public Map<String, List<ArticleUser>> mapArticleUser(List<String> aid) {
        if (aid.isEmpty()) {
            return new HashMap<>();
        }
        LambdaQueryWrapper<ArticleUser> q = Wrappers.lambdaQuery();
        q.eq(ArticleUser::getStatus, 1);
        q.in(ArticleUser::getAid, aid);
        List<ArticleUser> list = list(q);
        return list.stream().collect(Collectors.groupingBy(ArticleUser::getAid, Collectors.toList()));
    }

    @Override
    public void setArticleUserName(List<ArticleUser> list) {
        List<String> userIds = list.stream().map(ArticleUser::getUserId).collect(Collectors.toList());
        List<UserInfo> userInfoList = userInfoService.listByIds(userIds);
        Map<String, UserInfo> userInfoMap = userInfoList.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        for (ArticleUser item : list) {
            if (userInfoMap.containsKey(item.getUserId())) {
                UserInfo userInfo = userInfoMap.get(item.getUserId());
                item.setUserName(userInfo.getUserName());
            }
        }
    }
}
