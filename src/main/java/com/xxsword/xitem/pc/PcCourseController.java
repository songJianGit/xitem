package com.xxsword.xitem.pc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.constant.TimerType;
import com.xxsword.xitem.admin.controller.BaseController;
import com.xxsword.xitem.admin.domain.course.dto.CourseDto;
import com.xxsword.xitem.admin.domain.course.entity.Course;
import com.xxsword.xitem.admin.domain.course.entity.CourseUser;
import com.xxsword.xitem.admin.domain.course.vo.CoursePlayVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.service.course.CourseFileService;
import com.xxsword.xitem.admin.service.course.CourseService;
import com.xxsword.xitem.admin.service.course.CourseUserService;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("pc/course")
public class PcCourseController extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseUserService courseUserService;
    @Autowired
    private CourseFileService courseFileService;

    @RequestMapping("index")
    public String index(CourseDto courseDto, Integer pageNum, Model model) {
        if (pageNum == null) {
            pageNum = 1;
        }
        courseDto.setReleaseStatus(1);
        Page<Course> coursePage = courseService.page(new Page<>(pageNum, 9), courseDto.toQuery());
        model.addAttribute("courseList", coursePage.getRecords());
        model.addAttribute("totalPages", Utils.getPage(coursePage.getTotal(), (int) coursePage.getSize()));
        model.addAttribute("courseDto", courseDto);
        return "/pc/course/courseindex";
    }

    @RequestMapping("detail")
    public String detail(HttpServletRequest request, String cid, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Course course = courseService.getById(cid);
        CourseUser courseUser = courseUserService.getCourseUser(userInfo.getId(), cid);
        model.addAttribute("course", course);
        model.addAttribute("courseUser", courseUser);
        return "/pc/course/detail";
    }

    @RequestMapping("c/{cid}")
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
