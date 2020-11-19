package com.atwzw.servicebase.exceptionhandler;

import com.atwzw.commonutils.RestResult;
import com.atwzw.servicebase.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 指定出现什么异常执行这个方法:这里是拦截所有异常
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public RestResult error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return RestResult.error().message("执行了全局异常处理..");
    }

    // 自定义异常
    @ExceptionHandler(BztException.class)
    @ResponseBody //为了返回数据
    public RestResult error(BztException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return RestResult.error().code(e.getCode()).message(e.getMsg());
    }

}
