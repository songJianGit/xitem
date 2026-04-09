package com.xxsword.xitem.admin.service.cms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.cms.dto.ArticleDto;
import com.xxsword.xitem.admin.domain.cms.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    void setCategoryName(List<Article> list);

    Page<Article> pageArticle(Page<Article> page, ArticleDto articleDto);
}
