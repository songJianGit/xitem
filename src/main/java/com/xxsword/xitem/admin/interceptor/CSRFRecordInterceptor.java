package com.xxsword.xitem.admin.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class CSRFRecordInterceptor implements HandlerInterceptor {
    private static final String[] REFERER_DOMAIN = new String[]{"ssword.cn"};// 域名白名单
    private static final String[] URL_SAFE = new String[]{"/admin/ueditor/ueditorConfig"};// 链接白名单
    private static final Boolean CHECK = true;// 是否开启referer校验

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!CHECK) {
            return true;
        }
        String requestURI = request.getRequestURI();
        for (String item : URL_SAFE) {
            if (requestURI.contains(item)) {// 链接是否在白名单中
                return true;
            }
        }
        // 验证非get请求
        if (!"GET".equals(request.getMethod())) {
            String referer = request.getHeader("referer");
            if (StringUtils.isBlank(referer)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);// 状态置为404
                return false;
            }
            URL url = null;
            try {
                url = new URL(referer);
            } catch (MalformedURLException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);// URL解析异常，置为404
                return false;
            }
            for (String s : REFERER_DOMAIN) {
                if (s.equals(url.getHost())) {// 域名否在白名单中
                    return true;
                }
            }
            // 请求域名和referer域名是否相同
            return request.getServerName().equals(url.getHost());
        }
        return true;
    }
}
