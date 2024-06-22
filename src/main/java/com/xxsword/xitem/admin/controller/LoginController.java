package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.Codes;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.utils.CaptchaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
public class LoginController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户登出
     */
    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        request.getSession();
        return "redirect:login";
    }

    /**
     * 用户登录
     */
    @GetMapping("adminLogin")
    public String login(HttpServletRequest request) {
        request.getSession();
        return "/admin/login";
    }

    @PostMapping("checkLogin")
    @ResponseBody
    public RestResult checkLogin(HttpServletRequest request, String loginName, String passWord, String captcha) {
        if (StringUtils.isBlank(captcha)) {
            return RestResult.Codes(Codes.LOGIN_NULLYANZHENG);
        }
        HttpSession httpSession = request.getSession();
        String captcha_server = (String) httpSession.getAttribute(Constant.CAPTCHA);
        if (StringUtils.isBlank(captcha_server) || !captcha_server.equalsIgnoreCase(captcha)) {
            httpSession.setAttribute(Constant.CAPTCHA, "");
            return RestResult.Codes(Codes.LOGIN_YANZHENG);
        }
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(passWord)) {
            return RestResult.Codes(Codes.LOGIN_FAIL);
        }
        if (userInfoService.lockUser(loginName)) {
            httpSession.removeAttribute(Constant.CAPTCHA);
            return RestResult.Codes(Codes.LOGIN_FAIL_MAX);
        }
        UserInfo userInfo = userInfoService.getUserInfoByloginNameAndPassword(loginName, passWord);
        if (userInfo == null) {
            httpSession.removeAttribute(Constant.CAPTCHA);
            return RestResult.Codes(Codes.LOGIN_FAIL);
        }
        userInfoService.setUserInfoRoleAndFun(userInfo, true, true);
        httpSession.setAttribute(Constant.USER_INFO, userInfo);
        List<Role> roleList = userInfo.getRoleList();
        if (roleList == null || roleList.isEmpty()) {
            httpSession.setAttribute(Constant.USER_INFO_ROLE, 0);
        } else {
            httpSession.setAttribute(Constant.USER_INFO_ROLE, 1);
        }
        userInfoService.clearLockUser(userInfo.getId());
        return RestResult.Codes(Codes.LOGIN_OK);
    }

    /**
     * 验证码
     */
    @GetMapping("getCaptcha")
    public void getYzm(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            String verifyCode = CaptchaUtils.generateCaptcha(0, 37, response.getOutputStream());
            verifyCode = "1234";
            request.getSession().setAttribute(Constant.CAPTCHA, verifyCode.toLowerCase());
        } catch (Exception e) {
            log.error("获取验证码异常：{}", e.getMessage());
        }
    }

}
