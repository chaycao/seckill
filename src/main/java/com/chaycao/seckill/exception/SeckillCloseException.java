package com.chaycao.seckill.exception;

/**
 * @author chaycao
 * @description 秒杀关闭异常
 * @date 2018-06-25 16:46.
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
