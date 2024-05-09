package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.model.ueditor.ActionMap;
import com.xxsword.xitem.admin.utils.UeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequestMapping("admin/ueditor")
public class UeditorController extends BaseController {

    @ResponseBody
    @RequestMapping("ueditorConfig")
    public Object ueditorConfig(MultipartFile upfile, HttpServletRequest request, HttpServletResponse response, String action) {
        int actionCode = ActionMap.getType(action);
        switch (actionCode) {
            case ActionMap.CONFIG:
                return UeUtil.getUEditorConfig(response);
            case ActionMap.UPLOAD_IMAGE:
            case ActionMap.UPLOAD_SCRAWL:
            case ActionMap.UPLOAD_VIDEO:
            case ActionMap.UPLOAD_FILE:
                return UeUtil.uploadFile(upfile, request);
            case ActionMap.CATCH_IMAGE:
                log.info("捕获");// TODO 保存捕获的图
                break;
            case ActionMap.LIST_IMAGE:
            case ActionMap.LIST_FILE:
                log.info("多文件");// TODO 保存多文件
                break;
        }
        return "请求失败";
    }

}
