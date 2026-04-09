package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.domain.cms.dto.ArticleDto;
import com.xxsword.xitem.admin.domain.cms.entity.Article;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleData;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.service.cms.ArticleDataService;
import com.xxsword.xitem.admin.service.cms.ArticleService;
import com.xxsword.xitem.admin.utils.DateUtil;
import com.xxsword.xitem.admin.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin/cms")
public class CmsController extends BaseController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDataService articleDataService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("articleList")
    public String recordList() {
        return "admin/cms/articlelist";
    }

    @RequestMapping("articleListData")
    @ResponseBody
    public RestPaging<Article> recordListData(HttpServletRequest request, ArticleDto articleDto, Page<Article> page) {
        List<String> categoryIds = new ArrayList<>();
        if (StringUtils.isNotBlank(articleDto.getCategoryIds())) {
            String[] ids = articleDto.getCategoryIds().split(",");
            for (String id : ids) {
                categoryIds.addAll(categoryService.listCategoryIdByCategoryId(id));
            }
            articleDto.setCategoryAllIds(categoryIds);
        }
        Page<Article> data = articleService.page(page, articleDto.toQuery());
        articleService.setCategoryName(data.getRecords());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    /**
     * 编辑
     */
    @RequestMapping("articleEdit")
    public String articleEdit(String id, Model model) {
        Article article = articleService.getById(id);
        if (article == null) {
            article = new Article();
        } else {
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category == null ? "" : category.getTitle());
            ArticleData articleData = articleDataService.getById(id);
            if (articleData != null) {
                article.setArticleData(articleData);
            }
        }
        model.addAttribute("article", article);
        return "/admin/cms/articleedit";
    }

//    @RequestMapping("editiframe")
//    public String editiframe(HttpServletRequest request, String id, Model model) {
//        ArticleData articleData = new ArticleData();
//        if (StringUtils.isNotBlank(id)) {
//            articleData = articleDataService.getById(id);
//        }
//        model.addAttribute("articleData", articleData);
//        return "/admin/cms/editiframe";// 独立上传图片
//        return "/admin/cms/editiframev2";// 从页面中选择
//    }

    /**
     * 保存
     */
    @RequestMapping("articleSave")
    public String articleSave(HttpServletRequest request, Article article, ArticleData articleData) {
        UserInfo userInfo = Utils.getUserInfo(request);
        article.setBaseInfo(userInfo);
        if (StringUtils.isBlank(article.getId())) {
            article.setHits(0);
            // 新建时，直接赋值这几个字段
            article.setLastUpdate(DateUtil.now());
            article.setLastUserId(userInfo.getId());
        }
        articleService.saveOrUpdate(article);
        articleData.setId(article.getId());
        articleDataService.saveOrUpdate(articleData);
        return httpRedirect(request, "/admin/cms/articleList");
    }
}
