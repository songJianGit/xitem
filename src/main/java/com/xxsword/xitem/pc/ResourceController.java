package com.xxsword.xitem.pc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxsword.xitem.admin.constant.ResourceType;
import com.xxsword.xitem.admin.controller.BaseController;
import com.xxsword.xitem.admin.domain.course.entity.CourseFileItem;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.service.course.CourseFileItemService;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 敏感资源的逻辑读取
 */
@Slf4j
@Controller
@RequestMapping("resource")
public class ResourceController extends BaseController {

    @Autowired
    private CourseFileItemService courseFileItemService;

    /**
     * 视频资源
     */
    @GetMapping("files/{type}/{id}")
    public ResponseEntity<Resource> videoTypeId(HttpServletRequest request, @PathVariable String type, @PathVariable String id) {
        UserInfo userInfo = Utils.getUserInfo(request);
        if (userInfo == null) {
            log.warn("未登录的资源请求:{},{}", type, id);
            return ResponseEntity.notFound().build();
        }
//        if (!userInfo.getId().equals("1")) {暂不做资源的权限控制
//            log.warn("无该文件权限:{},{},{}", userInfo.getId(), type, id);
//            return ResponseEntity.notFound().build();
//        }
        String path = getFileInfoPath(ResourceType.getRecordTypeByCode(type), id);
        if (StringUtils.isBlank(path)) {
            log.warn("文件地址获取异常：{},{},{}", path, type, id);
            return ResponseEntity.notFound().build();
        }
        String path2 = UpLoadUtil.getProjectPath() + path;
        File file = new File(path2);
        // 检查文件是否存在
        if (!file.exists()) {
            log.warn("文件不存在:{},{},{}", path2, type, id);
            return ResponseEntity.notFound().build();
        }
        // 设置响应头，包括Content-Type和Content-Disposition
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getContentType(ResourceType.getRecordTypeByCode(type)));
        headers.setContentDispositionFormData("attachment", FilenameUtils.getName(path));// 文件名称信息
        // 使用FileSystemResource包装文件，并返回
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource);
    }

    private MediaType getContentType(ResourceType resourceType) {
        if (resourceType == null) {
            return null;
        }
        MediaType type = null;
        switch (resourceType.getCode()) {
            case "courseFileVideo":
                type = MediaType.APPLICATION_OCTET_STREAM;
                break;
            case "courseFileImg":
                type = MediaType.IMAGE_PNG;
                break;
            case "other":
                log.info("其它视频资源");
                break;
        }
        return type;
    }

    /**
     * 获取各类型视频文件路径
     */
    private String getFileInfoPath(ResourceType resourceType, String id) {
        if (resourceType == null) {
            return null;
        }
        String path = null;
        switch (resourceType.getCode()) {
            case "courseFileVideo":
            case "courseFileImg":
                LambdaQueryWrapper<CourseFileItem> query = Wrappers.lambdaQuery();
                query.select(CourseFileItem::getFilePath);
                query.eq(CourseFileItem::getId, id);
                CourseFileItem courseFileItemVideo = courseFileItemService.getOne(query);
                path = courseFileItemVideo == null ? null : courseFileItemVideo.getFilePath();
                break;
            case "other":
                log.info("其它资源");
                break;
        }
        return path;
    }
}
