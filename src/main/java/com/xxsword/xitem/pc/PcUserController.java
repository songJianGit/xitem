package com.xxsword.xitem.pc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.controller.BaseController;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.exam.ExamService;
import com.xxsword.xitem.admin.service.exam.UserPaperService;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("pc/user")
public class PcUserController extends BaseController {

    @Autowired
    private UserPaperService userPaperService;
    @Autowired
    private ExamService examService;

    /**
     * 用户中心
     *
     * @return
     */
    @GetMapping("userCenter")
    public String userCenter() {
        return "/pc/user/usercenter";
    }

    /**
     * 考试纪录
     */
    @GetMapping("userExam")
    public String userExam() {
        return "/pc/user/userexam";
    }

    @GetMapping("userExamData")
    @ResponseBody
    public RestResult userExamData(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        UserInfo userInfo = Utils.getUserInfo(request);
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }
        Page<UserPaper> userPaperPage = userPaperService.pageUserExamRecord(new Page<>(pageNum, pageSize), userInfo.getId());
        Set<String> examIds = userPaperPage.getRecords().stream().map(UserPaper::getExamid).collect(Collectors.toSet());
        if (examIds.isEmpty()) {
            return RestResult.OK();
        }
        List<Exam> examList = examService.listByIds(examIds);
        return RestResult.OK(examList);
    }
}
