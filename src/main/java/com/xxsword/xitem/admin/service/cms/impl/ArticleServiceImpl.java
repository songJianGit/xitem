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
import com.xxsword.xitem.admin.domain.cms.vo.ArticleVO;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.mapper.cms.ArticleMapper;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.service.cms.ArticleDataService;
import com.xxsword.xitem.admin.service.cms.ArticleService;
import com.xxsword.xitem.admin.service.cms.ArticleUserService;
import com.xxsword.xitem.admin.service.project.ProjectService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleDataService articleDataService;
    @Autowired
    private ArticleUserService articleUserService;
    @Autowired
    private ProjectService projectService;

    @Override
    public void setCategoryName(List<Article> list) {
        List<String> categoryIds = list.stream().map(Article::getCategoryId).collect(Collectors.toList());
        List<Category> categoryList = categoryService.listByIds(categoryIds);
        Map<String, Category> categoryMap = categoryList.stream().collect(Collectors.toMap(Category::getId, Function.identity()));
        for (Article article : list) {
            if (StringUtils.isBlank(article.getCategoryId()) || !categoryMap.containsKey(article.getCategoryId())) {
                continue;
            }
            article.setCategoryName(categoryMap.get(article.getCategoryId()).getTitle());
        }

        List<String> levelIds = list.stream().map(Article::getLevelId).collect(Collectors.toList());
        List<Category> levelList = categoryService.listByIds(levelIds);
        Map<String, Category> levelMap = levelList.stream().collect(Collectors.toMap(Category::getId, Function.identity()));
        for (Article article : list) {
            if (StringUtils.isBlank(article.getLevelId()) || !levelMap.containsKey(article.getLevelId())) {
                continue;
            }
            article.setLevelName(levelMap.get(article.getLevelId()).getTitle());
        }
    }

    @Override
    public void setArticleVOName(List<ArticleVO> list) {
        List<String> categoryIds = list.stream().map(ArticleVO::getCategoryId).collect(Collectors.toList());
        List<Category> categoryList = categoryService.listByIds(categoryIds);
        Map<String, Category> categoryMap = categoryList.stream().collect(Collectors.toMap(Category::getId, Function.identity()));
        for (ArticleVO article : list) {
            if (StringUtils.isBlank(article.getCategoryId()) || !categoryMap.containsKey(article.getCategoryId())) {
                continue;
            }
            article.setCategoryName(categoryMap.get(article.getCategoryId()).getTitle());
        }

        List<String> levelIds = list.stream().map(ArticleVO::getLevelId).collect(Collectors.toList());
        List<Category> levelList = categoryService.listByIds(levelIds);
        Map<String, Category> levelMap = levelList.stream().collect(Collectors.toMap(Category::getId, Function.identity()));
        for (ArticleVO article : list) {
            if (StringUtils.isBlank(article.getLevelId()) || !levelMap.containsKey(article.getLevelId())) {
                continue;
            }
            article.setLevelName(levelMap.get(article.getLevelId()).getTitle());
        }

        List<String> projectIds = list.stream().map(ArticleVO::getPid).collect(Collectors.toList());
        List<Project> projectList = projectService.listByIds(projectIds);
        Map<String, Project> projectMap = projectList.stream().collect(Collectors.toMap(Project::getId, Function.identity()));
        for (ArticleVO article : list) {
            if (StringUtils.isBlank(article.getPid()) || !projectMap.containsKey(article.getPid())) {
                continue;
            }
            article.setProjectName(projectMap.get(article.getPid()).getTitle());
        }
    }

    @Override
    public List<Article> listArticle(ArticleDto articleDto) {
        return list(articleDto.toQuery());
    }

    @Override
    @Transactional
    public void saveArticle(Article article, ArticleData articleData, String userlists) {
        saveOrUpdate(article);
        articleData.setId(article.getId());
        articleDataService.saveOrUpdate(articleData);

        if (StringUtils.isNotBlank(userlists)) {
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
}
