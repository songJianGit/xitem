package com.xxsword.xitem.pc;

import com.xxsword.xitem.admin.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class PcLoginController extends BaseController {

    @GetMapping("pclogin")
    public String pclogin() {
        return "/pc/pclogin";
    }

}
