package com.xxsword.xitem.admin.service.course.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import com.xxsword.xitem.admin.domain.course.entity.CourseFileItem;
import com.xxsword.xitem.admin.domain.course.vo.CoursePlayVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.course.CourseFileMapper;
import com.xxsword.xitem.admin.service.course.CourseFileItemService;
import com.xxsword.xitem.admin.service.course.CourseFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseFileServiceImpl extends ServiceImpl<CourseFileMapper, CourseFile> implements CourseFileService {

    @Autowired
    private CourseFileItemService courseFileItemService;

    @Override
    public void delByIds(UserInfo userInfo, String ids) {
        String[] idsS = ids.split(",");
        List<CourseFile> listUp = new ArrayList<>();
        for (String id : idsS) {
            CourseFile courseFileUp = new CourseFile();
            courseFileUp.setId(id);
            courseFileUp.setBaseInfo(userInfo);
            courseFileUp.setStatus(0);
            listUp.add(courseFileUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public CoursePlayVO courseFile(String courseFileId) {
        CoursePlayVO coursePlayVO = new CoursePlayVO();
        CourseFile courseFile = getById(courseFileId);
        String url = "";
        switch (courseFile.getCourseType()) {
            case 1:
                log.info("scorm");
                break;
            case 2:
                List<CourseFileItem> courseFileItemListVideo = courseFileItemService.listCourseFileItem(courseFile.getId());
                List<String> videoIds = courseFileItemListVideo.stream().map(CourseFileItem::getId).collect(Collectors.toList());
                coursePlayVO.setCourseFileItemIds(videoIds);
                coursePlayVO.setUrl("/pc/course/playvideo");
                break;
            case 5:
                List<CourseFileItem> courseFileItemListPdf = courseFileItemService.listCourseFileItem(courseFile.getId());
                List<String> pdfIds = courseFileItemListPdf.stream().map(CourseFileItem::getId).collect(Collectors.toList());
                coursePlayVO.setCourseFileItemIds(pdfIds);
                coursePlayVO.setUrl("/pc/course/playimg");
                break;
        }
        coursePlayVO.setCourseFile(courseFile);
        return coursePlayVO;
    }

}
