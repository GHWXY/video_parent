package com.wxy.services_base.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor  //生成带参数的构造器
public class FmException extends RuntimeException{
    private Integer code;
    private String msg;
}
