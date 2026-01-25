package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.FileTableDto;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 实时读取并显示，用户上传目录磁盘中，所有文件信息
 */
@Controller
@RequestMapping("admin/file")
public class FileController {

    @RequestMapping(value = "fileList")
    public String fileList(HttpServletRequest request) {
        return "/admin/file/list";
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
        String path = fileTableDto.getPath();
        if (path != null && path.contains("..")) {
            model.addAttribute("fileData", UpLoadUtil.fileData(null));
            return "/admin/file/filetable";
        }
        String upath_relative = UpLoadUtil.PATH_INFO + UpLoadUtil.getUserPath(userInfo.getId());// 用户基础文件夹(相对路径)
        String upath_absolute = UpLoadUtil.getProjectPath() + upath_relative;// 用户基础文件夹(绝对路径)
        if (StringUtils.isNotBlank(path)) {
            upath_absolute = upath_absolute + path;
            upath_relative = upath_relative + path;
        }
        model.addAttribute("fileData", UpLoadUtil.fileData(upath_absolute));
        model.addAttribute("upathRelative", upath_relative);
        model.addAttribute("path", path);
        model.addAttribute("fileTableDto", fileTableDto);
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
        Utils.hasFolder(UpLoadUtil.doUpathAbsolute(request.getSession(), null) + "/" + folderName);
        return RestResult.OK();
    }

}
