package com.xxsword.xitem.admin.controller;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.convert.PaperConvert;
import com.xxsword.xitem.admin.domain.exam.dto.PaperDto;
import com.xxsword.xitem.admin.domain.exam.dto.QRSDto;
import com.xxsword.xitem.admin.domain.exam.dto.QuestionDto;
import com.xxsword.xitem.admin.domain.exam.dto.QuestionRuleDto;
import com.xxsword.xitem.admin.domain.exam.entity.*;
import com.xxsword.xitem.admin.domain.exam.vo.PaperVO;
import com.xxsword.xitem.admin.domain.exam.vo.QRSVO;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.Codes;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.exam.*;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("admin/paper")
public class PaperController {
    @Autowired
    private PaperService paperService;
    @Autowired
    private QuestionRuleService questionRuleService;
    @Autowired
    private QRSService qrsService;
    @Autowired
    private UserPaperService userPaperService;
    @Autowired
    private UserPaperQuestionService userPaperQuestionService;


    @RequestMapping("list")
    public String list() {
        return "/admin/exam/paper/list";
    }

    @RequestMapping("data")
    @ResponseBody
    public RestPaging<Paper> data(HttpServletRequest request, Page<Paper> page, PaperDto paperDto) {
        Page<Paper> data = paperService.page(page, paperDto.toQuery());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        Paper paper = new Paper();
        if (StringUtils.isNotBlank(id)) {
            paper = paperService.getById(id);
        }
        model.addAttribute("paper", paper);
        return "/admin/exam/paper/edit";
    }

    @RequestMapping("save")
    public String save(HttpServletRequest request, Paper paper) {
        UserInfo userInfo = Utils.getUserInfo(request);
        paper.setBaseInfo(userInfo);
        paperService.saveOrUpdate(paper);
        return "redirect:list";
    }

    @RequestMapping("del")
    @ResponseBody
    public RestResult del(HttpServletRequest request, String ids) {
        UserInfo userInfo = Utils.getUserInfo(request);
        paperService.delPaperByIds(ids);
        paperService.upLastInfo(userInfo, ids);
        return RestResult.OK();
    }

    /**
     * 试卷的题目编辑(抽题规则的编辑)
     *
     * @param paperId
     * @param model
     * @return
     */
    @RequestMapping("questionRule")
    public String questionRule(String paperId, Model model) {
        Paper paper = paperService.getById(paperId);
        model.addAttribute("paper", paper);
        return "/admin/exam/paper/questionrule";
    }

    /**
     * 新增一条抽题规则
     *
     * @param request
     * @param paperId
     * @return
     */
    @RequestMapping("addQuestionRule")
    @ResponseBody
    public RestResult addQuestionRule(HttpServletRequest request, String paperId) {
        if (StringUtils.isBlank(paperId)) {
            return RestResult.Codes(Codes.PARAMETER_NULL);
        }
        UserInfo userInfo = Utils.getUserInfo(request);
        questionRuleService.addQuestionRule(userInfo, paperId);
        return RestResult.OK();
    }

    @RequestMapping("upQuestionRule")
    @ResponseBody
    public RestResult upQuestionRule(HttpServletRequest request, QuestionRule questionRule) {
        UserInfo userInfo = Utils.getUserInfo(request);
        int countQRS = qrsService.countQRSByQrid(questionRule.getId()).intValue();
        if (countQRS == 0) {
            return RestResult.Fail("请添加考题");
        }
        if (countQRS < questionRule.getNum()) {
            return RestResult.Fail("抽提数应小于总提数");
        } else {
            questionRule.setBaseInfo(userInfo);
            questionRuleService.updateById(questionRule);
            return RestResult.OK();
        }
    }

    /**
     * 删除抽题规则
     *
     * @param request
     * @param questionRuleIds
     * @return
     */
    @RequestMapping("delQuestionRule")
    @ResponseBody
    public RestResult delQuestionRule(HttpServletRequest request, String questionRuleIds) {
        UserInfo userInfo = Utils.getUserInfo(request);
        questionRuleService.delByIds(questionRuleIds);
        questionRuleService.upLastInfo(userInfo, questionRuleIds);
        return RestResult.OK();
    }

