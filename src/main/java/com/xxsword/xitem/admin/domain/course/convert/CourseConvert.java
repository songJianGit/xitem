package com.xxsword.xitem.admin.domain.course.convert;

import com.xxsword.xitem.admin.domain.course.entity.CourseUser;
import com.xxsword.xitem.admin.domain.course.vo.CourseUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CourseConvert {
    CourseConvert INSTANCE = Mappers.getMapper(CourseConvert.class);

    List<CourseUserVO> toCourseUserVO(List<CourseUser> courseUser);

}
