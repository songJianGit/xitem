package com.xxsword.xitem.admin.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class CSRFRecordInterceptor implements HandlerInterceptor {
    /**
     * 域名白名单
     */
    private static final String[] REFERER_DOMAIN = new String[]{"ssword.cn"};
    // 链接白名单
    private static final String[] URL_SAFE = new String[]{"pclogin"};
    /**
     * 是否开启referer校验
     */
    private static final Boolean CHECK = true;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        if (!CHECK) {
//            return true;
//        }
//        String requestURI = request.getRequestURI();
//        for (String item : URL_SAFE) {
//            if (requestURI.contains(item)) {
//                return true;// 白名单的链接，不检查
//            }
//        }
//        String referer = request.getHeader("referer");
//        String host = request.getServerName();
//        // 验证非get请求
//        if (!"GET".equals(request.getMethod())) {
//            if (referer == null) {
//                // 状态置为404
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                return false;
//            }
//            URL url = null;
//            try {
//                url = new URL(referer);
//            } catch (MalformedURLException e) {
//                // URL解析异常，也置为404
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                return false;
//            }
//            // 判断请求域名和referer域名是否相同
//            if (!host.equals(url.getHost())) {
//                // 如果不等，判断是否在白名单中
//                for (String s : REFERER_DOMAIN) {
//                    if (s.equals(url.getHost())) {
//                        return true;
//                    }
//                }
//                return false;
//            }
//        }
        return true;
    }
}
