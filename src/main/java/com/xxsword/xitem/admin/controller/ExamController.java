package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.exam.dto.ExamDto;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.exam.ExamService;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("admin/exam")
public class ExamController {
    @Autowired
    private ExamService examService;

    @RequestMapping("list")
    public String index() {
        return "/admin/exam/list";
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
        return "/admin/exam/edit";
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
    public RestResult del(HttpServletRequest request, String ids) {
        examService.delExamByIds(ids);
        return RestResult.OK();
    }

}
