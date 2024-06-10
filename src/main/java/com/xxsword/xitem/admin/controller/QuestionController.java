package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.domain.exam.dto.QuestionDto;
import com.xxsword.xitem.admin.domain.exam.entity.Question;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.service.exam.QuestionOptionService;
import com.xxsword.xitem.admin.service.exam.QuestionService;
import com.xxsword.xitem.admin.service.system.DictService;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("admin/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private DictService dictService;
    @Autowired
    private QuestionOptionService questionOptionService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("list")
    public String list(Model model) {
        return "/admin/exam/question/list";
    }

    @RequestMapping("data")
    @ResponseBody
    public RestPaging<Question> data(HttpServletRequest request, Page page, QuestionDto questionDto) {
        LambdaQueryWrapper<Question> query = questionDto.toQuery();
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(questionDto.getQcategory())) {
            List<Category> categoryList = categoryService.categoryC(questionDto.getQcategory());
            List<String> ids = categoryList.stream().map(Category::getId).collect(Collectors.toList());
            ids.add(questionDto.getQcategory());
            query.in(Question::getQcategory, ids);
        }
        Page<Question> data = questionService.page(page, query);
        questionService.setQuestionQCategory(data.getRecords());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    /**
     * 题目选择弹框
     *
     * @param model
     * @return
     */
    @RequestMapping("listQuestion")
    public String listQuestion(String qrid, Model model) {
        model.addAttribute("qrid", qrid);
        return "/admin/exam/question/listquestion";
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        Question question = new Question();
        if (StringUtils.isNotBlank(id)) {
            question = questionService.getById(id);
        }
        if(StringUtils.isNotBlank(question.getQcategory())){
            question.setQcategoryName(categoryService.getById(question.getQcategory()).getTitle());
        }
        model.addAttribute("question", question);
        model.addAttribute("questionOption", questionOptionService.questionOptionListByQid(question.getId()));
        return "/admin/exam/question/edit";
    }

    @RequestMapping("save")
    public String save(HttpServletRequest request, Question question, String optionJson) {
        UserInfo userInfo = Utils.getUserInfo(request);
        questionService.saveQuestionAndOption(userInfo, question, optionJson);
        return "redirect:list";
    }

    @RequestMapping("del")
    @ResponseBody
    public RestResult del(HttpServletRequest request, String ids) {
        UserInfo userInfo = Utils.getUserInfo(request);
        questionService.delByIds(ids);
        questionService.upLastInfo(userInfo, ids);
        return RestResult.OK();
    }

    /**
     * 题目的excel导入
     *
     * @return
     */
    @RequestMapping("excelquestion")
    public String excelquestion() {
        return "admin/exam/question/excelquestion";
    }

    @RequestMapping("questionImport")
    @ResponseBody
    public RestResult questionImport(HttpServletRequest request, @RequestParam(value = "fileinfo") MultipartFile multipartFile) {
        UserInfo userInfo = Utils.getUserInfo(request);
        String path = UpLoadUtil.upload(multipartFile, "/questionexcel");
        String fileType = FilenameUtils.getExtension(path).toLowerCase();
        if (StringUtils.isNotBlank(path)) {
            if (fileType.equalsIgnoreCase("xls") || fileType.equalsIgnoreCase("xlsx")) {
                return questionService.excelQuestion(UpLoadUtil.getProjectPath() + path, userInfo);
            } else {
                return RestResult.Fail("只能上传excel文档");
            }
        } else {
            log.warn("questionImport path null");
        }
        return RestResult.Fail();
    }
}
