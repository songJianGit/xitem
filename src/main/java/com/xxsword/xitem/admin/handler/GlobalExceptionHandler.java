package com.xxsword.xitem.admin.handler;

import com.xxsword.xitem.admin.model.Codes;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.system.RecordService;
import com.xxsword.xitem.admin.utils.RecordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常捕获
 */
//@ControllerAdvice // 传统的 Spring MVC 应用（返回视图）
@RestControllerAdvice // RESTful 应用（返回 JSON/XML）
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)// 确保代码仅在传统 Servlet 容器（如 Tomcat、Jetty）中生效，而在响应式 Web 应用（如 WebFlux）或非 Web 应用中自动禁用
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private RecordService recordService;

    /**
     * 未知异常全局捕获
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestResult error(Exception e) {
        e.printStackTrace();
        recordService.save(RecordUtils.errorToRecord(RecordUtils.stacktraceToString(e)));
        return RestResult.Codes(Codes.UNKNOWN_EXCEPTION);
    }


    /**
     * 统一业务异常处理（运行异常）
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public RestResult error(RuntimeException e) {
        e.printStackTrace();
        recordService.save(RecordUtils.errorToRecord(RecordUtils.stacktraceToString(e)));
        return RestResult.Codes(Codes.RUNTIME_EXCEPTION);
    }

}
