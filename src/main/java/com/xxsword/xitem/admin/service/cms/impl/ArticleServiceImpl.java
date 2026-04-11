package com.xxsword.xitem.admin.service.cms.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.domain.cms.dto.ArticleDto;
import com.xxsword.xitem.admin.domain.cms.entity.Article;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleData;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleUser;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.mapper.cms.ArticleMapper;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.service.cms.ArticleDataService;
import com.xxsword.xitem.admin.service.cms.ArticleService;
import com.xxsword.xitem.admin.service.cms.ArticleUserService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleDataService articleDataService;
    @Autowired
    private ArticleUserService articleUserService;

    @Override
    public void setCategoryName(List<Article> list) {
        for (Article article : list) {
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category == null ? "" : category.getTitle());
        }
    }

    @Override
    public Page<Article> pageArticle(Page<Article> page, ArticleDto articleDto) {
        return super.page(page, articleDto.toQuery());
    }

    @Override
    public void saveArticle(Article article, ArticleData articleData, String userlists) {
        saveOrUpdate(article);
        articleData.setId(article.getId());
        articleDataService.saveOrUpdate(articleData);

        JSONArray users = JSONArray.parseArray(StringEscapeUtils.unescapeHtml4(userlists));

        Set<String> hasUserIds = new HashSet<>();
        List<ArticleUser> userList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            String userId = users.getString(i);
            hasUserIds.add(userId);
            ArticleUser articleUser = articleUserService.getArticleUser(article.getId(), userId);
            if (articleUser == null) {
                articleUser = new ArticleUser();
            }
            articleUser.setUserId(userId);
            articleUser.setAid(article.getId());
            userList.add(articleUser);
        }

        if (!userlists.isEmpty()) {
            articleUserService.saveOrUpdateBatch(userList);
        }

        // 删除未出现的用户
        LambdaQueryWrapper<ArticleUser> del = Wrappers.lambdaQuery();
        del.eq(ArticleUser::getAid, article.getId());
        if (!hasUserIds.isEmpty()) {
            del.notIn(ArticleUser::getUserId, hasUserIds);
        }
        articleUserService.remove(del);

    }
}
