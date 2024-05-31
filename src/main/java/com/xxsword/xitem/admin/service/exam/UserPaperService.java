package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.dto.UserPaperDto;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.exam.vo.UserPaperVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

import java.util.List;

public interface UserPaperService extends IService<UserPaper> {

    void upLastInfo(UserInfo doUserInfo, String ids);

    /**
     * 获取未提交的UserPaper，没有则新增一个
     *
     * @param userInfo
     * @param paperId
     * @param examId
     * @param type     1-根据用户，试卷，考试信息获取，有则返回，无则新增 2-永远产生新的试卷
     */
    UserPaper getUserPaper(UserInfo userInfo, String paperId, String examId, Integer type);

    /**
     * 获取某一状态的用户答题记录
     *
     * @param userId
     * @param paperId
     * @param examId
     * @param subStatus 0-初始 1-已提交  null-查询全部
     * @return
     */
    List<UserPaper> listUserPaper(String userId, String paperId, String examId, Integer subStatus);

    Long countUserPaper(String userId, String paperId, String examId, Integer subStatus);

    /**
     * 更新用户答题记录状态，计算用户总成绩
     */
    UserPaper userPaperSub(UserInfo userInfo, String userPaperId);

    /**
     * 该用户的考试纪录
     *
     * @param userId
     * @return 考试id
     */
    Page<UserPaper> pageUserExamRecord(Page<UserPaper> page, String userId);

    /**
     * 对象转VO
     * <p>
     * 赋值考试用时
     *
     * @param list
     * @return
     */
    List<UserPaperVO> listUserPaperVOByUserPaper(List<UserPaper> list);

    /**
     * 考试成绩查询(重考以最高分为准)
     *
     * @param page
     * @return
     */
    Page<UserPaperVO> pageExamScore(Page<UserPaper> page, UserPaperDto userPaperDto);
}
