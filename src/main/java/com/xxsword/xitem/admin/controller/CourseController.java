package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.category.entity.Category;
import com.xxsword.xitem.admin.domain.course.dto.CourseDto;
import com.xxsword.xitem.admin.domain.course.dto.CourseUserDto;
import com.xxsword.xitem.admin.domain.course.entity.Course;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import com.xxsword.xitem.admin.domain.course.entity.CourseUser;
import com.xxsword.xitem.admin.domain.course.vo.CourseUserVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.category.CategoryService;
import com.xxsword.xitem.admin.service.course.CourseFileService;
import com.xxsword.xitem.admin.service.course.CourseService;
import com.xxsword.xitem.admin.service.course.CourseUserService;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
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

@Slf4j
@Controller
@RequestMapping("admin/course")
public class CourseController extends BaseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseFileService courseFileService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CourseUserService courseUserService;

    @RequestMapping("list")
    public String list() {
        return "/admin/course/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public RestPaging<Course> listData(HttpServletRequest request, CourseDto courseDto, Page<Course> page) {
        LambdaQueryWrapper<Course> query = courseDto.toQuery();
        if (StringUtils.isNotBlank(courseDto.getCourseCategory())) {
            List<Category> categoryList = categoryService.categoryC(courseDto.getCourseCategory());
            List<String> ids = categoryList.stream().map(Category::getId).collect(Collectors.toList());
            ids.add(courseDto.getCourseCategory());
            query.in(Course::getCourseCategory, ids);
        }
        Page<Course> data = courseService.page(page, query);
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        Course course = new Course();
        if (StringUtils.isNotBlank(id)) {
            course = courseService.getById(id);
        }
        if (StringUtils.isNotBlank(course.getCourseFileId())) {
            CourseFile courseFile = courseFileService.getById(course.getCourseFileId());
            model.addAttribute("courseFileName", courseFile.getTitle());
        }
        if (StringUtils.isNotBlank(course.getCourseCategory())) {
            course.setCourseCategoryName(categoryService.getById(course.getCourseCategory()).getTitle());
        }
        model.addAttribute("course", course);
        return "/admin/course/edit";
    }

    @RequestMapping("save")
    public String save(HttpServletRequest request, Course course, @RequestParam(value = "fileinfo") MultipartFile multipartFile) {
        UserInfo userInfo = Utils.getUserInfo(request);
        String path = UpLoadUtil.upload(multipartFile, "/coursecover");
        if (StringUtils.isNotBlank(path)) {
            course.setCover(path);
        }
        if (StringUtils.isBlank(course.getId())) {
            course.setSeq(DateTime.now().getMillis());
            course.setReleaseStatus(0);
        }
        courseService.saveOrUpdate(course);
        return "redirect:list";
    }

    @RequestMapping("del")
    @ResponseBody
    public RestResult del(HttpServletRequest request, String ids) {
        UserInfo userInfo = Utils.getUserInfo(request);
        courseService.delByIds(ids);
        return RestResult.OK();
    }

    @RequestMapping("release")
    @ResponseBody
    public RestResult release(HttpServletRequest request, String id) {
        UserInfo userInfo = Utils.getUserInfo(request);
        courseService.release(id);
        return RestResult.OK();
    }

    /**
     * banner的拖拽
     * 排序字段的交换
     *
     * @param request
     * @return
     */
    @RequestMapping("courseSeq")
    @ResponseBody
    public RestResult courseSeq(HttpServletRequest request, String id1, String id2) {
        UserInfo userInfo = Utils.getUserInfo(request);
        courseService.seq(id1, id2);
        return RestResult.OK();
    }

    /**
     * 学习记录查看弹框
     *
     * @return
     */
    @RequestMapping("courseUser")
    public String courseUser(String courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "/admin/course/courseuser";
    }

    @RequestMapping("courseUserData")
    @ResponseBody
    public RestPaging<CourseUserVO> courseUserData(HttpServletRequest request, Page<CourseUser> page, CourseUserDto courseUserDto) {
        Page<CourseUserVO> data = courseUserService.pageCourseUserVOByDto(page, courseUserDto);
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

}
