package com.xxsword.xitem.pc;

import com.xxsword.xitem.admin.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class PcLoginController extends BaseController {

    @GetMapping("pclogin")
    public String pclogin(HttpServletRequest request) {
        return "/pc/pclogin";
    }

    @GetMapping("pcindex")
    public String index() {
        return "/pc/index";
    }

}
