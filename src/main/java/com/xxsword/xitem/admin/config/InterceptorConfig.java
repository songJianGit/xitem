package com.xxsword.xitem.admin.config;

import com.xxsword.xitem.admin.interceptor.LoginInterceptor;
import com.xxsword.xitem.admin.interceptor.MenuInterceptor;
import com.xxsword.xitem.admin.interceptor.RecordInterceptor;
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
    private RecordInterceptor recordInterceptor;
    @Autowired
    private MenuInterceptor menuInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(loginInterceptor);
        // 配置不拦截的路径
//        ir.excludePathPatterns("/admin/system/login");
        // 配置拦截的路径
        ir.addPathPatterns("/admin/**");
        // 菜单拦截器
        InterceptorRegistration av = registry.addInterceptor(menuInterceptor);
        av.addPathPatterns("/admin/**");
        // 后台操作日志记录拦截器
        InterceptorRegistration re = registry.addInterceptor(recordInterceptor);
        re.addPathPatterns("/admin/**");
    }

}