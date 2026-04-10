package com.xxsword.xitem.admin.service.category;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.category.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * 所有上级集合(不包括自己)
     *
     * @param categoryId
     * @return
     */
    List<Category> categoryP(String categoryId);

    /**
     * 所有上级机构的ids，逗号分隔(不包括自己)
     */
    String categoryPIds(String categoryId);

    /**
     * 所有下级(不包括自己)
     *
     * @param categoryId
     * @return
     */
    List<Category> categoryC(String categoryId);

    /**
     * 更新全部的pids
     */
    void upCategoryPids();

    /**
     * 逻辑删除
     */
    void delCategory(String categoryIds);

    /**
     * 本级和本级以下的ids
     *
     * @param categoryId
     * @return
     */
    List<String> listCategoryIdByCategoryId(String categoryId);

    /**
     * 本级和本级以下的ids
     *
     * @param categoryIds
     * @return
     */
    List<String> listCategoryIdByCategoryId(List<String> categoryIds);

    /**
     * 顺序调整
     * @param id1
     * @param id2
     */
    void seq(String id1, String id2);

}
