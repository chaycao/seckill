package com.chaycao.seckill.exception;

/**
 * @author chaycao
 * @description 秒杀相关业务异常
 * @date 2018-06-25 16:47.
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
