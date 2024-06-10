package com.xxsword.xitem.admin.domain.category.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import lombok.Data;

@Data
public class CategoryDto {
    private String title;
    private String categoryId;// 指定业务id
    private String pid;// 指定业务id

    public LambdaQueryWrapper<Category> toQuery() {
        return new LambdaQueryWrapper<Category>().eq(Category::getStatus, 1)
                .like(StringUtils.isNotBlank(title), Category::getTitle, title)
                .eq(StringUtils.isNotBlank(pid), Category::getPid, pid)
                .orderByAsc(Category::getSeq, Category::getId);
    }
}
