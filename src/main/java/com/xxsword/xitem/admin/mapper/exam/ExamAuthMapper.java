package com.xxsword.xitem.admin.mapper.exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.dto.ExamAuthDto;
import com.xxsword.xitem.admin.domain.exam.entity.ExamAuth;
import com.xxsword.xitem.admin.domain.exam.vo.ExamAuthVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamAuthMapper extends BaseMapper<ExamAuth> {

    @Select("<script>" +
            "select " +
            "b.*,c.user_name,c.login_name,c.phone_no " +
            "from t_ex_exam_auth b left join t_sys_userinfo c on b.user_id=c.id " +
            "where 1=1 " +
            "<if test='dto.loginName!=null and dto.loginName!=\"\"'>" +
            "and c.login_name like concat('%', #{dto.loginName}, '%') " +
            "</if>" +
            "<if test='dto.userName!=null and dto.userName!=\"\"'>" +
            "and c.user_name like concat('%', #{dto.userName}, '%') " +
            "</if>" +
            "<if test='dto.phoneNo!=null and dto.phoneNo!=\"\"'>" +
            "and c.phone_no like concat('%', #{dto.phoneNo}, '%') " +
            "</if>" +
            "<if test='dto.examId!=null and dto.examId!=\"\"'>" +
            "and b.exam_id=#{dto.examId} " +
            "</if>" +
            "</script>")
    Page<ExamAuthVO> pageExamAuthByDto(Page<ExamAuth> page, ExamAuthDto dto);
}
