package com.xxsword.xitem.admin.service.category.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.category.CategoryMapper;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Override
    public List<Category> categoryP(String categoryId) {
        if (StringUtils.isBlank(categoryId)) {
            return new ArrayList<>();
        }
        List<Category> list = new ArrayList<>();
        diGuiUpCategory(list, categoryId);
        list.remove(0);// 剔除掉自己
        Collections.reverse(list);
        return list;
    }

    @Override
    public String categoryPIds(String categoryId) {
        return categoryP(categoryId).stream().map(Category::getId).collect(Collectors.joining(","));
    }

    // 递归向上寻找机构的所有上级信息
    private void diGuiUpCategory(List<Category> list, String categoryId) {
        Category category = getById(categoryId);
        if (category != null) {
            list.add(category);
            diGuiUpCategory(list, category.getPid());
        }
    }

    @Override
    public List<Category> categoryC(String categoryId) {
        LambdaQueryWrapper<Category> q = Wrappers.lambdaQuery();
        q.eq(Category::getStatus, 1);
        q.last(" AND find_in_set('" + categoryId + "', pids) order by seq asc, id asc");
        return list(q);
    }

    @Override
    public void upCategoryPids() {
        log.info("更新分类pids START");
        LambdaQueryWrapper<Category> query = Wrappers.lambdaQuery();
        query.eq(Category::getStatus, 1);
        query.orderByAsc(Category::getSeq);
        int pageSize = 500;
        long count = count(query);
        long page = Utils.getPage(count, pageSize) + 1;
        try {
            for (int i = 1; i < page; i++) {
                Page<Category> xo = page(new Page<>(i, pageSize), query);
                List<Category> categoryList = xo.getRecords();
                List<Category> categoryListUp = new ArrayList<>();
                for (Category o : categoryList) {
                    Category oUp = new Category();
                    oUp.setId(o.getId());
                    oUp.setPids(categoryPIds(o.getId()));
                    categoryListUp.add(oUp);
                }
                updateBatchById(categoryListUp);
                if (xo.getRecords().size() < pageSize) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("更新分类pids异常");
        }
        log.info("更新分类pids END");
    }

    @Override
    public void delCategory(String categoryIds) {
        String[] ids = categoryIds.split(",");
        for (String id : ids) {
            Category categoryUp = new Category();
            categoryUp.setId(id);
            categoryUp.setStatus(0);
            updateById(categoryUp);
            List<Category> list = categoryC(id);
            List<Category> listUp = new ArrayList<>();
            for (Category o : list) {
                Category oUp = new Category();
                oUp.setId(o.getId());
                oUp.setStatus(0);
                listUp.add(oUp);
            }
            updateBatchById(listUp);
        }
    }

    @Override
    public List<String> listCategoryIdByCategoryId(String categoryId) {
        List<Category> list = categoryC(categoryId);
        List<String> stringList = list.stream().map(Category::getId).collect(Collectors.toList());
        stringList.add(categoryId);
        return stringList;
    }

    @Override
    public List<String> listCategoryIdByCategoryId(List<String> categoryIds) {
        Set<String> set = new HashSet<>();
        for (String id : categoryIds) {
            set.addAll(listCategoryIdByCategoryId(id));
        }
        return new ArrayList<>(set);
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String categoryIds) {
        String[] ids = categoryIds.split(",");
        List<Category> listUp = new ArrayList<>();
        for (String id : ids) {
            Category categoryUp = new Category();
            categoryUp.setId(id);
            categoryUp.setBaseInfo(doUserInfo);
            listUp.add(categoryUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void seq(UserInfo userInfo, String id1, String id2) {
        Category category1 = getById(id1);
        Category category2 = getById(id2);
        Category category1Up = new Category();
        Category category2Up = new Category();
        category1Up.setId(id1);
        category1Up.setSeq(category2.getSeq());
        category1Up.setBaseInfo(userInfo);
        category2Up.setId(id2);
        category2Up.setSeq(category1.getSeq());
        category2Up.setBaseInfo(userInfo);
        List<Category> listUp = new ArrayList<>();
        listUp.add(category1Up);
        listUp.add(category2Up);
        updateBatchById(listUp);
    }
}
