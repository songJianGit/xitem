package com.xxsowrd.xitem.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping
@Slf4j
public class LoginController {

    @RequestMapping("getTokenInfo")
    @ResponseBody
    public Map<String, String> getTokenInfo(String token) {
        Map<String, String> map = new HashMap<>();
        log.info("token:{}", token);
        return map;
    }
}
