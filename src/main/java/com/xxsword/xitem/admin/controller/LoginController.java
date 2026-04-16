package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.entity.Function;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.Codes;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.utils.CaptchaUtils;
import com.xxsword.xitem.admin.utils.MenuUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.stream.Collectors;

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
        return httpRedirect(request, "/login");
    }

    /**
     * 用户登录
     */
    @GetMapping("login")
    public String login(HttpServletRequest request) {
        request.getSession();
        return "/admin/login";
    }

    @GetMapping
    public String toLogin(HttpServletRequest request) {
        return login(request);
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

        httpSession.setAttribute(Constant.USER_INFO, userInfo);// 将用户信息，存入session
        httpSession.setAttribute(Constant.USER_INFO_TAG, MenuUtil.listFunctionByRoles(userInfo.getRoleList()).stream().map(Function::getTag).collect(Collectors.toSet()));

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
//            verifyCode = "1234";
            request.getSession().setAttribute(Constant.CAPTCHA, verifyCode.toLowerCase());
        } catch (Exception e) {
            log.error("获取验证码异常：{}", e.getMessage());
        }
    }

    @GetMapping("getInitFlag")
    @ResponseBody
    public RestResult getInitFlag() {
        return RestResult.OK(initFlag() == null ? 1 : 0);
    }

    /**
     * 进入初始化
     *
     * @return
     */
    @GetMapping("systemInit")
    public String systemInit(HttpServletRequest request, Model model) {
        UserInfo userInfo = initFlag();
        if (userInfo == null) {
            return "/404";
        }
        userInfoService.setUserInfoRoleAndFun(userInfo, true, false);
        model.addAttribute("user", userInfo);
        request.getSession().setAttribute(Constant.USER_INFO, userInfo);// 将用户信息，存入session，保证save成功
        return "/admin/init";
    }

    /**
     * 系统的初始化标记获取（先检查这个系统是否被初始化）
     *
     * @return 返回null 表示 已经初始化
     */
    private UserInfo initFlag() {
        UserInfo userInfo = userInfoService.getById("1");
        if (userInfo == null) {
            return null;
        }
        Integer flag = userInfo.getInitFlag();
        if (flag == null) {
            return null;
        }
        if (flag.equals(1)) {
            return null;
        }
        long count = userInfoService.count();
        if (count == 1 && flag == 0) {
            return userInfo;// 没有初始化
        }
        return null;
    }
}
