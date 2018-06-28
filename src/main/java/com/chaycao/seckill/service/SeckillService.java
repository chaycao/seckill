package com.chaycao.seckill.service;

import com.chaycao.seckill.dto.Exposer;
import com.chaycao.seckill.dto.SeckillExecution;
import com.chaycao.seckill.entity.Seckill;
import com.chaycao.seckill.exception.RepeatKillException;
import com.chaycao.seckill.exception.SeckillCloseException;
import com.chaycao.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在“使用者”角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型
 *
 * @author chaycao
 * @description
 * @date 2018-06-25 16:28.
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开始时输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException, RepeatKillException, SeckillCloseException;

    /**
     * 执行秒杀操作 by 存储过程
     * @param seckillId
     * @param userPhone
     * @return
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);

}
