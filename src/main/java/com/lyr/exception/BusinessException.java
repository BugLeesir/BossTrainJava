package com.lyr.exception;

/**
 * @class BusinessException
 * @description 业务异常类,继承自RuntimeException,用于处理业务异常,包含一个构造方法,重写了getMessage方法,返回异常信息字符串
 * @author yunruili
 * @date 2024/01/14  05:18
 * @version 1.0.0
 */
public class BusinessException extends RuntimeException {
    /**
     * @description  重写了getMessage方法,返回异常信息字符串
     * @author yunruili
     * @date 2024/01/14 05:32
     * @param message 异常信息
     */
    public BusinessException(String message) {
        super(message);
    }
}
