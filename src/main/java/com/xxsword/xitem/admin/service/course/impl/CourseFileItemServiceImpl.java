package com.xxsword.xitem.admin.service.course.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import com.xxsword.xitem.admin.domain.course.entity.CourseFileItem;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.course.CourseFileItemMapper;
import com.xxsword.xitem.admin.service.course.CourseFileItemService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseFileItemServiceImpl extends ServiceImpl<CourseFileItemMapper, CourseFileItem> implements CourseFileItemService {

    @Override
    public List<CourseFileItem> listCourseFileItem(String courseFileId) {
        if (StringUtils.isBlank(courseFileId)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<CourseFileItem> query = Wrappers.lambdaQuery();
        query.eq(CourseFileItem::getStatus, 1);
        query.eq(CourseFileItem::getCourseFileId, courseFileId);
        query.orderByAsc(CourseFileItem::getSeq);
        return list(query);
    }

    @Override
    public void delCourseFileItem(String courseFileId) {
        LambdaQueryWrapper<CourseFileItem> query = Wrappers.lambdaQuery();
        query.eq(CourseFileItem::getStatus, 1);
        query.eq(CourseFileItem::getCourseFileId, courseFileId);
        query.select(CourseFileItem::getId);
        List<CourseFileItem> list = list(query);
        for (CourseFileItem item : list) {
            item.setStatus(0);
        }
        updateBatchById(list);
    }

    @Override
    public void delByIds(UserInfo userInfo, String ids) {
        String[] idsS = ids.split(",");
        List<CourseFileItem> listUp = new ArrayList<>();
        for (String id : idsS) {
            CourseFileItem courseFileItemUp = new CourseFileItem();
            courseFileItemUp.setId(id);
            courseFileItemUp.setBaseInfo(userInfo);
            courseFileItemUp.setStatus(0);
            listUp.add(courseFileItemUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void saveOrUpdateCourseFile(UserInfo userInfo, CourseFile courseFile, String fileInfos) {
        if (StringUtils.isBlank(fileInfos)) {
            return;
        }
        if (courseFile.getCourseType() == 2) {
            delCourseFileItem(courseFile.getId());
            CourseFileItem courseFileItem = new CourseFileItem();
            courseFileItem.setBaseInfo(userInfo);
            courseFileItem.setCourseFileId(courseFile.getId());
            courseFileItem.setSeq(DateTime.now().getMillis());
            courseFileItem.setFilePath(fileInfos);
            save(courseFileItem);
        }
    }

}
