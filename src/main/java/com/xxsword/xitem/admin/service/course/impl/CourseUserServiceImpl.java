package com.xxsword.xitem.admin.service.course.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.course.dto.CourseUserDto;
import com.xxsword.xitem.admin.domain.course.entity.CourseUser;
import com.xxsword.xitem.admin.domain.course.vo.CourseUserVO;
import com.xxsword.xitem.admin.mapper.course.CourseUserMapper;
import com.xxsword.xitem.admin.service.course.CourseUserService;
import org.springframework.stereotype.Service;


@Service
public class CourseUserServiceImpl extends ServiceImpl<CourseUserMapper, CourseUser> implements CourseUserService {

    @Override
    public CourseUser getCourseUser(String userId, String courseId) {
        LambdaQueryWrapper<CourseUser> query = Wrappers.lambdaQuery();
        query.eq(CourseUser::getCreateUserId, userId);
        query.eq(CourseUser::getCourseId, courseId);
        query.eq(CourseUser::getStatus, 1);
        return getOne(query);
    }

    @Override
    public Page<CourseUser> pageCourseUser(Page<CourseUser> page, String userId) {
        LambdaQueryWrapper<CourseUser> query = Wrappers.lambdaQuery();
        query.eq(CourseUser::getCreateUserId, userId);
        query.eq(CourseUser::getStatus, 1);
        return page(page, query);
    }

    @Override
    public Page<CourseUserVO> pageCourseUserVOByDto(Page<CourseUser> page, CourseUserDto dto) {
        return baseMapper.pageCourseUserVOByDto(page, dto);
    }
}
