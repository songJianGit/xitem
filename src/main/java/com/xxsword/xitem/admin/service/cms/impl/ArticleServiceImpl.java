package com.xxsword.xitem.admin.service.cms.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.domain.cms.dto.ArticleDto;
import com.xxsword.xitem.admin.domain.cms.entity.Article;
import com.xxsword.xitem.admin.mapper.cms.ArticleMapper;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.service.cms.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

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
}
