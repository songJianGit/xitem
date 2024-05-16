package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.dto.PaperDto;
import com.xxsword.xitem.admin.domain.exam.dto.QRSDto;
import com.xxsword.xitem.admin.domain.exam.dto.QuestionRuleDto;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;
import com.xxsword.xitem.admin.domain.exam.entity.QRS;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionRule;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.Codes;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.exam.PaperService;
import com.xxsword.xitem.admin.service.exam.QRSService;
import com.xxsword.xitem.admin.service.exam.QuestionRuleService;
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

    @RequestMapping("listQRS")
    public String listQRS(HttpServletRequest request, QRSDto qrsDto, Model model) {
        QuestionRule questionRule = questionRuleService.getById(qrsDto.getQrid());
        model.addAttribute("questionRule", questionRule);
        return "admin/exam/paper/listqrs";
    }

    /**
     * 抽提规则的题目
     *
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("pageQRS")
    @ResponseBody
    public RestPaging<QRS> pageQRS(HttpServletRequest request, QRSDto qrsDto, Page<QRS> page) {
        Page<QRS> data = qrsService.page(page, qrsDto.toQuery());
        qrsService.qRSsetQuestion(data.getRecords());
        return new RestPaging<>(data.getTotal(), data.getRecords());
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
}
