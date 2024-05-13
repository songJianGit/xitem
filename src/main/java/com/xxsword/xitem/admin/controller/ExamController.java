package com.xxsword.xitem.admin.controller;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.dto.ExamDto;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.exam.*;
import com.xxsword.xitem.admin.utils.DateUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

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
    private QuestionRuleService questionRuleService;
    @Autowired
    private QRSService qrsService;
    @Autowired
    private UserPaperQuestionService userPaperQuestionService;

    @RequestMapping("list")
    public String index(Integer extype, String examid, String msg, Integer scoretype, Model model) {
        model.addAttribute("extype", extype);
        model.addAttribute("examid", examid);
        model.addAttribute("scoretype", scoretype);
        return "admin/exam/list";
    }

    @RequestMapping("data")
    @ResponseBody
    public RestPaging<Exam> data(HttpServletRequest request, Page page, ExamDto examDto) {
        Page<Exam> data = examService.page(page, examDto.toQuery());
        examService.setExamexstatus(data.getRecords());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        Exam exam = new Exam();
        if (StringUtils.isNotBlank(id)) {
            exam = examService.getById(id);
        }
        model.addAttribute("exam", exam);
        return "admin/exam/edit";
    }

    @RequestMapping("save")
    public String save(HttpServletRequest request, Exam exam) {
        UserInfo userInfo = Utils.getUserInfo(request);
        exam.setBaseInfo(userInfo);
        examService.saveOrUpdate(exam);
        return "redirect:list";
    }

    @RequestMapping("del")
    @ResponseBody
    public RestResult del(HttpServletRequest request, String id) {
        Exam examUp = new Exam();
        examUp.setId(id);
        examUp.setStatus(0);
        examService.updateById(examUp);
        return RestResult.OK();
    }

