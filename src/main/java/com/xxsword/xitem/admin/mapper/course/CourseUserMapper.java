package com.xxsword.xitem.admin.mapper.course;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.course.dto.CourseUserDto;
import com.xxsword.xitem.admin.domain.course.entity.CourseUser;
import com.xxsword.xitem.admin.domain.course.vo.CourseUserVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseUserMapper extends BaseMapper<CourseUser> {

    @Select("<script>" +
            "select " +
            "a.*, b.user_name create_user_name " +
            "from t_cus_course_user a left join t_sys_userinfo b on a.create_user_id=b.id " +
            "where 1=1 " +
            "<if test='dto.courseId!=null and dto.courseId!=\"\"'>" +
            "and a.course_id=#{dto.courseId} " +
            "</if>" +
            "<if test='dto.userName!=null and dto.userName!=\"\"'>" +
            "and b.user_name like concat('%', #{dto.userName}, '%') " +
            "</if>" +
            "order by a.create_date desc,a.id " +
            "</script>")
    Page<CourseUserVO> pageCourseUserVOByDto(Page<CourseUser> page, CourseUserDto dto);
}
