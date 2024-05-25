package com.xxsword.xitem.pc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxsword.xitem.admin.controller.BaseController;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.service.exam.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("pc")
public class PcIndexController extends BaseController {

    @Autowired
    private ExamService examService;

    @GetMapping("index")
    public String index(Model model) {
        LambdaQueryWrapper<Exam> examQ = new LambdaQueryWrapper<Exam>().eq(Exam::getStatus, 1);
        List<Exam> examList = examService.list(examQ);
        model.addAttribute("examList", examList);
        return "/pc/index";
    }
}
