package com.xxsword.xitem.admin.service.course;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.course.entity.Course;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

public interface CourseService extends IService<Course> {

    void delByIds(UserInfo userInfo, String ids);

    /**
     * 发布和下架
     *
     * @param id
     */
    void release(UserInfo userInfo, String id);

    void seq(UserInfo userInfo, String id1, String id2);
}
