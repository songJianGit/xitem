package com.xxsword.xitem.admin.service.cms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.cms.dto.ArticleDto;
import com.xxsword.xitem.admin.domain.cms.entity.Article;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleData;
import com.xxsword.xitem.admin.domain.cms.vo.ArticleVO;
import com.xxsword.xitem.admin.model.EVO;

import java.util.List;

public interface ArticleService extends IService<Article> {
    void setCategoryName(List<Article> list);

    void setArticleVOName(List<ArticleVO> list);

    List<Article> listArticle(ArticleDto articleDto);

    void saveArticle(Article article, ArticleData articleData, String userlists);

    List<List<EVO>> exportData(List<ArticleVO> voList, String projectId);
}
