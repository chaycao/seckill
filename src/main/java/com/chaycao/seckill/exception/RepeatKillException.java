package com.chaycao.seckill.exception;

/**
 * @author chaycao
 * @description 重复秒杀异常（运行时异常）
 * @date 2018-06-25 16:44.
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
