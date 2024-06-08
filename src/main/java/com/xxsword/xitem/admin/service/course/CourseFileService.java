package com.xxsword.xitem.admin.service.course;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import com.xxsword.xitem.admin.domain.course.entity.CourseFileItem;
import com.xxsword.xitem.admin.domain.course.vo.CoursePlayVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

public interface CourseFileService extends IService<CourseFile> {

    void delByIds(UserInfo userInfo, String ids);

    /**
     * 获取课件相关播放信息
     *
     * @param courseFileId
     * @return
     */
    CoursePlayVO courseFile(String courseFileId);
}
