package com.xxsword.xitem.admin.controller;

import com.xxsword.xitem.admin.utils.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("admin/qrcode")
public class QrCodeController {

    // 生成二维码
    @RequestMapping("get")
    public void get(String content, HttpServletResponse servletResponse) {
        try {
            QRCodeUtil.createCodeToOutputStream(content, servletResponse.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
