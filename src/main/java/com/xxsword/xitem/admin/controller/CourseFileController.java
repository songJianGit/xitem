package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.course.dto.CourseFileDto;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import com.xxsword.xitem.admin.domain.course.entity.CourseFileItem;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.course.CourseFileItemService;
import com.xxsword.xitem.admin.service.course.CourseFileService;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("admin/coursefile")
public class CourseFileController extends BaseController {
    @Autowired
    private CourseFileService courseFileService;
    @Autowired
    private CourseFileItemService courseFileItemService;

    @RequestMapping("list")
    public String list() {
        return "/admin/coursefile/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public RestPaging<CourseFile> listData(HttpServletRequest request, CourseFileDto courseFileDto, Page<CourseFile> page) {
        Page<CourseFile> data = courseFileService.page(page, courseFileDto.toQuery());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        CourseFile courseFile = new CourseFile();
        if (StringUtils.isNotBlank(id)) {
            courseFile = courseFileService.getById(id);
        }
        List<CourseFileItem> listCourseFileItem = courseFileItemService.listCourseFileItem(courseFile.getId());
        model.addAttribute("courseFile", courseFile);
        model.addAttribute("courseFileItem0", listCourseFileItem.isEmpty() ? null : listCourseFileItem.get(0));
        return "/admin/coursefile/edit";
    }

    @RequestMapping("save")
    public String save(HttpServletRequest request, CourseFile courseFile, String fileInfos) {
        UserInfo userInfo = Utils.getUserInfo(request);
        courseFile.setBaseInfo(userInfo);
        courseFileService.saveOrUpdate(courseFile);
        courseFileItemService.saveOrUpdateCourseFile(userInfo, courseFile, fileInfos);
        return "redirect:list";
    }

    @RequestMapping("del")
    @ResponseBody
    public RestResult del(HttpServletRequest request, String ids) {
        UserInfo userInfo = Utils.getUserInfo(request);
        courseFileService.delByIds(userInfo, ids);
        return RestResult.OK();
    }

    @RequestMapping("courseFileShow")
    public String courseFileShow(HttpServletRequest request) {
        return "/admin/coursefile/coursefileshow";
    }

    /**
     * 课件的预览
     *
     * @param courseFileId
     * @param model
     * @return
     */
    @RequestMapping("preview")
    public String preview(String courseFileId, Model model) {
        CourseFile courseFile = courseFileService.getById(courseFileId);
        String url = "";
        switch (courseFile.getCourseType()) {
            case 1:
                log.info("预览scorm");
                break;
            case 2:
                List<CourseFileItem> courseFileItemListVideo = courseFileItemService.listCourseFileItem(courseFile.getId());
                List<String> videoIds = courseFileItemListVideo.stream().map(CourseFileItem::getId).collect(Collectors.toList());
                model.addAttribute("videoId", videoIds.isEmpty() ? null : videoIds.get(0));
                url = "/pc/course/playvideo";
                break;
            case 5:
                List<CourseFileItem> courseFileItemListPdf = courseFileItemService.listCourseFileItem(courseFile.getId());
                List<String> pdfIds = courseFileItemListPdf.stream().map(CourseFileItem::getId).collect(Collectors.toList());
                model.addAttribute("pdfIds", pdfIds);
                url = "/pc/course/playimg";
                break;
        }
        model.addAttribute("courseFile", courseFile);
        return url;
    }
}
