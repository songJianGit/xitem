package com.xxsword.xitem.admin.service.course;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

public interface CourseFileService extends IService<CourseFile> {

    void delByIds(UserInfo userInfo, String ids);

}
