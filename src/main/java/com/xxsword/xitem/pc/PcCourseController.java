package com.xxsword.xitem.pc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.constant.TimerType;
import com.xxsword.xitem.admin.controller.BaseController;
import com.xxsword.xitem.admin.domain.course.dto.CourseDto;
import com.xxsword.xitem.admin.domain.course.entity.Course;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import com.xxsword.xitem.admin.domain.course.entity.CourseFileItem;
import com.xxsword.xitem.admin.domain.course.vo.CoursePlayVO;
import com.xxsword.xitem.admin.domain.exam.dto.ExamDto;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.exam.vo.UserPaperVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.service.course.CourseFileItemService;
import com.xxsword.xitem.admin.service.course.CourseFileService;
import com.xxsword.xitem.admin.service.course.CourseService;
import com.xxsword.xitem.admin.utils.ExamUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("pc/course")
public class PcCourseController extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseFileService courseFileService;

    @RequestMapping("index")
    public String index(Model model) {
        CourseDto courseDto = new CourseDto();
        courseDto.setReleaseStatus(1);
        List<Course> courseList = courseService.list(new Page<>(1, 12), courseDto.toQuery());
        model.addAttribute("courseList", courseList);
        return "/pc/course/courseindex";
    }

    @RequestMapping("/c/{cid}")
    public String courseId(HttpServletRequest request, @PathVariable String cid, Model model) {
        Course course = courseService.getById(cid);
        CoursePlayVO coursePlayVO = courseFileService.courseFile(course.getCourseFileId());
        if (coursePlayVO == null) {
            return "/pc/course/playerror";
        }
        model.addAttribute("courseFile", coursePlayVO.getCourseFile());
        model.addAttribute("courseFileItemIds", coursePlayVO.getCourseFileItemIds());
        model.addAttribute("timerType", TimerType.COURSE_PLAY);
        model.addAttribute("course", course);
        return coursePlayVO.getUrl();
    }

}
