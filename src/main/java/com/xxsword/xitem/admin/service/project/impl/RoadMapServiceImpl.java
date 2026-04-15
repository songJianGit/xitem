package com.xxsword.xitem.admin.service.project.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.domain.cms.dto.ArticleDto;
import com.xxsword.xitem.admin.domain.cms.entity.Article;
import com.xxsword.xitem.admin.domain.project.dto.RoadMapDto;
import com.xxsword.xitem.admin.domain.project.entity.RoadMap;
import com.xxsword.xitem.admin.mapper.project.RoadMapMapper;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.service.cms.ArticleService;
import com.xxsword.xitem.admin.service.project.RoadMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoadMapServiceImpl extends ServiceImpl<RoadMapMapper, RoadMap> implements RoadMapService {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public List<RoadMap> listRoadMap(String projectId) {
        if (StringUtils.isBlank(projectId)) {
            return new ArrayList<>();
        }
        RoadMapDto roadMapDto = new RoadMapDto();
        roadMapDto.setProjectId(projectId);
        return list(roadMapDto.toQuery());
    }

    @Override
    public List<RoadMap> setRoadMapPercentage(List<RoadMap> list) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> roadMapIds = list.stream().map(RoadMap::getId).collect(Collectors.toList());
        ArticleDto articleDto = new ArticleDto();
        articleDto.setRoadMapIds(roadMapIds);
        List<Article> articles = articleService.list(articleDto.toQuery());
        Map<String, List<Article>> articleMap = articles.stream().collect(Collectors.groupingBy(Article::getRoadmapId));

        List<Category> categoryListLevel = categoryService.categoryC(Constant.TASK_STATUS);

        for (RoadMap roadMap : list) {
            if (articleMap.containsKey(roadMap.getId())) {
                List<Article> articleList = articleMap.get(roadMap.getId());
                Map<String, Long> articleCategoryNumber = articleList.stream().collect(Collectors.groupingBy(Article::getCategoryId, Collectors.counting()));
                String[] p = new String[categoryListLevel.size()];
                for (int i = 0; i < categoryListLevel.size(); i++) {
                    Category category = categoryListLevel.get(i);
                    Long num = articleCategoryNumber.get(category.getId());
                    p[i] = category.getTitle() + " : " + (num == null ? 0 : num);
                }
                roadMap.setPercentage(String.join(" ", p));
            } else {
                roadMap.setPercentage("无");
            }
        }
        return null;
    }
}
