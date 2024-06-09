package com.xxsword.xitem.admin.mapper.exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.dto.ExamDto;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamMapper extends BaseMapper<Exam> {

    @Select("<script>" +
            "select " +
            "b.* " +
            "from t_ex_exam b left join t_ex_exam_auth c on b.id=c.exam_id " +
            "where 1=1 " +
            "<if test='dto.title!=null and dto.title!=\"\"'>" +
            "and b.title like concat('%', #{dto.title}, '%') " +
            "</if>" +
            "<if test='dto.exType!=null and dto.exType!=\"\"'>" +
            "and b.ex_type=#{dto.exType} " +
            "</if>" +
            "<if test='dto.releaseStatus!=null and dto.releaseStatus!=\"\"'>" +
            "and b.release_status=#{dto.releaseStatus} " +
            "</if>" +
            "and c.user_id=#{userId} " +
            "</script>")
    Page<Exam> pageExam0ByUser(Page<Exam> page, String userId, ExamDto dto);
}
