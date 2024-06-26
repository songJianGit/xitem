package com.xxsword.xitem.pc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.convert.PaperConvert;
import com.xxsword.xitem.admin.domain.exam.dto.ExamDto;
import com.xxsword.xitem.admin.domain.exam.entity.*;
import com.xxsword.xitem.admin.domain.exam.vo.PaperVO;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionVO;
import com.xxsword.xitem.admin.domain.exam.vo.UserPaperVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.exam.*;
import com.xxsword.xitem.admin.utils.DateUtil;
import com.xxsword.xitem.admin.utils.ExamUtil;
import com.xxsword.xitem.admin.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("pc/exam")
public class PcExamController {
    @Autowired
    private ExamService examService;
    @Autowired
    private UserPaperService userPaperService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserPaperQuestionService userPaperQuestionService;
    @Autowired
    private QuestionRuleService questionRuleService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private ExamAuthService examAuthService;

    @RequestMapping("examType")
    public String examType(Integer exType, Model model) {
        model.addAttribute("exType", exType == null ? 1 : exType);
        return "/pc/exam/examtype";
    }

    @RequestMapping("index")
    public String index(HttpServletRequest request, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);

        ExamDto examDto0 = new ExamDto();
        examDto0.setReleaseStatus(1);
        examDto0.setExType(0);
        Page<Exam> examPage0 = examService.pageExamByUser(new Page<>(1, 10), userInfo.getId(), examDto0);

        ExamDto examDto1 = new ExamDto();
        examDto1.setReleaseStatus(1);
        examDto1.setExType(1);
        Page<Exam> examPage1 = examService.pageExamByUser(new Page<>(1, 10), userInfo.getId(), examDto1);

