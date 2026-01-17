package com.xxsword.xitem.admin.service.course;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.course.entity.Course;

public interface CourseService extends IService<Course> {

    void delByIds(String ids);

    /**
     * 发布和下架
     *
     * @param id
     */
    void release(String id);

    void seq(String id1, String id2);
}
