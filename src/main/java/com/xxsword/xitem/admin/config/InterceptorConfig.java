package com.xxsword.xitem.admin.config;

import com.xxsword.xitem.admin.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private PcLoginInterceptor pcLoginInterceptor;
    @Autowired
    private RecordInterceptor recordInterceptor;
    @Autowired
    private MenuInterceptor menuInterceptor;
    @Autowired
    private CSRFRecordInterceptor csrfRecordInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 后台拦截器
        InterceptorRegistration admin = registry.addInterceptor(loginInterceptor);
        // 配置不拦截的路径
        // admin.excludePathPatterns("/admin/system/login");
        // 配置拦截的路径
        admin.addPathPatterns("/admin/**");
        // 后台菜单拦截器
        InterceptorRegistration menu = registry.addInterceptor(menuInterceptor);
        menu.addPathPatterns("/admin/**");
        // 后台操作日志记录拦截器
        InterceptorRegistration record = registry.addInterceptor(recordInterceptor);
        record.addPathPatterns("/admin/**");
        // 前端拦截器
        InterceptorRegistration pc = registry.addInterceptor(pcLoginInterceptor);
        pc.addPathPatterns("/pc/**");
        // csrf防护
        InterceptorRegistration csrf = registry.addInterceptor(csrfRecordInterceptor);
        csrf.addPathPatterns("/pc/**");
        csrf.addPathPatterns("/admin/**");
    }

}