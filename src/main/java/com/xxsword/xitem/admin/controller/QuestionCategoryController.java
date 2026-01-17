package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.category.dto.CategoryDto;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.model.ZTree;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("admin/category/question")
public class QuestionCategoryController extends BaseCategoryController {

    private static final String QUESTION_CATEGORY_ID = "question";

    /**
     * 分类树数据
     *
     * @param request
     * @param open
     * @return
     */
    @RequestMapping("data")
    @ResponseBody
    public List<ZTree> data(HttpServletRequest request, Integer open, String checkedids, CategoryDto categoryDto) {
        categoryDto.setCategoryId(QUESTION_CATEGORY_ID);
        return super.dataBase(open, checkedids, categoryDto);
    }

    @RequestMapping("categoryList")
    public String categoryList() {
        return "/admin/category/question/list";
    }

    /**
     * 右侧分类表
     * 根据分类id，获取该分类下一级的所有分类信息
     *
     * @param request
     * @return
     */
    @RequestMapping("pageById")
    @ResponseBody
    public RestPaging pageById(HttpServletRequest request, Page<Category> page, CategoryDto categoryDto) {
        categoryDto.setCategoryId(QUESTION_CATEGORY_ID);
        return super.pageByIdBase(page, categoryDto);
    }

    @RequestMapping("categoryEdit")
    public String categoryEdit(String id, Model model) {
        model.addAttribute("category", super.categoryEditBase(id));
        return "/admin/category/question/edit";
    }

    /**
     * 分类的保存
     */
    @RequestMapping("saveCategory")
    @ResponseBody
    public RestResult saveCategory(HttpServletRequest request, Category category) {
        if (StringUtils.isBlank(category.getPid())) {
            category.setPid(QUESTION_CATEGORY_ID);
        }
        super.saveCategoryBase(category);
        return RestResult.OK();
    }

    /**
     * 分类的删除(连带删除其下所有分类)
     */
    @RequestMapping("delCategory")
    @ResponseBody
    public RestResult delCategory(String categoryIds) {
        super.delCategoryBase(categoryIds);
        return RestResult.OK();
    }

    @RequestMapping("seq")
    @ResponseBody
    public RestResult seq(String id1, String id2) {
        super.categorySeqBase(id1, id2);
        return RestResult.OK();
    }

    /**
     * 分类选择
     *
     * @param type 1-单选 2-多选
     * @return
     */
    @RequestMapping("categoryShow")
    public String categoryShow(Integer type, Model model) {
        if (type == null) {
            type = 1;
        }
        model.addAttribute("type", type);
        return "/admin/category/question/show";
    }
}
