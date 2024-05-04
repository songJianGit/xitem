package com.xxsword.xitem.admin.handler;

import com.xxsword.xitem.admin.model.Codes;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.system.RecordService;
import com.xxsword.xitem.admin.utils.RecordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常捕获
 */
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Slf4j
public class ExceptionHandler {

    @Autowired
    private RecordService recordService;

    /**
     * 未知异常全局捕获
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
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
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public RestResult error(RuntimeException e) {
        e.printStackTrace();
        recordService.save(RecordUtils.errorToRecord(RecordUtils.stacktraceToString(e)));
        return RestResult.Codes(Codes.RUNTIME_EXCEPTION);
    }


    /**
     * 接口不存在
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    public RestResult error(NoHandlerFoundException e) {
        e.printStackTrace();
        recordService.save(RecordUtils.errorToRecord(RecordUtils.stacktraceToString(e)));
        return RestResult.Codes(Codes.NO_HANDLER_FOUND_EXCEPTION);
    }

}
