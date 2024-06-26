package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.dto.ExamAuthDto;
import com.xxsword.xitem.admin.domain.exam.dto.ExamDto;
import com.xxsword.xitem.admin.domain.exam.dto.UserPaperDto;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.exam.entity.ExamAuth;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.exam.vo.ExamAuthVO;
import com.xxsword.xitem.admin.domain.exam.vo.UserPaperVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.exam.ExamAuthService;
import com.xxsword.xitem.admin.service.exam.ExamService;
import com.xxsword.xitem.admin.service.exam.PaperService;
import com.xxsword.xitem.admin.service.exam.UserPaperService;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("admin/exam")
public class ExamController {
    @Autowired
    private ExamService examService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private UserPaperService userPaperService;
    @Autowired
    private ExamAuthService examAuthService;

    @RequestMapping("list")
    public String index() {
        return "/admin/exam/list";
    }

    @RequestMapping("data")
    @ResponseBody
    public RestPaging<Exam> data(HttpServletRequest request, Page<Exam> page, ExamDto examDto) {
        Page<Exam> data = examService.page(page, examDto.toQuery());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        Exam exam = new Exam();
        if (StringUtils.isNotBlank(id)) {
            exam = examService.getById(id);
            if (StringUtils.isNotBlank(exam.getPaperId())) {
                Paper paper = paperService.getById(exam.getPaperId());
                exam.setPaperTitle(paper.getTitle());
            }
        }
        model.addAttribute("exam", exam);
        return "/admin/exam/edit";
    }

    @RequestMapping("save")
    public String save(HttpServletRequest request, Exam exam) {
        UserInfo userInfo = Utils.getUserInfo(request);
        exam.setBaseInfo(userInfo);
        if (StringUtils.isBlank(exam.getId())) {
            exam.setReleaseStatus(0);
        }
        examService.saveOrUpdate(exam);
        return "redirect:list";
    }

    @RequestMapping("del")
    @ResponseBody
    public RestResult del(HttpServletRequest request, String ids) {
        UserInfo userInfo = Utils.getUserInfo(request);
        examService.delByIds(userInfo, ids);
        return RestResult.OK();
    }

    @RequestMapping("release")
    @ResponseBody
    public RestResult release(HttpServletRequest request, String id) {
        UserInfo userInfo = Utils.getUserInfo(request);
        examService.release(userInfo, id);
        return RestResult.OK();
    }

    /**
     * 考试成绩列表
     *
     * @param examId
     * @param model
     * @return
     */
    @RequestMapping("examScore")
    public String examScore(String examId, Model model) {
        model.addAttribute("examId", examId);
        return "/admin/exam/examscore";
    }

    @RequestMapping("examScoreData")
    @ResponseBody
    public RestPaging<UserPaperVO> examscoreData(HttpServletRequest request, Page<UserPaper> page, UserPaperDto userPaperDto) {
        Page<UserPaperVO> data = userPaperService.pageExamScore(page, userPaperDto);
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    /**
     * 考试记录查看弹框
     *
     * @param examId
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping("userPaper")
    public String userPaper(String examId, String userId, Model model) {
        model.addAttribute("examId", examId);
        model.addAttribute("userId", userId);
        return "/admin/exam/userpaper";
    }

    @RequestMapping("userPaperData")
    @ResponseBody
    public RestPaging<UserPaperVO> userPaperData(HttpServletRequest request, Page<UserPaper> page, UserPaperDto userPaperDto) {
        Page<UserPaper> data = userPaperService.page(page, userPaperDto.toQuery());
        List<UserPaperVO> list = userPaperService.listUserPaperVOByUserPaper(data.getRecords());
        return new RestPaging<>(data.getTotal(), list);
    }

    @RequestMapping("userExamAuth")
    public String userExamAuth(HttpServletRequest request, String examId, Model model) {
        model.addAttribute("examId", examId);
        return "/admin/exam/userexamauth";
    }

    @RequestMapping("userExamAuthData")
    @ResponseBody
    public RestPaging<ExamAuthVO> userExamAuthData(HttpServletRequest request, Page<ExamAuth> page, ExamAuthDto examAuthDto) {
        Page<ExamAuthVO> data = examAuthService.pageExamAuthByDto(page, examAuthDto);
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("addExamAuth")
    @ResponseBody
    public RestResult addExamAuth(HttpServletRequest request, String examId, String userIds, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        if (StringUtils.isBlank(examId) || StringUtils.isBlank(userIds)) {
            return RestResult.Fail("参数异常");
        }
        examAuthService.upExamAuth(userInfo, examId, userIds);
        return RestResult.OK();
    }

    @RequestMapping("delExamAuth")
    @ResponseBody
    public RestResult delExamAuth(HttpServletRequest request, String ids) {
        if (StringUtils.isBlank(ids)) {
            return RestResult.Fail("参数异常");
        }
        examAuthService.removeBatchByIds(Arrays.asList(ids.split(",")));
        return RestResult.OK();
    }
}
