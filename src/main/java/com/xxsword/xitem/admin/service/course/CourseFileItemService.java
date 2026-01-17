package com.xxsword.xitem.admin.service.course;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import com.xxsword.xitem.admin.domain.course.entity.CourseFileItem;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface CourseFileItemService extends IService<CourseFileItem> {

    List<CourseFileItem> listCourseFileItem(String courseFileId);

    void delCourseFileItem(String courseFileId);

    void delByIds(String ids);

    /**
     * 异步处理课程文件
     *
     * @param userInfo
     * @param courseFile
     * @param fileInfos
     */
    void saveOrUpdateCourseFile(UserInfo userInfo, CourseFile courseFile, String fileInfos);

}
