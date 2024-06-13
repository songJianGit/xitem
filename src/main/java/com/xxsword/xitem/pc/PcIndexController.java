package com.xxsword.xitem.pc;

import com.xxsword.xitem.admin.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("pc")
public class PcIndexController extends BaseController {

    @GetMapping("outLogin")
    public String pclogin(HttpServletRequest request) {
        request.getSession().invalidate();// 销毁老的
        request.getSession();// 创建新的
        return "redirect:/login";
    }

}
