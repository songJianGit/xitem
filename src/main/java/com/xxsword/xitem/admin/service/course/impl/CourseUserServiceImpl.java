package com.xxsword.xitem.admin.service.course.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.course.entity.CourseUser;
import com.xxsword.xitem.admin.mapper.course.CourseUserMapper;
import com.xxsword.xitem.admin.service.course.CourseUserService;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl extends ServiceImpl<CourseUserMapper, CourseUser> implements CourseUserService {

}
