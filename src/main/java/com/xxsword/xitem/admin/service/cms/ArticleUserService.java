package com.xxsword.xitem.admin.service.cms;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.cms.entity.ArticleUser;

import java.util.List;

public interface ArticleUserService extends IService<ArticleUser> {
    List<ArticleUser> listArticleUserBy(String aid);

    ArticleUser getArticleUser(String aid, String userId);
}
