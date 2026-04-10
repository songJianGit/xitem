package com.xxsword.xitem.admin.service.cms.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleUser;
import com.xxsword.xitem.admin.mapper.cms.ArticleUserMapper;
import com.xxsword.xitem.admin.service.cms.ArticleUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleUserServiceImpl extends ServiceImpl<ArticleUserMapper, ArticleUser> implements ArticleUserService {

    @Override
    public List<ArticleUser> listArticleUserBy(String aid) {
        LambdaQueryWrapper<ArticleUser> q = Wrappers.lambdaQuery();
        q.eq(ArticleUser::getStatus, 1);
        q.eq(ArticleUser::getAid, aid);
        return list(q);
    }
}
