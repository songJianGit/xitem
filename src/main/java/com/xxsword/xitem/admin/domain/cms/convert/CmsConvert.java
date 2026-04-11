package com.xxsword.xitem.admin.domain.cms.convert;

import com.xxsword.xitem.admin.domain.cms.entity.Article;
import com.xxsword.xitem.admin.domain.cms.vo.ArticleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CmsConvert {
    CmsConvert INSTANCE = Mappers.getMapper(CmsConvert.class);

    List<ArticleVO> toArticleVO(List<Article> list);
}
