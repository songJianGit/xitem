package com.xxsword.xitem.admin.domain.cms.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.cms.entity.Article;
import com.xxsword.xitem.admin.model.PageM;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleDto extends PageM {
    private String projectId;
    private List<String> projectIds;
    private String title;
    private String categoryIds;// 承接页面参数，可以逗号分隔
    private List<String> categoryAllIds;// 递归搜索后的所有id
    private Integer atype;// 1-项目任务 2-项目文章
    private String roadMapId;// 里程碑
    private List<String> roadMapIds;// 里程碑

    public LambdaQueryWrapper<Article> toQuery() {
        return new LambdaQueryWrapper<Article>().eq(Article::getStatus, 1)
                .eq(StringUtils.isNotBlank(projectId), Article::getPid, projectId)
                .eq(atype != null, Article::getAtype, atype)
                .eq(StringUtils.isNotBlank(roadMapId), Article::getRoadmapId, roadMapId)
                .like(StringUtils.isNotBlank(title), Article::getTitle, title)
                .in(categoryAllIds != null && !categoryAllIds.isEmpty(), Article::getCategoryId, categoryAllIds)
                .in(projectIds != null && !projectIds.isEmpty(), Article::getPid, projectIds)
                .in(roadMapIds != null && !roadMapIds.isEmpty(), Article::getRoadmapId, roadMapIds)
                .orderByDesc(Article::getCreateDate, Article::getId);
    }
}
