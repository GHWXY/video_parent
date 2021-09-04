package com.wxy.services_base.exception;

import com.wxy.utils.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器, 只要发生了异常,如果在自己控制当中 没有去捕获 , 就会到此控制器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e){
        e.printStackTrace();
        return ResponseResult.error();
    }

    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(FmException.class)
    @ResponseBody //返回json数据
    public ResponseResult error(FmException e) {
        e.printStackTrace();
        return ResponseResult.error().code(e.getCode()).message(e.getMsg());
    }
}
