package com.xxsword.xitem.pc;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.controller.BaseController;
import com.xxsword.xitem.admin.domain.course.entity.Course;
import com.xxsword.xitem.admin.domain.course.entity.CourseUser;
import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.system.entity.Organ;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrder;
import com.xxsword.xitem.admin.domain.workorder.dto.WorkOrderDto;
import com.xxsword.xitem.admin.model.Codes;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.course.CourseService;
import com.xxsword.xitem.admin.service.course.CourseUserService;
import com.xxsword.xitem.admin.service.exam.ExamService;
import com.xxsword.xitem.admin.service.exam.UserPaperService;
import com.xxsword.xitem.admin.service.system.OrganService;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.service.workorder.WorkOrderService;
import com.xxsword.xitem.admin.utils.AesEncryptUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("pc/user")
public class PcUserController extends BaseController {

    @Autowired
    private UserPaperService userPaperService;
    @Autowired
    private ExamService examService;
    @Autowired
    private CourseUserService courseUserService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private WorkOrderService workOrderService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private OrganService organService;

    /**
     * 用户中心
     *
     * @return
     */
    @GetMapping("userCenter")
    public String userCenter() {
        return "/pc/user/usercenter";
    }

    /**
     * 考试纪录
     */
    @GetMapping("userExam")
    public String userExam() {
        return "/pc/user/userexam";
    }

    @GetMapping("userExamData")
    @ResponseBody
    public RestResult userExamData(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        UserInfo userInfo = Utils.getUserInfo(request);
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }
        Page<UserPaper> userPaperPage = userPaperService.pageUserExamRecord(new Page<>(pageNum, pageSize), userInfo.getId());
        Set<String> examIds = userPaperPage.getRecords().stream().map(UserPaper::getExamId).collect(Collectors.toSet());
        if (examIds.isEmpty()) {
            return RestResult.OK();
        }
        List<Exam> examList = examService.listByIds(examIds);
        return RestResult.OK(examList);
    }

    /**
     * 课程纪录
     */
    @GetMapping("userCourse")
    public String userCourse() {
        return "/pc/user/usercourse";
    }

    @GetMapping("userCourseData")
    @ResponseBody
    public RestResult userCourseData(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        UserInfo userInfo = Utils.getUserInfo(request);
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }
        Page<CourseUser> pageCourseUser = courseUserService.pageCourseUser(new Page<>(pageNum, pageSize), userInfo.getId());
        Set<String> ids = pageCourseUser.getRecords().stream().map(CourseUser::getCourseId).collect(Collectors.toSet());
        if (ids.isEmpty()) {
            return RestResult.OK();
        }
        List<Course> courseList = courseService.listByIds(ids);
        return RestResult.OK(courseList);
    }

    /**
     * 反馈纪录
     */
    @GetMapping("userWorkOrder")
    public String userWorkOrder() {
        return "/pc/user/userworkorder";
    }

    @GetMapping("userWorkOrderData")
    @ResponseBody
    public RestResult userWorkOrderData(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        UserInfo userInfo = Utils.getUserInfo(request);
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }
        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserId(userInfo.getId());
        Page<WorkOrder> pageWorkOrder = workOrderService.page(new Page<>(pageNum, pageSize), workOrderDto.toQuery());
        return RestResult.OK(pageWorkOrder.getRecords());
    }

    /**
     * 个人信息
     */
    @GetMapping("userInfo")
    public String userInfo(HttpServletRequest request, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        userInfo = userInfoService.getById(userInfo.getId());// 获取最新数据
        if (StringUtils.isNotBlank(userInfo.getOrganId())) {
            userInfo.setOrganName(organService.organPNames(userInfo.getOrganId()));
        }
        model.addAttribute("userInfo", userInfo);
        return "/pc/user/userinfo";
    }

    @PostMapping("userInfoSave")
    @ResponseBody
    public RestResult userInfoSave(HttpServletRequest request, String nickName, String email) {
        UserInfo userInfo = Utils.getUserInfo(request);
        UserInfo userInfoUp = new UserInfo();
        userInfoUp.setId(userInfo.getId());
        userInfoUp.setBaseInfo(userInfo);
        userInfoUp.setNickName(nickName);
        userInfoUp.setEmail(email);
        userInfoService.updateById(userInfoUp);
        return RestResult.OK();
    }

    /**
     * 修改密码
     */
    @GetMapping("userPassword")
    public String userPassword(HttpServletRequest request, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        userInfo = userInfoService.getById(userInfo.getId());// 获取最新数据
        if (StringUtils.isNotBlank(userInfo.getOrganId())) {
            userInfo.setOrganName(organService.organPNames(userInfo.getOrganId()));
        }
        model.addAttribute("userInfo", userInfo);
        return "/pc/user/userpassword";
    }

    @PostMapping("userPasswordSave")
    @ResponseBody
    public RestResult userPasswordSave(HttpServletRequest request, String password1, String password2, String password3) {
        if (StringUtils.isBlank(password1) || StringUtils.isBlank(password2) || StringUtils.isBlank(password3)) {
            return RestResult.Fail();
        }
        UserInfo userInfo = Utils.getUserInfo(request);
        userInfo = userInfoService.getById(userInfo.getId());// 获取最新数据
        password1 = password1.trim();
        password2 = password2.trim();
        password3 = password3.trim();
        if (!password1.equals(Utils.passwordDE(userInfo.getPassword()))) {
            return RestResult.Fail("原密码错误");
        }
        if (!password2.equals(password3)) {
            return RestResult.Fail("输入的新密码不一致");
        }
        if (!Utils.isValidPassword(password3)) {
            return RestResult.Codes(Codes.PASSWORD_COMPLEXITY);
        }
        UserInfo userInfoUp = new UserInfo();
        userInfoUp.setId(userInfo.getId());
        userInfoUp.setBaseInfo(userInfo);
        userInfoUp.setPassword(Utils.passwordEN(password3));
        userInfoService.updateById(userInfoUp);
        return RestResult.OK();
    }
}
