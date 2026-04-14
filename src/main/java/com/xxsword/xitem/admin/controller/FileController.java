package com.xxsword.xitem.admin.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.FileTableDto;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("admin/file")
public class FileController {

    @RequestMapping(value = "fileList")
    public String fileList(HttpServletRequest request) {
        return "/admin/file/list";
    }

    @RequestMapping(value = "fileListProject")
    public String fileListProject(HttpServletRequest request) {
        return "/admin/file/listproject";
    }

    /**
     * 文件管理-列表数据（各用户文件夹数据）
     *
     * @param request
     * @param fileTableDto
     * @param model
     * @return
     */
    @RequestMapping(value = "fileTable")
    public String fileTable(HttpServletRequest request, FileTableDto fileTableDto, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        String upath_relative = UpLoadUtil.PATH_INFO + UpLoadUtil.getUserPath(userInfo.getId());// 用户基础文件夹(相对路径)
        return fileTableAll(request, fileTableDto, upath_relative, model, "fileTable");
    }

    /**
     * 项目目录
     *
     * @param request
     * @param fileTableDto
     * @param model
     * @return
     */
    @RequestMapping(value = "fileTableProject")
    public String fileTableProject(HttpServletRequest request, FileTableDto fileTableDto, Model model) {
        String projectId = Utils.getProjectId(request);
        if (StringUtils.isNotBlank(projectId) && FilenameUtils.normalize(projectId) == null) {
            model.addAttribute("fileData", UpLoadUtil.fileData(null));
            return "/admin/file/filetable";
        }
        UserInfo userInfo = Utils.getUserInfo(request);
        String upath_relative = UpLoadUtil.PATH_INFO + UpLoadUtil.getUserPath(userInfo.getId()) + "/projectFile/" + projectId;// 用户基础文件夹(相对路径)
        return fileTableAll(request, fileTableDto, upath_relative, model, "fileTableProject");
    }

    private String fileTableAll(HttpServletRequest request, FileTableDto fileTableDto, String upath_relative, Model model, String lastPath) {
        String path = fileTableDto.getPath();
//        if (path != null && path.contains("..")) {
//            model.addAttribute("fileData", UpLoadUtil.fileData(null));
//            return "/admin/file/filetable";
//        }
        if (StringUtils.isNotBlank(path) && FilenameUtils.normalize(path) == null) {
            model.addAttribute("fileData", UpLoadUtil.fileData(null));
            return "/admin/file/filetable";
        }
        String upath_absolute = UpLoadUtil.getProjectPath() + upath_relative;// 用户基础文件夹(绝对路径)
        if (StringUtils.isNotBlank(path)) {
            upath_absolute = upath_absolute + path;
            upath_relative = upath_relative + path;
        }
        model.addAttribute("fileData", UpLoadUtil.fileData(upath_absolute));
        model.addAttribute("upathRelative", upath_relative);
        model.addAttribute("path", path);
        model.addAttribute("fileTableDto", fileTableDto);
        model.addAttribute("lastPath", lastPath);
        UpLoadUtil.doUpathAbsolute(request.getSession(), upath_absolute);
        return "/admin/file/filetable";
    }

    /**
     * 返回上一级
     *
     * @param request
     * @param fileTableDto
     * @param type         1-不返回上一级 0-返回上一级（默认）
     * @param model
     * @return
     */
    @RequestMapping(value = "fileTableBack")
    public String fileTableBack(HttpServletRequest request, FileTableDto fileTableDto, Integer type, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        String path = UpLoadUtil.doUpathAbsolute(request.getSession(), null);
        String upath_absolute = UpLoadUtil.getProjectPath() + UpLoadUtil.PATH_INFO + UpLoadUtil.getUserPath(userInfo.getId());

        path = UpLoadUtil.replaceSeparator(path);
        upath_absolute = UpLoadUtil.replaceSeparator(upath_absolute);

        if (type == null) {
            type = 0;
        }
        if (type == 0) {
            path = path.substring(0, path.lastIndexOf("/"));
        }
        path = path.replaceAll(upath_absolute, "");
        fileTableDto.setPath(path);
        return fileTable(request, fileTableDto, model);
    }

    /**
     * 新建文件夹
     */
    @RequestMapping(value = "createFolder")
    @ResponseBody
    public RestResult createFolder(HttpServletRequest request, String folderName) {
        if (StringUtils.isBlank(folderName) || FilenameUtils.normalize(folderName) == null) {
            return RestResult.Fail("异常路径");
        }
        Utils.hasFolder(UpLoadUtil.doUpathAbsolute(request.getSession(), null) + "/" + folderName);
        return RestResult.OK();
    }

    @RequestMapping(value = "delFile")
    @ResponseBody
    public RestResult delFile(HttpServletRequest request, String fileName) {
        if (StringUtils.isBlank(fileName) || FilenameUtils.normalize(fileName) == null) {
            return RestResult.Fail("异常路径");
        }
        String path = UpLoadUtil.doUpathAbsolute(request.getSession(), null) + "/" + fileName;
        if (path.startsWith(UpLoadUtil.getProjectPath())) {
            boolean b = FileUtil.del(path);
            log.warn("delFile:{},status:{}", path, b);
        }
        return RestResult.OK();
    }

}
