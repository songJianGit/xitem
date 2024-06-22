package com.xxsword.xitem.admin.service.course;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.course.dto.CourseUserDto;
import com.xxsword.xitem.admin.domain.course.entity.CourseUser;
import com.xxsword.xitem.admin.domain.course.vo.CourseUserVO;

public interface CourseUserService extends IService<CourseUser> {

    CourseUser getCourseUser(String userId, String courseId);

    Page<CourseUser> pageCourseUser(Page<CourseUser> page, String userId);

    Page<CourseUserVO> pageCourseUserVOByDto(Page<CourseUser> page, CourseUserDto dto);

}
