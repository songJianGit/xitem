package com.xxsword.xitem.admin.mapper.exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.dto.UserPaperDto;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.exam.vo.UserPaperVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaperMapper extends BaseMapper<UserPaper> {

    /**
     * 考试分数查询
     *
     * @param page
     * @param dto
     * @return 用户信息和考试最高分
     */
    @Select("<script>" +
            "select " +
            "a.user_id user_id, b.user_name user_name, max(a.score) score " +
            "from t_ex_user_paper a left join t_sys_userinfo b on a.user_id=b.id " +
            "where " +
            "exam_id=#{dto.examId} " +
            "<if test='dto.userName!=null and dto.userName!=\"\"'>" +
            "and b.user_name like concat('%', #{dto.userName}, '%') " +
            "</if>" +
            "<if test='dto.subStatus!=null'>" +
            "and a.sub_status = #{dto.subStatus} " +
            "</if>" +
            "group by a.user_id,b.user_name " +
            "</script>")
    Page<UserPaperVO> pageExamScore(Page<UserPaper> page, UserPaperDto dto);

}
