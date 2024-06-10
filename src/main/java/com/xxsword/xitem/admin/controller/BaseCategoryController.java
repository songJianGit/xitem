package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.category.dto.CategoryDto;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.model.ZTree;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.utils.Utils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BaseCategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类树数据
     *
     * @param open
     * @return
     */
    public List<ZTree> data(Integer open, String checkedids, CategoryDto categoryDto) {
        return dataI(open, checkedids, null, categoryDto);
    }

    /**
     * @param open       是否全部展开
     * @param checkedids 选中的ids
     * @param discheckid 不可选的ids
     * @return
     */
    private List<ZTree> dataI(Integer open, String checkedids, String discheckid, CategoryDto categoryDto) {
        if (open == null) {
            open = 0;
        }
        List<String> nocids = new ArrayList<>();
        if (StringUtils.isNotBlank(discheckid)) {// 勾选，但禁用的分类
            nocids = new ArrayList<>(Arrays.asList(discheckid.split(",")));
        }
        Set<String> checkedidset = new HashSet<>();
        if (StringUtils.isNotBlank(checkedids)) {
            String[] split = checkedids.split(",");
            checkedidset.addAll(Arrays.asList(split));
        }
        List<Category> listCategory = categoryService.categoryC(categoryDto.getCategoryId());
        listCategory.add(categoryService.getById(categoryDto.getCategoryId()));

        List<ZTree> datas = new ArrayList<>();
        for (Category category : listCategory) {
            ZTree zTreeVO = new ZTree();
            zTreeVO.setName(category.getTitle());
            zTreeVO.setpId(category.getPid());
            zTreeVO.setId(category.getId());
            zTreeVO.setOpen(!open.equals(0));
            if (checkedidset.contains(category.getId())) {
                zTreeVO.setChecked(true);
            }
            if (nocids.size() > 0) {
                for (String id : nocids) {
                    if (id.equalsIgnoreCase(category.getId())) {
                        zTreeVO.setChkDisabled(true);
                    }
                }
            }
            datas.add(zTreeVO);
        }
        return datas;
    }

    /**
     * 右侧分类表
     * 根据分类id，获取该分类下一级的所有分类信息
     *
     * @return
     */
    public RestPaging pageById(Page<Category> page, CategoryDto categoryDto) {
        List<Category> categoryList = categoryService.categoryC(categoryDto.getCategoryId());
        List<String> ids = categoryList.stream().map(Category::getId).collect(Collectors.toList());
        ids.add(categoryDto.getCategoryId());

        LambdaQueryWrapper<Category> query = categoryDto.toQuery();
        query.in(Category::getId, ids);
        Page<Category> p = categoryService.page(page, query);
        return new RestPaging(p.getTotal(), p.getRecords());
    }

    public Category categoryEdit(String id) {
        Category category = new Category();
        if (StringUtils.isNotBlank(id)) {
            category = categoryService.getById(id);
            Category pCategory = categoryService.getById(category.getPid());
            if (pCategory != null) {
                category.setPtitle(pCategory.getPtitle());
            }
        }
        return category;
    }

    /**
     * 分类的保存
     */
    public RestResult saveCategory(UserInfo userInfo, Category category) {
        category.setBaseInfo(userInfo);
        if (StringUtils.isBlank(category.getId())) {
            category.setSeq(DateTime.now().getMillis());
        }
        categoryService.saveOrUpdate(category);

        Category categoryUp = new Category();
        categoryUp.setId(category.getId());
        categoryUp.setPids(categoryService.categoryPIds(category.getId()));
        categoryService.updateById(categoryUp);// 更新该分类pids
        return RestResult.OK();
    }

    /**
     * 分类的删除(连带删除其下所有分类)
     */
    public RestResult delCategory(UserInfo userInfo, String categoryIds) {
        categoryService.delCategory(categoryIds);
        categoryService.upLastInfo(userInfo, categoryIds);
        return RestResult.OK();
    }

    public RestResult categorySeq(UserInfo userInfo, String id1, String id2) {
        categoryService.seq(userInfo, id1, id2);
        return RestResult.OK();
    }

}
