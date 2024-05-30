package com.xxsword.xitem.admin.mapper.exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.dto.UserPaperDto;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaperMapper extends BaseMapper<UserPaper> {

    @Select("<script>" +
            "select " +
            "a.userid userid,b.username username, max(a.score) score " +
            "from t_ex_user_paper a left join t_sys_userinfo b on a.userid=b.id " +
            "where " +
            "examid=#{dto.examId} " +
            "<if test='dto.userName!=null and dto.userName!=\"\"'>" +
            "and b.username like concat('%', #{dto.userName}, '%') " +
            "</if>" +
            "group by a.userid,b.username " +
            "</script>")
    Page<UserPaper> pageExamScore(Page<UserPaper> page, UserPaperDto dto);
}