    /**
     * 抽题规则的拖拽
     * 排序字段的交换
     *
     * @param request
     * @return
     */
    @RequestMapping("questionRuleSeq")
    @ResponseBody
    public RestResult questionRuleSeq(HttpServletRequest request, String id1, String id2) {
        UserInfo userInfo = Utils.getUserInfo(request);
        questionRuleService.questionRuleSeq(userInfo, id1, id2);
        return RestResult.OK();
    }

    /**
     * 抽提规则查询
     *
     * @param request
     * @param questionRuleDto
     * @return
     */
    @RequestMapping("questionRuleData")
    @ResponseBody
    public RestPaging questionRuleData(HttpServletRequest request, QuestionRuleDto questionRuleDto) {
        List<QuestionRule> data = questionRuleService.list(questionRuleDto.toQuery());
        questionRuleService.setQuestionRuleSNum(data);
        return new RestPaging<>(data.size(), data);
    }

    /**
     * 抽提规则的编辑页面
     *
     * @param request
     * @param qrsDto
     * @param model
     * @return
     */
    @RequestMapping("listQRS")
    public String listQRS(HttpServletRequest request, QRSDto qrsDto, Model model) {
        QuestionRule questionRule = questionRuleService.getById(qrsDto.getQrid());
        model.addAttribute("questionRule", questionRule);
        return "admin/exam/paper/listqrs";
    }

    @RequestMapping("listQRSData")
    @ResponseBody
    public RestPaging<QRSVO> listQRSData(HttpServletRequest request, QRSDto qrsDto, QuestionDto questionDto) {
        List<QRSVO> data = qrsService.listQRS(qrsDto, questionDto);
        return new RestPaging<>(data.size(), data);
    }

    /**
     * 题目与抽提规则的关联
     *
     * @param request
     * @param qrid
     * @param qids
     * @param score
     * @return
     */
    @RequestMapping("addQRS")
    @ResponseBody
    public RestResult addQRS(HttpServletRequest request, String qrid, String qids, Double score) {
        if (StringUtils.isBlank(qrid)) {
            return RestResult.Fail();
        }
        if (StringUtils.isBlank(qids)) {
            return RestResult.Fail();
        }
        UserInfo userInfo = Utils.getUserInfo(request);
        qrsService.addQRS(userInfo, qrid, qids, score);
        return RestResult.OK();
    }

    /**
     * 题目与抽提规则的关联
     *
     * @param request
     * @param qrsids
     * @return
     */
    @RequestMapping("delQRS")
    @ResponseBody
    public RestResult delQRS(HttpServletRequest request, String qrsids) {
        if (StringUtils.isBlank(qrsids)) {
            return RestResult.Fail();
        }
        String[] ids = qrsids.split(",");
        qrsService.removeBatchByIds(Arrays.asList(ids));
        return RestResult.OK();
    }

    /**
     * 抽提规则中的题目顺序调整
     *
     * @param request
     * @param jsonSeq
     * @return
     */
    @RequestMapping("upQRSSeq")
    @ResponseBody
    public RestResult upQRSSeq(HttpServletRequest request, String jsonSeq) {
        qrsService.upQRSSeq(JSONArray.parseArray(jsonSeq));
        return RestResult.OK();
    }

    /**
     * 抽提规则中的分值调整
     *
     * @param request
     * @param jsonScore
     * @return
     */
    @RequestMapping("upQRSScore")
    @ResponseBody
    public RestResult upQRSScore(HttpServletRequest request, String jsonScore) {
        qrsService.upQRSScore(JSONArray.parseArray(jsonScore));
        return RestResult.OK();
    }

    /**
     * 试卷的预览
     *
     * @param request
     * @return
     */
    @RequestMapping("paperShow")
    public String paperShow(HttpServletRequest request, String paperId, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Paper paper = paperService.getById(paperId);
        UserPaper userPaper = userPaperService.getUserPaper(userInfo, paperId, null, 2);
        List<QuestionVO> questionVOList = userPaperQuestionService.listQuestionByUserPaper(userPaper, true, true, true);
        PaperVO paperVO = PaperConvert.INSTANCE.toPaperVO(paper);
        paperVO.setQuestionVOList(questionVOList);
        paperVO.setSnum(questionRuleService.getPaperQNum(paperId));
        paperVO.setScore(questionRuleService.getPaperScore(paperId));

        model.addAttribute("paperVO", paperVO);
        return "/admin/exam/paper/papershow";
    }

}
