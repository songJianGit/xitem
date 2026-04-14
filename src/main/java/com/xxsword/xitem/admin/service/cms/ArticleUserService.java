package com.xxsword.xitem.admin.service.cms;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleUser;

import java.util.List;
import java.util.Map;

public interface ArticleUserService extends IService<ArticleUser> {
    List<ArticleUser> listArticleUserByAid(String aid);

    List<ArticleUser> listArticleUserByUserId(String userId);

    ArticleUser getArticleUser(String aid, String userId);

    Map<String, List<ArticleUser>> mapArticleUser(List<String> aid);

    void setArticleUserName(List<ArticleUser> list);
}