        model.addAttribute("examList0", examPage0 == null ? (new ArrayList<>()) : examPage0.getRecords());
        model.addAttribute("examList1", examPage1 == null ? (new ArrayList<>()) : examPage1.getRecords());
        return "/pc/exam/examindex";
    }

    @RequestMapping("examTypeData")
    @ResponseBody
    public RestResult examTypeData(HttpServletRequest request, Integer exType, Integer pageNum, Integer pageSize) {
        if (pageSize == null) {
            pageSize = 20;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }
        UserInfo userInfo = Utils.getUserInfo(request);
        ExamDto examDto = new ExamDto();
        examDto.setReleaseStatus(1);
        examDto.setExType(exType == null ? 1 : exType);
        Page<Exam> examPage = examService.pageExamByUser(new Page<>(pageNum, pageSize), userInfo.getId(), examDto);
        if (examPage == null) {
            return RestResult.Fail();
        }
        return RestResult.OK(examPage.getRecords());
    }

    /**
     * 考试简介页
     *
     * @param eid
     * @param model
     * @return
     */
    @RequestMapping("e/{eid}")
    public String examId(HttpServletRequest request, @PathVariable String eid, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Exam exam = examService.getById(eid);
        if (!examAuthService.checkUserExamAuth(userInfo.getId(), exam)) {
            model.addAttribute("exStatus", "未授权该考试");
            return "/pc/exam/examerror";
        }
        List<UserPaper> userPaperList = userPaperService.listUserPaper(userInfo.getId(), exam.getPaperId(), exam.getId(), 1);
        List<UserPaperVO> userPaperVOList = userPaperService.listUserPaperVOByUserPaper(userPaperList);
        model.addAttribute("exam", exam);
        model.addAttribute("paperScore", questionRuleService.getPaperScore(exam.getPaperId()));
        model.addAttribute("examStatus", ExamUtil.getExamStatus(exam));
        model.addAttribute("listUserPaper", userPaperVOList);
        return "/pc/exam/exam";
    }

    /**
     * 进入考试
     *
     * @param request
     * @param eid
     * @return
     */
    @RequestMapping("examPageShow")
    public String examPageShow(HttpServletRequest request, String eid, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Exam exam = examService.getById(eid);
        if (!examAuthService.checkUserExamAuth(userInfo.getId(), exam)) {
            model.addAttribute("exStatus", "未授权该考试");
            return "/pc/exam/examerror";
        }
        if (!examExamStatusCheck(exam, model)) {
            return "/pc/exam/examerror";
        }
        Long countUserPaper = userPaperService.countUserPaper(userInfo.getId(), exam.getPaperId(), exam.getId(), 1);
        if (countUserPaper >= exam.getMaxNum() && exam.getMaxNum() > 0) {
            model.addAttribute("exStatus", "已超过最大考试次数");
            return "/pc/exam/examerror";
        }
        UserPaper userPaper = userPaperService.getUserPaper(userInfo, exam.getPaperId(), exam.getId(), 1);
        if (!examCheckUserPaper(exam, userPaper, model)) {
            return "/pc/exam/examerror";
        }
        model.addAttribute("examTitle", exam.getTitle());
        model.addAttribute("endTime", ExamUtil.examEndTime(exam, userPaper));
        model.addAttribute("userPaperId", userPaper.getId());
        return "pc/exam/paperquestion";
    }

    /**
     * 获取试卷题目信息
     *
     * @param userPaperId
     * @return
     */
    @RequestMapping("userPaperQuestionIds")
    @ResponseBody
    public RestResult userPaperQuestionIds(String userPaperId) {
        List<String> userPaperQuestionIds = userPaperQuestionService.getPaperQ(userPaperId).stream().map(UserPaperQuestion::getId).collect(Collectors.toList());
        return RestResult.OK(userPaperQuestionIds);
    }

    /**
     * 考试状态检查
     *
     * @param exam
     * @param model
     * @return true-可以考 false-不能考
     */
    private boolean examExamStatusCheck(Exam exam, Model model) {
        int exStatus = ExamUtil.getExamStatus(exam);
        if (exStatus != 1) {// 考试时间
            if (exStatus == 0) {
                model.addAttribute("exStatus", "考试未开始");
            }
            if (exStatus == 2) {
                model.addAttribute("exStatus", "考试已结束");
            }
            return false;
        }
        return true;
    }

    /**
     * 考试时长超时状态检查
     *
     * @param exam
     * @return
     */
    private boolean examCheckUserPaper(Exam exam, UserPaper userPaper, Model model) {
        if (!ExamUtil.examDurationCheck(exam, userPaper)) {
            model.addAttribute("duration", exam.getDuration());
            model.addAttribute("cDate", userPaper.getCreateDate());
            model.addAttribute("nowDate", DateUtil.now());
            model.addAttribute("exStatus", "超过考试时长未交卷");
            model.addAttribute("userPaperId", userPaper.getId());
            return false;
        }
        return true;
    }

    /**
     * 考试页中的下一题
     *
     * @return
     */
    @RequestMapping("getQuestion")
    @ResponseBody
    public RestResult getQuestion(HttpServletRequest request, String nextQid) {
        UserPaperQuestion userPaperQuestion = userPaperQuestionService.getById(nextQid);
        UserPaper userPaper = userPaperService.getById(userPaperQuestion.getUserPaperId());
        if (userPaper == null) {
            return RestResult.Fail("参数异常");
        }
        if (!ExamUtil.examDurationCheck(examService.getById(userPaper.getExamId()), userPaper)) {
            return RestResult.Fail("已超过考试时长，请提交试卷");
        }
        if (userPaper.getStatus() == 1 && userPaper.getSubStatus() == 0) {
            QuestionVO questionVO = questionService.getQuestionVO(userPaperQuestion, true, false, true);
            return RestResult.OK(questionVO);
        } else {
            return RestResult.Fail("考试状态异常，请重新进入");
        }
    }

    @PostMapping("saveAnswer")
    @ResponseBody
    public RestResult saveAnswer(HttpServletRequest request, String qid, String answers) {
        if (StringUtils.isBlank(answers)) {
            return RestResult.OK("未作答");
        }
        UserInfo userInfo = Utils.getUserInfo(request);
        userPaperQuestionService.upUserPaperQuestionAnswers(userInfo, qid, answers);
        return RestResult.OK();
    }

    /**
     * 考试提交
     *
     * @param userPaperId
     * @return
     */
    @RequestMapping("examPageSubmit")
    @ResponseBody
    public RestResult examPageSubmit(HttpServletRequest request, String userPaperId) {
        UserInfo userInfo = Utils.getUserInfo(request);
        userPaperService.userPaperSub(userInfo, userPaperId);
        return RestResult.OK();
    }

    /**
     * 考试提交成功页
     *
     * @return
     */
    @RequestMapping("examSubmitOk")
    public String examSubmitOk(String userPaperId, Model model) {
        UserPaper userPaper = userPaperService.getById(userPaperId);
        Exam exam = examService.getById(userPaper.getExamId());
        model.addAttribute("score", userPaper.getScore());// 得分
        model.addAttribute("maxscore", questionRuleService.getPaperScore(exam.getPaperId()));// 总分
        model.addAttribute("examtitle", exam.getTitle());
        model.addAttribute("paperduration", DateUtil.sToHHmmss(DateUtil.differSecond(userPaper.getCreateDate(), userPaper.getSubDate(), DateUtil.sdfA1)));// 考试用时
        return "/pc/exam/examok";
    }

    /**
     * 哪些题目未作答
     *
     * @param userPaperId
     * @return
     */
    @RequestMapping("checkBlankNum")
    @ResponseBody
    public RestResult checkBlankNum(String userPaperId) {
        UserPaper userPaper = userPaperService.getById(userPaperId);
        List<Integer> integerList = userPaperQuestionService.checkBlankNum(userPaper);
        return RestResult.OK(integerList);
    }

    /**
     * 答题卡显示
     */
    @RequestMapping("examSheet")
    public String examSheet(String userPaperId, Model model) {
        List<UserPaperQuestion> userPaperQuestionList = userPaperQuestionService.getPaperQ(userPaperId);
        model.addAttribute("userPaperQuestionList", userPaperQuestionList);
        return "/pc/exam/examsheet";
    }

    /**
     * 用户自己的查卷
     *
     * @param request
     * @return
     */
    @RequestMapping("userPaperPreview")
    public String userPaperPreview(HttpServletRequest request, String userPaperId, Model model) {
        UserPaper userPaper = userPaperService.getById(userPaperId);
        Paper paper = paperService.getById(userPaper.getPaperId());
        List<QuestionVO> questionVOList = userPaperQuestionService.listQuestionByUserPaper(userPaper, true, true, true);
        PaperVO paperVO = PaperConvert.INSTANCE.toPaperVO(paper);
        paperVO.setQuestionVOList(questionVOList);
        paperVO.setSnum(questionVOList.size());
        paperVO.setScore(Utils.sum(questionVOList.stream().map(QuestionVO::getQscore).collect(Collectors.toList())));

        model.addAttribute("paperVO", paperVO);
        return "/pc/exam/userpaperpreview";
    }
}
