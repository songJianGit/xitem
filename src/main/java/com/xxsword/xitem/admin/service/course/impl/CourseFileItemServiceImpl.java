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
import com.xxsword.xitem.admin.utils.Pdf2PngUtil;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import org.springframework.scheduling.annotation.Async;
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
        query.orderByAsc(CourseFileItem::getSeq, CourseFileItem::getId);
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

    @Async
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
            courseFileItem.setSeq(0);
            courseFileItem.setFilePath(fileInfos);
            save(courseFileItem);
        }
        if (courseFile.getCourseType() == 5) {
            delCourseFileItem(courseFile.getId());
            String relativePath = UpLoadUtil.PATH_INFO + "/coursepdfimg" + UpLoadUtil.getTIMEPath() + "/" + courseFile.getId();
            String targetPath = UpLoadUtil.getProjectPath() + relativePath;
            List<String> listUrl = Pdf2PngUtil.pdfToPng(UpLoadUtil.getProjectPath() + fileInfos, targetPath);
            List<CourseFileItem> courseFileItemList = new ArrayList<>();
            if (!listUrl.isEmpty()) {
                for (int i = 0; i < listUrl.size(); i++) {
                    CourseFileItem courseFileItem = new CourseFileItem();
                    courseFileItem.setBaseInfo(userInfo);
                    courseFileItem.setCourseFileId(courseFile.getId());
                    courseFileItem.setSeq(i);
                    courseFileItem.setFilePath(relativePath + listUrl.get(i));
                    courseFileItemList.add(courseFileItem);
                }
            }
            saveBatch(courseFileItemList);
        }
    }

}
