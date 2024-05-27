package com.xxsword.xitem.pc;

import com.xxsword.xitem.admin.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("pc/user")
public class PcUserController extends BaseController {

    /**
     * 用户中心
     *
     * @param request
     * @return
     */
    @GetMapping("userCenter")
    public String userCenter(HttpServletRequest request, Model model) {
        return "/pc/user/usercenter";
    }

}