//    /**
//     * 考试题目
//     *
//     * @param id
//     * @param scoretype 试卷分数规则；1-多抽题规则模式（分数记录在规则中） 2-固定一条规则模式（分数记录在中间表）
//     * @param model
//     * @return
//     */
//    @RequestMapping("examQuestion")
//    public String examQuestion(HttpServletRequest request, String id, Integer scoretype, Model model) {
//        UserInfo userInfo = Utils.getUserInfo(request);
//        scoretype = scoretype == null ? 2 : scoretype;// 默认一条规则，直接导入题目，题目分数记录在中间表
//        paperService.checkExamPaper(id);
//        Exam exam = examService.getById(id);
//        List<Paper> paperList = paperService.listPaper(id);
//        Paper paper0 = paperList.get(0);// 设计上支持多张试卷，但是目前仅默认使用一张
//        model.addAttribute("exam", exam);
//        model.addAttribute("paper0", paper0);
//        model.addAttribute("examstatus", ExamUtil.getExamStatus(exam));
//        model.addAttribute("scoretype", scoretype);
//        if (scoretype == 2) {
//            QuestionRule questionRule = checkScoreType2(paper0, userInfo, scoretype);
//            model.addAttribute("questionRule", questionRule);
//        }
//        return "admin/exam/examquestionrule";
//    }
//
//    /**
//     * scoretype为2的时候，默认初始一条规则
//     */
//    private QuestionRule checkScoreType2(Paper paper0, UserInfo userInfo, int scoretype) {
//        List<QuestionRule> list = questionRuleService.listQuestionRuleByPid(paper0.getId());
//        if (list.size() > 1 && scoretype == 2) {// 规则大于1条，且scoretype为2时，删除多余规则（多加一次判断，加一层保险）
//            for (int i = 1; i < list.size(); i++) {
//                QuestionRule item = list.get(i);
//                item.setStatus(0);
//                questionRuleService.updateById(item);
//            }
//        }
//        if (list.size() == 0) {
//            QuestionRule questionRule = new QuestionRule();
//            questionRule.setStatus(1);
//            questionRule.setCdate(DateUtil.now());
//            questionRule.setCuserid(userInfo.getId());
//            questionRule.setCorganid(userInfo.getOrganid());
//            questionRule.setScoretype(2);
//            questionRule.setScore(0D);
//            questionRule.setPid(paper0.getId());
//            questionRule.setNum(-1);
//            questionRule.setTitle(paper0.getTitle() + "默认规则");
//            questionRuleService.save(questionRule);
//            return questionRule;
//        }
//        return list.get(0);
//    }
//
//    // 抽提规则翻页查询
//    @RequestMapping("questionRuleData")
//    @ResponseBody
//    public RestPaging<QuestionRule> questionRuleData(HttpServletRequest request, String pid, Page page) {
//        Page<QuestionRule> data = questionRuleService.page(page, getQuestionRuleDataQuery(pid, request.getParameterMap()));
//        questionRuleService.setQuestionRuleSNum(data.getRecords());
//        return new RestPaging<QuestionRule>(data.getTotal(), data.getRecords());
//    }
//
//    // 抽题规则查询对象
//    private QueryWrapper getQuestionRuleDataQuery(String pid, Map<String, String[]> map) {
//        QueryWrapper<QuestionRule> query = Wrappers.query();
//        query.eq("pid", pid);
//        query.eq("status", 1);
//        query.orderByAsc("seq", "id");
//        WebHelper.getqueryWrapperFromParams(query, map);
//        return query;
//    }
//
//    // 抽提规则
//    @RequestMapping("questionRule")
//    public String questionRule(HttpServletRequest request, String id, String pid, String examid, Integer scoretype, Model model) {
//        QuestionRule questionRule = new QuestionRule();
//        if (StringUtils.isNotBlank(id)) {
//            questionRule = questionRuleService.getById(id);
//            request.getSession().setAttribute(Constant.QUESTION_RULE_ID, id);
//        }
//        questionRule.setPid(pid);
//        questionRule.setScoretype(scoretype);
//        model.addAttribute("questionRule", questionRule);
//        model.addAttribute("examid", examid);
//        return "admin/exam/paper/questionrule";
//    }
//
//    // 删除抽提规则
//    @RequestMapping("delQuestionRule")
//    @ResponseBody
//    public RestResult delQuestionRule(String id) {
//        QuestionRule questionRule = questionRuleService.getById(id);
//        questionRule.setStatus(0);
//        questionRuleService.updateById(questionRule);
//        return RestResult.OK();
//    }
//
//    // 抽提规则的题目
//    @RequestMapping("pageQRS")
//    @ResponseBody
//    public RestPaging<QRS> pageQRS(HttpServletRequest request, String qrid, Page page) {
//        QueryWrapper<QRS> query = Wrappers.query();
//        query.eq("qrid", qrid);
//        query.orderByAsc("seq", "id");
//        WebHelper.getqueryWrapperFromParams(query, request.getParameterMap());
//        Page<QRS> data = qrsService.page(page, query);
//        qrsService.qRSsetQuestion(data.getRecords());
//        return new RestPaging<QRS>(data.getTotal(), data.getRecords());
//    }
//
//    // 题目与抽提规则的关联
//    @RequestMapping("addQRS")
//    @ResponseBody
//    public RestResult addQRS(HttpServletRequest request, String qrid, String qids, Double score) {
//        if (StringUtils.isBlank(qrid)) {
//            return RestResult.Fail();
//        }
//        if (StringUtils.isBlank(qids)) {
//            return RestResult.Fail();
//        }
//        qrsService.addQRS(qrid, qids, score);
//        return RestResult.OK();
//    }
//
//    // 题目与抽提规则的关联
//    @RequestMapping("delQRS")
//    @ResponseBody
//    public RestResult delQRS(HttpServletRequest request, String qrsid) {
//        if (StringUtils.isBlank(qrsid)) {
//            return RestResult.Fail();
//        }
//        qrsService.removeById(qrsid);
//        return RestResult.OK();
//    }
//
//    // 抽提规则的保存
//    @RequestMapping("saveQuestionRule")
//    @ResponseBody
//    public RestResult saveQuestionRule(HttpServletRequest request, QuestionRule questionRule) {
//        if (StringUtils.isBlank(questionRule.getId())) {
//            UserInfo userInfo = Utils.getUserInfo(request);
//            questionRule.setStatus(1);
//            questionRule.setCdate(DateUtil.now());
//            questionRule.setCuserid(userInfo.getId());
//            questionRule.setCorganid(userInfo.getOrganid());
//        }
//        questionRuleService.saveOrUpdate(questionRule);
//        request.getSession().setAttribute(Constant.QUESTION_RULE_ID, questionRule.getId());
//        return RestResult.OK(questionRule);
//    }
//
//    // 抽题规则的有效性验证
//    @RequestMapping("checkQuestionRule")
//    @ResponseBody
//    public RestResult checkQuestionRule(HttpServletRequest request) {
//        String questionRuleId = (String) request.getSession().getAttribute(Constant.QUESTION_RULE_ID);
//        QuestionRule questionRule = questionRuleService.getById(questionRuleId);
//        Integer count = qrsService.countQRSByQrid(questionRuleId);
//        if (questionRule.getNum() > count) {
//            return RestResult.Fail("该规则下的题目，少于设置的抽取题目数量");
//        }
//        return RestResult.OK();
//    }
//
//    // 抽提规则中的题目顺序调整
//    @RequestMapping("upQRSSeq")
//    @ResponseBody
//    public RestResult upQRSSeq(HttpServletRequest request, String json) {
//        qrsService.upQRSSeq(JSONArray.parseArray(json));
//        return RestResult.OK();
//    }
//
//    // 抽提规则中的分值调整
//    @RequestMapping("upQRSScore")
//    @ResponseBody
//    public RestResult upQRSScore(HttpServletRequest request, String json) {
//        qrsService.upQRSScore(JSONArray.parseArray(json));
//        return RestResult.OK();
//    }
//
//    /**
//     * 考试预览
//     */
//    @RequestMapping("examPageShow")
//    public String examPageShow(HttpServletRequest request, String eid, String pid, RedirectAttributes attributes) {
//        attributes.addAttribute("eid", eid);
//        attributes.addAttribute("pid", pid);
//        attributes.addAttribute("userid", Utils.getUserInfo(request).getId());
//        return "redirect:" + request.getContextPath() + "/pc/exam/examPageShow";
//    }
//
//    /**
//     * 成绩查询
//     */
//    @RequestMapping("examUserScore")
//    public String examUserScore(String eid, Model model) {
//        List<Paper> paperList = paperService.listPaper(eid);
//        if (paperList.size() == 0) {
//            model.addAttribute("paper0id", "");
//        } else {
//            model.addAttribute("paper0id", paperList.get(0).getId());
//        }
//        model.addAttribute("exam", examService.getById(eid));
//        return "admin/exam/userpaper/list";
//    }
//
//    /**
//     * 成绩查询-导出
//     */
//    @RequestMapping("exportExamUserScore")
//    @ResponseBody
//    public void exportExamUserScore(HttpServletRequest request, HttpServletResponse response, String paperid) {
//        Page<UserPaper> paperPage = userPaperService.pageUserPaper(new Page(1, 50000), paperid);
//        Paper paper = paperService.getById(paperid);
//        Exam exam = examService.getById(paper.getEid());
//        List<List<EVO>> datas = examUserInfoService.exportData(paperPage.getRecords(), exam);
//        if (datas.size() > 0) {
//            try {
//                OutputStream stream = response.getOutputStream();
//                response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.encodeFileName("考试成绩" + DateTime.now().toString(DateUtil.sdfB3) + ".xlsx", request));
//                response.setContentType("application/msexcel");
//                ExcelUtils.writeExcelcolXlsx(stream, datas, null);
//                stream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 考试成绩分页查询
//     *
//     * @param request
//     * @param paperid
//     * @param page
//     * @return
//     */
//    @RequestMapping("pageExamUserScore")
//    @ResponseBody
//    public RestPaging<ExamUserInfo> pageExamUserScore(HttpServletRequest request, String paperid, Page page) {
//        if (StringUtils.isBlank(paperid)) {
//            return new RestPaging<ExamUserInfo>(0, Lists.newArrayList());
//        }
//        Page<UserPaper> paperPage = userPaperService.pageUserPaper(page, paperid);
//        List<ExamUserInfo> examUserInfoList = Lists.newArrayList();
//        for (UserPaper item : paperPage.getRecords()) {
//            ExamUserInfo eu = examUserInfoService.getById(item.getUid());
//            eu.setScore(item.getScore());
//            eu.setSubdate(item.getSubdate());
//            eu.setUserpaperid(item.getId());
//            examUserInfoList.add(eu);
//        }
//        return new RestPaging<ExamUserInfo>(paperPage.getTotal(), examUserInfoList);
//    }
//
//    // 考试详情查看
//    @RequestMapping("userpaperquestion")
//    public String userpaperquestion(String userPaperId, Model model) {
//        UserPaper userPaper = userPaperService.getById(userPaperId);
//        List<UserPaperQuestion> userPaperQuestionList = userPaperQuestionService.getPaperQ(userPaper);
//        userPaperQuestionService.setQuestion(userPaperQuestionList);
//        model.addAttribute("userPaperQuestionList", userPaperQuestionList);
//        model.addAttribute("exam", examService.getById(userPaper.getEid()));
//        return "admin/exam/userpaper/userpaperquestion";
//    }
//
//    // 考题页->抽提规则列表->列表统计信息
//    @RequestMapping("questionRuleDataStatistics")
//    @ResponseBody
//    public RestResult questionRuleDataStatistics(HttpServletRequest request, String eid, String pid) {
//        Map m = Maps.newHashMap();
//        List<QuestionRule> questionRuleList = questionRuleService.list(getQuestionRuleDataQuery(pid, request.getParameterMap()));
//        // 考试总分、已设置题目总分、题目总数显示；
//        Exam exam = examService.getById(eid);// 目前考试就一张试卷，则直接取用考试总分
//        int sumQ = 0;
//        double sumQScore = 0;
//        if (questionRuleList.size() == 1 && questionRuleList.get(0).getScoretype() == 2) {
//            List<QRS> qrsList = qrsService.listQRSByQrid(questionRuleList.get(0).getId());
//            sumQ = qrsList.size();
//            for (QRS qrs : qrsList) {
//                sumQScore = Utils.sum(qrs.getScore() == null ? 0 : qrs.getScore(), sumQScore);
//            }
//        } else {
//            for (QuestionRule item : questionRuleList) {
//                sumQScore = Utils.sum(Utils.mul(item.getNum(), item.getScore()), sumQScore);
//                sumQ += item.getNum();
//            }
//        }
//        m.put("examScore", exam.getMaxscore() == null ? "暂无" : exam.getMaxscore());
//        m.put("sumQScore", sumQScore);
//        m.put("sumQ", sumQ);
//        return RestResult.OK(m);
//    }
//
//    /**
//     * 根据传入条件，提交考试记录，计算考试分数
//     * <p>
//     * 关键字：提交，手动，考试，计算，重新计算
//     */
//    @RequestMapping("examSubmitByEidAll")
//    @ResponseBody
//    public RestResult examSubmitByEidAll(HttpServletRequest request, String eid, String pid, String uid, Integer substatus) {
//        UserInfo userInfo = Utils.getUserInfo(request);
//        if (!userInfo.getId().equals("50b708fdb823b19ba9fa78492822d196")) {// 只有这个账号可以做这个危险操作
//            return RestResult.Fail();
//        }
//        logger.info("examSubmitByEidAll start userInfo:{},eid:{},pid:{},uid:{},substatus:{}", userInfo.getId(), eid, pid, uid, substatus);
//        if (StringUtils.isBlank(eid)) {
//            return RestResult.Fail();
//        }
//        userPaperService.examSubmitByEidAll(eid, pid, uid, substatus);
//        logger.info("examSubmitByEidAll end userInfo:{},eid:{},pid:{},uid:{},substatus:{}", userInfo.getId(), eid, pid, uid, substatus);
//        return RestResult.OK();
//    }
}
