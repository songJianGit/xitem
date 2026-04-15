package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.constant.RoleSetting;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.domain.cms.convert.CmsConvert;
import com.xxsword.xitem.admin.domain.cms.dto.ArticleDto;
import com.xxsword.xitem.admin.domain.cms.entity.Article;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleData;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleUser;
import com.xxsword.xitem.admin.domain.cms.vo.ArticleVO;
import com.xxsword.xitem.admin.domain.cms.vo.CommentsVO;
import com.xxsword.xitem.admin.domain.project.dto.ProjectDto;
import com.xxsword.xitem.admin.domain.project.dto.ProjectUserDto;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.domain.project.entity.RoadMap;
import com.xxsword.xitem.admin.domain.project.vo.AUVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.service.cms.ArticleDataService;
import com.xxsword.xitem.admin.service.cms.ArticleService;
import com.xxsword.xitem.admin.service.cms.ArticleUserService;
import com.xxsword.xitem.admin.service.cms.CommentsService;
import com.xxsword.xitem.admin.service.project.ProjectService;
import com.xxsword.xitem.admin.service.project.ProjectUserService;
import com.xxsword.xitem.admin.service.project.RoadMapService;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/cms")
public class CmsController extends BaseController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDataService articleDataService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private ArticleUserService articleUserService;
    @Autowired
    private RoadMapService roadMapService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("mytask")
    public String mytask(HttpServletRequest request, Model model) {
        List<Category> categoryList = categoryService.categoryC(Constant.TASK_STATUS);
        List<Category> categoryListLevel = categoryService.categoryC(Constant.TASK_STATUS_LEVEL);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("categoryListLevel", categoryListLevel);
        return "admin/mytask";
    }

    @RequestMapping("articleList")
    public String articleList(HttpServletRequest request, Model model) {
        List<Category> categoryList = categoryService.categoryC(Constant.TASK_STATUS);
        List<Category> categoryListLevel = categoryService.categoryC(Constant.TASK_STATUS_LEVEL);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("categoryListLevel", categoryListLevel);
        return "admin/cms/articlelist";
    }

    @RequestMapping("articlelistwiki")
    public String articlelistwiki(HttpServletRequest request, Model model) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setAtype(2);
        articleDto.setProjectId(Utils.getProjectId(request));
        articleDto.setCurrent(1);
        articleDto.setSize(500);// 只拿500篇文章
        Page<Article> data = articleService.page(articleDto.toPage(), articleDto.toQuery());
        List<Article> articleList = data.getRecords();
        model.addAttribute("alist", articleList);

        if (!articleList.isEmpty()) {
            Article article = articleList.get(0);
            model.addAttribute("articleId", article.getId());
        }
        return "admin/cms/articlelistwiki";
    }

    @RequestMapping("articleListRoadMap")
    public String articleListRoadMap(String roadMapId, Model model) {
        model.addAttribute("roadMapId", roadMapId);
        return "admin/cms/articlelistroadmap";
    }

    @RequestMapping("articlelistwikishow")
    public String articlelistwikishow(HttpServletRequest request, String id, Model model) {
        if (StringUtils.isBlank(id)) {
            return "admin/cms/articlelistwikishow";
        }
        Article article = articleService.getById(id);
        ArticleData articleData = articleDataService.getById(article.getId());
        UserInfo createUser = userInfoService.getById(article.getCreateUserId());
        if (createUser != null) {
            article.setCreateUserName(createUser.getUserName());
        }
        model.addAttribute("article", article);
        model.addAttribute("articleDataContent", StringEscapeUtils.unescapeHtml4(articleData.getContent()));
        return "admin/cms/articlelistwikishow";
    }

    @RequestMapping("userListData1")
    @ResponseBody
    @Operation(summary = "项目任务", description = "项目任务")
    public RestPaging<ArticleVO> userListData1(HttpServletRequest request, ArticleDto articleDto) {
        return pageArticleVO(request, articleDto, 1);
    }

    @RequestMapping("userListData2")
    @ResponseBody
    @Operation(summary = "待办任务", description = "待办任务")
    public RestPaging<ArticleVO> userListData2(HttpServletRequest request, ArticleDto articleDto) {
        return pageArticleVO(request, articleDto, 2);
    }

    @RequestMapping("userListData3")
    @ResponseBody
    @Operation(summary = "里程碑相关任务", description = "里程碑相关任务")
    public RestPaging<ArticleVO> userListData3(HttpServletRequest request, ArticleDto articleDto) {
        if (StringUtils.isBlank(articleDto.getRoadMapId())) {
            return new RestPaging<>(0, new ArrayList<>());
        }
        return pageArticleVO(request, articleDto, 3);
    }

    /**
     * @param request
     * @param articleDto
     * @param type       1-项目任务 2-待办任务 3-里程碑相关任务(ArticleDto里带里程碑id)
     * @return
     */
    private RestPaging<ArticleVO> pageArticleVO(HttpServletRequest request, ArticleDto articleDto, Integer type) {
        List<String> categoryIds = new ArrayList<>();
        if (StringUtils.isNotBlank(articleDto.getCategoryIds())) {
            String[] ids = articleDto.getCategoryIds().split(",");
            for (String id : ids) {
                categoryIds.addAll(categoryService.listCategoryIdByCategoryId(id));
            }
            articleDto.setCategoryAllIds(categoryIds);
        }

        if (StringUtils.isNotBlank(articleDto.getProjectTitle())) {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setTitle(articleDto.getProjectTitle());
            List<Project> lp = projectService.list(projectDto.toQuery());
            if (lp.isEmpty()) {
                return new RestPaging<>(0L, new ArrayList<>());
            } else {
                articleDto.setProjectTitleIds(lp.stream().map(Project::getId).toList());
            }
        }

        UserInfo userInfo = Utils.getUserInfo(request);
        List<ProjectUser> pIds = new ArrayList<>();
        if (type == 1) {
            pIds = projectUserService.listProjectUser(new ProjectUserDto(Utils.getProjectId(request), null));
            articleDto.setProjectIds(pIds.stream().map(ProjectUser::getPid).toList());
        }
        if (type == 2) {
            pIds = projectUserService.listProjectUser(new ProjectUserDto(null, userInfo.getId()));
            articleDto.setProjectIds(pIds.stream().map(ProjectUser::getPid).toList());
        }
        if (articleDto.getTaskSearchFlag() != null && articleDto.getTaskSearchFlag() == 2) {
            List<ArticleUser> articleUserList = articleUserService.listArticleUserByUserId(userInfo.getId());
            List<String> aIds = articleUserList.stream().map(ArticleUser::getAid).toList();
            if (aIds.isEmpty()) {
                return new RestPaging<>(0L, new ArrayList<>());
            } else {
                articleDto.setArticleIds(aIds);
            }
        }
        articleDto.setAtype(1);
        Page<Article> data = articleService.page(articleDto.toPage(), articleDto.toQuery());
        List<ArticleVO> voList = CmsConvert.INSTANCE.toArticleVO(data.getRecords());
        articleService.setArticleVOName(voList);

        Map<String, List<ArticleUser>> articleUserMap = articleUserService.mapArticleUser(voList.stream().map(ArticleVO::getId).toList());

        for (ArticleVO item : voList) {
            if (articleUserMap.containsKey(item.getId())) {
                List<ArticleUser> articleUserList = articleUserMap.get(item.getId());
                articleUserService.setArticleUserName(articleUserList);
                item.setUsers(articleUserList);
            }
        }

        return new RestPaging<>(data.getTotal(), voList);
    }

    @RequestMapping("articleEdit2")
    public String articleEdit2(HttpServletRequest request, String id, Integer readFlag, Model model) {
        if (readFlag == null) {
            readFlag = 1;
        }
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
            UserInfo createUser = userInfoService.getById(article.getCreateUserId());
            if (createUser != null) {
                article.setCreateUserName(createUser.getUserName());
            }
        }

        List<Category> categoryList = categoryService.categoryC(Constant.TASK_STATUS);
        List<Category> categoryListLevel = categoryService.categoryC(Constant.TASK_STATUS_LEVEL);
        // 成员
        String projectId = Utils.getProjectId(request);
        if (StringUtils.isBlank(projectId)) {
            projectId = article.getPid();
        }
        List<RoadMap> roadMapList = roadMapService.listRoadMap(projectId);
        List<ProjectUser> projectUserList = projectUserService.list(new ProjectUserDto(projectId, null).toQuery());
        projectUserService.setProjectUserUserName(projectUserList);
        List<ArticleUser> articleUsers = articleUserService.listArticleUserByAid(id);
        Set<String> articleUsersUserIds = articleUsers.stream().map(ArticleUser::getUserId).collect(Collectors.toSet());

        List<AUVO> voList = new ArrayList<>();
        for (ProjectUser item : projectUserList) {
            AUVO auvo = new AUVO();
            auvo.setId(item.getUserId());
            auvo.setUserId(item.getUserId());
            auvo.setUserNameFast(item.getUserName().substring(0, 1));
            auvo.setUserName(item.getUserName());
            auvo.setAid(id);
            auvo.setJobTitle(item.getJobTitle());
            auvo.setJoinFlag(articleUsersUserIds.contains(item.getUserId()));
            voList.add(auvo);
        }
        // 评论
        List<CommentsVO> commentsVOList = commentsService.listCommentsVO(id);
        model.addAttribute("article", article);
        model.addAttribute("categoryList", categoryList);// 任务状态
        model.addAttribute("categoryListLevel", categoryListLevel);// 优先级
        model.addAttribute("roadMapList", roadMapList);// 里程碑
        model.addAttribute("voList", voList);// 项目成员
        model.addAttribute("commentsVOList", commentsVOList);// 评论

        UserInfo userInfo = Utils.getUserInfo(request);
        ProjectUser projectUser = projectUserService.getProjectUser(projectId, userInfo.getId());
        if (RoleSetting.isNotAdmin(userInfo)) {
            model.addAttribute("readFlagP2", projectUser == null ? 0 : projectUser.getReadFlag());
        }
        model.addAttribute("readFlag", readFlag);
        return "/admin/cms/articleedit2";
    }

    @RequestMapping("articleEdit3")
    public String articleEdit3(HttpServletRequest request, String id, Model model) {
        Article article = articleService.getById(id);
        if (article == null) {
            article = new Article();
        } else {
            ArticleData articleData = articleDataService.getById(id);
            if (articleData != null) {
                article.setArticleData(articleData);
            }
        }
        model.addAttribute("article", article);
        return "/admin/cms/articleeditwiki";
    }

    @RequestMapping("articleSave")
    @ResponseBody
    public RestResult articleSave(HttpServletRequest request, Article article, ArticleData articleData, String userlists) {
        String projectId = Utils.getProjectId(request);
        if (StringUtils.isBlank(projectId)) {
            return RestResult.Fail("操作失败");
        }
        article.setPid(projectId);
        articleService.saveArticle(article, articleData, userlists);
        return RestResult.OK();
    }

    @RequestMapping("delById")
    @ResponseBody
    public RestResult delById(HttpServletRequest request, String id) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Article article = articleService.getById(id);
        if (RoleSetting.isNotAdmin(userInfo) && !userInfo.getId().equals(article.getCreateUserId())) {
            return RestResult.Fail("不能删除别人的数据");
        }
        LambdaUpdateWrapper<Article> up = Wrappers.lambdaUpdate();
        up.eq(Article::getId, id);
        up.set(Article::getStatus, 0);
        articleService.update(up);
        return RestResult.OK();
    }

    @RequestMapping("upLevelId")
    @ResponseBody
    public RestResult upLevelId(HttpServletRequest request, String projectId, String levelId) {
        if (StringUtils.isBlank(projectId) || StringUtils.isBlank(levelId)) {
            return RestResult.Fail();
        }
        LambdaUpdateWrapper<Article> up = Wrappers.lambdaUpdate();
        up.eq(Article::getId, projectId);
        up.set(Article::getLevelId, levelId);
        articleService.update(up);
        return RestResult.OK();
    }

    @RequestMapping("upCategoryId")
    @ResponseBody
    public RestResult upCategoryId(HttpServletRequest request, String projectId, String categoryId) {
        if (StringUtils.isBlank(projectId) || StringUtils.isBlank(categoryId)) {
            return RestResult.Fail();
        }
        LambdaUpdateWrapper<Article> up = Wrappers.lambdaUpdate();
        up.eq(Article::getId, projectId);
        up.set(Article::getCategoryId, categoryId);
        articleService.update(up);
        return RestResult.OK();
    }
}
