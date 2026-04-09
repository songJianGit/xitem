package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.TinyMCECallBackVO;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("admin/tinymce")
public class TinymceController extends BaseController {

    @ResponseBody
    @RequestMapping("upload")
    public TinyMCECallBackVO upload(MultipartFile file, HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        TinyMCECallBackVO tinyMCECallBackVO = new TinyMCECallBackVO();
        Map<String, String> json = new HashMap<>();
        if (StringUtils.isBlank(fileType)) {
            return tinyMCECallBackVO;
        }
        fileType = fileType.toLowerCase();
        switch (fileType) {
            case "image/jpeg":
            case "image/jpg":
            case "image/png":
            case "image/gif":
            case "image/webp":
            case "media/mp4":
                UserInfo userInfo = Utils.getUserInfo(request);
                String url = request.getSession().getServletContext().getContextPath() + UpLoadUtil.upload(file, UpLoadUtil.getUserPath(userInfo.getId()) + "/tinymceFile");
                tinyMCECallBackVO.setUrl(url);
                json.put("alt", fileName);
                tinyMCECallBackVO.setJsonInfo(json);
                break;
        }
        return tinyMCECallBackVO;
    }

}
